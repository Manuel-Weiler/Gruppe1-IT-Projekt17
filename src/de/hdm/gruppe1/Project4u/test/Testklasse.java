package de.hdm.gruppe1.Project4u.test;

import java.util.Date;
import java.util.Vector;

import com.ibm.icu.text.SimpleDateFormat;

import de.hdm.gruppe1.Project4u.server.db.AusschreibungMapper;
import de.hdm.gruppe1.Project4u.server.db.EigenschaftMapper;
import de.hdm.gruppe1.Project4u.server.db.PartnerprofilMapper;

import de.hdm.gruppe1.Project4u.server.db.ProjektmarktplatzMapper;

import de.hdm.gruppe1.Project4u.server.db.ProjektMapper;
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;

import de.hdm.gruppe1.Project4u.shared.bo.Eigenschaft;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;

import de.hdm.gruppe1.Project4u.shared.bo.Projektmarktplatz;

import de.hdm.gruppe1.Project4u.shared.bo.Projekt;

public class Testklasse {
	public static void main(String[] args) {

		/*
		
		String strDate  = "13-05-2017";
		String ctrDate  = "14-05-2017";

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		//Date date = sdf.parse(strDate); 
		
		Ausschreibung a = new Ausschreibung (); 


		e.setName("alter");
		e.setWert("97"); 
		*/
		//Partnerprofil p = new Partnerprofil();
		//PartnerprofilMapper pm = PartnerprofilMapper.partnerprofilMapper();
		//EigenschaftMapper em = EigenschaftMapper.eigenschaftMapper();
		//p=pm.findById(0);
		
		/*
		ProjektmarktplatzMapper ppm = ProjektmarktplatzMapper.projektmarktplatzMapper();
		Vector<Projektmarktplatz> v = new Vector<Projektmarktplatz>();
		v=ppm.findAll();

		a.setAusschreibungstext("Test Text");
		//a.setBewerbungsfrist(date);
		a.setBezeichnung("Test bezeichnung");
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
		*/
		/*
		for(Projektmarktplatz proji: v){
			System.out.println(proji.getName());
			
		} 
		*/

		
	}
		
		
}