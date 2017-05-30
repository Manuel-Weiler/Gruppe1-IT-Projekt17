package de.hdm.gruppe1.Project4u.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe1.Project4u.client.gui.NavigationsleisteWidget;
import de.hdm.gruppe1.Project4u.client.gui.NutzerForm;
import de.hdm.gruppe1.Project4u.shared.LoginService;
import de.hdm.gruppe1.Project4u.shared.LoginServiceAsync;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;

public class Project4u implements EntryPoint {

	Project4uAdministrationAsync Project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();

	private Organisationseinheit loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Please sign in to your Google Account to access application.");
	private Anchor signinLink = new Anchor("Sign In");
	private Anchor signOutLink = new Anchor("Sign Out");
	Organisationseinheit nutzer = ClientsideSettings.getAktuellerUser();
	
	
	/*
	 * Diese Methode pr�ft den Login-Status
	 * 
	 */
	public void onModuleLoad() {
		loadProject4u();
	}
		
	/*	LoginServiceAsync loginService = GWT.create(LoginService.class);
		
		loginService.login(GWT.getHostPageBaseURL() + "Project4u.html", new AsyncCallback<Organisationseinheit>() {
			// f�ngt m�gliche Fehler ab
			public void onFailure(Throwable error) {
				//Window.alert(error.getMessage());
			}

			// Falls keine Fehler auftreten:
			public void onSuccess(Organisationseinheit result) {
				loginInfo = result;
				if (loginInfo.getLoggedIn()) {
					
					
					
					Project4uVerwaltung.checkStatus(loginInfo, new AsyncCallback<Organisationseinheit>() {
						
						@Override
						public void onSuccess(Organisationseinheit result) {
							ClientsideSettings.setAktuellerUser(nutzer);
							boolean status = result.getStatus();
							if(status == true){
							loadProject4u();}
							else{
								Window.alert("Diese Email ist nicht in der Datenbank vorhanden" 
										+ "Erstelle ein neues Konto oder verwende eine andere Adresse");
							
						}
						}
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Datenbank nicht da!");
							
						}
					});
					
					
				} else {
					loadLogin();
				}
			
		}});

	}

	
	
	
	
	private void loadLogin() {
		signinLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signinLink);
		RootPanel.get("content").add(loginPanel);
		
		//RootPanel.get("Nutzer").setVisible(false);
	}
*/
	private void loadProject4u() {
		
	//TODO: in Methode loadProject4u() soll geladen werden, sobald der Nutzer eingelogt ist.
			NavigationsleisteWidget nt = new NavigationsleisteWidget();
			RootPanel.get("nav").add(nt);
			nt.homeButtonclick();
		
		/*
		Startseite startseite = new Startseite();
		startseite.loadStartseite(); */
		
		
	}
}

