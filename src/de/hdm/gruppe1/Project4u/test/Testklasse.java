package de.hdm.gruppe1.Project4u.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;

import de.hdm.gruppe1.Project4u.server.db.AusschreibungMapper;
import de.hdm.gruppe1.Project4u.server.db.EigenschaftMapper;
import de.hdm.gruppe1.Project4u.server.db.PartnerprofilMapper;
import de.hdm.gruppe1.Project4u.server.db.ProjektMapper;
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Eigenschaft;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;

public class Testklasse {
	public static void main(String[] args) {
		
		

		//SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		Calendar cal = Calendar.getInstance();
		cal.set(2017, 7, 10);
		
		Date date = new Date();
		date = cal.getTime();
		
		
		//Date date = sdf.parse(strDate); 
		
	
		AusschreibungMapper am = AusschreibungMapper.ausschreibungMapper();
	
		ArrayList<Ausschreibung> al = new ArrayList<Ausschreibung>();
		al= am.findAll();
		
		
		
		for(Ausschreibung e: al){
			System.out.println(e.getBezeichnung());
			
		} 
		
		
		
		
		
		
	}
		
		
}