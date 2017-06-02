package de.hdm.gruppe1.Project4u.client.gui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.ReportGeneratorAsync;

public class StartseiteReport extends Composite {

	ReportGeneratorAsync ReportGenerator = ClientsideSettings.getReportVerwaltung();
	
	VerticalPanel startReportPanel = new VerticalPanel();
	Label satz = new Label("Mit den Buttons auf der linken Seite finden Sie alle Reports!");
	
	public StartseiteReport(){
		
		startReportPanel.setSpacing(20);
		startReportPanel.add(satz);
		
		initWidget(startReportPanel);
	}

}
