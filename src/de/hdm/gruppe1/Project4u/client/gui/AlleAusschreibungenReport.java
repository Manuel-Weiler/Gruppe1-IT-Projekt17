package de.hdm.gruppe1.Project4u.client.gui;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.report.HTMLReportWriter;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAllAusschreibungen;

public class AlleAusschreibungenReport extends VerticalPanel{
	
	//TODO: Sobald der Login implementiert ist muss hier der aktuelle Nutzer nochmal abgerufen werden.
	
	/**
	 * VerticalPanel hinzufÃ¼gen.
	 */
	public VerticalPanel verPanel = new VerticalPanel();

	/**
	 * Label zur Information hinzufÃ¼gen.
	 */
	private Label infoLabel = new Label();
	private Label ueberschriftLabel = new Label();
	
	
	
	// Konstructor
	public AlleAusschreibungenReport(){
		run();
	}
	
	//Name des Reports
	public void run(){
		this.add(verPanel);
		
		ueberschriftLabel.setText("Alle Ausschreibungen");
		
		reportAuslesen();
		verPanel.add(ueberschriftLabel);
		verPanel.add(infoLabel);
	}
	
	//Report auslesen

	
	public void reportAuslesen(){
		ClientsideSettings.getReportGenerator().createAllAusschreibungenReport(new AsyncCallback<ReportByAllAusschreibungen>(){
			
			//Fehler abfangen
			public void onFailure(Throwable caught){
				infoLabel.setText("Fehler");
			}
			
			/**
			* Report auslesen
			* @param writer neue HTML Seite erzeugen und fuer den Report ausgeben
			**/
			
			public void onSuccess(ReportByAllAusschreibungen report){
				if(report != null){
					//neue HTML-Seite für den Report erzeugen
					
					HTMLReportWriter writer = new HTMLReportWriter();
					//TODO: Methode in HTMLReportWriter implementieren, welche eine Tabelle für den Report erzeugt.
					RootPanel.get("contentR").clear();
					RootPanel.get("contentR").add(new HTML(writer.getReportText()));
				}
			}
			
		});
	}
		
}
