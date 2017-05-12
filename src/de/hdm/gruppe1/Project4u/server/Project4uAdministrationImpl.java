package de.hdm.gruppe1.Project4u.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe1.Project4u.server.db.AusschreibungMapper;
import de.hdm.gruppe1.Project4u.server.db.BeteiligungMapper;
import de.hdm.gruppe1.Project4u.server.db.BewerbungMapper;
import de.hdm.gruppe1.Project4u.server.db.EigenschaftMapper;
import de.hdm.gruppe1.Project4u.server.db.OrganisationseinheitMapper;
import de.hdm.gruppe1.Project4u.server.db.PartnerprofilMapper;
import de.hdm.gruppe1.Project4u.server.db.ProjektMapper;
import de.hdm.gruppe1.Project4u.server.db.ProjektmarktplatzMapper;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministration;
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Beteiligung;
import de.hdm.gruppe1.Project4u.shared.bo.Bewerbung;
import de.hdm.gruppe1.Project4u.shared.bo.Bewertung;
import de.hdm.gruppe1.Project4u.shared.bo.Eigenschaft;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;
import de.hdm.gruppe1.Project4u.shared.bo.Projektmarktplatz;

@SuppressWarnings("serial")
public class Project4uAdministrationImpl extends RemoteServiceServlet implements Project4uAdministration {

	private BewerbungMapper bewerbungMapper = null;
	private BeteiligungMapper beteiligungMapper = null;
	private AusschreibungMapper ausschreibungMapper = null; 
	private OrganisationseinheitMapper organisationseinheitMapper = null;
	private PartnerprofilMapper partnerprofilMapper = null;
	private ProjektmarktplatzMapper projektmarktplatzMapper = null;
	private ProjektMapper projektMapper = null;
	private EigenschaftMapper eigenschaftMapper = null;

	
	public Project4uAdministrationImpl() throws IllegalArgumentException{
		
	}
	
	//Initialisierung
	public void init() throws IllegalArgumentException{

		this.eigenschaftMapper = EigenschaftMapper.eigenschaftMapper();
		this.beteiligungMapper = BeteiligungMapper.beteiligungMapper(); 
		this.organisationseinheitMapper = OrganisationseinheitMapper.organisationseinheitMapper();
		this.partnerprofilMapper = PartnerprofilMapper.partnerprofilMapper();
        this.projektmarktplatzMapper = ProjektmarktplatzMapper.projektmarktplatzMapper();
        this.projektMapper = ProjektMapper.projektMapper();
	}

	/*
	 * #########################################################################
	 * ABSCHNITT, Beginn: Ausschreibung
	 * #########################################################################
	 * 
	 */
	
	public Ausschreibung insertAusschreibung(Ausschreibung a, Partnerprofil pa, Projekt pr)throws IllegalArgumentException{
		return this.ausschreibungMapper.insertAusschreibung(a, pa, pr);
	}
	
	public void update(Ausschreibung ausschreibung) throws IllegalArgumentException {
		ausschreibungMapper.updateAusschreibung(ausschreibung);
	}
	
	public void delete(Ausschreibung ausschreibung) throws IllegalArgumentException {
		ausschreibungMapper.deleteAusschreibung(ausschreibung);
	}
	
	public Ausschreibung findByIdAusschreibung (int id) throws IllegalArgumentException {
		return this.ausschreibungMapper.findByIdAusschreibung(id);
	}
	
	public Ausschreibung findByNameAusschreibung(String bezeichnung) throws IllegalArgumentException {
		return this.ausschreibungMapper.findByNameAusschreibung(bezeichnung);
	}
	/*
	 * Find by Name
	 */
	
	public Vector<Ausschreibung> findbyPerson (String name)throws IllegalArgumentException{
		return this.ausschreibungMapper.findByPerson(name);
	}
	
	public Vector<Ausschreibung> findbyProjekt (String name)throws IllegalArgumentException{
		return this.ausschreibungMapper.findByProjekt(name);
	}
	
	
	/*
	 * #########################################################################
	 * ABSCHNITT, Ende: Ausschreibung
	 * #########################################################################
	 * 
	 */
	
	

	/*
	 * #########################################################################
	 * ABSCHNITT, Beginn: Beteiligung
	 * #########################################################################
	 * 
	 */

	public Beteiligung insertBeteiligung( Beteiligung b, Organisationseinheit or, Bewertung be, Projekt pr)throws IllegalArgumentException{
		return this.beteiligungMapper.insertBeteiligung(b, or, be, pr, be);
	}
	
	
	public void delete(Beteiligung b) throws IllegalArgumentException {
		beteiligungMapper.deleteBeteiligung(b);
	}
	/*
	 * #########################################################################
	 * ABSCHNITT, Ende: Beteiligung
	 * #########################################################################
	 * 
	 */
	
	/*
	 * #########################################################################
	 * ABSCHNITT, Beginn: Bewerbung
	 * #########################################################################
	 * 
	 */

	public Bewerbung createBewerbung(int bewerbungID, Date erstelldatum, String bewerbungstext)
			throws IllegalArgumentException {
		Bewerbung bewerbung = new Bewerbung();
		return this.bewerbungMapper.insert(bewerbung, null, null);
	}

	public void updateBewerbung(int bewerbungID, Date erstelldatum, String bewerbungstext)
			throws IllegalArgumentException {

		Bewerbung bewerbung = new Bewerbung();
		bewerbung.setBewerbungID(bewerbungID);
		bewerbung.setErstelldatum(erstelldatum);
		bewerbung.setBewerbungstext(bewerbungstext);

		this.bewerbungMapper.update(bewerbung);
	}
	/*
	 * #########################################################################
	 * ABSCHNITT, Ende: Bewerbung
	 * #########################################################################
	 * 
	 */
	
	/*
	 * #########################################################################
	 * ABSCHNITT, Beginn: Organisationseinheit
	 * #########################################################################
	 * 
	 */
	
	//Login-Status
		public Organisationseinheit checkStatus(Organisationseinheit loginInfo){
			return this.organisationseinheitMapper.checkStatus(loginInfo);
		}

	public Organisationseinheit createOrganisationseinheit(String google_id, String name, String typ)
			throws IllegalArgumentException {

		Organisationseinheit organisationseinheit = new Organisationseinheit();
		organisationseinheit.setGoogleId(google_id);
		organisationseinheit.setName(name);
		organisationseinheit.setTyp(typ);

		return this.organisationseinheitMapper.insert(organisationseinheit);

	}
	
	public Organisationseinheit getOrganisationseinheitById(int id) throws IllegalArgumentException {
		return this.organisationseinheitMapper.findByKey(id);
	}

	public Vector<Organisationseinheit> getAllOrganisationseinheiten() throws IllegalArgumentException {
		return this.organisationseinheitMapper.findAll();
	}

	public Vector<Organisationseinheit> getOrganisationseinheitByName(String name) {
		return this.organisationseinheitMapper.findByName(name);
	}
	
	public Vector<Organisationseinheit> getOrganisationseinheitByTyp(String typ) {
		return this.organisationseinheitMapper.findByTyp(typ);
	}
	
	public void insert(Organisationseinheit organisationseinheit) throws IllegalArgumentException {
		this.organisationseinheitMapper.insert(organisationseinheit);
	}

	public void update(Organisationseinheit organisationseinheit) throws IllegalArgumentException {
		this.organisationseinheitMapper.update(organisationseinheit);
	}

	public void delete(Organisationseinheit organisationseinheit) throws IllegalArgumentException {
		
		/*//Zugehörige Bewerbungen löschen
  		Vector<Bewerbung> vb = new Vector<Bewerbung>();
  		vb = BewerbungMapper.bewerbungMapper().findByOrganisationseinheit(organisationseinheit);
  		for(Bewerbung b: vb){
  			BewerbungMapper.bewerbungMapper().delete(b);
  		}
      
  		//Zugehöriges Partnerprofil löschen
      	PartnerprofilMapper.partnerprofilMapper().deletePartnerprofil(PartnerprofilMapper.partnerprofilMapper().findByOrganisationseinheit(organisationseinheit));
      
      	//Zugehörige Projekte löschen		      
      	Vector<Projekt> vp = new Vector<Projekt>();
      		vp = ProjektMapper.projektMapper().findByOrganisationseinheit(organisationseinheit);
      		for(Projekt p: vp){
      			ProjektMapper.projektMapper().delete(p);
      		}*/
		
		this.bewerbungMapper.deleteBewerbungOfOrganisationseinheit(organisationseinheit);
		this.partnerprofilMapper.deletePartnerprofilOfOrganisationseinheit(organisationseinheit);
		this.projektMapper.deleteProjektOfOrganisationseinheit(organisationseinheit);
		
		this.organisationseinheitMapper.delete(organisationseinheit);
	}
	/*
	 * #########################################################################
	 * ABSCHNITT, Ende: Organisationseinheit
	 * #########################################################################
	 * 
	 */

	/*
	 * #########################################################################
	 * ABSCHNITT, Beginn: Partnerprofil
	 * #########################################################################
	 * 
	 */

	/**
	 * Mit dieser Methode wird dem zu speichernden Partnerprofil die richtige
	 * <code>id</code> vergeben und das Partnerprofil in der Datenbank abgelegt.
	 * 
	 * @param p
	 *            das Partnerprofil-Objekt, dass in der Datenbank abgelegt wird.
	 * @param o
	 *            das Organisationseinheit-Objekt, dem das Partnerprofil
	 *            zugeordnet ist.
	 * @return das m�glicherweise durch die Methode ge�nderte
	 *         Partnerprofil-Objekt.
	 */

	public Partnerprofil insertPartnerprofil(Partnerprofil p, Organisationseinheit o) throws IllegalArgumentException {
		return this.partnerprofilMapper.insertPartnerprofil(p, o);
	}

	public Partnerprofil findById(int i) throws IllegalArgumentException {
		return this.partnerprofilMapper.findById(i);
	}

	/**
	 * Die Methode �ndert das �nderungsdatum eines Partnerprofils Die id, das
	 * Erstellungsdatum, sowie die Fremdschl�ssel-id der zugeh�rigen
	 * Organisationseinheit sind unver�nderlich
	 * 
	 * @return ein Partnerprofil-Objekt mit ge�ndertem �nderungsdatum
	 * 
	 */
	public Partnerprofil updatePartnerprofil(Partnerprofil p) throws IllegalArgumentException {
		return this.partnerprofilMapper.updatePartnerprofil(p);
	}

	public void deletePartnerprofil(Partnerprofil p) throws IllegalArgumentException {
		
		//Zugehörige EIgenschaften löschen
		this.eigenschaftMapper.deleteEigenschaftOfPartnerprofil(p);
		
		//Partnerprofil löschen
		partnerprofilMapper.deletePartnerprofil(p);
	}

	public ArrayList<Ausschreibung> getAllAusschreibungen() throws IllegalArgumentException {
		return this.ausschreibungMapper.findAll();
	}

	/*
	 * TODO: public Ausschreibung getAusschreibungOf(Partnerprofil p) public
	 * Vector <Eigenschaft> getEigenschaftenOf (Partnerprofil p)
	 */

	
	
	/**Diese Methode gibt alle Eigenschaftsobjekte zu einem Partnerprofil-Objekt p zur�ck
	 * @param p Partnerprofil
	 * @return 
	 * @throws IllegalArgumentException
	 */
	public Vector <Eigenschaft> getEigenschaftenOfPartnerprofil (Partnerprofil p)throws IllegalArgumentException{
		return this.partnerprofilMapper.getEigenschaftenOfPartnerprofil(p);
	}
	
	
	/**Diese Methode gibt die zugeh�rige Organisationseinheit zu einem Partnerprofil zur�ck.
	 * @param p
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Organisationseinheit getOrganisationseinheitOfPartnerprofil (Partnerprofil p)throws IllegalArgumentException{
		return this.partnerprofilMapper.getOrganisationseinheitOfPartnerprofil(p);
	}
	
	/*
	 * #########################################################################
	 * ABSCHNITT, Ende: Partnerprofil
	 * #########################################################################
	 * 
	 */


	
	/*
	 * #########################################################################
	 * ABSCHNITT, Beginn: Eigenschaft
	 * #########################################################################
	 * 
	 */
	
	/**Die Methode vergibt dem zu speichernden Eigenschafts-Objekts einen Prim�rschl�ssel und 
	 * legt es in der DB ab. Zudem aktualisiert sie das �nderungsdatum des zugeh�rigen 
	 * Partnerprofil-Objekts
	 * @param e
	 * @param p
	 * @throws IllegalArgumentException
	 */
	public Eigenschaft insertEigenschaft(Eigenschaft e, Partnerprofil p)throws IllegalArgumentException{
		return this.eigenschaftMapper.insertEigenschaft(e, p);
	}
	
	public Eigenschaft updateEigenschaft(Eigenschaft e)throws IllegalArgumentException{
		return this.eigenschaftMapper.updateEigenschaft(e);
	}
	
	public void deleteEigenschaft(Eigenschaft e)throws IllegalArgumentException{
		eigenschaftMapper.deleteEigenschaft(e);
	}
	
	/**Die Methode l�scht alle Eigenschaften, die in einer Fremdschl�sselbeziehung zu 
	 * einem Partnerprofil p stehen.
	 * @param p
	 * @throws IllegalArgumentException
	 */
	
	public void deleteAllEigenschaftOfPartnerprofil(Partnerprofil p)throws IllegalArgumentException{
		eigenschaftMapper.deleteAllEigenschaftOfPartnerprofil(p);
	}
	
	/**Die Methode gibt alle Eigenschaftsobjekte eines Partnernprofils wieder
	 * @param p
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Vector<Eigenschaft> selectAllEigenschaftOfPartnerprofil(Partnerprofil p)throws IllegalArgumentException{
		return this.eigenschaftMapper.findByPartnerprofil(p);
	}
	
	/*
	 * #########################################################################
	 * ABSCHNITT, Ende: Eigenschaft
	 * #########################################################################
	 * 
	 */


	
	/*
	 * #########################################################################
	 * ABSCHNITT, Beginn: Projektmarktplatz
	 * #########################################################################
	 * 
	 */
	
	public Projektmarktplatz createProjektmarktplatz(int id, String name )
			throws IllegalArgumentException {

		Projektmarktplatz projektmarktplatz = new Projektmarktplatz();
		projektmarktplatz.setProjektmarktplatzId(1);
		projektmarktplatz.setName(name);
		

		return this.projektmarktplatzMapper.insert(projektmarktplatz);

	}
	
	public Projektmarktplatz findProjektmarktplatzById(int id) throws IllegalArgumentException {
		return this.projektmarktplatzMapper.findById(id);
	}

	public Vector<Projektmarktplatz> findAllProjektmarktplatz() throws IllegalArgumentException {
		return this.projektmarktplatzMapper.findAll();
	}

	public void update(Projektmarktplatz p) throws IllegalArgumentException {
		projektmarktplatzMapper.update(p);
	}

	public void delete(Projektmarktplatz p) throws IllegalArgumentException {
		projektmarktplatzMapper.delete(p);
	}	

	/*
	 * #########################################################################
	 * ABSCHNITT, Ende: Projektmarktplatz
	 * #########################################################################
	 * 
	 */
	
	/*
	 * #########################################################################
	 * ABSCHNITT, Beginn: Projekt
	 * #########################################################################
	 * 
	 */
	
	public Projekt createProjekt(int id, String name, Date startdatum, Date enddatum, String beschreibung,
			                     int projektmarktplatz_id, int organisationseinheit_id)
		   throws IllegalArgumentException {

		Projekt projekt = new Projekt();
		projekt.setProjektId(1);
		projekt.setName(name);
		projekt.setStartdatum(startdatum);
		projekt.setEnddatum(enddatum);
		projekt.setBeschreibung(beschreibung);
		projekt.setProjektmarktplatzId(projektmarktplatz_id);
		projekt.setOrganisationseinheitId(organisationseinheit_id);
		
		

		return this.projektMapper.insert(projekt);

	}
	
	public Projekt findProjektById(int id) throws IllegalArgumentException {
		return this.projektMapper.findById(id);
	}

	public Vector<Projekt> findAllProjekt() throws IllegalArgumentException {
		return this.projektMapper.findAll();
	}

	public void update(Projekt p) throws IllegalArgumentException {
		projektMapper.update(p);
	}

	public void delete(Projekt p) throws IllegalArgumentException {
		projektMapper.delete(p);
	}	
	
	public Vector<Projekt> findByName(String name) throws IllegalArgumentException {
		return this.projektMapper.findByName(name);
	}
	/*
	 * #########################################################################
	 * ABSCHNITT, Ende: Projekt
	 * #########################################################################
	 * 
	 */
}
