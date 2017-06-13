package de.hdm.gruppe1.Project4u.test;

import java.util.Vector;

import javax.swing.JOptionPane;

import de.hdm.gruppe1.Project4u.server.db.OrganisationseinheitMapper;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;

public class OrgaTest {

	public static void main(String[] args) {
		
		
		String name;
		String google_id;
		String typ;
		
		//name = JOptionPane.showInputDialog(null, "Name eingeben");
		//google_id = JOptionPane.showInputDialog(null, "Google Mail eingeben");
		//typ = JOptionPane.showInputDialog(null, "Typ angeben (Unternehmen, Team oder Person)");
		
		//Orga Objekt & Mapper Objekt erzeugen
		Organisationseinheit o = new Organisationseinheit();
		Organisationseinheit o2 = new Organisationseinheit();
		Organisationseinheit o3 = new Organisationseinheit();
		Partnerprofil pp = new Partnerprofil();
		Partnerprofil pp2 = new Partnerprofil();
		Partnerprofil pp3 = new Partnerprofil();
		
		OrganisationseinheitMapper om = OrganisationseinheitMapper.organisationseinheitMapper();
		
		//Alle finden
				Vector<Organisationseinheit> vo = new Vector<Organisationseinheit>();
				vo = om.findAll();
				for(Organisationseinheit oe: vo){
					System.out.println(oe.getName());
				}
				
		
		
		/*//Orga anlegen
		o.setName("Karl Wuchtig");
		o.setGoogleId("karl.wuchtig@gmail.de");
		o.setTyp("Person");
		o.setPartnerprofilId(2);
		om.insert(o);
		
		o2.setName("Max Mustermann");
		o2.setGoogleId("max.mustermann@gmail.de");
		o2.setTyp("Person");
		o.setPartnerprofilId(3);
		om.insert(o2);
		
		o3.setName("HdM");
		o3.setGoogleId("hdm@gmail.de");
		o3.setTyp("Unternehmen");
		o.setPartnerprofilId(4);
		om.insert(o3);
		
		//Alle finden
		Vector<Organisationseinheit> vo2 = new Vector<Organisationseinheit>();
		vo2 = om.findAll();
		for(Organisationseinheit oe: vo2){
			System.out.println("Alle: " + oe.getName() );
		}
		
		//Orga updaten - Name geändert
		o.setName("Karl Wuchtelig");
		o.setGoogleId("karl.wuchtelig@gmail.de");
		om.update(o);
		
		//Alle finden
		Vector<Organisationseinheit> vo3 = new Vector<Organisationseinheit>();
		vo3 = om.findAll();
		for(Organisationseinheit oe: vo3){
			System.out.println("Update: " + oe.getName() );
		}
		*/
		//Nach ID finden
				Organisationseinheit oEinheit = new Organisationseinheit();
				oEinheit = om.findByKey(2);				
				System.out.println(oEinheit.getName());
		
		/*//Orga löschen
		om.delete(o);
		
		//Alle finden
		Vector<Organisationseinheit> vo4 = new Vector<Organisationseinheit>();
		vo4 = om.findAll();
		for(Organisationseinheit oe: vo4){
			System.out.println("Delete: " + oe.getName() );
		}
		
		
		
		// Nach Name finden
		Vector<Organisationseinheit> vo6 = new Vector<Organisationseinheit>();
		vo6 = om.findByName("HdM");
		for(Organisationseinheit oe: vo6){
			System.out.println("Nach Name finden: " + oe.getName() );
		}
		
		// Nach Typ finden
		Vector<Organisationseinheit> vo7 = new Vector<Organisationseinheit>();
		vo7 = om.findByTyp("Person");
		for(Organisationseinheit oe: vo7){
			System.out.println("Nach Typ: " + oe.getName() + " " +  oe.getTyp());
		}*/
		
		

	}

}
