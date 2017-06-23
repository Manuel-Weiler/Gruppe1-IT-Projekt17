package de.hdm.gruppe1.Project4u.server.report;

import java.io.Serializable;
import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.Date;
//import java.util.logging.Logger;
import java.util.Vector;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe1.Project4u.shared.report.AllBeteiligungenForNutzer;
import de.hdm.gruppe1.Project4u.shared.report.AllBewerbungenForNutzer;
import de.hdm.gruppe1.Project4u.shared.report.AlleAusschreibungenForBewerbung;
import de.hdm.gruppe1.Project4u.shared.report.Column;
import de.hdm.gruppe1.Project4u.shared.report.CompositeParagraph;
import de.hdm.gruppe1.Project4u.shared.report.FanIn;
import de.hdm.gruppe1.Project4u.shared.report.FanInFanOut;
import de.hdm.gruppe1.Project4u.shared.report.FanOut;
import de.hdm.gruppe1.Project4u.shared.report.Report;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAlleAusschreibungen;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAlleBewerbungenForAusschreibungen;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAusschreibungenForPartnerprofil;
import de.hdm.gruppe1.Project4u.shared.report.ReportByProjektverflechtungen;
import de.hdm.gruppe1.Project4u.shared.report.ReportForEigeneBewerbungen;
import de.hdm.gruppe1.Project4u.shared.report.Row;
import de.hdm.gruppe1.Project4u.shared.report.SimpleParagraph;
import de.hdm.gruppe1.Project4u.server.Project4uAdministrationImpl;

import de.hdm.gruppe1.Project4u.server.db.AusschreibungMapper;
import de.hdm.gruppe1.Project4u.server.db.BewerbungMapper;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministration;
import de.hdm.gruppe1.Project4u.shared.ReportGenerator;

import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Beteiligung;
import de.hdm.gruppe1.Project4u.shared.bo.Bewerbung;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;

/**
 * Implementierung ReportGenerator-Interface
 */

public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator, Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * Reportgenerator braucht Zugriff auf Project4uAdministration
	 */
	private Project4uAdministration project4uAdministration = null;

	/**
	 * No-Argument-Konstruktor.
	 * 
	 * @throws IllegalArgumentException
	 */
	public ReportGeneratorImpl() throws IllegalArgumentException {

	}

	/**
	 * Initialisierung des Objekts. Diese Methode ist vor dem Hintergrund von
	 * GWT RPC zusÃ¤tzlich zum No Argument Constructor notwendig.
	 * 
	 * @throws IllegalArgumentException
	 */

	public void init() throws IllegalArgumentException {
		Project4uAdministrationImpl a = new Project4uAdministrationImpl();
		a.init();
		this.project4uAdministration = a;
	}

	/**
	 * Zugehoerige PartnerboerseAdministration auslesen
	 * 
	 * @return project4uAdministration
	 */

	protected Project4uAdministration getProject4uAdministration() {
		return this.project4uAdministration;
	}

	/**
	 * Die Methode soll dem Report ein Impressum hinzufuegen. Dazu wird
	 * zunaechst ein neuer CompositeParagraph angelegt, da das Impressum
	 * mehrzeilig sein soll. Danach werden belibige SimpleParagraph dem
	 * CompositeParagraph hinzugefuegt. Zum Schluss wird CompositeParagraph dem
	 * Report hinzugefuegt ueber setImprint.
	 * 
	 * @param r
	 *            der um das Impressum zu erweiternde Report.
	 */
	protected void addImprint(Report r) {

		// Das Impressum kann aus mehreren Zeilen bestehen
		CompositeParagraph imprint = new CompositeParagraph();

		// 1.Zeile:
		imprint.addSubParagraph(new SimpleParagraph("Project4u"));

		// weitere Zeilen kï¿½nnen ergï¿½nzt wrden

		// Das eigentliche Hinzufuegen des Impressums zum Report
		r.setImprint(imprint);
	}

	/*
	 * 1. Report um alle Ausschreibungen auszugeben
	 * 
	 * @author: Dominik Sasse
	 */

	public ReportByAlleAusschreibungen createAlleAusschreibungenReport() throws IllegalArgumentException {

		if (this.getProject4uAdministration() == null)
			return null;

		// Leeren Report anlegen
		ReportByAlleAusschreibungen report = new ReportByAlleAusschreibungen();

		report.setTitle("Alle Ausschreibungen");

		// Kopfzeile fï¿½r die Tabelle anlegen:
		Row headline = new Row();

		// Kopfzeile soll n Spalten haben mit folgenden Ueberschriften:

		headline.addColumn(new Column("Ausschreibungs-ID"));
		headline.addColumn(new Column("Bezeichnung"));
		headline.addColumn(new Column("Projektleiter"));
		headline.addColumn(new Column("Bewerbungsfrist"));
		headline.addColumn(new Column("Ausschreibungstext"));
		headline.addColumn(new Column("Erstelldatum:"));
		headline.addColumn(new Column("Projekt-ID"));
		headline.addColumn(new Column("Partnerprofil-ID"));
		headline.addColumn(new Column("Status"));

		// Kopfzeile wird dem Report hinzugefuegt
		report.addRow(headline);

		// Reportinhalt:

		AusschreibungMapper am = AusschreibungMapper.ausschreibungMapper();
		ArrayList<Ausschreibung> au = new ArrayList<Ausschreibung>();
		au = am.findAllAusschreibungen();

		for (Ausschreibung a : au) {
			// neue, leere Zeile anlegen
			Row ausschreibungRow = new Row();
			// fï¿½r jede Spalte dieser Zeile wird nun der Inhalt geschrieben
			ausschreibungRow.addColumn(new Column(String.valueOf(a.getAusschreibungId())));
			ausschreibungRow.addColumn(new Column(a.getBezeichnung()));
			ausschreibungRow.addColumn(new Column(a.getNameProjektleiter()));
			ausschreibungRow.addColumn(new Column(String.valueOf(a.getBewerbungsfrist())));
			ausschreibungRow.addColumn(new Column(a.getAusschreibungstext()));
			ausschreibungRow.addColumn(new Column(String.valueOf(a.getErstellDatum())));
			ausschreibungRow.addColumn(new Column(String.valueOf(a.getProjektId())));
			ausschreibungRow.addColumn(new Column(String.valueOf(a.getPartnerprofilId())));
			ausschreibungRow.addColumn(new Column(String.valueOf(a.getStatus())));

			// Zeile dem Report hinzufï¿½gen
			report.addRow(ausschreibungRow);
		}

		// Report ausgeben
		return report;

	}

	/**
	 * 2. Report um alle Ausschreibungen des aktuellen Nutzer auszugeben
	 * 
	 * @author Dominik Sasse
	 * @author Manuel Weiler
	 * @return der fertige Report
	 */

	public ReportByAusschreibungenForPartnerprofil createAusschreibungenForPartnerprofil(Organisationseinheit orga)
			throws IllegalArgumentException {

		if (project4uAdministration == null)
			return null;
		// Leeren Report anlegen
		ReportByAusschreibungenForPartnerprofil result = new ReportByAusschreibungenForPartnerprofil();

		// Titel und Bezeichung des Reports
		result.setTitle("Meine Ausschreibungen");

		// Kopfzeile fï¿½r die Tabelle anlegen:
		Row headline = new Row();

		// Kopfzeile soll n Spalten haben mit folgenden Ueberschriften:

		headline.addColumn(new Column("Ausschreibungs-ID"));
		headline.addColumn(new Column("Bezeichnung"));
		headline.addColumn(new Column("Projektleiter"));
		headline.addColumn(new Column("Bewerbungsfrist"));
		headline.addColumn(new Column("Ausschreibungstext"));
		headline.addColumn(new Column("Erstelldatum:"));
		headline.addColumn(new Column("Projekt-ID"));
		headline.addColumn(new Column("Partnerprofil-ID"));
		headline.addColumn(new Column("Status"));

		// Kopfzeile wird dem Report hinzugefuegt
		result.addRow(headline);

		// Reportinhalt:

		// Von den Vektor passendeVektoren muss nun wieder auf Ausschreibungen
		// gekommen werden, damit diese ausgegeben werden können.

		Vector<Ausschreibung> passendeAusschreibungen = this.project4uAdministration
				.getAusschreibungenForPartnerprofil(orga);

		for (Ausschreibung au : passendeAusschreibungen) {
			// neue, leere Zeile anlegen
			Row ausschreibungRow = new Row();
			// fï¿½r jede Spalte dieser Zeile wird nun der Inhalt geschrieben
			ausschreibungRow.addColumn(new Column(String.valueOf(au.getAusschreibungId())));
			ausschreibungRow.addColumn(new Column(au.getBezeichnung()));
			ausschreibungRow.addColumn(new Column(au.getNameProjektleiter()));
			ausschreibungRow.addColumn(new Column(String.valueOf(au.getBewerbungsfrist())));
			ausschreibungRow.addColumn(new Column(au.getAusschreibungstext()));
			ausschreibungRow.addColumn(new Column(String.valueOf(au.getErstellDatum())));
			ausschreibungRow.addColumn(new Column(String.valueOf(au.getProjektId())));
			ausschreibungRow.addColumn(new Column(String.valueOf(au.getPartnerprofilId())));
			ausschreibungRow.addColumn(new Column(String.valueOf(au.getStatus())));

			// Zeile dem Report hinzufï¿½gen
			result.addRow(ausschreibungRow);
		}
		return result;
	}

	/*
	 * 3. Report welcher alle Bewerbungen auf Ausschreibungen des Benutzers
	 * zurückgibt.
	 * 
	 * @author Dominik Sasse
	 * 
	 * @author Ugut Bayrak
	 */

	// Zuerst brauchen wir alle Ausschreibungen des Benutzer welche wir einzeln
	// ausgeben. Anschließend
	public ReportByAlleBewerbungenForAusschreibungen createAlleBewerbungenForAusschreibungen(Organisationseinheit o)
			throws IllegalArgumentException {

		if (this.getProject4uAdministration() == null)
			return null;

		// Leeren Report anlegen
		ReportByAlleBewerbungenForAusschreibungen result = new ReportByAlleBewerbungenForAusschreibungen();

		result.setTitle("Alle Bewerbungen auf eigene Ausschreibungen");

		result.setCreated(new Date());

		// Kopfzeile fï¿½r die Tabelle anlegen:
		Row headline = new Row();

		// Kopfzeile soll n Spalten haben mit folgenden Ueberschriften:

		headline.addColumn(new Column("Bewerbungs-ID"));
		headline.addColumn(new Column("Erstelldatum"));
		headline.addColumn(new Column("Bewerbungstext"));
		headline.addColumn(new Column("Ausschreibung-ID"));
		headline.addColumn(new Column("Organisationseinheit-ID"));
		headline.addColumn(new Column("Status"));

		// Kopfzeile wird dem Report hinzugefuegt
		result.addRow(headline);

		// Reportinhalt:

		// Organisationseinheit hat Ausschreibungen mit einer ID
		// Diese AusschreibungsID muss = der AusschreibungsID der Bewerbungen
		// sein.
		Vector<Ausschreibung> aus = project4uAdministration.getAusschreibungenForOrga(o);

		Vector<Bewerbung> be = project4uAdministration.getBewerbungForOrganisationseinheit(o);
		Vector<Bewerbung> bew = new Vector<Bewerbung>();

		for (Bewerbung bewerbung : be) {
			if (bewerbung.getOrganisationseinheitId() == o.getOrganisationseinheitId()) {
				bew.add(bewerbung);
			}
		}

		for (Bewerbung b : bew) {
			// neue, leere Zeile anlegen
			Row bewerbungRow = new Row();
			// fï¿½r jede Spalte dieser Zeile wird nun der Inhalt geschrieben
			bewerbungRow.addColumn(new Column(String.valueOf(b.getBewerbungId())));
			bewerbungRow.addColumn(new Column(String.valueOf(b.getErstelldatum())));
			bewerbungRow.addColumn(new Column(b.getBewerbungstext()));
			bewerbungRow.addColumn(new Column(String.valueOf(b.getAusschreibungId())));
			bewerbungRow.addColumn(new Column(String.valueOf(b.getOrganisationseinheitId())));
			bewerbungRow.addColumn(new Column(b.getStatus()));

			// Zeile dem Report hinzufï¿½gen
			result.addRow(bewerbungRow);
		}
		return result;

	}

	/*
	 * 4. Abfrage der eigenen Bewerbungen und den zugehörigen Ausschreibungen
	 * des Benutzers
	 * 
	 * @author Dominik Sasse
	 */

	// Zuerst müssen alle Bewerbungen des Nutzers ausgeeben werden
	public ReportForEigeneBewerbungen createEigeneBewerbungenReport(Organisationseinheit orga) throws IllegalArgumentException {

		if (this.getProject4uAdministration() == null)
			return null;

		ReportForEigeneBewerbungen result = new ReportForEigeneBewerbungen();

		result.setTitle("Alle Bewerbungen dieses Nutzers");

		// Kopfzeile fï¿½r die Tabelle anlegen:
		Row headline = new Row();

		// Kopfzeile soll n Spalten haben mit folgenden Ueberschriften:

		headline.addColumn(new Column("Bewerbungs-ID"));
		headline.addColumn(new Column("Erstelldatum"));
		headline.addColumn(new Column("Bewerbungstext"));
		headline.addColumn(new Column("Organisationseinheit-ID"));
		headline.addColumn(new Column("Status"));
		headline.addColumn(new Column("Ausschreibung-ID"));
		// Inhalt der Ausschreibung
		headline.addColumn(new Column("Ausschreibungsbezeichnung"));
		headline.addColumn(new Column("Projektleiter der Ausschreibung"));
		headline.addColumn(new Column("Bewerbungsfrist"));
		headline.addColumn(new Column("Ausschreibungstext"));
		headline.addColumn(new Column("Erstelldatum"));

		// Kopfzeile wird dem Report hinzugefuegt
		result.addRow(headline);

		// Reportinhalt:

		Vector<Bewerbung> bew = project4uAdministration.getBewerbungForOrganisationseinheit(orga);

		// Anschließend müssen die Ausschreibungen zu diesen Bewerbungen
		// ausgegeben werden.

		for (Bewerbung be : bew) {

			Ausschreibung au = project4uAdministration.findByIdAusschreibung(be.getAusschreibungId());
			Row bewerbungRow = new Row();
			// fï¿½r jede Spalte dieser Zeile wird nun der Inhalt geschrieben
			bewerbungRow.addColumn(new Column(String.valueOf(be.getBewerbungId())));
			bewerbungRow.addColumn(new Column(String.valueOf(be.getErstelldatum())));
			bewerbungRow.addColumn(new Column(be.getBewerbungstext()));
			bewerbungRow.addColumn(new Column(String.valueOf(be.getOrganisationseinheitId())));
			bewerbungRow.addColumn(new Column(String.valueOf(be.getStatus())));
			bewerbungRow.addColumn(new Column(String.valueOf(be.getAusschreibungId())));
			// Inhalt Ausschreibung

			bewerbungRow.addColumn(new Column(au.getBezeichnung()));
			bewerbungRow.addColumn(new Column(au.getNameProjektleiter()));
			bewerbungRow.addColumn(new Column(String.valueOf(au.getBewerbungsfrist())));
			bewerbungRow.addColumn(new Column(au.getAusschreibungstext()));
			bewerbungRow.addColumn(new Column(String.valueOf(au.getErstellDatum())));

			
			// Zeile dem Report hinzufï¿½gen
			 result.addRow(bewerbungRow);
		}

		return result;

	}

	// Anschließend müssen die Ausschreibungen zu diesen Bewerbungen ausgegeben
	// werden.
	// public AlleAusschreibungenForBewerbung alleAusschreibungenForBewerbung(){
	//
	// if (this.getProject4uAdministration() == null)
	// return null;
	//
	// AlleAusschreibungenForBewerbung result = new
	// AlleAusschreibungenForBewerbung();
	//
	//
	//
	// return result;
	// }

	/*
	 * Abfrage von Projektverflechtungen (Teilnahmen und weitere
	 * Einreichungen/Bewerbungen) eines Bewerbers durch den Ausschreibenden.
	 * 
	 * @author Dominik Sasse
	 * 
	 * @author Georg Erich
	 */

	public AllBeteiligungenForNutzer allBeteiligungenForNutzer(Organisationseinheit orga) {

		if (this.getProject4uAdministration() == null)
			return null;

		AllBeteiligungenForNutzer result = new AllBeteiligungenForNutzer();

		result.setTitle("Alle Beteiligungen");

		Row headline = new Row();

		headline.addColumn(new Column("ProjektId"));
		headline.addColumn(new Column("Startdatum"));
		headline.addColumn(new Column("Enddatum"));
		headline.addColumn(new Column("Personentage"));
		headline.addColumn(new Column("OrganisationsId"));

		result.addRow(headline);

		Vector<Beteiligung> allBeteiligungen = project4uAdministration.getBeteiligungForOrga(orga);

		for (Beteiligung be : allBeteiligungen) {

			Row beteiligungsRow = new Row();
			beteiligungsRow.addColumn(new Column(String.valueOf(be.getProjektId())));
			beteiligungsRow.addColumn(new Column(String.valueOf(be.getStartdatum())));
			beteiligungsRow.addColumn(new Column(String.valueOf(be.getEnddatum())));
			beteiligungsRow.addColumn(new Column(String.valueOf(be.getPersonentage())));
			beteiligungsRow.addColumn(new Column(String.valueOf(be.getOrganisationseinheitId())));

			result.addRow(beteiligungsRow);

		}
		return result;
	}

	public AllBewerbungenForNutzer allBewerbungenForNutzer(Organisationseinheit orga) {

		if (this.getProject4uAdministration() == null)
			return null;

		AllBewerbungenForNutzer result = new AllBewerbungenForNutzer();

		result.setTitle("Alle Bewerbungen ");

		Row headline = new Row();

		headline.addColumn(new Column("iD"));
		headline.addColumn(new Column("Erstelldatum"));
		headline.addColumn(new Column("Bewerbungstext"));
		headline.addColumn(new Column("AusschreibungId"));
		headline.addColumn(new Column("OrganisationsId"));
		headline.addColumn(new Column("Status"));

		result.addRow(headline);

		Vector<Bewerbung> allBewerbungen = project4uAdministration.getBewerbungForOrganisationseinheit(orga);

		for (Bewerbung be : allBewerbungen) {

			Row beteiligungsRow = new Row();
			beteiligungsRow.addColumn(new Column(String.valueOf(be.getBewerbungId())));
			beteiligungsRow.addColumn(new Column(String.valueOf(be.getErstelldatum())));
			beteiligungsRow.addColumn(new Column(be.getBewerbungstext()));
			beteiligungsRow.addColumn(new Column(String.valueOf(be.getAusschreibungId())));
			beteiligungsRow.addColumn(new Column(String.valueOf(be.getOrganisationseinheitId())));
			beteiligungsRow.addColumn(new Column(be.getStatus()));

			result.addRow(beteiligungsRow);

		}
		return result;
	}

	public ReportByProjektverflechtungen createProjektverflechtungReport(Organisationseinheit orga)
			throws IllegalArgumentException {

		if (this.getProject4uAdministration() == null)
			return null;

		ReportByProjektverflechtungen report = new ReportByProjektverflechtungen();

		report.setTitle("Projektverflechtungen");

		report.addSubReport(this.allBeteiligungenForNutzer(orga));
		report.addSubReport(this.allBewerbungenForNutzer(orga));

		// Report ausgeben
		return report;
	}

	public FanIn createFanInAnalyseReport() throws IllegalArgumentException {

		if (project4uAdministration == null)
			return null;

		FanIn result = new FanIn();

		result.setTitle("Anzahl der Bewerbungen");

		Row headline = new Row();
		headline.addColumn(new Column("ID"));
		headline.addColumn(new Column("Organisationseinheit"));
		headline.addColumn(new Column("laufend"));
		headline.addColumn(new Column("abgelehnt"));
		headline.addColumn(new Column("angenommen"));

		result.addRow(headline);

		Vector<Organisationseinheit> allOrgas = project4uAdministration.getAllOrganisationseinheiten();

		for (Organisationseinheit orga : allOrgas) {
			Vector<Bewerbung> laufendeBewerbungen = new Vector<Bewerbung>();
			Vector<Bewerbung> abgelehnteBewerbungen = new Vector<Bewerbung>();
			Vector<Bewerbung> erfolgreicheBewerbungen = new Vector<Bewerbung>();

			Vector<Bewerbung> alleBewerbungen = project4uAdministration.getBewerbungForOrganisationseinheit(orga);

			for (Bewerbung be : alleBewerbungen) {

				// TODO: Status der Bewerbungen abrufen (laufend, abgelehnt,
				// erfolgreich) sobald diese implementiert sind.
			}
		}

		return result;
	}

	public FanOut createFanOutAnalyseReport() throws IllegalArgumentException {

		if (project4uAdministration == null) {
			return null;
		}

		FanOut result = new FanOut();

		result.setTitle("Anzahl der Ausschreibungen");

		Row headline = new Row();
		headline.addColumn(new Column("ID"));
		headline.addColumn(new Column("Organisationseinheit"));
		headline.addColumn(new Column("geschlossen"));
		// headline.addColumn(new Column("abgebrochen"));
		headline.addColumn(new Column("laufend"));

		result.addRow(headline);

		Vector<Organisationseinheit> allOrgas = project4uAdministration.getAllOrganisationseinheiten();

		for (Organisationseinheit orga : allOrgas) {
			Vector<Ausschreibung> laufendeAusschreibungen = new Vector<Ausschreibung>();
			Vector<Ausschreibung> abgebrocheneAusschreibungen = new Vector<Ausschreibung>();
			Vector<Ausschreibung> besetzteAusschreibungen = new Vector<Ausschreibung>();

			Vector<Ausschreibung> alleAusschreibungen = project4uAdministration.getAusschreibungenForOrga(orga);
		}
		return result;

	}

	public FanInFanOut createFanInFanOutReport() throws IllegalArgumentException {

		if (project4uAdministration == null)
			return null;

		FanInFanOut result = new FanInFanOut();

		result.setTitle("FanIn- FanOut-Analyse");

		result.addSubReport(this.createFanInAnalyseReport());
		result.addSubReport(this.createFanInFanOutReport());

		return result;
	}

}
