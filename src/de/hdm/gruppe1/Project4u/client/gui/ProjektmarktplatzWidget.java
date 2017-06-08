package de.hdm.gruppe1.Project4u.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;
import de.hdm.gruppe1.Project4u.shared.bo.Projektmarktplatz;




public class ProjektmarktplatzWidget extends Composite {
	
	Project4uAdministrationAsync Project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();
	
	Button deleteProjektmarktplatz = new Button("Projektmarktplatz löschen"); //TODO: anlegen
	Button seeProjektmarktplatz = new Button("Projektmarktplatz ansehen"); 
	
	
	
	
	/*
	 * Der Key-Provider vergibt jedem Objekt der Tabelle eine Id, damit auch einzelne Objekte der
	 * in der Liste weiter verarbeitet werden k�nnen. 
	 */
	public static final ProvidesKey<Projektmarktplatz> KEY_PROVIDER = new ProvidesKey<Projektmarktplatz>() {
		public Object getKey(Projektmarktplatz item) {
			return item == null ? null : item.getProjektmarktplatzId();
		}
	};
	
	
	public ProjektmarktplatzWidget(Vector <Projektmarktplatz> projektmarktplaetze){
		RootPanel.get("contentHeader").clear();
		RootPanel.get("contentHeader").add(new Label("Alle Projektmarktplätze:"));
		
		Button addProjektmarktplatz = new Button("Projektmarktplatz anlegen");	
		addProjektmarktplatz.addClickHandler(new addProjektmarktplatzClickHandler());
		
		final Button changeProjektmarktplatz = new Button("Projektmarktplatz bearbeiten");
		VerticalPanel vPanel = new VerticalPanel();		

		
		//Abfrage, ob bisher �berhaupt Projektmarktpl�tze existieren.
		if (projektmarktplaetze.isEmpty()){
			vPanel.clear();
			Label noProjektmarktplatz = new Label("Es existiert noch kein Projektmarktplatz, lege einen an!");
			vPanel.add(noProjektmarktplatz);
			vPanel.add(addProjektmarktplatz);
			initWidget(vPanel);
		}
		else{
			vPanel.clear();
			CellTable<Projektmarktplatz> pMarktplatzeTable = new CellTable<Projektmarktplatz>(KEY_PROVIDER);
			
			//Die Spalte der Projektmarktplatz-Tabelle wird erstellt und deren Inhalt definiert.
			TextColumn<Projektmarktplatz> nameColumn = new TextColumn<Projektmarktplatz>() {
				public String getValue(Projektmarktplatz object) {
					return object.getName();
				}
			};
			
			/**
			 * Hinzuf�gen der Spalten zur Tabelle, in der Reihenfolge von Links nach
			 * Rechts. Definition der Spaltennamen.
			 */
			
			pMarktplatzeTable.addColumn(nameColumn, "Name");
			
			//F�llen der Tabelle ab dem Index 0.
			pMarktplatzeTable.setRowData(0, projektmarktplaetze);
			
			//Anpassen des Widgets an die Breite des div-Elements "content"
			pMarktplatzeTable.setWidth(RootPanel.get("content").getOffsetWidth()+"px");
			
			
			/*
			 * Das SelectionModel wird zur Tabelle der Projektmarktpl�tze hinzugef�gt
			 * und gew�hrleistet, �hnlich einem ClickHandler, dass beim Klicken auf
			 * eine Tabellenzeile das jeweilige Objekt zur�ckgegeben wird.
			 */
			final SingleSelectionModel<Projektmarktplatz> selectionModel = new SingleSelectionModel<Projektmarktplatz>(KEY_PROVIDER);

			
			pMarktplatzeTable.setSelectionModel(selectionModel);

			/*
			 * Der durch den SelectionHandler zur�ckgegebene Projektmarktplatz kann an eine
			 * Instanz des ProjekteWidgets �bergeben werden. 
			 */
			selectionModel.addSelectionChangeHandler(new Handler() {
				
				@Override
				public void onSelectionChange(SelectionChangeEvent event) {
					final DialogBox diBox = new DialogBox();
					VerticalPanel vPanel = new VerticalPanel();
					vPanel.add(seeProjektmarktplatz);
					vPanel.add(deleteProjektmarktplatz);
					vPanel.add(changeProjektmarktplatz);
					diBox.add(vPanel);
					seeProjektmarktplatz.setPixelSize(270, 30);
					deleteProjektmarktplatz.setPixelSize(270, 30);
					changeProjektmarktplatz.setPixelSize(270, 30);
					
					
					changeProjektmarktplatz.addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							diBox.hide();
							final DialogBox Box = new DialogBox();
							
							VerticalPanel vPanel = new VerticalPanel();
							Label name = new Label("Name des Projektmarktplatzes");
							final TextBox pName = new TextBox();
							pName.setValue(selectionModel.getSelectedObject().getName()); 
							Button update = new Button("Änderungen am Projektmarktplatz speichern");
							
							update.addClickHandler(new ClickHandler() {
								
								@Override
								public void onClick(ClickEvent event) {
									if (pName.getValue()!=null){
										
										Projektmarktplatz p = selectionModel.getSelectedObject();
										p.setName(pName.getValue());
										Project4uVerwaltung.update(p, new AsyncCallback<Void>() {
											
											@Override
											public void onSuccess(Void result) {
												Project4uVerwaltung.findAllProjektmarktplatz(new AsyncCallback<Vector<Projektmarktplatz>>() {

													
													public void onSuccess(Vector<Projektmarktplatz> result) {
														
														RootPanel.get("content").clear();
														RootPanel.get("content").add(new ProjektmarktplatzWidget(result));
														
													}
													
													@Override
													public void onFailure(Throwable caught) {							
													}
												});
											}
											
											@Override
											public void onFailure(Throwable caught) {
											Window.alert(caught.getMessage());
											}
										});
										Box.hide();
									}
									else {
										Window.alert("Bitte einen Namen eingeben");
									}
								}
							});
							
							vPanel.add(name);
							vPanel.add(pName);
							vPanel.add(update);
							Box.add(vPanel);
							Box.center();
							Box.setAnimationEnabled(true);
							Box.setAutoHideEnabled(true);
							Box.show();
							
							
							
						}
					});
					
					/*
					 * Mit dem Klick auf den Button <code>seeProjektmarktplatz</code> wird die Ansicht der Projektmarktplätze geschlossen
					 * und alle Projekte zum gewählten Projektmarktplatz angezeigt.
					 */
					seeProjektmarktplatz.addClickHandler(new ClickHandler() {
												
						public void onClick(ClickEvent event) {

							Project4uVerwaltung.findByProjektmarktplatz(selectionModel.getSelectedObject(),
									new AsyncCallback<Vector<Projekt>>() {
								 
								public void onSuccess(Vector<Projekt> result) {
									diBox.hide();
									RootPanel.get("content").clear();
									RootPanel.get("content").add(new ProjektWidget(result, selectionModel.getSelectedObject()));
									
									RootPanel.get("contentHeader").clear();
									RootPanel.get("contentHeader")
											.add(new Label("Alle Projekte des Projektmarktplatzes "
													+ selectionModel.getSelectedObject().getName()));
								}
								@Override
								public void onFailure(Throwable caught) {												
								}
							});
							
						}
					});
					
					deleteProjektmarktplatz.addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							// TODO: Löschen des Projektmarktplatzes 
							
						}
					});
					diBox.setAnimationEnabled(true);
					diBox.setAutoHideEnabled(true);
					diBox.center();
					diBox.show();
					
					
				}
			});
			vPanel.add(pMarktplatzeTable);
			vPanel.add(addProjektmarktplatz);
			initWidget(vPanel);
		}
		
	}
	
	/*
	 * Clickhandler f�r das Hinzuf�gen eines neuen Projektmarktplatzes. Der neu erzeugte Projektmarktplatz
	 * wird in die DB geschrieben und das ProjektmarktplatzWidget wird neu erzeugt.
	 */
	private class addProjektmarktplatzClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			final DialogBox dBox = new DialogBox();
		
			VerticalPanel vPanel = new VerticalPanel();
			Label name = new Label("Name des neuen Projektmarktplatzes");
			final TextBox pName = new TextBox();
			Button createP = new Button("Neuen Projektmarktplatz anlegen");
			createP.addClickHandler(new ClickHandler() {

				public void onClick(ClickEvent event) {
					if (!pName.getValue().isEmpty()){
						Projektmarktplatz pPlatz = new Projektmarktplatz();
						pPlatz.setName(pName.getValue());
						
						//Dem neu erzeugten Projektmarktplatz wird der Name zugewiesen und er wird in der DB abgelegt
						Project4uVerwaltung.createProjektmarktplatz(pPlatz, new AsyncCallback<Projektmarktplatz>() {
							
							@Override
							public void onSuccess(Projektmarktplatz result) {
								
								//...anschlie�end wird das ProjektmarktplatzWidget neu erzeugt
								Project4uVerwaltung.findAllProjektmarktplatz(new AsyncCallback<Vector<Projektmarktplatz>>() {

									
									public void onSuccess(Vector<Projektmarktplatz> result) {
										RootPanel.get("content").clear();
										RootPanel.get("content").add(new ProjektmarktplatzWidget(result));

									}
									
									@Override
									public void onFailure(Throwable caught) {							
									}
								});
								
							}
							
							@Override
							public void onFailure(Throwable caught) {
								Window.alert(caught.getMessage());
								
							}
						});
												
						dBox.hide();
					}
					else{
						Window.alert("Bitte einen Namen eingeben");
					}
					
				}
			});
			vPanel.add(name);
			vPanel.add(pName);
			vPanel.add(createP);
			dBox.add(vPanel);
			dBox.center();
			dBox.show();
			dBox.setAutoHideEnabled(true);
			
		}}
	
	
		

			
			
		}
	
	

