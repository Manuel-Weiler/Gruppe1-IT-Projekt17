package de.hdm.gruppe1.Project4u.client.gui;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Bewerbung;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;
import de.hdm.gruppe1.Project4u.shared.bo.Projektmarktplatz;

public class AusgangsbewerbungenWidget {

	Project4uAdministrationAsync Project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();
	private String erg;
	VerticalPanel vp = new VerticalPanel();
	
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
		RootPanel.get("contentHeader").add(new Label("Alle "));
		
		Project4uVerwaltung.getAllBewerbungenOfUser(ClientsideSettings.getAktuellerUser(), new AsyncCallback<Vector<Bewerbung>>() {
			
			@Override
			public void onSuccess(Vector<Bewerbung> result) {
				vp.add(createTableOfUserbewerbungen(result));
				RootPanel.get("content").clear();
				RootPanel.get("content").add(vp);
			}
			public void onFailure(Throwable caught) {}
		});
		
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	private CellTable<Bewerbung> createTableOfUserbewerbungen (Vector<Bewerbung> bewerbungen){
		
		CellTable<Bewerbung> userBewerbungen = new CellTable<Bewerbung>(KEY_PROVIDER);
		
		// Die Spalten der Bewerbung-Tabelle werden erstellt und deren
		// Inhalt definiert.
		//TODO:
		TextColumn<Bewerbung> projekt = new TextColumn<Bewerbung>() {
			public String getValue(Bewerbung object) {
				return object.getBewerbungstext();
			}
		};
		
		//TODO:
		TextColumn<Bewerbung> ausschreibung = new TextColumn<Bewerbung>() {
			public String getValue(Bewerbung object) {
				
				return getAusschreibungsnameOfBewerbung(object);
			}
		};
		
		
		DateCell datecell = new DateCell(); 
		Column<Bewerbung, Date> erstelldatum = new Column<Bewerbung, Date> (datecell){

			@Override
			public Date getValue(Bewerbung object) {
				return object.getErstelldatum();
			}	
		};
		
		TextColumn<Bewerbung> status = new TextColumn<Bewerbung>() {
			public String getValue(Bewerbung object) {
				return object.getStatus();
			}
		};
		
		
		
		
		/*
		 * Hinzuf�gen der Spalten zur Tabelle, in der Reihenfolge von
		 * Links nach Rechts. Definition der Spaltennamen.
		 */

		userBewerbungen.addColumn(projekt, "Projekt");
		userBewerbungen.addColumn(ausschreibung, "Ausschreibung");
		userBewerbungen.addColumn(erstelldatum, "Erstelldatum");
		userBewerbungen.addColumn(status, "Status");
		

		// F�llen der Tabelle ab dem Index 0.
		userBewerbungen.setRowData(0, bewerbungen);

		// Anpassen des Widgets an die Breite des div-Elements "content"
		userBewerbungen.setWidth(RootPanel.get("content").getOffsetWidth() + "px");

		//TODO: ggf. pager
		
		return userBewerbungen;
	}
	
	private String getAusschreibungsnameOfBewerbung(Bewerbung b){
		
		Project4uVerwaltung.findByIdAusschreibung(b.getAusschreibungId(), new AsyncCallback<Ausschreibung>() {
			
			@Override
			public void onSuccess(Ausschreibung result) {
				erg = result.getBezeichnung();
				Window.alert(erg);
				
			}
			
			@Override
			public void onFailure(Throwable caught) {	
			}
		});
		
		return erg;
		
	}
	
	
}
