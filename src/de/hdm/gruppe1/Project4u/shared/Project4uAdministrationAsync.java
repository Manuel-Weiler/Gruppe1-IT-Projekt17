package de.hdm.gruppe1.Project4u.shared;


import java.util.Vector;

import java.util.ArrayList;


import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;
import de.hdm.gruppe1.Project4u.shared.bo.Projektmarktplatz;

public interface Project4uAdministrationAsync {

	void createOrganisationseinheit(String google_id, String name, String typ, Partnerprofil partnerprofil, AsyncCallback<Organisationseinheit> callback);

	/**
	 * @param callback
	 */
	
	//Organisationseinheit
	void deleteOrganisationseinheit(Organisationseinheit organisationseinheit, AsyncCallback<Void> callback);

	void getOrganisationseinheitById(int id, AsyncCallback<Organisationseinheit> callback);
	
	
	
	void init(AsyncCallback<Void> callback);
	
	void checkStatus(Organisationseinheit loginInfo, AsyncCallback<Organisationseinheit> callback);

	
	void findAllProjektmarktplatz(AsyncCallback<Vector<Projektmarktplatz>> callback);
	
	void testMethode (AsyncCallback<String> callback);

	
	void getAllAusschreibungen(AsyncCallback<ArrayList<Ausschreibung>> callback);

	void testMethode2(AsyncCallback<Vector<Projektmarktplatz>> callback);

	void createProjektmarktplatz(Projektmarktplatz p, AsyncCallback<Projektmarktplatz> callback);

	void update(Projektmarktplatz p, AsyncCallback<Void> callback);

	void update(Projekt p, AsyncCallback<Void> callback);

	void createProjekt(Projekt p, Projektmarktplatz pm, Organisationseinheit o, AsyncCallback<Projekt> callback);

	void findByProjektmarktplatz(Projektmarktplatz projektmarktplatz, AsyncCallback<Vector<Projekt>> callback);

	void findAusschreibungbyProjekt(Projekt projekt, AsyncCallback<Vector<Ausschreibung>> callback);

}
