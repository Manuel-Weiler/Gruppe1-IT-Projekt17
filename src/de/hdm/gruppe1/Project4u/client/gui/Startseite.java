package de.hdm.gruppe1.Project4u.client.gui;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;

public class Startseite extends HorizontalPanel{
	
	Project4uAdministrationAsync project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();
	Organisationseinheit nutzer = ClientsideSettings.getAktuellerUser();
	
	private Label head = new Label ("Willkommen!");
	
	//Panels
	private VerticalPanel vertPanel = new VerticalPanel();
	private HorizontalPanel horPanel = new HorizontalPanel();
	private HorizontalPanel nutzerPanel = new HorizontalPanel();

	//Widgets
	private FlexTable startseiteFlexTable = new FlexTable();
	private Label infoLabel = new Label();
	
	
	private int row;
	private int beschreibungInt;
	private int beschreibungsTable;
	protected int Beschreibung;
	
	//Methode f�r die Startseite
	public void loadStartseite(){
		this.add(horPanel);
		horPanel.add(vertPanel);
		
		//horizontale Navigationsleiste
		final Navigationsleiste navigationsleiste = new Navigationsleiste();
		navigationsleiste.loadNavigation();
		
		startseiteFlexTable.setText(0, 0, "Vorname");
		startseiteFlexTable.setText(1, 0, "Nachname");
		startseiteFlexTable.setText(2, 0, "EMail");
		
		//CSS
		startseiteFlexTable.addStyleName("flexTable");
		infoLabel.addStyleName("Label-Style");
		
		//Hier folgen die entsprechenden Methoden um den Nutzer aufzurufen und dessen Daten anzuzeigen:
		
		
		//TODO
		
		//Widgets und Panels hinzuf�gen
		
		vertPanel.add(startseiteFlexTable);
		vertPanel.add(infoLabel);
		horPanel.add(vertPanel);
		nutzerPanel.add(horPanel);
		
		RootPanel.get("Nutzer").add(nutzerPanel);
		RootPanel.get("Container").add(head);
		
	}
}
