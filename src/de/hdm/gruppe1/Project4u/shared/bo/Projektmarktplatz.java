package de.hdm.gruppe1.Project4u.shared.bo;

public class Projektmarktplatz extends BusinessObject{
	
	private static final long serialVersionUID = 1L;
	
	private int projektmarktplatzId = 0;
	private String name = "";
	private String passwort = null;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
	    this.name = name;
	}
	
	public int getProjektmarktplatzId() {
		return projektmarktplatzId;
	}

	public void setProjektmarktplatzId(int projektmarktplatzId) {
		this.projektmarktplatzId = projektmarktplatzId;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}
	
	
}
