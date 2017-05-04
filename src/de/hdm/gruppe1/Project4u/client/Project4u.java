package de.hdm.gruppe1.Project4u.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.hdm.gruppe1.Project4u.client.gui.Fusszeile;
import de.hdm.gruppe1.Project4u.client.gui.Navigationsleiste;
import de.hdm.gruppe1.Project4u.client.gui.NutzerForm;
import de.hdm.gruppe1.Project4u.client.gui.Startseite;
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
	
	/**
	 * MenÃ¼leiste wird als Widget erstellt.
	 */

	VerticalPanel menuPanel = new VerticalPanel();

	Button profilButton = new Button("Nutzerprofil");
	Button homeButton = new Button("Startseite");
	Button pMarktplatz = new Button("Projektmarktplätze");
	Button eBewerbungen = new Button("Eingangsbewerbungen");
	Button aBewerbungen = new Button("Ausgangsbewerbungen");

	public Widget menuWidget() {

		menuPanel.add(homeButton);
		menuPanel.add(profilButton);
		
		menuPanel.add(pMarktplatz);
		menuPanel.add(eBewerbungen);
		menuPanel.add(aBewerbungen);

		// Abstand zwischen den einzelnen Buttons
		menuPanel.setSpacing(20);

		// Layout Button
		profilButton.setPixelSize(200, 40);
		eBewerbungen.setPixelSize(200, 40);
		homeButton.setPixelSize(200, 40);
		pMarktplatz.setPixelSize(200, 40);
		aBewerbungen.setPixelSize(200, 40);

		return menuPanel;
	}

	/*
	 * Diese Methode prï¿½ft den Login-Status
	 * 
	 */
	public void onModuleLoad() {
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL() + "Project4u.html", new AsyncCallback<Organisationseinheit>() {
			// fï¿½ngt mï¿½gliche Fehler ab
			public void onFailure(Throwable error) {

			}

			// Falls keine Fehler auftreten:
			public void onSuccess(Organisationseinheit result) {
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

		/*final Navigationsleiste navigationsleiste = new Navigationsleiste();
		navigationsleiste.loadNavigation();
		final Fusszeile fusszeile = new Fusszeile();
		fusszeile.loadFusszeile();

		Startseite startseite = new Startseite();
		startseite.loadStartseite(); */
		
		
	}
}

class CheckStatusNutzerCallback implements AsyncCallback<Organisationseinheit> {
	public void onFailure(Throwable caught) {
		Window.alert("Datenbank nicht da!");
	}

	public void onSuccess(Organisationseinheit nutzer){
		ClientsideSettings.setAktuellerUser(nutzer);
		final boolean status = nutzer.getStatus();
		if(status == true){
			Startseite startseite = new Startseite();
			startseite.loadStartseite();
		} else{
			Window.alert("Diese Email ist nicht in der Datenbank vorhanden" 
					+ "Erstelle ein neues Konto oder verwende eine andere Adresse");
			NutzerForm nutzerForm = new NutzerForm();
			nutzerForm.loadNutzerForm(nutzer.getGoogleId());
		}
		
	}
}
