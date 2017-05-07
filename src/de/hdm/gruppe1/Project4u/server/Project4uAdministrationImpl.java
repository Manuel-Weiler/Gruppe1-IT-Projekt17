package de.hdm.gruppe1.Project4u.server;

import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe1.Project4u.server.db.*;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministration;
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Beteiligung;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;

@SuppressWarnings("serial")
public class Project4uAdministrationImpl extends RemoteServiceServlet implements Project4uAdministration{
	

	
	private static final Beteiligung Beteiligung = null;


	private BeteiligungMapper beteiligungMapper = null;

	private AusschreibungMapper ausschreibungMapper = null;

	private OrganisationseinheitMapper organisationseinheitMapper = null;
	private PartnerprofilMapper partnerprofilMapper = null;

	
	public Project4uAdministrationImpl() throws IllegalArgumentException{
		
	}
	
	//Initialisierung
	public void init() throws IllegalArgumentException{

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
		throws IllegalArgumentException{
		
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
	
	public Organisationseinheit findByKey(int id) throws IllegalArgumentException {
		return this.organisationseinheitMapper.findByKey(id);
	}
	
	public Vector<Organisationseinheit> findAll() throws IllegalArgumentException {
		return this.organisationseinheitMapper.findAll();
	}
	
	public Vector<Organisationseinheit> findByNachname(String name) {
		return this.organisationseinheitMapper.findByNachname(name);
	}
	
	public void update (Organisationseinheit organisationseinheit) throws IllegalArgumentException {
		organisationseinheitMapper.update(organisationseinheit);
	}
	
	public void delete (Organisationseinheit organisationseinheit) throws IllegalArgumentException {
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
	 * @param p das Partnerprofil-Objekt, dass in der Datenbank abgelegt wird.
	 * @param o das Organisationseinheit-Objekt, dem das Partnerprofil zugeordnet ist.
	 * @return das möglicherweise durch die Methode geänderte Partnerprofil-Objekt.
	 */
	 
	public Partnerprofil insertPartnerprofil(Partnerprofil p, Organisationseinheit o)
			throws IllegalArgumentException {
		return this.partnerprofilMapper.insertPartnerprofil(p, o);
	}
	
	public Partnerprofil findById(int i)throws IllegalArgumentException {
		return this.partnerprofilMapper.findById(i);
	}
	
	
	/**
	 * Die Methode ändert das Änderungsdatum eines Partnerprofils Die id, das
	 * Erstellungsdatum, sowie die Fremdschlüssel-id der zugehörigen
	 * Organisationseinheit sind unveränderlich
	 * @return ein Partnerprofil-Objekt mit geändertem Änderungsdatum
	 * 
	 */
	public Partnerprofil updatePartnerprofil(Partnerprofil p) throws IllegalArgumentException {
		return this.partnerprofilMapper.updatePartnerprofil(p);
	}
	
	public void deletePartnerprofil (Partnerprofil p)throws IllegalArgumentException{
		partnerprofilMapper.deletePartnerprofil(p);
	}
	
	/*
	 * TODO: 	public Ausschreibung getAusschreibungOf(Partnerprofil p)
	 * 			public Vector <Eigenschaft> getEigenschaftenOf (Partnerprofil p)
	 */
	
	/*
	 * #########################################################################
	 * ABSCHNITT, Ende: Partnerprofil
	 * #########################################################################
	 * 
	 */

}
