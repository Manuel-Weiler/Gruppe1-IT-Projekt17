package de.hdm.gruppe1.Project4u.shared.report;

import java.io.Serializable;
import java.util.Vector;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Ein zusammengesetzter Report. Dieser Report kann aus einer Menge von
 * Teil-Reports (vgl. Attribut subReports) bestehen.
 * 
 * ----------------------------------------------------------------------------------------
 * Diese Klasse wurde, wie von Herrn Prof. Thies in der Vorlesung gewuenscht,
 * als Grundlage uebernommen und beiNotwendigkeit an die Beduerfnisse des
 * IT-Projekts SS 2016 "Partnerboerse" angepasst.
 */

public class CompositeReport extends Report implements IsSerializable, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Die Menge der Teil-Reports.
	 */
	private Vector<Report> subReports = new Vector<Report>();

	/**
	 * Hinzufuegen eines Teil-Reports.
	 * 
	 * @param r
	 *            der hinzuzufuegende Teil-Report.
	 */
	public void addSubReport(Report r) {
		this.subReports.addElement(r);
	}

	/**
	 * Entfernen eines Teil-Reports.
	 * 
	 * @param r
	 *            der zu entfernende Teil-Report.
	 */
	public void removeSubReport(Report r) {
		this.subReports.removeElement(r);
	}

	/**
	 * Auslesen der Anzahl von Teil-Reports.
	 * 
	 * @return int Anzahl der Teil-Reports.
	 */
	public int getNumSubReports() {
		return this.subReports.size();
	}

	/**
	 * Auslesen eines einzelnen Teil-Reports.
	 * 
	 * @param i
	 *            Position des Teilreports. Bei n Elementen laeuft der Index i
	 *            von 0 bis n-1.
	 * 
	 * @return Position des Teil-Reports.
	 */
	public Report getSubReportAt(int i) {
		return this.subReports.elementAt(i);
	}

}