package de.hdm.gruppe1.Project4u.server.db;

import java.sql.*;

import com.google.appengine.api.utils.SystemProperty;

public class DBConnection {
	private static Connection con = null;

	// Deployment URL http://1-dot-project4u-165512.appspot.com/

	private static String googleUrl = "jdbc:google:mysql://project4u-165512:hdm-project4u/Project4u?user=root&password=test";
	
	//lokale URL: http://127.0.0.1:8888/Project4u.html
	private static String localUrl = "jdbc:mysql://127.0.0.1:8888/Project4u?user=root";

	// Datenbankverbindung aufbauen
	public static Connection connection() {
		if (con == null) {
			String url = null;
			try {

				if(SystemProperty.environment.value() == SystemProperty.Environment.Value.Production){
					Class.forName("com.mysql.jdbc.GoogleDriver");
					url = googleUrl;
				} else{
					Class.forName("com.mysql.jdbc.Driver");
					url = localUrl;
				}
				con = DriverManager.getConnection(url);

			} catch (Exception e) {
				con = null;
				e.printStackTrace();
			}
		}
		return con;
	}

	/**
	 * Schließt das ResultSet, das Statement und die Connection.
	 * 
	 * @param rs
	 *            ResultSet
	 * @param stmt
	 *            Statement
	 * @param con
	 *            Datenbankverbindung
	 * @throws Exception
	 */
	public static void closeAll(ResultSet rs, Statement stmt, Connection con) throws IllegalArgumentException {

	}

}
