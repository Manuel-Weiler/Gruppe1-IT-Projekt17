package de.hdm.gruppe1.Project4u.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;

import de.hdm.gruppe1.Project4u.server.Project4uAdministrationImpl;
import de.hdm.gruppe1.Project4u.server.db.AusschreibungMapper;
import de.hdm.gruppe1.Project4u.server.db.BewerbungMapper;
import de.hdm.gruppe1.Project4u.server.db.EigenschaftMapper;
import de.hdm.gruppe1.Project4u.server.db.OrganisationseinheitMapper;
import de.hdm.gruppe1.Project4u.server.db.PartnerprofilMapper;
import de.hdm.gruppe1.Project4u.server.db.ProjektMapper;
import de.hdm.gruppe1.Project4u.shared.LoginInfo;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministration;
import de.hdm.gruppe1.Project4u.server.db.ProjektmarktplatzMapper;
import de.hdm.gruppe1.Project4u.shared.LoginInfo;
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Bewerbung;
import de.hdm.gruppe1.Project4u.shared.bo.Eigenschaft;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;
import de.hdm.gruppe1.Project4u.shared.bo.Projektmarktplatz;

public class Testklasse {
	public static void main(String[] args) {


		OrganisationseinheitMapper om = new OrganisationseinheitMapper();
		Organisationseinheit o = om.findByKey(2);
		BewerbungMapper bm = BewerbungMapper.bewerbungMapper();
		Vector<Bewerbung> be = new Vector <Bewerbung> ();
		be= bm.findByOrganisationseinheit(o);

		for(Bewerbung b : be){
			System.out.println(b.getBewerbungId());
			System.out.println(b.getErstelldatum());
			System.out.println(b.getBewerbungstext());
			System.out.println(b.getAusschreibungId());
			System.out.println(b.getOrganisationseinheitId());
			System.out.println(b.getStatus());
		}

		
		
	}

}











