package de.hdm.gruppe1.Project4u.shared.report;

import java.io.Serializable;
import java.util.Vector;

/**
 * Diese Klasse stellt eine Menge einzelner Absaetze (SimpleParagraph-Objekte) dar. 
 * Diese werden als Unterabschnitte in einem Vector abgelegt verwaltet.
 * 
 * @author Thies
 */

public class CompositeParagraph extends Paragraph implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * Speicherplatz f�r Unterabschnitte
	 */
	private Vector<SimpleParagraph> subParagraphs = new Vector<SimpleParagraph>();

	
	/**
	 * Unterabschnitt hinzuf�gen
	 * @param p
	 */
	public void addSubParagraph(SimpleParagraph p) {
		this.subParagraphs.addElement(p);
	}

	/**
	 * Unterabschnitt entfernen
	 * @param p
	 */
	public void removeSubParagraph(SimpleParagraph p) {
		this.subParagraphs.removeElement(p);
	}

	/**
	 * Auslesen aller Unterabschnnitte
	 * @return subParagraph
	 */
	public Vector<SimpleParagraph> getSubParagraphs() {
		return this.subParagraphs;
	}

	/**
	 * Auslesen der Anzahl der Unterabschnitte
	 * @return subParagraph
	 */
	public int getNumParagraphs() {
		return this.subParagraphs.size();
	}

	/**
	 * Auslesen der einzelenen Unterabschnitte
	 * @param i
	 * @return subParagraphs
	 */
	public SimpleParagraph getParagraphAt(int i) {
		return this.subParagraphs.elementAt(i);
	}

	/**
	 * Umwandlung des subParagraph in einen String
	 */
	public String toString() {
		/*
		 * Wir legen einen leeren Buffer an, in den wir sukzessive s�mtliche
		 * String-Repr�sentationen der Unterabschnitte eintragen.
		 */
		StringBuffer result = new StringBuffer();

		// Schleife �ber alle Unterabschnitte
		for (int i = 0; i < this.subParagraphs.size(); i++) {
			SimpleParagraph p = this.subParagraphs.elementAt(i);

			/*
			 * den jew. Unterabschnitt in einen String wandeln und an den Buffer
			 * h�ngen.
			 */
			result.append(p.toString() + "\n");
		}

		/*
		 * Schlie�lich wird der Buffer in einen String umgewandelt und
		 * zur�ckgegeben.
		 */
		return result.toString();
	}
}