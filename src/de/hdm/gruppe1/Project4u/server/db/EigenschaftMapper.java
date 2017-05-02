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
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM Eigenschaft ");

			if (rs.next()) {
				/*
				 * Der bisher gr��te Prim�rschl�ssel wird um 1 erh�ht und dem
				 * Eigenschaft-Objekt zugewiesen.
				 */
				e.setEigenschaftId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				// Jetzt erst erfolgt die tatsächliche Einfügeoperation
				stmt.executeUpdate("INSERT INTO Eigenschaft (id, name, wert, partnerprofil_id) " + "VALUES ("
						+ e.getEigenschaftId() + ",'" + e.getName() + "','" + e.getWert() + "','"
						+ p.getPartnerprofilId() + "')");
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

	
}
