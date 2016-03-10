/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.coj.restapi.templates;

import java.util.List;

/**
 *
 * @author cesar
 */
public class ContestDescriptionRest {
    
    String contestType;
    String contestantType;
    String accessType;
    String registrationType;
    boolean templateToVirtualContest;
    
    int penaltyB0EachRejectedSubmission;
    int frozenTime;
    int deadTime;
    
    int levels;
    int acceptedLimit;
    int acceptedByLevels;
    int pointsByProblem;
    
    List<String> programmingLanguages;
    boolean showProblemsToAll;
    boolean showJudgmentsToTheContestants;
    boolean showJudgmentsToAll;
    boolean showStandings;
    boolean showStandingstoall;
    boolean showStatisticsToTheContestants;
    boolean showStatisticsToAll;
    int goldMedals;
    int silverMedals;
    int bronzeMedals;
    
    String overwiev;

    public ContestDescriptionRest(String contestType, String contestantType, String accessType, String registrationType, boolean templateToVirtualContest, int penaltyB0EachRejectedSubmission, int frozenTime, int deadTime, int levels, int acceptedLimit, int acceptedByLevels, int pointsByProblem, List<String> programmingLanguages, boolean showProblemsToAll, boolean showJudgmentsToTheContestants, boolean showJudgmentsToAll, boolean showStandings, boolean showStandingstoall, boolean showStatisticsToTheContestants, boolean showStatisticsToAll, int goldMedals, int silverMedals, int bronzeMedals, String overwiev) {
        this.contestType = contestType;
        this.contestantType = contestantType;
        this.accessType = accessType;
        this.registrationType = registrationType;
        this.templateToVirtualContest = templateToVirtualContest;
        this.penaltyB0EachRejectedSubmission = penaltyB0EachRejectedSubmission;
        this.frozenTime = frozenTime;
        this.deadTime = deadTime;
        this.levels = levels;
        this.acceptedLimit = acceptedLimit;
        this.acceptedByLevels = acceptedByLevels;
        this.pointsByProblem = pointsByProblem;
        this.programmingLanguages = programmingLanguages;
        this.showProblemsToAll = showProblemsToAll;
        this.showJudgmentsToTheContestants = showJudgmentsToTheContestants;
        this.showJudgmentsToAll = showJudgmentsToAll;
        this.showStandings = showStandings;
        this.showStandingstoall = showStandingstoall;
        this.showStatisticsToTheContestants = showStatisticsToTheContestants;
        this.showStatisticsToAll = showStatisticsToAll;
        this.goldMedals = goldMedals;
        this.silverMedals = silverMedals;
        this.bronzeMedals = bronzeMedals;
        this.overwiev = overwiev;
    }

    public String getContestType() {
        return contestType;
    }

    public void setContestType(String contestType) {
        this.contestType = contestType;
    }

    public String getContestantType() {
        return contestantType;
    }

    public void setContestantType(String contestantType) {
        this.contestantType = contestantType;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }

    public boolean isTemplateToVirtualContest() {
        return templateToVirtualContest;
    }

    public void setTemplateToVirtualContest(boolean templateToVirtualContest) {
        this.templateToVirtualContest = templateToVirtualContest;
    }

    public int getPenaltyB0EachRejectedSubmission() {
        return penaltyB0EachRejectedSubmission;
    }

    public void setPenaltyB0EachRejectedSubmission(int penaltyB0EachRejectedSubmission) {
        this.penaltyB0EachRejectedSubmission = penaltyB0EachRejectedSubmission;
    }

    public int getFrozenTime() {
        return frozenTime;
    }

    public void setFrozenTime(int frozenTime) {
        this.frozenTime = frozenTime;
    }

    public int getDeadTime() {
        return deadTime;
    }

    public void setDeadTime(int deadTime) {
        this.deadTime = deadTime;
    }

    public int getLevels() {
        return levels;
    }

    public void setLevels(int levels) {
        this.levels = levels;
    }

    public int getAcceptedLimit() {
        return acceptedLimit;
    }

    public void setAcceptedLimit(int acceptedLimit) {
        this.acceptedLimit = acceptedLimit;
    }

    public int getAcceptedByLevels() {
        return acceptedByLevels;
    }

    public void setAcceptedByLevels(int acceptedByLevels) {
        this.acceptedByLevels = acceptedByLevels;
    }

    public int getPointsByProblem() {
        return pointsByProblem;
    }

    public void setPointsByProblem(int pointsByProblem) {
        this.pointsByProblem = pointsByProblem;
    }

    public List<String> getProgrammingLanguages() {
        return programmingLanguages;
    }

    public void setProgrammingLanguages(List<String> programmingLanguages) {
        this.programmingLanguages = programmingLanguages;
    }

    public boolean isShowProblemsToAll() {
        return showProblemsToAll;
    }

    public void setShowProblemsToAll(boolean showProblemsToAll) {
        this.showProblemsToAll = showProblemsToAll;
    }

    public boolean isShowJudgmentsToTheContestants() {
        return showJudgmentsToTheContestants;
    }

    public void setShowJudgmentsToTheContestants(boolean showJudgmentsToTheContestants) {
        this.showJudgmentsToTheContestants = showJudgmentsToTheContestants;
    }

    public boolean isShowJudgmentsToAll() {
        return showJudgmentsToAll;
    }

    public void setShowJudgmentsToAll(boolean showJudgmentsToAll) {
        this.showJudgmentsToAll = showJudgmentsToAll;
    }

    public boolean isShowStandings() {
        return showStandings;
    }

    public void setShowStandings(boolean showStandings) {
        this.showStandings = showStandings;
    }

    public boolean isShowStandingstoall() {
        return showStandingstoall;
    }

    public void setShowStandingstoall(boolean showStandingstoall) {
        this.showStandingstoall = showStandingstoall;
    }

    public boolean isShowStatisticsToTheContestants() {
        return showStatisticsToTheContestants;
    }

    public void setShowStatisticsToTheContestants(boolean showStatisticsToTheContestants) {
        this.showStatisticsToTheContestants = showStatisticsToTheContestants;
    }

    public boolean isShowStatisticsToAll() {
        return showStatisticsToAll;
    }

    public void setShowStatisticsToAll(boolean showStatisticsToAll) {
        this.showStatisticsToAll = showStatisticsToAll;
    }

    public int getGoldMedals() {
        return goldMedals;
    }

    public void setGoldMedals(int goldMedals) {
        this.goldMedals = goldMedals;
    }

    public int getSilverMedals() {
        return silverMedals;
    }

    public void setSilverMedals(int silverMedals) {
        this.silverMedals = silverMedals;
    }

    public int getBronzeMedals() {
        return bronzeMedals;
    }

    public void setBronzeMedals(int bronzeMedals) {
        this.bronzeMedals = bronzeMedals;
    }

    public String getOverwiev() {
        return overwiev;
    }

    public void setOverwiev(String overwiev) {
        this.overwiev = overwiev;
    }

    
    

   
    
    
}
