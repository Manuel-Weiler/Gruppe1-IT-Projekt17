package de.hdm.gruppe1.Project4u.test;

import de.hdm.gruppe1.Project4u.server.db.OrganisationseinheitMapper;
import de.hdm.gruppe1.Project4u.server.db.ProjektMapper;
import de.hdm.gruppe1.Project4u.server.db.ProjektmarktplatzMapper;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;
import de.hdm.gruppe1.Project4u.shared.bo.Projektmarktplatz;

public class ProjektTest {
	
 public static void main(String[] args) {
		
		
		Projekt p = new Projekt();
		
		p.setName("project4u");
    	// p.setEnddatum(enddatum);
		p.setBeschreibung("für it_projekt");
		
		
	   ProjektMapper pm = ProjektMapper.projektMapper();
	   ProjektmarktplatzMapper pmm = ProjektmarktplatzMapper.projektmarktplatzMapper();
       OrganisationseinheitMapper om= OrganisationseinheitMapper.organisationseinheitMapper();
	   Projektmarktplatz projektmarktplatz = new Projektmarktplatz();
	   projektmarktplatz = pmm.findById(1);
	   Organisationseinheit organisationseinheit = new Organisationseinheit();
	   organisationseinheit = om.findByKey(2);
				
	   pm.insert(p, projektmarktplatz, organisationseinheit );

}
}
