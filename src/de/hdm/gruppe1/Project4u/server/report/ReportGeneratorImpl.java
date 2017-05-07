package de.hdm.gruppe1.Project4u.server.report;

import java.util.Date;
import java.util.logging.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe1.Project4u.shared.report.CompositeParagraph;
import de.hdm.gruppe1.Project4u.shared.report.Report;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAllAusschreibungen;
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
	Logger log = Logger.getLogger("logger");

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

		CompositeParagraph imprint = new CompositeParagraph();

		imprint.addSubParagraph(new SimpleParagraph("PaarSheep"));

		r.setImprint(imprint);
	}

	/**
	 * Methode um alle Ausschreibungen in einem Report ausgeben zu können
	 */

	public ReportByAllAusschreibungen createReportByAllAusschreibungen(Ausschreibung au)
			throws IllegalArgumentException {

		if (this.getProject4uAdministration() == null)

			return null;

		/**
		 * Leerer Report anlegen
		 */

		ReportByAllAusschreibungen result = new ReportByAllAusschreibungen();

		/**
		 * Titel und Bezeichnung des Reports
		 */

		result.setTitle("Alle Ausschreibungen");

		CompositeParagraph imprint = new CompositeParagraph();

		// Alle Ausschreibungen aus eienr ArrayList auslesen.
		// TODO

		/**
		 * Impressum hinzufuegen
		 */
		result.setImprint(imprint);

		/**
		 * Erstelldatum des Reports hinzufuegen
		 */
		result.setCreated(new Date());

		/**
		 * Kopfdaten des Reports:
		 */
		CompositeParagraph header = new CompositeParagraph();

		/**
		 * Inhalt des Reports ID
		 */
		header.addSubParagraph(new SimpleParagraph("Ausschreibungs-ID: " + au.getAusschreibungID()));

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
		header.addSubParagraph(new SimpleParagraph("Bezeichnung: " + au.getAblaufdatum()));

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

		return result;

	}

}
