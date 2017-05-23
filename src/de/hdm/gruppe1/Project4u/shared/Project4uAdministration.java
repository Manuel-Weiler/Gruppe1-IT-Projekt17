package de.hdm.gruppe1.Project4u.shared;


import java.util.Vector;

import java.util.ArrayList;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.gruppe1.Project4u.shared.bo.*;

@RemoteServiceRelativePath("projectadministration")
public interface Project4uAdministration extends RemoteService{

	public void init() throws IllegalArgumentException;
	
	public Organisationseinheit createOrganisationseinheit(String google_id, String name, String typ);
	
	public Organisationseinheit checkStatus(Organisationseinheit loginInfo) throws IllegalArgumentException;
	

	public Vector<Projektmarktplatz> findAllProjektmarktplatz() throws IllegalArgumentException;

	String testMethode();

	public ArrayList<Ausschreibung> getAllAusschreibungen() throws IllegalArgumentException;
	
	public ArrayList<Bewerbung> getBewerbungenOf(Ausschreibung aus)
	throws IllegalArgumentException;



}
