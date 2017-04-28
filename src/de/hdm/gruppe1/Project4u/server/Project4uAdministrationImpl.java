package de.hdm.gruppe1.Project4u.server;

import java.util.Vector;

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
	
	public Organisationseinheit createOrganisationseinheit(String google_id, String name, String typ)
		throws IllegalArgumentException{
		
		Organisationseinheit organisationseinheit = new Organisationseinheit();
		organisationseinheit.setGoogleId(google_id);
		organisationseinheit.setName(name);
		
		return this.organisationseinheitMapper.insert(organisationseinheit);
		
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

}
