package de.hdm.gruppe1.Project4u.shared;


import java.util.Vector;

import java.util.ArrayList;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Projektmarktplatz;

import de.hdm.gruppe1.Project4u.shared.bo.*;

@RemoteServiceRelativePath("projectadministration")
public interface Project4uAdministration extends RemoteService{

	public void init() throws IllegalArgumentException;
	
	public Organisationseinheit createOrganisationseinheit(String google_id, String name, String typ);
	
	public Organisationseinheit checkStatus(Organisationseinheit loginInfo) throws IllegalArgumentException;
	

	public Vector<Projektmarktplatz> findAllProjektmarktplatz() throws IllegalArgumentException;

	//TODO: Testmethode löschen
	String testMethode();

	public ArrayList<Ausschreibung> getAllAusschreibungen() throws IllegalArgumentException;
	
	//TODO: Testmethode löschen
	public Vector<Projektmarktplatz> testMethode2() throws IllegalArgumentException;
	
	public Projektmarktplatz createProjektmarktplatz(Projektmarktplatz p) throws IllegalArgumentException;
	
	public void update(Projektmarktplatz p) throws IllegalArgumentException;


}
