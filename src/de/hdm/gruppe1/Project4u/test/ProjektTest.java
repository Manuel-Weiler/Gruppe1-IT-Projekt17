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
		
	     p.setName("hdm");
		 p.setBeschreibung("für it_projekt");
		
		// p.setEnddatum(enddatum); TODO eigenes Datum setzen!
		
	   ProjektMapper pm = ProjektMapper.projektMapper();
	   ProjektmarktplatzMapper pmm = ProjektmarktplatzMapper.projektmarktplatzMapper();
       OrganisationseinheitMapper om= OrganisationseinheitMapper.organisationseinheitMapper();
	   Projektmarktplatz projektmarkt = new Projektmarktplatz();
	   projektmarkt = pmm.findById(1);
	   Organisationseinheit orga = new Organisationseinheit();
	   orga = om.findByKey(2);
				
	   pm.insert(p, projektmarkt, orga );

}
}
