package de.hdm.gruppe1.Project4u.server.db;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.gruppe1.Project4u.shared.bo.Bewerbung;

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
	
	protected BewerbungMapper(){
		
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
	public static BewerbungMapper bewerbungMapper(){
		if(bewerbungMapper == null){
			bewerbungMapper = new BewerbungMapper();
		}
		return bewerbungMapper;
	}
	
	/**
	 * Diese Methode bezieht ihre Informationen aus der
	 * PartnerboerseAdministrationImpl und erstellt mit diesen einen neuen
	 * Nutzer in der Datenbank.
	 * 
	 * @param bewerbung
	 * @return bewerbung
	 */
	public Bewerbung insert(Bewerbung bewerbung){
		Connection con = DBConnection.connection();
		
		try{
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT MAX(BewerbungID) AS maxid " + "FROM Bewerbung ");
			
			if(rs.next()){
				bewerbung.setBewerbungID(rs.getInt("maxid")+1);
				
				stmt.executeUpdate("INSERT INTO Bewerbung (BewerbungID, Erstelldatum, Bewerbungstext)"
						+ "VALUES (" + bewerbung.getBewerbungID() + "," + bewerbung.getErstelldatum() + "," 
						+ bewerbung.getBewerbungstext() + ")");
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return bewerbung;
	}
	

}



















