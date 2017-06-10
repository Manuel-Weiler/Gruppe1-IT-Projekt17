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
import de.hdm.gruppe1.Project4u.server.Project4uAdministrationImpl;
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
	ReportGeneratorAsync ReportVerwaltung = ClientsideSettings.getReportVerwaltung();

	private Project4uAdministration project4uAdministration = null;

	

	private OrganisationseinheitMapper organisationseinheitMapper = null;
	private PartnerprofilMapper partnerprofilMapper = null;

	
	//Vector wird mit EigenschaftsObjekten des Partnerprofils befüllt
	public Vector <Eigenschaft> getEigenschaftenOfPartnerprofil (Partnerprofil p)throws IllegalArgumentException{
		return this.partnerprofilMapper.getEigenschaftenOfPartnerprofil(p);
	}
	
	//Ermittelt den aktuellen Nutzer
	public Organisationseinheit getOrganisationseinheitByUser(LoginInfo login) throws IllegalArgumentException {

		for (Organisationseinheit o : organisationseinheitMapper.findByTyp("Person")) {
			if (o.getGoogleId().equalsIgnoreCase(login.getEmailAddress())) {

				return o;
			}
		}
		return null;

	};

	//Ermittlung des Partnerprofil der Organisationseinheit
	public Partnerprofil getPartnerprofilOfOrganisationseinheit(Organisationseinheit orga)
			throws IllegalArgumentException {
		return this.partnerprofilMapper.findById(orga.getPartnerprofilId());
	}

	//Ermittelt Eigenschaften einer Organisationseinheit 
	public Vector<Eigenschaft> getEigenschaftenOfOrganisationseinheit(Organisationseinheit orga)
			throws IllegalArgumentException {
		Partnerprofil partnerprofil = new Partnerprofil();
		partnerprofil = getPartnerprofilOfOrganisationseinheit(orga);

		return getEigenschaftenOfPartnerprofil(partnerprofil);
	}

	
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

	public ReportByAusschreibungenForPartnerprofil createAusschreibungenForPartnerprofil(Organisationseinheit orga)
			throws IllegalArgumentException{
		
		if (this.getProject4uAdministration() == null)
			return null;
		
		//Leeren Report anlegen
		ReportByAusschreibungenForPartnerprofil result = new ReportByAusschreibungenForPartnerprofil();
		
		
		//Titel und Bezeichung des Reports
		result.setTitle("Meine Ausschreibungen");
		
		//Impressum hinzufuegen
		result.setCreated(new Date());
		
		//Kopfdaten des Reports
		CompositeParagraph header = new CompositeParagraph();
		
		header.addSubParagraph(new SimpleParagraph("Hier sehen Sie alle Ausschreibungen die Ihrerm Partnerprofil entsprechen"));
		
		//Kopfdaten zu Report hinzufï¿½gen
		result.setHeaderData(header);

		
		//Kopfzeile fï¿½r die Tabelle anlegen:
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
				report.addRow(headline);
				
				//Reportinhalt:
				
			
				//Partnerprofile vergleichen anhand der Eigenschaftsobjekten
				//Ließt die Eigenschaften von einem Partnerprofil aus
				
				Partnerprofil pa = new Partnerprofil();
				EigenschaftMapper em = EigenschaftMapper.eigenschaftMapper();
				Vector<Eigenschaft> ve = new Vector<Eigenschaft>();
				ve = em.findByPartnerprofil(pa);
			
				
				

				
				
				//Eigenschaften des aktuellen Nutzers auslesen und in einen Vektor packen

				//Organisationseinheit orga = new Organisationseinheit();
				ReportVerwaltung.getEigenschaftenOfOrganisationseinheit(orga, new AsyncCallback<Vector<Eigenschaft>>(){

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
						
					}

					@Override
					public void onSuccess(Vector<Eigenschaft> result) {
						ReportVerwaltung.getOrganisationseinheitByUser(ClientsideSettings.getAktuellerUser(),
								new AsyncCallback<Organisationseinheit>() {
							
							public void onSuccess(Organisationseinheit result) {
								//TODO: Den Vektor zurückgeben mit den Eigenschaften des aktuellen User
								
							}
							
							public void onFailure(Throwable caught) {
								Window.alert(caught.getMessage());
								
							}
					
				});
				
						
						
						
				//Hier wird eine ArrayList mit allen Partnerprofilen ausgegeben.
				PartnerprofilMapper pm = PartnerprofilMapper.partnerprofilMapper();
				ArrayList<Partnerprofil> alp = new ArrayList<Partnerprofil>();
				alp = pm.findAllPartnerprofile();
				
				//Alle Ausschreibungen:
				AusschreibungMapper am = AusschreibungMapper.ausschreibungMapper();
				ArrayList<Ausschreibung> au = new ArrayList<Ausschreibung>();
				au = am.findAllAusschreibungen();
				
				//Zunächst muss sichergestellt werden dass wir nur die Partnerprofile von Ausschreibungen erhalten.
				//Diese Ausschreibungen werden in eine ArrayList geschrieben.
				
				ArrayList<Ausschreibung> ala =  new ArrayList<Ausschreibung>();
				
				for(Partnerprofil par : alp){
					for(Ausschreibung aus : au){
						if(par.getID() == aus.getPartnerprofilId()){
							ala.add(aus);
						}
					}
				}
				
				//Eigenschaften zu den einzelnen Partnerprofilen auslesen.
				//und Eigenschaften vergleichen
				
				for(Ausschreibung auss : ala){
					pa = pm.findById(auss.getPartnerprofilId());
					ve = em.findByPartnerprofil(pa);
					
					if(ve.equals(aktuellerNutzerEigenschaften)){
						
					}
					}

				
				
				//Ausschreibungen auslesen
				
				
				// Ausschreibungen einem Array hinzufügen
				
				
				
				for(Ausschreibung a : au){
					//neue, leere Zeile anlegen
					Row ausschreibungRow = new Row();
					//fï¿½r jede Spalte dieser Zeile wird nun der Inhalt geschrieben
					ausschreibungRow.addColumn(new Column(String.valueOf(a.getAusschreibungId())));
					ausschreibungRow.addColumn(new Column(a.getBezeichnung()));
					ausschreibungRow.addColumn(new Column(a.getNameProjektleiter()));
					ausschreibungRow.addColumn(new Column(String.valueOf(a.getBewerbungsfrist())));
					ausschreibungRow.addColumn(new Column(a.getAusschreibungstext()));
					ausschreibungRow.addColumn(new Column(String.valueOf(a.getErstellDatum())));
					ausschreibungRow.addColumn(new Column(String.valueOf(a.getProjektId())));
					ausschreibungRow.addColumn(new Column(String.valueOf(a.getPartnerprofilId())));
					
					//Zeile dem Report hinzufï¿½gen
					report.addRow(ausschreibungRow);
				}
				
				//Report ausgeben
				return report;
				
	}


	// TODO: Testmethode entfernen
	public String testMethode() {
		String test = "Dies ist ein Test für den RPC-Call";
		return test;
	}

}
