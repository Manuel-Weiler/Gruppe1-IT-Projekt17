package de.hdm.gruppe1.Project4u.shared;


import java.util.Vector;

import java.util.ArrayList;


import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Eigenschaft;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;
import de.hdm.gruppe1.Project4u.shared.bo.Projektmarktplatz;

public interface Project4uAdministrationAsync {

	

	/**
	 * @param callback
	 */
	void init(AsyncCallback<Void> callback);
	
	

	
	void findAllProjektmarktplatz(AsyncCallback<Vector<Projektmarktplatz>> callback);
	
	void testMethode (AsyncCallback<String> callback);

	
	void getAllAusschreibungen(AsyncCallback<ArrayList<Ausschreibung>> callback);

	void testMethode2(AsyncCallback<Vector<Projektmarktplatz>> callback);

	void createProjektmarktplatz(Projektmarktplatz p, AsyncCallback<Projektmarktplatz> callback);

	void update(Projektmarktplatz p, AsyncCallback<Void> callback);

	void findAllProjekteOfProjektmarktplatz(Projektmarktplatz pp, AsyncCallback<Vector<Projekt>> callback);

	void update(Projekt p, AsyncCallback<Void> callback);

	void createProjekt(Projekt p, Projektmarktplatz pm, Organisationseinheit o, AsyncCallback<Projekt> callback);

	void findAusschreibungbyProjekt(Projekt projekt, AsyncCallback<Vector<Ausschreibung>> callback);

	void checkStatus(LoginInfo loginInfo, AsyncCallback<Boolean> callback);

	void updateEigenschaft(Eigenschaft e, AsyncCallback<Eigenschaft> callback);

	void createPartnerprofil(AsyncCallback<Partnerprofil> callback);

	void insertEigenschaft(Eigenschaft e, Partnerprofil p, AsyncCallback<Eigenschaft> callback);




	void createOrganisationseinheit(Organisationseinheit orga, AsyncCallback<Organisationseinheit> callback);




	void getEigenschaftenOfOrganisationseinheit(Organisationseinheit orga, AsyncCallback<Vector<Eigenschaft>> callback);




	void getPartnerprofilOfOrganisationseinheit(Organisationseinheit orga, AsyncCallback<Partnerprofil> callback);




	void getOrganisationseinheitByUser(LoginInfo login, AsyncCallback<Organisationseinheit> callback);




	void getAllOrganisationseinheitenOfTypTeamUnternehmen(AsyncCallback<Vector<Organisationseinheit>> callback);

}
