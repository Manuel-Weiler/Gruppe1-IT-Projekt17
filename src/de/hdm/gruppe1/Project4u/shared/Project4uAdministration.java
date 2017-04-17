package de.hdm.gruppe1.Project4u.shared;

import com.google.gwt.user.client.rpc.RemoteService;

import de.hdm.gruppe1.Project4u.shared.bo.Nutzer;

public interface Project4uAdministration extends RemoteService{
	
	public Nutzer createNutzer(String emailAddress, String vorname, String nachname) throws IllegalArgumentException;
	
	public void init() throws IllegalArgumentException;
	
	public Nutzer checkStatus(Nutzer loginInfo) throws IllegalArgumentException;

}
