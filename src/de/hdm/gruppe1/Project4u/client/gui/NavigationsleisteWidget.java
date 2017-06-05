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
import de.hdm.gruppe1.Project4u.shared.bo.Projektmarktplatz;

public class NavigationsleisteWidget extends Composite{
	
	Project4uAdministrationAsync Project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();
	/**
	 * Menüleiste wird als Widget erstellt.
	 */
	private int test=0; 
	
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
		
		profilButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				DialogBox dBox = new DialogBox();
				VerticalPanel v = new VerticalPanel();
				Button allbuttons = new Button("test");
				allbuttons.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						Project4u.nt.setButtonsEnabled();
						
					}
				});
				Label label = new Label("profil");
				v.add(label);
				v.add(allbuttons);
				dBox.add(v);
				dBox.center();
				dBox.setAutoHideEnabled(true);
				dBox.show();
				
			}
		});
		
		
		initWidget(menuPanel);
	}
	public void homeButtonclick(){
		homeButton.click();
	}
	
	public void setButtonsEnabled(){
		pMarktplatz.setEnabled(true);
		eBewerbungen.setEnabled(true);
		aBewerbungen.setEnabled(true);
		profilButton.setEnabled(true);
	}
	
	public void setButtonsUnenabled(){	
		pMarktplatz.setEnabled(false);
		eBewerbungen.setEnabled(false);
		aBewerbungen.setEnabled(false);
		profilButton.setEnabled(false);
		
		
		
		
	}
	public int getTest() {
		return test;
	}
	public void setTest(int test) {
		this.test = test;
	}
}
