package de.hdm.gruppe1.Project4u.test;

import java.util.Vector;

import de.hdm.gruppe1.Project4u.server.db.BeteiligungMapper;
import de.hdm.gruppe1.Project4u.server.db.BewerbungMapper;
import de.hdm.gruppe1.Project4u.server.db.OrganisationseinheitMapper;
import de.hdm.gruppe1.Project4u.server.db.ProjektMapper;
import de.hdm.gruppe1.Project4u.server.db.ProjektmarktplatzMapper;
import de.hdm.gruppe1.Project4u.shared.bo.Beteiligung;
import de.hdm.gruppe1.Project4u.shared.bo.Bewerbung;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;
import de.hdm.gruppe1.Project4u.shared.bo.Projektmarktplatz;

public class ProjektTest {
	
 public static void main(String[] args) {
		
//
	 BeteiligungMapper bm = new BeteiligungMapper();
	 OrganisationseinheitMapper om = new OrganisationseinheitMapper();
	 BewerbungMapper bwm = new BewerbungMapper();
	 
	 Organisationseinheit orga = om.findByKey(2); 
//			Vector<Beteiligung> result = new Vector<>();
//			
//			Vector<Beteiligung> beteiligungen = bm.findByOrganisationseinheit(orga);
//			
//			if(beteiligungen !=null){
//				result.addAll(beteiligungen);
//				
//			}
//			for(Beteiligung b : result){
//				System.out.println(b.getPersonentage());
//		}
	 
	 
	 
				Vector<Bewerbung> result = new Vector<>();
				
				Vector<Bewerbung> bewerbung = bwm.findByOrganisationseinheit(orga);
				
				if (bewerbung != null) {
					result.addAll(bewerbung);
				}
				for (Bewerbung b: result){
					System.out.println(b.getErstelldatum());
			}		
	 
}
 
}