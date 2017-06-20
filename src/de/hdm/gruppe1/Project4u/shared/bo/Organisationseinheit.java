package de.hdm.gruppe1.Project4u.shared.bo;

public class Organisationseinheit extends BusinessObject{
	
	//Das Attribut Typ muss als Enum festgelegt werden, so dass nur die folgenden Werte verwendet werden können
	//und keine Fehler auftreten.
	public enum orgaTyp {
		Person,
		team,
		unternehmen;

	}

	private static final long serialVersionUID = 1L;


	
	private int partnerprofilId;

	private int organisationseinheitId = 0;

	private String google_id;
	private String name;
	private orgaTyp typ;
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
		return typ.toString();
	}
	
	public void setTyp(String typ) {
		this.typ = orgaTyp.valueOf(typ);
	}

	


}
