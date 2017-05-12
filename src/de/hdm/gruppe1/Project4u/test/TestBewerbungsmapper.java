package de.hdm.gruppe1.Project4u.test;

import de.hdm.gruppe1.Project4u.server.db.AusschreibungMapper;
import de.hdm.gruppe1.Project4u.server.db.BewerbungMapper;
import de.hdm.gruppe1.Project4u.server.db.OrganisationseinheitMapper;
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Bewerbung;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;

public class TestBewerbungsmapper {

	public static void main(String[] args) {
		
		
		Bewerbung b = new Bewerbung();
		
		
		
		
		
		BewerbungMapper bm = BewerbungMapper.bewerbungMapper();
		b=bm.findById(1);
		System.out.println(b.getBewerbungstext());
		b.setBewerbungstext("Hallo");
		//bm.insert(b,ausschreibung,orga);
		bm.update(b);
		
		
	}
	
}
