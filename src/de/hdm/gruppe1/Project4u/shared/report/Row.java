package de.hdm.gruppe1.Project4u.shared.report;

import java.io.Serializable;
import java.util.Vector;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Zeile einer Tabelle eines SimpleReport-Objekts. Row -Objekte implementieren
 * das Serializable-Interface und koennen daher als Kopie z.B. vom Server an den
 * Client uebertragen werden.
 * 
 * @see SimpleReport
 * @see Column
 * @author Thies
 */

public class Row implements IsSerializable, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Speicherplatz fuer die Spalten der Zeile.
	 */
	private Vector<Column> columns = new Vector<Column>();

	/**
	 * Hinzufuegen einer Spalte.
	 * 
	 * @param c
	 *            das Spaltenobjekt
	 */
	public void addColumn(Column c) {
		this.columns.addElement(c);
	}

	/**
	 * Entfernen einer benannten Spalte
	 * 
	 * @param c
	 *            das zu entfernende Spaltenobjekt
	 */
	public void removeColumn(Column c) {
		this.columns.removeElement(c);
	}

	/**
	 * Auslesen saemtlicher Spalten.
	 * 
	 * @return Vector-Objekts mit saetlichen Spalten
	 */
	public Vector<Column> getColumns() {
		return this.columns;
	}

	/**
	 * Auslesen der Anzahl saemmtlicher Spalten.
	 * 
	 * @return int Anzahl der Spalten
	 */
	public int getNumColumns() {
		return this.columns.size();
	}

	/**
	 * Auslesen eines einzelnen Spalten-Objekts.
	 * 
	 * @param i
	 *            der Index der auszulesenden Spalte (0 <= i < n), mit n =
	 *            Anzahl der Spalten.
	 * @return das gewuenschte Spaltenobjekt.
	 */
	public Column getColumnAt(int i) {
		return this.columns.elementAt(i);
	}
}