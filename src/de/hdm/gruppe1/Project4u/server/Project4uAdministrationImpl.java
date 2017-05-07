package de.hdm.gruppe1.Project4u.server;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe1.Project4u.server.db.BeteiligungMapper;
import de.hdm.gruppe1.Project4u.server.db.BewerbungMapper;
import de.hdm.gruppe1.Project4u.server.db.EigenschaftMapper;
import de.hdm.gruppe1.Project4u.server.db.OrganisationseinheitMapper;
import de.hdm.gruppe1.Project4u.server.db.PartnerprofilMapper;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministration;
import de.hdm.gruppe1.Project4u.shared.bo.Beteiligung;
import de.hdm.gruppe1.Project4u.shared.bo.Bewerbung;
import de.hdm.gruppe1.Project4u.shared.bo.Eigenschaft;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;

@SuppressWarnings("serial")
public class Project4uAdministrationImpl extends RemoteServiceServlet implements Project4uAdministration {

	private BewerbungMapper bewerbungMapper = null;
	private static final Beteiligung Beteiligung = null;
	private BeteiligungMapper beteiligungMapper = null;
	private OrganisationseinheitMapper organisationseinheitMapper = null;
	private PartnerprofilMapper partnerprofilMapper = null;

	private EigenschaftMapper eigenschaftMapper = null;

	
	public Project4uAdministrationImpl() throws IllegalArgumentException{
		
	}

	
	//Initialisierung
	public void init() throws IllegalArgumentException{

		
			
		this.eigenschaftMapper = EigenschaftMapper.eigenschaftMapper();
		this.beteiligungMapper = BeteiligungMapper.beteiligungMapper(); 
		this.organisationseinheitMapper = OrganisationseinheitMapper.organisationseinheitMapper();
		this.partnerprofilMapper = PartnerprofilMapper.partnerprofilMapper();

	}

	/*
	 * #########################################################################
	 * ABSCHNITT, Beginn: Organisationseinheit
	 * #########################################################################
	 * 
	 */
	public Organisationseinheit createOrganisationseinheit(String google_id, String name, String typ)
			throws IllegalArgumentException {

		Organisationseinheit organisationseinheit = new Organisationseinheit();
		organisationseinheit.setGoogleId(google_id);
		organisationseinheit.setName(name);
		organisationseinheit.setTyp(typ);

		return this.organisationseinheitMapper.insert(organisationseinheit);

	}
	
	/*
	 * #########################################################################
	 * ABSCHNITT, Beginn: Ausschreibung
	 * #########################################################################
	 * 
	 */
	
	
	/*
	 * #########################################################################
	 * ABSCHNITT, Beginn: Beteiligung
	 * #########################################################################
	 * 
	 */
	
	public Beteiligung createBeteiligung () throws IllegalArgumentException {
		    Beteiligung beteiligung = new Beteiligung();
		    beteiligung.setBeteiligungId(beteiligung);


		    beteiligung.setID(1);

		    return this.beteiligungMapper.insert(beteiligung);
		  }
	
	public void delete (Beteiligung delete){
		beteiligungMapper.deleteBeteiligung(Beteiligung);
	}
	
	//Login-Status
	public Organisationseinheit checkStatus(Organisationseinheit loginInfo){
		return this.organisationseinheitMapper.checkStatus(loginInfo);
	}

	public Bewerbung createBewerbung(int bewerbungID, Date erstelldatum, String bewerbungstext)
			throws IllegalArgumentException {
		Bewerbung bewerbung = new Bewerbung();
		return this.bewerbungMapper.insert(bewerbung);
	}

	public void updateBewerbung(int bewerbungID, Date erstelldatum, String bewerbungstext)
			throws IllegalArgumentException {

		Bewerbung bewerbung = new Bewerbung();
		bewerbung.setBewerbungID(bewerbungID);
		bewerbung.setErstelldatum(erstelldatum);
		bewerbung.setBewerbungstext(bewerbungstext);

		this.bewerbungMapper.updateBewerbung(bewerbung);
	}

	public Organisationseinheit findByKey(int id) throws IllegalArgumentException {
		return this.organisationseinheitMapper.findByKey(id);
	}

	public Vector<Organisationseinheit> findAll() throws IllegalArgumentException {
		return this.organisationseinheitMapper.findAll();
	}

	public Vector<Organisationseinheit> findByNachname(String name) {
		return this.organisationseinheitMapper.findByNachname(name);
	}

	public void update(Organisationseinheit organisationseinheit) throws IllegalArgumentException {
		organisationseinheitMapper.update(organisationseinheit);
	}

	public void delete(Organisationseinheit organisationseinheit) throws IllegalArgumentException {
		organisationseinheitMapper.delete(organisationseinheit);
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
		partnerprofilMapper.deletePartnerprofil(p);
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
		return this.eigenschaftMapper.selectAllEigenschaftOfPartnerprofil(p);
	}
	
	/*
	 * #########################################################################
	 * ABSCHNITT, Ende: Eigenschaft
	 * #########################################################################
	 * 
	 */

}
