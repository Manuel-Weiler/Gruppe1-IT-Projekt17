package de.hdm.gruppe1.Project4u.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import com.ibm.icu.text.SimpleDateFormat;

import de.hdm.gruppe1.Project4u.shared.bo.*;


public class PartnerprofilMapper {

	/*
	 * Die statische Variable partnerprofilMapper stellt sicher, dass es von der
	 * Klasse PartnerprofilMapper nur eine einzige Instanz gibt bzw. die
	 * Variable speichert die einzige Instanz dieser Klasse.
	 */
	private static PartnerprofilMapper partnerprofilMapper = null;

	/*
	 * Der private Konstruktor verhindert, dass eine Instanz der Klasse
	 * PartnerprofilMapper �ber <code>new</code> erzeugt werden kann.
	 */
	private PartnerprofilMapper() {
	}

	/*
	 * Die Methode partnerprofilMapper stellt die Singleton-Eigenschaft sicher,
	 * indem Sie dafür sorgt, dass nur eine einzige Instanz von
	 * <code>NutzerpofilMapper</code> existiert.
	 */
	public static PartnerprofilMapper partnerprofilMapper() {
		if (partnerprofilMapper == null) {
			partnerprofilMapper = new PartnerprofilMapper();
		}
		return partnerprofilMapper;
	}

	/*
	 * Mit dieser Methode wird dem zu speichernden Partnerprofil die richtige
	 * <code>id</code> vergeben und das Partnerprofil in der Datenbank abgelegt.
	 * 
	 * @param: p das Partnerprofil-Objekt, dass in der Datenbank abgelegt wird.
	 * @return: das m�glicherweise durch die Methode ge�nderte Partnerprofil-Objekt.
	 */
	//TODO: Organisationseinheit-Objekt als Parameter hinzuf�gen.
	 public Partnerprofil insertPartnerprofil(Partnerprofil p){
		 
		 Connection con = DBConnection.connection();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 Date date = new Date();
			p.setAenderungsdatum(date);
			p.setErstelldatum(date);

		    try {
		      Statement stmt = con.createStatement();

		      //Abfrage der gr��ten bisher vergebnen <code>id</code>
		      ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
		          + "FROM Partnerprofil ");

		      if (rs.next()) {
		        /*
		         * Der bisher gr��te Prim�rschl�ssel wird um 1 erh�ht und dem 
		         * Partnerprofil-Objekt zugewiesen.
		         */
		        p.setPartnerprofilId(rs.getInt("maxid") + 1);

		        stmt = con.createStatement();
		        
		        

		        // Jetzt erst erfolgt die tatsächliche Einfügeoperation
		        stmt.executeUpdate("INSERT INTO Partnerprofil (id, erstelldatum, �nderungsdatum, Organisationseinheit_id) "
		            + "VALUES (" + p.getID() + ",'" + sdf.format(p.getErstelldatum()) + "','"
		            + sdf.format(p.getAenderungsdatum()) + "','1')"); //TODO: Organisationseiheit_id in DB speichern
		      }
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }

		    /*
		     * R�ckgabe, des evtl. korrigierten Partnerprofil-Objekts.
		     * P.S: Diese R�ckgabe ist nicht zwingend notwendig, da die Verweise auf das bisherige 
		     * Objekt auch auf das ge�nderte Objekt verweisen w�rden. 
		     */
		 
		return p;
		 
	 }
	 
	 /*
	  * Mit dieser Methode wird ein Partnerprofil-Objekt mit einer bestimmten id ausgegeben.
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
				 * Dem R�ckgabeobjekt werden die Werte aus der Tabelle
				 * zugewiesen und so das Tupel aus der Tabelle wieder in ein
				 * Objekt transformiert.
				 */
				p.setID(rs.getInt("id"));
				p.setErstelldatum(rs.getDate("erstelldatum"));
				p.setAenderungsdatum(rs.getDate("�nderungsdatum"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}


	/*
	 * Die nachfolgende Methode speichert Ver�nderungen am Partnerprofilobjekt
	 * in der Datenbank
	 */
	public Partnerprofil updatePartnerprofil(Partnerprofil p) {
		Connection con = DBConnection.connection();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE Partnerprofil " + "SET �nderungsdatum='" + sdf.format(d) + "' WHERE id='"
					+ p.getPartnerprofilId() + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}
	
	/*
	 * Diese Methode l�scht eine Partnerprofil aus der Datebank.
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
		
	}
	
}

