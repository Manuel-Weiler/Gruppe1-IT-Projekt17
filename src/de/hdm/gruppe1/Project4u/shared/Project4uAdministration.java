package de.hdm.gruppe1.Project4u.shared;

import com.google.gwt.user.client.rpc.RemoteService;

import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;

public interface Project4uAdministration extends RemoteService{
	
	public Organisationseinheit createOrganisationseinheit(String google_id, String name, String typ);
	
	public void init() throws IllegalArgumentException;
	
	public Organisationseinheit checkStatus(Organisationseinheit loginInfo) throws IllegalArgumentException;

}
