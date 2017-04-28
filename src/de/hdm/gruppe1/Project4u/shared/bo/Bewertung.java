package de.hdm.gruppe1.Project4u.shared.bo;

public class Bewertung {

	private int bewertungID = 0;
	private float bewertungspunkte = 0;
	private String stellungnahme = null;

	public int getBewertungID() {
		return bewertungID;
	}

	public void setBewertungID(int bewertungID) {
		this.bewertungID = bewertungID;
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
