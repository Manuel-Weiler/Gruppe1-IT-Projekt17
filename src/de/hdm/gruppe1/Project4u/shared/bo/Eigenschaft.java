package de.hdm.gruppe1.Project4u.shared.bo;

public class Eigenschaft extends BusinessObject {

	private static final long serialVersionUID = 1L;
	
	private int eigenschaftId = 0;
	private String name;
	private String wert;
	
	/**
	 * Fremdschlüsselbeziehung zum Partnerprofil, dass die Eigenschaft beschreibt
	 */
	private int partnerprofilId;

	public int getEigenschaftId() {
		return eigenschaftId;
	}

	public void setEigenschaftId(int eigenschaftId) {
		this.eigenschaftId = eigenschaftId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWert() {
		return wert;
	}

	public void setWert(String wert) {
		this.wert = wert;
	}

	public int getPartnerprofilId() {
		return partnerprofilId;
	}

	public void setPartnerprofilId(int partnerprofilId) {
		this.partnerprofilId = partnerprofilId;
	}
	
	
}
