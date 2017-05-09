package de.hdm.gruppe1.Project4u.test;

import de.hdm.gruppe1.Project4u.server.db.BewertungMapper;
import de.hdm.gruppe1.Project4u.shared.bo.Bewertung;

public class TestBewertungsmapper {
	
	public static void main(String[] args) {
		
		Bewertung be = new Bewertung();
		
		//be.setBewertungID(1);
		be.setBewertungspunkte(0);
		be.setStellungnahme("blablub");
		
		BewertungMapper bem = BewertungMapper.bewertungMapper();
		
		bem.insert(be);
	}

}
