package de.hdm.gruppe1.Project4u.shared;


import java.util.Vector;

import java.util.ArrayList;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.gruppe1.Project4u.shared.bo.*;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAlleAusschreibungen;

@RemoteServiceRelativePath("projectadministration")
public interface Project4uAdministration extends RemoteService{

	public void init() throws IllegalArgumentException;
	
	

	public boolean checkStatus(LoginInfo loginInfo) throws IllegalArgumentException;

	public void deleteOrganisationseinheit(Organisationseinheit organisationseinheit) throws IllegalArgumentException;
	
	public Organisationseinheit getOrganisationseinheitById(int id) throws IllegalArgumentException;
	
	public Vector<Projektmarktplatz> findAllProjektmarktplatz() throws IllegalArgumentException;

	//TODO: Testmethode l�schen
	String testMethode();

	public ArrayList<Ausschreibung> getAlleAusschreibungen() throws IllegalArgumentException;
	
	public Ausschreibung createAusschreibung(Ausschreibung a, int partnerprofilId, Projekt pr)throws IllegalArgumentException;
	
	//TODO: Testmethode l�schen
	public Vector<Projektmarktplatz> testMethode2() throws IllegalArgumentException;
	
	public Projektmarktplatz createProjektmarktplatz(Projektmarktplatz p, LoginInfo login)throws IllegalArgumentException;
	
	public void update(Projektmarktplatz p) throws IllegalArgumentException;

	public void update(Projekt p) throws IllegalArgumentException;
	
	public Projekt createProjekt(Projekt p, Projektmarktplatz pm, LoginInfo login)
			   throws IllegalArgumentException;

	
	public Vector<Ausschreibung> findAusschreibungbyProjekt (Projekt projekt)throws IllegalArgumentException;
	
	public Eigenschaft updateEigenschaft(Eigenschaft e) throws IllegalArgumentException;
	
	public Partnerprofil createPartnerprofil() throws IllegalArgumentException;
	
	public void deletePartnerprofil(Partnerprofil p) throws IllegalArgumentException;
	
	public Eigenschaft insertEigenschaft(Eigenschaft e, Partnerprofil p)throws IllegalArgumentException;
	
	public void insertEigenschaften(Vector<Eigenschaft> eigenschaften, Organisationseinheit orga)throws IllegalArgumentException;
	
	public void insertEigenschaftenByPartnerprofil(Vector<Eigenschaft> eigenschaften, int partnerprofilId)throws IllegalArgumentException;
	
	public void deleteEigenschaft(Eigenschaft e) throws IllegalArgumentException;
	
	public Organisationseinheit createOrganisationseinheit(Organisationseinheit orga) throws IllegalArgumentException;
	
	public void updateOrganisationseinheit(Organisationseinheit organisationseinheit) throws IllegalArgumentException;
	
	public Vector<Eigenschaft> getEigenschaftenOfOrganisationseinheit(Organisationseinheit orga)throws IllegalArgumentException;
	
	public void deleteAllEigenschaftenOfOrganisationseinheit(Organisationseinheit orga)throws IllegalArgumentException;
	
	public void deleteAllEigenschaftOfPartnerprofil(int partnerprofilId)throws IllegalArgumentException;
	
	public Partnerprofil getPartnerprofilOfOrganisationseinheit(Organisationseinheit orga)throws IllegalArgumentException;
	
	public Organisationseinheit getOrganisationseinheitByUser(LoginInfo login)throws IllegalArgumentException;
	
	public Vector<Organisationseinheit> getAllOrganisationseinheitenOfTypTeamUnternehmen()throws IllegalArgumentException;
	
	public void insertLinkedTeamUnternehmenOfOrganisationseinheit(LoginInfo login,
			Organisationseinheit teamunternehmen) throws IllegalArgumentException;
	
	public Vector<Organisationseinheit> getLinkedTeamAndUnternehmenOfOrganisationseinheit(LoginInfo login)
			throws IllegalArgumentException;
	
	public Vector<Eigenschaft> getAllEigenschaftenByPartnerprofilId(int partnerprofilId)throws IllegalArgumentException;
	
	public Vector<Projekt> findByProjektmarktplatz (Projektmarktplatz projektmarktplatz) throws IllegalArgumentException;

	public Vector<Beteiligung> getBeteiligungForOrga(Organisationseinheit orga) throws IllegalArgumentException;

	public Vector<Ausschreibung> getAusschreibungenForPartnerprofil(Organisationseinheit orga)
			throws IllegalArgumentException;
	
	public Vector<Bewerbung> getBewerbungForOrganisationseinheit(Organisationseinheit orga) throws IllegalArgumentException;

	public Vector<Organisationseinheit> getAllOrganisationseinheitOfTypPerson()	throws IllegalArgumentException;
	
	public Vector<Organisationseinheit> getAllOrganisationseinheiten() throws IllegalArgumentException;
	
	public Vector<Ausschreibung> getAusschreibungenForOrga(Organisationseinheit orga) throws IllegalArgumentException;

	public void deleteLinkedTeamUnternehmenOfOrganisationseinheit(LoginInfo login,
			Organisationseinheit team) throws IllegalArgumentException;

	public Ausschreibung updateAusschreibung(Ausschreibung ausschreibung) throws IllegalArgumentException ;
	
	public Ausschreibung findByIdAusschreibung(int id) throws IllegalArgumentException;

}
