package de.hdm.gruppe1.Project4u.server;


import java.io.Serializable;

import java.util.ArrayList;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe1.Project4u.server.db.AusschreibungMapper;
import de.hdm.gruppe1.Project4u.server.db.BeteiligungMapper;
import de.hdm.gruppe1.Project4u.server.db.BewerbungMapper;
import de.hdm.gruppe1.Project4u.server.db.BewertungMapper;
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
public class Project4uAdministrationImpl extends RemoteServiceServlet implements Project4uAdministration, Serializable{

	private BewerbungMapper bewerbungMapper = null;
	private BewertungMapper bewertungMapper = null;
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
        this.bewertungMapper = BewertungMapper.bewertungMapper();
	}
	
	
	
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

	public void deleteOrganisationseinheit(Organisationseinheit organisationseinheit) throws IllegalArgumentException {
		
		//Zugehöriges Partnerprofil löschen
  		Partnerprofil partnerprofil = partnerprofilMapper.findByOrganisationseinheit(organisationseinheit);
  		
  		if(partnerprofil != null) {
  			this.deletePartnerprofil(partnerprofil);
  		}
      
      	//Zugehörige Projekte löschen		      
      	Vector<Projekt> vp = new Vector<Projekt>();     	
      	if(vp != null) {
      		vp = projektMapper.findByOrganisationseinheit(organisationseinheit);
      		for(Projekt projekt: vp){
      			this.delete(projekt);
      		}
      	}
		
		//Zugehörige Bewerbungen löschen
  		Vector<Bewerbung> vb = new Vector<Bewerbung>();	
  		if (vb != null) {
  			vb = this.bewerbungMapper.findByOrganisationseinheit(organisationseinheit);
  			for(Bewerbung b: vb){
  				this.deleteBewerbung(b);
  			}
  		}
      	//Organisationseinheit löschen
      	this.organisationseinheitMapper.delete(organisationseinheit);
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

	public void updateOrganisationseinheit(Organisationseinheit organisationseinheit) throws IllegalArgumentException {
		this.organisationseinheitMapper.update(organisationseinheit);
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

	public Partnerprofil createPartnerprofil(Organisationseinheit o) throws IllegalArgumentException {		
		Partnerprofil p = new Partnerprofil();
		p.setOrganisationseinheitId(o.getOrganisationseinheitId());	
		return this.partnerprofilMapper.insertPartnerprofil(p);
	}

	public Partnerprofil updatePartnerprofil(Partnerprofil p) throws IllegalArgumentException {
		return this.partnerprofilMapper.updatePartnerprofil(p);
	}

	public void deletePartnerprofil(Partnerprofil p) throws IllegalArgumentException {
		
		//Zugehörige Eigenschaften löschen
		Vector<Eigenschaft> ve = new Vector<Eigenschaft>();
      	
      	if(ve != null) {
      		ve = eigenschaftMapper.findByPartnerprofil(p);
      		for(Eigenschaft eigenschaft: ve){
      			this.deleteEigenschaft(eigenschaft);
      		}
      	}
    		//Partnerprofil löschen
    		this.partnerprofilMapper.deletePartnerprofil(p);
      	}
	
	public Partnerprofil findById(int i) throws IllegalArgumentException {
		return this.partnerprofilMapper.findById(i);
	}
		

	/*
	 * TODO: public Ausschreibung getAusschreibungOf(Partnerprofil p) public
	 * Vector <Eigenschaft> getEigenschaftenOf (Partnerprofil p)
	 */

	public Vector <Eigenschaft> getEigenschaftenOfPartnerprofil (Partnerprofil p)throws IllegalArgumentException{
		return this.partnerprofilMapper.getEigenschaftenOfPartnerprofil(p);
	}
	
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
	
	public Eigenschaft updateEigenschaft(Eigenschaft e) throws IllegalArgumentException{
		return this.eigenschaftMapper.updateEigenschaft(e);
	}
	
	public void deleteEigenschaft(Eigenschaft e) throws IllegalArgumentException{
		eigenschaftMapper.deleteEigenschaft(e);
	}

	
	public void deleteAllEigenschaftOfPartnerprofil(Partnerprofil p)throws IllegalArgumentException{
		eigenschaftMapper.deleteAllEigenschaftOfPartnerprofil(p);
	}

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
	 * ABSCHNITT, Beginn: Projekt
	 * #########################################################################
	 * 
	 */
	
	public Projekt createProjekt(Projekt p, Projektmarktplatz pm, Organisationseinheit o)
			throws IllegalArgumentException {
		
		return this.projektMapper.insert(p, pm, o);
	}
	
	public Projekt createProjekt(int id, String name, Date startdatum, Date enddatum, String beschreibung,
			                     int projektmarktplatz_id, int organisationseinheit_id) throws IllegalArgumentException {

		Projekt projekt = new Projekt();
		projekt.setProjektId(1);
		projekt.setName(name);
		projekt.setStartdatum(startdatum);
		projekt.setEnddatum(enddatum);
		projekt.setBeschreibung(beschreibung);
		projekt.setProjektmarktplatzId(projektmarktplatz_id);
		projekt.setOrganisationseinheitId(organisationseinheit_id);
				
     return this.projektMapper.insert(projekt, null, null);

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
		
		//Zugehörige Ausschreibungen löschen
		Vector<Ausschreibung> va = new Vector<Ausschreibung>();
		va = ausschreibungMapper.findByProjekt(p);      	
		if(va != null) {
		    for(Ausschreibung ausschreibung: va){
		    	this.deleteAusschreibung(ausschreibung);
		    }
		}
		this.projektMapper.delete(p);
	}	
	
	public Vector<Projekt> findByName(String name) throws IllegalArgumentException {
		return this.projektMapper.findByName(name);
	}
	
	/**
	 * Diese Methode gibt alle Projekte wieder, die zu einem Projektmarktplatz pp gehören
	 * @param pp
	 * @return
	 * @throws IllegalArgumentException
	 * @author Tobias
	 */
	public Vector<Projekt> findAllProjekteOfProjektmarktplatz(Projektmarktplatz pp) throws IllegalArgumentException{
		return this.projektMapper.findAllProjekteOfProjektmarktplatz(pp);
		
	}
	
	/*
	 * #########################################################################
	 * ABSCHNITT, Ende: Projekt
	 * #########################################################################
	 * 
	 */
	

	/*
	 * #########################################################################
	 * ABSCHNITT, Beginn: Ausschreibung
	 * #########################################################################
	 * 
	 */
	
	public Ausschreibung createAusschreibung(Partnerprofil pa, Projekt pr)throws IllegalArgumentException{
		Ausschreibung a = new Ausschreibung();
		a.setProjektId(pr.getProjektId());
		
		return this.ausschreibungMapper.insertAusschreibung(a, pa, pr);
	}
	
	public void updateAusschreibung(Ausschreibung ausschreibung) throws IllegalArgumentException {
		ausschreibungMapper.updateAusschreibung(ausschreibung);
	}
	
	public void deleteAusschreibung(Ausschreibung ausschreibung) throws IllegalArgumentException {
		
		//zugehörige Bewerbungen löschen
		Vector<Bewerbung> bv = new Vector<Bewerbung>();
		bv = bewerbungMapper.findByAusschreibung(ausschreibung);
		if(bv != null) {
			for(Bewerbung bewerbung: bv) {
				this.deleteBewerbung(bewerbung);
			}
		}
		
		//TODO: zugehöriges Partnerprofil löschen
		//Partnerprofil partnerprofil = ausschreibungMapper.findB
  		//if(partnerprofil != null) {
  		//	this.deletePartnerprofil(partnerprofil);
  		//}
		
		
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
	
//	public Vector<Ausschreibung> findbyPerson (String name)throws IllegalArgumentException{
//		return this.ausschreibungMapper.findByPerson(name);
//	}
	
	public Vector<Ausschreibung> findAusschreibungbyProjekt (Projekt projekt)throws IllegalArgumentException{
		return this.ausschreibungMapper.findByProjekt(projekt);
	}
	
	
	public ArrayList<Ausschreibung> getAllAusschreibungen() throws IllegalArgumentException {
		
		return this.ausschreibungMapper.findAllAusschreibungen();
	}
	
	/*
	 * #########################################################################
	 * ABSCHNITT, Ende: Ausschreibung
	 * #########################################################################
	 * 
	 */
	
	/*
	 * #########################################################################
	 * ABSCHNITT, Beginn: Bewerbung
	 * #########################################################################
	 * 
	 */

	public Bewerbung createBewerbung(int bewerbungID, Date erstelldatum, String bewerbungstext) throws IllegalArgumentException {
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
	
	public void deleteBewerbung(Bewerbung bewerbung) {
		
		//Zugehörige Bewertungen löschen
		Bewertung bewertung = this.bewertungMapper.findByBewerbung(bewerbung);
  		if (bewertung != null) {
  			this.deleteBewertung(bewertung);
  			}
  		this.bewerbungMapper.delete(bewerbung);
  		}
		
		
		
	/*
	 * #########################################################################
	 * ABSCHNITT, Ende: Bewerbung
	 * #########################################################################
	 * 
	 */
	
	/*
	 * #########################################################################
	 * ABSCHNITT, Beginn: Bewertung
	 * #########################################################################
	 * 
	 */
	
	public Bewertung createBewertung(Bewerbung bewerbung, float bewertungspunkte, String stellungnahme) throws IllegalArgumentException {
		Bewertung bewertung = new Bewertung();
		bewertung.setBewerbungID(bewerbung.getBewerbungID());
		bewertung.setBewertungspunkte(bewertungspunkte);
		bewertung.setStellungnahme(stellungnahme);
		return this.bewertungMapper.insert(bewertung);
	}
	
	public void updateBewertung(Bewertung bewertung) throws IllegalArgumentException {
		this.bewertungMapper.update(bewertung);
	}
	
	public void deleteBewertung(Bewertung bewertung) {
		
		//Zugehörige Beteiligung löschen
		Beteiligung beteiligung = beteiligungMapper.findByBewertung(bewertung);
  		if (beteiligung != null) {
  			
  			//Zugehörige Beteiligung löschen
  			this.delete(beteiligung);
  			}
		this.bewertungMapper.delete(bewertung);
  		}
	
	/*
	 * #########################################################################
	 * ABSCHNITT, Ende: Bewertung
	 * #########################################################################
	 * 
	 */

	/*
	 * #########################################################################
	 * ABSCHNITT, Beginn: Beteiligung
	 * #########################################################################
	 * 
	 */

	public Beteiligung createBeteiligung(	Date startdatum, Date enddatum, int personentage, 
											Organisationseinheit organisationseinheit, Projekt projekt, 
											Bewertung bewertung) throws IllegalArgumentException {
		
		Beteiligung beteiligung = new Beteiligung();
		beteiligung.setStartdatum(startdatum);
		beteiligung.setEnddatum(enddatum);
		beteiligung.setPersonentage(personentage);
		beteiligung.setOrganisationseinheitId(organisationseinheit.getOrganisationseinheitId());
		beteiligung.setBewertungId(bewertung.getBewertungID());
		beteiligung.setProjektId(projekt.getProjektId());
		
		return this.beteiligungMapper.insertBeteiligung(beteiligung);
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
	 * ABSCHNITT, Beginn: Projektmarktplatz
	 * #########################################################################
	 * 
	 */
	
	public Projektmarktplatz createProjektmarktplatz(Projektmarktplatz p)
			throws IllegalArgumentException {

		return this.projektmarktplatzMapper.insert(p);

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
	

	
	//TODO: Testmethode
	public String testMethode (){
		String test = "Dies ist ein Test-String";
		return test;
	}
	
	public Vector<Projektmarktplatz> testMethode2(){
		Projektmarktplatz test = new Projektmarktplatz();
		test.setName("test");
		test.setProjektmarktplatzId(1);
		Vector<Projektmarktplatz> vtest = new Vector<Projektmarktplatz>();
		vtest.add(test);
		return vtest;
	}

	

	
	
}
