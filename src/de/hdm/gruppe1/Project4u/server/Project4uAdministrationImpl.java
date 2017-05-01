package de.hdm.gruppe1.Project4u.server;

import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe1.Project4u.server.db.*;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministration;
import de.hdm.gruppe1.Project4u.shared.bo.Beteiligung;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;

@SuppressWarnings("serial")
public class Project4uAdministrationImpl extends RemoteServiceServlet implements Project4uAdministration{
	

	
	private BeteiligungMapper beteiligungMapper = null;


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
	
	public Organisationseinheit createOrganisationseinheit(String google_id, String name, String typ)
		throws IllegalArgumentException{
		
		Organisationseinheit organisationseinheit = new Organisationseinheit();
		organisationseinheit.setGoogleId(google_id);
		organisationseinheit.setName(name);
		organisationseinheit.setTyp(typ);
		
		return this.organisationseinheitMapper.insert(organisationseinheit);
		
	}
	
/*
 *  Insert- Methode für die Beteiligung muss verfasst werden
 */
	
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
	 * ABSCHNITT, Beginn: Partnerprofil
	 * #########################################################################
	 * 
	 */

	
	/*
	 * #########################################################################
	 * ABSCHNITT, Ende: Partnerprofil
	 * #########################################################################
	 * 
	 */

}
