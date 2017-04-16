package de.hdm.gruppe1.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe1.shared.LoginService;
import de.hdm.gruppe1.shared.LoginServiceAsync;
import de.hdm.gruppe1.shared.bo.Nutzer;

public class Projektmarktplatz implements EntryPoint {

	private Nutzer loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Please sign in to your Google Account to access application.");
	private Anchor singinLink = new Anchor();
	private Anchor singoutLink = new Anchor();
	Nutzer nutzer = ClientsideSettings.getAktuellerUser();
	

	/* Diese Methode prüft den Login-Status
	 * 
	 */
	public void onModuleLoad() {
		LoginServiceAsync loginService = GWT.create(LoginService.class); 
		LoginService.login(GWT.getHostPageBaseURL()+ "Projektmarktplatz.html", new AsyncCallback<Nutzer>(){
			//fängt mögliche Fehler ab
			public void onFailure(Throwable error){
				
			}
			//Falls keine Fehler auftreten:
			public void onSuccess(Nutzer result){
				loginInfo = result;
				if(loginInfo.getLoggedIn()){
					
				}
					
			}
				)
		
	}

}
