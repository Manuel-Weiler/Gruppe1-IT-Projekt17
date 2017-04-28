package de.hdm.gruppe1.Project4u.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;

public interface Project4uAdministrationAsync {

	void createOrganisationseinheit(String google_id, String name, String typ, AsyncCallback<Organisationseinheit> callback);

	/**
	 * @param callback
	 */
	void init(AsyncCallback<Void> callback);
	
	void checkStatus(Organisationseinheit loginInfo, AsyncCallback<Organisationseinheit> callback);
}
