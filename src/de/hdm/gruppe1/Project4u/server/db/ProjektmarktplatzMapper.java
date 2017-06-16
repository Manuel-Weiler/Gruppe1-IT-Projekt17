package de.hdm.gruppe1.Project4u.server.db;

import java.sql.*;
import java.util.Vector;

import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;
import de.hdm.gruppe1.Project4u.shared.bo.Projektmarktplatz;

/**
 * Mapper-Klasse, die <code>Projektmarktplatz</code>-Objekte auf eine
 * relationale Datenbank abgebildet. Hierzu wird eine Reihe von Methoden zur
 * Verfügung gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt,
 * modifiziert und gelöscht werden können. Das Mapping ist bidirektional.
 * D.h., Objekte können in DB-Strukturen und DB-Strukturen in Objekte
 * umgewandelt werden.
 * 
 * 
 * @author Thies
 * @author Ugur
 */
public class ProjektmarktplatzMapper {

	/**
	 * Die Klasse ProjektmarktplatzMapper wird nur einmal instantiiert. Man
	 * spricht hierbei von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * fuer saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 */
	private static ProjektmarktplatzMapper projektmarktplatzMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit
	 * <code>new</code> neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected ProjektmarktplatzMapper() {

	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>ProjektmarktplatzMapper.projektmarktplatzMapper()</code>. Sie
	 * stellt die Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur
	 * eine einzige Instanz von <code>ProjektmarktplatzMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> ProjektmarktplatzMapper sollte nicht mittels
	 * <code>new</code> instantiiert werden, sondern stets durch Aufruf dieser
	 * statischen Methode.
	 * 
	 * @return projektmarktplatzMapper
	 *         <code>ProjektmartkplatzMapper</code>-Objekt.
	 */
	public static ProjektmarktplatzMapper projektmarktplatzMapper() {
		if (projektmarktplatzMapper == null) {
			projektmarktplatzMapper = new ProjektmarktplatzMapper();
		}
		return projektmarktplatzMapper;
	}

	/**
	 ** @param id
<<<<<<< HEAD
	 ** @return Liefert ein Projektmarktplatz entsprechend der �bergebenen id
	 *         zurueck.
=======
	 ** @return Liefert ein Projektmarktplatz entsprechend der �bergebenen id zurueck.
>>>>>>> refs/heads/master
	 **/

      public Projektmarktplatz findById(int id){
		 // DB-Verbindung holen
		 Connection con = DBConnection.connection();
		 Projektmarktplatz p = new Projektmarktplatz();
		 try {
		   // Leeres SQL-Statement (JDBC) anlegen
		   Statement stmt = con.createStatement();
		   
		   // Statement ausf�llen und als Query an die DB schicken
		   ResultSet rs = stmt.executeQuery("SELECT * FROM Projektmarktplatz " + "WHERE id='" + id +"'");
		   
		   /*
	        * Da id Prim�rschl�ssel ist, kann max. nur ein Tupel zur�ckgegeben
	        * werden. Pr�fe, ob ein Ergebnis vorliegt.
	        */
		    if (rs.next()) {
		      // Ergebnis-Tupel in Objekt umwandeln
		      
		      p.setProjektmarktplatzId(rs.getInt("id"));
		      p.setName(rs.getString("name"));
		      p.setOrganisationseinheitId(rs.getInt("organisationseinheit_id"));
		      
		      }
		    }
           catch (SQLException e) {
        	 e.printStackTrace();
        	 
           }
		 
		 	return p;
		   }
	  
	  /**
		 * Diese Methode bezieht ihre Informationen aus der
		 * Project4uAdministrationImpl und erstellt mit diesen einen neuen
		 * Projektmarktplatz in der Datenbank.
		 * 
		 * @param projektmarktplatz
		 * @return projektmarktplatz
		 */
	  
	  public Projektmarktplatz insert(Projektmarktplatz p){
		  Connection con = DBConnection.connection();
		  
		  try{
			  Statement stmt = con.createStatement();
			  
			  ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM projektmarktplatz");
			   
			  if (rs.next()) {
	              p.setProjektmarktplatzId(rs.getInt("maxid") + 1);
	            }
			  
			  stmt.executeUpdate("INSERT INTO Projektmarktplatz (id, name, organisationseinheit_id) " 
			           + "VALUES (" + p.getProjektmarktplatzId() + ", '" + p.getName()+"', " + p.getOrganisationseinheitId() + ");");
			                    
			          }
		      catch (SQLException e) {
		    	 e.printStackTrace();
		    	 }

		return p;

	}

	public Projektmarktplatz update(Projektmarktplatz p) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE projektmarktplatz SET name = '" + p.getName() + "' WHERE id ='"
					+ p.getProjektmarktplatzId() + "';");
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Um Analogie zu insert(Projektmarktplatz p) zu wahren, geben wir p
		// zurück
		return p;
	}

	/**
	 * Löschen der Daten eines <code>Projektmarktplatz</code>-Objekts aus der
	 * Datenbank.
	 * 
	 * @param p
	 *            das aus der DB zu löschende "Objekt"
	 */
	public void delete(Projektmarktplatz p) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM Projektmarktplatz WHERE id='" + p.getProjektmarktplatzId() + "'");

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	public Vector<Projektmarktplatz> findAll() {
		Connection con = DBConnection.connection();
		// Ergebnisvektor vorbereiten
		Vector<Projektmarktplatz> result = new Vector<Projektmarktplatz>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM Projektmarktplatz");

		      // Für jeden Eintrag im Suchergebnis wird nun ein Projektmarktplatz-Objekt
		      // erstellt.
		      while (rs.next()) {
		        Projektmarktplatz p = new Projektmarktplatz();
		        p.setProjektmarktplatzId(rs.getInt("id"));
		        p.setName(rs.getString("name"));
		        p.setOrganisationseinheitId(rs.getInt("organisationseinheit_id"));

		        
		      }
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Ergebnisvektor zurückgeben
		return result;

	}

	  
	  public Vector<Projektmarktplatz> findByOrganisationseinheit(Organisationseinheit o) {
		    Connection con = DBConnection.connection();
		    // Ergebnisvektor vorbereiten
		    Vector<Projektmarktplatz> result = new Vector<Projektmarktplatz>();

		    try {
		      Statement stmt = con.createStatement();

		      ResultSet rs = stmt.executeQuery("SELECT * FROM Projektmarktplatz WHERE organisationseinheit_id= " + o.getOrganisationseinheitId() + 
		    		  " ORDER BY organisationseinheit_id");
		   

		      // Für jeden Eintrag im Suchergebnis wird nun ein Projekt-Objekt
		      // erstellt.
		      while (rs.next()) {
		        Projektmarktplatz pm = new Projektmarktplatz();
		        pm.setProjektmarktplatzId(rs.getInt("id"));
			    pm.setName(rs.getString("name"));
			    pm.setOrganisationseinheitId(rs.getInt("organisationseinheit_id"));
		

		        // Hinzufügen des neuen Objekts zum Ergebnisvektor
		        result.addElement(pm);
		      }
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }

		    // Ergebnisvektor zurückgeben
		    return result;
	 }
	  

}
