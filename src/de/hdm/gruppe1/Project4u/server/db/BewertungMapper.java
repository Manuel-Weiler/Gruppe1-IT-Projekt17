package de.hdm.gruppe1.Project4u.server.db;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.gruppe1.Project4u.shared.bo.Bewertung;
import de.hdm.gruppe1.Project4u.shared.bo.Nutzer;

/**
 * Mapper-Klasse, die <code>Bewertung</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfügung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gelöscht werden können. Das Mapping ist bidirektional. D.h., Objekte
 * können in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * 
 * 
 * @author Thies
 * @author Manuel
 */
public class BewertungMapper {
	/**
	 * Die Klasse BewertungMapper wird nur einmal instantiiert. Man spricht
	 * hierbei von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * fuer saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 */
	private static BewertungMapper bewertungMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit
	 * <code>new</code> neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected BewertungMapper() {

	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>BewertungMapper.bewertungMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine
	 * einzige Instanz von <code>BewertungMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> BewertungMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * 
	 * @return bewertungMapper <code>BewertungMapper</code>-Objekt.
	 */
	public static BewertungMapper bewertungMapper() {
		if (bewertungMapper == null) {
			bewertungMapper = new BewertungMapper();
		}
		return bewertungMapper;
	}

	/**
	 * Diese Methode bezieht ihre Informationen aus der
	 * PartnerboerseAdministrationImpl und erstellt mit diesen einen neuen
	 * Nutzer in der Datenbank.
	 * 
	 * @param bewertung
	 * @return bewertung
	 */

	public Bewertung insert(Bewertung bewertung) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT MAX(BewertungID) AS maxid " + "FROM Bewertung ");

			if (rs.next()) {
				bewertung.setBewertungID(rs.getInt("maxid") + 1);

				stmt.executeUpdate("INSERT INTO Bewertung (BewertungID, Bewertungspunkte, Stellungnahme)" + "VALUES ("
						+ bewertung.getBewertungID() + "," + bewertung.getBewertungspunkte() + ","
						+ bewertung.getStellungnahme() + ")");
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return bewertung;
	}
}
