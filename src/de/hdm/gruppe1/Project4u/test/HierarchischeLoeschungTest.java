package de.hdm.gruppe1.Project4u.test;


import de.hdm.gruppe1.Project4u.server.Project4uAdministrationImpl;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;


public class HierarchischeLoeschungTest {

	public static void main(String[] args) {
		
		Project4uAdministrationImpl aImpl = new Project4uAdministrationImpl();
		Organisationseinheit orga = new Organisationseinheit();
		Partnerprofil partnerprofil = new Partnerprofil();
		
		partnerprofil = aImpl.createPartnerprofil();
		orga.setGoogleId("max.frisch@gmx.de");
		orga.setName("Max Frisch");
		orga.setTyp("Typ");
		orga.setPartnerprofilId(partnerprofil.getID());
		orga = aImpl.createOrganisationseinheit(orga);

	}

}
