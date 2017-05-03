package de.hdm.gruppe1.Project4u.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.gruppe1.Project4u.shared.bo.Eigenschaft;
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
	 * EigenschaftMapper über <code>new</code> erzeugt werden kann.
	 */
	private EigenschaftMapper() {
	}
	
	
	/**
	 * Die Methode erzeug eine Instanz der Klasse EigenschaftMapper für den Fall, dass es bisher
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
	 * Die Methode vergibt dem zu speichernden Eigenschafts-Objekts einen Primärschlüssel und legt es in 
	 * der DB ab. Zudem aktualisiert sie das Änderungsdatum des zugehörigen Partnerprofil-Objekts
	 * @param e Eigenschaft
	 * @param p Partnerprofil
	 * @return Eigenschaft
	 * @author Tobias
	 */
	public Eigenschaft insertEigenschaft(Eigenschaft e, Partnerprofil p) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			// Abfrage der größten bisher vergebnen <code>id</code>
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM Eigenschaft ");

			if (rs.next()) {
				/*
				 * Der bisher größte Primärschlüssel wird um 1 erhöht und dem
				 * Eigenschaft-Objekt zugewiesen.
				 */
				e.setEigenschaftId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				// Jetzt erst erfolgt die tatsÃ¤chliche EinfÃ¼geoperation
				stmt.executeUpdate("INSERT INTO Eigenschaft (id, name, wert, partnerprofil_id) " + "VALUES ("
						+ e.getEigenschaftId() + ",'" + e.getName() + "','" + e.getWert() + "','"
						+ p.getPartnerprofilId() + "')");
			}
		} catch (SQLException s) {
			s.printStackTrace();
		}

		/*
		 * Im Anschluss an das Speichern des Eigenschaft-Objekts wird das
		 * Änderungsdatum des verknüpften Partnerprofils aktualisiert.
		 */
		PartnerprofilMapper.partnerprofilMapper().updatePartnerprofil(p);
		
		
		/*
		 * Rückgabe, des evtl. korrigierten Partnerprofil-Objekts. P.S: Diese
		 * Rückgabe ist nicht zwingend notwendig, da die Verweise auf das
		 * bisherige Objekt auch auf das geänderte Objekt verweisen würden.
		 */
		return e;
	}
	
	public Eigenschaft updateEigenschaft(Eigenschaft e) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("UPDATE Eigenschaft SET name='" + e.getName() + "' wert='" + e.getWert() + "' WHERE id='"
					+ e.getEigenschaftId() + "'");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return e;
	}
	
	
	/**
	 * Löscht ein Eigenschaftsobjekt aus der Datenbank
	 * 
	 * @param e Eigenschaft
	 */
	public void deleteEigenschaft(Eigenschaft e) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Eigenschaft WHERE id='" + e.getEigenschaftId() + "'");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Die Methode löscht alle Eigenschaften, die in einer Fremdschlüsselbeziehung
	 * zu einem Partnerprofil p stehen.
	 * @param p Partnerprofil
	 */
	public void deleteAllEigenschaftOfPartnerprofil(Partnerprofil p){
		Vector<Eigenschaft> eigenschaften = new Vector<Eigenschaft>();
		
		//Zuerst wird ein Vector mit allen Eigenschaften erzeugt, die mit dem Partnerprofil-Objekt in
		//Beziehung stehen.
		eigenschaften=selectAllEigenschaftOfPartnerprofil(p);
		
		//Für jedes Element dieses Vectors wird nun die Methode deleteEigenschaft() aufgerufen.
		for(Eigenschaft e : eigenschaften){
			EigenschaftMapper.eigenschaftMapper().deleteEigenschaft(e);
		}
	}
	
	/**
	 * Die Methode gibt alle Eigenschaftsobjekte eines Partnernprofils wieder
	 * @param p Partnerprofil
	 * @return Vektor mit allen Eigenschaftsobjekten die mit einem Partneprofil verknüpft sind.
	 */
	public Vector<Eigenschaft> selectAllEigenschaftOfPartnerprofil(Partnerprofil p){
		Connection con = DBConnection.connection();
		Vector<Eigenschaft> ergebnis = new Vector<Eigenschaft>();
		
		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM Eigenschaft WHERE partnerprofil_id='"
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
	
	

	
	
}
