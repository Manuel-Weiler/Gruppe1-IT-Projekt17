package de.hdm.gruppe1.Project4u.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;
import de.hdm.gruppe1.Project4u.shared.bo.Projektmarktplatz;

/**
 * Mapper-Klasse, die <code>Projekt</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfügung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gelöscht werden können. Das Mapping ist bidirektional. D.h., Objekte können
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * 
 * 
 * @author Thies
 * @author Ugur
 */
public class ProjektMapper {

	

	/**
	 * Die Klasse ProjektMapper wird nur einmal instantiiert. Man spricht
	 * hierbei von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * fuer saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 */
	private static ProjektMapper projektMapper = null;
	
	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit
	 * <code>new</code> neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected ProjektMapper(){
		
	}
	
	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>ProjektMapper.projektMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine
	 * einzige Instanz von <code>ProjektMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> ProjektMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * 
	 * @return projektMapper <code>ProjektMapper</code>-Objekt.
	 */
	public static ProjektMapper projektMapper() {
	    if (projektMapper == null) {
	      projektMapper = new ProjektMapper();
	    }
         return projektMapper;
	  }
	
	/**
	 ** @param id
	 ** @return Liefert ein Projekt entsprechend der �bergebenen id zurueck.
	 **/

      public Projekt findById(int id){
		 // DB-Verbindung holen
		 Connection con = DBConnection.connection();
		 
		 try {
		   // Leeres SQL-Statement (JDBC) anlegen
		   Statement stmt = con.createStatement();
		   

		   // Statement ausf�llen und als Query an die DB schicken
		   ResultSet rs = stmt.executeQuery("SELECT * "  + "FROM Projekt WHERE id='" + id + "'");

	
		   
		   /*
	        * Da id Prim�rschl�ssel ist, kann max. nur ein Tupel zur�ckgegeben
	        * werden. Pr�fe, ob ein Ergebnis vorliegt.
	        */
		    if (rs.next()) {
		      // Ergebnis-Tupel in Objekt umwandeln
		      Projekt p = new Projekt();
		      p.setProjektId(rs.getInt("id"));
		      p.setName(rs.getString("name"));
		      p.setStartdatum(rs.getDate("startdatum"));
		      p.setEnddatum(rs.getDate("enddatum"));
		      p.setBeschreibung(rs.getString("beschreibung"));
		      p.setProjektmarktplatzId(rs.getInt("projektmarktplatz_id"));
		      p.setOrganisationseinheitId(rs.getInt("organisationseinheit_id"));
		      
		      return p;
		      }
		    }
           catch (SQLException e) {
        	 e.printStackTrace();
        	 return null; 
           }
		 
		     return null;
		   }
	  
	  /**
		 * Diese Methode bezieht ihre Informationen aus der
		 * Project4uAdministrationImpl und erstellt mit diesen einen neuen
		 * Projekt in der Datenbank.
		 * 
		 * @param projekt
		 * @return projekt
		 */
	  
	  public Projekt insert(Projekt p, Projektmarktplatz pm, Organisationseinheit o){
		  Connection con = DBConnection.connection();
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  Date date = new Date();
		  p.setStartdatum(date);
		 // p.setEnddatum(date);
		  
		  try{
			  Statement stmt = con.createStatement();
			  ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM Projekt ");
			  
			  if (rs.next()) {
	              p.setProjektId(rs.getInt("maxid") + 1);
	            }
			  
			  stmt.executeUpdate("INSERT INTO Projekt (id ,name, startdatum, enddatum, beschreibung," +
				  		"projektmarktplatz_id, organisationseinheit_id)  VALUES (" + p.getProjektId() + ", '" + p.getName()
				  		+ "', '" + sdf.format(p.getStartdatum()) + "', '"
				  		+ sdf.format(p.getEnddatum()) + "', '"  + p.getBeschreibung() + "', '" + 
				  		p.getProjektmarktplatzId() + "', '" + o.getOrganisationseinheitId() + "')");
			                    
			          }
		      catch (SQLException e) {
		    	 e.printStackTrace();
		    	 }
		  
		return p;

		   }
	  
	  /**
	   * Wiederholtes Schreiben eines Objekts in die Datenbank.
	   * 
	   * @param p das Objekt, das in die DB geschrieben werden soll
	   * @return das als Parameter übergebene Objekt
	   */
	  public Projekt update(Projekt p) {
	    Connection con = DBConnection.connection();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	    try {
	      Statement stmt = con.createStatement();

	      stmt.executeUpdate("UPDATE Projekt SET startdatum='" + sdf.format(p.getStartdatum()) + "', "
			  		+ "enddatum='" + sdf.format(p.getEnddatum()) + "', " + "name='" + p.getName() + "', "
					+ "beschreibung='" + p.getBeschreibung() + "', " + " WHERE id=" + p.getProjektId());

	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }

	    // Um Analogie zu insert(Projekt p) zu wahren, geben wir p zurück
	    return p;  
	  }
	  
	  /**
	   * Löschen der Daten eines <code>Projekt</code>-Objekts aus der Datenbank.
	   * 
	   * @param p das aus der DB zu löschende "Objekt"
	   */
	  public void delete(Projekt p) {
	    Connection con = DBConnection.connection();

	    try {
	      Statement stmt = con.createStatement();

	      stmt.executeUpdate("DELETE FROM Projekt WHERE id=" + p.getProjektId() +"'");

	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }
	  }
	  
	  public Vector<Projekt> findAll() {
		    Connection con = DBConnection.connection();
		    // Ergebnisvektor vorbereiten
		    Vector<Projekt> result = new Vector<Projekt>();

		    try {
		      Statement stmt = con.createStatement();

		      ResultSet rs = stmt.executeQuery("SELECT id, name, startdatum, enddatum, beschreibung,"
		      		                         + "projektmarktplatz_id, organisationseinheit_id" 
		    		                         + "FROM Projekt" + "ORDER BY id"+ "'");
		   

		      // Für jeden Eintrag im Suchergebnis wird nun ein Projekt-Objekt
		      // erstellt.
		      while (rs.next()) {
		        Projekt p = new Projekt();
		        p.setProjektId(rs.getInt("id"));
		        p.setName(rs.getString("name"));
		        p.setStartdatum(rs.getDate("startdatum"));
		        p.setEnddatum(rs.getDate("enddatum"));
		        p.setBeschreibung(rs.getString("beschreibung"));
		        p.setProjektmarktplatzId(rs.getInt("projektmarktplatz_id"));
		        p.setOrganisationseinheitId(rs.getInt("organisationseinheit_id"));



		        // Hinzufügen des neuen Objekts zum Ergebnisvektor
		        result.addElement(p);
		      }
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }

		    // Ergebnisvektor zurückgeben
		    return result;
	 }
	  
	  public Vector<Projekt> findByName(String name) {
		    Connection con = DBConnection.connection();
		    Vector<Projekt> result = new Vector<Projekt>();

		    try {
		      Statement stmt = con.createStatement();

		      ResultSet rs = stmt.executeQuery("SELECT id, name, startdatum, enddatum, beschreibung"
		      	  + "projektmarktplatz_id, organisationseinheit_id " + "FROM Projekt "
		          + "WHERE name LIKE '" + name + "' ORDER BY name");

		      // Für jeden Eintrag im Suchergebnis wird nun ein Customer-Objekt
		      // erstellt.
		      while (rs.next()) {
		        Projekt p = new Projekt();
		        p.setProjektId(rs.getInt("id"));
		        p.setName(rs.getString("name"));
		        p.setStartdatum(rs.getDate("startdatum"));
		        p.setEnddatum(rs.getDate("enddatum"));
		        p.setBeschreibung(rs.getString("beschreibung"));
		        p.setProjektmarktplatzId(rs.getInt("projektmarktplatz_id"));
		        p.setOrganisationseinheitId(rs.getInt("organisationseinheit_id"));



		        // Hinzufügen des neuen Objekts zum Ergebnisvektor
		        result.addElement(p);
		      }
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }

		    // Ergebnisvektor zurückgeben
		    return result;
		  }
	  
	  public Vector<Projekt> findByOrganisationseinheit(Organisationseinheit o) {
		    Connection con = DBConnection.connection();
		    // Ergebnisvektor vorbereiten
		    Vector<Projekt> result = new Vector<Projekt>();

		    try {
		      Statement stmt = con.createStatement();

		      ResultSet rs = stmt.executeQuery("SELECT * FROM Projekt WHERE organisationseinheit_id='" + o.getOrganisationseinheitId() + "' ORDER BY organisationseinheit_id");
		   

		      // Für jeden Eintrag im Suchergebnis wird nun ein Projekt-Objekt
		      // erstellt.
		      while (rs.next()) {
		        Projekt p = new Projekt();
		        p.setProjektId(rs.getInt("id"));
		        p.setName(rs.getString("name"));
		        p.setStartdatum(rs.getDate("startdatum"));
		        p.setEnddatum(rs.getDate("enddatum"));
		        p.setBeschreibung(rs.getString("beschreibung"));
		        p.setProjektmarktplatzId(rs.getInt("projektmarktplatz_id"));
		        p.setOrganisationseinheitId(rs.getInt("organisationseinheit_id"));

		        // Hinzufügen des neuen Objekts zum Ergebnisvektor
		        result.addElement(p);
		      }
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }

		    // Ergebnisvektor zurückgeben
		    return result;
	 }
	  
	  public void deleteProjektOfOrganisationseinheit(Organisationseinheit o) {
			Connection con = DBConnection.connection();
			
			try {
				Statement stmt = con.createStatement();
				stmt.executeUpdate("DELETE FROM Partnerprofil WHERE organisationseinheit_id= " + o.getOrganisationseinheitId());
				
			} catch (Exception e2) {
				 e2.printStackTrace();
			}
		}

}