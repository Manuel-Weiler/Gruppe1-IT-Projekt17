/**
 * 
 */
package de.hdm.gruppe1.Project4u.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;

import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;

/**
 * @author Tobias
 *
 */
public class BewerbungWidget {
	Project4uAdministrationAsync Project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();
	DialogBox box = new DialogBox();
	

	Button save = new Button("Bewerbung abschicken");
	Button cancel = new Button("Abbrechen");
	
	public BewerbungWidget(Ausschreibung aus){
		
	}
	
	
	
	
	
}
