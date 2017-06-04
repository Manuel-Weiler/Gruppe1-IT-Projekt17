package de.hdm.gruppe1.Project4u.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Projektmarktplatz;

public class NavigationsleisteWidget extends Composite{
	
	Project4uAdministrationAsync Project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();
	/**
	 * Menüleiste wird als Widget erstellt.
	 */

	VerticalPanel menuPanel = new VerticalPanel();

	Button profilButton = new Button("Nutzerprofil");
	Button homeButton = new Button("Startseite");
	Button pMarktplatz = new Button("Projektmarktplätze");
	Button eBewerbungen = new Button("Eingangsbewerbungen");
	Button aBewerbungen = new Button("Ausgangsbewerbungen");
	Button logout = new Button("Logout");
	
	//Test
	Button orgaLoeschen = new Button("Orga löschen");
	Button testBtn = new Button("Test");
	

	public NavigationsleisteWidget() {

		menuPanel.add(homeButton);
		menuPanel.add(profilButton);
		
		menuPanel.add(pMarktplatz);
		menuPanel.add(eBewerbungen);
		menuPanel.add(aBewerbungen);
		
		//Test
		menuPanel.add(orgaLoeschen);
		menuPanel.add(testBtn);

		// Abstand zwischen den einzelnen Buttons
		menuPanel.setSpacing(20);

		// Layout Button
		profilButton.setPixelSize(200, 40);
		eBewerbungen.setPixelSize(200, 40);
		homeButton.setPixelSize(200, 40);
		pMarktplatz.setPixelSize(200, 40);
		aBewerbungen.setPixelSize(200, 40);
		
		
		
		////////////////////////////////TEST/////////////////////////////////////////////
		//Test
		orgaLoeschen.setPixelSize(200, 40);
		testBtn.setPixelSize(200, 40);
		
		
		testBtn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				Project4uVerwaltung.testMethode(new AsyncCallback<String>() {
				@Override
				public void onSuccess(String result) {
					Label test = new Label(result);
					RootPanel.get("content").clear();
					RootPanel.get("content").add(test);
					
				}
				
				@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});
				
			}
		});
		
		orgaLoeschen.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				Project4uVerwaltung.getOrganisationseinheitById(3, new AsyncCallback<Organisationseinheit>() {
					
					@Override
					public void onSuccess(Organisationseinheit result) {
						Label erfolgreich1 = new Label("Erfolgreich1");
						RootPanel.get("content").add(erfolgreich1);
						
						Project4uVerwaltung.deleteOrganisationseinheit(result, new AsyncCallback<Void>() {
							
							@Override
							public void onSuccess(Void result) {
								Label orgaLoeschen = new Label("Erfolgreich2");
								RootPanel.get("content").clear();
								RootPanel.get("content").add(orgaLoeschen);
								
							}
							
							@Override
							public void onFailure(Throwable caught) {
								Label orgaLoeschen = new Label("Fail2");
								RootPanel.get("content").clear();
								RootPanel.get("content").add(orgaLoeschen);
								
							}
						});
						
					}
					@Override
					public void onFailure(Throwable caught) {
						Label orgaLoeschen = new Label("Fail1");
						RootPanel.get("content").clear();
						RootPanel.get("content").add(orgaLoeschen);
						
					}
				});
			
				
			}
		});
		//////////////////////////////////TEST ENDE/////////////////////////////////////////////
		
		
		logout.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Logout hinzuf�gen
				
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
		
		pMarktplatz.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				
				Project4uVerwaltung.findAllProjektmarktplatz(new AsyncCallback<Vector<Projektmarktplatz>>() {
					
					@Override
					public void onSuccess(Vector<Projektmarktplatz> result) {
						RootPanel.get("content").clear();
						RootPanel.get("content").add(new ProjektmarktplatzWidget(result));
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						DialogBox dBox = new DialogBox();
						
						Label label = new Label(caught.getMessage());
						dBox.add(label);
						dBox.center();
						dBox.setAutoHideEnabled(true);
						dBox.show();
						
					}
				});
				
			}
		});
		
		
		
		initWidget(menuPanel);
	}
	public void homeButtonclick(){
		homeButton.click();
	}
}
