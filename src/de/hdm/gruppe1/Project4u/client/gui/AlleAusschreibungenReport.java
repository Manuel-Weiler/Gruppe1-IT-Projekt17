package de.hdm.gruppe1.Project4u.client.gui;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.report.HTMLReportWriter;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAlleAusschreibungen;

public class AlleAusschreibungenReport extends Composite{
	
	Project4uAdministrationAsync Project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();
	
	private Label infoLabel = new Label();
	
	// Konstructor
	public AlleAusschreibungenReport(ArrayList<Ausschreibung> result){
	  reportAuslesen();
	}

	//Report auslesen

	public void reportAuslesen(){

	ClientsideSettings.getReportGenerator().createAlleAusschreibungenReport(new AsyncCallback<ReportByAlleAusschreibungen>(){
	  
	  //Fehler abfangen
	  public void onFailure(Throwable caught){
	    infoLabel.setText("Fehler");
	  }
	  
	  /**
	  * Report auslesen
	  * @param writer neue HTML Seite erzeugen und fuer den Report ausgeben
	  **/
	  
	  public void onSuccess(ReportByAlleAusschreibungen report){
	    if(report != null){
	      //neue HTML-Seite für den Report erzeugen
	      
	      HTMLReportWriter writer = new HTMLReportWriter();
	      writer.process(report);
	      RootPanel.get("contentR").clear();
	      RootPanel.get("contentR").add(new HTML(writer.getReportText()));
	    }
	  }
	  
	});
	}

	}