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
import de.hdm.gruppe1.Project4u.shared.LoginInfo;
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Eigenschaft;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;

public class Testklasse {
	public static void main(String[] args) {


//		PartnerprofilMapper pm = new PartnerprofilMapper();
//		Partnerprofil pa = new Partnerprofil();
//		EigenschaftMapper em = EigenschaftMapper.eigenschaftMapper();
//		Vector<Eigenschaft> vektorEigenschaft = new Vector<Eigenschaft>();
//		pa = pm.findById(2);
//
//		vektorEigenschaft = em.findByPartnerprofil(pa);
//		
//		for(Eigenschaft e : vektorEigenschaft){
//
//			System.out.println(e.getName());
//		}
		
		AusschreibungMapper au = new AusschreibungMapper();
		System.out.println(au.findByIdAusschreibung(2).getAusschreibungId());
		System.out.println(au.findByIdAusschreibung(2).getBezeichnung());
		System.out.println(au.findByIdAusschreibung(2).getNameProjektleiter());
		System.out.println(au.findByIdAusschreibung(2).getBewerbungsfrist());
		System.out.println(au.findByIdAusschreibung(2).getAusschreibungstext());
		System.out.println(au.findByIdAusschreibung(2).getErstellDatum());
		System.out.println(au.findByIdAusschreibung(2).getPartnerprofilId());
		System.out.println(au.findByIdAusschreibung(2).getProjektId());
		
		
	}
		
		
}