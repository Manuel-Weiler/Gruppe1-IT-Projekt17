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
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Eigenschaft;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;

public class Testklasse {
	public static void main(String[] args) {


		OrganisationseinheitMapper om = new OrganisationseinheitMapper();
		Organisationseinheit orga = om.findByKey(6);

		System.out.println(orga.getID());

		System.out.println(orga.getOrganisationseinheitId());
		System.out.println(orga.getGoogleId());
		System.out.println(orga.getName());
		System.out.println(orga.getTyp());
		System.out.println(orga.getPartnerprofilId());
//			EigenschaftMapper em = new EigenschaftMapper();
//			PartnerprofilMapper pm = new PartnerprofilMapper();
//			AusschreibungMapper am = new AusschreibungMapper();
//
//			//Partnerprofil p = pm.findById(2);
//
//			Vector<Eigenschaft> nutzereigenschaften = em.findByPartnerprofil(pm.findById(2));
//			ArrayList<Ausschreibung> alleAusschreibungen = am.findAllAusschreibungen();
//			Vector<Ausschreibung> ausForPartnerprofil = new Vector<Ausschreibung>();
//
//			for (Ausschreibung au : alleAusschreibungen) {
//				
//			Vector<Eigenschaft> ausEig = em.findByPartnerprofil(pm.findById(au.getPartnerprofilId()));
//
////			for(int i =0; i< ausEig.size(); i++){
////
////				System.out.println(ausEig.elementAt(5).getName());	
////			}
//			
//						
//				// hier speichern wir die Eigenschaften aller Ausschreibungen ab.
//				for (Eigenschaft eig : ausEig) {
//					String eigenschaftAus = eig.getName();
//
//					// Hier speichern wir die Eigenschaften des aktuellen Nutzers
//					// ab.
//					for (Eigenschaft unsereEigenschaft : nutzereigenschaften) {
//						String eigenschaftUnsere = unsereEigenschaft.getName();
//
//						// hier vergleichen wir die Eigenschaften des aktuellen
//						// Nutzers mit denen der Auschreibungen.
//						if (eigenschaftAus.equals(eigenschaftUnsere)) {
//							ausForPartnerprofil.add(au);
//						}
//					}
//				}
//
//			for(Ausschreibung aus : ausForPartnerprofil){
//				System.out.println(aus.getAusschreibungId());
//				System.out.println(aus.getBezeichnung());
//				System.out.println(aus.getNameProjektleiter());
//				System.out.println(aus.getBewerbungsfrist());
//				System.out.println(aus.getAusschreibungstext());
//				System.out.println(aus.getErstellDatum());
//				System.out.println(aus.getPartnerprofilId());
//				System.out.println(aus.getProjektId());
//				}	
//
//		}

	}

		
		
}