package de.hdm.gruppe1.Project4u.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	
	
	public Beteiligung insert(Beteiligung beteiligung){
		Connection con = DBConnection.connection();
		
		try{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(beteiligungId) AS maxid " + "FROM Beteiligung ");
			if(rs.next()){
				
// BeteiligungsID muss eine ID hochz�hlen
			
			}	
		}
		
		catch (SQLException e2){
			e2.printStackTrace();
		}
		
		return beteiligung;
	}
	
	
	public void deleteBeteiligung (Beteiligung beteiligung){
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Beteiligung WHERE id='"
					+ beteiligung.getbeteiligungId() + "'");
			
		} catch (SQLException e) {
			e.printStackTrace();
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
			stmt.executeUpdate("DELETE FROM Beteiligung WHERE bewertung_id= " + b.getBewertungID());
			
		} catch (Exception e2) {
			 e2.printStackTrace();
		}
	}


}
