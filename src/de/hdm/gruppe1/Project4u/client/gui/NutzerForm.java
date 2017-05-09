package de.hdm.gruppe1.Project4u.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;

public class NutzerForm extends VerticalPanel {

	Project4uAdministrationAsync project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();

	private TextBox vornameTextBox = new TextBox();
	private TextBox nachnameTextBox = new TextBox();

	private Label idValueLabel = new Label();
	private VerticalPanel vertPanel = new VerticalPanel();

	// Formular zur Erstellung von einem neuen Nutzer.
	public void loadNutzerForm(String email) {
		final String emailAddress = email;

		Grid nutzerGrid = new Grid(4, 3);
		this.add(nutzerGrid);
		vertPanel.add(nutzerGrid);

		Label idLabel = new Label("");
		nutzerGrid.setWidget(0, 0, idLabel);
		nutzerGrid.setWidget(0, 1, idValueLabel);

		Label vornameLabel = new Label("Vorname:");
		nutzerGrid.setWidget(1, 0, vornameLabel);
		nutzerGrid.setWidget(1, 1, vornameTextBox);

		Label nachnameLabel = new Label("Nachname:");
		nutzerGrid.setWidget(2, 0, nachnameLabel);
		nutzerGrid.setWidget(2, 1, nachnameTextBox);

		RootPanel.get("Nutzer").add(vertPanel);
		HorizontalPanel nutzerButtonsPanel = new HorizontalPanel();
		this.add(nutzerButtonsPanel);

		Button anlegenButton = new Button("Anlegen");
		nutzerButtonsPanel.add(anlegenButton);
		Button abbrechenButton = new Button("Abbrechen");
		nutzerButtonsPanel.add(abbrechenButton);

		RootPanel.get("Nutzer").add(nutzerButtonsPanel);

		// Bei Abbruch wird nutzerform neu geladen und emailadresse eingetragen
		abbrechenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get("Nutzer").clear();
				NutzerForm nutzerform = new NutzerForm();
				nutzerform.loadNutzerForm(emailAddress);
			}
		});
		
		//Speicher-Button zum Anlegen
		anlegenButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				String vorname = vornameTextBox.getText();
				String nachname = nachnameTextBox.getText();
				
				project4uVerwaltung.createOrganisationseinheit(emailAddress, vorname, nachname, new CreateNutzerCallback());
			}
		});
	}

}

class CreateNutzerCallback implements AsyncCallback<Organisationseinheit>{
	public void onFailure(Throwable caught){
		Window.alert("Das Anlegen ist fehlgeschlagen!");
	}
	
	public void onSuccess(Organisationseinheit nutzer){
		if(nutzer != null){
			Window.alert("Anlegen erfolgreich!");
			RootPanel.get("Nutzer").clear();
			Window.Location.reload();
			//Startseite loadStartseite = new Startseite();
			//loadStartseite.loadStartseite();
		}
	}
	
}
