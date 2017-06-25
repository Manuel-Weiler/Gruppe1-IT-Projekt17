package de.hdm.gruppe1.Project4u.client.gui;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Bewerbung;
import de.hdm.gruppe1.Project4u.shared.bo.Bewertung;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;
import de.hdm.gruppe1.Project4u.shared.bo.Projektmarktplatz;

public class AusgangsbewerbungenWidget extends Composite {

	Project4uAdministrationAsync Project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();
	VerticalPanel vp = new VerticalPanel();
	VerticalPanel vep = new VerticalPanel();
	Vector<Bewerbung> bew = new Vector<>();
	Bewerbung clickedBewerbung = new Bewerbung();
	HTML headingUserBew = new HTML("<p class='heading'>Alle Bewerbungen Ihres perönlichen Profils '"
			+ ClientsideSettings.getAktuellerUser().getEmailAddress() + "'</p>");
	HTML headingOrgaBew = new HTML(
			"<p id='heading'>Bewerbungen der Teams und Unternehmen, der Sie zugehörig sind: </p>");
	DialogBox box = new DialogBox();
	HorizontalPanel details = new HorizontalPanel();
	HorizontalPanel buttons = new HorizontalPanel();
	Button close = new Button("Schließen");
	Button delete = new Button("Bewerbung zurückziehen");
	SimplePager pagerLinkedBewerbungen = new SimplePager(TextLocation.CENTER, false, 0, false);
	SimplePager pagerUserbewerbungen = new SimplePager(TextLocation.CENTER, false, 0, false);

	/*
	 * Der Key-Provider vergibt jedem Objekt der Tabelle eine Id, damit auch
	 * einzelne Objekte der in der Liste weiter verarbeitet werden k�nnen.
	 */

	public static final ProvidesKey<Bewerbung> KEY_PROVIDER = new ProvidesKey<Bewerbung>() {
		public Object getKey(Bewerbung item) {
			return item == null ? null : item.getBewerbungId();
		}
	};

	CellTable<Bewerbung> userBewerbungen = new CellTable<Bewerbung>(KEY_PROVIDER);
	CellTable<Bewerbung> linkedBewerbungen = new CellTable<Bewerbung>(KEY_PROVIDER);

	public AusgangsbewerbungenWidget() {
		RootPanel.get("contentHeader").clear();
		RootPanel.get("contentHeader").add(new Label("Ausgangsbewerbungen"));

		Project4uVerwaltung.getAllBewerbungenOfUser(ClientsideSettings.getAktuellerUser(),
				new AsyncCallback<Vector<Bewerbung>>() {

					@Override
					public void onSuccess(Vector<Bewerbung> result) {
						bew = result;
						vp.add(headingUserBew);
						vp.add(createTableOfUserbewerbungen(result));
						vp.add(pagerUserbewerbungen);

						Project4uVerwaltung.getAllBewerbungenOfLinkedTeamAndUnternehmen(
								ClientsideSettings.getAktuellerUser(), new AsyncCallback<Vector<Bewerbung>>() {

							@Override
							public void onSuccess(Vector<Bewerbung> linkedBewerbungen) {

								vp.add(headingOrgaBew);
								vp.add(createTableOfLinkedOrgabewerbungen(linkedBewerbungen));
								vp.add(pagerLinkedBewerbungen);

								RootPanel.get("content").clear();
								RootPanel.get("content").add(vp);

							}

							@Override
							public void onFailure(Throwable caught) {
							}
						});

					}

					public void onFailure(Throwable caught) {
					}
				});

		delete.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Project4uVerwaltung.deleteBewerbung(clickedBewerbung, new AsyncCallback<Void>() {

					@Override
					public void onSuccess(Void result) {
						MessageBox.alertWidget("Löschen Bewerbung", "Ihre Bewerbung wurde erfolgreich gelöscht!");
						details.clear();
						box.clear();
						box.hide();
						RootPanel.get("content").clear();
						new AusgangsbewerbungenWidget();
					}

					@Override
					public void onFailure(Throwable caught) {
					}
				});

			}
		});

		close.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				details.clear();
				box.clear();
				box.hide();

			}
		});

	}



	private CellTable<Bewerbung> createTableOfUserbewerbungen(Vector<Bewerbung> bewerbungen) {

		// Die Spalten der Bewerbung-Tabelle werden erstellt und deren
		// Inhalt definiert.

		TextColumn<Bewerbung> projekt = new TextColumn<Bewerbung>() {
			public String getValue(Bewerbung object) {
				return object.getProjektname();
			}
		};

		TextColumn<Bewerbung> ausschreibung = new TextColumn<Bewerbung>() {
			public String getValue(Bewerbung object) {
				return object.getAusschreibungsname();
			}
		};

		DateCell datecell = new DateCell();
		Column<Bewerbung, Date> erstelldatum = new Column<Bewerbung, Date>(datecell) {

			@Override
			public Date getValue(Bewerbung object) {
				return object.getErstelldatum();
			}
		};

		TextColumn<Bewerbung> status = new TextColumn<Bewerbung>() {
			public String getValue(Bewerbung object) {

				return object.getStatus();
			}
			public String getCellStyleNames(Context context, Bewerbung object) {
				if(object.getStatus().equalsIgnoreCase("angenommen")){
					return "green";
				}
				else if(object.getStatus().equalsIgnoreCase("abgelehnt")){
					return "rot";
				}
				else {
					return super.getCellStyleNames(context, object);	
					}
				
			}
		};

		ButtonCell buttCell = new ButtonCell();
		Column<Bewerbung, String> bewertungColumn = new Column<Bewerbung, String>(buttCell) {
			@Override
			public String getValue(Bewerbung bewerbung) {
				// The value to display in the button.

				return "Bewertung einsehen";
			}
		};

		bewertungColumn.setFieldUpdater(new bewertungAnsehenButtonFieldUpdater());

		ButtonCell buttonCell = new ButtonCell();
		Column<Bewerbung, String> buttonColumn = new Column<Bewerbung, String>(buttonCell) {
			@Override
			public String getValue(Bewerbung bewerbung) {
				// The value to display in the button.
				return "Bewerbungsdetails";
			}
		};

		// You can then set a FieldUpdater on the Column to be notified of
		// clicks.

		buttonColumn.setFieldUpdater(new detailButtonFieldUpdater());

		/*
		 * Hinzuf�gen der Spalten zur Tabelle, in der Reihenfolge von Links nach
		 * Rechts. Definition der Spaltennamen.
		 */

		userBewerbungen.addColumn(projekt, "Projekt");
		userBewerbungen.addColumn(ausschreibung, "Ausschreibung");
		userBewerbungen.addColumn(erstelldatum, "Erstelldatum");
		userBewerbungen.addColumn(status, "Status");
		userBewerbungen.addColumn(bewertungColumn);
		userBewerbungen.addColumn(buttonColumn);

		

		userBewerbungen.setRowCount(bewerbungen.size());

		userBewerbungen.setRowData(bewerbungen);
		
		
		
		/*
		 * Der DataListProvider ermöglicht zusammen mit dem SimplePager die Anzeige der 
		 * Daten über mehere Seiten hinweg
		 */
		ListDataProvider<Bewerbung> dataProvider = new ListDataProvider<Bewerbung>();
	    dataProvider.addDataDisplay(userBewerbungen);
	    dataProvider.setList(bewerbungen);
	    
	    
		
	    pagerUserbewerbungen.setDisplay(userBewerbungen);
	    pagerUserbewerbungen.setPageSize(10);
	    
	    userBewerbungen.setWidth("100%");



		return userBewerbungen;
	}
	
	
	

	private CellTable<Bewerbung> createTableOfLinkedOrgabewerbungen(Vector<Bewerbung> bewerbungen) {

		// Die Spalten der Bewerbung-Tabelle werden erstellt und deren
		// Inhalt definiert.

		TextColumn<Bewerbung> projekt = new TextColumn<Bewerbung>() {
			public String getValue(Bewerbung object) {
				return object.getProjektname();
			}
		};

		TextColumn<Bewerbung> ausschreibung = new TextColumn<Bewerbung>() {
			public String getValue(Bewerbung object) {
				return object.getAusschreibungsname();
			}
		};

		DateCell datecell = new DateCell();
		Column<Bewerbung, Date> erstelldatum = new Column<Bewerbung, Date>(datecell) {

			@Override
			public Date getValue(Bewerbung object) {
				return object.getErstelldatum();
			}
		};

		TextColumn<Bewerbung> status = new TextColumn<Bewerbung>() {
			public String getValue(Bewerbung object) {
				return object.getStatus();
			}
			
			@Override
			public String getCellStyleNames(Context context, Bewerbung object) {
				if(object.getStatus().equalsIgnoreCase("angenommen")){
					return "green";
				}
				else if(object.getStatus().equalsIgnoreCase("abgelehnt")){
					return "rot";
				}
				else {
					return super.getCellStyleNames(context, object);	
					}
				
			}
			
		};

		ButtonCell buttCell = new ButtonCell();
		Column<Bewerbung, String> bewertungColumn = new Column<Bewerbung, String>(buttCell) {
			@Override
			public String getValue(Bewerbung bewerbung) {
				// The value to display in the button.

				return "Bewertung einsehen";
			}
		};

		bewertungColumn.setFieldUpdater(new bewertungAnsehenButtonFieldUpdater());

		ButtonCell buttonCell = new ButtonCell();
		Column<Bewerbung, String> buttonColumn = new Column<Bewerbung, String>(buttonCell) {
			@Override
			public String getValue(Bewerbung bewerbung) {
				// The value to display in the button.
				return "Details";
			}
		};

		// You can then set a FieldUpdater on the Column to be notified of
		// clicks.

		buttonColumn.setFieldUpdater(new detailButtonFieldUpdater());

		/*
		 * Hinzuf�gen der Spalten zur Tabelle, in der Reihenfolge von Links nach
		 * Rechts. Definition der Spaltennamen.
		 */

		linkedBewerbungen.addColumn(projekt, "Projekt");
		linkedBewerbungen.addColumn(ausschreibung, "Ausschreibung");
		linkedBewerbungen.addColumn(erstelldatum, "Erstelldatum");
		linkedBewerbungen.addColumn(status, "Status");
		linkedBewerbungen.addColumn(bewertungColumn);
		linkedBewerbungen.addColumn(buttonColumn);

		linkedBewerbungen.setRowCount(bewerbungen.size());

		linkedBewerbungen.setRowData(bewerbungen);
		
		
		
		linkedBewerbungen.setRowData(bewerbungen);

		
		/*
		 * Der DataListProvider ermöglicht zusammen mit dem SimplePager die Anzeige der 
		 * Daten über mehere Seiten hinweg
		 */
		ListDataProvider<Bewerbung> dataProvider = new ListDataProvider<Bewerbung>();
	    dataProvider.addDataDisplay(linkedBewerbungen);
	    dataProvider.setList(bewerbungen);
	    
	    
		
	    pagerLinkedBewerbungen.setDisplay(linkedBewerbungen);
	    pagerLinkedBewerbungen.setPageSize(10);
	    
	    linkedBewerbungen.setWidth("100%");

		

		return linkedBewerbungen;
	}

	private class bewertungAnsehenButtonFieldUpdater implements FieldUpdater<Bewerbung, String> {

		@Override
		public void update(int index, Bewerbung object, String value) {
			
			if (object.getStatus().equalsIgnoreCase("angenommen") || object.getStatus().equalsIgnoreCase("abgelehnt")) {

				clickedBewerbung = object;

				Project4uVerwaltung.getBewertungOfBewerbung(object, new AsyncCallback<Bewertung>() {

					@Override
					public void onSuccess(Bewertung result) {
						if (result!=null){
						
						BewertungWidget bw = new BewertungWidget(clickedBewerbung);
						
						bw.setViewModusOn(result);
						bw.show();
						}
						else{Window.alert("Fehler, keine Bewertung vorhanden");}

					}

					public void onFailure(Throwable caught) {
					}
				});

			}
			else{
				MessageBox.alertWidget("Keine Bewertung verfügbar", "Eine Bewertung liegt erst vor, wenn die Bewerbung </br>den Satus 'angenommen' oder 'abgelehnt' hat");
			}
		}

	}

	private class detailButtonFieldUpdater implements FieldUpdater<Bewerbung, String> {

		@Override
		public void update(int index, final Bewerbung object, String value) {
			clickedBewerbung = object;

			Project4uVerwaltung.findByIdAusschreibung(object.getAusschreibungId(), new AsyncCallback<Ausschreibung>() {

				@Override
				public void onSuccess(Ausschreibung result) {

					OrganisationseinheitProfilAnzeigeWidget profil = new OrganisationseinheitProfilAnzeigeWidget(
							object.getOrganisationseinheitId());
					BewerbungWidget bewerbung = new BewerbungWidget(result);
					bewerbung.setAllDisabled(object);

					details.add(profil.getVP());
					details.add(bewerbung.getVP());
					vep.add(details);
					buttons.add(close);
					if (object.getStatus().equalsIgnoreCase("angenommen")||object.getStatus().equalsIgnoreCase("abgelehnt")){
						delete.setVisible(false);
					}
					buttons.add(delete);
					vep.add(buttons);
					box.setText("Bewerbungsübersicht");
					box.add(vep);
					box.setPopupPositionAndShow(new PositionCallback() {

						@Override
						public void setPosition(int offsetWidth, int offsetHeight) {
							box.setPopupPosition(Window.getClientWidth() / 2 - (box.getOffsetWidth()),
									Window.getClientHeight() / 4);

						}
					});

				}

				@Override
				public void onFailure(Throwable caught) {
				}
			});
		}
	}

}
