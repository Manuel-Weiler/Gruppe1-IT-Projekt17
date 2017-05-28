package de.hdm.gruppe1.Project4u.client.gui;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.report.HTMLReportWriter;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAllAusschreibungen;

public class AlleAusschreibungenReport extends Composite{
	
	Project4uAdministrationAsync Project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();
	
	//Button seeAlleAuschreibungen = new Button ("Alle Auschreibungen");
	
	/**
	 * VerticalPanel hinzufügen.
	 */
	public VerticalPanel verPanel = new VerticalPanel();

	/**
	 * Label zur Information hinzufügen.
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
	}
	
	//Report auslesen

	
	public void reportAuslesen(){
		ClientsideSettings.getReportGenerator().createAllAusschreibungenReport(au, new AsyncCallback<ReportByAllAusschreibungen>(){
			
			//Fehler abfangen
			public void onFailure(Throwable caught){
				infoLabel.setText("Fehler");
			}
			
			/**
			* Report auslesen
			* @param writer neue HTML Seite erzeugen und fuer den Report ausgeben
			**/
			
			public void onSuccess(ReportByAllAusschreibungen report){
				
				}
			}
			
		});
	}
		
}
