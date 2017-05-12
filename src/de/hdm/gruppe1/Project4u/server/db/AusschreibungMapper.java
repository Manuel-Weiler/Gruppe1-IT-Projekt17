package de.hdm.gruppe1.Project4u.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import com.ibm.icu.text.SimpleDateFormat;

import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;


public class AusschreibungMapper {
	
	private static AusschreibungMapper ausschreibungMapper = null; 
	
	private AusschreibungMapper () {
	}
	
	public static AusschreibungMapper ausschreibungMapper() {
		if (ausschreibungMapper == null) {
			ausschreibungMapper = new AusschreibungMapper();
		}
		return ausschreibungMapper;
	}
	
// EINF�GEN EINER AUSSCHREIBUNG 
	public Ausschreibung insert(Ausschreibung ausschreibung){
		Connection con = DBConnection.connection();
		
		try{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(beteiligungId) AS maxid " + "FROM Beteiligung ");
			if(rs.next()){
				
			
			}	
		}
		
		catch (SQLException e2){
			e2.printStackTrace();
		}
		
		return ausschreibung;
	}

// FINDEN NACH ID
	public Ausschreibung findById(int i) {
		Connection con = DBConnection.connection();
		Ausschreibung ausschreibung = new Ausschreibung();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM Ausschreibung WHERE id='" + i + "'");

			if (rs.next()) {

				ausschreibung.setID(rs.getInt("id"));
							}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ausschreibung;
	}

// FINDEN EINER AUSSCHREIBUNG NACH NAME
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
					+ ausschreibung.getAusschreibungID() + "'");
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
					+ ausschreibung.getAusschreibungID() + "'");
			
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
				Ausschreibung ausschreibung = new Ausschreibung();
				ausschreibung.setAusschreibungID(rs.getInt("id")); //TODO in der DB steht "id" jedoch sollte das vielleicht "AusschreibungID" hei�en
				ausschreibung.setBezeichnung(rs.getString("bezeichnung"));
				//TODO ausschreibung.setNameProjektleiter(rs.getString("name_projektleiter"));
				ausschreibung.setAblaufdatum(rs.getDate("bewerbungsfrist")); //TODO setAblaufdatum sollte eigentlich bewerbungsfrist hei�en oder umgekehrt --> EINEHITLICH!!!
				ausschreibung.setAusschreibungstext(rs.getString("ausschreibungstext"));
				//TODO ausschreibung.setErstelldatum(rs.getDate("erstelldatum")); fehlt noch!
				//TODO ausschreibung.setProjektId(rs.getInt("projekt_id")); fehlt noch!
				//TODO ausschreibung.setPartnerprofilId(rs.getInt("partnerprofil_id")); fehlt noch!
				
				result.add(ausschreibung);
			}
		} catch(SQLException e2) {
			e2.printStackTrace();
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
