package de.hdm.gruppe1.Project4u.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;

import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;

public class Project4uReport implements EntryPoint {

	@Override
	public void onModuleLoad() {


		//Project4uAdministrationAsync instanziieren, um deren Methoden zu verwenden
		
		Project4uAdministrationAsync project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();
		
		//private static String editorHtmlName = "Project4u.html";
		
		//TODO: Variablen für den Login instanziieren
		
		
		
		
		Button alleAusschreibungen = new Button("Alle Ausschreibungen");
		
		//TODO: aktueller User sicherheitshalber nochmal aufrufen
		
		
		//TODO: Login erzeugen:
		
		
		//TODO: Navigationsleiste einbinden/ erstellen
		
		
		//TODO: Prüfen, ob der asynchrone Callback funktioniert
		
		
	}
	
	

}
