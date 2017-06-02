package de.hdm.gruppe1.Project4u.server.report;

import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.Date;
//import java.util.logging.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe1.Project4u.shared.report.Column;
import de.hdm.gruppe1.Project4u.shared.report.CompositeParagraph;
import de.hdm.gruppe1.Project4u.shared.report.Report;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAlleAusschreibungen;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAusschreibungenForPartnerprofil;
import de.hdm.gruppe1.Project4u.shared.report.Row;
import de.hdm.gruppe1.Project4u.shared.report.SimpleParagraph;
import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.server.Project4uAdministrationImpl;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministration;
import de.hdm.gruppe1.Project4u.shared.ReportGenerator;
import de.hdm.gruppe1.Project4u.shared.ReportGeneratorAsync;
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
	 * GWT RPC zusätzlich zum No Argument Constructor notwendig.
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
		
		//weitere Zeilen k�nnen erg�nzt wrden
		
		//Das eigentliche Hinzufuegen des Impressums zum Report
		r.setImprint(imprint);
	}

	/**
	 * Methode um alle Ausschreibungen in einem Report ausgeben zu k�nnen
	 * @return der fertige Report
	 */

	@Override
	public ReportByAlleAusschreibungen createAlleAusschreibungenReport() 
			throws IllegalArgumentException {

		if (this.getProject4uAdministration() == null)
			return null;

		/**
		 * Leeren Report anlegen
		 */

		ReportByAlleAusschreibungen result = new ReportByAlleAusschreibungen();

		/**
		 * Titel und Bezeichnung des Reports
		 */

		result.setTitle(" ");
		
		this.addImprint(result);
		/*
		 * Erstelldatum des Reports hinzufuegen
		 */
		result.setCreated(new Date());

		
		//Kopfdaten des Reports:

		CompositeParagraph header = new CompositeParagraph();
		header.addSubParagraph(new SimpleParagraph("Hier sehen Sie alle Ausschreibungen der Projektplattform"));
		
		//Kopfdaten zum Report hinzuf�gen
		result.setHeaderData(header);

		/**
//		 * TODO: Sobald der Login implementiert ist kann ein Nutzer identifiziert werden
//		 * und in einem Header nochmal �ber dem Report ausgegeben werden.
//		 */
		header.addSubParagraph(new SimpleParagraph("Nutzer-ID:"));
//
//

		/**
		 * Report ausgeben
		 */

		
		//Kopfzeile f�r die Tabelle anlegen:
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
		ArrayList<Ausschreibung> alleAusschreibungen = this.project4uAdministration.getAlleAusschreibungen();
		
		for(Ausschreibung a : alleAusschreibungen){
			//neue, leere Zeile anlegen
			Row ausschreibungRow = new Row();
			//f�r jede Spalte dieser Zeile wird nun der Inhalt geschrieben
			ausschreibungRow.addColumn(new Column(String.valueOf(a.getAusschreibungId())));
			ausschreibungRow.addColumn(new Column(String.valueOf(a.getBezeichnung())));
			ausschreibungRow.addColumn(new Column(String.valueOf(a.getNameProjektleiter())));
			ausschreibungRow.addColumn(new Column(String.valueOf(a.getBewerbungsfrist())));
			ausschreibungRow.addColumn(new Column(String.valueOf(a.getAusschreibungstext())));
			ausschreibungRow.addColumn(new Column(String.valueOf(a.getErstellDatum())));
			ausschreibungRow.addColumn(new Column(String.valueOf(a.getProjektId())));
			ausschreibungRow.addColumn(new Column(String.valueOf(a.getPartnerprofilId())));
			
			//Zeile dem Report hinzuf�gen
			result.addRow(ausschreibungRow);
		}
		
		//Report ausgeben
		return result;

	}
	
	@Override
	public ReportByAusschreibungenForPartnerprofil createAusschreibungenForPartnerprofil(Ausschreibung au)
			throws IllegalArgumentException{
		
		if (this.getProject4uAdministration() == null)
			return null;
		/**
		 * leeren Report anlegen
		 */
		
		ReportByAusschreibungenForPartnerprofil result = new ReportByAusschreibungenForPartnerprofil();
		
		/**
		 * Titel und Bezeichung des Reports
		 */
		result.setTitle("Meine Ausschreibungen");
		
		//Impressum hinzufuegen
		result.setCreated(new Date());
		
		//Kopfdaten des Reports
		
		CompositeParagraph header = new CompositeParagraph();
		
		header.addSubParagraph(new SimpleParagraph("Hier sehen Sie alle Ausschreibungen die Ihrerm Partnerprofil entsprechen"));
		
		//Kopfdaten zu Report hinzuf�gen
		result.setHeaderData(header);
		
		/**
		 * Inhalt des Reports ID
		 */
		
		header.addSubParagraph(new SimpleParagraph("Ausschreibungs-ID: " + au.getAusschreibungId()));
		
		/**
		 * Inhalt des Reports Bezeichung
		 */
		header.addSubParagraph(new SimpleParagraph("Bezeichnung: " + au.getBezeichnung()));
	
		
		return result;
				
	}


}
