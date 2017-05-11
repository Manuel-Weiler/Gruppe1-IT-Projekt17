package de.hdm.gruppe1.Project4u.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;

import de.hdm.gruppe1.Project4u.shared.bo.*;

public interface Project4uAdministration extends RemoteService{

	public void init() throws IllegalArgumentException;
	
	public Organisationseinheit createOrganisationseinheit(String google_id, String name, String typ);
	
	public Organisationseinheit checkStatus(Organisationseinheit loginInfo) throws IllegalArgumentException;
	
	public ArrayList<Ausschreibung> getAllAusschreibungen() throws IllegalArgumentException;
	

}
