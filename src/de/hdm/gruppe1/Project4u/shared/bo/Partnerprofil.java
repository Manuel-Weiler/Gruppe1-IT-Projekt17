package de.hdm.gruppe1.Project4u.shared.bo;

import java.util.Date;

public class Partnerprofil extends BusinessObject{
	
	private static final long serialVersionUID = 1L;

	private int partnerprofilId=0;
	private Date erstelldatum;
	private Date aenderungsdatum;
	private int organisationseinheitId;
	

	public Date getErstelldatum() {
		return erstelldatum;
	}

	public void setErstelldatum(Date erstelldatum) {
		this.erstelldatum = erstelldatum;
	}

	public Date getAenderungsdatum() {
		return aenderungsdatum;
	}

	public void setAenderungsdatum(Date aenderungsdatum) {
		this.aenderungsdatum = aenderungsdatum;
	}

	public int getPartnerprofilId() {
		return partnerprofilId;
	}

	public void setPartnerprofilId(int partnerprofilId) {
		this.partnerprofilId = partnerprofilId;
	}

	public int getOrganisationseinheitId() {
		return organisationseinheitId;
	}

	public void setOrganisationseinheitId(int organisationseinheitId) {
		this.organisationseinheitId = organisationseinheitId;
	}
	
	
	
}
