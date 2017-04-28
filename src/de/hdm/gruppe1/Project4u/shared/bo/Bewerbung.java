package de.hdm.gruppe1.Project4u.shared.bo;

import java.util.Date;

public class Bewerbung extends Nutzer{

	private static final long serialVersionUID = 1L;
	
	private int bewerbungID = 0;
	private Date erstelldatum = null;
	private String bewerbungstext = null;

	public int getBewerbungID() {
		return bewerbungID;
	}

	public void setBewerbungID(int bewerbungID) {
		this.bewerbungID = bewerbungID;
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
