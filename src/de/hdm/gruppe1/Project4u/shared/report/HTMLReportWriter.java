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

public class HTMLReportWriter extends ReportWriter {

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
	 * Zunaechst wird ein Buffer angelegt, in den waehrend der Prozessierung die
	 * Ergebnisse geschtieben werden. Danach werden nach und nach alle
	 * Bestandteile des Reports ausgelesen und in HTML-Form uebersetzt. Am Ende
	 * wird der Buffer in einen String umgewandelt und der reportText-Variable
	 * zugewiesen. Dies ermoeglich,das Ergebnis durch getReportText()
	 * auszulesen.
	 */
	
	public void process(ReportByAlleAusschreibungen a){
		
		this.resetReportText();
		StringBuffer result = new StringBuffer();

		result.append("<H3>" + a.getTitle() + "</H3>");
		
		Vector<Row> rows = a.getRows();
		result.append("<table style=\"width:400px;margin-bottom: 30px\">");
		
		for(int i = 0; i< rows.size(); i++){
			Row row = rows.elementAt(i);
			result.append("<tr>");
			for(int j = 0; j < row.getNumColumns(); j++){
				if(i==0){
					result.append("<td style=\"background:silver;font-weight:bold\">" + row.getColumnAt(j) + "</td>");
				}
				else {
					if(i>1){
						result.append("<td style=\"border-top:1px solid silver;margin-bottom: 30px\">" + row.getColumnAt(j) + "</td>");
					}
					else {
						result.append("<td valign=\"top\">" + row.getColumnAt(j) +  "</td>");
					}
				}
			}
			result.append("</tr>");
		}
		result.append("</table>");
		this.reportText = result.toString();
	}
		
		
		
		
//	Ursprünglicher Ansatz:
		
//	    /*
//	     * Nun werden Schritt fÃ¼r Schritt die einzelnen Bestandteile des Reports
//	     * ausgelesen und in HTML-Form Ã¼bersetzt.
//	     */
//	    result.append("<H1>" + a.getTitle() + "</H1>");
//	    result.append("<table><tr>");
//
//	    if (a.getHeaderData() != null) {
//	      result.append("<td>" + paragraph2HTML(a.getHeaderData()) + "</td>");
//	    }
//
//	    result.append("<td>" + paragraph2HTML(a.getImprint()) + "</td>");
//	    result.append("</tr><tr><td></td><td>" + a.getCreated().toString()
//	        + "</td></tr></table>");
//
//
//		this.reportText = result.toString();
//
//	}
	
	/**
	 * Auslesen des Ergebnisses der zuletzt aufgerufenen Prozessierungsmethode.
	 * 
	 * @return ein String im HTML-Format
	 */
	public String getReportText() {
		return this.getHeader() + this.reportText + this.getTrailer();
	}
}
