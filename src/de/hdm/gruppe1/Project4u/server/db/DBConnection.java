package de.hdm.gruppe1.Project4u.server.db;

import java.sql.*;

import com.google.appengine.api.utils.SystemProperty;

import com.google.appengine.api.utils.SystemProperty;

public class DBConnection {
	private static Connection con = null;

	// Datenbankverbindung aufbauen
	public static Connection connection() {
		if (con == null) {
			try {

				 if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
	                    // Load the class that provides the new
	                    // "jdbc:google:mysql://" prefix.
	                    Class.forName("com.mysql.jdbc.GoogleDriver");
	                    con = DriverManager.getConnection("jdbc:google:mysql://project4u-165512:hdm-project4u/project4u?user=root");
	                    
	                    
	                } else {
	                    // Local MySQL instance to use during development.
	                    Class.forName("com.mysql.jdbc.Driver");
	                    con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project4u", "root", null);
	                }
				
				/*
				 // Local MySQL instance to use during development.
                 Class.forName("com.mysql.jdbc.Driver");
                 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project4u?user=root&password=");
				*/
			
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
