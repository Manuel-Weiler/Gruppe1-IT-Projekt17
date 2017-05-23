package de.hdm.gruppe1.Project4u.shared.bo;

import java.util.Date;

public class Bewerbung{

	private static final long serialVersionUID = 1L;
	
	private int bewerbungId = 0;
	private int organisationseinheitId;
	private Date erstelldatum = null;
	private String bewerbungstext = null;

	public int getBewerbungID() {
		return bewerbungId;
	}

	public void setBewerbungID(int bewerbungID) {
		this.bewerbungId = bewerbungID;
	}
	
	public int getOrganisationseinheitId() {
		return organisationseinheitId;
	}
	
	public void setOrganisationseinheitId(int organisationseinheitId) {
		this.organisationseinheitId = organisationseinheitId;
	}

	public Date getErstelldatum() {
		return erstelldatum;
	}

	public void setErstelldatum(Date erstelldatum) {
		this.erstelldatum = erstelldatum;
	}

	public String getBewerbungstext() {
		return bewerbungstext;
	}

	public void setBewerbungstext(String bewerbungstext) {
		this.bewerbungstext = bewerbungstext;
	}

}
