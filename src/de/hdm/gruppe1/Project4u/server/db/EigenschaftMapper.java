package de.hdm.gruppe1.Project4u.server.db;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.ibm.icu.text.SimpleDateFormat;

import de.hdm.gruppe1.Project4u.shared.bo.*;


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

	
}
