package de.hdm.gruppe1.Project4u.shared.bo;

import java.util.Date;

public class Ausschreibung extends BusinessObject{

	
	private static final long serialVersionUID = 1L;

	private int ausschreibungID = 0; 
	
	private String bezeichnung;
	
	private Date ablaufdatum; 
	
	private String ausschreibungstext;
	
	
	
	Ausschreibung Ausschreibung = new Ausschreibung (); 
	
	public String getBezeichnung() {
		
		return bezeichnung;
	}
	
	public void setBezeichnung (String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
	public Date getAblaufdatum() {
		
		return ablaufdatum;
	}
	
	public void setAblaufdatum (Date ablaufdatum) {
		this.ablaufdatum = ablaufdatum;
	}
	
	public String getAusschreibungstext() {
		
		return ausschreibungstext;
	}
	
	public void setAusschreibungstext (String ausschreibungstext) {
		this.ausschreibungstext = ausschreibungstext;
	}

	public int getAusschreibungID() {
		return ausschreibungID;
	}

	public void setAusschreibungID(int ausschreibungID) {
		this.ausschreibungID = ausschreibungID;
	}
	
/** Brauchen wir das?
 * @author Dominik
 * 
 * 	public boolean equals(Object o){
		if(o != null && o instanceof Ausschreibung){
			Ausschreibung au = (Ausschreibung) o;
			try{
				return super.equals(au);
			}
			catch(IllegalArgumentException e){
				return false;
			}
		}
		return false;
	}
 */

}
