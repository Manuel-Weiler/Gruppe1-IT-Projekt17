package de.hdm.gruppe1.Project4u.test;

import java.util.Vector;

import de.hdm.gruppe1.Project4u.server.db.EigenschaftMapper;
import de.hdm.gruppe1.Project4u.server.db.PartnerprofilMapper;
import de.hdm.gruppe1.Project4u.server.db.ProjektmarktplatzMapper;
import de.hdm.gruppe1.Project4u.shared.bo.Eigenschaft;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;
import de.hdm.gruppe1.Project4u.shared.bo.Projektmarktplatz;

public class Testklasse {
	public static void main(String[] args) {
		/*Eigenschaft e = new Eigenschaft();
		
		
		e.setName("alter");
		e.setWert("97"); 
		*/
		//Partnerprofil p = new Partnerprofil();
		//PartnerprofilMapper pm = PartnerprofilMapper.partnerprofilMapper();
		//EigenschaftMapper em = EigenschaftMapper.eigenschaftMapper();
		//p=pm.findById(0);
		
		ProjektmarktplatzMapper ppm = ProjektmarktplatzMapper.projektmarktplatzMapper();
		Vector<Projektmarktplatz> v = new Vector<Projektmarktplatz>();
		v=ppm.findAll();
		
		
		/*
		 * Vector<Eigenschaft> ev= new Vector<Eigenschaft>();
		
		 * ev=em.selectAllEigenschaftOfPartnerprofil(p);
		 *
		*/
		for(Projektmarktplatz proji: v){
			System.out.println(proji.getName());
			
		} 
		
		
		
		
		
		
	}
		
		
}
