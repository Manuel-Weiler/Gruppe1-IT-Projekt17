package de.hdm.gruppe1.Project4u.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;

import com.ibm.icu.text.SimpleDateFormat;

import de.hdm.gruppe1.Project4u.shared.bo.*;


public class PartnerprofilMapper {

		/**
	 * Die statische Variable partnerprofilMapper stellt sicher, dass es von der
	 * Klasse PartnerprofilMapper nur eine einzige Instanz gibt bzw. die
	 * Variable speichert die einzige Instanz dieser Klasse.
	 * @author Tobias
	 */
	private static PartnerprofilMapper partnerprofilMapper = null;

	/*
	 * Der private Konstruktor verhindert, dass eine Instanz der Klasse
	 * PartnerprofilMapper über <code>new</code> erzeugt werden kann.
	 */
	private PartnerprofilMapper() {
	}

	
	/**
	 * Die Methode partnerprofilMapper stellt die Singleton-Eigenschaft sicher,
	 * indem Sie dafÃ¼r sorgt, dass nur eine einzige Instanz von
	 * <code>NutzerpofilMapper</code> existiert.
	 * @return ein neu instanziiertes PartnerprofilMapper-Objekt
	 * @author Tobias
	 */
	public static PartnerprofilMapper partnerprofilMapper() {
		if (partnerprofilMapper == null) {
			partnerprofilMapper = new PartnerprofilMapper();
		}
		return partnerprofilMapper;
	}

		
	 /**
	  * Mit dieser Methode wird dem zu speichernden Partnerprofil die richtige
	 * <code>id</code> vergeben und das Partnerprofil in der Datenbank abgelegt.
	 * @param p das Partnerprofil-Objekt, dass in der Datenbank abgelegt wird.
	 * @param o das Organisationseinheit-Objekt, dem das Partnerprofil zugeordnet ist.
	 * @return das möglicherweise durch die Methode geänderte Partnerprofil-Objekt.
	 * @author Tobias
	 */
	public Partnerprofil insertPartnerprofil(Partnerprofil p, Organisationseinheit o){
		 
		 Connection con = DBConnection.connection();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 Date date = new Date();
			p.setAenderungsdatum(date);
			p.setErstelldatum(date);

		    try {
		      Statement stmt = con.createStatement();

		      //Abfrage der größten bisher vergebnen <code>id</code>
		      ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
		          + "FROM Partnerprofil ");

		      if (rs.next()) {
		        /*
		         * Der bisher größte Primärschlüssel wird um 1 erhöht und dem 
		         * Partnerprofil-Objekt zugewiesen.
		         */
		        p.setPartnerprofilId(rs.getInt("maxid") + 1);

		        stmt = con.createStatement();
		        
		        

		        // Jetzt erst erfolgt die tatsÃ¤chliche EinfÃ¼geoperation
		        stmt.executeUpdate("INSERT INTO Partnerprofil (id, erstelldatum, änderungsdatum, Organisationseinheit_id) "
		            + "VALUES (" + p.getPartnerprofilId() + ",'" + sdf.format(p.getErstelldatum()) + "','"
		            + sdf.format(p.getAenderungsdatum()) + "','"+o.getOrganisationseinheitId()+"')"); 
		      }
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }

		    /*
		     * Rückgabe, des evtl. korrigierten Partnerprofil-Objekts.
		     * P.S: Diese Rückgabe ist nicht zwingend notwendig, da die Verweise auf das bisherige 
		     * Objekt auch auf das geänderte Objekt verweisen würden. 
		     */
		 
		return p;
		 
	 }
	 
	 
	
	/**	  
	 * Mit dieser Methode wird ein Partnerprofil-Objekt mit einer bestimmten <code>id</code>ausgegeben.
	 * @param id
	 * @return Partnerprofil p aus der DB
	 * @author Tobias
	 */
	public Partnerprofil findById(int i) {
		Connection con = DBConnection.connection();
		Partnerprofil p = new Partnerprofil();

		try {
			Statement stmt = con.createStatement();

			// Abfrage des gesuchten Partnerprofils zur <code>id</code>
			ResultSet rs = stmt.executeQuery("SELECT * " + "FROM Partnerprofil WHERE id='" + i + "'");

			if (rs.next()) {

				/*
				 * Dem Rückgabeobjekt werden die Werte aus der Tabelle
				 * zugewiesen und so das Tupel aus der Tabelle wieder in ein
				 * Objekt transformiert.
				 */
				p.setID(rs.getInt("id"));
				p.setErstelldatum(rs.getDate("erstelldatum"));
				p.setAenderungsdatum(rs.getDate("änderungsdatum"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}


	/*
	 * Die nachfolgende Methode speichert Veränderungen am Partnerprofilobjekt
	 * in der Datenbank
	 */
	public Partnerprofil updatePartnerprofil(Partnerprofil p) {
		Connection con = DBConnection.connection();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE Partnerprofil " + "SET änderungsdatum='" + sdf.format(d) + "' WHERE id='"
					+ p.getPartnerprofilId() + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}
	
	/*
	 * Diese Methode löscht ein Partnerprofil aus der Datebank.
	 */
	public void deletePartnerprofil (Partnerprofil p){
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM Partnerprofil WHERE id='"
					+ p.getPartnerprofilId() + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
//////////////////////////	
	}
	/* TODO: Anpassen, wenn Klasse Ausschreibung&Ausschreibungsmapper existiert
	 * Diese Methode gibt die Ausschreibung zurück, die durch das Partnerprofil-Objekt 
	 * beschrieben wird.
	public Ausschreibung getAusschreibungOf(Partnerprofil p)
	{
		return a;
	} */
/////////////////////////
	
	/*
	 * Diese Methode gibt die zugehörige Organisationseinheit zu einem Partnerprofil zurück.
	 * Dabei wird sich des Mappers der Klasse OrganisationseinheitMapper bedient.
	 */
	public Organisationseinheit getOrganisationseinheitOfPartnerprofil (Partnerprofil p){
		
		return OrganisationseinheitMapper.organisationseinheitMapper().findByKey(p.getOrganisationseinheitId());
	}
	
////////////////////////
	
	
	/**
	 * Diese Methode gibt alle Eigenschaftsobjekte zu einem Partnerprofil-Objekt p zurück
	 * @param p
	 * @return
	 */
	public Vector <Eigenschaft> getEigenschaftenOfPartnerprofil (Partnerprofil p){
		
		
		
		return EigenschaftMapper.eigenschaftMapper().selectAllEigenschaft(p);
	}
	
}

