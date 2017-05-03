package de.hdm.gruppe1.Project4u.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.gruppe1.Project4u.shared.bo.Beteiligung;


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
				
// BeteiligungsID muss eine ID hochzählen
			
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


}
