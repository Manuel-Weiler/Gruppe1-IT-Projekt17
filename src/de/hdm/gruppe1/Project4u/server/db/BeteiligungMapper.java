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
	
	
	public Beteiligung insertBeteiligung(Beteiligung beteiligung) {
		Connection con = DBConnection.connection();
		
		try{
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM Beteiligung ");
			
			
			if(rs.next()){
				beteiligung.setBeteiligungId(rs.getInt("maxid") +1);
				

				stmt.executeUpdate("INSERT INTO Beteiligung (id) "
			            + "VALUES (" + beteiligung.getBeteiligungId());
			}
		} catch (SQLException e2){
			e2.printStackTrace();
		}
		return beteiligung;
	}
		
	
	 public void delete(Beteiligung beteiligung) {
		    Connection con = DBConnection.connection();

		    try {
		      Statement stmt = con.createStatement();

		      stmt.executeUpdate("DELETE FROM Beteiligung " + "WHERE id=" + beteiligung.getBeteiligungId());
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }
		  }

}
