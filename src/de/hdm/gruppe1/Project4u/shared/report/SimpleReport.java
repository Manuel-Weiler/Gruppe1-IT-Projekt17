package de.hdm.gruppe1.Project4u.shared.report;

import java.util.Vector;

/**
 * Ein einfacher Report, der neben den Informationen der Superklasse Report eine
 * Tabelle mit "Positionsdaten" aufweist. Die Tabelle greift auf zwei
 * Hilfsklassen namens Row und Column zurueck. Die Positionsdaten sind
 * vergleichbar mit der Liste der Bestellpositionen eines Bestellscheins. Dort
 * werden in eine Tabelle zeilenweise Eintragung z.B. bzgl. Artikelnummer,
 * Artikelbezeichnung, Menge, Preis vorgenommen.
 * 
 * @see Row
 * @see Column
 * @author Thies
 */

public abstract class SimpleReport extends Report {

	private static final long serialVersionUID = 1L;

	/**
	 * Tabelle mit Positionsdaten. Die Tabelle wird zeilenweise in diesem Vector
	 * abgelegt.
	 */
	private Vector<Row> table = new Vector<Row>();

	/**
	 * Hinzufuegen einer Zeile.
	 * 
	 * @param r
	 *            die hinzuzufuegende Zeile
	 */
	public void addRow(Row r) {
		this.table.addElement(r);
	}

	/**
	 * Entfernen einer Zeile.
	 * 
	 * @param r
	 *            die zu entfernende Zeile.
	 */
	public void removeRow(Row r) {
		this.table.removeElement(r);
	}

	/**
	 * Auslesen saemtlicher Positionsdaten.
	 * 
	 * @return die Tabelle der Positionsdaten
	 */
	public Vector<Row> getRows() {
		return this.table;
	}
}
