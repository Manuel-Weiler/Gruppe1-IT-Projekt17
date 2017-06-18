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
		


	
	

		
		

			EigenschaftMapper em = new EigenschaftMapper();
			PartnerprofilMapper pm = new PartnerprofilMapper();
			AusschreibungMapper am = new AusschreibungMapper();

			Partnerprofil p = pm.findById(2);

			Vector<Eigenschaft> nutzereigenschaften = em.findByPartnerprofil(p);
			ArrayList<Ausschreibung> alleAusschreibungen = am.findAllAusschreibungen();
			Vector<Ausschreibung> ausForPartnerprofil = new Vector<Ausschreibung>();

			//for (Ausschreibung au : alleAusschreibungen) {
				
			Vector<Eigenschaft> ausEig = em.findByPartnerprofil(p);
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
//						if (eigenschaftAus == eigenschaftUnsere) {
//							ausForPartnerprofil.add(au);
//						}
//					}
//				}
//
//			}
//			for(Ausschreibung au : ausForPartnerprofil){
//				System.out.println(au.getAusschreibungId());
//				System.out.println(au.getBezeichnung());
//				System.out.println(au.getNameProjektleiter());
//				System.out.println(au.getBewerbungsfrist());
//				System.out.println(au.getAusschreibungstext());
//				System.out.println(au.getErstellDatum());
//				System.out.println(au.getPartnerprofilId());
//				System.out.println(au.getProjektId());
//				}	

				for(int i =0; i< ausEig.size(); i++){

					System.out.println(ausEig.elementAt(5).getName());	
				}
		}

		
		
		
	
		
		
}