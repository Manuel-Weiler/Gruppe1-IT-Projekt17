package de.hdm.gruppe1.Project4u.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.gruppe1.Project4u.shared.bo.Eigenschaft;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;


public class EigenschaftMapper {

	/**
	 * Die statische Variable eigenschaftMapper stellt sicher, dass es von der
	 * Klasse EigenschaftMapper nur eine einzige Instanz gibt bzw. die
	 * Variable speichert die einzige Instanz dieser Klasse.
	 * @author Tobias
	 */
	private static EigenschaftMapper eigenschaftMapper = null;
	
	/*
	 * Der private Konstruktor verhindert, dass eine Instanz der Klasse
	 * EigenschaftMapper �ber <code>new</code> erzeugt werden kann.
	 */
	private EigenschaftMapper() {
	}
	
	
	/**
	 * Die Methode erzeug eine Instanz der Klasse EigenschaftMapper f�r den Fall, dass es bisher
	 * keine Instanz von EigenschaftMapper gibt
	 * @return EingenschaftMapper
	 * @author Tobias
	 */
	public static EigenschaftMapper eigenschaftMapper(){
		if (eigenschaftMapper==null){
			eigenschaftMapper = new EigenschaftMapper();
		}
		return eigenschaftMapper;
	}
	
	/**
	 * Die Methode vergibt dem zu speichernden Eigenschafts-Objekts einen Prim�rschl�ssel und legt es in 
	 * der DB ab. Zudem aktualisiert sie das �nderungsdatum des zugeh�rigen Partnerprofil-Objekts
	 * @param e Eigenschaft
	 * @param p Partnerprofil
	 * @return Eigenschaft
	 * @author Tobias
	 */
	public Eigenschaft insertEigenschaft(Eigenschaft e, Partnerprofil p) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			// Abfrage der gr��ten bisher vergebnen <code>id</code>
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM eigenschaft ;");

			if (rs.next()) {
				/*
				 * Der bisher gr��te Prim�rschl�ssel wird um 1 erh�ht und dem
				 * Eigenschaft-Objekt zugewiesen.
				 */
				e.setEigenschaftId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				// Jetzt erst erfolgt die tatsächliche Einfügeoperation
				stmt.executeUpdate("INSERT INTO eigenschaft (id, name, wert, partnerprofil_id) " + "VALUES ("
						+ e.getEigenschaftId() + ",'" + e.getName() + "','" + e.getWert() + "','"
						+ p.getPartnerprofilId() + "');");
			}
		} catch (SQLException s) {
			s.printStackTrace();
		}

		/*
		 * Im Anschluss an das Speichern des Eigenschaft-Objekts wird das
		 * �nderungsdatum des verkn�pften Partnerprofils aktualisiert.
		 */
		PartnerprofilMapper.partnerprofilMapper().updatePartnerprofil(p);
		
		
		/*
		 * R�ckgabe, des evtl. korrigierten Partnerprofil-Objekts. P.S: Diese
		 * R�ckgabe ist nicht zwingend notwendig, da die Verweise auf das
		 * bisherige Objekt auch auf das ge�nderte Objekt verweisen w�rden.
		 */
		return e;
	}
	
	public Eigenschaft updateEigenschaft(Eigenschaft e) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("UPDATE eigenschaft SET name='" + e.getName() + "', wert='" + e.getWert() + "' WHERE id='"
					+ e.getEigenschaftId() + "'");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return e;
	}
	
	
	/**
	 * L�scht ein Eigenschaftsobjekt aus der Datenbank
	 * 
	 * @param e Eigenschaft
	 */
	public void deleteEigenschaft(Eigenschaft e) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM eigenschaft WHERE id='" + e.getEigenschaftId() + "'");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Die Methode l�scht alle Eigenschaften, die in einer Fremdschl�sselbeziehung
	 * zu einem Partnerprofil p stehen.
	 * @param p Partnerprofil
	 */
	public void deleteAllEigenschaftOfPartnerprofil(Partnerprofil p){
		Vector<Eigenschaft> eigenschaften = new Vector<Eigenschaft>();
		
		//Zuerst wird ein Vector mit allen Eigenschaften erzeugt, die mit dem Partnerprofil-Objekt in
		//Beziehung stehen.
		eigenschaften=findByPartnerprofil(p);
		
		//F�r jedes Element dieses Vectors wird nun die Methode deleteEigenschaft() aufgerufen.
		for(Eigenschaft e : eigenschaften){
			EigenschaftMapper.eigenschaftMapper().deleteEigenschaft(e);
		}
	}
	
	/**
	 * Die Methode gibt alle Eigenschaftsobjekte eines Partnernprofils wieder
	 * @param p Partnerprofil
	 * @return Vektor mit allen Eigenschaftsobjekten die mit einem Partneprofil verkn�pft sind.
	 */
	public Vector<Eigenschaft> findByPartnerprofil(Partnerprofil p){
		Connection con = DBConnection.connection();
		Vector<Eigenschaft> ergebnis = new Vector<Eigenschaft>();
		
		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM eigenschaft WHERE partnerprofil_id='"
					+ p.getPartnerprofilId() + "'");
			
			while (rs.next()){
				Eigenschaft eigenschaft = new Eigenschaft();
				eigenschaft.setEigenschaftId(rs.getInt("id"));
				eigenschaft.setName(rs.getString("name"));
				eigenschaft.setWert(rs.getString("wert"));
				eigenschaft.setPartnerprofilId(rs.getInt("partnerprofil_id"));
				
				ergebnis.addElement(eigenschaft);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ergebnis;
	}
	
	
	public void deleteEigenschaftOfPartnerprofil(Partnerprofil p) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM eigenschaft WHERE partnerprofil_id= " + p.getPartnerprofilId());
			
		} catch (Exception e2) {
			 e2.printStackTrace();
		}
	}
	
	

	
	
}
