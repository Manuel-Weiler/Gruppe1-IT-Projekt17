package de.hdm.gruppe1.Project4u.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe1.Project4u.client.gui.MessageBox;
import de.hdm.gruppe1.Project4u.client.gui.NavigationsleisteWidget;
import de.hdm.gruppe1.Project4u.client.gui.PartnerprofilWidget;
import de.hdm.gruppe1.Project4u.shared.LoginInfo;
import de.hdm.gruppe1.Project4u.shared.LoginService;
import de.hdm.gruppe1.Project4u.shared.LoginServiceAsync;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;

public class Project4u implements EntryPoint {

	Project4uAdministrationAsync Project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();

	private static String editorHtmlName = "Project4u.html";

	static final int REFRESH_INTERVAL = 5000; // ms

	public static final NavigationsleisteWidget nt = new NavigationsleisteWidget();
	
	    private LoginInfo loginInfo = null;
	    private VerticalPanel loginPanel = new VerticalPanel();
	    private Label loginLabel = new Label("Bitte einloggen um auf die Project4u-Plattform zugreifen zu können");
	    private Anchor signInLink = new Anchor("Sign In");
	    


	public void onModuleLoad() {
		// Check login status using login service.

		LoginServiceAsync loginService = ClientsideSettings.getLoginService();
		loginService.login(GWT.getHostPageBaseURL() + editorHtmlName, new AsyncCallback<LoginInfo>() {
			public void onFailure(Throwable error) {
			}

			public void onSuccess(LoginInfo result) {
				loginInfo = result;
				ClientsideSettings.setAktuellerUser(result);
				if (loginInfo.isLoggedIn()) {
					ClientsideSettings.setAktuellerUser(result);
					loadProject4u();
					newUserCheck(result);
				} else {
					  loadLogin();
		  }
		  }
		  });
		  }
	

	  
	  private void loadLogin() {
			// Assemble login panel.
			signInLink.setHref(loginInfo.getLoginUrl());
			loginPanel.add(loginLabel);
			loginPanel.add(signInLink);
			RootPanel.get("content").add(loginPanel);
	  }


	private void loadProject4u() {

			
			RootPanel.get("nav").add(nt);
			nt.homeButtonclick();
}
	
	
	
	
	
	private void newUserCheck(LoginInfo log){
		Project4uVerwaltung.checkStatus(log, new AsyncCallback<Boolean>() {
			public void onSuccess(Boolean result) {
				if(!result){
					nt.setButtonsUnenabled();
					
					MessageBox.alertWidget("Nutzerkonto", "Sie haben noch kein Profil, bitte legen Sie eines an");
					RootPanel.get("content").clear();
					RootPanel.get("content").add(new PartnerprofilWidget());
				}				
			}				
			public void onFailure(Throwable caught) {								
			}
		});
	}

	}

