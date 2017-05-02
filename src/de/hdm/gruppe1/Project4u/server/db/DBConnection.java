package de.hdm.gruppe1.Project4u.server.db;

import java.sql.*;
import java.sql.DriverManager;

public class DBConnection {
	private static Connection con = null;
	//private static String googleUrl = "'jdbc:mysql://173.194.86.227:3306/project4u', 'TobiasReumann', null"; // <-- da muss unsere Google-DatenbankURL rein!
	//private static String localUrl = ""; // <-- da muss unsere lokale DatenbankURL rein!
	
	//Datenbankverbindung aufbauen
	public static Connection connection(){
		if (con == null) {

			try {

				con = DriverManager.getConnection("jdbc:mysql://173.194.86.227:3306/project4u", "TobiasReumann", null);
			
			} catch (Exception e) {
				con = null;
				e.printStackTrace();
			}
		}
		return con;
	}
    /**
	 * Schließt das ResultSet, das Statement und die Connection.
	 * @param rs ResultSet
	 * @param stmt Statement
	 * @param con Datenbankverbindung
	 * @throws Exception
	 */
	public static void closeAll(ResultSet rs, Statement stmt, Connection con) throws IllegalArgumentException{
		
	}

}
