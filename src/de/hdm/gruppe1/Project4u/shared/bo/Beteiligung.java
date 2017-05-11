package de.hdm.gruppe1.Project4u.shared.bo;

import java.sql.Date;

public class Beteiligung extends BusinessObject{
		
	private static final long serialVersionUID = 1L;
	
	private int beteiligungId = 0;
	
	private Date startdatum ;
	
	private Date enddatum; 
	
	private int personentage;
	
	private int organisationseinheitId; 
	
	private int projektId;
	
	private int bewertungId;
	
	

	public int getBeteiligungId() {
		return beteiligungId;
	}
	public void setBeteiligungId(int beteiligungId) {
		this.beteiligungId = beteiligungId;
	}
	public Date getStartdatum() {
		return startdatum;
	}
	public void setStartdatum(Date startdatum) {
		this.startdatum = startdatum;
	}
	public Date getEnddatum() {
		return enddatum;
	}
	public void setEnddatum(Date enddatum) {
		this.enddatum = enddatum;
	}
	public int getPersonentage() {
		return personentage;
	}
	public void setPersonentage(int personentage) {
		this.personentage = personentage;
	}
	public int getOrganisationseinheitId() {
		return organisationseinheitId;
	}
	public void setOrganisationseinheitId(int organisationseinheitId) {
		this.organisationseinheitId = organisationseinheitId;
	}
	public int getProjektId() {
		return projektId;
	}
	public void setProjektId(int projektId) {
		this.projektId = projektId;
	}
	public int getBewertungId() {
		return bewertungId;
	}
	public void setBewertungId(int bewertungId) {
		this.bewertungId = bewertungId;
	}
	


	
	


	
 
}
