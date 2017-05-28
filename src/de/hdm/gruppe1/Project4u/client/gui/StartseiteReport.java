package de.hdm.gruppe1.Project4u.client.gui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;

public class StartseiteReport extends Composite {
	
	Project4uAdministrationAsync project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();
	
	VerticalPanel startReportPanel = new VerticalPanel();
	Label satz = new Label("Mit den Buttons auf der linken Seite finden Sie alle Reports!");
	
	public StartseiteReport(){
		
		startReportPanel.setSpacing(20);
		startReportPanel.add(satz);
		
		initWidget(startReportPanel);
	}

}
