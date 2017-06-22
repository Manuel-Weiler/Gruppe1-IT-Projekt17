/**
 * 
 */
package de.hdm.gruppe1.Project4u.client.gui;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Beteiligung;
import de.hdm.gruppe1.Project4u.shared.bo.Bewerbung;
import de.hdm.gruppe1.Project4u.shared.bo.Bewertung;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;

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
	Ausschreibung ausschreibung = new Ausschreibung();

	public BewertungWidget(Bewerbung bewerbung) {
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

					//Abgleich, ob die Bewertung "1.0" vergeben wurde. Bei dieser wird automatisch eine Beteiligung erzeugt.
					float f = result.getBewertungspunkte();
					float eins = Float.parseFloat("1.0");
					int u = Float.compare(f, eins);

					if (u == 0) {

						Project4uVerwaltung.getProjektOfBewerbung(bew, new getProjektOfBewerbungCallback());

					} else {
						
						Project4uVerwaltung.updateStatusOfBewerbung("abgelehnt", bew.getBewerbungId(), new AsyncCallback<Void>() {
							
							@Override
							public void onSuccess(Void result) {}
							public void onFailure(Throwable caught) {
								Window.alert(caught.getMessage());}});

						MessageBox.alertWidget("Erfolg!", "Ihre Bewertung wurde erfolgreich angelegt.");
					}

				}

				@Override
				public void onFailure(Throwable caught) {
				}
			});
		}
	}
	
	
	
	
	
	
	

	/**
	 * Die Methode gibt die Anzahl an Tagen zwischen zwei Date-Objekten zurück.
	 * @param one
	 * @param two
	 * @return
	 */
	private static long daysBetween(Date one, Date two) {
		long difference = (one.getTime() - two.getTime()) / 86400000; // 1000*60*60*24
		return Math.abs(difference);
	}
	
	
	
	
	
	

	public VerticalPanel getVP() {
		return this.vp;
	}
	
	
	
	

	/**
	 * Die Methode fügt das Widget einer DialogBox hinzu, und öffnet diese.
	 */
	public void show() {
		box.add(vp);
		box.center();
		box.setPopupPositionAndShow(new PositionCallback() {

			@Override
			public void setPosition(int offsetWidth, int offsetHeight) {
				box.setPopupPosition(Window.getClientWidth() / 2 - (box.getOffsetWidth() / 2),
						Window.getClientHeight() / 3);

			}
		});
	}
	
	
	
	

	/**
	 * Im Anschluss an die Anfrage des Projektes wird ein Beteiligungsobjekt über die Proxy erzeugt.
	 * @author Tobias
	 *
	 */
	private class getProjektOfBewerbungCallback implements AsyncCallback<Projekt> {

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(Projekt projekt) {

			long l = daysBetween(new Date(), projekt.getEnddatum());
			int tage = (int) l;

			Project4uVerwaltung.createBeteiligung(new Date(), projekt.getEnddatum(), tage,
					bew.getOrganisationseinheitId(), projekt.getProjektId(), bewertg.getBewerbungId(),
					new createBeteiligungCallback());

		}

	}

	
	
	
	
	
	private class createBeteiligungCallback implements AsyncCallback<Beteiligung> {

		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(Beteiligung result) {
			
			MessageBox.alertWidget("Erfolg!", "Ihre Bewertung mit '1.0' hat eine Beteiligung erfolgreich angelegt.");
			
			
			
			
			//Status der betroffenen Ausschreibung auf "beendet" ändern
			Project4uVerwaltung.updateStatusOfAusschreibung(bew.getAusschreibungId(), "beendet", new updateStatusOfAusschreibungCallback());
		}
	}
	
	
	
	
	private class updateStatusOfAktuelleBewerbungCallback implements AsyncCallback<Void>{

		@Override
		public void onFailure(Throwable caught) {	
		}

		@Override
		public void onSuccess(Void result) {
			
			
			//Alle Bewerbungen mit Status "ausstehend" auf die jeweilige Ausschreibung werden abgelehnt und erhalten eine Bewertung mit '0.0'
			Project4uVerwaltung.cancelAllBewerbungenOfAusschreibungWithStatusAusstehend(ausschreibung, new AsyncCallback<Void>() {
				
				@Override
				public void onSuccess(Void result) {	
				}
				
				@Override
				public void onFailure(Throwable caught) {	
				}
			});
			
		}
		
	}

		
		
	
	private class updateStatusOfAusschreibungCallback implements AsyncCallback<Ausschreibung>{

		@Override
		public void onFailure(Throwable caught) {
		}
		@Override
		public void onSuccess(Ausschreibung result) {
			ausschreibung = result;
			
			/*
			 * Update der Bewerbung und setzen des Status angenommen. Im
			 * Anschluss daran werden alle noch nicht bewerteten Bewerbungen
			 * (Status 'ausstehend')auf die selbe Ausschreibung mit "0.0"
			 * bewertet und auf "abgelehnt" gesetzt.
			 */
			Project4uVerwaltung.updateStatusOfBewerbung("angenommen", bew.getBewerbungId(), new updateStatusOfAktuelleBewerbungCallback());
			
		}
		
	}
	
	
	

}
