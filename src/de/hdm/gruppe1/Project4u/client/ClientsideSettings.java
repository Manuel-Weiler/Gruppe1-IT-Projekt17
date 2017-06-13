package de.hdm.gruppe1.Project4u.client;

import com.google.gwt.core.client.GWT;

import de.hdm.gruppe1.Project4u.shared.CommonSettings;
import de.hdm.gruppe1.Project4u.shared.LoginInfo;
import de.hdm.gruppe1.Project4u.shared.LoginService;
import de.hdm.gruppe1.Project4u.shared.LoginServiceAsync;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministration;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.ReportGenerator;
import de.hdm.gruppe1.Project4u.shared.ReportGeneratorAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;

public class ClientsideSettings extends CommonSettings{
	
	//Leere Objektvariablen, um Project4uAdministrationAsync einmalig zu instaziieren
	
	//private static Project4uAdministrationAsync project4uAdministration = null;
	private static Project4uAdministrationAsync project4uVerwaltung = null;
	private static ReportGeneratorAsync reportVerwaltung = null;
	private static LoginServiceAsync loginService = null;
	private static LoginInfo aktuellerUser = null;

	public static LoginServiceAsync getLoginService(){
		if(loginService == null){
			loginService = GWT.create(LoginService.class);
		}
		return loginService;
	}
	
	/**
	 * gibt den aktuelle eingeloggten User zurueck
	 * @return aktuellerUser
	 */
	public static LoginInfo getAktuellerUser() {
		return aktuellerUser;
	}

	/**
	 * setzt den aktuell eingeloggten User als Userd
	 */
	public static void setAktuellerUser(LoginInfo nutzer) {
		ClientsideSettings.aktuellerUser = nutzer;
	}


	//Sollte es keine Instanz dieser Klasse geben, so wird diese hier erzeugt.
	public static Project4uAdministrationAsync getProject4uVerwaltung(){
		
		if(project4uVerwaltung == null){
			project4uVerwaltung = GWT.create(Project4uAdministration.class);
		}
		
		return project4uVerwaltung;
	}
	
	/**
	 * Anlegen und Auslesen des applikationsweit eindeutigen ReportGenerators.
	 * Diese Methode erstellt den ReportGenerator, sofern dieser noch nicht
	 * existiert. Bei wiederholtem Aufruf dieser Methode wird stets das bereits
	 * zuvor angelegte Objekt zurueckgegeben.
	 * 
	 * @return Instanz des Typs ReportGeneratorAsync
	 */
	public static ReportGeneratorAsync getReportVerwaltung() {

		if (reportVerwaltung == null) {
			reportVerwaltung = GWT.create(ReportGenerator.class);
		}
		return reportVerwaltung;

	}
}
