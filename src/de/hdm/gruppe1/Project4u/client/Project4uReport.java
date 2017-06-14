package de.hdm.gruppe1.Project4u.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


import de.hdm.gruppe1.Project4u.shared.ReportGeneratorAsync;
import de.hdm.gruppe1.Project4u.client.gui.NavigationsleisteReport;
import de.hdm.gruppe1.Project4u.shared.LoginInfo;
import de.hdm.gruppe1.Project4u.shared.LoginService;
import de.hdm.gruppe1.Project4u.shared.LoginServiceAsync;

public class Project4uReport implements EntryPoint {

	// ReportGeneratorAsync instanziieren, um deren Methoden zu verwenden

	ReportGeneratorAsync ReportGenerator = ClientsideSettings.getReportVerwaltung();
	//Project4uAdministrationAsync Project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();

	private static String editorHtmlName = "Project4uReport.html";
	
	static final int REFRESH_INTERVAL = 5000; // ms
	private LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Please sign in to your Google Account to access the application.");
	private Anchor signInLink = new Anchor("Sign In");
	private Anchor signOutLink = new Anchor("Sign Out");

	public void onModuleLoad() {
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL() + editorHtmlName, new AsyncCallback<LoginInfo>() {
			public void onFailure(Throwable error) {
			}

			public void onSuccess(LoginInfo result) {
				loginInfo = result;
				if (loginInfo.isLoggedIn()) {
					loadProject4uReport();
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
		RootPanel.get("contentR").add(loginPanel);
	}

	private void loadProject4uReport() {
		NavigationsleisteReport naviR = new NavigationsleisteReport();
		RootPanel.get("navi").add(naviR);
		naviR.homeButtonClick();
	}

}
