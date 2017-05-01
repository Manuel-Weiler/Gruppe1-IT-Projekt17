package de.hdm.gruppe1.Project4u.server.db;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
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
	
	
	
}
