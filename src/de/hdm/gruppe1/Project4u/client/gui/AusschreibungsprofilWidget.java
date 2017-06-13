/**
 * 
 */
package de.hdm.gruppe1.Project4u.client.gui;

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

import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;

/**
 * @author Tobias
 *
 */
public class AusschreibungsprofilWidget extends Composite{
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
		
		
		bezeichnung.setEnabled(false);
		projektleiter.setEnabled(false);
		bewerbungsfrist.setEnabled(false);
		ausschreibungstext.setEnabled(false);
		
		//Abfrage ob die Ausschreibung neu erstellt oder ein bestehendes Objekt ist
		if(aus.getAusschreibungId()==0){
			
			bezeichnung.setEnabled(true);
			bewerbungsfrist.setEnabled(true);
			ausschreibungstext.setEnabled(true);
			
			flex.setWidget(5, 1, savenew);
			
		}
		else{
			
		}
		
		
		
	}
	
	
	
	
}
