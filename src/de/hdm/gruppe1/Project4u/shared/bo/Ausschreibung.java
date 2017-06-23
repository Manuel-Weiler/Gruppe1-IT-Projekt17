package de.hdm.gruppe1.Project4u.shared.bo;

import java.util.Date;

public class Ausschreibung extends BusinessObject{

	private static final long serialVersionUID = 1L;
	
	private int ausschreibungId = 0; 
	private String bezeichnung;
	private String nameProjektleiter;
	private Date bewerbungsfrist; 
	private String ausschreibungstext;

	private Date erstellDatum;	
	private int projektId;
	private int partnerprofilId;
	private ausschreibungStatus status;
	
	public enum ausschreibungStatus {
		laufend,
		beendet;
	}


	

	public String getStatus() {
		return status.toString();
	}

	public void setStatus(String status) {
		this.status = ausschreibungStatus.valueOf(status);
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public String getAusschreibungstext() {
		return ausschreibungstext;
	}

	public void setAusschreibungstext(String ausschreibungstext) {
		this.ausschreibungstext = ausschreibungstext;
	}

	public Date getErstellDatum() {
		return erstellDatum;
	}

	public void setErstellDatum(Date erstellDatum) {
		this.erstellDatum = erstellDatum;
	}
	

	public String getNameProjektleiter() {
		return nameProjektleiter;
	}

	public void setNameProjektleiter(String nameProjektleiter) {
		this.nameProjektleiter = nameProjektleiter;
	}

	public Date getBewerbungsfrist() {
		return bewerbungsfrist;
	}

	public void setBewerbungsfrist(Date bewerbungsfrist) {
		this.bewerbungsfrist = bewerbungsfrist;
	}

	public int getProjektId() {
		return projektId;
	}

	public void setProjektId(int projektId) {
		this.projektId = projektId;
	}

	public int getPartnerprofilId() {
		return partnerprofilId;
	}

	public void setPartnerprofilId(int partnerprofilId) {
		this.partnerprofilId = partnerprofilId;
	}

	public int getAusschreibungId() {
		return ausschreibungId;
	}

	public void setAusschreibungId(int ausschreibungId) {
		this.ausschreibungId = ausschreibungId;
	}
	
}
	