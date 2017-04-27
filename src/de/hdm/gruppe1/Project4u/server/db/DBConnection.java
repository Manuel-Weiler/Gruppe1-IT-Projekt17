package de.hdm.gruppe1.Project4u.server.db;

import java.sql.*;
import java.sql.DriverManager;

import com.google.appengine.api.utils.SystemProperty;

public class DBConnection {
	private static Connection con = null;
<<<<<<< HEAD
	private static String googleUrl = "jdbc:google:mysql://173.194.86.227/project4u?user=MaxBotta&password=";
	private static String localUrl = "jdbc:mysql://127.0.0.1:3306/project4u?user=MaxBotta&password=1234";
=======
	private static String googleUrl = "'jdbc:mysql://173.194.86.227:3306/project4u', 'TobiasReumann', null"; // <-- da muss unsere Google-DatenbankURL rein!
	private static String localUrl = ""; // <-- da muss unsere lokale DatenbankURL rein!
>>>>>>> refs/heads/master
	
	//DAtenbankverbindung aufbauen
	public static Connection connection(){
		if (con == null){
			String url = null;
			try{
				if(SystemProperty.environment.value() == SystemProperty.Environment.Value.Production){
					Class.forName("com.mysql.jdbc.GoogleDriver");
					url = googleUrl;
				} else{
					//Falls noch nicht auf Google Datenbank dann soll er lokal nehmen
					Class.forName("com.mysql.jdbc.Driver");
					url = localUrl;
				}
				con = DriverManager.getConnection(url);
			} catch (Exception e){
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
