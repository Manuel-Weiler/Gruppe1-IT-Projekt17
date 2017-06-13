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

import de.hdm.gruppe1.Project4u.shared.report.Column;
import de.hdm.gruppe1.Project4u.shared.report.CompositeParagraph;
import de.hdm.gruppe1.Project4u.shared.report.Report;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAlleAusschreibungen;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAusschreibungenForPartnerprofil;
import de.hdm.gruppe1.Project4u.shared.report.Row;
import de.hdm.gruppe1.Project4u.shared.report.SimpleParagraph;
import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.server.Project4uAdministrationImpl; //TODO Methoden ändern auf ReportGenerator Impl
import de.hdm.gruppe1.Project4u.server.db.AusschreibungMapper;
import de.hdm.gruppe1.Project4u.server.db.EigenschaftMapper;
import de.hdm.gruppe1.Project4u.server.db.OrganisationseinheitMapper;
import de.hdm.gruppe1.Project4u.server.db.PartnerprofilMapper;
import de.hdm.gruppe1.Project4u.shared.LoginInfo;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministration;
import de.hdm.gruppe1.Project4u.shared.ReportGenerator;
import de.hdm.gruppe1.Project4u.shared.ReportGeneratorAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Eigenschaft;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;

/**
 * Implementierung ReportGenerator-Interface
 */

@SuppressWarnings("serial")
public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator, Serializable {
	// Logger log = Logger.getLogger("logger");

	/**
	 * Reportgenerator braucht Zugriff auf Project4uAdministration
	 */
	//ReportGeneratorAsync ReportVerwaltung = ClientsideSettings.getReportVerwaltung();

	private ReportGenerator reportGenerator = null;
	private Project4uAdministration project4uAdministration = null;
	private OrganisationseinheitMapper organisationseinheitMapper = null;
	private PartnerprofilMapper partnerprofilMapper = null;
	//private AusschreibungMapper ausschreibungMapper = null;


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

	/**
	 * Methode um alle Ausschreibungen in einem Report ausgeben zu kï¿½nnen
	 * 
	 * @return der fertige Report
	 */

	public ReportByAlleAusschreibungen createAlleAusschreibungenReport() throws IllegalArgumentException {

		if (this.getProject4uAdministration() == null)
			return null;

		/**
		 * Leeren Report anlegen
		 */
		ReportByAlleAusschreibungen report = new ReportByAlleAusschreibungen();

		/**
		 * Titel und Bezeichnung des Reports
		 */
		report.setTitle(" ");

		// this.addImprint(result);

		/*
		 * Erstelldatum des Reports hinzufuegen
		 */
		// result.setCreated(new Date());

		// Kopfdaten des Reports:

		// CompositeParagraph header = new CompositeParagraph();
		// header.addSubParagraph(new SimpleParagraph("Hier sehen Sie alle
		// Ausschreibungen der Projektplattform"));

		// Kopfdaten zum Report hinzufï¿½gen
		// result.setHeaderData(header);

		/**
		 * // * TODO: Sobald der Login implementiert ist kann ein Nutzer
		 * identifiziert werden // * und in einem Header nochmal ï¿½ber dem
		 * Report ausgegeben werden. //
		 */
		// header.addSubParagraph(new SimpleParagraph("Nutzer-ID:"));
		//
		//

		/**
		 * Report ausgeben
		 */

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

			// Zeile dem Report hinzufï¿½gen
			report.addRow(ausschreibungRow);
		}

		// Report ausgeben
		return report;

	}

	public ReportByAusschreibungenForPartnerprofil createAusschreibungenForPartnerprofil(LoginInfo login)
			throws IllegalArgumentException {


		if (this.getProject4uAdministration() == null)
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

		// Kopfzeile wird dem Report hinzugefuegt
		result.addRow(headline);

		// Reportinhalt:
		
		
		//Von den Vektor passendeVektoren muss nun wieder auf Ausschreibungen gekommen werden, damit diese ausgegeben werden können.	
		
		
		//Ausschreibung a = ausschreibungMapper.findByIdAusschreibung(2);
		AusschreibungMapper au = new AusschreibungMapper();
			// neue, leere Zeile anlegen
			Row ausschreibungRow = new Row();
			// fï¿½r jede Spalte dieser Zeile wird nun der Inhalt geschrieben
			ausschreibungRow.addColumn(new Column(String.valueOf(au.findByIdAusschreibung(2).getAusschreibungId())));
			ausschreibungRow.addColumn(new Column(au.findByIdAusschreibung(2).getBezeichnung()));
			ausschreibungRow.addColumn(new Column(au.findByIdAusschreibung(2).getNameProjektleiter()));
			ausschreibungRow.addColumn(new Column(String.valueOf(au.findByIdAusschreibung(2).getBewerbungsfrist())));
			ausschreibungRow.addColumn(new Column(au.findByIdAusschreibung(2).getAusschreibungstext()));
			ausschreibungRow.addColumn(new Column(String.valueOf(au.findByIdAusschreibung(2).getErstellDatum())));
			ausschreibungRow.addColumn(new Column(String.valueOf(au.findByIdAusschreibung(2).getProjektId())));
			ausschreibungRow.addColumn(new Column(String.valueOf(au.findByIdAusschreibung(2).getPartnerprofilId())));

			// Zeile dem Report hinzufï¿½gen
			result.addRow(ausschreibungRow);

		return result;
	}
		
		
//
//
//		for(Organisationseinheit orga : organisationseinheitMapper.findByTyp("Person")){
//			if (orga.getGoogleId().equalsIgnoreCase(login.getEmailAddress())){
//			
//				ReportByAusschreibungenForPartnerprofil result = new ReportByAusschreibungenForPartnerprofil();
//				
//				Row testRow = new Row();
//				
//				testRow.addColumn(new Column("Hallo"));
//				
//				result.addRow(testRow);
//
//				return result;
//			}
//
//		}
//		return null;
//
//	}
		
		

//				
//				// Vektor mit Eigenschaften befüllen
//				Vector<Eigenschaft> vektorEigeneEigenschaften = this.reportGenerator
//						.getEigenschaftenOfOrganisationseinheit(orga);
//				
//				
//				
//				// Partnerprofile vergleichen anhand der Eigenschaftsobjekten
//				// Ließt die Eigenschaften von einem Partnerprofil aus
//				
//				PartnerprofilMapper pm = PartnerprofilMapper.partnerprofilMapper();
//				Partnerprofil pa = pm.findById(orga.getPartnerprofilId());
//				EigenschaftMapper em = EigenschaftMapper.eigenschaftMapper();
//				Vector<Eigenschaft> vektorEigenschaft = new Vector<Eigenschaft>();
//				vektorEigenschaft = em.findByPartnerprofil(pa);
//
//
//				// Alle Ausschreibungen aufrufen
//				AusschreibungMapper am = AusschreibungMapper.ausschreibungMapper();
//				ArrayList<Ausschreibung> alleAusschreibungen = new ArrayList<Ausschreibung>();
//				alleAusschreibungen = am.findAllAusschreibungen();
//
//				// Eigenschaften zu den einzelnen Partnerprofilen auslesen.
//
//				Vector<Vector<Eigenschaft>> vektormitEigenschaftsVektoren = new Vector<>();
//				
//				for (Ausschreibung auss : alleAusschreibungen) {
//					pa = pm.findById(auss.getPartnerprofilId());
//					vektorEigenschaft = em.findByPartnerprofil(pa);
//					vektormitEigenschaftsVektoren.add(vektorEigenschaft);
//				}
//
//				//Vektor mit Ausschreibungen anlegen bei welchen die gesuchten Eigenschaften mit den eigenen Eigenschaften übereinstimmen.
//				Vector<Ausschreibung> au = new Vector<>();
//				
//				for(Vector<Eigenschaft> VE : vektormitEigenschaftsVektoren){
//					//eigene Eigenschaft mit anderen Eigenschaften vergleichen
//
//					for(int i= 0; i < vektormitEigenschaftsVektoren.size(); i++){
//						
//						if(VE.elementAt(i).getName().equals(vektorEigeneEigenschaften.elementAt(i).getName())){
//							
//						} 
//						if(VE.elementAt(i).getWert().equals(vektorEigeneEigenschaften.elementAt(i).getWert())){
//							
//						}
//						//Wir müssen sicher gehen dass wir nicht unser eigenes Partnerprofil bekommen.
//						if(VE.elementAt(i).getPartnerprofilId() != vektorEigeneEigenschaften.elementAt(i).getPartnerprofilId()){
//							
//						}
//						
//						au = am.findByPartnerprofil(pm.findById(VE.elementAt(i).getPartnerprofilId()));	
//					}
//				}
//				
//				//Von den Vektor passendeVektoren muss nun wieder auf Ausschreibungen gekommen werden, damit diese ausgegeben werden können.	
//				
//				for (Ausschreibung a : au) {
//					// neue, leere Zeile anlegen
//					Row ausschreibungRow = new Row();
//					// fï¿½r jede Spalte dieser Zeile wird nun der Inhalt geschrieben
//					ausschreibungRow.addColumn(new Column(String.valueOf(a.getAusschreibungId())));
//					ausschreibungRow.addColumn(new Column(a.getBezeichnung()));
//					ausschreibungRow.addColumn(new Column(a.getNameProjektleiter()));
//					ausschreibungRow.addColumn(new Column(String.valueOf(a.getBewerbungsfrist())));
//					ausschreibungRow.addColumn(new Column(a.getAusschreibungstext()));
//					ausschreibungRow.addColumn(new Column(String.valueOf(a.getErstellDatum())));
//					ausschreibungRow.addColumn(new Column(String.valueOf(a.getProjektId())));
//					ausschreibungRow.addColumn(new Column(String.valueOf(a.getPartnerprofilId())));
//
//					// Zeile dem Report hinzufï¿½gen
//					result.addRow(ausschreibungRow);
//				}
//
//
//				// Report ausgeben
//				return result;
				

		
		


	

	// TODO: Testmethode entfernen
	public String testMethode() {
		String test = "Dies ist ein Test für den RPC-Call";
		return test;
	}

}