package de.hdm.gruppe1.Project4u.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe1.Project4u.server.db.*;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministration;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;

@SuppressWarnings("serial")
public class Project4uAdministrationImpl extends RemoteServiceServlet implements Project4uAdministration{
	
	private OrganisationseinheitMapper organisationseinheitMapper = null;
	
	public Project4uAdministrationImpl() throws IllegalArgumentException{
		
	}
	
	//Initialisierung
	public void init() throws IllegalArgumentException{
		
		this.organisationseinheitMapper = OrganisationseinheitMapper.organisationseinheitMapper();
		
	}
	
	public Organisationseinheit createOrganisationseinheit(String emailAddress, String vorname, String nachname)
		throws IllegalArgumentException{
		
		Organisationseinheit organisationseinheit = new Organisationseinheit();
		organisationseinheit.setEmailAddress(emailAddress);
		organisationseinheit.setVorname(vorname);
		organisationseinheit.setNachname(nachname);
		
		return this.organisationseinheitMapper.insert(organisationseinheit);
		
	}
	
	//Login-Status
	public Organisationseinheit checkStatus(Organisationseinheit loginInfo){
		return this.organisationseinheitMapper.checkStatus(loginInfo);
	}

}
