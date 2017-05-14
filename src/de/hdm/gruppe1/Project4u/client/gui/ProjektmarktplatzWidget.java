package de.hdm.gruppe1.Project4u.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.gruppe1.Project4u.shared.bo.Projektmarktplatz;




public class ProjektmarktplatzWidget extends Composite {
	
	Button addProjektmarktplatz = new Button("Projektmarktplatz anlegen");//TODO: Clickhandler und Maske für Projektmarktplatz anlegen implementieren
	Button deleteProjektmarktplatz = new Button("Projektmarktplatz löschen"); //TODO: anlegen
	Button seeProjektmarktplatz = new Button("Projektmarktplatz ansehen"); //TODO: Clickhandler: mit ProjektWidget verknüpfen

	/*
	 * Der Key-Provider vergibt jedem Objekt der Tabelle eine Id, damit auch einzelne Objekte der
	 * in der Liste weiter verarbeitet werden können. 
	 */
	public static final ProvidesKey<Projektmarktplatz> KEY_PROVIDER = new ProvidesKey<Projektmarktplatz>() {
		public Object getKey(Projektmarktplatz item) {
			return item == null ? null : item.getProjektmarktplatzId();
		}
	};
	
	public ProjektmarktplatzWidget(Vector <Projektmarktplatz> projektmarktplaetze){
		if (projektmarktplaetze.isEmpty()){
			VerticalPanel vPanel = new VerticalPanel();
			Label noProjektmarktplatz = new Label("Es existiert noch kein Projektmarktplatz, lege einen an!");
			vPanel.add(noProjektmarktplatz);
			vPanel.add(addProjektmarktplatz);
		}
		else{
			
			CellTable<Projektmarktplatz> pMarktplatzeTable = new CellTable<Projektmarktplatz>(KEY_PROVIDER);
			
			//Die Spalte der Projektmarktplatz-Tabelle wird erstellt und deren Inhalt definiert.
			TextColumn<Projektmarktplatz> nameColumn = new TextColumn<Projektmarktplatz>() {
				public String getValue(Projektmarktplatz object) {
					return object.getName();
				}
			};
			
			/**
			 * Hinzufügen der Spalten zur Tabelle, in der Reihenfolge von Links nach
			 * Rechts. Definition der Spaltennamen.
			 */
			
			pMarktplatzeTable.addColumn(nameColumn, "Name");
			
			//Füllen der Tabelle ab dem Index 0.
			pMarktplatzeTable.setRowData(0, projektmarktplaetze);
			
			//Anpassen des Widgets an die Breite des div-Elements "content"
			pMarktplatzeTable.setWidth(RootPanel.get("content").getOffsetWidth()+"px");
			
			
			
			/*
			 * Das SelectionModel wird zur Tabelle der Projektmarktplätze hinzugefügt
			 * und gewährleistet, ähnlich einem ClickHandler, dass beim Klicken auf
			 * eine Tabellenzeile das jeweilige Objekt zurückgegeben wird.
			 */
			final SingleSelectionModel<Projektmarktplatz> selectionModel = new SingleSelectionModel<Projektmarktplatz>(KEY_PROVIDER);

			pMarktplatzeTable.setSelectionModel(selectionModel);

			/*
			 * Der durch den SelectionHandler zurückgegebene Projektmarktplatz kann an eine
			 * Instanz des ProjekteWidgets übergeben werden. 
			 */
			selectionModel.addSelectionChangeHandler(new Handler() {
				
				@Override
				public void onSelectionChange(SelectionChangeEvent event) {
					DialogBox dBox = new DialogBox();
					HorizontalPanel hPanel = new HorizontalPanel();
					hPanel.add(seeProjektmarktplatz);
					hPanel.add(deleteProjektmarktplatz);
					dBox.add(hPanel);
					seeProjektmarktplatz.setPixelSize(270, 30);
					deleteProjektmarktplatz.setPixelSize(270, 30);
					
					seeProjektmarktplatz.addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							// TODO: Die Projektmarktplatzinstanz wird an eine Projektinstanz weitergegeben,
							//und diese im content Bereich angezeigt.
							
						}
					});
					deleteProjektmarktplatz.addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							// TODO: Löschen des Projektmarktplatzes nach vorangegangener Prüfung, ob
							//der Nutzer der Ersteller ist.
							
						}
					});
					dBox.setAutoHideEnabled(true);
					dBox.center();
					dBox.show();
					
					
				}
			});
			initWidget(pMarktplatzeTable);
		}
		
	}
	
	
	
}
