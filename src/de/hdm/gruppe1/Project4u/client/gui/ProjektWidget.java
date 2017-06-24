package de.hdm.gruppe1.Project4u.client.gui;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.shared.LoginInfo;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;
import de.hdm.gruppe1.Project4u.shared.bo.Projektmarktplatz;

public class ProjektWidget extends Composite{
	
	Project4uAdministrationAsync Project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();
	Projektmarktplatz projektmarktplatz = new Projektmarktplatz();
	private Organisationseinheit orgOfLoggedInUser = new Organisationseinheit();
	private Vector<Projekt> projekte = new Vector<Projekt>();
	private Vector<Organisationseinheit> allProjektleiter = new Vector<>();
	
	Button addProjekt = new Button("Projekt anlegen");
	Button addAusschreibung = new Button("Ausschreibung hinzufügen");
	VerticalPanel vPanel = new VerticalPanel();
	VerticalPanel verP = new VerticalPanel();
	HorizontalPanel hPanel = new HorizontalPanel();
	Projekt selectedProjekt = new Projekt();
	
	//TODO: Projekt löschen
	
	


	/*
	 * Der Key-Provider vergibt jedem Objekt der Tabelle eine Id, damit auch einzelne Objekte der
	 * in der Liste weiter verarbeitet werden k�nnen. 
	 */
	public static final ProvidesKey<Projekt> KEY_PROVIDER = new ProvidesKey<Projekt>() {
		public Object getKey(Projekt item) {
			return item == null ? null : item.getProjektId();
		}
	};
	
	public static final ProvidesKey<Ausschreibung> KEY_PROVIDER_AUSSCHREIBUNG = new ProvidesKey<Ausschreibung>() {
		public Object getKey(Ausschreibung item) {
			return item == null ? null : item.getAusschreibungId();
		}
	};
	
	
	
	public ProjektWidget(Projektmarktplatz pMarktpl) {
		getOrganisationseinheitOfUser(ClientsideSettings.getAktuellerUser());
		this.projektmarktplatz = pMarktpl;

		addAusschreibung.addClickHandler(new hinzufuegenAusschreibungClickhandler());
		Project4uVerwaltung.findByProjektmarktplatz(pMarktpl, new getProjekteCallback());
		
		
		initWidget(vPanel);
	}
	
	
	
	
	
	protected void projektChange( final Projekt p, final Projektmarktplatz m){
		
		final DialogBox db = new DialogBox();
		VerticalPanel vp = new VerticalPanel();
		
		
		Label name = new Label("Projektname:");
		vp.add(name);
		final TextBox nam = new TextBox();
		vp.add(nam);
		
		
		Label projektleiter = new Label("Projektleiter:");
		vp.add(projektleiter);
		final TextBox pLeiter = new TextBox();	
		pLeiter.setValue(orgOfLoggedInUser.getName()+" - "+ClientsideSettings.getAktuellerUser().getEmailAddress());
		pLeiter.setEnabled(false);
		pLeiter.setTitle("Wenn Sie nicht der Projektleiter sind, loggen Sie sich mit dem entsprechenden Account ein");
		pLeiter.setWidth("300px");
		vp.add(pLeiter);
		
		
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
				if((nam.getValue().isEmpty())||(beschr.getValue().isEmpty())){
					Window.alert("Bitte alle Felder ausfüllen.");
				}
				else if (endate.getValue().before(stdate.getValue())) {
					Window.alert("Das Enddatum muss nach dem Startdatum liegen.");
				}
				
				else{
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
							
							
							db.hide();
							RootPanel.get("content").clear();
							RootPanel.get("content").add(new ProjektWidget(m));
							RootPanel.get("contentHeader").clear();
							RootPanel.get("contentHeader")
									.add(new Label("Alle Projekte des Projektmarktplatzes "
											+ m.getName()));
							
							
							}
						public void onFailure(Throwable caught) {
							
						}
					});
				}
				//Der else-fall tritt ein, wenn ein neues Projekt erstellt wird.
				else{
					
					
					Project4uVerwaltung.createProjekt(p, m, ClientsideSettings.getAktuellerUser(), new AsyncCallback<Projekt>() {
						
						
						public void onSuccess(Projekt result) {
							
							//Nach dem Speichern des Projektes wird das ProjektWidget erstellt und im contend-div angezeigt.						
							db.hide();
							RootPanel.get("content").clear();
							RootPanel.get("content").add(new ProjektWidget(m));
							RootPanel.get("contentHeader").clear();
							RootPanel.get("contentHeader")
									.add(new Label("Alle Projekte des Projektmarktplatzes "
											+ m.getName()));
							
							}

							
							public void onFailure(Throwable caught) {

							}
						});
					
				}
				
				} }
		});
		vp.add(saveButton);
		db.add(vp);
		db.setAutoHideEnabled(true);
		db.center();
		db.show();
		
	}
	
	
	public void ausschreibungAnsehen(Projekt p){
		this.selectedProjekt=p;
		verP.clear();
		hPanel.clear();
				
		SimplePanel te = new SimplePanel();			
		HTML hr = new HTML("<hr style= 'border: 0; height: 3px; background: #333; margin-top: 50px; margin-bottom: 50px; background-image: linear-gradient(to right, #ccc, #333, #ccc);'>");
		te.add(hr);		
		te.setWidth(vPanel.getOffsetWidth()+"px");
		verP.add(te);
		
		HTML heading = new HTML("<p class='heading'>Ausschreibungen zum Projekt '"+p.getName()+"':</p>");
		verP.add(heading);
		
		
		
		
		
		Project4uVerwaltung.findActiveAusschreibungenOfProjekt(p, new AsyncCallback<Vector<Ausschreibung>>() {

			@Override
			public void onSuccess(Vector<Ausschreibung> result) {
				if (result.isEmpty()) {
					
					HTML noAusschreibungen = new HTML(
							"<p class='heading'>-- keine Ausschreibungen zu diesem Projekt --</p>");
					noAusschreibungen.setHeight("30px");
					verP.add(noAusschreibungen);
					verP.add(addAusschreibung);
					vPanel.add(verP);
					
				} else {
					ausschreibungsTabelle(result);

				}

			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());

			}
		});
	}
	
	
	
	private void getOrganisationseinheitOfUser(LoginInfo login){
		
		Project4uVerwaltung.getOrganisationseinheitByUser(login, new AsyncCallback<Organisationseinheit>() {
			
			public void onSuccess(Organisationseinheit result) {
				setOrg(result);
				
				
				
			}
			public void onFailure(Throwable caught) {
			}
		});
		
	}
	
	private void setOrg(Organisationseinheit o){
		this.orgOfLoggedInUser=o;
	}
	
	
	protected void ausschreibungsTabelle(Vector<Ausschreibung> chosenAusschreibungen){
		CellTable<Ausschreibung> ausschreibungTabelle = new CellTable<Ausschreibung>( KEY_PROVIDER_AUSSCHREIBUNG);
		
		TextColumn<Ausschreibung> nameAusschreibung = new TextColumn<Ausschreibung>() {
			public String getValue(Ausschreibung object) {
				return object.getBezeichnung();
			}
		};
		
		TextColumn<Ausschreibung> projektleiter = new TextColumn<Ausschreibung>() {
			public String getValue(Ausschreibung object) {
				return object.getNameProjektleiter();
			}
		};
		
		DateCell datecell = new DateCell(); 
		Column<Ausschreibung, Date> bewerbungsfrist = new Column<Ausschreibung, Date> (datecell){
			
			@Override
			public Date getValue(Ausschreibung object) {
				return object.getBewerbungsfrist();								
			}	
			public String getCellStyleNames (Context context, Ausschreibung object){
				if (object.getBewerbungsfrist().before(new Date())){
					return "rot";
				}
				else {return null;}
				
			}
		};
		
		ButtonCell buttonCell = new ButtonCell();
		Column<Ausschreibung, String> buttonColumn = new Column<Ausschreibung, String>(buttonCell) {
		  @Override
		  public String getValue(Ausschreibung au) {
		    // The value to display in the button.
		    return "Details";
		  }
		};
		

		//You can then set a FieldUpdater on the Column to be notified of clicks.
		
		buttonColumn.setFieldUpdater(new FieldUpdater <Ausschreibung, String>() {
		  public void update(int index, Ausschreibung object, String value) {
		    // Value is the button value.  Object is the row object.
		    AusschreibungsprofilWidget apw = new AusschreibungsprofilWidget(object, selectedProjekt, projektmarktplatz);
		    apw.showBox();
		  }
		});
		
		ausschreibungTabelle.addColumn(nameAusschreibung, "Bezeichnung");
		ausschreibungTabelle.addColumn(projektleiter, "Projektleiter");
		ausschreibungTabelle.addColumn(bewerbungsfrist, "Bewerbungsfrist");
		ausschreibungTabelle.addColumn(buttonColumn, "");
		
		
		ausschreibungTabelle.setRowCount(chosenAusschreibungen.size());
		
		// Füllen der Tabelle ab dem Index 0.
		ausschreibungTabelle.setRowData(0, chosenAusschreibungen);
			
		
		/*
		 * Der DataListProvider ermöglicht zusammen mit dem SimplePager die Anzeige der 
		 * Daten über mehere Seiten hinweg
		 */
		ListDataProvider<Ausschreibung> dataProvider = new ListDataProvider<Ausschreibung>();
	    dataProvider.addDataDisplay(ausschreibungTabelle);
	    dataProvider.setList(chosenAusschreibungen);
	
		SimplePager pager = new SimplePager(TextLocation.CENTER, false, 0, false);
	    pager.setDisplay(ausschreibungTabelle);
	    pager.setPageSize(10);
	    
		ausschreibungTabelle.setWidth("100%");
		
		hPanel.add(ausschreibungTabelle);
		 
		verP.add(hPanel);
		verP.add(pager);
		verP.add(addAusschreibung);
		vPanel.add(verP);
		
	}
	
	private class hinzufuegenAusschreibungClickhandler implements ClickHandler{

		public void onClick(ClickEvent event) {
			
			Project4uVerwaltung.getOrganisationseinheitById(selectedProjekt.getOrganisationseinheitId(),
					new AsyncCallback<Organisationseinheit>() {

						@Override
						public void onSuccess(Organisationseinheit projektleiter) {
							
							if(projektleiter.getGoogleId().equalsIgnoreCase(ClientsideSettings.getAktuellerUser().getEmailAddress())){
							
							
							AusschreibungsprofilWidget aussch = new AusschreibungsprofilWidget(new Ausschreibung(), selectedProjekt, projektmarktplatz);
							aussch.showBox();
							}
							else
							{
								MessageBox.alertWidget("Ausschreibung anlegen", "Sie sind nicht Projektleiter des Projektes <b>'"+selectedProjekt.getName()
								+"'</b></br> Legen Sie Ihr eigenes Projekt an, oder wenden Sie sich an:</br>" +projektleiter.getGoogleId());
							}
						}

						@Override
						public void onFailure(Throwable caught) {
							

						}
					});
			
			
			
		}
		
	}
	
	
	
	private class getProjekteCallback implements AsyncCallback<Vector<Projekt>>{

		
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
		}

		public void onSuccess(Vector<Projekt> result) {
			projekte=result;
			
			
			Project4uVerwaltung.findProjektleiterOfProjects(result, new AsyncCallback<Vector<Organisationseinheit>>() {
				
				@Override
				public void onSuccess(Vector<Organisationseinheit> allProjektleitr) {
					allProjektleiter=allProjektleitr;
					
					
					
					
					
					//Pr�fung, ob schon Projekte zum Projektmarktplatz existieren
					if (projekte.isEmpty()){
						vPanel.clear();
						Label noProjekt = new Label("Es existiert noch kein Projekt, lege eines an!");
						vPanel.add(noProjekt);
						vPanel.add(addProjekt);
						
						addProjekt.addClickHandler(new ClickHandler() {
							
							@Override
							public void onClick(ClickEvent event) {
								Projekt neu = new Projekt();
								projektChange(neu, projektmarktplatz);
								
							}
						}); 
						initWidget(vPanel);
					}
					else{
						vPanel.clear();
						vPanel.add(addProjekt);
						addProjekt.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								Projekt neu = new Projekt();
								projektChange(neu, projektmarktplatz);

							}
						});
						
						CellTable<Projekt> projektTabelle = new CellTable<Projekt>(KEY_PROVIDER);
						
						//Die Spalte der Projekt-Tabelle wird erstellt und deren Inhalt definiert.
						TextColumn<Projekt> nameColumn = new TextColumn<Projekt>() {
							public String getValue(Projekt object) {
								
								return object.getName();
							}
						};
						
						
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
						
						
						
						
						/*
						 * Das SelectionModel wird zur Tabelle der Projekte hinzugef�gt
						 * und gewährleistet, ähnlich einem ClickHandler, dass beim Klicken auf
						 * eine Tabellenzeile das jeweilige Objekt zur�ckgegeben wird.
						 */
						final SingleSelectionModel<Projekt> selectionModel = new SingleSelectionModel<Projekt>(KEY_PROVIDER);	
						projektTabelle.setSelectionModel(selectionModel);
						selectionModel.addSelectionChangeHandler(new Handler() {
							
							@Override
							public void onSelectionChange(SelectionChangeEvent event) {
								final DialogBox diBox = new DialogBox();
								VerticalPanel vPanel = new VerticalPanel();
								Button seeProjekt = new Button("Ausschreibungen zum Projekt ansehen");
								seeProjekt.addClickHandler(new ClickHandler() {
									
									@Override
									public void onClick(ClickEvent event) {
										diBox.hide();
										ausschreibungAnsehen(selectionModel.getSelectedObject()); 
										
									}
								});
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
						projektTabelle.addColumn(projektleiter, "Projektleiter");
						projektTabelle.addColumn(startdatum, "Startdatum");
						projektTabelle.addColumn(enddatum, "Enddatum");
						projektTabelle.addColumn(description, "Beschreibung");
						
						projektTabelle.setRowCount(projekte.size());
						
						//F�llen der Tabelle ab dem Index 0.
						projektTabelle.setRowData(0,  projekte);
						
						//Anpassen des Widgets an die Breite des div-Elements "content"
						//projektTabelle.setWidth(RootPanel.get("content").getOffsetWidth()+"px");
						
						
						/*
						 * Der DataListProvider ermöglicht zusammen mit dem SimplePager die Anzeige der 
						 * Daten über mehere Seiten hinweg
						 */
						ListDataProvider<Projekt> dataProvider = new ListDataProvider<Projekt>();
					    dataProvider.addDataDisplay(projektTabelle);
					    dataProvider.setList(projekte);
					
						SimplePager pager = new SimplePager(TextLocation.CENTER, false, 0, false);
					    pager.setDisplay(projektTabelle);
					    pager.setPageSize(10);
					    
					    projektTabelle.setWidth("100%");
						
						
						vPanel.add(projektTabelle);
						vPanel.add(pager);
						
				}
					
					
					
					
					
					
					
					
					
					
				}
				
				@Override
				public void onFailure(Throwable caught) {
					
					
				}
			});
		
			
			
			
			
			
		}
	}
	
}
