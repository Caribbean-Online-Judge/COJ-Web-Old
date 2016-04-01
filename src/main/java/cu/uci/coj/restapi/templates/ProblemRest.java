/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.coj.restapi.templates;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * @author lucy
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProblemRest {
    
    Object pid;
    String ballon;
    String title;
    Integer sub;
    Integer ac;
    Double acporciento;
    Double score;
    
    //Private Atributes of User
    Boolean favorite;
    String status;
    
    Integer level;
    
    //Constructor de visualizar los problemas publicos
    public ProblemRest(Object pid, String title, int sub, int ac, double acporciento, double score) {
        this.pid = pid;
        this.title = title;
        this.sub = sub;
        this.ac = ac;
        this.acporciento = acporciento;
        this.score = score;
    }

    //Constructor de visualizar los problemas privados
    public ProblemRest(Object pid, String title, int sub, int ac, double acporciento, double score, boolean favorite, String status) {
        this.pid = pid;
        this.title = title;
        this.sub = sub;
        this.ac = ac;
        this.acporciento = acporciento;
        this.score = score;
        this.favorite = favorite;
        this.status = status;
    }

    //Construvtor para los problemas de competencia
    public ProblemRest(Object pid, String ballon, String title, int ac, Integer level) {
        this.pid = pid;
        this.ballon = ballon;
        this.title = title;
        this.ac = ac;
        this.level = level;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
    
    

    public Object getPid() {
        return pid;
    }

    public void setPid(Object pid) {
        this.pid = pid;
    }

    public String getBallon() {
        return ballon;
    }

    public void setBallon(String ballon) {
        this.ballon = ballon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSub() {
        return sub;
    }

    public void setSub(Integer sub) {
        this.sub = sub;
    }

    public Integer getAc() {
        return ac;
    }

    public void setAc(Integer ac) {
        this.ac = ac;
    }

    public Double getAcporciento() {
        return acporciento;
    }

    public void setAcporciento(Double acporciento) {
        this.acporciento = acporciento;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
    
    
    
}
