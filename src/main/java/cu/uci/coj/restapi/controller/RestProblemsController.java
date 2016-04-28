/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.coj.restapi.controller;


import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import cu.uci.coj.dao.ContestDAO;
import cu.uci.coj.dao.ProblemDAO;
import cu.uci.coj.dao.RecommenderDAO;
import cu.uci.coj.dao.SubmissionDAO;
import cu.uci.coj.dao.UserDAO;
import cu.uci.coj.dao.UtilDAO;
import cu.uci.coj.model.Contest;
import cu.uci.coj.model.Language;
import cu.uci.coj.model.Limits;
import cu.uci.coj.model.Problem;
import cu.uci.coj.recommender.Recommender;
import cu.uci.coj.restapi.templates.ProblemContestRest;
import cu.uci.coj.restapi.templates.ProblemDescriptionRest;
import cu.uci.coj.restapi.templates.ProblemRest;
import cu.uci.coj.restapi.utils.ErrorUtils;
import cu.uci.coj.restapi.utils.TokenUtils;
import cu.uci.coj.utils.Utils;
import cu.uci.coj.utils.paging.IPaginatedList;
import cu.uci.coj.utils.paging.PagingOptions;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import org.apache.commons.io.FileUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author lucy
 */
@Controller
@RequestMapping("/problem")
public class RestProblemsController {

    @Resource
    private ProblemDAO problemDAO;
    @Resource
    private UserDAO userDAO;
    @Resource
    private RecommenderDAO recommenderDAO;
    @Resource
    private UtilDAO utilDAO;
    @Resource
    private SubmissionDAO submissionDAO;
    @Resource
    private Utils utils;
    @Resource
    private ContestDAO contestDAO;
    
    
    @ApiOperation(value = "Obtener todos los problemas",  
            notes = "Devuelve todos los problemas del COJ.",
            response = ProblemRest.class,
            responseContainer = "List")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ejemplo de respuesta del método") })
    @RequestMapping(value = "", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<ProblemRest> getAllProblemsOrFiltrerProblems(
            @ApiIgnore String username,
            @ApiParam(value = "Filtrar por nombre, id o descripción") @RequestParam(required = false, value = "pattern") String pattern,
            @ApiIgnore Integer filterby,
            @ApiParam(value = "Filtrar por clasificación del problema (Ver filter)") @RequestParam(required = false, value = "classification", defaultValue = "-1") Integer idClassification,
            @ApiParam(value = "Filtrar por complejidad", allowableValues = "-1,1,2,3,4,5") @RequestParam(required = false, value = "complexity", defaultValue = "-1") Integer complexity) {

       /* try {
            PasswordEncoder encoder = new Md5PasswordEncoder();
            String password = encoder.encodePassword("dovier","ABC123XYZ789");
            System.out.println(password);
            Long l = new Long(15 * 60 * 1000);
            System.out.println(TokenUtils.CreateTokenUser("dovier"));
            l = new Long(15 * 60 * 1000);
            System.out.println(TokenUtils.CreateAPIKey("dovier", "cesar"));
        } catch (Exception e) {}*/

        if (filterby == null) {
            filterby = 0;
        }

        if (filterby == 5) {
            PagingOptions options = new PagingOptions(1);
            Recommender recommender = new Recommender(userDAO, problemDAO, recommenderDAO);
            List<Problem> recommendations = recommender.recommendations(username, username == null ? 0 : problemDAO.integer(
                    "select.uid.by.username", username), options.getSort(), options.getDirection());
            int found = recommendations.size();
            if (found != 0) {
                List<ProblemRest> listProblemsRest = new LinkedList();
                for (Problem p : recommendations) {
                    ProblemRest pr = BuildProblemRest(p, username);
                    listProblemsRest.add(pr);
                }

                return listProblemsRest;
            }
        }

        int found = problemDAO.countProblem(pattern, filterby, username, idClassification, complexity);

        List<Problem> listProblems = new LinkedList();
        for (int i = 1; i <= end(found); i++) {
            PagingOptions options = new PagingOptions(i);
            int x = username == null ? 0 : problemDAO.integer("select.uid.by.username", username);
            IPaginatedList<Problem> pages = problemDAO.findAllProblems("en", pattern, found, options, username, x, filterby, idClassification, complexity);
            listProblems.addAll(pages.getList());
        }

        List<ProblemRest> listProblemsRest = new LinkedList();

        for (Problem p : listProblems) {
            ProblemRest pr = BuildProblemRest(p, username);
            listProblemsRest.add(pr);
        }

        return listProblemsRest;
    }
    
    @ApiOperation(value = "Obtener todos los problemas por competencias",  
            notes = "Dado el identificador de una competencia devuelve todos los problemas utilizados en la misma.",
            response = ProblemContestRest.class,
            responseContainer = "List")
    @ApiResponses(value = { @ApiResponse(code = 404, message = "bad cid or access private")  })
    @RequestMapping(value = "/contest/{cid}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<?> getAllProblemsInContest(@ApiParam(value = "Identificador de una competencia") @PathVariable int cid) {
        
        if(!contestDAO.existsContest(cid))
            return new ResponseEntity<>(ErrorUtils.BAD_CID,HttpStatus.NOT_FOUND);
        
        String username = null;
        Contest contest = contestDAO.loadContest(cid);
        contestDAO.unfreezeIfNecessary(contest);

        if ((contest.isRunning() || contest.isPast()) && contest.isShow_problem_out()) {
            contest.setShow_status(true);
            int found = problemDAO.countProblemContest(cid);
            PagingOptions options = new PagingOptions(1);
            IPaginatedList<Problem> pages = problemDAO.getContestProblems(found,"en",username, contest, options);
            
            List<ProblemContestRest> listProblemsContestRest = new LinkedList();

            for (Problem p : pages.getList()) {
                String balloon = contest.isBalloon() == true ? p.getBalloonColor() : null;
                Integer level  = contest.getStyle() == 4 ? p.getLevel() : null;
                ProblemContestRest pcr = null;
                if(contest.getStyle()==1)
                    pcr = new ProblemContestRest(p.getPid(),""+p.getLetter(),balloon, p.getTitle(), p.getAccu(),level);
                else
                    pcr = new ProblemContestRest(p.getPid(),balloon, p.getTitle(), p.getAccu(),level);
                    
                if (contest.getStyle() == 3 )
                    pcr.setScore(p.getPoints());
                listProblemsContestRest.add(pcr);
            }
            
             return new ResponseEntity<>(listProblemsContestRest,HttpStatus.OK);        
        }

        return new ResponseEntity<>(ErrorUtils.BAD_CID,HttpStatus.NOT_FOUND);

    }
    

    @ApiOperation(value = "Obtener problemas por páginas",  
            notes = "Devuelve los problemas por páginas (50 problemas por página) como en el sitio web COJ.",
            response = ProblemRest.class,
            responseContainer = "List")
    @ApiResponses(value = { @ApiResponse(code = 404, message = "page out of index")  })
    @RequestMapping(value = "/page/{page}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<?> getAllProblemsOrderByPage(
            @ApiParam(value = "Número de la página") @PathVariable int page, 
            @ApiIgnore String username) {

        int found = problemDAO.countProblem(null, 0, username, -1, -1);
        if (page > 0 && page <= end(found)) {
            PagingOptions options = new PagingOptions(page);

            int idUsername = username == null ? 0 : problemDAO.integer("select.uid.by.username", username);

            IPaginatedList<Problem> pages = problemDAO.findAllProblems("en", null, found, options, username, idUsername, 0, -1, -1);

            List<Problem> listProblems = pages.getList();
            List<ProblemRest> listProblemsRest = new LinkedList();

            for (Problem p : listProblems) {
                ProblemRest pr = BuildProblemRest(p, username);
                listProblemsRest.add(pr);
            }

            return new ResponseEntity<>(listProblemsRest, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ErrorUtils.PAGE_OUT_OF_INDEX, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Obtener descripción del problema",  
            notes = "Devuelve la descripción de un problema dado su identificador.",
            response = ProblemDescriptionRest.class)
    @ApiResponses(value = { @ApiResponse(code = 404, message = "bad pid")  })
    @RequestMapping(value = "/{pid}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<?> getProblemDescriptionsByID(
            @ApiParam(value = "Identificador del problema") @PathVariable int pid,
            @ApiParam(value = "Idioma del problema (Ver filter)") @RequestParam(required = false, value = "locale", defaultValue = "en") String locale) {
        
        if (!problemDAO.exists(pid) )
            return new ResponseEntity<>(ErrorUtils.BAD_PID, HttpStatus.NOT_FOUND);
        
        Problem p = null;
        try {
            p = problemDAO.getProblemByCode(locale, pid, false);
        } catch (NullPointerException ne) {
            return new ResponseEntity<>(ErrorUtils.BAD_PID, HttpStatus.NOT_FOUND);
        }
        p.setDate(p.getDate().split(" ")[0]);
        problemDAO.fillProblemLanguages(p);
        problemDAO.fillProblemLimits(p);
        Recommender recommender = new Recommender(userDAO, problemDAO, recommenderDAO);
        List<Problem> recommendations = recommender.findSimilarProblems(p);
 
        List<String> languages = new LinkedList();
        List<Long> totaltime = new LinkedList();
        List<Long> testtime = new LinkedList();
        List<String> memory = new LinkedList();
        List<String> size = new LinkedList();
        for (Language leng : p.getLanguages()) {
            for (Limits limit : p.getLimits()) {
                if (leng.getLid() == limit.getLanguageId()) {
                    totaltime.add(limit.getMaxTotalExecutionTime());
                    testtime.add(limit.getMaxCaseExecutionTime());
                    memory.add(FileUtils.byteCountToDisplaySize(limit.getMaxMemory()));
                    size.add(FileUtils.byteCountToDisplaySize(limit.getMaxSourceCodeLenght()));
                }

            }
            languages.add(leng.getLanguage());
        }

        List<String> recomended = new LinkedList();

        for (Problem pro : recommendations) {
            recomended.add("" + pro.getPid());
        }

        String[] arreglo = author_source(pid);
        ProblemDescriptionRest problemDescrptions;
        problemDescrptions = new ProblemDescriptionRest(p.getAuthor(),arreglo[0].trim(),arreglo[1].trim(), p.getUsername(), p.getDate(), totaltime, testtime, memory, "64 MB", size, languages, p.getDescription(), p.getInput(), p.getOutput(), p.getInputex(), p.getOutputex(), p.getComments(), recomended);

        return new ResponseEntity<>(problemDescrptions, HttpStatus.OK);

    }
    
    private ProblemRest BuildProblemRest(Problem p,String username){
        if(username == null)
            return new ProblemRest(p.getPid(), p.getTitle(), p.getSubmissions(), p.getAc(), p.getAvgs(), p.getPoints());
        return new ProblemRest(p.getPid(), p.getTitle(), p.getSubmissions(), p.getAc(), p.getAvgs(), p.getPoints(), p.isFavorite(), ResolveStatus(p, username));
    }
    
    private String[] author_source(int pid){
        String[] arreglo = new String[2];
        
        String s = problemDAO.string("select.author.problem.id",pid);
        if(s.contains("[")){
            s = s.replace("[", "!");
            arreglo = s.split("!");
            arreglo[1] = arreglo[1].replace("]", "");
        }
        else
        {
            arreglo[0] = s;
            arreglo[1] = "";
        }
        return arreglo;
    }
            
    
    private int end(int found) {
        if (found % 50 == 0) {
            return found / 50;
        } else {
            return (found / 50) + 1;
        }
    }

    private String ResolveStatus(Problem p, String username) {
        String s = "not send";
        if (p.isSolved()) {
            s = "solved";
        }
        if (p.isUnsolved()) {
            s = "unsolved";
        }
        if (username == null) {
            s = "not logged";
        }
        return s;
    }

    private int ValidateApiAndToken(String apikey, String token) throws IOException {
        
        if (apikey == null || token == null) {
            return 8;
        }

        try {
            int error = TokenUtils.ValidateAPIKey(apikey);
            if (error > 0) {
                return error;
            }

            int error2 = TokenUtils.ValidateTokenUser(token);
            if (error2 > 0) {
                return error2;
            }
        } catch (Exception e) {
            return 9;
        }

        return 0;
    }
    
    

    private String ExtractUser(String token) {
        String username = null;
        try {
            username = TokenUtils.getUsernameFromToken(token);
        } catch (Exception ex) {
            Logger.getLogger(RestProblemsController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return username;
    }  

    //------------------------------PRIVATE METHODS (TOKEN NECESSARY)-------------------------------
   
    @ApiOperation(value = "Obtener todos los problemas (Privado)",  
            notes = "Devuelve todos los problemas del COJ.",
            response = ProblemRest.class,
            responseContainer = "List")
    @ApiResponses( value = { @ApiResponse(code = 401, message = "username token mismatch, hash incorrect, token expirated, username apikey mismatch, apikey hash incorrect, apikey expirated, apikey secret incorrect, token or apikey incorrect") })
    @RequestMapping(value = "", method = RequestMethod.POST, headers = "Accept=application/json", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public ResponseEntity<?> getAllProblemsOrFiltrerProblemsPrivate(
            @ApiParam(value = "Llave de desarrollador")@RequestParam(required = true, value = "apikey") String apikey,
            @ApiParam(value = "Token de usuario")@RequestParam(required = true, value = "token") String token,
            @ApiParam(value = "Filtrar por nombre, id o descripción") @RequestParam(required = false, value = "pattern") String pattern,
            @ApiParam(value = "Filtrar por Todos(0), Resueltos(1), Por Resolver(2), Por Intentar(3), Favoritos(4), Recommendados(5)",allowableValues = "0,1,2,3,4,5") @RequestParam(required = false, value = "filterby", defaultValue = "0") Integer filterby,
            @ApiParam(value = "Filtrar por clasificación del problema (Ver filter)") @RequestParam(required = false, value = "classification", defaultValue = "-1") Integer classification,
            @ApiParam(value = "Filtrar por complejidad", allowableValues = "-1,1,2,3,4,5") @RequestParam(required = false, value = "complexity", defaultValue = "-1") Integer complexity) {
        try {
            int error = ValidateApiAndToken(apikey,token);
            if (error > 0) {
                if(error == 8)
                    return new ResponseEntity<>(TokenUtils.ErrorMessage(8), HttpStatus.BAD_REQUEST);
                else    
                    return new ResponseEntity<>(TokenUtils.ErrorMessage(error), HttpStatus.UNAUTHORIZED);
            }

            String username = null;
            username = ExtractUser(token);

            List<ProblemRest> listproblemrest = getAllProblemsOrFiltrerProblems(username, pattern, filterby, classification, complexity);

            return new ResponseEntity<>(listproblemrest, HttpStatus.OK);
        } catch (IOException ex) {
            return new ResponseEntity<>(TokenUtils.ErrorMessage(8), HttpStatus.BAD_REQUEST);
        }

    }

    
    @ApiOperation(value = "Obtener problemas por páginas (Privado)",  
            notes = "Devuelve los problemas por páginas (50 problemas por página) como en el sitio web COJ.",
            response = ProblemRest.class,
            responseContainer = "List")
    @ApiResponses(value = { @ApiResponse(code = 401, message = "username token mismatch, hash incorrect, token expirated, username apikey mismatch, apikey hash incorrect, apikey expirated, apikey secret incorrect, token or apikey incorrect"),
                            @ApiResponse(code = 404, message = "page out of index")  })
    @RequestMapping(value = "/page/{page}", method = RequestMethod.POST, headers = "Accept=application/json", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public ResponseEntity<?> getAllProblemsOrderByPagePrivate(
            @ApiParam(value = "Llave de desarrollador") @RequestParam(value = "apikey") String apikey,
            @ApiParam(value = "Token de usuario") @RequestParam(value = "token") String token,
            @ApiParam(value = "Número de la página") @PathVariable int page) {
        try {

           int error = ValidateApiAndToken(apikey,token);
            if (error > 0) {
                if(error == 8)
                    return new ResponseEntity<>(TokenUtils.ErrorMessage(8), HttpStatus.BAD_REQUEST);
                else    
                    return new ResponseEntity<>(TokenUtils.ErrorMessage(error), HttpStatus.UNAUTHORIZED);
            }

            String username = null;
            username = ExtractUser(token);

            return getAllProblemsOrderByPage(page, username);

        } catch (IOException ex) {
           return new ResponseEntity<>(TokenUtils.ErrorMessage(8), HttpStatus.BAD_REQUEST);
        }

    }
   
    
    @ApiOperation(value = "Agregar/Quitar problema como favorito",  
            notes = "Cambiar el estado de favorito de un problema dado el identificador del mismo")
    @ApiResponses(value = { @ApiResponse(code = 401, message = "username token mismatch, hash incorrect, token expirated, username apikey mismatch, apikey hash incorrect, apikey expirated, apikey secret incorrect, token or apikey incorrect"),
                            @ApiResponse(code = 404, message = "page out of index")  })
    @RequestMapping(value = "/togglefavorite", method = RequestMethod.POST, headers = "Accept=application/json", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public ResponseEntity<?> togglefavorite(
            @ApiParam(value = "Llave del desarrollador") @RequestParam(required = true, value = "apikey") String apikey,
            @ApiParam(value = "Token de usuario") @RequestParam(required = true, value = "token") String token,
            @ApiParam(value = "Marcar o no como favorito") @RequestParam(required = true, value = "favorite", defaultValue = "false") Boolean favorite,
            @ApiParam(value = "Identificador del problema") @RequestParam(required = true, value = "pid") Integer pid) {
        try {
            int error = ValidateApiAndToken(apikey,token);
            if (error > 0) {
                if(error == 8)
                    return new ResponseEntity<>(TokenUtils.ErrorMessage(8), HttpStatus.BAD_REQUEST);
                else    
                    return new ResponseEntity<>(TokenUtils.ErrorMessage(error), HttpStatus.UNAUTHORIZED);
            }

            String username = null;          
            username = ExtractUser(token);           

            try {
                int uid = problemDAO.integer("select.uid.by.username", username);
                if (!problemDAO.bool("problem.ismark.asfavorite.byuser", uid, pid)
                        && problemDAO.bool("exist.problem.bypid", pid) && favorite) {
                    problemDAO.dml("problem.mark.asfavorite.byuser", uid, pid);
                    
                }
                
                if (problemDAO.bool("problem.ismark.asfavorite.byuser", uid, pid) && !favorite) {
                    problemDAO.dml("problem.unmark.favorite.byuser", uid, pid);
                }
                
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (EmptyResultDataAccessException e) {
                return new ResponseEntity<>(ErrorUtils.BAD_PID, HttpStatus.BAD_REQUEST);
            }
           

        } catch (IOException ex) {
            return new ResponseEntity<>(TokenUtils.ErrorMessage(8), HttpStatus.BAD_REQUEST);
        }
    }
    
    
   
    
    
   
}
