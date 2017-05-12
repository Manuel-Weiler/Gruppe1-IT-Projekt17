package de.hdm.gruppe1.Project4u.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.ibm.icu.text.SimpleDateFormat;

import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Eigenschaft;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;


public class AusschreibungMapper {


	private static AusschreibungMapper ausschreibungMapper = null;
	

	private AusschreibungMapper() {
	}
	

	public static AusschreibungMapper ausschreibungMapper(){
		if (ausschreibungMapper==null){
			ausschreibungMapper = new AusschreibungMapper();
		}
		return ausschreibungMapper;
	}
	

	public Ausschreibung insertAusschreibung(Ausschreibung au, Partnerprofil pa, Projekt pr) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + 
			"FROM Ausschreibung ");

			if (rs.next()) {
		
				au.setAusschreibungId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				stmt.executeUpdate("INSERT INTO Ausschreibung "
						+ "(id, bezeichnung, name_projektleiter, "
						+ "bewerbungsfrist, ausschreibungstext, erstelldatum, "
						+ "projekt_id, partnerprofil_id) " 
						+ "VALUES ('"
						+ au.getAusschreibungId() + "','" 
						+ au.getBezeichnung() + "','" 
						+ au.getNameProjektleiter()+"','"
						+ sdf.format(au.getBewerbungsfrist()) + "','" 
						+ au.getAusschreibungstext() + "','" 
						+ sdf.format(au.getErstellDatum()) + "','" 
						+ pr.getProjektId() + "','" 
						+ pa.getPartnerprofilId() + "')");
			}
		} catch (SQLException s) {
			s.printStackTrace();
		}

		return au;
	}
	
	public Ausschreibung updateAusschreibung(Ausschreibung au) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("UPDATE Ausschreibung SET "
					+ "bezeichnung='" + au.getBezeichnung() 
					+ "name_projektleiter='" + au.getNameProjektleiter() 
					+ "bewerbungsfrist='" + au.getBewerbungsfrist() 
					+ "ausschreibungstext='" + au.getAusschreibungstext()
					+ "erstelldatum='" + au.getErstellDatum() 
					+ "' WHERE id='"
					+ au.getAusschreibungId() + "'");
					
					
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return au;
	}
	
	

	public void deleteAusschreibung(Ausschreibung au) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Ausschreibung WHERE id='" 
			+ au.getAusschreibungId() + "'");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	/*
	 * 
	*
	*
*public Ausschreibung findByIdAusschreibung (int i) {
*		Connection con = DBConnection.connection();
	*	Ausschreibung au = new Ausschreibung();
		
	*	try {
	*
	*		ResultSet rs = stmt.executeQuery("SELECT * " + "FROM Ausschreibung WHERE id='" + i + "'");
	*		if (rs.next()) {
*
*				au.setID(rs.getInt("id"));
*				au.setErstellDatum(rs.getDate("erstelldatum"));
*			}
*		} catch (SQLException e) {
*			e.printStackTrace();
*		}
*		return au;
*	}
*/	
	public Ausschreibung findByIdAusschreibung (int i) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM Ausschreibung WHERE id='" + i + "'");

			if (rs.next()) {
				Ausschreibung au = new Ausschreibung();

				au.setID(rs.getInt("id"));
				au.setBezeichnung (rs.getString("bezeichnung"));
				au.setNameProjektleiter (rs.getString("name_projektleiter"));
				au.setBewerbungsfrist (rs.getDate("bewerbungsfrist"));
				au.setAusschreibungstext (rs.getString("ausschreibungstext"));
				au.setErstellDatum(rs.getDate("erstelldatum"));
				
				return au;
							}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	return null;
}

	public Ausschreibung findByName(String bezeichnung) {
		Connection con = DBConnection.connection();
		Ausschreibung ausschreibung = new Ausschreibung();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * " + "FROM Ausschreibung WHERE bezeichnung='" + bezeichnung + "'");

			if (rs.next()) {

				ausschreibung.setID(rs.getInt("bezeichnung"));
							}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ausschreibung;
	}
	
//FINDEN EINER AUSSCHREIBUNG NACH PROJEKT
public Ausschreibung findByProjekt(Projekt name) {
		Connection con = DBConnection.connection();
		Ausschreibung ausschreibung = new Ausschreibung();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * " + "FROM Ausschreibung WHERE Projekt='" + "");
				//get. of
			if (rs.next()) {

				ausschreibung.setID(rs.getInt("id"));
							}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ausschreibung;
	}


	
// BEARBEITEN EINER AUSSCHREIBUNG
	public Ausschreibung update (Ausschreibung ausschreibung) {
		Connection con = DBConnection.connection();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE Partnerprofil " + "SET �nderungsdatum='" + sdf.format(d) + "' WHERE id='"
					+ ausschreibung.getAusschreibungId() + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ausschreibung;
	}
	

// L�schen einer Ausschreibung
	public void delete (Ausschreibung ausschreibung) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM ausschreibung WHERE id='"
					+ ausschreibung.getAusschreibungId() + "'");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Auslesen alle Ausschreibungen
	public ArrayList<Ausschreibung> findAll(){
		Connection con = DBConnection.connection();
		
		ArrayList<Ausschreibung> result = new ArrayList<Ausschreibung>();
		
		try{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Ausschreibung");
			
			while(rs.next()){
				Ausschreibung au = new Ausschreibung();
				au.setAusschreibungId(rs.getInt("id")); //TODO in der DB steht "id" jedoch sollte das vielleicht "AusschreibungID" hei�en
				au.setBezeichnung(rs.getString("bezeichnung"));
				au.setNameProjektleiter(rs.getString("name_projektleiter"));
				au.setBewerbungsfrist(rs.getDate("bewerbungsfrist"));
				au.setAusschreibungstext(rs.getString("ausschreibungstext"));
				au.setErstellDatum(rs.getDate("erstelldatum")); 
				au.setProjektId(rs.getInt("projekt_id"));
				au.setPartnerprofilId(rs.getInt("partnerprofil_id")); 
				
				result.add(au);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
// So oder mit einem Vector?
	public Ausschreibung  findByNameAusschreibung (String bezeichnung) {
		Connection con = DBConnection.connection();
		Ausschreibung au = new Ausschreibung();
		
		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * " + "FROM Ausschreibung WHERE "
					+ "bezeichnung='" + bezeichnung + "'");

			if (rs.next()) {

				au.setID(rs.getInt("id"));
				au.setBezeichnung (rs.getString("bezeichnung"));
				au.setNameProjektleiter (rs.getString("name_projektleiter"));
				au.setBewerbungsfrist (rs.getDate("bewerbungsfrist"));
				au.setAusschreibungstext (rs.getString("ausschreibungstext"));
				au.setErstellDatum(rs.getDate("erstelldatum"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return au;
	}
		public Vector<Ausschreibung> findByProjekt (String name) {
		    Connection con = DBConnection.connection();
		    Vector<Ausschreibung> result = new Vector<Ausschreibung>();

		    try {
		      Statement stmt = con.createStatement();

		      ResultSet rs = stmt.executeQuery("SELECT name "
		          + "FROM Projekt " + "WHERE name LIKE '" + name
		          + "' ORDER BY name");

		 
		      while (rs.next()) {
		        Ausschreibung au = new Ausschreibung();
				au.setID(rs.getInt("id"));
				au.setBezeichnung (rs.getString("bezeichnung"));
				au.setNameProjektleiter (rs.getString("name_projektleiter"));
				au.setBewerbungsfrist (rs.getDate("bewerbungsfrist"));
				au.setAusschreibungstext (rs.getString("ausschreibungstext"));
				au.setErstellDatum(rs.getDate("erstelldatum"));

		        result.addElement(au);
		      }
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }

		    return result;
		  }
		
		public Vector<Ausschreibung> findByPerson (String name) {
		    Connection con = DBConnection.connection();
		    Vector<Ausschreibung> result = new Vector<Ausschreibung>();

		    try {
		      Statement stmt = con.createStatement();

		      ResultSet rs = stmt.executeQuery("SELECT name_projektleiter "
		          + "FROM Ausschreibung " + "WHERE name_projektleiter LIKE '" 
		    		  + name
		          + "' ORDER BY name_projektleiter");

		 
		      while (rs.next()) {
		        Ausschreibung au = new Ausschreibung();
				au.setID(rs.getInt("id"));
				au.setBezeichnung (rs.getString("bezeichnung"));
				au.setNameProjektleiter (rs.getString("name_projektleiter"));
				au.setBewerbungsfrist (rs.getDate("bewerbungsfrist"));
				au.setAusschreibungstext (rs.getString("ausschreibungstext"));
				au.setErstellDatum(rs.getDate("erstelldatum"));

		        result.addElement(au);
		      }
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }

		    return result;
		  }
	
	public void deleteAusschreibungOfProjekt(Projekt p) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Ausschreibung WHERE projekt_id= " + p.getOrganisationseinheitId());
			
		} catch (Exception e2) {
			 e2.printStackTrace();
		}
	}
	}

	