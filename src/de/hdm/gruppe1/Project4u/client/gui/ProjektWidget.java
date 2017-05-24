package de.hdm.gruppe1.Project4u.client.gui;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ProvidesKey;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;
import de.hdm.gruppe1.Project4u.shared.bo.Projektmarktplatz;

public class ProjektWidget extends Composite{
	
	//TODO: Projekt anlegen-Maske implementieren & Clickhandler hinzuf�gen
	Button addProjekt = new Button("Projekt anlegen");
	


	/*
	 * Der Key-Provider vergibt jedem Objekt der Tabelle eine Id, damit auch einzelne Objekte der
	 * in der Liste weiter verarbeitet werden k�nnen. 
	 */
	public static final ProvidesKey<Projekt> KEY_PROVIDER = new ProvidesKey<Projekt>() {
		public Object getKey(Projekt item) {
			return item == null ? null : item.getProjektId();
		}
	};
	
	public ProjektWidget (Vector<Projekt> projekte){
		//Pr�fung, ob schon Projekte zum Projektmarktplatz existieren
		if (projekte.isEmpty()){
			VerticalPanel vPanel = new VerticalPanel();
			Label noProjekt = new Label("Es existiert noch kein Projekt, lege eines an!");
			vPanel.add(noProjekt);
			vPanel.add(addProjekt);
			
		}
		else{
			
			CellTable<Projekt> projektTabelle = new CellTable<Projekt>(KEY_PROVIDER);
			
			//Die Spalte der Projekt-Tabelle wird erstellt und deren Inhalt definiert.
			TextColumn<Projekt> nameColumn = new TextColumn<Projekt>() {
				public String getValue(Projekt object) {
					return object.getName();
				}
			};
			
			DateCell datecell = new DateCell(); 
			Column<Projekt, Date> startdatum = new Column<Projekt, Date> (datecell){

				@Override
				public Date getValue(Projekt object) {
					return object.getStartdatum();
				}	
			};
			
			DateCell datecell2 = new DateCell(); 
			Column<Projekt, Date> enddatum = new Column<Projekt, Date> (datecell2){

				@Override
				public Date getValue(Projekt object) {
					return object.getEnddatum();
				}	
			};
			
			
			/**
			 * Hinzuf�gen der Spalten zur Tabelle, in der Reihenfolge von Links nach
			 * Rechts. Definition der Spaltennamen.
			 */
			
			
			projektTabelle.addColumn(nameColumn, "Name");
			projektTabelle.addColumn(startdatum, "Startdatum");
			projektTabelle.addColumn(enddatum, "Enddatum");
			
			//F�llen der Tabelle ab dem Index 0.
			projektTabelle.setRowData(0,  projekte);
			
			//Anpassen des Widgets an die Breite des div-Elements "content"
			projektTabelle.setWidth(RootPanel.get("content").getOffsetWidth()+"px");
		
	}
	
}
}
