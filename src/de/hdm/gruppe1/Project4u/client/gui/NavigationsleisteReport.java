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
import de.hdm.gruppe1.Project4u.shared.LoginInfo;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.ReportGeneratorAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.report.FanInFanOut;
import de.hdm.gruppe1.Project4u.shared.report.HTMLReportWriter;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAlleAusschreibungen;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAlleBewerbungenForAusschreibungen;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAusschreibungenForPartnerprofil;
import de.hdm.gruppe1.Project4u.shared.report.ReportByProjektverflechtungen;
import de.hdm.gruppe1.Project4u.shared.report.ReportForEigeneBewerbungen;

public class NavigationsleisteReport extends Composite {

	protected static final Organisationseinheit Organisationseinheit = null;

	ReportGeneratorAsync ReportVerwaltung = ClientsideSettings.getReportVerwaltung();
	Project4uAdministrationAsync Project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();

	public LoginInfo loginInfo = null;
	// public Organisationseinheit aktuellerNutzer = null;

	// Menï¿½ fï¿½r den Reportgenerator

	VerticalPanel menuReportPanel = new VerticalPanel();

	Button homeButton = new Button("Startseite");
	Button alleAusschreibungenButton = new Button("Alle Ausschreibungen");
	Button ausschreibungenForPartnerprofilButton = new Button("Ausschreibungen die zu dir passen");
	Button alleBewerbungenForUsersAusschreibungenButton = new Button("Alle Bewerbungen auf deine Ausschreibungen");
	Button userbewerbungenForAusschreibungButton = new Button("Deine Bewerbungen auf Ausschreibungen");
	Button projektverflechtungenButton = new Button("Projektverflechtungen");
	Button fanInFanOutButton = new Button("FanIn-FanOut-Analyse");

	// Methode um den aktuellen Nutzer zu bekommen

	public NavigationsleisteReport() {

		menuReportPanel.add(homeButton);

		menuReportPanel.add(alleAusschreibungenButton);
		menuReportPanel.add(ausschreibungenForPartnerprofilButton);
		menuReportPanel.add(alleBewerbungenForUsersAusschreibungenButton);
		menuReportPanel.add(userbewerbungenForAusschreibungButton);
		menuReportPanel.add(projektverflechtungenButton);
		menuReportPanel.add(fanInFanOutButton);

		// Buttonabstand
		menuReportPanel.setSpacing(5);

		// Button-Layout

		homeButton.setPixelSize(200, 40);

		alleAusschreibungenButton.setPixelSize(200, 40);
		ausschreibungenForPartnerprofilButton.setPixelSize(200, 40);
		alleBewerbungenForUsersAusschreibungenButton.setPixelSize(200, 40);
		userbewerbungenForAusschreibungButton.setPixelSize(200, 40);
		projektverflechtungenButton.setPixelSize(200, 40);
		fanInFanOutButton.setPixelSize(200, 40);

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

		/*
		 * Ausgabe Report 1 Alle Ausschreibungen
		 * 
		 * @author Dominik Sasse
		 */
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

		/*
		 * Ausgabe Report 2 Alle Ausschreibungen passend zum Nutzer
		 * 
		 * @author Dominik Sasse
		 */
		ausschreibungenForPartnerprofilButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {

				Project4uVerwaltung.getOrganisationseinheitByUser(ClientsideSettings.getAktuellerUser(),
						new AsyncCallback<Organisationseinheit>() {

							public void onSuccess(Organisationseinheit result) {

								ReportVerwaltung.createAusschreibungenForPartnerprofil(result,
										new AsyncCallback<ReportByAusschreibungenForPartnerprofil>() {
											public void onSuccess(ReportByAusschreibungenForPartnerprofil result2) {

												if (result2 != null) {
													HTMLReportWriter writer = new HTMLReportWriter();
													writer.process(result2);
													RootPanel.get("contentR").clear();
													RootPanel.get("contentR").add(new HTML(writer.getReportText()));
												}

										else {
													DialogBox dBox = new DialogBox();

													Label label = new Label(
															"Es existieren leider keine passenden Ausschreibungen.");
													dBox.add(label);
													dBox.center();
													dBox.setAutoHideEnabled(true);
													dBox.show();
												}
											}

											public void onFailure(Throwable caught) {
												DialogBox dBox = new DialogBox();

												Label label = new Label(
														"Es existieren leider keine passenden Ausschreibungen.");
												dBox.add(label);
												dBox.center();
												dBox.setAutoHideEnabled(true);
												dBox.show();

											}

										});

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

		/*
		 * Ausgabe Report 3 AlleBewerbungen auf Ausschreibungen des Nutzers
		 * 
		 * @author Dominik Sasse
		 */
		alleBewerbungenForUsersAusschreibungenButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {

				Project4uVerwaltung.getOrganisationseinheitByUser(ClientsideSettings.getAktuellerUser(),
						new AsyncCallback<Organisationseinheit>() {

							public void onSuccess(Organisationseinheit x) {

								ReportVerwaltung.createAlleBewerbungenForAusschreibungen(x,
										new AsyncCallback<ReportByAlleBewerbungenForAusschreibungen>() {
											public void onSuccess(ReportByAlleBewerbungenForAusschreibungen y) {

												if (y != null) {
													HTMLReportWriter writer = new HTMLReportWriter();
													writer.process(y);
													RootPanel.get("contentR").clear();
													RootPanel.get("contentR").add(new HTML(writer.getReportText()));
												} else {
													DialogBox dBox = new DialogBox();

													Label label = new Label(
															"Es existieren keine Ausschreibungen für diesen Nutzer.");
													dBox.add(label);
													dBox.center();
													dBox.setAutoHideEnabled(true);
													dBox.show();
												}
											}

											public void onFailure(Throwable caught) {
												DialogBox dBox = new DialogBox();

												Label label = new Label(
														"Es existieren keine Ausschreibungen für diesen Nutzer.");
												dBox.add(label);
												dBox.center();
												dBox.setAutoHideEnabled(true);
												dBox.show();

											}

										});

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

		/*
		 * Ausgabe Report 4 AlleBewerbungen des Nutzers auf Ausschreibungen
		 * 
		 * @author Dominik Sasse
		 */
		userbewerbungenForAusschreibungButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {

				Project4uVerwaltung.getOrganisationseinheitByUser(ClientsideSettings.getAktuellerUser(),
						new AsyncCallback<Organisationseinheit>() {

							public void onSuccess(Organisationseinheit x) {

								ReportVerwaltung.createEigeneBewerbungenReport(x,
										new AsyncCallback<ReportForEigeneBewerbungen>() {
											public void onSuccess(ReportForEigeneBewerbungen c) {

												if (c != null) {
													HTMLReportWriter writer = new HTMLReportWriter();
													writer.process(c);
													RootPanel.get("contentR").clear();
													RootPanel.get("contentR").add(new HTML(writer.getReportText()));
												} else {
													DialogBox dBox = new DialogBox();

													Label label = new Label(
															"Es existieren keine Bewerbungen für diesen Nutzer.");
													dBox.add(label);
													dBox.center();
													dBox.setAutoHideEnabled(true);
													dBox.show();
												}
											}

											public void onFailure(Throwable caught) {
												DialogBox dBox = new DialogBox();

												Label label = new Label(
														"Es existieren keine Bewerbungen für diesen Nutzer.");
												dBox.add(label);
												dBox.center();
												dBox.setAutoHideEnabled(true);
												dBox.show();

											}

										});

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

		/*
		 * Ausgabe Report 5 Projektverflechtungen
		 * 
		 * @author Dominik Sasse
		 */

		// TODO SubReport implementieren (eventuell mit Auswahlmenü für
		// spezifischen Bewerber)

		projektverflechtungenButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {

				Project4uVerwaltung.getOrganisationseinheitByUser(ClientsideSettings.getAktuellerUser(),
						new AsyncCallback<Organisationseinheit>() {

							public void onSuccess(Organisationseinheit orga) {

								ReportVerwaltung.createProjektverflechtungReport(orga,
										new AsyncCallback<ReportByProjektverflechtungen>() {
											public void onSuccess(ReportByProjektverflechtungen c) {

												if (c != null) {
													HTMLReportWriter writer = new HTMLReportWriter();
													writer.process(c);
													RootPanel.get("contentR").clear();
													RootPanel.get("contentR").add(new HTML(writer.getReportText()));
												} else {
													DialogBox dBox = new DialogBox();

													Label label = new Label(
															"Es existieren keine Verflechtungen für diesen Nutzer.");
													dBox.add(label);
													dBox.center();
													dBox.setAutoHideEnabled(true);
													dBox.show();
												}
											}

											public void onFailure(Throwable caught) {
												DialogBox dBox = new DialogBox();

												Label label = new Label(
														"Es existieren keine Verflechtungen für diesen Nutzer.");
												dBox.add(label);
												dBox.center();
												dBox.setAutoHideEnabled(true);
												dBox.show();

											}

										});

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

		/*
		 * Ausgabe Report 6 FanIn FanOut Analyse
		 * 
		 * @author Dominik Sasse
		 */
		fanInFanOutButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {

				ReportVerwaltung.createFanInFanOutReport(new AsyncCallback<FanInFanOut>() {
					public void onSuccess(FanInFanOut c) {

						if (c != null) {
							HTMLReportWriter writer = new HTMLReportWriter();
							writer.process(c);
							RootPanel.get("contentR").clear();
							RootPanel.get("contentR").add(new HTML(writer.getReportText()));
						} else {
							DialogBox dBox = new DialogBox();

							Label label = new Label("Es existieren keine Verflechtungen für diesen Nutzer.");
							dBox.add(label);
							dBox.center();
							dBox.setAutoHideEnabled(true);
							dBox.show();
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

		initWidget(menuReportPanel);
	}

	public void homeButtonClick() {
		homeButton.click();
	}
}
