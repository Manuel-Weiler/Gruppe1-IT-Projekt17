package de.hdm.gruppe1.Project4u.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;

import de.hdm.gruppe1.Project4u.server.db.AusschreibungMapper;
import de.hdm.gruppe1.Project4u.server.db.EigenschaftMapper;
import de.hdm.gruppe1.Project4u.server.db.OrganisationseinheitMapper;
import de.hdm.gruppe1.Project4u.server.db.PartnerprofilMapper;
import de.hdm.gruppe1.Project4u.server.db.ProjektMapper;
import de.hdm.gruppe1.Project4u.server.db.ProjektmarktplatzMapper;
import de.hdm.gruppe1.Project4u.shared.LoginInfo;
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Eigenschaft;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;
import de.hdm.gruppe1.Project4u.shared.bo.Projektmarktplatz;

public class Testklasse {
	public static void main(String[] args) {
		
		

		//SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	/*	
		Calendar cal = Calendar.getInstance();
		cal.set(2017, 7, 10);
		
		Date date = new Date();
		date = cal.getTime();
		
		
		//Date date = sdf.parse(strDate); 
		
	


		a.setNameProjektleiter("HS");
		a.setPartnerprofilId(2);
		a.setProjektId(3);
		a.setErstellDatum(new Date());
		 
		Partnerprofil pa = new Partnerprofil();
		pa.setPartnerprofilId(0);
		Projekt pr = new Projekt ();
		pr.setProjektId(1);
		
		 
		PartnerprofilMapper p = PartnerprofilMapper.partnerprofilMapper();
		ProjektMapper l = ProjektMapper.projektMapper(); */

		//AusschreibungMapper am = AusschreibungMapper.ausschreibungMapper();
	//	p=p.findById(0);
		

		//am.findByPartnerprofil(pa);

		//am.insertAusschreibung(a, pa, pr);



		OrganisationseinheitMapper om = OrganisationseinheitMapper.organisationseinheitMapper();
		  Vector<Organisationseinheit> ev= new Vector<Organisationseinheit>();

		
		LoginInfo login = new LoginInfo();
		Projektmarktplatz pm = new Projektmarktplatz();
		pm.setName("knuu");
		pm.setOrganisationseinheitId(1);
		
		
		
	//	ProjektmarktplatzMapper om = ProjektmarktplatzMapper.projektmarktplatzMapper();
		 
		//om.insert(pm);
		 
		/*
		for(Organisationseinheit e: ev){
			System.out.println(e.getName());

			
		} 
		*/
		
		
		
		
		
	}
		
		
}