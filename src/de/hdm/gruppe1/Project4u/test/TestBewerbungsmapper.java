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
		
		//b.setBewerbungID(1);
		b.setBewerbungstext("blabla");
		
		
		
		
		
		BewerbungMapper bm = BewerbungMapper.bewerbungMapper();
		OrganisationseinheitMapper om = OrganisationseinheitMapper.organisationseinheitMapper();
		AusschreibungMapper am= AusschreibungMapper.ausschreibungMapper();
		Ausschreibung ausschreibung = new Ausschreibung();
		ausschreibung= am.findById(0);
		Organisationseinheit orga = new Organisationseinheit();
		orga= om.findByKey(2);
				
		bm.insert(b,ausschreibung,orga );
		
	}
	
}
