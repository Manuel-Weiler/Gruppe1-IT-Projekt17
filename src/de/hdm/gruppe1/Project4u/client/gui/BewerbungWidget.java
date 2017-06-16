/**
 * 
 */
package de.hdm.gruppe1.Project4u.client.gui;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;

/**
 * @author Tobias
 *
 */
public class BewerbungWidget {
	Project4uAdministrationAsync Project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();
	
	DialogBox box = new DialogBox();
	VerticalPanel vp = new VerticalPanel();
	FlexTable flex = new FlexTable();

	
	HTML hinweis = new HTML(
			"<p>Bitte wählen Sie das Profil mit dem Sie sich bewerben <br>Das Profil wird dem Projektleiter übermittel, passen Sie es ggf. an!</p>");

	Label ausName = new Label("Bewerbung auf die Ausschreibung: ");
	Label datum = new Label("Datum ");
	Label text = new Label("Motivationsschreiben: ");
	
	
	ListBox bewerbendesProfil = new ListBox();
	TextBox ausschreibungsname = new TextBox();
	DateBox erstelldatum = new DateBox();
	TextArea freitext = new TextArea();
	
	Button save = new Button("Bewerbung abschicken");
	Button cancel = new Button("Abbrechen");
	
	public BewerbungWidget(Ausschreibung aus){
		
		Project4uVerwaltung.getLinkedTeamAndUnternehmenOfOrganisationseinheit(ClientsideSettings.getAktuellerUser(), new getOrganisationseinheitenCallback());
		
		
		
	}
	
	
	private class getOrganisationseinheitenCallback implements AsyncCallback<Vector<Organisationseinheit>>{

		
		@Override
		public void onFailure(Throwable caught) {
		}

		
		@Override
		public void onSuccess(Vector<Organisationseinheit> result) {
			// TODO Auto-generated method stub
			
			
		}
		
	}
	
	
}
