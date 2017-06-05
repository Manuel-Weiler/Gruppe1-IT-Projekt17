package de.hdm.gruppe1.Project4u.shared;


import java.util.Vector;

import java.util.ArrayList;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.gruppe1.Project4u.shared.bo.*;

@RemoteServiceRelativePath("projectadministration")
public interface Project4uAdministration extends RemoteService{

	public void init() throws IllegalArgumentException;
	
	
	public boolean checkStatus(LoginInfo loginInfo) throws IllegalArgumentException;
	

	public Vector<Projektmarktplatz> findAllProjektmarktplatz() throws IllegalArgumentException;

	//TODO: Testmethode l�schen
	String testMethode();

	public ArrayList<Ausschreibung> getAllAusschreibungen() throws IllegalArgumentException;
	
	//TODO: Testmethode l�schen
	public Vector<Projektmarktplatz> testMethode2() throws IllegalArgumentException;
	
	public Projektmarktplatz createProjektmarktplatz(Projektmarktplatz p) throws IllegalArgumentException;
	
	public void update(Projektmarktplatz p) throws IllegalArgumentException;

	public Vector<Projekt> findAllProjekteOfProjektmarktplatz(Projektmarktplatz pp) throws IllegalArgumentException;
	
	public void update(Projekt p) throws IllegalArgumentException;
	
	public Projekt createProjekt(Projekt p, Projektmarktplatz pm, Organisationseinheit o)
			   throws IllegalArgumentException;
	
	public Vector<Ausschreibung> findAusschreibungbyProjekt (Projekt projekt)throws IllegalArgumentException;
	
	public Eigenschaft updateEigenschaft(Eigenschaft e) throws IllegalArgumentException;
	
	public Partnerprofil createPartnerprofil() throws IllegalArgumentException;
	
	public Eigenschaft insertEigenschaft(Eigenschaft e, Partnerprofil p)throws IllegalArgumentException;
	
	public Organisationseinheit createOrganisationseinheit(Organisationseinheit orga) throws IllegalArgumentException;
	
	public Vector<Eigenschaft> getEigenschaftenOfOrganisationseinheit(Organisationseinheit orga)throws IllegalArgumentException;
	
	public Partnerprofil getPartnerprofilOfOrganisationseinheit(Organisationseinheit orga)throws IllegalArgumentException;
	
	public Organisationseinheit getOrganisationseinheitByUser(LoginInfo login)throws IllegalArgumentException;
	
	
}
