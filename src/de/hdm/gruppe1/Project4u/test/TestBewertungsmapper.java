package de.hdm.gruppe1.Project4u.test;

import de.hdm.gruppe1.Project4u.server.db.BewerbungMapper;
import de.hdm.gruppe1.Project4u.server.db.BewertungMapper;
import de.hdm.gruppe1.Project4u.shared.bo.Bewerbung;
import de.hdm.gruppe1.Project4u.shared.bo.Bewertung;

public class TestBewertungsmapper {
	
	public static void main(String[] args) {
		
		Bewertung be = new Bewertung();
		
		//be.setBewertungID(1);
		be.setBewertungspunkte((float) 0.6);
		be.setStellungnahme("blablub");
		
		BewertungMapper bem = BewertungMapper.bewertungMapper();

		BewerbungMapper bewerbungMapper = BewerbungMapper.bewerbungMapper();
		//Bewerbung bewerbung = Bewerbung.bewerbung();
		//int bewerbung = bewerbungMapper.findById(0);
		
		
		
		//bem.insert(be, bewerbung);
	}

}
