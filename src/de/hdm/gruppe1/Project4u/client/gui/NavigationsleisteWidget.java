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
import de.hdm.gruppe1.Project4u.client.Project4u;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Projektmarktplatz;
import de.hdm.gruppe1.Project4u.client.gui.ProjektmarktplatzWidget;
//import de.hdm.gruppe1.Project4u.client.gui.MeineProjekteWidget;

public class NavigationsleisteWidget extends Composite {

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

	
	//////TEST////////////////////////////////////////
	/*Button orgaLoeschen = new Button("Orga löschen");
	Button projektmarktplatzLoeschen = new Button("Projektmarktplatz löschen");
	Button meineProjekte = new Button("Meine Projekte");*/
	//////TEST ENDE///////////////////////////////////

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
	
		
		
		////////////////////////////////TEST/////////////////////////////////////////////
		/*menuPanel.add(orgaLoeschen);
		menuPanel.add(meineProjekte);
		menuPanel.add(projektmarktplatzLoeschen);
		
		orgaLoeschen.setPixelSize(200, 40);
		meineProjekte.setPixelSize(200, 40);
		projektmarktplatzLoeschen.setPixelSize(200, 40);
		
		
		
		meineProjekte.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Project4uVerwaltung.getOrganisationseinheitByUser(ClientsideSettings.getAktuellerUser(),
						new AsyncCallback<Organisationseinheit>() {
					public void onSuccess(Organisationseinheit result) {
						RootPanel.get("content").clear();
						RootPanel.get("content").add(new MeineProjekteWidget(result));
						
					}
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());	
					}
				});
			}
		});
		
		meineProjekte.addClickHandler(new ClickHandler() {
			
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
						
						
					}
				});
				
			}
		});
		
		orgaLoeschen.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				Project4uVerwaltung.getOrganisationseinheitById(1, new AsyncCallback<Organisationseinheit>() {
					
					@Override
					public void onSuccess(Organisationseinheit result) {
						RootPanel.get("content").clear();
						Label erfolgreich1 = new Label("Name: " 
														+ result.getName() + " ID: "
														+ result.getOrganisationseinheitId() + " Typ: "
														+ result.getTyp() + " Partnerprofil ID: "
														+ result.getPartnerprofilId());
						RootPanel.get("content").add(erfolgreich1);
						
						Project4uVerwaltung.deleteOrganisationseinheit(result, new AsyncCallback<Void>() {
							
							@Override
							public void onSuccess(Void result) {
								Label orgaLoeschen = new Label("Erfolgreich geloescht");
								//RootPanel.get("content").clear();
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
		projektmarktplatzLoeschen.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				Project4uVerwaltung.getOrganisationseinheitById(1, new AsyncCallback<Organisationseinheit>() {
					
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
		});*/
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
				Label startseiteLabel = new Label("Willkommen auf Project4u, dem Projektmarktplatz-Marktplatz");

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

		
		profilButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Project4uVerwaltung.getOrganisationseinheitByUser(ClientsideSettings.getAktuellerUser(),
						new AsyncCallback<Organisationseinheit>() {
					public void onSuccess(Organisationseinheit result) {
						RootPanel.get("content").clear();
						RootPanel.get("content").add(new PartnerprofilWidget(result));
						
					}
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());	
					}
				});
			}
		});

		initWidget(menuPanel);
	}

	public void homeButtonclick() {
		homeButton.click();
	}
	
	public void setButtonsEnabled(){
		pMarktplatz.setEnabled(true);
		eBewerbungen.setEnabled(true);
		aBewerbungen.setEnabled(true);
		profilButton.setEnabled(true);
		homeButton.setEnabled(true);
	}
	
	public void setButtonsUnenabled(){	
		pMarktplatz.setEnabled(false);
		eBewerbungen.setEnabled(false);
		aBewerbungen.setEnabled(false);
		profilButton.setEnabled(false);
		homeButton.setEnabled(false);
		
		
	}
	
}
