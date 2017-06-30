package de.hdm.gruppe1.Project4u.client.gui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
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
import de.hdm.gruppe1.Project4u.shared.bo.Bewerbung;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Beteiligung;

public class MeineProjekteWidget extends Composite {

	Project4uAdministrationAsync Project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();
	DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyy-MM-dd");
	VerticalPanel vp = new VerticalPanel();
	Vector<Projekt> projekte = new Vector<Projekt>();
	Projekt clickedProjekt = new Projekt();
	Organisationseinheit aktuellerNutzer= new Organisationseinheit();
	private Vector<Organisationseinheit> allProjektleiter = new Vector<>();
	HTML headingUserProjekte = new HTML("<p class='heading'>Aktuelle Projekte an denen Sie beteiligt sind: </p>");
	HTML headingUserOldProjekte = new HTML("<p class='heading'>Alte Projekte an denen Sie beteiligt waren: </p>");

	
	/*
	 * Der Key-Provider vergibt jedem Objekt der Tabelle eine Id, damit auch
	 * einzelne Objekte der in der Liste weiter verarbeitet werden k�nnen.
	 */
	public static final ProvidesKey<Projekt> KEY_PROVIDER_AKTUELL = new ProvidesKey<Projekt>() {
		public Object getKey(Projekt item) {
			return item == null ? null : item.getProjektId();
		}
	};
	public static final ProvidesKey<Projekt> KEY_PROVIDER_ALT = new ProvidesKey<Projekt>() {
		public Object getKey(Projekt item) {
			return item == null ? null : item.getProjektId();
		}
	};
	
	CellTable<Projekt> projektTabelleAktuell = new CellTable<Projekt>(KEY_PROVIDER_AKTUELL);
	CellTable<Projekt> projektTabelleAlt = new CellTable<Projekt>(KEY_PROVIDER_ALT);
	
	public MeineProjekteWidget() {
		vp.clear();
		RootPanel.get("content").clear();
		RootPanel.get("contentHeader").clear();
		RootPanel.get("contentHeader").add(new Label("Meine Projektbeteiligungen"));
		
		
		
		Project4uVerwaltung.findProjekteOfBeteiligteOrganisationseinheit(ClientsideSettings.getAktuellerUser(),
				new AsyncCallback<Vector<Projekt>>() {

					public void onSuccess(Vector<Projekt> result) {
						projekte = result;
						Project4uVerwaltung.findProjektleiterOfProjects(projekte, new AsyncCallback<Vector<Organisationseinheit>>() {
							@Override
							public void onSuccess(Vector<Organisationseinheit> result) {
								allProjektleiter = result;
								
								Date currentDate = new Date();
								Vector<Projekt> aktuelleProjekte = new Vector<>();
								Vector<Projekt> alteProjekte = new Vector<>();
								
								for(Projekt p : projekte) {
									if (p.getEnddatum().equals(currentDate) || p.getEnddatum().after(currentDate)) {
										aktuelleProjekte.add(p);
									}
								}
								for(Projekt p : projekte) {
									if (p.getEnddatum().before(currentDate)) {
										alteProjekte.add(p);
									}
								}
								RootPanel.get("content").clear();
								RootPanel.get("content").add(vp);
								
								vp.add(headingUserProjekte);
								vp.add(createTableOfCurrentUserProjekte(aktuelleProjekte));
								
								
								vp.add(headingUserOldProjekte);
								vp.add(createTableOfOldUserProjekte(alteProjekte));
								
								
								
								
								
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
	
	public CellTable<Projekt> createTableOfCurrentUserProjekte(Vector<Projekt> projekte) {
		
		TextColumn<Projekt> projektName = new TextColumn<Projekt>() {
			public String getValue(Projekt object) {
				
				return object.getName();
			}
		};
		
		
		
		TextColumn<Projekt> projektleiter = new TextColumn<Projekt>() {
			public String getValue(Projekt object) {
				String name = null;
				for (Organisationseinheit org : allProjektleiter){
					if (org.getOrganisationseinheitId() == object.getOrganisationseinheitId()){
						
						name = org.getName();
						
					}
					
				}
				return name;
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
			public String getCellStyleNames (Context context, Projekt object){
				if (object.getEnddatum().before(new Date())){
					return "rot";
				}
				else {return null;}
				
			}
		};
		
		
		TextColumn<Projekt> description = new TextColumn<Projekt>() {
			public String getValue(Projekt object) {
				return object.getBeschreibung();
			}
		};
		
		
		ButtonCell beteiligungLoeschenButton = new ButtonCell();
		Column<Projekt, String> deleteBeteiligung = new Column<Projekt, String>(beteiligungLoeschenButton) {
			@Override
			public String getValue(Projekt projekt) {
				// The value to display in the button.
				return "Beteiligung Loeschen";
			}
		};
		
		
		
		deleteBeteiligung.setFieldUpdater(new FieldUpdater<Projekt, String>() {
			
			@Override
			public void update(int index, Projekt object, String value) {
				clickedProjekt = object;
				
				Project4uVerwaltung.findBeteiligungByOrganisationseinheitAndProjekt(ClientsideSettings.getAktuellerUser(), object, 
						new AsyncCallback<Beteiligung>() {
							@Override
							public void onSuccess(Beteiligung result) {
								
								Project4uVerwaltung.deleteBeteiligung(result, new AsyncCallback<Void>() {
									@Override
									public void onSuccess(Void result) {
									
										MessageBox.alertWidget("Beteiligung gelöscht!", "Beteiligung am Projekt: <b>'"+ clickedProjekt.getName()
												+ "'</b> erfolgreich gelöscht");
										
										new MeineProjekteWidget();
										

									
										
							

										
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
		});
		
		
		
	
		projektTabelleAktuell.addColumn(projektName, "Name");
		projektTabelleAktuell.addColumn(projektleiter, "Projektleiter");
		projektTabelleAktuell.addColumn(startdatum, "Start");
		projektTabelleAktuell.addColumn(enddatum, "Ende");
		projektTabelleAktuell.addColumn(description, "Beschreibung");
		projektTabelleAktuell.addColumn(deleteBeteiligung);
		
		//Anzahl der Zeilen definieren
		projektTabelleAktuell.setRowCount(projekte.size());
		
		//Fuellen der Tabelle ab dem Index 0.
		projektTabelleAktuell.setRowData(0,  projekte);
		
		return projektTabelleAktuell;
	}
	
public CellTable<Projekt> createTableOfOldUserProjekte(Vector<Projekt> projekte) {
		
		TextColumn<Projekt> projektName = new TextColumn<Projekt>() {
			public String getValue(Projekt object) {
				
				return object.getName();
			}
		};
		projektTabelleAlt.addColumn(projektName, "Name");
		
		
		TextColumn<Projekt> projektleiter = new TextColumn<Projekt>() {
			public String getValue(Projekt object) {
				String name = null;
				for (Organisationseinheit org : allProjektleiter){
					 
					if (org.getOrganisationseinheitId()==object.getOrganisationseinheitId()){
						name = org.getName();
						
					}
					
				}
				return name;
			}
		};
		projektTabelleAlt.addColumn(projektleiter, "Projektleiter");
		
		DateCell datecell = new DateCell(); 
		Column<Projekt, Date> startdatum = new Column<Projekt, Date> (datecell){

			@Override
			public Date getValue(Projekt object) {
				return object.getStartdatum();
			}	
		};
		projektTabelleAlt.addColumn(startdatum, "Start");
		
		DateCell datecell2 = new DateCell(); 
		Column<Projekt, Date> enddatum = new Column<Projekt, Date> (datecell2){

			@Override
			public Date getValue(Projekt object) {
				return object.getEnddatum();
			}	
			public String getCellStyleNames (Context context, Projekt object){
				if (object.getEnddatum().before(new Date())){
					return "rot";
				}
				else {return null;}
				
			}
		};
		projektTabelleAlt.addColumn(enddatum, "Ende");
		
		TextColumn<Projekt> description = new TextColumn<Projekt>() {
			public String getValue(Projekt object) {
				return object.getBeschreibung();
			}
		};
		projektTabelleAlt.addColumn(description, "Beschreibung");
		
		ButtonCell beteiligungLoeschenButton = new ButtonCell();
		Column<Projekt, String> deleteBeteiligung = new Column<Projekt, String>(beteiligungLoeschenButton) {
			@Override
			public String getValue(Projekt projekt) {
				// The value to display in the button.
				return "Beteiligung Loeschen";
			}
		};
		
		
		
		deleteBeteiligung.setFieldUpdater(new FieldUpdater<Projekt, String>() {
			
			@Override
			public void update(int index, Projekt object, String value) {
				clickedProjekt = object;
				
				Project4uVerwaltung.findBeteiligungByOrganisationseinheitAndProjekt(ClientsideSettings.getAktuellerUser(), object, 
						new AsyncCallback<Beteiligung>() {
							@Override
							public void onSuccess(Beteiligung result) {
								
								Project4uVerwaltung.deleteBeteiligung(result, new AsyncCallback<Void>() {
									@Override
									public void onSuccess(Void result) {
									
										MessageBox.alertWidget("Beteiligung gelöscht!", "Beteiligung am Projekt: <b>'"+ clickedProjekt.getName()
												+ "'</b> erfolgreich gelöscht");
										
										new MeineProjekteWidget();
										
										

										
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
		});
		projektTabelleAlt.addColumn(deleteBeteiligung);
		
		
		
		//Anzahl der Zeilen definieren
		projektTabelleAlt.setRowCount(projekte.size());
		
		//Fuellen der Tabelle ab dem Index 0.
		projektTabelleAlt.setRowData(0,  projekte);
		
		return projektTabelleAlt;
	}
}