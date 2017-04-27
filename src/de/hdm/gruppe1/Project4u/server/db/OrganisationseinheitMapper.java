package de.hdm.gruppe1.Project4u.server.db;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;



/**
 * Mapper-Klasse, die <code>Nutzerprofil</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfügung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gelöscht werden können. Das Mapping ist bidirektional. D.h., Objekte können
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * 
 * 
 * @author Thies
 * @author Dominik
 */
public class OrganisationseinheitMapper {
	
	/**
	 * Die Klasse NutzerprofilMapper wird nur einmal instantiiert. Man spricht
	 * hierbei von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * fuer saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 */
	private static OrganisationseinheitMapper organisationseinheitMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit
	 * <code>new</code> neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected OrganisationseinheitMapper(){
		
	}
	
	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>NutzerprofilMapper.nutzerpofilMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine
	 * einzige Instanz von <code>NutzerpofilMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> NutzerprofilMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * 
	 * @return nutzerprofilMapper <code>NutzerprofilMapper</code>-Objekt.
	 */
	public static OrganisationseinheitMapper organisationseinheitMapper(){
		if(organisationseinheitMapper == null){
			organisationseinheitMapper = new OrganisationseinheitMapper();
		}
		return organisationseinheitMapper;
	}

	/**
	 * Diese Methode bezieht ihre Informationen aus der
	 * PartnerboerseAdministrationImpl und erstellt mit diesen einen neuen
	 * Nutzer in der Datenbank.
	 * 
	 * @param organisationseinheit
	 * @return organisationseinheit
	 */
	
	public Organisationseinheit insert(Organisationseinheit organisationseinheit){
		Connection con = DBConnection.connection();
		
		try{
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM Organisationseinheit ");
			
			
			if(rs.next()){
				organisationseinheit.setOrganisationseinheitId(rs.getInt("maxid") +1);
				
				stmt.executeUpdate("INSERT INTO Organisationseinheit (id, vorname, nachname) "
			            + "VALUES (" + organisationseinheit.getOrganisationseinheitId() + ",'" + organisationseinheit.getVorname() + "','"
			            + organisationseinheit.getNachname() + "')");
			}
		} catch (SQLException e2){
			e2.printStackTrace();
		}
		return organisationseinheit;
	}
	
	public Organisationseinheit update(Organisationseinheit c) {
	    Connection con = DBConnection.connection();

	    try {
	      Statement stmt = con.createStatement();

	      stmt.executeUpdate("UPDATE Organisationseinheit " + "SET vorname=\""
	          + c.getVorname() + "\", " + "nachname=\"" + c.getNachname() + "\" "
	          + "WHERE id=" + c.getOrganisationseinheitId());

	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	    }

	    // Um Analogie zu insert(Customer c) zu wahren, geben wir c zurück
	    return c;
	  }
	
	 public void delete(Organisationseinheit c) {
		    Connection con = DBConnection.connection();

		    try {
		      Statement stmt = con.createStatement();

		      stmt.executeUpdate("DELETE FROM Organisationseinheit " + "WHERE id=" + c.getOrganisationseinheitId());
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }
		  }
	
	/**
	 * In dieser Methode wird überprüft ob der Nutzer bereits in der Datenbank
	 * vorhanden ist.
	 *
	 * Die Überprüfung wird anhand der Emailadresse vorgenommen, welche in dem
	 * Nutzerobjekt loginInfo enthalten ist.
	 * 
	 * @param loginInfo
	 * @param organisationseinheit
	 *            Nutzerdaten des Users werden hineingeladen
	 * @param con
	 *            Datenbankverbindung
	 * @param email
	 *            Email Adresse des Users, der sich einloggen will
	 * @return daten werden zurueckgegeben.
	 */
	public Organisationseinheit checkStatus(Organisationseinheit loginInfo){
		Organisationseinheit organisationseinheit = loginInfo;
		
		Connection con = DBConnection.connection();
		String email = loginInfo.getEmailAddress();
		
		try{
			//Anlegen eines leeren SQL-Statements
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM Organisationseinheit WHERE " + "GoogleMail = '" + email + "'");
		
		if(rs.next()){
			organisationseinheit.setStatus(true);
			organisationseinheit.setOrganisationseinheitId(rs.getInt("id"));
			organisationseinheit.setEmailAddress(rs.getString("GoogleMail"));
			
			ResultSet rs2 = stmt.executeQuery("SELECT * FROM Organisationseinheit WHERE " + "id = " + rs.getInt("id"));
			if(rs2.next()){
				organisationseinheit.setID(rs2.getInt("id"));
			}
		} else{
			organisationseinheit.setStatus(false);
		}
	} catch(SQLException e){
		e.printStackTrace();
		return null;
	}
		return organisationseinheit;
	}
	
	 public Organisationseinheit findByKey(int id) {
		    // DB-Verbindung holen
		    Connection con = DBConnection.connection();

		    try {
		      // Leeres SQL-Statement (JDBC) anlegen
		      Statement stmt = con.createStatement();

		      // Statement ausfüllen und als Query an die DB schicken
		      ResultSet rs = stmt
		          .executeQuery("SELECT id, google_id, nachname, vorname FROM Organisationseinheit "
		              + "WHERE id=" + id + " ORDER BY nachname");

		      /*
		       * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
		       * werden. Prüfe, ob ein Ergebnis vorliegt.
		       */
		      if (rs.next()) {
		        // Ergebnis-Tupel in Objekt umwandeln
		        Organisationseinheit c = new Organisationseinheit();
		        c.setOrganisationseinheitId(rs.getInt("id"));
		        c.setNachname(rs.getString("nachname"));
		        c.setVorname(rs.getString("vorname"));

		        return c;
		      }
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		      return null;
		    }

		    return null;
		  }
	 
	 public Vector<Organisationseinheit> findAll() {
		    Connection con = DBConnection.connection();
		    // Ergebnisvektor vorbereiten
		    Vector<Organisationseinheit> result = new Vector<Organisationseinheit>();

		    try {
		      Statement stmt = con.createStatement();

		      ResultSet rs = stmt.executeQuery("SELECT id, firstName, lastName "
		          + "FROM customers " + "ORDER BY lastName");

		      // Für jeden Eintrag im Suchergebnis wird nun ein Organisationseinheit-Objekt
		      // erstellt.
		      while (rs.next()) {
		        Organisationseinheit c = new Organisationseinheit();
		        c.setOrganisationseinheitId(rs.getInt("id"));
		        c.setVorname(rs.getString("firstName"));
		        c.setNachname(rs.getString("lastName"));

		        // Hinzufügen des neuen Objekts zum Ergebnisvektor
		        result.addElement(c);
		      }
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }

		    // Ergebnisvektor zurückgeben
		    return result;
		  }
	 
	 public Vector<Organisationseinheit> findByNachname(String name) {
		    Connection con = DBConnection.connection();
		    Vector<Organisationseinheit> result = new Vector<Organisationseinheit>();

		    try {
		      Statement stmt = con.createStatement();

		      ResultSet rs = stmt.executeQuery("SELECT id, vorname, nachname "
		          + "FROM Organisationseinheit " + "WHERE nachname LIKE '" + name
		          + "' ORDER BY nachname");

		      // Für jeden Eintrag im Suchergebnis wird nun ein Organisationseinheit-Objekt
		      // erstellt.
		      while (rs.next()) {
		        Organisationseinheit c = new Organisationseinheit();
		        c.setOrganisationseinheitId(rs.getInt("id"));
		        c.setVorname(rs.getString("vorname"));
		        c.setNachname(rs.getString("nachname"));

		        // Hinzufügen des neuen Objekts zum Ergebnisvektor
		        result.addElement(c);
		      }
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }

		    // Ergebnisvektor zurückgeben
		    return result;
		  }
}
