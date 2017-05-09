package de.hdm.gruppe1.Project4u.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import com.ibm.icu.text.SimpleDateFormat;

import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;



public class AusschreibungMapper {
	
	private static AusschreibungMapper AusschreibungMapper = null; 
	
	private AusschreibungMapper () {
	}
	
	
// EINFÜGEN EINER AUSSCHREIBUNG 
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

			ResultSet rs = stmt.executeQuery("SELECT * " + "FROM Ausschreibung WHERE id='" + i + "'");

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
	public Ausschreibung updateAusschreibung (Ausschreibung ausschreibung) {
		Connection con = DBConnection.connection();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE Partnerprofil " + "SET änderungsdatum='" + sdf.format(d) + "' WHERE id='"
					+ ausschreibung.getAusschreibungID() + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ausschreibung;
	}
	

// Löschen einer Ausschreibung
	public void deleteAusschreibung (Ausschreibung ausschreibung){
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM ausschreibung WHERE id='"
					+ ausschreibung.getAusschreibungID() + "'");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	


}
