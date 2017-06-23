package de.hdm.gruppe1.Project4u.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import org.apache.tools.ant.types.CommandlineJava.SysProperties;

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
import de.hdm.gruppe1.Project4u.shared.report.Column;
import de.hdm.gruppe1.Project4u.shared.report.Row;

public class Testklasse {
	public static void main(String[] args) {

		OrganisationseinheitMapper om = new OrganisationseinheitMapper();
		Organisationseinheit o = om.findByKey(7);
		AusschreibungMapper bm = AusschreibungMapper.ausschreibungMapper();
		ArrayList<Ausschreibung> bf = new ArrayList<>();
		bf = bm.findAllAusschreibungen();

		// for(Ausschreibung b : be){
		//
		// System.out.println(b.getAusschreibungId());
		// System.out.println(b.getBezeichnung());
		// System.out.println(b.getNameProjektleiter());
		// System.out.println(b.getBewerbungsfrist());
		// System.out.println(b.getAusschreibungstext());
		// System.out.println(b.getProjektId());
		// System.out.println(b.getErstellDatum());
		// System.out.println(b.getPartnerprofilId());
		// System.out.println(b.getStatus());
		// }

		BewerbungMapper bewerbungMapper = new BewerbungMapper();

		Vector<Bewerbung> bew = bewerbungMapper.findByOrganisationseinheit(o);

		// Anschließend müssen die Ausschreibungen zu diesen Bewerbungen
		// ausgegeben werden.

		for (Bewerbung be : bew) {

			Ausschreibung au = bm.findByIdAusschreibung(be.getAusschreibungId());
			// Row bewerbungRow = new Row();
			// fï¿½r jede Spalte dieser Zeile wird nun der Inhalt geschrieben
			System.out.println(be.getBewerbungId());
			System.out.println(be.getErstelldatum());
			System.out.println(be.getBewerbungstext());
			System.out.println(be.getOrganisationseinheitId());
			System.out.println(be.getStatus());
			System.out.println(be.getAusschreibungId());
			// Inhalt Ausschreibung

			System.out.println(au.getBezeichnung());
			System.out.println(au.getNameProjektleiter());
			System.out.println(au.getBewerbungsfrist());
			System.out.println(au.getAusschreibungstext());
			System.out.println(au.getErstellDatum());

		}

	}

}
