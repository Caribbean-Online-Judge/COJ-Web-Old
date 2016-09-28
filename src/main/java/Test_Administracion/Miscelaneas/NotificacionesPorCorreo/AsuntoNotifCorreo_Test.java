package Test_Administracion.Miscelaneas.NotificacionesPorCorreo;

import Test_Administracion.COJBoard.Competencia.Utiles_TestAdministracionCompetencia;
import cu.uci.coj.board.dao.WbContestDAO;
import cu.uci.coj.board.dao.WbSiteDAO;
import cu.uci.coj.controller.admin.MailNotificationController;
import cu.uci.coj.controller.admin.WbBoardAdminController;
import cu.uci.coj.dao.ContestDAO;
import cu.uci.coj.dao.UtilDAO;
import cu.uci.coj.mail.MailService;
import cu.uci.coj.validator.MailNotificationValidator;
import cu.uci.coj.validator.WbContestValidator;
import cu.uci.coj.validator.WbSiteValidator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.Resource;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContext.xml"})
@TestExecutionListeners(listeners={ServletTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class
})
public class AsuntoNotifCorreo_Test {

    private MockMvc mockMvc;


    @Mock
    private MailService mailService;
    @Mock
    private UtilDAO utilDAO;
    @Spy
    private MailNotificationValidator validator;
    @InjectMocks
    private MailNotificationController mailNotificationController;


    MvcResult result;
    //Variables auxiliares
    MvcResult r;

   private Utiles_TestAdministracionNotificacionesCorreo utiles_testAdministracionNotificacionesCorreo;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        utiles_testAdministracionNotificacionesCorreo = new Utiles_TestAdministracionNotificacionesCorreo();
        this.mockMvc = MockMvcBuilders.standaloneSetup(mailNotificationController).build();

        result = mockMvc.perform(post("/j_spring_security_check").param("j_username", utiles_testAdministracionNotificacionesCorreo.getUsuario()).param("j_password", utiles_testAdministracionNotificacionesCorreo.getContrasenna()))
                .andReturn();

        }



    @After
    // tearDown destruye objetos utilizados despues del test
    public void tearDown() throws Exception {

    }


    //Validando asunto de notifiaciones por correo
    @Test
    public void validarAsuntoNotificacionesCorreo() throws Exception {

        ArrayList<String> lista1 = utiles_testAdministracionNotificacionesCorreo.getAsuntosCorrectosLenguajeProgramacion();
        ArrayList<String> lista2 = utiles_testAdministracionNotificacionesCorreo.getAsuntosIncorrectosLenguajeProgramacion();

        String direccionAux="/admin/notify.xhtml";

        for (String a : lista1) {
            r = mockMvc.perform(post(direccionAux)
                    .param("subject", a)
                    .param("text", utiles_testAdministracionNotificacionesCorreo.getCuerpoCorrecta())
                    .session((MockHttpSession) result.getRequest().getSession()))
                    .andReturn();

            Object o =new Object();
            o=r.getResponse().getRedirectedUrl();
            Assert.assertEquals("Validacion de asunto incorrecta", "/admin/index.xhtml",o );
        }


        for (String a : lista2) {
            r = mockMvc.perform(post(direccionAux)
                    .param("subject", a)
                    .param("text", utiles_testAdministracionNotificacionesCorreo.getCuerpoCorrecta())
                    .session((MockHttpSession) result.getRequest().getSession()))
                    .andReturn();

            String acutal=r.getResponse().getRedirectedUrl();
            if(acutal.equals("/admin/index.xhtml"))
            {
                acutal=null;
            }
            Assert.assertEquals("Validacion de asunto incorrecta", null,acutal );

        }


    }





}