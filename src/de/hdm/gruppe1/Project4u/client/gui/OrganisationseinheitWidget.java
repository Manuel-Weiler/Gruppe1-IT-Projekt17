package de.hdm.gruppe1.Project4u.client.gui;

import java.util.Vector;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
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
			
			
		CellTable<Organisationseinheit> orgaTabelle = new CellTable<Organisationseinheit>(KEY_PROVIDER);
		
		//Die Spalte der Organisationseinheiten-Tabelle wird erstellt und deren Inhalt definiert.
		TextColumn<Organisationseinheit> nameColumn = new TextColumn<Organisationseinheit>() {
			public String getValue(Organisationseinheit object) {
				return object.getName();
			}
		};
		
		
		ButtonCell buttonCell = new ButtonCell();
		Column<Organisationseinheit, String> buttonColumn = new Column<Organisationseinheit, String>(buttonCell) {
		  @Override
		  public String getValue(Organisationseinheit au) {
		    // The value to display in the button.
		    return "Organisationseinheit "+ au.getName()+ " löschen";
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
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		
		
	}
	
	
	
	
	}
	
	
}
