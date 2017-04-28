package de.hdm.gruppe1.Project4u.shared.bo;

public class Projektmarktplatz extends BusinessObject{
	
	private static final long serialVersionUID = 1L;
	
	private int projektmarktplatzId = 0;
	private String name = "";

	
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
	
	
}
