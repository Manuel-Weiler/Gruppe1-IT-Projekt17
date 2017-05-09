package de.hdm.gruppe1.Project4u.test;

import de.hdm.gruppe1.Project4u.server.db.BewerbungMapper;
import de.hdm.gruppe1.Project4u.shared.bo.Bewerbung;

public class TestBewerbungsmapper {

	public static void main(String[] args) {
		
		
		Bewerbung b = new Bewerbung();
		
		//b.setBewerbungID(1);
		b.setBewerbungstext("blabla");
		//b.setErstelldatum();
		
		BewerbungMapper bm = BewerbungMapper.bewerbungMapper();
		
		bm.insert(b);
		
	}
	
}
