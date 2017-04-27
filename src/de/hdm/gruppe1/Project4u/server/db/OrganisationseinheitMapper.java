package de.hdm.gruppe1.Project4u.server.db;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.gruppe1.Project4u.shared.bo.Nutzer;

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
	private static OrganisationseinheitMapper nutzerMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit
	 * <code>new</code> neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected OrganisationseinheitMapper(){
		
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
	public static OrganisationseinheitMapper nutzerMapper(){
		if(nutzerMapper == null){
			nutzerMapper = new OrganisationseinheitMapper();
		}
		return nutzerMapper;
	}

	/**
	 * Diese Methode bezieht ihre Informationen aus der
	 * PartnerboerseAdministrationImpl und erstellt mit diesen einen neuen
	 * Nutzer in der Datenbank.
	 * 
	 * @param nutzerprofil
	 * @return nutzerprofil
	 */
	public Nutzer insert(Nutzer nutzer){
		Connection con = DBConnection.connection();
		
		try{
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT MAX(NutzerID) AS maxid " + "FROM Organisationseinheit ");
			
			
			if(rs.next()){
				nutzer.setNutzerId(rs.getInt("maxid") +1);
				
				stmt.executeUpdate("HIER MUSS DAS INSERT-SQL-STATEMENT REIN!!!");
			}
		} catch (SQLException e2){
			e2.printStackTrace();
		}
		return nutzer;
	}
	/**
	 * In dieser Methode wird überprüft ob der Nutzer bereits in der Datenbank
	 * vorhanden ist.
	 *
	 * Die Überprüfung wird anhand der Emailadresse vorgenommen, welche in dem
	 * Nutzerobjekt loginInfo enthalten ist.
	 * 
	 * @param loginInfo
	 * @param nutzer
	 *            Nutzerdaten des Users werden hineingeladen
	 * @param con
	 *            Datenbankverbindung
	 * @param email
	 *            Email Adresse des Users, der sich einloggen will
	 * @return nutzerdaten werden zurueckgegeben.
	 */
	public Nutzer checkStatus(Nutzer loginInfo){
		Nutzer nutzer = loginInfo;
		
		Connection con = DBConnection.connection();
		String email = loginInfo.getEmailAddress();
		
		try{
			//Anlegen eines leeren SQL-Statements
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM Nutzer WHERE " + "GoogleMail = '" + email + "'");
		
		if(rs.next()){
			nutzer.setStatus(true);
			nutzer.setNutzerId(rs.getInt("NutzerID"));
			nutzer.setEmailAddress(rs.getString("GoogleMail"));
			
			ResultSet rs2 = stmt.executeQuery("SELECT * FROM Nutzer WHERE " + "NutzerID = " + rs.getInt("NutzerID"));
			if(rs2.next()){
				nutzer.setID(rs2.getInt("NutzerID"));
			}
		} else{
			nutzer.setStatus(false);
		}
	} catch(SQLException e){
		e.printStackTrace();
		return null;
	}
		return nutzer;
	}
	
}