package de.hdm.gruppe1.Project4u.server.db;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;

import com.ibm.icu.text.SimpleDateFormat;

import de.hdm.gruppe1.Project4u.server.db.DBConnection;
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Bewerbung;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;

/**
 * Mapper-Klasse, die <code>Nutzerprofil</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfügung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gelöscht werden können. Das Mapping ist bidirektional. D.h., Objekte
 * können in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * 
 * 
 * @author Thies
 * @author Dominik
 */

public class BewerbungMapper {

	/**
	 * Die Klasse NutzerprofilMapper wird nur einmal instantiiert. Man spricht
	 * hierbei von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * fuer saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 */

	private static BewerbungMapper bewerbungMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit
	 * <code>new</code> neue Instanzen dieser Klasse zu erzeugen.
	 */

	protected BewerbungMapper() {

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
	 * @return bewerbungMapper <code>BewerbungMapper</code>-Objekt.
	 */
	public static BewerbungMapper bewerbungMapper() {
		if (bewerbungMapper == null) {
			bewerbungMapper = new BewerbungMapper();
		}
		return bewerbungMapper;
	}

	/**
	 * Diese Methode bezieht ihre Informationen aus der Project4uImpl und
	 * erstellt mit diesen eine neue Bewerbung in der Datenbank.
	 * 
	 * @param bewerbung
	 * @return bewerbung
	 */

	public Bewerbung insert(Bewerbung bewerbung, Ausschreibung a, Organisationseinheit o) {
		Connection con = DBConnection.connection();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		bewerbung.setErstelldatum(date);
		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM Bewerbung ");
			
			if(rs.next()){
				bewerbung.setBewerbungID(rs.getInt("maxid")+1);
				
				stmt.executeUpdate("INSERT INTO Bewerbung (id, erstelldatum, bewerbungstext, ausschreibung_id, organisationseinheit_id)"
						+ "VALUES ('" + bewerbung.getBewerbungID() + "','" + sdf.format(bewerbung.getErstelldatum()) + "','" 
						+ bewerbung.getBewerbungstext() + "', '"+a.getAusschreibungId()+"', '"+o.getOrganisationseinheitId()+"')");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bewerbung;
	}

	/**
	 * Diese Methode bezieht ihre Informationen aus der Project4uImpl und
	 * erm�glicht es den Bewerbungstext in der Datenbank zu �ndern.
	 * 
	 * @param bewerbung
	 * @return bewerbung
	 */

	public void update(Bewerbung bewerbung){
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(
					"UPDATE Bewerbung SET bewerbungstext = '" + bewerbung.getBewerbungstext() + "' WHERE id = '"
							+ bewerbung.getBewerbungID()+"';");

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Diese Methode bezieht ihre Informationen aus der Project4uImpl und
	 * erm�glicht es eine Bewerbung aus der Datenbank zu l�schen.
	 * 
	 * @param bewerbung
	 * @return bewerbung
	 */

	public void delete(Bewerbung bewerbung) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Bewerbung WHERE id=" + bewerbung.getBewerbungID());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	public Bewerbung findById(int id) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();
		Bewerbung b = new Bewerbung();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
							"SELECT * FROM Bewerbung WHERE id= '" + id + "'");

			/*
			 * Da id Primärschlüssel ist, kann max. nur ein Tupel
			 * zurückgegeben werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				b.setBewerbungID(rs.getInt("id"));
				b.setErstelldatum(rs.getDate("erstelldatum"));
				b.setBewerbungstext(rs.getString("bewerbungstext"));
				b.setAusschreibungId(rs.getInt("ausschreibung_id"));
				b.setOrganisationseinheitId(rs.getInt("organisationseinheit_id"));

				return b;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}

	public Vector<Bewerbung> findAll() {
		Connection con = DBConnection.connection();
		// Ergebnisvektor vorbereiten
		Vector<Bewerbung> result = new Vector<Bewerbung>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, erstelldatum, bewerbungstext, "
					+ "ausschreibung_id, organisationseinheit_id " + "FROM Bewerbung ORDER BY id");

			// Für jeden Eintrag im Suchergebnis wird nun ein
			// Organisationseinheit-Objekt
			// erstellt.
			while (rs.next()) {
				Bewerbung b = new Bewerbung();
				b.setBewerbungID(rs.getInt("id"));
				b.setErstelldatum(rs.getDate("erstelldatum"));
				b.setBewerbungstext(rs.getString("bewerbungstext"));
				b.setAusschreibungId(rs.getInt("ausschreibung_id"));
				b.setOrganisationseinheitId(rs.getInt("organisationseinheit_id"));

				// Hinzufügen des neuen Objekts zum Ergebnisvektor
				result.addElement(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Ergebnisvektor zurückgeben
		return result;
	}

	public Vector<Bewerbung> findByOrganisationseinheit(Organisationseinheit o) {
		Connection con = DBConnection.connection();
		// Ergebnisvektor vorbereiten
		Vector<Bewerbung> result = new Vector<Bewerbung>();



	    try {
	    	
	    	Statement stmt = con.createStatement();
	    	ResultSet rs = stmt.executeQuery("SELECT id, erstelldatum, bewerbungstext, "
	    	 		+ "ausschreibung_id, organisationseinheit_id "
	   	          	+ "FROM Bewerbung WHERE organisationseinheit_id= '" + o.getOrganisationseinheitId() + "' ORDER BY id");

			// Für jeden Eintrag im Suchergebnis wird nun ein
			// Organisationseinheit-Objekt
			// erstellt.
			while (rs.next()) {
				Bewerbung b = new Bewerbung();
				b.setBewerbungID(rs.getInt("id"));
				b.setErstelldatum(rs.getDate("erstelldatum"));
				b.setBewerbungstext(rs.getString("bewerbungstext"));
				b.setAusschreibungId(rs.getInt("ausschreibung_id"));
				b.setOrganisationseinheitId(rs.getInt("organisationseinheit_id"));

				// Hinzufügen des neuen Objekts zum Ergebnisvektor
				result.addElement(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Ergebnisvektor zurückgeben
		return result;

	  }
	
	public Vector<Bewerbung> findByAusschreibung(Ausschreibung ausschreibung) {
		Connection con = DBConnection.connection();
		// Ergebnisvektor vorbereiten
		Vector<Bewerbung> result = new Vector<Bewerbung>();

	    try {
	    	
	    	Statement stmt = con.createStatement();
	    	ResultSet rs = stmt.executeQuery(	"SELECT * FROM Bewerbung WHERE ausschreibung_id= " + 
	    										ausschreibung.getAusschreibungId() + " ORDER BY id");

			while (rs.next()) {
				Bewerbung b = new Bewerbung();
				b.setBewerbungID(rs.getInt("id"));
				b.setErstelldatum(rs.getDate("erstelldatum"));
				b.setBewerbungstext(rs.getString("bewerbungstext"));
				b.setAusschreibungId(rs.getInt("ausschreibung_id"));
				b.setOrganisationseinheitId(rs.getInt("organisationseinheit_id"));

				// Hinzufügen des neuen Objekts zum Ergebnisvektor
				result.addElement(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Ergebnisvektor zurückgeben
		return result;

	  }
	
	public void deleteBewerbungOfOrganisationseinheit(Organisationseinheit o) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Bewerbung WHERE organisationseinheit_id= " + o.getOrganisationseinheitId());
			
		} catch (Exception e2) {
			 e2.printStackTrace();
		}
	}
	
	public void deleteBewerbungOfAusschreibung(Ausschreibung a) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Bewerbung WHERE ausschreibung_id= " + a.getAusschreibungId());
			
		} catch (Exception e2) {
			 e2.printStackTrace();
		}

	}
	
	

}
