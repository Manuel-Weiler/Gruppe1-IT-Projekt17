package de.hdm.gruppe1.Project4u.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;

public class NavigationsleisteReport extends Composite{
	
	Project4uAdministrationAsync Project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();
	
	//Menü für den Reportgenerator
	
	VerticalPanel menuReportPanel = new VerticalPanel();
	
	Button homeButton = new Button("Startseite");
	Button alleAusschreibungenButton = new Button("Alle Ausschreibungen");
	
	public NavigationsleisteReport(){
		
		menuReportPanel.add(homeButton);
		menuReportPanel.add(alleAusschreibungenButton);
		
		//Buttonabstand
		menuReportPanel.setSpacing(20);
		
		//Button-Layout
		
		homeButton.setPixelSize(200, 40);
		homeButton.setPixelSize(200, 40);
		
		homeButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event){
				Label startseiteReportLabel = new Label("Willkommen! Hier finden Sie Ihre Reports");
				
				RootPanel.get("contentHeader").clear();
				RootPanel.get("contentHeader").add(startseiteReportLabel);
				
				RootPanel.get("content").clear();
				RootPanel.get("content").add(new StartseiteReport());
			}
		});
		
		alleAusschreibungenButton.addClickHandler(new ClickHandler(){
			
			@Override
			public void onClick(ClickEvent event){
				
				Project4uVerwaltung.getAllAusschreibungen(new AsyncCallback<ArrayList<Ausschreibung>>() {
					
					@Override
					public void onSuccess(ArrayList<Ausschreibung> result){
						RootPanel.get("contentR").clear();
						RootPanel.get("contentR").add(new AlleAusschreibungenReport());
					}
					
					@Override
					public void onFailure(Throwable caught){
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
		
		initWidget(menuReportPanel);
	}
	
	public void homeButtonClick(){
		homeButton.click();
	}

}
