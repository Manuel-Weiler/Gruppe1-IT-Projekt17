package de.hdm.gruppe1.Project4u.shared.bo;

import java.util.Date;

public class Bewerbung extends BusinessObject{

	private static final long serialVersionUID = 1L;
	
	private int bewerbungId = 0;
	private int organisationseinheitId;
	private int ausschreibungId;
	private Date erstelldatum = null;
	private String bewerbungstext = null;
	//TODO: status als enum implementieren
	private String status = null;

	public int getBewerbungId() {
		return bewerbungId;
	}

	public void setBewerbungId(int bewerbungID) {
		this.bewerbungId = bewerbungID;
	}
	
	public int getOrganisationseinheitId() {
		return organisationseinheitId;
	}
	
	public void setOrganisationseinheitId(int organisationseinheitId) {
		this.organisationseinheitId = organisationseinheitId;
	}
	
	public int getAusschreibungId() {
		return ausschreibungId;
	}
	
	public void setAusschreibungId(int ausschreibungId) {
		this.ausschreibungId = ausschreibungId;
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

	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
