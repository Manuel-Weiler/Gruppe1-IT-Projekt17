package de.hdm.gruppe1.Project4u.server.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.hdm.gruppe1.Project4u.shared.report.AllBeteiligungenForNutzer;
import de.hdm.gruppe1.Project4u.shared.report.AllBewerbungenForNutzer;
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
	 * GWT RPC zusätzlich zum No Argument Constructor notwendig.
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

		// weitere Zeilen k�nnen erg�nzt wrden

		// Das eigentliche Hinzufuegen des Impressums zum Report
		r.setImprint(imprint);
	}

	/*
	 * 1. Report um alle Ausschreibungen auszugeben
	 * 
	 * @author: Dominik Sasse
	 * 
	 * @author: Manuel Weiler
	 */

	public ReportByAlleAusschreibungen createAlleAusschreibungenReport() throws IllegalArgumentException {

		if (this.getProject4uAdministration() == null)
			return null;

		// Leeren Report anlegen
		ReportByAlleAusschreibungen report = new ReportByAlleAusschreibungen();

		report.setTitle("Alle Ausschreibungen");

		// Kopfzeile f�r die Tabelle anlegen:
		Row headline = new Row();

		// Kopfzeile soll n Spalten haben mit folgenden Ueberschriften:

		headline.addColumn(new Column("Bezeichnung"));
		headline.addColumn(new Column("Projektleiter"));
		headline.addColumn(new Column("Bewerbungsfrist"));
		headline.addColumn(new Column("Ausschreibungstext"));
		headline.addColumn(new Column("Erstelldatum:"));
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
			// f�r jede Spalte dieser Zeile wird nun der Inhalt geschrieben
			ausschreibungRow.addColumn(new Column(a.getBezeichnung()));
			ausschreibungRow.addColumn(new Column(a.getNameProjektleiter()));
			ausschreibungRow.addColumn(new Column(String.valueOf(a.getBewerbungsfrist())));
			ausschreibungRow.addColumn(new Column(a.getAusschreibungstext()));
			ausschreibungRow.addColumn(new Column(String.valueOf(a.getErstellDatum())));
			ausschreibungRow.addColumn(new Column(String.valueOf(a.getStatus())));

			// Zeile dem Report hinzuf�gen
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
	 */

	public ReportByAusschreibungenForPartnerprofil createAusschreibungenForPartnerprofil(Organisationseinheit orga)
			throws IllegalArgumentException {

		if (project4uAdministration == null)
			return null;
		// Leeren Report anlegen
		ReportByAusschreibungenForPartnerprofil result = new ReportByAusschreibungenForPartnerprofil();

		// Titel und Bezeichung des Reports
		result.setTitle("Meine Ausschreibungen");

		// Kopfzeile f�r die Tabelle anlegen:
		Row headline = new Row();

		// Kopfzeile soll n Spalten haben mit folgenden Ueberschriften:

		headline.addColumn(new Column("Bezeichnung"));
		headline.addColumn(new Column("Projektleiter"));
		headline.addColumn(new Column("Bewerbungsfrist"));
		headline.addColumn(new Column("Ausschreibungstext"));
		headline.addColumn(new Column("Erstelldatum"));
		headline.addColumn(new Column("Status"));

		// Kopfzeile wird dem Report hinzugefuegt
		result.addRow(headline);

		// Reportinhalt:

		// Von den Vektor passendeVektoren muss nun wieder auf Ausschreibungen
		// gekommen werden, damit diese ausgegeben werden k�nnen.

		Vector<Ausschreibung> passendeAusschreibungen = this.project4uAdministration
				.getAusschreibungenForPartnerprofil(orga);

		for (Ausschreibung au : passendeAusschreibungen) {
			// neue, leere Zeile anlegen
			Row ausschreibungRow = new Row();
			// f�r jede Spalte dieser Zeile wird nun der Inhalt geschrieben
			ausschreibungRow.addColumn(new Column(au.getBezeichnung()));
			ausschreibungRow.addColumn(new Column(au.getNameProjektleiter()));
			ausschreibungRow.addColumn(new Column(String.valueOf(au.getBewerbungsfrist())));
			ausschreibungRow.addColumn(new Column(au.getAusschreibungstext()));
			ausschreibungRow.addColumn(new Column(String.valueOf(au.getErstellDatum())));
			ausschreibungRow.addColumn(new Column(String.valueOf(au.getStatus())));

			// Zeile dem Report hinzuf�gen
			result.addRow(ausschreibungRow);
		}
		return result;
	}

	/*
	 * 3. Report welcher alle Bewerbungen auf Ausschreibungen des Benutzers
	 * zur�ckgibt.
	 * 
	 * @author Dominik Sasse
	 * 
	 * @author Ugut Bayrak
	 */

	// Zuerst brauchen wir alle Ausschreibungen des Benutzer welche wir einzeln
	// ausgeben.
	public ReportByAlleBewerbungenForAusschreibungen createAlleBewerbungenForAusschreibungen(Organisationseinheit o)
			throws IllegalArgumentException {

		if (this.getProject4uAdministration() == null)
			return null;

		// Zuerst werden die Ausschreibungen des Nutzers ben�tigt
		Vector<Ausschreibung> aus = project4uAdministration.getAusschreibungenForOrga(o);

		// Leeren Report anlegen
		ReportByAlleBewerbungenForAusschreibungen result = new ReportByAlleBewerbungenForAusschreibungen();

		result.setTitle("Alle Bewerbungen auf eigene Ausschreibungen");

		// F�r jede Ausschreibung soll ein Report mit den Bewerbungen erstellt
		// werden
		for (Ausschreibung au : aus) {

			// Ausschreibungsinformationen angeben
			Row ausschreibungsheadline = new Row();
			ausschreibungsheadline.addColumn(new Column("Ausschreibungsbezeichnung: " + au.getBezeichnung()));
			result.addRow(ausschreibungsheadline);
			
			Row ausschreibungsHeadline1 = new Row();
			ausschreibungsHeadline1.addColumn(new Column("Projektleiter: " + au.getNameProjektleiter()));
			result.addRow(ausschreibungsHeadline1);
			
			Row ausschreibungsheadline2 = new Row();
			ausschreibungsheadline2.addColumn(new Column("Ausschreibungstext: " + au.getAusschreibungstext()));
			result.addRow(ausschreibungsheadline2);

			// Ausschreibungstext hinzuf�gen
			Row ausschreibungstextline = new Row();
			ausschreibungstextline.addColumn(new Column(" "));
			result.addRow(ausschreibungstextline);

			Row ausschreibungstextline1 = new Row();
			ausschreibungstextline1.addColumn(new Column(" "));
			result.addRow(ausschreibungstextline1);
			
			// Nun sollen die dazugeh�rigen Bewerbungen hinzugef�gt werden
			// Kopfzeile f�r die Tabelle anlegen:
			Row headline = new Row();

			// Kopfzeile soll n Spalten haben mit folgenden Ueberschriften:

			headline.addColumn(new Column("Bewerbungstext"));
			headline.addColumn(new Column("Erstelldatum"));
			headline.addColumn(new Column("Status"));

			// Kopfzeile wird dem Report hinzugefuegt
			result.addRow(headline);

			// Reportinhalt:

			Vector<Bewerbung> be = project4uAdministration.getAllBewerbungen();
			Vector<Bewerbung> bewerbungForAusschreibung = new Vector<Bewerbung>();

			for (Bewerbung bew : be) {
				if (bew.getAusschreibungId() == au.getAusschreibungId()) {
					bewerbungForAusschreibung.add(bew);
				}

			}

			for (Bewerbung b : bewerbungForAusschreibung) {
				// neue, leere Zeile anlegen
				Row bewerbungRow = new Row();
				// f�r jede Spalte dieser Zeile wird nun der Inhalt
				// geschrieben
				bewerbungRow.addColumn(new Column(b.getBewerbungstext()));
				
				Row bewerbungRow1 = new Row();
				bewerbungRow1.addColumn(new Column(String.valueOf(b.getErstelldatum())));
				
				Row bewerbungRow2 = new Row();
				bewerbungRow2.addColumn(new Column(b.getStatus()));

				//Leerzeilen anlegen zur Abgrenzung
				Row bewerbungRow3 = new Row();
				bewerbungRow3.addColumn(new Column(" "));
				
				Row bewerbungRow4 = new Row();
				bewerbungRow4.addColumn(new Column(" "));
				
				Row bewerbungRow5 = new Row();
				bewerbungRow5.addColumn(new Column(" "));
				// Zeile dem Report hinzuf�gen
				result.addRow(bewerbungRow);
			}

		}
		return result;
	}

	/*
	 * 4. Abfrage der eigenen Bewerbungen und den zugeh�rigen Ausschreibungen
	 * des Benutzers
	 * 
	 * @author Dominik Sasse
	 */

	// Zuerst m�ssen alle Bewerbungen des Nutzers ausgeeben werden
	public ReportForEigeneBewerbungen createEigeneBewerbungenReport(Organisationseinheit orga)
			throws IllegalArgumentException {

		if (this.getProject4uAdministration() == null)
			return null;

		ReportForEigeneBewerbungen result = new ReportForEigeneBewerbungen();

		result.setTitle("Alle Bewerbungen dieses Nutzers");

		// Kopfzeile f�r die Tabelle anlegen:
		Row headline = new Row();

		// Kopfzeile soll n Spalten haben mit folgenden Ueberschriften:

		headline.addColumn(new Column("Erstelldatum"));
		headline.addColumn(new Column("Bewerbungstext"));
		headline.addColumn(new Column("Status"));
		//leere Spalte einf�gen f�r inhaltliche Trennung
		headline.addColumn(new Column("  "));
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

		// Anschlie�end m�ssen die Ausschreibungen zu diesen Bewerbungen
		// ausgegeben werden.

		for (Bewerbung be : bew) {

			Ausschreibung au = project4uAdministration.findByIdAusschreibung(be.getAusschreibungId());
			Row bewerbungRow = new Row();
			// f�r jede Spalte dieser Zeile wird nun der Inhalt geschrieben
			bewerbungRow.addColumn(new Column(String.valueOf(be.getErstelldatum())));
			bewerbungRow.addColumn(new Column(be.getBewerbungstext()));
			bewerbungRow.addColumn(new Column(String.valueOf(be.getStatus())));
			//leere Spalte f�r inhaltliche Trennung
			bewerbungRow.addColumn(new Column(""));
			// Inhalt Ausschreibung
			bewerbungRow.addColumn(new Column(au.getBezeichnung()));
			bewerbungRow.addColumn(new Column(au.getNameProjektleiter()));
			bewerbungRow.addColumn(new Column(String.valueOf(au.getBewerbungsfrist())));
			bewerbungRow.addColumn(new Column(au.getAusschreibungstext()));
			bewerbungRow.addColumn(new Column(String.valueOf(au.getErstellDatum())));

			// Zeile dem Report hinzuf�gen
			result.addRow(bewerbungRow);
		}

		return result;

	}

	/*
	 * Report 5. Abfrage von Projektverflechtungen (Teilnahmen und weitere
	 * Einreichungen/Bewerbungen) eines Bewerbers durch den Ausschreibenden.
	 * 
	 * @author Dominik Sasse
	 * 
	 * @author Georg Erich
	 */

	// Simple Report Alle Beteiligungen pro Nutzer
	public AllBeteiligungenForNutzer allBeteiligungenForNutzer(Organisationseinheit orga) throws IllegalArgumentException {

		if (this.getProject4uAdministration() == null)
			return null;

		AllBeteiligungenForNutzer result = new AllBeteiligungenForNutzer();

		result.setTitle("Alle Beteiligungen");

		Row headline = new Row();

		headline.addColumn(new Column("Startdatum"));
		headline.addColumn(new Column("Enddatum"));
		headline.addColumn(new Column("Personentage"));

		result.addRow(headline);

		Vector<Beteiligung> allBeteiligungen = project4uAdministration.getBeteiligungForOrga(orga);

		for (Beteiligung be : allBeteiligungen) {

			Row beteiligungsRow = new Row();
			beteiligungsRow.addColumn(new Column(String.valueOf(be.getStartdatum())));
			beteiligungsRow.addColumn(new Column(String.valueOf(be.getEnddatum())));
			beteiligungsRow.addColumn(new Column(String.valueOf(be.getPersonentage())));

			result.addRow(beteiligungsRow);

		}
		return result;
	}

	// SimpleReport Alle Bewerbungen pro Nutzer
	public AllBewerbungenForNutzer allBewerbungenForNutzer(Organisationseinheit orga) throws IllegalArgumentException {

		if (this.getProject4uAdministration() == null)
			return null;

		AllBewerbungenForNutzer result = new AllBewerbungenForNutzer();

		result.setTitle("Alle Bewerbungen ");

		Row headline = new Row();

		headline.addColumn(new Column("Erstelldatum"));
		headline.addColumn(new Column("Bewerbungstext"));
		headline.addColumn(new Column("Status"));

		result.addRow(headline);

		Vector<Bewerbung> allBewerbungen = project4uAdministration.getAllBewerbungen();

		for (Bewerbung be : allBewerbungen) {

			if (be.getOrganisationseinheitId() == orga.getOrganisationseinheitId()) {
				Row beteiligungsRow = new Row();
				beteiligungsRow.addColumn(new Column(String.valueOf(be.getErstelldatum())));
				beteiligungsRow.addColumn(new Column(be.getBewerbungstext()));
				beteiligungsRow.addColumn(new Column(be.getStatus()));

				result.addRow(beteiligungsRow);

			}
		}
		return result;
	}

	// Hier werden nun die 2 SimpleReports zusammengef�gt
	public ReportByProjektverflechtungen createProjektverflechtungReport(Organisationseinheit orga)
			throws IllegalArgumentException {

		if (this.getProject4uAdministration() == null)
			return null;

		ReportByProjektverflechtungen report = new ReportByProjektverflechtungen();

		report.setTitle("Projektverflechtungen");

		report.addSubReport(this.allBeteiligungenForNutzer(orga));
		report.addSubReport(this.allBewerbungenForNutzer(orga));

		return report;
	}

	/*
	 * Report 6 Durchf�hrung einer Fan-in/Fan-out-Analyse: Zu allen Teilnehmern
	 * kann jeweils die Anzahl von Bewerbungen (laufende, abgelehnte,
	 * angenommene) (eine Art Fan-out) und deren Anzahl von Ausschreibungen
	 * (erfolgreich besetzte, abgebrochene, laufende, also Fan-out) tabellarisch
	 * aufgef�hrt werden.
	 * 
	 * @author: Dominik Sasse
	 */

	// Ausgabe der Bewerber mit Status
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

				if (String.valueOf(be.getStatus()).equals("ausstehend")) {
					laufendeBewerbungen.add(be);
				}
				if (String.valueOf(be.getStatus()).equals("abgelehnt")) {
					abgelehnteBewerbungen.add(be);
				}
				if (String.valueOf(be.getStatus()).equals("angenommen")) {
					erfolgreicheBewerbungen.add(be);
				}
			}
			
			Row numbRows = new Row();
			
			numbRows.addColumn(new Column(String.valueOf(orga.getOrganisationseinheitId())));

			if(String.valueOf(orga.getTyp()).equals("Person")){
				numbRows.addColumn(new Column(orga.getName()));
			}

			if(String.valueOf(orga.getTyp()).equals("Team")){
				numbRows.addColumn(new Column(orga.getName()));
			}

			if(String.valueOf(orga.getTyp()).equals("Unternehmen")){
				numbRows.addColumn(new Column(orga.getName()));
			}
			
			numbRows.addColumn(new Column(String.valueOf(laufendeBewerbungen.size())));
			numbRows.addColumn(new Column(String.valueOf(abgelehnteBewerbungen.size())));
			numbRows.addColumn(new Column(String.valueOf(erfolgreicheBewerbungen.size())));
			
			result.addRow(numbRows);
		}

		return result;
	}

	// Ausgabe der Ausschreiber mit Status
	public FanOut createFanOutAnalyseReport() throws IllegalArgumentException {

		if (project4uAdministration == null) {
			return null;
		}

		FanOut result = new FanOut();

		result.setTitle("Anzahl der Ausschreibungen");

		Row headline = new Row();
		headline.addColumn(new Column("ID"));
		headline.addColumn(new Column("Organisationseinheit"));
		headline.addColumn(new Column("laufend"));
		headline.addColumn(new Column("geschlossen"));

		result.addRow(headline);

		Vector<Organisationseinheit> allOrgas = project4uAdministration.getAllOrganisationseinheiten();

		for (Organisationseinheit orga : allOrgas) {
			Vector<Ausschreibung> laufendeAusschreibungen = new Vector<Ausschreibung>();
			Vector<Ausschreibung> besetzteAusschreibungen = new Vector<Ausschreibung>();

			Vector<Ausschreibung> alleAusschreibungen = project4uAdministration.getAusschreibungenForOrga(orga);
			
			for (Ausschreibung au : alleAusschreibungen) {

				if (au.getStatus().toString().equals("laufend")) {
					laufendeAusschreibungen.add(au);
				}
				if (au.getStatus().toString().equals("beendet")) {
					besetzteAusschreibungen.add(au);
				}

			}
			
			Row numbRows = new Row();
			
			numbRows.addColumn(new Column(String.valueOf(orga.getOrganisationseinheitId())));

			if(String.valueOf(orga.getTyp()).equals("Person")){
				numbRows.addColumn(new Column(orga.getName()));
			}

			if(String.valueOf(orga.getTyp()).equals("Team")){
				numbRows.addColumn(new Column(orga.getName()));
			}

			if(String.valueOf(orga.getTyp()).equals("Unternehmen")){
				numbRows.addColumn(new Column(orga.getName()));
			}
			
			numbRows.addColumn(new Column(String.valueOf(laufendeAusschreibungen.size())));
			numbRows.addColumn(new Column(String.valueOf(besetzteAusschreibungen.size())));

			
			result.addRow(numbRows);
		}
		return result;

	}

	public FanInFanOut createFanInFanOutReport() throws IllegalArgumentException {

		if (project4uAdministration == null)
			return null;

		FanInFanOut result = new FanInFanOut();

		result.setTitle("FanIn- FanOut-Analyse");

		
		
		result.addSubReport(this.createFanInAnalyseReport());
		result.addSubReport(this.createFanOutAnalyseReport());

		return result;
	}

}
