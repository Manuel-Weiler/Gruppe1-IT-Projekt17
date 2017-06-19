package de.hdm.gruppe1.Project4u.shared.bo;

public class Bewertung extends BusinessObject{

	private int bewertungId = 0;
	private int bewerbungId = 0;
	private float bewertungspunkte = 0;
	private String stellungnahme = null;

	public int getBewertungId() {
		return bewertungId;
	}

	public void setBewertungId(int bewertungId) {
		this.bewertungId = bewertungId;
	}
	
	public int getBewerbungId() {
		return bewerbungId;
	}

	public void setBewerbungID(int bewerbungId) {
		this.bewerbungId = bewerbungId;
	}


	public float getBewertungspunkte() {
		return bewertungspunkte;
	}

	public void setBewertungspunkte(float bewertungspunkte) {
		this.bewertungspunkte = bewertungspunkte;
	}

	public String getStellungnahme() {
		return stellungnahme;
	}

	public void setStellungnahme(String stellungnahme) {
		this.stellungnahme = stellungnahme;
	}

}
