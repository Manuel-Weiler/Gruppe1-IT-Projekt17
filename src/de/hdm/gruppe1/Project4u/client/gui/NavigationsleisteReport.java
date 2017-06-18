package de.hdm.gruppe1.Project4u.client.gui;

import java.util.Vector;

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
import de.hdm.gruppe1.Project4u.server.db.OrganisationseinheitMapper;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.ReportGeneratorAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.report.HTMLReportWriter;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAlleAusschreibungen;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAusschreibungenForPartnerprofil;

public class NavigationsleisteReport extends Composite {

	ReportGeneratorAsync ReportVerwaltung = ClientsideSettings.getReportVerwaltung();
	Project4uAdministrationAsync Project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();

	public Organisationseinheit aktuellerNutzer = null;

	// Men� f�r den Reportgenerator

	VerticalPanel menuReportPanel = new VerticalPanel();

	Button homeButton = new Button("Startseite");
	Button alleAusschreibungenButton = new Button("Alle Ausschreibungen");
	Button ausschreibungenForPartnerprofilButton = new Button("Ausschreibungen die zu dir passen");
	Button testButton = new Button("Test");

	// Methode um den aktuellen Nutzer zu bekommen

	public NavigationsleisteReport() {

		menuReportPanel.add(homeButton);
		menuReportPanel.add(alleAusschreibungenButton);
		menuReportPanel.add(ausschreibungenForPartnerprofilButton);
		menuReportPanel.add(testButton);

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

		// Testmethode
		testButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {

				ReportVerwaltung.testMethode(new AsyncCallback<String>() {

					public void onSuccess(String result) {
						DialogBox dBox = new DialogBox();

						Label label = new Label(result);
						dBox.add(label);
						dBox.center();
						dBox.setAutoHideEnabled(true);
						dBox.show();
					}

					public void onFailure(Throwable caught) {
						DialogBox dBox = new DialogBox();

						Label label = new Label(caught.getMessage());
						dBox.add(label);
						dBox.center();
						dBox.setAutoHideEnabled(true);
						dBox.show();
					}
				});
			}
		});

		alleAusschreibungenButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {

				ReportVerwaltung.createAlleAusschreibungenReport(new AsyncCallback<ReportByAlleAusschreibungen>() {

					public void onSuccess(ReportByAlleAusschreibungen result1) {

						if (result1 != null) {
							HTMLReportWriter writer = new HTMLReportWriter();
							writer.process(result1);
							RootPanel.get("contentR").clear();
							RootPanel.get("contentR").add(new HTML(writer.getReportText()));
						}
					}

					public void onFailure(Throwable caught) {
						DialogBox dBox = new DialogBox();

						Label label = new Label(caught.getMessage());
						dBox.add(label);
						dBox.center();
						dBox.setAutoHideEnabled(true);
						dBox.show();

					}
				});
			}
		});

		ausschreibungenForPartnerprofilButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {

				Project4uVerwaltung.getOrganisationseinheitByUser(ClientsideSettings.getAktuellerUser(),
						new AsyncCallback<Organisationseinheit>() {

							public void onSuccess(Organisationseinheit result) {
								// if (result != null) {
								aktuellerNutzer = result;

								ReportVerwaltung.createAusschreibungenForPartnerprofil(aktuellerNutzer,
										new AsyncCallback<ReportByAusschreibungenForPartnerprofil>() {
											public void onSuccess(ReportByAusschreibungenForPartnerprofil result2) {

												if (result2 != null) {
													HTMLReportWriter writer = new HTMLReportWriter();
													writer.process(result2);
													RootPanel.get("contentR").clear();
													RootPanel.get("contentR").add(new HTML(writer.getReportText()));
												}
											}

											public void onFailure(Throwable caught) {
												DialogBox dBox = new DialogBox();

												Label label = new Label(caught.getMessage());
												dBox.add(label);
												dBox.center();
												dBox.setAutoHideEnabled(true);
												dBox.show();

											}

										});

								// }

							}

							public void onFailure(Throwable caught) {
								DialogBox dBox = new DialogBox();

								Label label = new Label(caught.getMessage());
								dBox.add(label);
								dBox.center();
								dBox.setAutoHideEnabled(true);
								dBox.show();

							}

						});

			}
		});

		initWidget(menuReportPanel);
	}

	public void homeButtonClick() {
		homeButton.click();
	}
}
