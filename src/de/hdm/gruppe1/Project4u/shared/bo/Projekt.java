package de.hdm.gruppe1.Project4u.shared.bo;

import java.util.Date;

public class Projekt extends BusinessObject{
	
   private static final long serialVersionUID = 1L;

   private int projektId = 0;
   private String name = null;
   private Date startdatum = null;
   private Date enddatum= null;
   private String beschreibung= null;
   private int projektmarktplatzId = 0;
   private int organisationseinheitId = 0;
   
   
   public int getProjektId() {
		return projektId;
	}
   
   public void setProjektId(int projektId) {
		this.projektId = projektId;
	}
   
   public String getName() {
		return name;
	}
   
   public void setName(String name) {
		this.name = name;
	}
   
   public Date getStartdatum(){
		return startdatum;
	}
   
   public void setStartdatum(Date startdatum){
		this.startdatum = startdatum;
	}
   
   public Date getEnddatum() {
		return enddatum;
	}
   
   public void setEnddatum(Date enddatum) {
		this.enddatum = enddatum;
	}
   
   public String getBeschreibung() {
		return beschreibung;
	}
   
   public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
   
   public int getProjektmarktplatzId() {
		return projektmarktplatzId;
	}
   
   public void setProjektmarktplatzId(int projektmarktplatzId) {
		this.projektmarktplatzId = projektmarktplatzId;
	}
   
   public int getOrganisationseinheitId() {
		return organisationseinheitId;
	}
   
   public void setOrganisationseinheitId(int organisationseinheitId) {
		this.organisationseinheitId = organisationseinheitId;
	}
   
}