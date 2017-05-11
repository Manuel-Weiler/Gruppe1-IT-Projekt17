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
		Ausschreibung a = new Ausschreibung (); 
		
		
		a.setAusschreibungstext("Test Text");
	//	a.setBewerbungsfrist(2017-06-15);
		a.setBezeichnung("Test bezeichnung");
	//	a.setErstellDatum(2017,05,12);
		a.setID(1);
		a.setNameProjektleiter("HS");
		a.setPartnerprofilId(2);
		a.setProjektId(3);
		
		Partnerprofil pa = new Partnerprofil();
		Projekt pr = new Projekt ();
		
		
		PartnerprofilMapper p = PartnerprofilMapper.partnerprofilMapper();
		ProjektMapper l = ProjektMapper.projektMapper();
		AusschreibungMapper am = AusschreibungMapper.ausschreibungMapper();
	//	p=p.findById(0);
		
		am.insertAusschreibung(a, pa, pr);
		/*
		 * Vector<Eigenschaft> ev= new Vector<Eigenschaft>();
		
		 * ev=em.selectAllEigenschaftOfPartnerprofil(p);
		 *
		
		for(Eigenschaft e: ev){
			System.out.println(e.getName());
			
		} */
		
		
		
		
		
		
	}
		
		
}