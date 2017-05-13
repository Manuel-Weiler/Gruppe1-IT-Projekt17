package de.hdm.gruppe1.Project4u.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Beteiligung;
import de.hdm.gruppe1.Project4u.shared.bo.Bewertung;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;


public class BeteiligungMapper {
	
	private static BeteiligungMapper beteiligungMapper = null; 
	
	protected BeteiligungMapper () {
	}
	
	public static BeteiligungMapper beteiligungMapper (){
		if (beteiligungMapper == null){
			beteiligungMapper= new BeteiligungMapper ();
		}
		return beteiligungMapper; 

	}
	
	
	public Beteiligung insertBeteiligung(Beteiligung b, Organisationseinheit o, Bewertung be2, Projekt p, Bewertung be ) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + 
			"FROM Beteiligung ");

			if (rs.next()) {
		
				b.setBeteiligungId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				stmt.executeUpdate("INSERT INTO Beteiligung "
						+ "(id, startdatum, enddatum, "
						+ "persontage, organisationseinheit_id, projekt_id, "
						+ "bewertung_id) " 
						+ "VALUES ("
						+ be.getBewertungId() + ",'" 
						+ b.getStartdatum() + "','" 
						+ b.getEnddatum() + "','" 
						+ b.getPersonentage() + "','" 
						+ o.getOrganisationseinheitId() + "','" 
						+ p.getProjektId() + "','" 
						+ be.getBewertungId() + "')");
			}
		} catch (SQLException s) {
			s.printStackTrace();
		}


		return b;
		}
		
	
	public void deleteBeteiligung(Beteiligung b) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Beteiligung WHERE id='" 
			+ b.getBeteiligungId() + "'");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public void deleteBeteiligungOfProjekt(Projekt p) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Beteiligung WHERE projekt_id= " + p.getProjektId());
			
		} catch (Exception e2) {
			 e2.printStackTrace();
		}
	}
	
	public void deleteBeteiligungOfBewertung(Bewertung b) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Beteiligung WHERE bewertung_id= " + b.getBewertungId());
			
		} catch (Exception e2) {
			 e2.printStackTrace();
		}
	}

}
