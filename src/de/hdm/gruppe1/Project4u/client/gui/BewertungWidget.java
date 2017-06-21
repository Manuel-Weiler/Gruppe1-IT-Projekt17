/**
 * 
 */
package de.hdm.gruppe1.Project4u.client.gui;

import java.util.Date;
import java.util.Vector;

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
import de.hdm.gruppe1.Project4u.shared.bo.Bewerbung;
import de.hdm.gruppe1.Project4u.shared.bo.Bewertung;

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
					// TODO Auto-generated method stub
					
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
