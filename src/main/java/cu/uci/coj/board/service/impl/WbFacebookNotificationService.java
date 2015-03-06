package cu.uci.coj.board.service.impl;

import java.util.List;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;

import cu.uci.coj.board.service.WbNotificationService;
import cu.uci.coj.model.WbContest;
import cu.uci.coj.model.WbSite;

/**
*
* @author Eddy Roberto Morales Perez
*/

//@Service("wbFacebookNotificationService")
public class WbFacebookNotificationService implements WbNotificationService {

	final String accessToken = "1067682aa96aebe98aa5a3042791c0f1";
	
	Facebook facebook;	
	
	public WbFacebookNotificationService() {
		facebook = new FacebookTemplate("1067682aa96aebe98aa5a3042791c0f1");
	}

	@Override
	public void sendNotifications(List<WbSite> newcontestNotificationSites, List<WbSite> changedNotificationSites, List<WbContest> nearContestNotification) {
		String text = "";		
		WbContest contest;
		
		if(nearContestNotification != null) {
			for(int i = 0;i<nearContestNotification.size();i++) {
				contest = nearContestNotification.get(i);
				text = "The contest " + contest.getName() + " is near. More info: http://coj.uci.cu/wboard/contests.xhtml.";
				facebook.feedOperations().updateStatus(text);		
			}
		}		
	
		if(newcontestNotificationSites != null) {
			for(int i = 0;i<newcontestNotificationSites.size();i++) {
				WbSite site = newcontestNotificationSites.get(i);
				for(int j = 0;j<site.getContests().size();j++) {			
					contest = site.getContests().get(j);
					text = "New contest: " + contest.getName() + ". More info: http://coj.uci.cu/wboard/contests.xhtml.";
					facebook.feedOperations().updateStatus(text);		
				}
			}
		}
		
		if(changedNotificationSites != null) {
			for(int i = 0;i<changedNotificationSites.size();i++) {
				WbSite site = changedNotificationSites.get(i);
				for(int j = 0;j<site.getContests().size();j++) {			
					contest = site.getContests().get(j);
					text = "The contest " + contest.getName() + " has suffered changes. More info: http://coj.uci.cu/wboard/contests.xhtml.";
					facebook.feedOperations().updateStatus(text);		
				}
			}
		}		
	}
}