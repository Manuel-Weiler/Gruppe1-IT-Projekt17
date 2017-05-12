package de.hdm.gruppe1.Project4u.server.report;

import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.Date;
//import java.util.logging.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe1.Project4u.shared.report.Column;
import de.hdm.gruppe1.Project4u.shared.report.CompositeParagraph;
import de.hdm.gruppe1.Project4u.shared.report.Report;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAllAusschreibungen;
import de.hdm.gruppe1.Project4u.shared.report.Row;
import de.hdm.gruppe1.Project4u.shared.report.SimpleParagraph;
import de.hdm.gruppe1.Project4u.server.Project4uAdministrationImpl;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministration;
import de.hdm.gruppe1.Project4u.shared.ReportGenerator;
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;

/**
 * Implementierung ReportGenerator-Interface
 */

@SuppressWarnings("serial")
public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator {
	//Logger log = Logger.getLogger("logger");

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
	
	@Override
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

		
		//Das Impressum kann aus mehreren Zeilen bestehen
		CompositeParagraph imprint = new CompositeParagraph();
		
		//1.Zeile:
		imprint.addSubParagraph(new SimpleParagraph("Project4u"));
		
		//weitere Zeilen können ergänzt wrden
		
		//Das eigentliche Hinzufuegen des Impressums zum Report
		r.setImprint(imprint);
	}

	/**
	 * Methode um alle Ausschreibungen in einem Report ausgeben zu können
	 * @return der fertige Report
	 */

	@Override
	public ReportByAllAusschreibungen createAllAusschreibungenReport(Ausschreibung au) 
			throws IllegalArgumentException {

		if (this.getProject4uAdministration() == null)
			return null;

		/**
		 * Leeren Report anlegen
		 */

		ReportByAllAusschreibungen result = new ReportByAllAusschreibungen();

		/**
		 * Titel und Bezeichnung des Reports
		 */

		result.setTitle("Alle Ausschreibungen");
		
		//Impressum hinzufuegen
		this.addImprint(result);

		/*
		 * Erstelldatum des Reports hinzufuegen
		 */
		result.setCreated(new Date());

		
		//Kopfdaten des Reports:

		CompositeParagraph header = new CompositeParagraph();
		header.addSubParagraph(new SimpleParagraph("Hier sehen Sie alle Ausschreibungen der Projektplattform"));
		
		//Kopfdaten zum Report hinzufügen
		result.setHeaderData(header);

		/**
		 * Inhalt des Reports ID
		 */
		header.addSubParagraph(new SimpleParagraph("Ausschreibungs-ID: " + au.getAusschreibungId()));

		/**
		 * Inhalt des Reports Bezeichnung
		 */
		header.addSubParagraph(new SimpleParagraph("Bezeichnung: " + au.getBezeichnung()));

		/**
		 * Inhalt des Reports Projektleiter
		 */
		header.addSubParagraph(new SimpleParagraph("Projektleiter: "));

		/**
		 * Inhalt des Reports Bewerbungsfrist/ Ablaufdatum
		 */
		header.addSubParagraph(new SimpleParagraph("Bezeichnung: " + au.getBewerbungsfrist()));

		/**
		 * Inhalt des Reports Ausschreibungstext
		 */
		header.addSubParagraph(new SimpleParagraph("Ausschreibungstext: " + au.getAusschreibungstext()));

		/**
		 * Inhalt des Reports Erstelldatum
		 */
		header.addSubParagraph(new SimpleParagraph("Erstelldatum: "));

		/**
		 * Inhalt des Reports Projekt-ID
		 */
		header.addSubParagraph(new SimpleParagraph("Projekt-ID: "));

		/**
		 * Inhalt des Reports Partnerprofil-ID
		 */
		header.addSubParagraph(new SimpleParagraph("Partnerprofil-ID: "));

		/**
		 * Report ausgeben
		 */

		
		//Kopfzeile für die Tabelle anlegen:
		Row headline = new Row();
		
		//Kopfzeile soll n Spalten haben mit folgenden Ueberschriften:
		headline.addColumn(new Column("Ausschreibungs-ID"));
		headline.addColumn(new Column("Bezeichnung"));
		headline.addColumn(new Column("Projektleiter"));
		headline.addColumn(new Column("Bewerbungsfrist"));
		headline.addColumn(new Column("Ausschreibungstext"));
		headline.addColumn(new Column("Erstelldatum:"));
		headline.addColumn(new Column("Projekt-ID"));
		headline.addColumn(new Column("Partnerprofil-ID"));
		
		//Kopfzeile wird dem Report hinzugefuegt
		result.addRow(headline);
		
		//Reportinhalt:
		ArrayList<Ausschreibung> allAusschreibungen = this.project4uAdministration.getAllAusschreibungen();
		
		for(Ausschreibung a : allAusschreibungen){
			//neue, leere Zeile anlegen
			Row ausschreibungRow = new Row();
			//für jede Spalte dieser Zeile wird nun der Inhalt geschrieben
			ausschreibungRow.addColumn(new Column(String.valueOf(a.getAusschreibungId())));
			ausschreibungRow.addColumn(new Column(String.valueOf(a.getBezeichnung())));
			//TODO ausschreibungRow.addColumn(new Column(String.valueOf(a.getProjektleiter())));
			ausschreibungRow.addColumn(new Column(String.valueOf(a.getBewerbungsfrist())));
			ausschreibungRow.addColumn(new Column(String.valueOf(a.getAusschreibungstext())));
			//TODO ausschreibungRow.addColumn(new Column(String.valueOf(a.getErstelldatum())));
			//TODO ausschreibungRow.addColumn(new Column(String.valueOf(a.getProjektID())));
			//TODO ausschreibungRow.addColumn(new Column(String.valueOf(a.getPartnerprofilID())));
			
			//Zeile dem Report hinzufügen
			result.addRow(ausschreibungRow);
		}
		
		//Report ausgeben
		return result;

	}

}
