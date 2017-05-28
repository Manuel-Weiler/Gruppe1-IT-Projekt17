package de.hdm.gruppe1.Project4u.client.gui;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;

import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;
import de.hdm.gruppe1.Project4u.shared.bo.Projektmarktplatz;

public class ProjektWidget extends Composite{
	
	Project4uAdministrationAsync Project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();
	Projektmarktplatz projektmarktplatz = new Projektmarktplatz();
	
	//TODO: Projekt anlegen-Maske implementieren & Clickhandler hinzuf�gen
	Button addProjekt = new Button("Projekt anlegen");
	
	//TODO: Projekt löschen,  Ausschreibungen


	/*
	 * Der Key-Provider vergibt jedem Objekt der Tabelle eine Id, damit auch einzelne Objekte der
	 * in der Liste weiter verarbeitet werden k�nnen. 
	 */
	public static final ProvidesKey<Projekt> KEY_PROVIDER = new ProvidesKey<Projekt>() {
		public Object getKey(Projekt item) {
			return item == null ? null : item.getProjektId();
		}
	};
	
	public ProjektWidget (Vector<Projekt> projekte, Projektmarktplatz pMarktpl){
		
		this.projektmarktplatz =pMarktpl;
		
		VerticalPanel vPanel = new VerticalPanel();
		
		//Pr�fung, ob schon Projekte zum Projektmarktplatz existieren
		if (projekte.isEmpty()){
			vPanel.clear();
			Label noProjekt = new Label("Es existiert noch kein Projekt, lege eines an!");
			vPanel.add(noProjekt);
			vPanel.add(addProjekt);
			initWidget(vPanel);
			addProjekt.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					Projekt neu = new Projekt();
					projektChange(neu, projektmarktplatz);
					
				}
			});
		}
		else{
			vPanel.clear();
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
			
			TextColumn<Projekt> description = new TextColumn<Projekt>() {
				public String getValue(Projekt object) {
					return object.getBeschreibung();
				}
			};
			
			Project4uVerwaltung.findAusschreibungbyProjekt(projekt, new AsyncCallback<Vector<Ausschreibung>>() {
				
				@Override
				public void onSuccess(Vector<Ausschreibung> result) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
			});
			
			SelectionCell ausschreibungen = new SelectionCell(null);
			Column<Projekt, String> ausschreibungSpalte = new Column<Projekt, String>(ausschreibungen) {
				
				@Override
				public String getValue(Projekt object) {
					// TODO Auto-generated method stub
					return null;
				}
			};
			
			/*
			 * Das SelectionModel wird zur Tabelle der Projektmarktpl�tze hinzugef�gt
			 * und gew�hrleistet, �hnlich einem ClickHandler, dass beim Klicken auf
			 * eine Tabellenzeile das jeweilige Objekt zur�ckgegeben wird.
			 */
			final SingleSelectionModel<Projekt> selectionModel = new SingleSelectionModel<Projekt>(KEY_PROVIDER);	
			projektTabelle.setSelectionModel(selectionModel);
			selectionModel.addSelectionChangeHandler(new Handler() {
				
				@Override
				public void onSelectionChange(SelectionChangeEvent event) {
					final DialogBox diBox = new DialogBox();
					VerticalPanel vPanel = new VerticalPanel();
					Button seeProjekt = new Button("Projekt ansehen");
					Button deleteProjekt = new Button("Projekt löschen");
					Button changeProjekt = new Button("Projekt bearbeiten");
					changeProjekt.addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							projektChange(selectionModel.getSelectedObject(), projektmarktplatz);
							diBox.hide();
						}
					});
					vPanel.add(seeProjekt);
					vPanel.add(deleteProjekt);
					vPanel.add(changeProjekt);
					diBox.add(vPanel);
					seeProjekt.setPixelSize(270, 30);
					deleteProjekt.setPixelSize(270, 30);
					changeProjekt.setPixelSize(270, 30);
					diBox.setAnimationEnabled(true);
					diBox.setAutoHideEnabled(true);
					diBox.center();
					diBox.show();
				}});
			
			/**
			 * Hinzuf�gen der Spalten zur Tabelle, in der Reihenfolge von Links nach
			 * Rechts. Definition der Spaltennamen.
			 */
			
			projektTabelle.addColumn(nameColumn, "Name");
			projektTabelle.addColumn(startdatum, "Startdatum");
			projektTabelle.addColumn(enddatum, "Enddatum");
			projektTabelle.addColumn(description, "Beschreibung");
			
			//F�llen der Tabelle ab dem Index 0.
			projektTabelle.setRowData(0,  projekte);
			
			//Anpassen des Widgets an die Breite des div-Elements "content"
			projektTabelle.setWidth(RootPanel.get("content").getOffsetWidth()+"px");
			
			
			vPanel.add(projektTabelle);
			initWidget(vPanel);
	}
		
}
	protected void projektChange( final Projekt p, final Projektmarktplatz m){
		final DialogBox db = new DialogBox();
		VerticalPanel vp = new VerticalPanel();
		//TODO: Projektleiter implementieren
		
		Label name = new Label("Name:");
		vp.add(name);
		final TextBox nam = new TextBox();
		vp.add(nam);
		
		
		Label sdate = new Label("Startdatum:");
		vp.add(sdate);
		final DateBox stdate = new DateBox();
		final DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyy-MM-dd");
		
		stdate.setFormat(new DateBox.DefaultFormat(dateFormat));		
		vp.add(stdate);
		
		
		Label edate = new Label("Enddatum:");
		vp.add(edate);
		final DateBox endate = new DateBox();	
		endate.setFormat(new DateBox.DefaultFormat(dateFormat));	
		vp.add(endate);
		
		
		Label beschreibung = new Label("Beschreibung:");
		vp.add(beschreibung);
		final TextArea beschr = new TextArea();		
		beschr.setWidth("270px");
		beschr.setHeight("150px");
		beschr.addKeyPressHandler(new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (beschr.getValue().length()>250){
					Window.alert("Es sind maximal 250 Zeichen als Beschreibung erlaubt.");
				}
			}
		});
		vp.add(beschr);
		
		//Nur wenn es sich um kein neu erzeugtes Projekt handelt, sollen die Werte übernommen werden.
		if(p.getProjektId()!=0){
		nam.setValue(p.getName());
		stdate.setValue(p.getStartdatum());
		endate.setValue(p.getEnddatum());
		beschr.setValue(p.getBeschreibung());}
		
		Button saveButton = new Button("Speichern");
		saveButton.setPixelSize(270, 30);
		saveButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if((!nam.getValue().isEmpty())&&(!beschr.getValue().isEmpty())){
				p.setName(nam.getValue());
				p.setStartdatum(stdate.getValue());
				p.setEnddatum(endate.getValue());
				p.setBeschreibung(beschr.getValue());
				p.setProjektmarktplatzId(m.getProjektmarktplatzId());
				
				/*
				 * An dieser Stelle wird geprüft, ob die ID des Projektes noch der default-Wert ist,
				 * also ob eine INSERT oder UPDATE-Operation auf der DB ausgeführt werden muss 
				 */
				
				if(p.getProjektId()!=0){
					Project4uVerwaltung.update(p, new AsyncCallback<Void>() {
						public void onSuccess(Void result) {
							
							Project4uVerwaltung.findAllProjekteOfProjektmarktplatz(m,
									new AsyncCallback<Vector<Projekt>>() {
								
								public void onSuccess(Vector<Projekt> result) {
									db.hide();
									RootPanel.get("content").clear();
									RootPanel.get("content").add(new ProjektWidget(result, m));
									RootPanel.get("contentHeader").clear();
									RootPanel.get("contentHeader")
											.add(new Label("Alle Projekte des Projektmarktplatzes "
													+ m.getName()));
								}
								@Override
								public void onFailure(Throwable caught) {												
								}
							});
							}
						public void onFailure(Throwable caught) {
							
						}
					});
				}
				//Der else-fall tritt ein, wenn ein neues Projekt erstellt wird.
				else{
					
					//TODO: Organisationseinheit or gegen ID des Projektleiters tauschen.
					
					Organisationseinheit or = new Organisationseinheit();
					or.setOrganisationseinheitId(2);
					Project4uVerwaltung.createProjekt(p, m, or, new AsyncCallback<Projekt>() {
						
						@Override
						public void onSuccess(Projekt result) {
							
							//Nach dem Speichern des Projektes wird das ProjektWidget erstellt und im contend-div angezeigt.
							Project4uVerwaltung.findAllProjekteOfProjektmarktplatz(m,
									new AsyncCallback<Vector<Projekt>>() {
								
								public void onSuccess(Vector<Projekt> result) {
									db.hide();
									RootPanel.get("content").clear();
									RootPanel.get("content").add(new ProjektWidget(result, m));
									RootPanel.get("contentHeader").clear();
									RootPanel.get("contentHeader")
											.add(new Label("Alle Projekte des Projektmarktplatzes "
													+ m.getName()));
								}
								@Override
								public void onFailure(Throwable caught) {												
								}
							});
							
							
							}

							@Override
							public void onFailure(Throwable caught) {

							}
						});
					
				}
				
				} else {
					Window.alert("Bitte alle Felder ausfüllen");
				}}
		});
		vp.add(saveButton);
		db.add(vp);
		db.setAutoHideEnabled(true);
		db.center();
		db.show();
		
	}

}
