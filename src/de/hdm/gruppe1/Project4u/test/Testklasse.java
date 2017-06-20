package de.hdm.gruppe1.Project4u.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;

import de.hdm.gruppe1.Project4u.server.Project4uAdministrationImpl;
import de.hdm.gruppe1.Project4u.server.db.AusschreibungMapper;
import de.hdm.gruppe1.Project4u.server.db.EigenschaftMapper;
import de.hdm.gruppe1.Project4u.server.db.OrganisationseinheitMapper;
import de.hdm.gruppe1.Project4u.server.db.PartnerprofilMapper;
import de.hdm.gruppe1.Project4u.server.db.ProjektMapper;
import de.hdm.gruppe1.Project4u.shared.LoginInfo;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministration;
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


		Vector<Ausschreibung> ergebnis = new Vector<Ausschreibung>();
		AusschreibungMapper am = new AusschreibungMapper();
		ArrayList<Ausschreibung> auss = am.findAllAusschreibungen();
		ProjektMapper pm = new ProjektMapper();
		Vector<Projekt> p = pm.findAll();

		OrganisationseinheitMapper om = new OrganisationseinheitMapper();
		Organisationseinheit orga = om.findByKey(2);

		for (Projekt pro : p) {
			// Für alle Projekte mit der OrganisationseinheitsId XY...
			if (pro.getOrganisationseinheitId() == orga.getOrganisationseinheitId()) {
				for (Ausschreibung ausschreibung : auss) {
					// ...füge die Ausschreibungen dem ergebnis-Vektor hinzu.
					if (pro.getProjektId() == ausschreibung.getProjektId()) {
						ergebnis.add(ausschreibung);
					}
				}
			}
		}

		for(Ausschreibung au : ergebnis){
			System.out.println(au.getNameProjektleiter());
		}

		
		
	}

}











