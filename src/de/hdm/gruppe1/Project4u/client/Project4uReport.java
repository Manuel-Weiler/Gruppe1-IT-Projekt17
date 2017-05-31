package de.hdm.gruppe1.Project4u.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.ReportGeneratorAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.client.gui.NavigationsleisteReport;
import de.hdm.gruppe1.Project4u.shared.LoginService;
import de.hdm.gruppe1.Project4u.shared.LoginServiceAsync;

public class Project4uReport  implements EntryPoint {

	//Project4uAdministrationAsync instanziieren, um deren Methoden zu verwenden
	
	//Project4uAdministrationAsync project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();
	ReportGeneratorAsync ReportGenerator = ClientsideSettings.getReportGenerator();
	
	private static String editorHtmlName = "Project4uReport.html";
	
	//TODO: Variablen f�r den Login instanziieren
	
	private Organisationseinheit loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Please sign in to your Google Account to access application.");
	private Anchor signinLink = new Anchor("Sign In");
	private Anchor signOutLink = new Anchor("Sign Out");
	Organisationseinheit nutzer = ClientsideSettings.getAktuellerUser();
	

	public void onModuleLoad() {
		loadProject4uReport();

		}
	
	private void loadProject4uReport(){
		NavigationsleisteReport naviR = new NavigationsleisteReport();
		RootPanel.get("navi").add(naviR);
		naviR.homeButtonClick();
	}
	
}
