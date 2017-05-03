package de.hdm.gruppe1.Project4u.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import de.hdm.gruppe1.Project4u.shared.bo.Projekt;

/**
 * Mapper-Klasse, die <code>Projekt</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur VerfÃ¼gung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gelÃ¶scht werden kÃ¶nnen. Das Mapping ist bidirektional. D.h., Objekte kÃ¶nnen
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * 
 * 
 * @author Thies
 * @author Ugur
 */
public class ProjektMapper {

	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * Die Klasse ProjektmarktplatzMapper wird nur einmal instantiiert. Man spricht
	 * hierbei von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * fuer saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 */
	private static ProjektMapper projektMapper = null;
	
	/**
	 * GeschÃ¼tzter Konstruktor - verhindert die MÃ¶glichkeit, mit
	 * <code>new</code> neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected ProjektMapper(){
		
	}
	
	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>ProjektMapper.projektMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafÃ¼r sorgt, dass nur eine
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
	 ** @return Liefert ein Projekt entsprechend der übergebenen id zurueck.
	 **/

      public Projekt findById(int id){
		 // DB-Verbindung holen
		 Connection con = DBConnection.connection();
		 
		 try {
		   // Leeres SQL-Statement (JDBC) anlegen
		   Statement stmt = con.createStatement();
		   
		   // Statement ausfüllen und als Query an die DB schicken
		   ResultSet rs = stmt.executeQuery("SELECT * FROM projekt " + "WHERE ProjektId=" + id);
		   
		   /*
	        * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
	        * werden. Prüfe, ob ein Ergebnis vorliegt.
	        */
		    if (rs.next()) {
		      // Ergebnis-Tupel in Objekt umwandeln
		      Projekt p = new Projekt();
		      p.setProjektId(rs.getInt("projektId"));
		      p.setName(rs.getString("name"));
		      p.setStartdatum(rs.getDate("startdatum"));
		      p.setEnddatum(rs.getDate("enddatum"));
		      p.setBeschreibung(rs.getString("beschreibung"));
		      p.setProjektmarktplatzId(rs.getInt("projektmarktplatzId"));
		      p.setOrganisationseinheitId(rs.getInt("organisationseinheitId"));
		      
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
		 * PartnerboerseAdministrationImpl und erstellt mit diesen einen neuen
		 * Projekt in der Datenbank.
		 * 
		 * @param projekt
		 * @return projekt
		 */
	  
	  public Projekt insert(Projekt p){
		  Connection con = DBConnection.connection();
		  
		  try{
			  Statement stmt = con.createStatement();
			  
			  ResultSet rs = stmt.executeQuery("SELECT MAX(projektId) AS maxid " + "FROM projekt ");
			  
			  if (rs.next()) {
	              p.setProjektId(rs.getInt("maxid") + 1);
	            }
			  
			  stmt.executeUpdate("INSERT INTO projekt (projektId ,name, startdatum, enddatum, beschreibung," +
				  		"projektmarktplatzId, organisationseinheitId) VALUES (" + p.getProjektId() + ", '" + sdf.format(p.getStartdatum()) + "', '"
				  		+ sdf.format(p.getEnddatum()) + "', '" + p.getName() + "', '" + p.getBeschreibung() + "', " + 
				  		p.getOrganisationseinheitId() + ", " + p.getProjektmarktplatzId() + ")");
			                    
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
	   * @return das als Parameter Ã¼bergebene Objekt
	   */
	  public Projekt update(Projekt p) {
	    Connection con = DBConnection.connection();

	    try {
	      Statement stmt = con.createStatement();

	      stmt.executeUpdate("UPDATE projekt SET startdatum='" + sdf.format(p.getStartdatum()) + "', "
			  		+ "enddatum='" + sdf.format(p.getEnddatum()) + "', " + "name='" + p.getName() + "', "
					+ "beschreibung='" + p.getBeschreibung() + "', " + "organisationseinheitId=" + p.getOrganisationseinheitId()
					+ ", " + "projektmarktplatzId=" + p.getProjektmarktplatzId() + " WHERE projektId=" + p.getProjektId());

	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }

	    // Um Analogie zu insert(Projekt p) zu wahren, geben wir p zurÃ¼ck
	    return p;  
	  }
	  
	  /**
	   * LÃ¶schen der Daten eines <code>Projekt</code>-Objekts aus der Datenbank.
	   * 
	   * @param p das aus der DB zu lÃ¶schende "Objekt"
	   */
	  public void delete(Projekt p) {
	    Connection con = DBConnection.connection();

	    try {
	      Statement stmt = con.createStatement();

	      stmt.executeUpdate("DELETE FROM projekt WHERE projektId=" + p.getProjektId());

	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }
	  }
	
	  }