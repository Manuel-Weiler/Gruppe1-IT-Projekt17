package de.hdm.gruppe1.Project4u.client.gui;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.ProvidesKey;

import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Bewerbung;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;
import de.hdm.gruppe1.Project4u.shared.bo.Projektmarktplatz;

public class AusgangsbewerbungenWidget {

	Project4uAdministrationAsync Project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();
	
	
	/*
	 * Der Key-Provider vergibt jedem Objekt der Tabelle eine Id, damit auch
	 * einzelne Objekte der in der Liste weiter verarbeitet werden k�nnen.
	 */
	 
	public static final ProvidesKey<Bewerbung> KEY_PROVIDER = new ProvidesKey<Bewerbung>() {
		public Object getKey(Bewerbung item) {
			return item == null ? null : item.getBewerbungId();
		}
	};
 
	
	public AusgangsbewerbungenWidget(){
		RootPanel.get("contentHeader").clear();
		RootPanel.get("contentHeader").add(new Label("Alle Projektmarktplätze:"));
		
		
		
		
	}
}
