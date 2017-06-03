package de.hdm.gruppe1.Project4u.shared.bo;

public class Organisationseinheit extends BusinessObject{
	
	public enum organisationseinheitenTyp {
		Person,
		Team,
		Unternehmen
	}

	private static final long serialVersionUID = 1L;


	
	private int partnerprofilId;

	private int organisationseinheitId = 0;

	private String google_id;
	private String name;
	//TODO: variable "typ" muss als ein Enum definiert werden.
	private String typ;
	private boolean status = false;
	



	public String getGoogleId() {
		return google_id;
	}

	public void setGoogleId(String emailAddress) {
		this.google_id = emailAddress;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getOrganisationseinheitId() {
		return organisationseinheitId;
	}

	public void setOrganisationseinheitId(int organisationseinheitId) {
		this.organisationseinheitId = organisationseinheitId;
	}
	
	public int getPartnerprofilId() {
		return partnerprofilId;
	}
	
	public void setPartnerprofilId(int partnerprofilId) {
		this.partnerprofilId = partnerprofilId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTyp() {
		return typ;
	}
	
	public void setTyp(String typ) {
		this.typ = typ;
	}

	


}
