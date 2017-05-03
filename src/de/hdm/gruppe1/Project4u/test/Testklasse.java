package de.hdm.gruppe1.Project4u.test;

import java.util.Vector;

import de.hdm.gruppe1.Project4u.server.db.EigenschaftMapper;
import de.hdm.gruppe1.Project4u.server.db.PartnerprofilMapper;
import de.hdm.gruppe1.Project4u.shared.bo.Eigenschaft;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;

public class Testklasse {
	public static void main(String[] args) {
		Eigenschaft e = new Eigenschaft();
		
		
		e.setName("alter");
		e.setWert("97"); 
		Partnerprofil p = new Partnerprofil();
		
		
		PartnerprofilMapper pm = PartnerprofilMapper.partnerprofilMapper();
		EigenschaftMapper em = EigenschaftMapper.eigenschaftMapper();
		p=pm.findById(0);
		
		em.insertEigenschaft(e, p);
		/*
		 * Vector<Eigenschaft> ev= new Vector<Eigenschaft>();
		
		 * ev=em.selectAllEigenschaftOfPartnerprofil(p);
		 *
		
		for(Eigenschaft e: ev){
			System.out.println(e.getName());
			
		} */
		
		
		
		
		
		
	}
		
		
}
