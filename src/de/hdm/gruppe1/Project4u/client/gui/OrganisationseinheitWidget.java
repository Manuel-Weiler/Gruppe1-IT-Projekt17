package de.hdm.gruppe1.Project4u.client.gui;

import java.util.Vector;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
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
	HTML heading = new HTML("<p class='heading'>Teams und Unternehmen:</p>");
	
	
	public OrganisationseinheitWidget(Vector<Organisationseinheit> orgas){
		
		CellTable<Organisationseinheit> orgaTabelle = new CellTable<Organisationseinheit>(KEY_PROVIDER);
		
		//Die Spalte der Projekt-Tabelle wird erstellt und deren Inhalt definiert.
		TextColumn<Organisationseinheit> nameColumn = new TextColumn<Organisationseinheit>() {
			public String getValue(Organisationseinheit object) {
				return object.getName();
			}
		};
		
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
