package de.hdm.gruppe1.Project4u.client.gui;

import java.util.Vector;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Eigenschaft;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;

public class OrganisationseinheitWidget extends Composite{

	Project4uAdministrationAsync Project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();
	
	public static final ProvidesKey<Organisationseinheit> KEY_PROVIDER = new ProvidesKey<Organisationseinheit>() {
		public Object getKey(Organisationseinheit item) {
			return item == null ? null : item.getOrganisationseinheitId();
		}
	};
	
	Button addProjekt = new Button("Organisationseinheit hinzufügen");
	VerticalPanel vPanel = new VerticalPanel();
	HTML heading = new HTML("<h2 class='h2heading'>Teams und Unternehmen:</h2>");
	Vector<Organisationseinheit> linked = new Vector<Organisationseinheit>();
	
	 
	public OrganisationseinheitWidget(Vector<Organisationseinheit> orgas){
		
		
		
		if (orgas.isEmpty()){
			vPanel.clear();
			vPanel.add(heading);
			HTML noOrgas = new HTML("<p class='heading'>Es existieren noch keine Unternehmen und Teams, lege welche an!</p>");
			noOrgas.setHeight("40px");
			vPanel.add(noOrgas);
			vPanel.add(addProjekt);
			initWidget(vPanel);
		}
		else{
		
			
		vPanel.clear();
		vPanel.add(heading);
		getLinkedOrgas(orgas);
		//Window.alert(linked.firstElement().getName());
		
	    initWidget(vPanel);
	    
	    
	}
	
	
	
	
	
	}
	
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
			//TODO: styling
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
		};
		
		ButtonCell buttonCell = new ButtonCell();
	
		Column<Organisationseinheit, String> buttonColumn = new Column<Organisationseinheit, String>(buttonCell) {
		  @Override
		  
		 
		  
		  public String getValue(Organisationseinheit au) {
		    // The value to display in the button.
			  String buttonvalue = "";
				for (Organisationseinheit org : linked){
					if(org.getOrganisationseinheitId()==au.getOrganisationseinheitId()){
						buttonvalue=  "Zugehörigkeit zur Organisationseinheit beenden";
					}
					else{  
						buttonvalue=  "Zugehörigkeit zur Organisationseinheit definieren";
					}
			
				}
				return buttonvalue;
		 
		  }
		  
		
		  
		};
		


		
		buttonColumn.setFieldUpdater(new FieldUpdater <Organisationseinheit, String>() {
		  public void update(int index, Organisationseinheit object, String value) {
		    // Value is the button value.  Object is the row object.
			  for (Organisationseinheit org : linked){
					if(org.getOrganisationseinheitId()==object.getOrganisationseinheitId()){
						//TODO: Löschen Zugehörigkeit
						Window.alert("gelöscht");
					}
					else{  
						 setZugehoerigkeit(object);
					}
			
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
				//TODO: löschen Orga implementieren
				Button deleteOrga = new Button("Organisationseinheit löschen");
				
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
	
		SimplePager pager = new SimplePager(TextLocation.CENTER, false, 0, false);
	    pager.setDisplay(orgaTabelle);
	    pager.setPageSize(10);
	    
	    orgaTabelle.setWidth("100%");
	    
	    
	    vPanel.add(orgaTabelle);
	}
	
	private void orgaProfil(Organisationseinheit o){
		DialogBox db = new DialogBox();
		final VerticalPanel vp = new VerticalPanel();
		HTML Profil = new HTML("<p class='heading'>Profil: "+o.getName()+"</p>");
		FlexTable flexTable = new FlexTable();
		final FlexTable flexTableEigenschaften = new FlexTable();
		final Button speichern = new Button("Speichern");
		speichern.setWidth("100px");
		final Button bearbeiten = new Button("Bearbeiten");
		bearbeiten.setWidth("100px");
		
		Label email = new Label("E-Mail:");
		final TextBox mail = new TextBox();
		Label orgaName = new Label("Profilname:");
		final TextBox orgaNam = new TextBox();
		Label typ = new Label("Kontentyp:");
		final ListBox typbox = new ListBox();
		
		vp.add(Profil);
		
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
		
		
		vp.add(flexTable);
		
		Project4uVerwaltung.getEigenschaftenOfOrganisationseinheit(o, new AsyncCallback<Vector<Eigenschaft>>() {
			
			@Override
			public void onSuccess(Vector<Eigenschaft> result) {
				if(!result.isEmpty()){
				for(Eigenschaft e : result){
					TextBox tb = new TextBox();
					tb.setValue(e.getName());
					flexTableEigenschaften.setWidget(flexTableEigenschaften.getRowCount(), 0,  tb);
					TextBox tbox = new TextBox();
					tbox.setValue(e.getWert());
					flexTableEigenschaften.setWidget(flexTableEigenschaften.getRowCount()-1, 1,  tbox);
					
				}
				vp.add(flexTableEigenschaften);
				vp.add(bearbeiten);
				vp.add(speichern);
				
				}
			}
			public void onFailure(Throwable caught) {}
		});
		
		
		
		mail.setEnabled(false);
		orgaNam.setEnabled(false);
		typbox.setEnabled(false);		
		speichern.setVisible(false);
		
	
		
		speichern.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO update Orga implementieren
				
			}
		});
		
		bearbeiten.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				orgaNam.setEnabled(true);
				typbox.setEnabled(true);
				speichern.setVisible(true);
			}
		});
		
		db.add(vp);
		db.setAnimationEnabled(true);
		db.setAutoHideEnabled(true);
		db.center();
		db.show();
		
		
	}
	
	 
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
	
	private void setZugehoerigkeit(Organisationseinheit orga) {

		Project4uVerwaltung.insertLinkedTeamUnternehmenOfOrganisationseinheit(ClientsideSettings.getAktuellerUser(),
				orga, new AsyncCallback<Void>() {

					@Override
					public void onSuccess(Void result) {
						// TODO Refresh Tabelle

						
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}
				});
	}
	
	
}
