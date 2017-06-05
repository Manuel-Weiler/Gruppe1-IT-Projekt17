package de.hdm.gruppe1.Project4u.client.gui;

import java.util.Vector;

import com.google.gwt.cell.client.ButtonCell;
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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
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
	Label email = new Label("EMail:");
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
		
		RootPanel.get("content-header").clear();
		Label neuProfil = new Label("Neues Profil anlegen");
		RootPanel.get("content-header").add(neuProfil);
		
		//
		
		Project4uVerwaltung.createPartnerprofil(new AsyncCallback<Partnerprofil>() {
			
			@Override
			public void onSuccess(Partnerprofil result) {
				  neuesProfil = result;	
			}

			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());			
			}
		});
		
		VerticalPanel vPanel = new VerticalPanel();
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
		
		
		
		
		add.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				DialogBox db = new DialogBox();
				VerticalPanel vp = new VerticalPanel();
				HorizontalPanel hp = new HorizontalPanel();
				Button ok = new Button("OK");
				final TextBox name = new TextBox();
				final TextBox wert = new TextBox();
				hp.add(name);
				hp.add(wert);
				vp.add(hp);
				vp.add(ok);
				db.add(vp);
				
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
						Eigenschaft eig = new Eigenschaft();
						eig.setName(name.getValue());
						eig.setWert(wert.getValue());
						Project4uVerwaltung.insertEigenschaft(eig, neuesProfil, new AsyncCallback<Eigenschaft>() {
							
							@Override
							public void onSuccess(Eigenschaft result) {
								Label name = new Label(result.getName());
								Label wert = new Label(result.getWert());
								
								flexTable.setWidget(flexTable.getRowCount(), 0, name);
								flexTable.setWidget(flexTable.getRowCount(), 1, wert);
								
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
		
		
		speichern.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(mail.getValue().isEmpty()||orgaNam.getValue().isEmpty()){
					Window.alert("Bitte die Felder EMail und Name ausfüllen");
				}
				else{
				Organisationseinheit neuOrga = new Organisationseinheit();
				neuOrga.setGoogleId(mail.getValue());
				neuOrga.setName(orgaNam.getValue());
				neuOrga.setTyp(typbox.getSelectedValue());
				neuOrga.setPartnerprofilId(neuesProfil.getPartnerprofilId());
				Project4uVerwaltung.createOrganisationseinheit(neuOrga, new AsyncCallback<Organisationseinheit>() {
					
					@Override
					public void onSuccess(Organisationseinheit result) {								
						
						if (!berufsbezeichnungBox.getValue().isEmpty()){
						Eigenschaft eins = new Eigenschaft();
						eins.setName("Berufsbezeichnung");
						eins.setWert(berufsbezeichnungBox.getValue());
						Project4uVerwaltung.insertEigenschaft(eins, neuesProfil, new AsyncCallback<Eigenschaft>() {
							public void onSuccess(Eigenschaft result) {	}
							public void onFailure(Throwable caught) {
								Window.alert(caught.getMessage());}
						});
						}
						
						
						if (!berufserfahrungBox.getValue().isEmpty()){
							Eigenschaft zwei = new Eigenschaft();
							zwei.setName("Berufserfahrung");
							zwei.setWert(berufserfahrungBox.getValue());
							Project4uVerwaltung.insertEigenschaft(zwei, neuesProfil, new AsyncCallback<Eigenschaft>() {
								public void onSuccess(Eigenschaft result) {	}
								public void onFailure(Throwable caught) {
									Window.alert(caught.getMessage());}
							});
							}
						
						if (!abschlussBox.getValue().isEmpty()){
							Eigenschaft drei = new Eigenschaft();
							drei.setName("Abschluss");
							drei.setWert(abschlussBox.getValue());
							Project4uVerwaltung.insertEigenschaft(drei, neuesProfil, new AsyncCallback<Eigenschaft>() {
								public void onSuccess(Eigenschaft result) {	}
								public void onFailure(Throwable caught) {
									Window.alert(caught.getMessage());}
							});
							}
						
						
						if (!programSpracheBox.getValue().isEmpty()){
							Eigenschaft vier = new Eigenschaft();
							vier.setName("Programmiersprache");
							vier.setWert(programSpracheBox.getValue());
							Project4uVerwaltung.insertEigenschaft(vier, neuesProfil, new AsyncCallback<Eigenschaft>() {
								public void onSuccess(Eigenschaft result) {	}
								public void onFailure(Throwable caught) {
									Window.alert(caught.getMessage());}
							});
							}
						
						Project4u.nt.setButtonsEnabled();
						
						
						
						
						
						
					}
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());						
					}
				});
				}
				
			
				
			
			}
		});
		
		initWidget(vPanel);
		
	}
	
	public PartnerprofilWidget(Ausschreibung a){
		
		
	}
	
	public PartnerprofilWidget(Organisationseinheit o){
		RootPanel.get("content-header").clear();
		Label Profil = new Label("Ihr Nutzerprofil");
		RootPanel.get("content-header").add(Profil);
		
		
		
	}
	
	
	private CellTable<Eigenschaft> createCellTable(Vector<Eigenschaft> eigenschaften) {
		
		CellTable<Eigenschaft> profilTabelle = new CellTable<Eigenschaft>(KEY_PROVIDER);

		// Die Spalte der Eigenschaften-Tabelle wird erstellt und deren Inhalt
		// definiert.
		TextColumn<Eigenschaft> nameColumn = new TextColumn<Eigenschaft>() {
			public String getValue(Eigenschaft object) {
				return object.getName();
			}
		};

		TextColumn<Eigenschaft> valueColumn = new TextColumn<Eigenschaft>() {
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
			  //TODO: löschen implementieren
		    Window.alert("You clicked: " + object.getName());
		  }
		});

		
		profilTabelle.addColumn(nameColumn);
		profilTabelle.addColumn(valueColumn);
		profilTabelle.addColumn(buttonColumn);

		valueColumn.setFieldUpdater(new FieldUpdater<Eigenschaft, String>() {

			@Override
			public void update(int index, Eigenschaft object, String value) {
				object.setWert(value);
				Project4uVerwaltung.updateEigenschaft(object, new AsyncCallback<Eigenschaft>() {

					public void onSuccess(Eigenschaft result) {

					}

					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}
				});

			}
		});

		nameColumn.setFieldUpdater(new FieldUpdater<Eigenschaft, String>() {

			@Override
			public void update(int index, Eigenschaft object, String value) {
				object.setName(value);
				;
				Project4uVerwaltung.updateEigenschaft(object, new AsyncCallback<Eigenschaft>() {

					public void onSuccess(Eigenschaft result) {

					}

					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}
				});

			}
		});
		
		profilTabelle.setRowData(0, eigenschaften);
		
		return profilTabelle;
	}
	
	
	
	
	
	
	
	
	
	
	
}
