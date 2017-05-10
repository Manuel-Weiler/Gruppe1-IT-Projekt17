package de.hdm.gruppe1.Project4u.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NavigationsleisteWidget extends Composite{
	/**
	 * MenÃ¼leiste wird als Widget erstellt.
	 */

	VerticalPanel menuPanel = new VerticalPanel();

	Button profilButton = new Button("Nutzerprofil");
	Button homeButton = new Button("Startseite");
	Button pMarktplatz = new Button("Projektmarktplätze");
	Button eBewerbungen = new Button("Eingangsbewerbungen");
	Button aBewerbungen = new Button("Ausgangsbewerbungen");
	Button logout = new Button("Logout");
	

	public NavigationsleisteWidget() {

		menuPanel.add(homeButton);
		menuPanel.add(profilButton);
		
		menuPanel.add(pMarktplatz);
		menuPanel.add(eBewerbungen);
		menuPanel.add(aBewerbungen);

		// Abstand zwischen den einzelnen Buttons
		menuPanel.setSpacing(20);

		// Layout Button
		profilButton.setPixelSize(200, 40);
		eBewerbungen.setPixelSize(200, 40);
		homeButton.setPixelSize(200, 40);
		pMarktplatz.setPixelSize(200, 40);
		aBewerbungen.setPixelSize(200, 40);
		
		logout.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Logout hinzufügen
				
			}
		});

		homeButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Label startseiteLabel = new Label("Willkommen auf Project4u, der Projektmarktplatz für Projekte");

				RootPanel.get("contentHeader").clear();
				RootPanel.get("contentHeader").add(startseiteLabel);

				RootPanel.get("content").clear();
				RootPanel.get("content").add(new StartseiteWidget());
				
				
			}
			
			
		});
		
		
		
		initWidget(menuPanel);
	}
	public void homeButtonclick(){
		homeButton.click();
	}
}
