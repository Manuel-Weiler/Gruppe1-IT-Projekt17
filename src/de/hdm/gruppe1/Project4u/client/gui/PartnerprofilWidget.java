package de.hdm.gruppe1.Project4u.client.gui;

import java.util.Vector;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
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
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ProvidesKey;

import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.client.Project4u;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Eigenschaft;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;

public class PartnerprofilWidget extends Composite{
	
	Project4uAdministrationAsync Project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();
	
	FlexTable flexTable = new FlexTable();
	Label email = new Label("E-Mail:");
	TextBox mail = new TextBox();
	Label orgaName = new Label("Profilname:");
	TextBox orgaNam = new TextBox();
	Label typ = new Label("Kontentyp:");
	ListBox typbox = new ListBox();
	
	Label berufsbezeichnung = new Label("Berufsbezeichnung:");
	TextBox berufsbezeichnungBox = new TextBox();
	Label berufserfahrung = new Label("Arbeitserfahrung in Jahren:");
	TextBox berufserfahrungBox = new TextBox();
	Label abschluss = new Label("Bildungsabschluss:");
	TextBox abschlussBox = new TextBox();
	Label programSprache = new Label("bevorzugte Programmiersprache:");
	TextBox programSpracheBox = new TextBox();
	
		
	Button add = new Button("Eigenschaft hinzufügen");
	Button speichern = new Button("Speichern");
	
	Partnerprofil neuesProfil = new Partnerprofil();
	Organisationseinheit neueOrga = new Organisationseinheit();
	VerticalPanel vPanel = new VerticalPanel();
	VerticalPanel eigenschaftenPanel = new VerticalPanel();
	
	/*
	 * Der Key-Provider vergibt jedem Objekt der Tabelle eine Id, damit auch einzelne Objekte der
	 * in der Liste weiter verarbeitet werden k�nnen. 
	 */
	public static final ProvidesKey<Eigenschaft> KEY_PROVIDER = new ProvidesKey<Eigenschaft>() {
		public Object getKey(Eigenschaft item) {
			return item == null ? null : item.getEigenschaftId();
		}
	};
	
	public PartnerprofilWidget(){
		
		RootPanel.get("contentHeader").clear();
		Label neuProfil = new Label("Neues Profil anlegen");
		RootPanel.get("contentHeader").add(neuProfil);
		
		
		
		Project4uVerwaltung.createPartnerprofil(new AsyncCallback<Partnerprofil>() {
			
			@Override
			public void onSuccess(Partnerprofil result) {
				  neuesProfil = result;	
			}

			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());			
			}
		});
		
		
		vPanel.add(add);
		
		
		mail.setValue(ClientsideSettings.getAktuellerUser().getEmailAddress());
		mail.setEnabled(false);
		
		flexTable.setWidget(0, 0, email);
		flexTable.setWidget(0, 1, mail);
		flexTable.setWidget(1, 0, orgaName);
		flexTable.setWidget(1, 1, orgaNam);
		flexTable.setWidget(2, 0, typ);
		typbox.addItem("Person");
		typbox.setVisibleItemCount(1);
		flexTable.setWidget(2, 1, typbox);
		flexTable.setWidget(3, 0, berufsbezeichnung);
		flexTable.setWidget(3, 1, berufsbezeichnungBox);
		flexTable.setWidget(4, 0, berufserfahrung);
		flexTable.setWidget(4, 1, berufserfahrungBox);
		flexTable.setWidget(5, 0, abschluss);
		flexTable.setWidget(5, 1, abschlussBox);
		flexTable.setWidget(6, 0, programSprache);
		flexTable.setWidget(6, 1, programSpracheBox);
		
		vPanel.add(flexTable);
		vPanel.add(speichern);
		initWidget(vPanel);
		
		
		
		add.addClickHandler(new addEigenschaftClickhandler());
		
		
		speichern.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(mail.getValue().isEmpty()||orgaNam.getValue().isEmpty()){
					Window.alert("Bitte die Felder EMail und Name ausfüllen");
				}
				else{
				speichern.setEnabled(false);	
				Organisationseinheit neuOrga = new Organisationseinheit();
				neuOrga.setGoogleId(mail.getValue());
				neuOrga.setName(orgaNam.getValue());
				neuOrga.setTyp(typbox.getSelectedValue());
				neuOrga.setPartnerprofilId(neuesProfil.getPartnerprofilId());
				Project4uVerwaltung.createOrganisationseinheit(neuOrga, new AsyncCallback<Organisationseinheit>() {
					
					@Override
					public void onSuccess(Organisationseinheit result) {								
						neueOrga = result;
						if (!berufsbezeichnungBox.getValue().isEmpty()){
						Eigenschaft eins = new Eigenschaft();
						eins.setName("Berufsbezeichnung");
						eins.setWert(berufsbezeichnungBox.getValue());
						Project4uVerwaltung.insertEigenschaft(eins, neuesProfil, new AsyncCallback<Eigenschaft>() {
							public void onSuccess(Eigenschaft result) {
								
								
								if (!berufserfahrungBox.getValue().isEmpty()){
									Eigenschaft zwei = new Eigenschaft();
									zwei.setName("Berufserfahrung");
									zwei.setWert(berufserfahrungBox.getValue());
									Project4uVerwaltung.insertEigenschaft(zwei, neuesProfil, new AsyncCallback<Eigenschaft>() {
										public void onSuccess(Eigenschaft result) {
											
											
											if (!abschlussBox.getValue().isEmpty()){
												Eigenschaft drei = new Eigenschaft();
												drei.setName("Abschluss");
												drei.setWert(abschlussBox.getValue());
												Project4uVerwaltung.insertEigenschaft(drei, neuesProfil, new AsyncCallback<Eigenschaft>() {
													public void onSuccess(Eigenschaft result) {
														
														
														if (!programSpracheBox.getValue().isEmpty()){
															Eigenschaft vier = new Eigenschaft();
															vier.setName("Programmiersprache");
															vier.setWert(programSpracheBox.getValue());
															Project4uVerwaltung.insertEigenschaft(vier, neuesProfil, new AsyncCallback<Eigenschaft>() {
																public void onSuccess(Eigenschaft result) {	
																	
																	Project4u.nt.setButtonsEnabled();
																	RootPanel.get("content").clear();
																	RootPanel.get("content").add(new PartnerprofilWidget(neueOrga));
																	
																}
																public void onFailure(Throwable caught) {
																	Window.alert(caught.getMessage());}
															});
															}
														
														
														
													}
													public void onFailure(Throwable caught) {
														Window.alert(caught.getMessage());}
												});
												}	
										}
										public void onFailure(Throwable caught) {
											Window.alert(caught.getMessage());}
									});
									}															
							}
							public void onFailure(Throwable caught) {
								Window.alert(caught.getMessage());}
						});
						}
						
						
				
						
						
						
						
						
						
						
					}
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());						
					}
				});
				}
				
			
				
			
			}
		});
		
		
		
	}
	
	public PartnerprofilWidget(Ausschreibung a){
		
		
	}
	
	public PartnerprofilWidget(Organisationseinheit o){
		//TODO: löschen Organisationeinheit implementieren
		Button deleteOrga = new Button("Nutzerprofil löschen");
		
		RootPanel.get("contentHeader").clear();
		Label Profil = new Label("Ihr Nutzerprofil");
		RootPanel.get("contentHeader").add(Profil);
		Button bearbeiten = new Button("Nutzername bearbeiten");
		final VerticalPanel vp = new VerticalPanel();
		
		mail.setValue(o.getGoogleId());
		mail.setEnabled(false);
		orgaNam.setValue(o.getName());
		orgaNam.setEnabled(false);
		mail.setTitle("Die E-Mail-Adresse kann nicht geändert werden.");
		typbox.setTitle("Der Kontentyp kann nicht geändert werden. Legen Sie ggf. zusäzliche Konten an.");
		int i = (RootPanel.get("content").getOffsetWidth())-550;
		HTML test = new HTML("<p style='margin-left: "+i+"px'><p>");
		flexTable.setWidget(0, 0, orgaName);
		flexTable.setWidget(0, 1, orgaNam);
		flexTable.setWidget(1, 0, email);
		flexTable.setWidget(1, 1, mail);		
		flexTable.setWidget(2, 0, typ);
		typbox.addItem("Person");
		typbox.setVisibleItemCount(1);
		typbox.setEnabled(false);
		flexTable.setWidget(2, 1, typbox);
		flexTable.setWidget(0, 3, bearbeiten);
		flexTable.setWidget(0, 5, deleteOrga);
		flexTable.setWidget(0, 4, test);
		
		vp.add(flexTable);
		
		HTML p = new HTML("<p class='heading'>Eigenschaften:</p>");		
		vp.add(p);
		
		Project4uVerwaltung.getPartnerprofilOfOrganisationseinheit(o, new AsyncCallback<Partnerprofil>() {
			public void onSuccess(Partnerprofil result) {
				neuesProfil= result;
				
			}
			public void onFailure(Throwable caught) {
			}
		});
		
		
		
		
		Project4uVerwaltung.getEigenschaftenOfOrganisationseinheit(o, new AsyncCallback<Vector<Eigenschaft>>() {
		
			@Override
			public void onSuccess(Vector<Eigenschaft> result) {
				eigenschaftenPanel.add(createCellTable(result));
				vp.add(eigenschaftenPanel);
				add.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {

						final DialogBox db = new DialogBox();
						Label nam = new Label ("Bezeichnung:");
						Label wer = new Label ("Wert:");
						Button ok = new Button("OK");
						final TextBox name = new TextBox();
						final TextBox wert = new TextBox();
						FlexTable ft = new FlexTable();
						ft.setWidget(0, 0, nam);
						ft.setWidget(0, 1, wer);
						ft.setWidget(1, 0, name);
						ft.setWidget(1, 1, wert);
						ft.setWidget(2, 2, ok);
						db.add(ft);
						
						db.center();
						db.setAnimationEnabled(true);
						db.setAutoHideEnabled(true);
						db.show();
						
						ok.addClickHandler(new ClickHandler() {
							
							@Override
							public void onClick(ClickEvent event) {
								if (name.getValue().isEmpty() || wert.getValue().isEmpty()){
									Window.alert("Bitte beide Felder ausfüllen");
								}
								else{
								db.hide();
								Eigenschaft eig = new Eigenschaft();
								eig.setName(name.getValue());
								eig.setWert(wert.getValue());
								Project4uVerwaltung.insertEigenschaft(eig, neuesProfil, new AsyncCallback<Eigenschaft>() {
									
									@Override
									public void onSuccess(Eigenschaft result) {
										
										Project4uVerwaltung.getOrganisationseinheitByUser(ClientsideSettings.getAktuellerUser(),
												new AsyncCallback<Organisationseinheit>() {
											public void onSuccess(Organisationseinheit result) {
												RootPanel.get("content").clear();
												RootPanel.get("content").add(new PartnerprofilWidget(result));
												
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
							}
						});
						
					}
						
					
				});
				vp.add(add);
				SimplePanel te = new SimplePanel();			
				HTML hr = new HTML("<hr style= 'border: 0; height: 3px; background: #333; margin-top: 50px; margin-bottom: 15px; background-image: linear-gradient(to right, #ccc, #333, #ccc);'>");
				te.add(hr);		
				te.setWidth(RootPanel.get("content").getOffsetWidth()+"px");
				vp.add(te);
				Project4uVerwaltung.getAllOrganisationseinheitenOfTypTeamUnternehmen(new AsyncCallback<Vector<Organisationseinheit>>() {
					
					@Override
					public void onSuccess(Vector<Organisationseinheit> result) {
						vp.add(new OrganisationseinheitWidget(result));
						
					}
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}
				});
				
			}			
			public void onFailure(Throwable caught) {				
			}
		});
		
		
		
		
		initWidget(vp);
	}
	
	
	private CellTable<Eigenschaft> createCellTable(Vector<Eigenschaft> eigenschaften) {
		
		CellTable<Eigenschaft> profilTabelle = new CellTable<Eigenschaft>(KEY_PROVIDER);

		// Die Spalte der Eigenschaften-Tabelle wird erstellt und deren Inhalt
		// definiert.
		Column<Eigenschaft, String> nameColumn = new Column<Eigenschaft, String>( new EditTextCell()) {
			public String getValue(Eigenschaft object) {
				return object.getName();
			}
		};

		Column<Eigenschaft, String> valueColumn = new Column<Eigenschaft, String>(new EditTextCell()) {
			public String getValue(Eigenschaft object) {
				return object.getWert();

			}
		};
		
		ButtonCell buttonCell = new ButtonCell();
		Column<Eigenschaft, String> buttonColumn = new Column<Eigenschaft, String>(buttonCell) {
		  @Override
		  public String getValue(Eigenschaft au) {
		    // The value to display in the button.
		    return "Eigenschaft "+ au.getName()+ " löschen";
		  }
		};
		


		
		buttonColumn.setFieldUpdater(new FieldUpdater <Eigenschaft, String>() {
		  public void update(int index, Eigenschaft object, String value) {
		    // Value is the button value.  Object is the row object.
			  
			  Project4uVerwaltung.deleteEigenschaft(object, new AsyncCallback<Void>() {
				
				@Override
				public void onSuccess(Void result) {
					
					Project4uVerwaltung.getOrganisationseinheitByUser(ClientsideSettings.getAktuellerUser(),
							new AsyncCallback<Organisationseinheit>() {
						public void onSuccess(Organisationseinheit result) {
							RootPanel.get("content").clear();
							RootPanel.get("content").add(new PartnerprofilWidget(result));
							
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
		  }
		});

		
		profilTabelle.addColumn(nameColumn);
		profilTabelle.addColumn(valueColumn);
		profilTabelle.addColumn(buttonColumn);

		valueColumn.setFieldUpdater(new FieldUpdater<Eigenschaft, String>() {

			@Override
			public void update(int index, Eigenschaft object, String value) {
				if(value.isEmpty()){
					MessageBox.alertWidget("Leeres Feld!", "Das Feld darf nicht leer sein");
				}
				else{
				object.setWert(value);
				Project4uVerwaltung.updateEigenschaft(object, new AsyncCallback<Eigenschaft>() {

					public void onSuccess(Eigenschaft result) {

					}

					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}
				});}

			}
		});

		nameColumn.setFieldUpdater(new FieldUpdater<Eigenschaft, String>() {

			@Override
			public void update(int index, Eigenschaft object, String value) {
				if(value.isEmpty()){
					MessageBox.alertWidget("Leeres Feld!", "Das Feld darf nicht leer sein");
				}
				else{
				object.setName(value);
				;
				Project4uVerwaltung.updateEigenschaft(object, new AsyncCallback<Eigenschaft>() {

					public void onSuccess(Eigenschaft result) {

					}

					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}
				});}

			}
		});
		
		profilTabelle.setRowData(0, eigenschaften);
		
		return profilTabelle;
	}
	
	
	
	
	
	
	private class addEigenschaftClickhandler implements ClickHandler{
			
		public void onClick(ClickEvent event) {
				final DialogBox db = new DialogBox();
				Label nam = new Label ("Bezeichnung:");
				Label wer = new Label ("Wert:");
				Button ok = new Button("OK");
				final TextBox name = new TextBox();
				final TextBox wert = new TextBox();
				FlexTable ft = new FlexTable();
				ft.setWidget(0, 0, nam);
				ft.setWidget(0, 1, wer);
				ft.setWidget(1, 0, name);
				ft.setWidget(1, 1, wert);
				ft.setWidget(2, 2, ok);
				db.add(ft);
				
				db.center();
				db.setAnimationEnabled(true);
				db.setAutoHideEnabled(true);
				db.show();
				
				ok.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						if (name.getValue().isEmpty() || wert.getValue().isEmpty()){
							Window.alert("Bitte beide Felder ausfüllen");
						}
						else{
						db.hide();
						Eigenschaft eig = new Eigenschaft();
						eig.setName(name.getValue());
						eig.setWert(wert.getValue());
						Project4uVerwaltung.insertEigenschaft(eig, neuesProfil, new AsyncCallback<Eigenschaft>() {
							
							@Override
							public void onSuccess(Eigenschaft result) {
								Label name = new Label(result.getName());
								Label wert = new Label(result.getWert());
								
								flexTable.setWidget(flexTable.getRowCount(), 0, name);
								flexTable.setWidget(flexTable.getRowCount()-1, 1, wert);
								
								
							}
							
							
							public void onFailure(Throwable caught) {
								Window.alert(caught.getMessage());		
							}
						});
						
						}
					}
				});
				
			}}
		;
		
		
		
	
	
	
	
	
	
	
	
	
	
}
