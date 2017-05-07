package de.hdm.gruppe1.Project4u.shared.report;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Spalte eines Row-Objekts. Column-Objekte implementieren das Serializable-Interface und 
 * koennen daher als Kopie z.B. vom Server an den Client uebertragen werden.
 * 
 * @see Row
 * @author Thies
 * ------------------------------------------------------------------------------------------
 * Diese Klasse wurde, wie von Herrn Prof. Dr. Thies in der Vorlesung gewuenscht, als Grundlage 
 * uebernommen und bei Notwendigkeit an die Beduerfnisse des IT-Projekts SS 2016 "Partnerboerse" 
 * angepasst. 
 */

public class Column implements IsSerializable, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Der Wert eines Spaltenobjekts entspricht dem Zelleneintrag einer Tabelle.
	 * In dieser Realisierung handelt es sich um einen einfachen textuellen Wert.
	 */
	private String value = "";
	
	/**
	 * No-Argument-Konstruktor.
	 * 
	 * @see #Column(String)
	 * @see SimpleParagraph#SimpleParagraph()
	 */
	public Column() {
	}
	
	/**
	 * Konstruktor, der die Angabe eines Werts (Spalteneintrag) erzwingt.
	 * 
	 * @param s der Wert, der durch das Column-Objekt dargestellt werden soll.
	 * @see #Column()
	 */
	public Column(String s) {
		this.value = s;
	}
	
	 /**
	   * Auslesen des Spaltenwerts.
	   * 
	   * @return der Eintrag als String
	   */
	public String getValue() {
		return value;
	}
	
	  /**
	   * Ueberschreiben des aktuellen Spaltenwerts.
	   * 
	   * @param value neuer Spaltenwert
	   */
	public void setValue(String value) {
	    this.value = value;
	}
	
	  /**
	   * Umwandeln des Column-Objekts in einen String.
	   * 
	   * @see java.lang.Object
	   */
	public String toString() {
	    return this.value;
	  }

}