package de.hdm.gruppe1.Project4u.test;

import java.util.Vector;

import de.hdm.gruppe1.Project4u.server.db.AusschreibungMapper;
import de.hdm.gruppe1.Project4u.server.db.EigenschaftMapper;
import de.hdm.gruppe1.Project4u.server.db.PartnerprofilMapper;
import de.hdm.gruppe1.Project4u.server.db.ProjektMapper;
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Eigenschaft;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;

public class Testklasse {
	public static void main(String[] args) {
		Ausschreibung ausschreibung = new Ausschreibung();
		
		ausschreibung.setAusschreibungstext("Testtext");
		ausschreibung.setBezeichnung("Testbezeichnung");
	//	ausschreibung.setErstellDatum(Date);
		ausschreibung.setID(2);
		ausschreibung.setNameProjektleiter("HS");
		ausschreibung.setPartnerprofilId(3);
		ausschreibung.setProjektId(5);
	
		Partnerprofil partnerprofil = new Partnerprofil();
		Projekt projekt = new Projekt (); 
		
		AusschreibungMapper ausschreibungMapper = AusschreibungMapper.ausschreibungMapper();
		ProjektMapper projektMapper = ProjektMapper.projektMapper();
		PartnerprofilMapper partnerprofilMapper  = PartnerprofilMapper.partnerprofilMapper();
		projekt = projektMapper.findById(1);
		partnerprofil = partnerprofilMapper.findById(1);
		/*
		 * Vector<Eigenschaft> ev= new Vector<Eigenschaft>();
		
		 * ev=em.selectAllEigenschaftOfPartnerprofil(p);
		 *
		
		for(Eigenschaft e: ev){
			System.out.println(e.getName());
			
		} */
		
		
		
		
		
		
	}
		
		
}