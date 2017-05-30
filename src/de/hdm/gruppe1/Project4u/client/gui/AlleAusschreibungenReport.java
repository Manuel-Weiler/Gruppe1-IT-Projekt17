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
import de.hdm.gruppe1.Project4u.shared.ReportGeneratorAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.report.HTMLReportWriter;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAlleAusschreibungen;

public class AlleAusschreibungenReport extends Composite{
	
	ReportGeneratorAsync Project4uReport = ClientsideSettings.getReportGenerator();
	
	private Label infoLabel = new Label();
	
	// Konstructor
	public AlleAusschreibungenReport(ArrayList<Ausschreibung> result){
		
	  reportAuslesen(result);
	}

	//Report auslesen

	public void reportAuslesen(ArrayList<Ausschreibung> allAusschreibungen){
		Project4uReport.createAlleAusschreibungenReport(allAusschreibungen, new AsyncCallback<ReportByAlleAusschreibungen>() {
			
			@Override
			public void onSuccess(ReportByAlleAusschreibungen result) {
				if(result != null){
				      //neue HTML-Seite f�r den Report erzeugen
				      
				      HTMLReportWriter writer = new HTMLReportWriter();
				      writer.process(result);
				      RootPanel.get("contentR").clear();
				      RootPanel.get("contentR").add(new HTML(writer.getReportText()));
				    }
				
			
			}
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		

	/*ClientsideSettings.getReportGenerator().createAlleAusschreibungenReport(new AsyncCallback<ReportByAlleAusschreibungen>(){
	  
	  //Fehler abfangen
	  public void onFailure(Throwable caught){
	    infoLabel.setText("Fehler");
	  }
	  
	  *//**
	  * Report auslesen
	  * @param writer neue HTML Seite erzeugen und fuer den Report ausgeben
	  **//*
	  
	  public void onSuccess(ReportByAlleAusschreibungen report){
	    if(report != null){
	      //neue HTML-Seite f�r den Report erzeugen
	      
	      HTMLReportWriter writer = new HTMLReportWriter();
	      writer.process(report);
	      RootPanel.get("contentR").clear();
	      RootPanel.get("contentR").add(new HTML(writer.getReportText()));
	    }
	  }
	  
	});*/
	}

	}