package de.hdm.gruppe1.Project4u.shared.report;

import java.util.Vector; //Wird später bei der Implementierung und Ausgabe der Reports benötigt.

import de.hdm.gruppe1.Project4u.shared.report.CompositeParagraph;
import de.hdm.gruppe1.Project4u.shared.report.Paragraph;
import de.hdm.gruppe1.Project4u.shared.report.SimpleParagraph;

/**
 * Ein ReportWriter, der Reports mittels HTML formatiert. Das imZielformat
 * vorliegende Ergebnis wird in der Variable reportText abgelegt und kann nach
 * Aufruf der entsprechenden Prozessierungsmethode mit getReportText()
 * ausgelesen werden.
 * 
 * @author Thies
 */

public class HTMLReportWriter {

	/**
	 * Diese Variable wird mit dem Ergebnis einer Umwandlung (vgl.
	 * process...-Methoden) belegt. Format: HTML-Text
	 */
	private String reportText = "";

	/**
	 * Zuruecksetzen der Variable reportText.
	 */
	public void resetReportText() {
		this.reportText = "";
	}

	/**
	 * Umwandeln eines Paragraph-Objekts in HTML.
	 * 
	 * @param p
	 *            der Paragraph
	 * @return HTML-Text
	 */
	public String paragraph2HTML(Paragraph p) {
		if (p instanceof CompositeParagraph) {
			return this.paragraph2HTML((CompositeParagraph) p);
		} else {
			return this.paragraph2HTML((SimpleParagraph) p);
		}
	}

	/**
	 * Umwandeln eines CompositeParagraph-Objekts in HTML.
	 * 
	 * @param p
	 *            der CompositeParagraph
	 * @return HTML-Text
	 */
	public String paragraph2HTML(CompositeParagraph p) {
		StringBuffer result = new StringBuffer();

		for (int i = 0; i < p.getNumParagraphs(); i++) {
			result.append("<p>" + p.getParagraphAt(i) + "</p>");
		}

		return result.toString();
	}

	/**
	 * Umwandeln eines SimpleParagraph-Objekts in HTML.
	 * 
	 * @param p
	 *            der SimpleParagraph
	 * @return HTML-Text
	 */
	public String paragraph2HTML(SimpleParagraph p) {
		return "<p>" + p.toString() + "</p>";
	}

	/**
	 * HTML-Header-Text produzieren.
	 * 
	 * @return HTML-Text
	 */
	public String getHeader() {
		StringBuffer result = new StringBuffer();

		result.append("<html><head><title></title></head><body>");
		return result.toString();
	}

	/**
	 * HTML-Trailer-Text produzieren.
	 * 
	 * @return HTML-Text
	 */
	public String getTrailer() {
		return "</body></html>";
	}

	/**
	 * Auslesen des Ergebnisses der zuletzt aufgerufenen Prozessierungsmethode.
	 * 
	 * @return ein String im HTML-Format
	 */
	public String getReportText() {
		return this.getHeader() + this.reportText + this.getTrailer();
	}

}
