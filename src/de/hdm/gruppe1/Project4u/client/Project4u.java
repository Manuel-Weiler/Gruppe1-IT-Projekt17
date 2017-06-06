package de.hdm.gruppe1.Project4u.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe1.Project4u.client.gui.NavigationsleisteWidget;
import de.hdm.gruppe1.Project4u.shared.LoginInfo;
import de.hdm.gruppe1.Project4u.shared.LoginService;
import de.hdm.gruppe1.Project4u.shared.LoginServiceAsync;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;

public class Project4u implements EntryPoint {

	Project4uAdministrationAsync Project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();
	


	static final int REFRESH_INTERVAL = 5000; // ms
	  private VerticalPanel mainPanel = new VerticalPanel();
	  private FlexTable stocksFlexTable = new FlexTable();
	  private HorizontalPanel addPanel = new HorizontalPanel();
	  private Label lastUpdatedLabel = new Label();
	    private LoginInfo loginInfo = null;
	    private VerticalPanel loginPanel = new VerticalPanel();
	    private Label loginLabel = new Label("Please sign in to your Google Account to access the StockWatcher application.");
	    private Anchor signInLink = new Anchor("Sign In");
	    private Anchor signOutLink = new Anchor("Sign Out");

	  public void onModuleLoad() {
		// Check login status using login service.
		  LoginServiceAsync loginService = GWT.create(LoginService.class);
		  loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
		  public void onFailure(Throwable error) {
		  }
		  public void onSuccess(LoginInfo result) {
		  loginInfo = result;
		  if(loginInfo.isLoggedIn()) {
		  loadProject4u();
		  } else {
		  loadLogin();
		  }
		  }
		  });
		  }

	      public void onSuccess(LoginInfo result) {
	        loginInfo = result;
	        if(loginInfo.isLoggedIn()) {
	          loadProject4u();
	        } else {
	          loadLogin();
	        }
	      }


	  
	  private void loadLogin() {
			// Assemble login panel.
			signInLink.setHref(loginInfo.getLoginUrl());
			loginPanel.add(loginLabel);
			loginPanel.add(signInLink);
			RootPanel.get("content").add(loginPanel);
	  }
	

		


	private void loadProject4u() {
			NavigationsleisteWidget nt = new NavigationsleisteWidget();
			RootPanel.get("nav").add(nt);
			nt.homeButtonclick();


		
	
	
}


}