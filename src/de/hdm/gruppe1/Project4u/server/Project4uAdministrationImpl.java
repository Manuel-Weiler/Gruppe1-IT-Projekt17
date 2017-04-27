package de.hdm.gruppe1.Project4u.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe1.Project4u.server.db.*;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministration;
import de.hdm.gruppe1.Project4u.shared.bo.Beteiligung;
import de.hdm.gruppe1.Project4u.shared.bo.Nutzer;

@SuppressWarnings("serial")
public class Project4uAdministrationImpl extends RemoteServiceServlet implements Project4uAdministration{
	
	private NutzerMapper nutzerMapper = null;
	
	private BeteiligungMapper beteiligungMapper = null;

	
	public Project4uAdministrationImpl() throws IllegalArgumentException{
		
	}
	
	//Initialisierung
	public void init() throws IllegalArgumentException{
		
		this.nutzerMapper = NutzerMapper.nutzerMapper();
		this.beteiligungMapper = BeteiligungMapper.beteiligungMapper(); 
		
	}
	
	public Nutzer createNutzer(String emailAddress, String vorname, String nachname)
		throws IllegalArgumentException{
		
		Nutzer nutzer = new Nutzer();
		nutzer.setEmailAddress(emailAddress);
		nutzer.setVorname(vorname);
		nutzer.setNachname(nachname);
		
		return this.nutzerMapper.insert(nutzer);
		
	}
	
/*
 *  Insert- Methode für die Beteiligung muss verfasst werden
 */
	
	//Login-Status
	public Nutzer checkStatus(Nutzer loginInfo){
		return this.nutzerMapper.checkStatus(loginInfo);
	}

}
