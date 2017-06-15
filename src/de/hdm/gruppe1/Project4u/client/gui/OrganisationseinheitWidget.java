package de.hdm.gruppe1.Project4u.client.gui;

import java.util.Vector;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;

import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Eigenschaft;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;

public class OrganisationseinheitWidget extends Composite{

	Project4uAdministrationAsync Project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();
	
	public static final ProvidesKey<Organisationseinheit> KEY_PROVIDER = new ProvidesKey<Organisationseinheit>() {
		public Object getKey(Organisationseinheit item) {
			return item == null ? null : item.getOrganisationseinheitId();
		}
	};
	
	Button addOrga = new Button("Organisationseinheit hinzufügen");
	VerticalPanel vPanel = new VerticalPanel();
	HTML heading = new HTML("<h2 class='h2heading'>Teams und Unternehmen:</h2>");
	Vector<Organisationseinheit> linked = new Vector<Organisationseinheit>();
	
	 
	public OrganisationseinheitWidget(Vector<Organisationseinheit> orgas){
		
		addOrga.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				newOrgaProfil();
				
			}
		});
		
		if (orgas.isEmpty()){
			vPanel.clear();
			vPanel.add(heading);
			HTML noOrgas = new HTML("<p class='heading'>Es existieren noch keine Unternehmen und Teams, lege welche an!</p>");
			noOrgas.setHeight("40px");
			vPanel.add(noOrgas);
			vPanel.add(addOrga);
			initWidget(vPanel);
		}
		else{
		
			
		vPanel.clear();
		vPanel.add(heading);
		getLinkedOrgas(orgas);
		
		
		
		
	    initWidget(vPanel);
	    
	    
	}
	
	
	
	
	
	}
	
	
	
	/**
	 * Die Methode erzeugt eine CellTable mit allen Organisationseinheiten vom
	 * Typ "Team" und "Unternehmen" und fügt diese zum VerticalPanel des
	 * OrganisationseinheitWidgets hinzu.
	 * 
	 * @param orgas
	 */
	private void drawTable(Vector<Organisationseinheit> orgas){
		CellTable<Organisationseinheit> orgaTabelle = new CellTable<Organisationseinheit>(KEY_PROVIDER);
		
		//Die Spalte der Organisationseinheiten-Tabelle wird erstellt und deren Inhalt definiert.
		TextColumn<Organisationseinheit> nameColumn = new TextColumn<Organisationseinheit>() {
			public String getValue(Organisationseinheit object) {
				return object.getName();
			}
		};
		
		TextColumn<Organisationseinheit> typeColumn = new TextColumn<Organisationseinheit>() {
			public String getValue(Organisationseinheit object) {
				return object.getTyp();
			}
		};
		
		TextColumn<Organisationseinheit> status = new TextColumn<Organisationseinheit>() {
			
			public String getValue(Organisationseinheit object) {
				String test = "Keine Zugehörigkeit definiert";
				for (Organisationseinheit org : linked){
					if(org.getOrganisationseinheitId()==object.getOrganisationseinheitId()){
						 test=  "Zugehörigkeit definiert";
					}
					else{  
					}
				}
				return test;
			}
			
			public String getCellStyleNames (Context context, Organisationseinheit object){
				if (islinked(object)){
					return "linked";
				}
				else {return "not-linked";}
			}
		};
		
		ButtonCell buttonCell = new ButtonCell();
	
		Column<Organisationseinheit, String> buttonColumn = new Column<Organisationseinheit, String>(buttonCell) {
		  @Override
		  
		 
		  
		  public String getValue(Organisationseinheit au) {
		    // The value to display in the button.
			  String buttonvalue;
				
					if(islinked(au)){
						buttonvalue=  "Zugehörigkeit zur Organisationseinheit beenden";
					}
					else{  
						buttonvalue= "Zugehörigkeit zur Organisationseinheit definieren";
						
					}
			
				
				return buttonvalue;
		 
		  }
		  
		
		  
		};
		


		
		buttonColumn.setFieldUpdater(new FieldUpdater <Organisationseinheit, String>() {
		  public void update(int index, Organisationseinheit object, String value) {
		    // Value is the button value.  Object is the row object.
			  
			  
					if(islinked(object)){
						deleteZugehoerigkeit(object);
					}
					else{  
						 setZugehoerigkeit(object);
					}
			
				
			  
			 
			  
		  }
		});
		
		ButtonCell detailsCell = new ButtonCell();
		Column<Organisationseinheit, String> detailsColumn = new Column<Organisationseinheit, String>(detailsCell) {
		  @Override
		  
		  public String getValue(Organisationseinheit au) {
		    // The value to display in the button.
		    return "Details";
		  }
		};
		


		
		detailsColumn.setFieldUpdater(new FieldUpdater <Organisationseinheit, String>() {
		  public void update(int index, final Organisationseinheit object, String value) {
		    // Value is the button value.  Object is the row object.
			  final DialogBox diBox = new DialogBox();
				VerticalPanel vPanel = new VerticalPanel();
				Button seeOrga = new Button("Profil zur Organisationseinheit ansehen");
				seeOrga.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						diBox.hide();
						orgaProfil(object); 
						
						
						
					}
				});
				
				Button deleteOrga = new Button("Organisationseinheit löschen");
				deleteOrga.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						diBox.hide();
						deleteTeamUnternehmen(object);

					}
				});
				
				
				vPanel.add(seeOrga);
				vPanel.add(deleteOrga);				
				diBox.add(vPanel);
				seeOrga.setPixelSize(270, 30);
				deleteOrga.setPixelSize(270, 30);				
				diBox.setAnimationEnabled(true);
				diBox.setAutoHideEnabled(true);
				diBox.center();
				diBox.show();
		  }
		});
		  
		
		
		
		
		
		
		orgaTabelle.addColumn(nameColumn, "Name");
		orgaTabelle.addColumn(typeColumn, "Typ");
		orgaTabelle.addColumn(status, "Status");
		orgaTabelle.addColumn(buttonColumn);
		orgaTabelle.addColumn(detailsColumn);
		
		orgaTabelle.setRowCount(orgas.size());
		
		// Füllen der Tabelle ab dem Index 0.
		orgaTabelle.setRowData(0, orgas);
			
		
		/*
		 * Der DataListProvider ermöglicht zusammen mit dem SimplePager die Anzeige der 
		 * Daten über mehere Seiten hinweg
		 */
		ListDataProvider<Organisationseinheit> dataProvider = new ListDataProvider<Organisationseinheit>();
	    dataProvider.addDataDisplay(orgaTabelle);
	    dataProvider.setList(orgas);
	    //TODO: Pager funktioniert noch nicht, ggf. reihenfolge beachten
		SimplePager pager = new SimplePager(TextLocation.CENTER, false, 0, false);
	    pager.setDisplay(orgaTabelle);
	    pager.setPageSize(6);
	    
	    orgaTabelle.setWidth("100%");
	    
	    
	    vPanel.add(orgaTabelle);
	    vPanel.add(addOrga);
	}
	
	
	
	/**
	 * Die Methode löscht eine Organisationseinheit vom Typ "Team"/"Unternehmen"
	 * @param orga
	 */
	private void deleteTeamUnternehmen(Organisationseinheit orga){
		if(!ClientsideSettings.getAktuellerUser().getEmailAddress().equalsIgnoreCase(orga.getGoogleId())){
			MessageBox.alertWidget("Löschen "+orga.getName(), "Sie sind nicht der Ersteller der Organisationseinheit, wenden Sie sich an: "+orga.getGoogleId());
		}
		else{
			//TODO: delete Organisationseinheit
		}
	}
	
	
	/**
	 * Die Methode überprüft, ob ein Element der Liste der Teams und Unternehmen auch in der Liste der 
	 * Organisationseinheiten ist, die mit dem Profil des Nutzers verlinkt sind
	 * @param obj
	 * @return
	 * @author Tobias
	 */
	private boolean islinked(Organisationseinheit obj){
		boolean result = false;
		for (Organisationseinheit org : linked){
			if(org.getOrganisationseinheitId()==obj.getOrganisationseinheitId()){
				 result=  true;
			}
			else{  
			}
	
		}
		return result;
	}
	
	/**
	 * Die Methode stellt das Partnerprofil einer Organisationseinheit mit allen
	 * anhängigen Eigenschaftsobjekten in einer Dialogbox dar.
	 * @param o
	 * @author Tobias
	 */
	private void orgaProfil(final Organisationseinheit o){
		 
		final DialogBox db = new DialogBox();
		db.setGlassEnabled(true);
		final VerticalPanel vp = new VerticalPanel();
		HTML Profil = new HTML("<p class='heading'>Profil: "+o.getName()+"</p>");
		FlexTable flexTable = new FlexTable();
		final FlexTable flexTableEigenschaften = new FlexTable();
		final Button speichern = new Button("Speichern");
		speichern.setWidth("100px"); 
		final Button bearbeiten = new Button("Bearbeiten");
		bearbeiten.setWidth("100px");
		final Button addEigenschaft = new Button("Eigenschaft hinzufügen");
		
		Label email = new Label("E-Mail:");
		final TextBox mail = new TextBox();
		Label orgaName = new Label("Profilname:");
		final TextBox orgaNam = new TextBox();
		Label typ = new Label("Kontentyp:");
		final ListBox typbox = new ListBox();
		
		if(o.getGoogleId().equalsIgnoreCase(ClientsideSettings.getAktuellerUser().getEmailAddress())){
			bearbeiten.setVisible(true);
		}
		
		
		
		mail.setValue(o.getGoogleId());	
		orgaNam.setValue(o.getName());
		typbox.addItem(o.getTyp());
		if(o.getTyp().equalsIgnoreCase("Team")){
			typbox.addItem("Unternehmen");
		}
		else{
			typbox.addItem("Team");
		}
		typbox.setVisibleItemCount(1);
		mail.setTitle("Die E-Mail-Adresse kann nicht geändert werden.");
		
		flexTable.setWidget(0, 0, orgaName);
		flexTable.setWidget(0, 1, orgaNam);
		flexTable.setWidget(1, 0, email);
		flexTable.setWidget(1, 1, mail);		
		flexTable.setWidget(2, 0, typ);			
		flexTable.setWidget(2, 1, typbox);
		
		vp.add(Profil);
		vp.add(flexTable);
		vp.add(addEigenschaft);
		vp.add(bearbeiten);
		vp.add(speichern);
		
		
		mail.setEnabled(false);
		orgaNam.setEnabled(false);
		typbox.setEnabled(false);
		
		bearbeiten.setVisible(false);
		speichern.setVisible(false);
		addEigenschaft.setVisible(false);
		
		Project4uVerwaltung.getEigenschaftenOfOrganisationseinheit(o, new AsyncCallback<Vector<Eigenschaft>>() {
			
			@Override
			public void onSuccess(Vector<Eigenschaft> result) {
				if(!result.isEmpty()){
				for(Eigenschaft e : result){
					TextBox tb = new TextBox();
					tb.setValue(e.getName());
					tb.setEnabled(false);
					flexTableEigenschaften.setWidget(flexTableEigenschaften.getRowCount(), 0,  tb);
					TextBox tbox = new TextBox();
					tbox.setValue(e.getWert());
					tbox.setEnabled(false);
					flexTableEigenschaften.setWidget(flexTableEigenschaften.getRowCount()-1, 1,  tbox);
					
				}
				vp.add(flexTableEigenschaften);
				
				
				}
				
			}
			public void onFailure(Throwable caught) {}
		});
		
		
		
	//TODO: 
		addEigenschaft.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {

				final DialogBox db = new DialogBox();
				Label nam = new Label ("Bezeichnung:");
				Label wer = new Label ("Wert:");
				Button cancel = new Button("Abbrechen");
				Button ok = new Button("OK");
				final TextBox name = new TextBox();
				final TextBox wert = new TextBox();
				FlexTable ft = new FlexTable();
				ft.setWidget(0, 0, nam);
				ft.setWidget(0, 1, wer);
				ft.setWidget(1, 0, name);
				ft.setWidget(1, 1, wert);
				ft.setWidget(2, 1, ok);
				ft.setWidget(2, 0, cancel);
				db.add(ft);
				
				db.center();
				db.setAnimationEnabled(true);
				db.setAutoHideEnabled(true);
				db.show();
				
				cancel.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						db.hide();
						
					}
				});
				
				ok.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						if (name.getValue().isEmpty() || wert.getValue().isEmpty()){
							Window.alert("Bitte beide Felder ausfüllen");
						}
						else{
						db.hide();
						
						flexTableEigenschaften.setWidget(flexTableEigenschaften.getRowCount(), 0, name);
						flexTableEigenschaften.setWidget(flexTableEigenschaften.getRowCount()-1, 1, wert);
						
						
						}
					}
				});
				
			;
				
			}
		});
		
		
		
		speichern.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(!orgaNam.getValue().isEmpty()){
				o.setName(orgaNam.getValue());}
				o.setTyp(typbox.getSelectedValue());
				
				
				
				Vector<Eigenschaft> neueEigenschaften = new Vector<Eigenschaft>();
				for(int i=0; i<flexTableEigenschaften.getRowCount(); i++){
					Eigenschaft e = new Eigenschaft();
					Widget w = flexTableEigenschaften.getWidget(i, 0);
					 if (w instanceof TextBox) {
						 if(!((TextBox) w).getValue().isEmpty()){
							e.setName(((TextBox) w).getValue());
						 };
					 }
					 
					 Widget v = flexTableEigenschaften.getWidget(i, 1);
					 if (v instanceof TextBox) {
						 if(!((TextBox) v).getValue().isEmpty()){
								e.setWert(((TextBox) v).getValue());
							 };
					 }
					 if(e.getName()!=null && e.getWert()!=null){
						 neueEigenschaften.add(e);
						 }
				}
							
				final Vector<Eigenschaft> eigens = neueEigenschaften;
				
				Project4uVerwaltung.updateOrganisationseinheit(o, new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						Project4uVerwaltung.deleteAllEigenschaftenOfOrganisationseinheit(o, new AsyncCallback<Void>() {
							
							@Override
							public void onSuccess(Void result) {
								
								
								Project4uVerwaltung.insertEigenschaften(eigens, o, new AsyncCallback<Void>() {
									public void onSuccess(Void result) {
										
										Project4uVerwaltung.getAllOrganisationseinheitenOfTypTeamUnternehmen(new AsyncCallback<Vector<Organisationseinheit>>() {
											
											
											public void onSuccess(Vector<Organisationseinheit> result) {
												vPanel.clear();
												vPanel.add(new OrganisationseinheitWidget(result));
												
											}
											public void onFailure(Throwable caught) {
												Window.alert(caught.getMessage());
											}
										});
									}
									public void onFailure(Throwable caught) {
										Window.alert(caught.getMessage());
									}
								});
							}
							public void onFailure(Throwable caught) {
								Window.alert(caught.getMessage());
							}
						});
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
						
					}
				});
				
				
				db.hide();
			}
		});
		
		
		
		
		bearbeiten.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				for(int i=0; i<flexTableEigenschaften.getRowCount(); i++){
					Widget w = flexTableEigenschaften.getWidget(i, 0);
					 if (w instanceof TextBox) {
						((TextBox) w).setEnabled(true);
					 }
					 
					 Widget v = flexTableEigenschaften.getWidget(i, 1);
					 if (v instanceof TextBox) {
						 ((TextBox) v).setEnabled(true);
					 }
				}
				orgaNam.setEnabled(true);
				typbox.setEnabled(true);
				speichern.setVisible(true);
				addEigenschaft.setVisible(true);
				
			}
		});
		
		db.add(vp);
		db.setAnimationEnabled(true);
		db.setAutoHideEnabled(true);
		db.center();
		db.show();
		
	}
	
	
	
	
	
	
	private void newOrgaProfil() {
		Project4uVerwaltung.createPartnerprofil(new AsyncCallback<Partnerprofil>() {

			@Override
			public void onSuccess(Partnerprofil result) {
				final Partnerprofil neuesProfil = result;
				final Organisationseinheit o = new Organisationseinheit();
				
				final DialogBox db = new DialogBox();
				db.setGlassEnabled(true);
				final VerticalPanel vp = new VerticalPanel();
				HorizontalPanel hp = new HorizontalPanel();
				HTML Profil = new HTML("<p class='heading'>neues Profil: </p>");
				FlexTable flexTable = new FlexTable();
				final FlexTable flexTableEigenschaften = new FlexTable();
				final Button speichern = new Button("Speichern");
				speichern.setWidth("100px");
				final Button abbrechen = new Button("Abbrechen");
				abbrechen.setWidth("100px");
				final Button addEigenschaft = new Button("Eigenschaft hinzufügen");
				
				
				Label email = new Label("E-Mail:");
				final TextBox mail = new TextBox();
				Label orgaName = new Label("Profilname:");
				final TextBox orgaNam = new TextBox();
				Label typ = new Label("Kontentyp:");
				final ListBox typbox = new ListBox();
				
				vp.add(Profil);

				mail.setValue(ClientsideSettings.getAktuellerUser().getEmailAddress());
				typbox.addItem("Unternehmen");
				typbox.addItem("Team");
				typbox.setVisibleItemCount(1);

				flexTable.setWidget(0, 0, orgaName);
				flexTable.setWidget(0, 1, orgaNam);
				flexTable.setWidget(1, 0, email);
				flexTable.setWidget(1, 1, mail);		
				flexTable.setWidget(2, 0, typ);			
				flexTable.setWidget(2, 1, typbox);
				
				
				vp.add(flexTable);
				vp.add(flexTableEigenschaften);
				vp.add(addEigenschaft);
				hp.add(abbrechen);
				hp.add(speichern);
				vp.add(hp);
				
				mail.setEnabled(false);
				o.setPartnerprofilId(result.getPartnerprofilId());
				
				abbrechen.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						Project4uVerwaltung.deletePartnerprofil(neuesProfil, new AsyncCallback<Void>() {
							public void onSuccess(Void result) {
								db.hide();
							}
							public void onFailure(Throwable caught) {
							}
						});
					}
				});
			
				
				speichern.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						
						
						if(orgaNam.getValue().isEmpty()){
						Window.alert("Der Profilname muss aufgefüllt sein");
						}
						else{
						o.setName(orgaNam.getValue());
						o.setTyp(typbox.getSelectedValue());
						o.setGoogleId(mail.getValue());
						
						
						
						Vector<Eigenschaft> neueEigenschaften = new Vector<Eigenschaft>();
						for(int i=0; i<flexTableEigenschaften.getRowCount(); i++){
							Eigenschaft e = new Eigenschaft();
							Widget w = flexTableEigenschaften.getWidget(i, 0);
							 if (w instanceof TextBox) {
								 if(!((TextBox) w).getValue().isEmpty()){
									e.setName(((TextBox) w).getValue());
								 };
							 }
							 
							 Widget v = flexTableEigenschaften.getWidget(i, 1);
							 if (v instanceof TextBox) {
								 if(!((TextBox) v).getValue().isEmpty()){
										e.setWert(((TextBox) v).getValue());
									 };
							 }
							 if(e.getName()!=null && e.getWert()!=null){
							 neueEigenschaften.add(e);
							 }
						}
									
						final Vector<Eigenschaft> eigens = neueEigenschaften;
						
						
						Project4uVerwaltung.createOrganisationseinheit(o, new AsyncCallback<Organisationseinheit>() {
							
							@Override
							public void onSuccess(Organisationseinheit result) {
								
								Project4uVerwaltung.insertEigenschaften(eigens, o, new AsyncCallback<Void>() {
								public void onSuccess(Void result) {
									
									Project4uVerwaltung.getAllOrganisationseinheitenOfTypTeamUnternehmen(new AsyncCallback<Vector<Organisationseinheit>>() {
										
										
										public void onSuccess(Vector<Organisationseinheit> result) {
											vPanel.clear();
											vPanel.add(new OrganisationseinheitWidget(result));
											
										}
										public void onFailure(Throwable caught) {
											Window.alert(caught.getMessage());
										}
									});
								}
								public void onFailure(Throwable caught) {
									Window.alert(caught.getMessage());
								}
							});
							}
							
							@Override
							public void onFailure(Throwable caught) {
							}
						});				
										
										
								
						
						
						db.hide();
						}
					}
				});
				
				
				
			
				
				addEigenschaft.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {

						final DialogBox db = new DialogBox();
						Label nam = new Label ("Bezeichnung:");
						Label wer = new Label ("Wert:");
						Button cancel = new Button("Abbrechen");
						Button ok = new Button("OK");
						final TextBox name = new TextBox();
						final TextBox wert = new TextBox();
						FlexTable ft = new FlexTable();
						ft.setWidget(0, 0, nam);
						ft.setWidget(0, 1, wer);
						ft.setWidget(1, 0, name);
						ft.setWidget(1, 1, wert);
						ft.setWidget(2, 1, ok);
						ft.setWidget(2, 0, cancel);
						db.add(ft);
						
						db.center();
						db.setAnimationEnabled(true);
						db.setAutoHideEnabled(true);
						db.show();
						
						cancel.addClickHandler(new ClickHandler() {
							
							@Override
							public void onClick(ClickEvent event) {
								db.hide();
								
							}
						});
						
						ok.addClickHandler(new ClickHandler() {
							
							@Override
							public void onClick(ClickEvent event) {
								if (name.getValue().isEmpty() || wert.getValue().isEmpty()){
									Window.alert("Bitte beide Felder ausfüllen");
								}
								else{
								db.hide();
								
								flexTableEigenschaften.setWidget(flexTableEigenschaften.getRowCount(), 0, name);
								flexTableEigenschaften.setWidget(flexTableEigenschaften.getRowCount()-1, 1, wert);
								
								
								}
							}
						});
						
					;
						
					}
				});
				
			
					
				
				
				
				db.add(vp);
				db.setAnimationEnabled(true);
				db.setAutoHideEnabled(false);
				db.center();
				db.show();
				
				
				
				
				
			}

			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	 
	/**
	 * Die Methode gibt einen Vector mit allen Organisationseinheiten vom Typ "Team" und "Unternehmen" zurück,
	 * zu denen der Nutzer mit seinem Organisationseinheiten-Objekt eine Zugehörigkeit definiert hat.
	 * @param orgs
	 * @author Tobias
	 */
	private void getLinkedOrgas (final Vector<Organisationseinheit> orgs){
		
		Project4uVerwaltung.getLinkedTeamAndUnternehmenOfOrganisationseinheit(ClientsideSettings.getAktuellerUser(), new AsyncCallback<Vector<Organisationseinheit>>() {
			
			@Override
			public void onSuccess(Vector<Organisationseinheit> result) {
				
				setLinkedOrgas(result);
				
				drawTable(orgs);
			}
			public void onFailure(Throwable caught) {	
			}
			
		});
		
	}
	
	private void setLinkedOrgas(Vector<Organisationseinheit> orgas){
		this.linked = orgas;
		
	}
	
	
	/**
	 * Die Methode setzt eine Zugehörigkeit zwischen der Organisationseinheit
	 * des Nutzers vom Typ "Person" und der Organisationseinheit die der Methode
	 * als Parameter mitgegeben wird.
	 * 
	 * @param orga
	 */
	private void setZugehoerigkeit(Organisationseinheit orga) {

		Project4uVerwaltung.insertLinkedTeamUnternehmenOfOrganisationseinheit(ClientsideSettings.getAktuellerUser(),
				orga, new AsyncCallback<Void>() {

					@Override
					public void onSuccess(Void result) {
						
						
						Project4uVerwaltung.getAllOrganisationseinheitenOfTypTeamUnternehmen(new AsyncCallback<Vector<Organisationseinheit>>() {
							
							@Override
							public void onSuccess(Vector<Organisationseinheit> result) {
								vPanel.clear();
								vPanel.add(new OrganisationseinheitWidget(result));
								
							}
							public void onFailure(Throwable caught) {
								Window.alert(caught.getMessage());
							}
						});
						
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}
				});
	}
	
	
	/**
	 * Die Methode löscht eine bestehende Zugehörigkeit zwischen der Organisationseinheit
	 * des Nutzers vom Typ "Person" und der Organisationseinheit die der Methode
	 * als Parameter mitgegeben wird.
	 * @param orga
	 */
	private void deleteZugehoerigkeit(Organisationseinheit orga) {

		Project4uVerwaltung.deleteLinkedTeamUnternehmenOfOrganisationseinheit(ClientsideSettings.getAktuellerUser(),
				orga, new AsyncCallback<Void>() {

					@Override
					public void onSuccess(Void result) {
						
						
						Project4uVerwaltung.getAllOrganisationseinheitenOfTypTeamUnternehmen(new AsyncCallback<Vector<Organisationseinheit>>() {
							
							@Override
							public void onSuccess(Vector<Organisationseinheit> result) {
								vPanel.clear();
								vPanel.add(new OrganisationseinheitWidget(result));
								
							}
							public void onFailure(Throwable caught) {
								Window.alert(caught.getMessage());
							}
						});
						
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}
				});
	}
	
	
}
