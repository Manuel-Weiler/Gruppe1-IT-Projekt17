package de.hdm.gruppe1.Project4u.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.shared.ReportGeneratorAsync;
import de.hdm.gruppe1.Project4u.shared.report.HTMLReportWriter;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAlleAusschreibungen;

public class NavigationsleisteReport extends Composite {

	ReportGeneratorAsync ReportGenerator = ClientsideSettings.getReportGenerator();

	// Men� f�r den Reportgenerator

	VerticalPanel menuReportPanel = new VerticalPanel();

	Button homeButton = new Button("Startseite");
	Button alleAusschreibungenButton = new Button("Alle Ausschreibungen");

	public NavigationsleisteReport() {

		menuReportPanel.add(homeButton);
		menuReportPanel.add(alleAusschreibungenButton);

		// Buttonabstand
		menuReportPanel.setSpacing(20);

		// Button-Layout

		homeButton.setPixelSize(200, 40);
		homeButton.setPixelSize(200, 40);

		homeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Label startseiteReportLabel = new Label("Willkommen! Hier finden Sie Ihre Reports");

				RootPanel.get("contentRHeader").clear();
				RootPanel.get("contentRHeader").add(startseiteReportLabel);

				RootPanel.get("contentR").clear();
				RootPanel.get("contentR").add(new StartseiteReport());
			}
		});

		alleAusschreibungenButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				ReportGenerator.createAlleAusschreibungenReport(new createAlleAusschreibungenReportCallback());

			}

		});
		initWidget(menuReportPanel);
	}
	
	public void homeButtonClick() {
		homeButton.click();
	}
	
}

class createAlleAusschreibungenReportCallback implements AsyncCallback<ReportByAlleAusschreibungen> {

	@Override
	public void onFailure(Throwable caught) {

		DialogBox dBox = new DialogBox();

		Label label = new Label(caught.getMessage());
		dBox.add(label);
		dBox.center();
		dBox.setAutoHideEnabled(true);
		dBox.show();
	}

	@Override
	public void onSuccess(ReportByAlleAusschreibungen report) {
		if (report != null) {
			HTMLReportWriter writer = new HTMLReportWriter();
			writer.process(report);
			RootPanel.get("contentR").clear();
			RootPanel.get("contentR").add(new HTML(writer.getReportText()));
		}

	}

}
