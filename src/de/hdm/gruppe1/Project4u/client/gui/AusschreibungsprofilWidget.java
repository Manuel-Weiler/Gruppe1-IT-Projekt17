/**
 * 
 */
package de.hdm.gruppe1.Project4u.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
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
public class AusschreibungsprofilWidget extends Composite{
	Project4uAdministrationAsync Project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();
	FlexTable flex = new FlexTable();
	DialogBox db = new DialogBox();
	HTML heading = new HTML("<p class='heading'>Neue Ausschreibung:</p>");
	VerticalPanel vPan = new VerticalPanel();
	
	Button savenew = new Button("Speichern");
	Button update = new Button("Speichern");
	Button cancel = new Button("Abbrechen");
	
	Label bezeichng = new Label("Projektbezeichnung: ");
	Label projektleitr = new Label("Projektleiter: ");
	Label bewerbungsfrst = new Label("Bewerbungsfrist: ");
	Label ausschrtext = new Label("Ausschreibungstext: ");
	
	TextBox bezeichnung = new TextBox();
	TextBox projektleiter = new TextBox();
	DateBox bewerbungsfrist = new DateBox();
	TextArea ausschreibungstext = new TextArea();
	
	
	public AusschreibungsprofilWidget(Ausschreibung aus){
		db.setGlassEnabled(true);
		
		vPan.add(heading);
		
		flex.setWidget(0, 0, bezeichng);
		flex.setWidget(0, 1, bezeichnung);
		flex.setWidget(1, 0, projektleitr);
		flex.setWidget(1, 1, projektleiter);
		flex.setWidget(2, 0, bewerbungsfrst);
		flex.setWidget(2, 1, bewerbungsfrist);
		flex.setWidget(3, 0, ausschrtext);
		flex.setWidget(4, 0, ausschreibungstext);
		flex.setWidget(5, 0, cancel);
		
		vPan.add(flex);
		
		bezeichnung.setEnabled(false);
		projektleiter.setEnabled(false);
		bewerbungsfrist.setEnabled(false);
		ausschreibungstext.setEnabled(false);
		
		
		
		
		
		// Abfrage ob die Ausschreibung neu erstellt oder ein bestehendes Objekt
		// ist
		if (aus.getAusschreibungId() == 0) {

			Project4uVerwaltung.getOrganisationseinheitByUser(ClientsideSettings.getAktuellerUser(),
					new AsyncCallback<Organisationseinheit>() {

						@Override
						public void onSuccess(Organisationseinheit result) {
							projektleiter.setValue(result.getName());

						}
						public void onFailure(Throwable caught) {
						}
					});
		
			bezeichnung.setEnabled(true);
			bewerbungsfrist.setEnabled(true);
			ausschreibungstext.setEnabled(true);
			
			flex.setWidget(5, 1, savenew);
			
		}
		else{
			
		}
		
		db.add(vPan);
		initWidget(db);
	}
	
	/**
	 * Der Clickhandler soll das erzeugen eines neuen Ausschreibungsobjektes
	 * ausführen, sobald der Nutzer alle erforderlichen Daten in das
	 * AusschreibungsprofilWidget eingetragen hat.
	 * @author Tobias
	 */
	private class neueAusschreibungSpeichernClickhandler implements ClickHandler{

		
		@Override
		public void onClick(ClickEvent event) {
			
			
		}
		
	}
	
	
}
