package de.hdm.gruppe1.Project4u.client.gui;

import java.util.Vector;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ProvidesKey;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;

public class ProjektWidget extends Composite{
	
	//TODO: Projekt anlegen-Maske implementieren & Clickhandler hinzufügen
	Button addProjekt = new Button("Projekt anlegen");
	


	/*
	 * Der Key-Provider vergibt jedem Objekt der Tabelle eine Id, damit auch einzelne Objekte der
	 * in der Liste weiter verarbeitet werden können. 
	 */
	public static final ProvidesKey<Projekt> KEY_PROVIDER = new ProvidesKey<Projekt>() {
		public Object getKey(Projekt item) {
			return item == null ? null : item.getProjektId();
		}
	};
	
	public ProjektWidget (Vector<Projekt> projekte){
		//Prüfung, ob schon Projekte zum Projektmarktplatz existieren
		if (projekte.isEmpty()){
			VerticalPanel vPanel = new VerticalPanel();
			Label noProjekt = new Label("Es existiert noch kein Projekt, lege einen an!");
			vPanel.add(noProjekt);
			vPanel.add(addProjekt);
		}
		else{
			
			CellTable<Projekt> projektTabelle = new CellTable<Projekt>(KEY_PROVIDER);
			
			
		
	}
	
}
}
