package de.hdm.gruppe1.Project4u.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Beteiligung;
import de.hdm.gruppe1.Project4u.shared.bo.Bewertung;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;
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
	
	
	public Beteiligung insertBeteiligung(Beteiligung b) {
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
						+ b.getBeteiligungId() + ",'" 
						+ b.getStartdatum() + "','" 
						+ b.getEnddatum() + "','" 
						+ b.getPersonentage() + "','" 
						+ b.getOrganisationseinheitId() + "','" 
						+ b.getProjektId() + "','" 
						+ b.getBewertungId() + "')");
			}
		} catch (SQLException s) {
			s.printStackTrace();
		}


		return b;
		}
		
	
	public void delete(Beteiligung b) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Beteiligung WHERE id= " + b.getBeteiligungId());
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

	public Beteiligung findByBewertung(Bewertung bewertung) {
		Connection con = DBConnection.connection();
		

		try {
			Statement stmt = con.createStatement();

			// Abfrage des gesuchten Partnerprofils zur <code>id</code>
			ResultSet rs = stmt.executeQuery("SELECT * FROM Beteiligung WHERE bewertung_id=" + bewertung.getBewerbungId());

			if (rs.next()) {

				/*
				 * Dem R�ckgabeobjekt werden die Werte aus der Tabelle
				 * zugewiesen und so das Tupel aus der Tabelle wieder in ein
				 * Objekt transformiert.
				 */
				Beteiligung b = new Beteiligung();
				b.setBeteiligungId(rs.getInt("id"));
				b.setStartdatum(rs.getDate("startdatum"));
				b.setEnddatum(rs.getDate("enddatum"));
				b.setPersonentage(rs.getInt("personentage"));
				b.setOrganisationseinheitId(rs.getInt("organisationseinheit_id"));
				b.setProjektId(rs.getInt("projekt_id"));
				b.setBewertungId(rs.getInt("bewertung_id"));
			    
				return b;
		      }
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		      return null;
		    }

		    return null;
		  }
	
	public Vector<Beteiligung> findByProjekt (Projekt projekt) {
	    Connection con = DBConnection.connection();
	    Vector<Beteiligung> result = new Vector<Beteiligung>();

	    try {
	      Statement stmt = con.createStatement();

	      ResultSet rs = stmt.executeQuery("SELECT * FROM Beteiligung WHERE projekt_id= " + projekt.getProjektId() + 
	    		  " ORDER BY id");

	 
	      while (rs.next()) {
	    	  Beteiligung b = new Beteiligung();
	    	  b.setBeteiligungId(rs.getInt("id"));
	    	  b.setStartdatum(rs.getDate("startdatum"));
	    	  b.setEnddatum(rs.getDate("enddatum"));
	    	  b.setPersonentage(rs.getInt("personentage"));
	    	  b.setOrganisationseinheitId(rs.getInt("organisationseinheit_id"));
	    	  b.setProjektId(rs.getInt("projekt_id"));
	    	  b.setBewertungId(rs.getInt("bewertung_id"));

	        result.addElement(b);
	      }
	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	    }

	    return result;
	  }
	
	
	public Vector<Beteiligung> findByOrganisationseinheit (Organisationseinheit organisationseinheit) {
	    Connection con = DBConnection.connection();
	    Vector<Beteiligung> result = new Vector<Beteiligung>();

	    try {
	      Statement stmt = con.createStatement();

	      ResultSet rs = stmt.executeQuery("SELECT * FROM Beteiligung WHERE organisationseinheit_id= " + organisationseinheit.getOrganisationseinheitId() + 
	    		  " ORDER BY id");

	 
	      while (rs.next()) {
	    	  Beteiligung b = new Beteiligung();
	    	  b.setBeteiligungId(rs.getInt("id"));
	    	  b.setStartdatum(rs.getDate("startdatum"));
	    	  b.setEnddatum(rs.getDate("enddatum"));
	    	  b.setPersonentage(rs.getInt("personentage"));
	    	  b.setOrganisationseinheitId(rs.getInt("organisationseinheit_id"));
	    	  b.setProjektId(rs.getInt("projekt_id"));
	    	  b.setBewertungId(rs.getInt("bewertung_id"));

	        result.addElement(b);
	      }
	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	    }

	    return result;
	  }

}
