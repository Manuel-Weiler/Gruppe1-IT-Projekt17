package de.hdm.gruppe1.Project4u.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.gruppe1.Project4u.shared.bo.Nutzer;

public interface Project4uAdministrationAsync {

	void createNutzer(String emailAddress, String vorname, String nachname, AsyncCallback<Nutzer> callback);

	/**
	 * @param callback
	 */
	void init(AsyncCallback<Void> callback);
	
	void checkStatus(Nutzer loginInfo, AsyncCallback<Nutzer> callback);
}
