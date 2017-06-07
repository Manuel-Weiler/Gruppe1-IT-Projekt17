package de.hdm.gruppe1.Project4u.client.gui;

import java.util.Vector;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
		
		ButtonCell buttonCell = new ButtonCell();
		Column<Organisationseinheit, String> buttonColumn = new Column<Organisationseinheit, String>(buttonCell) {
		  @Override
		  public String getValue(Organisationseinheit au) {
		    // The value to display in the button.
		    return "Zugehörigkeit zur Organisationseinheit definieren";
		  }
		};
		


		
		buttonColumn.setFieldUpdater(new FieldUpdater <Organisationseinheit, String>() {
		  public void update(int index, Organisationseinheit object, String value) {
		    // Value is the button value.  Object is the row object.
			  //TODO: löschen implementieren
		    Window.alert("You clicked: " + object.getName());
		  }
		});
		  
		
		
		final SingleSelectionModel<Organisationseinheit> selectionModel = new SingleSelectionModel<Organisationseinheit>(KEY_PROVIDER);	
		orgaTabelle.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				final DialogBox diBox = new DialogBox();
				VerticalPanel vPanel = new VerticalPanel();
				Button seeOrga = new Button("Profil zur Organisationseinheit ansehen");
				seeOrga.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						diBox.hide();
						orgaProfil(selectionModel.getSelectedObject()); 
						
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
			}});
			
		
		
		
		orgaTabelle.addColumn(nameColumn, "Name");
		orgaTabelle.addColumn(typeColumn, "Typ");
		orgaTabelle.addColumn(buttonColumn);
		
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
	    initWidget(vPanel);
	    
	    
	}
	
	
	
	
	}
	
	public void orgaProfil(Organisationseinheit o){
		DialogBox db = new DialogBox();
		final VerticalPanel vp = new VerticalPanel();
		Label Profil = new Label("Profil: "+o.getName());
		FlexTable flexTable = new FlexTable();
		final FlexTable flexTableEigenschaften = new FlexTable();
		final Button speichern = new Button("Speichern");
		final Button bearbeiten = new Button("Bearbeiten");
		
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
		typbox.setVisibleItemCount(1);
		mail.setTitle("Die E-Mail-Adresse kann nicht geändert werden.");
		typbox.setTitle("Der Kontentyp kann nicht geändert werden. Legen Sie ggf. zusäzliche Konten an.");
		
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
				mail.setEnabled(true);
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
}
