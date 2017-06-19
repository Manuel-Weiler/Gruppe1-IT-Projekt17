package de.hdm.gruppe1.Project4u.server.db;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.gruppe1.Project4u.shared.bo.Bewerbung;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;



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
	public OrganisationseinheitMapper(){
		
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
	
	public Organisationseinheit insert(Organisationseinheit organisationseinheit) {
		Connection con = DBConnection.connection();
		
		try{
			Statement stmt = con.createStatement();
			

			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM organisationseinheit;");

			
			
			if(rs.next()){
				organisationseinheit.setOrganisationseinheitId(rs.getInt("maxid") +1);
				
				stmt = con.createStatement();
				
				stmt.executeUpdate("INSERT INTO organisationseinheit (id, google_id, name, typ, partnerprofil_id) "
			            + "VALUES ('" + organisationseinheit.getOrganisationseinheitId() + "', '" + organisationseinheit.getGoogleId() + "','" + organisationseinheit.getName() + "', '" + organisationseinheit.getTyp() + "', '" + organisationseinheit.getPartnerprofilId() + "');");
			
			
			}
		} catch (SQLException e2){
			e2.printStackTrace();
		}
		return organisationseinheit;
	}
	
	public Organisationseinheit update(Organisationseinheit o) {
	    Connection con = DBConnection.connection();

	    try {
	      Statement stmt = con.createStatement();

	      stmt.executeUpdate("UPDATE Organisationseinheit SET google_id=\""
	          + o.getGoogleId() + "\", " + "name=\"" + o.getName() + "\", " + "typ=\"" + o.getTyp() + "\" "
	          + "WHERE id=" + o.getOrganisationseinheitId());

	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	    }

	    // Um Analogie zu insert(Customer c) zu wahren, geben wir c zurück
	    return o;
	  }


	 public void delete(Organisationseinheit o) {
		    Connection con = DBConnection.connection();

		    try {
		      Statement stmt = con.createStatement();
		      
		      //Organisationseinheit löschen
		      stmt.executeUpdate("DELETE FROM Organisationseinheit WHERE id=" + o.getOrganisationseinheitId());
		      	      
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }
		  }
	
	
	
	 public Organisationseinheit findByKey(int id) {
		    // DB-Verbindung holen
		    Connection con = DBConnection.connection();

		    try {
		      // Leeres SQL-Statement (JDBC) anlegen
		      Statement stmt = con.createStatement();

		      // Statement ausfüllen und als Query an die DB schicken

		      ResultSet rs = stmt.executeQuery("SELECT * FROM organisationseinheit WHERE id=" + id);


		      /*
		       * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
		       * werden. Prüfe, ob ein Ergebnis vorliegt.
		       */
		      if (rs.next()) {
		        // Ergebnis-Tupel in Objekt umwandeln
		        Organisationseinheit o = new Organisationseinheit();
		        o.setOrganisationseinheitId(rs.getInt("id"));
		        o.setName(rs.getString("name"));
		        o.setGoogleId(rs.getString("google_id"));
		        o.setTyp(rs.getString("typ"));
		        o.setPartnerprofilId(rs.getInt("partnerprofil_id"));

		        return o;
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

		      ResultSet rs = stmt.executeQuery("SELECT * FROM organisationseinheit " + "ORDER BY name");

		      // Für jeden Eintrag im Suchergebnis wird nun ein Organisationseinheit-Objekt
		      // erstellt.
		      while (rs.next()) {
		        Organisationseinheit o = new Organisationseinheit();
		        o.setOrganisationseinheitId(rs.getInt("id"));
		        o.setGoogleId(rs.getString("google_id"));
		        o.setName(rs.getString("name"));
		        o.setTyp(rs.getString("typ"));
		        o.setPartnerprofilId(rs.getInt("partnerprofil_id"));
		        //TO DO: 


		        // Hinzufügen des neuen Objekts zum Ergebnisvektor
		        result.addElement(o);
		      }
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }

		    // Ergebnisvektor zurückgeben
		    return result;
		  }
	 
	 public Vector<Organisationseinheit> findByName(String name) {
		    Connection con = DBConnection.connection();
		    Vector<Organisationseinheit> result = new Vector<Organisationseinheit>();

		    try {
		      Statement stmt = con.createStatement();

		      ResultSet rs = stmt.executeQuery("SELECT * "
		          + "FROM organisationseinheit " + "WHERE name LIKE '" + name
		          + "' ORDER BY name");

		      // Für jeden Eintrag im Suchergebnis wird nun ein Organisationseinheit-Objekt
		      // erstellt.
		      while (rs.next()) {
		        Organisationseinheit o = new Organisationseinheit();
		        o.setOrganisationseinheitId(rs.getInt("id"));
		        o.setGoogleId(rs.getString("google_id"));
		        o.setName(rs.getString("name"));
		        o.setTyp(rs.getString("typ"));
		        o.setPartnerprofilId(rs.getInt("partnerprofil_id"));
		        
		        // Hinzufügen des neuen Objekts zum Ergebnisvektor
		        result.addElement(o);
		      }
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }

		    // Ergebnisvektor zurückgeben
		    return result;
		  }
	 
	 public Vector<Organisationseinheit> findByTyp(String typ) {
		   Connection con = DBConnection.connection();
		    Vector<Organisationseinheit> result = new Vector<Organisationseinheit>();

		    try {
		      Statement stmt = con.createStatement();

		      ResultSet rs = stmt.executeQuery("SELECT * "
		          + "FROM organisationseinheit WHERE typ='" + typ
		          + "' ORDER BY name");

		      // Für jeden Eintrag im Suchergebnis wird nun ein Organisationseinheit-Objekt
		      // erstellt.
		      while (rs.next()) {
		    	   Organisationseinheit o = new Organisationseinheit();
			        o.setOrganisationseinheitId(rs.getInt("id"));
			        o.setGoogleId(rs.getString("google_id"));
			        o.setName(rs.getString("name"));
			        o.setTyp(rs.getString("typ"));
			        o.setPartnerprofilId(rs.getInt("partnerprofil_id"));

		        // Hinzufügen des neuen Objekts zum Ergebnisvektor
		        result.addElement(o);
		      }
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }

		    // Ergebnisvektor zurückgeben
		    return result;
		  }
	 
	 // GEORG

	 public Organisationseinheit getOrganisationseinheitByGoogleId (String googleId) throws Exception {
		 		 
		    Connection con = DBConnection.connection();

		    try {
		      Statement stmt = con.createStatement();

		      ResultSet rs = stmt
		    		  
		          .executeQuery("SELECT * FROM organisationseinheit "
		              + "WHERE google_id='" + googleId +"'");

		  
		      if (rs.next()) {
		        Organisationseinheit o = new Organisationseinheit();
		        o.setOrganisationseinheitId(rs.getInt("id"));
		        o.setName(rs.getString("name"));
		        o.setGoogleId(rs.getString("google_id"));
		        o.setTyp(rs.getString("typ"));
		        o.setPartnerprofilId(rs.getInt("partnerprofil_id"));

		        return o;
		      }
		    }
		    catch (SQLException e) {
			      e.printStackTrace();
			      return null;
			    }

			    return null;
			  }
	 
	 
	 
	public Vector<Organisationseinheit> getLinkedTeamAndUnternehmenOfOrganisationseinheit(Organisationseinheit orga) {
		Connection con = DBConnection.connection();
		Vector<Organisationseinheit> result = new Vector<Organisationseinheit>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT organisationseinheit_id "
					+ "FROM organisationseinheit_has_organisationseinheit WHERE person_id='"
					+ orga.getOrganisationseinheitId() + "';");

			// Für jeden Eintrag im Suchergebnis wird nun ein
			// Organisationseinheit-Objekt
			// erstellt.
			while (rs.next()) {
				Organisationseinheit o = new Organisationseinheit();
				o = findByKey(rs.getInt("organisationseinheit_id"));

				// Hinzufügen des neuen Objekts zum Ergebnisvektor
				result.addElement(o);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		    // Ergebnisvektor zurückgeben
		    return result;
		  }
	
	
	public void insertLinkedTeamUnternehmenOfOrganisationseinheit(Organisationseinheit person, Organisationseinheit teamunternehmen) {
		Connection con = DBConnection.connection();
		try{
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("INSERT INTO organisationseinheit_has_organisationseinheit (person_id, organisationseinheit_id) "
		            + "VALUES ('" + person.getOrganisationseinheitId() + "', '" + teamunternehmen.getOrganisationseinheitId()+"');");
					
		} catch (SQLException e2){
			e2.printStackTrace();
		}
	}
	
	public void deleteLinkedTeamUnternehmenOfOrganisationseinheit(Organisationseinheit person,
			Organisationseinheit team) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			// Organisationseinheit löschen
			stmt.executeUpdate(
					"DELETE FROM organisationseinheit_has_organisationseinheit WHERE organisationseinheit_id='"
							+ team.getOrganisationseinheitId() + "' AND person_id='"
							+ person.getOrganisationseinheitId() + "';");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Diese Methode löscht alle Verbindungen einer Organisationseinheit zu anderen Organisationseinheiten
	public void deleteVerbindungenOfOrganisationseinheit(Organisationseinheit organisationseinheit) {
		Connection con = DBConnection.connection();
		
		if (organisationseinheit.getTyp().equalsIgnoreCase("Person")) {
			try {
				Statement stmt = con.createStatement();

				//Person löschen
				stmt.executeUpdate(
						"DELETE FROM organisationseinheit_has_organisationseinheit WHERE person_id="
								+ organisationseinheit.getOrganisationseinheitId());

			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} else if(organisationseinheit.getTyp().equalsIgnoreCase("Unternehmen") || organisationseinheit.getTyp().equalsIgnoreCase("Team")) {
			try {
				Statement stmt = con.createStatement();

				//Organisationseinheit löschen
				stmt.executeUpdate(
						"DELETE FROM organisationseinheit_has_organisationseinheit WHERE organisationseinheit_id="
								+ organisationseinheit.getOrganisationseinheitId());

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		}
		//TODO prüfen
		//Diese Methode löscht alle Verbindungen einer Organisationseinheit zu anderen Organisationseinheiten
		public Vector<Organisationseinheit> getVerbindungenOfOrganisationseinheit(Organisationseinheit organisationseinheit) {
			Connection con = DBConnection.connection();
			Vector<Organisationseinheit> result = new Vector<Organisationseinheit>();
			
			if (organisationseinheit.getTyp().equalsIgnoreCase("Person")) {
				try {
					Statement stmt = con.createStatement();

					//Verbindungen einer Person finden
					ResultSet rs = stmt.executeQuery("SELECT * FROM organisationseinheit_has_organisationseinheit WHERE person_id="
							+ organisationseinheit.getOrganisationseinheitId());
					
					while (rs.next()) {
						Organisationseinheit o = new Organisationseinheit();
						o = findByKey(rs.getInt("person_id"));

						// Hinzufügen des neuen Objekts zum Ergebnisvektor
						result.addElement(o);
					}

				} catch (SQLException e) {
					e.printStackTrace();
				} 
				
			} else if(organisationseinheit.getTyp().equalsIgnoreCase("Unternehmen") || organisationseinheit.getTyp().equalsIgnoreCase("Team")) {
				try {
					Statement stmt = con.createStatement();

					//Verbindungen eines teams oder Unternehmens finden
					ResultSet rs = stmt.executeQuery("SELECT * FROM organisationseinheit_has_organisationseinheit WHERE organisationseinheit_id="
							+ organisationseinheit.getOrganisationseinheitId());
					
					while (rs.next()) {
						Organisationseinheit o = new Organisationseinheit();
						o = findByKey(rs.getInt("organisationseinheit_id"));

						// Hinzufügen des neuen Objekts zum Ergebnisvektor
						result.addElement(o);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			} return result;

		
	}
	
	
	
	 
}

