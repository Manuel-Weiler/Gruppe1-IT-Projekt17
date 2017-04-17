package de.hdm.gruppe1.Project4u.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe1.Project4u.client.gui.Fusszeile;
import de.hdm.gruppe1.Project4u.client.gui.Navigationsleiste;
import de.hdm.gruppe1.Project4u.client.gui.NutzerForm;
import de.hdm.gruppe1.Project4u.client.gui.Startseite;
import de.hdm.gruppe1.Project4u.shared.LoginService;
import de.hdm.gruppe1.Project4u.shared.LoginServiceAsync;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Nutzer;

public class Project4u implements EntryPoint {

	Project4uAdministrationAsync Project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();

	private Nutzer loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Please sign in to your Google Account to access application.");
	private Anchor signinLink = new Anchor("Sign In");
	private Anchor signOutLink = new Anchor("Sign Out");
	Nutzer nutzer = ClientsideSettings.getAktuellerUser();

	/*
	 * Diese Methode prüft den Login-Status
	 * 
	 */
	public void onModuleLoad() {
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL() + "Project4u.html", new AsyncCallback<Nutzer>() {
			// fängt mögliche Fehler ab
			public void onFailure(Throwable error) {

			}

			// Falls keine Fehler auftreten:
			public void onSuccess(Nutzer result) {
				loginInfo = result;
				if (loginInfo.getLoggedIn()) {
					Project4uVerwaltung.checkStatus(loginInfo, new CheckStatusNutzerCallback());
					loadProject4u();
				} else {
					loadLogin();
				}
			}
		});

	}

	private void loadLogin() {
		signinLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signinLink);
		RootPanel.get("NutzerForm").add(loginPanel);
		
		RootPanel.get("Nutzer").setVisible(false);
	}

	private void loadProject4u() {

		final Navigationsleiste navigationsleiste = new Navigationsleiste();
		navigationsleiste.loadNavigation();
		final Fusszeile fusszeile = new Fusszeile();
		fusszeile.loadFusszeile();

		Startseite startseite = new Startseite();
		startseite.loadStartseite();
	}
}

class CheckStatusNutzerCallback implements AsyncCallback<Nutzer> {
	public void onFailure(Throwable caught) {
		Window.alert("Datenbank nicht da!");
	}

	public void onSuccess(Nutzer nutzer){
		ClientsideSettings.setAktuellerUser(nutzer);
		final boolean status = nutzer.getStatus();
		if(status == true){
			Startseite startseite = new Startseite();
			startseite.loadStartseite();
		} else{
			Window.alert("Diese Email ist nicht in der Datenbank vorhanden" 
					+ "Erstelle ein neues Konto oder verwende eine andere Adresse");
			NutzerForm nutzerForm = new NutzerForm();
			nutzerForm.loadNutzerForm(nutzer.getEmailAddress());
		}
		
	}
}
