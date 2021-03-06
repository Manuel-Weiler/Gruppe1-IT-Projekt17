package de.hdm.gruppe1.Project4u.shared;


import java.util.Vector;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;

import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Beteiligung;

import de.hdm.gruppe1.Project4u.shared.bo.Bewerbung;
import de.hdm.gruppe1.Project4u.shared.bo.Bewertung;
import de.hdm.gruppe1.Project4u.shared.bo.Eigenschaft;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;
import de.hdm.gruppe1.Project4u.shared.bo.Projektmarktplatz;


public interface Project4uAdministrationAsync {

	

	/**
	 * @param callback
	 */
	
	
	
	void findBeteiligungByOrganisationseinheitAndProjekt(Organisationseinheit o, Projekt p, AsyncCallback<Beteiligung> callback);
	
	void findBeteiligungByOrganisationseinheitAndProjekt(LoginInfo login, Projekt p, AsyncCallback<Beteiligung> callback);
	
	void deleteBeteiligung(Beteiligung beteiligung, AsyncCallback<Void> callback);
	
	//////ORGANISATIONSEINHEIT//////
	void deleteOrganisationseinheit(Organisationseinheit organisationseinheit, AsyncCallback<Void> callback);

	void getOrganisationseinheitById(int id, AsyncCallback<Organisationseinheit> callback);
	
	void getPersonTeamUnternehmenByUser(LoginInfo login, AsyncCallback<Organisationseinheit> callback);
	//////ENDE ORGANISATIONSEINHEIT/////
	
	//////PROJEKT//////
	void findProjektByOrganisationseinheit(Organisationseinheit organisationseinheit, AsyncCallback<Vector<Projekt>> callback);
	void findProjekteOfBeteiligteOrganisationseinheit(LoginInfo loginInfo, AsyncCallback<Vector<Projekt>> callback);
	//////PROJEKT ENDE//////
	
	void init(AsyncCallback<Void> callback);

	
	void findAllProjektmarktplatz(AsyncCallback<Vector<Projektmarktplatz>> callback);
	
	void testMethode (AsyncCallback<String> callback);
	
	void getAlleAusschreibungen(AsyncCallback<ArrayList<Ausschreibung>> callback);

	void testMethode2(AsyncCallback<Vector<Projektmarktplatz>> callback);

	void createProjektmarktplatz(Projektmarktplatz p, LoginInfo login, AsyncCallback<Projektmarktplatz> callback);

	void update(Projektmarktplatz p, AsyncCallback<Void> callback);

	void update(Projekt p, AsyncCallback<Void> callback);


	void createProjekt(Projekt p, Projektmarktplatz pm, LoginInfo login, AsyncCallback<Projekt> callback);


	void findByProjektmarktplatz(Projektmarktplatz projektmarktplatz, AsyncCallback<Vector<Projekt>> callback);


	void checkStatus(LoginInfo loginInfo, AsyncCallback<Boolean> callback);

	void updateEigenschaft(Eigenschaft e, AsyncCallback<Eigenschaft> callback);

	void createPartnerprofil(AsyncCallback<Partnerprofil> callback);

	void insertEigenschaft(Eigenschaft e, Partnerprofil p, AsyncCallback<Eigenschaft> callback);

	void createOrganisationseinheit(Organisationseinheit orga, AsyncCallback<Organisationseinheit> callback);

	void getEigenschaftenOfOrganisationseinheit(Organisationseinheit orga, AsyncCallback<Vector<Eigenschaft>> callback);

	void getPartnerprofilOfOrganisationseinheit(Organisationseinheit orga, AsyncCallback<Partnerprofil> callback);

	void getOrganisationseinheitByUser(LoginInfo login, AsyncCallback<Organisationseinheit> callback);

	void getAllOrganisationseinheitenOfTypTeamUnternehmen(AsyncCallback<Vector<Organisationseinheit>> callback);

	void insertLinkedTeamUnternehmenOfOrganisationseinheit(LoginInfo login, Organisationseinheit teamunternehmen,
			AsyncCallback<Void> callback);

	void getLinkedTeamAndUnternehmenOfOrganisationseinheit(LoginInfo login,
			AsyncCallback<Vector<Organisationseinheit>> callback);

	void getBeteiligungForOrga(Organisationseinheit orga, AsyncCallback<Vector<Beteiligung>> callback);
	
	void getAusschreibungenForPartnerprofil(Organisationseinheit orga, AsyncCallback<Vector<Ausschreibung>> callback);

	void getBewerbungForOrganisationseinheit(Organisationseinheit orga, AsyncCallback<Vector<Bewerbung>> callback);

	void getAllOrganisationseinheitOfTypPerson(AsyncCallback<Vector<Organisationseinheit>> callback);

	void getAllOrganisationseinheiten(AsyncCallback<Vector<Organisationseinheit>> callback);

	void getAusschreibungenForOrga(Organisationseinheit orga, AsyncCallback<Vector<Ausschreibung>> callback);

	void deleteLinkedTeamUnternehmenOfOrganisationseinheit(LoginInfo login, Organisationseinheit team,
			AsyncCallback<Void> callback);

	void deleteEigenschaft(Eigenschaft e, AsyncCallback<Void> callback);

	void deleteAllEigenschaftenOfOrganisationseinheit(Organisationseinheit orga, AsyncCallback<Void> callback);

	void insertEigenschaften(Vector<Eigenschaft> eigenschaften, Organisationseinheit orga,
			AsyncCallback<Void> callback);

	void updateOrganisationseinheit(Organisationseinheit organisationseinheit, AsyncCallback<Void> callback);

	void deletePartnerprofil(Partnerprofil p, AsyncCallback<Void> callback);

	void createAusschreibung(Ausschreibung a, int partnerprofilId, Projekt pr, AsyncCallback<Ausschreibung> callback);

	void insertEigenschaftenByPartnerprofil(Vector<Eigenschaft> eigenschaften, int partnerprofilId,
			AsyncCallback<Void> callback);

	void getAllEigenschaftenByPartnerprofilId(int partnerprofilId, AsyncCallback<Vector<Eigenschaft>> callback);

	void deleteAllEigenschaftOfPartnerprofil(int partnerprofilId, AsyncCallback<Void> callback);

	void updateAusschreibung(Ausschreibung ausschreibung, AsyncCallback<Ausschreibung> callback);

	void findProjektleiterOfProjects(Vector<Projekt> projekte, AsyncCallback<Vector<Organisationseinheit>> callback);

	void createBewerbung(Bewerbung bewerbung, int ausschreibungId, int organisationsId,
			AsyncCallback<Bewerbung> callback);

	void getAllBewerbungenOfUser(LoginInfo login, AsyncCallback<Vector<Bewerbung>> callback);

	void findByIdAusschreibung(int id, AsyncCallback<Ausschreibung> callback);

	void findProjektById(int id, AsyncCallback<Projekt> callback);

	void getAllBewerbungenOfLinkedTeamAndUnternehmen(LoginInfo login, AsyncCallback<Vector<Bewerbung>> callback);

	void deleteBewerbung(Bewerbung bewerbung, AsyncCallback<Void> callback);

	void getEingangsbewerbungenOfProjektleiter(LoginInfo login, AsyncCallback<Vector<Bewerbung>> callback);

	void createBewertung(Bewertung bewertung, AsyncCallback<Bewertung> callback);


	void getProjektOfBewerbung(Bewerbung bewerbung, AsyncCallback<Projekt> callback);

	void updateStatusOfAusschreibung(int ausschreibungId, String status, AsyncCallback<Ausschreibung> callback);

	void updateStatusOfBewerbung(String status, int bewerbungsID, AsyncCallback<Void> callback);

	void getBewerbungenOfAusschreibungWithStatusAusstehend(Ausschreibung aus,
			AsyncCallback<Vector<Bewerbung>> callback);

	void cancelAllBewerbungenOfAusschreibungWithStatusAusstehend(Ausschreibung aus, AsyncCallback<Void> callback);

	void findActiveAusschreibungenOfProjekt(Projekt projekt, AsyncCallback<Vector<Ausschreibung>> callback);

	void getBewertungOfBewerbung(Bewerbung b, AsyncCallback<Bewertung> callback);

	void createBeteiligungAndUpdateAllOtherBewerbungenAndUpdateAusschreibung(Bewerbung bewerbung, Bewertung bertung,
			AsyncCallback<Void> callback);

	void getAllBewerbungen(AsyncCallback<Vector<Bewerbung>> callback);

	void deleteProjekt(Projekt projekt, AsyncCallback<Void> callback);

	void delete(Projektmarktplatz p, AsyncCallback<Void> callback);


}
