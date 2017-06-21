/**
 * 
 */
package de.hdm.gruppe1.Project4u.client.gui;

import java.util.Date;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;

import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Beteiligung;
import de.hdm.gruppe1.Project4u.shared.bo.Bewerbung;
import de.hdm.gruppe1.Project4u.shared.bo.Bewertung;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;

import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Tobias
 *
 */
public class BewertungWidget {
	
	Project4uAdministrationAsync Project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();
	
	DialogBox box = new DialogBox();
	VerticalPanel vp = new VerticalPanel();
	FlexTable flex = new FlexTable();
	ListBox bewertungspunkte = new ListBox();
	TextArea beurteilung = new TextArea();
	HTML punkte = new HTML("Bewerten Sie die Bewerbung</br> mit Punkten von 0,0 bis 1,0 ");
	HTML text = new HTML(
			"Beurteilungstext: </br></br><b>Hinweis:</b> Bei einer Bewertung von 1.0</br> wird automatisch eine Beteiligung festgelegt.");
	Button save = new Button("Bewertung erstellen");
	Button cancel = new Button("Abbrechen");
	Bewerbung bew = new Bewerbung();
	Bewertung bewertg = new Bewertung();
	
	
	public BewertungWidget(Bewerbung bewerbung){
		bew = bewerbung;
		
		flex.setWidget(0, 0, punkte);
		flex.setWidget(0, 1, bewertungspunkte);
		flex.setWidget(1, 0, text);
		flex.setWidget(1, 1, beurteilung);
		flex.setWidget(2, 0, cancel);
		flex.setWidget(2, 1, save);
		
		bewertungspunkte.addItem("0.0");
		bewertungspunkte.addItem("0.1");
		bewertungspunkte.addItem("0.2");
		bewertungspunkte.addItem("0.3");
		bewertungspunkte.addItem("0.4");
		bewertungspunkte.addItem("0.5");
		bewertungspunkte.addItem("0.6");
		bewertungspunkte.addItem("0.7");
		bewertungspunkte.addItem("0.8");
		bewertungspunkte.addItem("0.9");
		bewertungspunkte.addItem("1.0");
		
		bewertungspunkte.setWidth("300px");
		beurteilung.setWidth("300px");
		beurteilung.setHeight("200px");
		
		vp.add(flex);
		
		
		cancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				box.hide();
			}
		});
		
		save.addClickHandler(new saveClickHandler());
		
	}
	
	
	
	private class saveClickHandler implements ClickHandler {

		
		@Override
		public void onClick(ClickEvent event) {
			Bewertung bewert = new Bewertung();
			float points = Float.parseFloat(bewertungspunkte.getSelectedValue());
            bewert.setBewertungspunkte(points); 
            bewert.setStellungnahme(beurteilung.getValue());
            bewert.setBewerbungID(bew.getBewerbungId());
			
			Project4uVerwaltung.createBewertung(bewert, new AsyncCallback<Bewertung>() {
				
				@Override
				public void onSuccess(Bewertung result) {
					bewertg = result;
					box.hide();
					MessageBox.alertWidget("Erfolg!",
							"Ihre Bewertung wurde erfolgreich angelegt.");
					float f = result.getBewertungspunkte();
					float eins = Float.parseFloat("1.0");
					int u = Float.compare(f, eins);
					
					if (u==0){
						
						Project4uVerwaltung.getProjektOfBewerbung(bew, new AsyncCallback<Projekt>() {
							
							@Override
							public void onSuccess(Projekt projekt) {
								long personentage = projekt.getEnddatum().getTime()-new Date().getTime();
								long tage =  TimeUnit.DAYS.convert(personentage, TimeUnit.MILLISECONDS);
								int t = (int) tage;
								Project4uVerwaltung.createBeteiligung(new Date(), projekt.getEnddatum(), t, bew.getOrganisationseinheitId(), projekt.getProjektId(), bewertg.getBewerbungId(), new AsyncCallback<Beteiligung>() {
									
									@Override
									public void onSuccess(Beteiligung result) {
										// TODO Auto-generated method stub
										
									}
									@Override
									public void onFailure(Throwable caught) {
									}
								});

							}
							
							@Override
							public void onFailure(Throwable caught) {
							}
						});
						
					}

				}
				
				@Override
				public void onFailure(Throwable caught) {
				}
			});
			
		}
		
	}
	
	
	
	public VerticalPanel getVP(){
		return this.vp;
	}
	
	
	public void show(){
		box.add(vp);
		box.center();
		box.setPopupPositionAndShow(new PositionCallback() {
			
			@Override
			public void setPosition(int offsetWidth, int offsetHeight) {
				box.setPopupPosition(Window.getClientWidth()/2-(box.getOffsetWidth()/2), 
						Window.getClientHeight()/3);
				
			}
		});
	}
	

}
