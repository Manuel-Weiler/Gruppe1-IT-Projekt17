package de.hdm.gruppe1.Project4u.shared.report;

import java.util.Vector; //Wird sp�ter bei der Implementierung und Ausgabe der Reports ben�tigt.

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

	/*
	 * Report 1 Ausgabe aller Ausschreibungen
	 * 
	 * @author: Dominik Sasse
	 */
	public void process(ReportByAlleAusschreibungen a) {

		this.resetReportText();
		StringBuffer result = new StringBuffer();

		result.append("<H3>" + a.getTitel() + "</H3>");

		Vector<Row> rows = a.getRows();
		result.append("<table style=\"width:400px;margin-bottom: 30px\">");

		for (int i = 0; i < rows.size(); i++) {
			Row row = rows.elementAt(i);
			result.append("<tr>");
			for (int j = 0; j < row.getNumColumns(); j++) {
				if (i == 0) {
					result.append("<td style=\"background:silver;font-weight:bold\">" + row.getColumnAt(j) + "</td>");
				} else {
					if (i > 1) {
						result.append("<td style=\"border-top:1px solid silver;margin-bottom: 30px\">"
								+ row.getColumnAt(j) + "</td>");
					} else {
						result.append("<td valign=\"top\">" + row.getColumnAt(j) + "</td>");
					}
				}
			}
			result.append("</tr>");
		}
		result.append("</table>");
		this.reportText = result.toString();
	}

	/*
	 * Report 2 Ausgabe aller Ausschreibungen passend zum Nutzer
	 * 
	 * @author: Dominik Sasse
	 */
	public void process(ReportByAusschreibungenForPartnerprofil b) {

		this.resetReportText();
		StringBuffer result = new StringBuffer();

		result.append("<H3>" + b.getTitel() + "</H3>");

		Vector<Row> rows = b.getRows();
		result.append("<table style=\"width:400px;margin-bottom: 30px\">");

		for (int i = 0; i < rows.size(); i++) {
			Row row = rows.elementAt(i);
			result.append("<tr>");
			for (int j = 0; j < row.getNumColumns(); j++) {
				if (i == 0) {
					result.append("<td style=\"background:silver;font-weight:bold\">" + row.getColumnAt(j) + "</td>");
				} else {
					if (i > 1) {
						result.append("<td style=\"border-top:1px solid silver;margin-bottom: 30px\">"
								+ row.getColumnAt(j) + "</td>");
					} else {
						result.append("<td valign=\"top\">" + row.getColumnAt(j) + "</td>");
					}
				}
			}
			result.append("</tr>");
		}
		result.append("</table>");
		this.reportText = result.toString();
	}

	/*
	 * Report 3 Ausgabe aller Ausschreibungen
	 * 
	 * @author: Dominik Sasse
	 */
	public void process(ReportByAlleBewerbungenForAusschreibungen b) {

		this.resetReportText();
		StringBuffer result = new StringBuffer();

		result.append("<H3>" + b.getTitel() + "</H3>");

		Vector<Row> rows = b.getRows();
		result.append("<table style=\"width:600px;margin-bottom: 30px\">");

		for (int i = 0; i < rows.size(); i++) {
			Row row = rows.elementAt(i);
			result.append("<tr>");
			for (int j = 0; j < row.getNumColumns(); j++) {
				if (i == 0) {
					result.append("<td style=\"border-top:1px solid silver;margin-bottom: 30px\">" + row.getColumnAt(j) + "</td>");
				} else {
					if (i > 1) {
						result.append("<td style=\"border-top:1px solid silver;margin-bottom: 30px\">"
								+ row.getColumnAt(j) + "</td>");
					} else {
						result.append("<td valign=\"top\">" + row.getColumnAt(j) + "</td>");
					}
				}
			}
			result.append("</tr>");
		}
		result.append("</table>");
		this.reportText = result.toString();
	}

	/*
	 * Report 4 Abfrage der eigenen Bewerbungen und den zugeh�rigen
	 * Ausschreibungen des Benutzers
	 * 
	 * @author: Dominik Sasse
	 */
	public void process(ReportForEigeneBewerbungen c) {

		this.resetReportText();
		StringBuffer result = new StringBuffer();

		result.append("<H3>" + c.getTitel() + "</H3>");

		Vector<Row> rows = c.getRows();
		result.append("<table style=\"width:400px;margin-bottom: 30px\">");

		for (int i = 0; i < rows.size(); i++) {
			Row row = rows.elementAt(i);
			result.append("<tr>");
			for (int j = 0; j < row.getNumColumns(); j++) {
				if (i == 0) {
					result.append("<td style=\"background:silver;font-weight:bold\">" + row.getColumnAt(j) + "</td>");
				} else {
					if (i > 1) {
						result.append("<td style=\"border-top:1px solid silver;margin-bottom: 30px\">"
								+ row.getColumnAt(j) + "</td>");
					} else {
						result.append("<td valign=\"top\">" + row.getColumnAt(j) + "</td>");
					}
				}
			}
			result.append("</tr>");
		}
		result.append("</table>");
		this.reportText = result.toString();
	}

	/*
	 * Report 5 Projektverflechtungen
	 * 
	 * @author: Dominik Sasse
	 */

	// Sub Report 1
	public void process(AllBewerbungenForNutzer c) {

		this.resetReportText();
		StringBuffer result = new StringBuffer();

		result.append("<H3>" + c.getTitel() + "</H3>");

		Vector<Row> rows = c.getRows();
		result.append("<table style=\"width:400px;margin-bottom: 30px\">");

		for (int i = 0; i < rows.size(); i++) {
			Row row = rows.elementAt(i);
			result.append("<tr>");
			for (int j = 0; j < row.getNumColumns(); j++) {
				if (i == 0) {
					result.append("<td style=\"background:silver;font-weight:bold\">" + row.getColumnAt(j) + "</td>");
				} else {
					if (i > 1) {
						result.append("<td style=\"border-top:1px solid silver;margin-bottom: 30px\">"
								+ row.getColumnAt(j) + "</td>");
					} else {
						result.append("<td valign=\"top\">" + row.getColumnAt(j) + "</td>");
					}
				}
			}
			result.append("</tr>");
		}
		result.append("</table>");
		this.reportText = result.toString();
	}

	// SubReport 2
	public void process(AllBeteiligungenForNutzer c) {

		this.resetReportText();
		StringBuffer result = new StringBuffer();

		result.append("<H3>" + c.getTitel() + "</H3>");

		Vector<Row> rows = c.getRows();
		result.append("<table style=\"width:400px;margin-bottom: 30px\">");

		for (int i = 0; i < rows.size(); i++) {
			Row row = rows.elementAt(i);
			result.append("<tr>");
			for (int j = 0; j < row.getNumColumns(); j++) {
				if (i == 0) {
					result.append("<td style=\"background:silver;font-weight:bold\">" + row.getColumnAt(j) + "</td>");
				} else {
					if (i > 1) {
						result.append("<td style=\"border-top:1px solid silver;margin-bottom: 30px\">"
								+ row.getColumnAt(j) + "</td>");
					} else {
						result.append("<td valign=\"top\">" + row.getColumnAt(j) + "</td>");
					}
				}
			}
			result.append("</tr>");
		}
		result.append("</table>");
		this.reportText = result.toString();
	}

	// Ausgabe der beiden Sub Reports

	public void process(ReportByProjektverflechtungen c) {

		this.resetReportText();
		StringBuffer result = new StringBuffer();

		result.append("<H2>" + c.getTitel() + "</H2>");
		result.append("<table><tr>");

		for (int i = 0; i < c.getNumSubReports(); i++) {

			this.processSimpleReport(c.getSubReportAt(i));
			result.append(this.reportText + "\n");

			this.resetReportText();
		}

		this.reportText = result.toString();
	}

	/*
	 * Report 6 FanIn FanOut Analyse
	 * 
	 * @author: Dominik Sasse
	 */

	// Sub Report 1
	public void process(FanIn c) {

		this.resetReportText();

		StringBuffer result = new StringBuffer();

		result.append("<H3>" + c.getTitel() + "</H3>");

		Vector<Row> rows = c.getRows();
		result.append("<table style=\"width:400px;margin-bottom: 30px\">");

		for (int i = 0; i < rows.size(); i++) {
			Row row = rows.elementAt(i);
			result.append("<tr>");
			for (int j = 0; j < row.getNumColumns(); j++) {
				if (i == 0) {
					result.append("<td style=\"background:silver;font-weight:bold\">" + row.getColumnAt(j) + "</td>");
				} else {
					if (i > 1) {
						result.append("<td style=\"border-top:1px solid silver;margin-bottom: 30px\">"
								+ row.getColumnAt(j) + "</td>");
					} else {
						result.append("<td valign=\"top\">" + row.getColumnAt(j) + "</td>");
					}
				}
			}
			result.append("</tr>");
		}
		result.append("</table>");
		this.reportText = result.toString();
	}

	// SubReport 2
	public void process(FanOut c) {

		this.resetReportText();
		StringBuffer result = new StringBuffer();

		result.append("<H3>" + c.getTitel() + "</H3>");

		Vector<Row> rows = c.getRows();
		result.append("<table style=\"width:400px;margin-bottom: 30px\">");

		for (int i = 0; i < rows.size(); i++) {
			Row row = rows.elementAt(i);
			result.append("<tr>");
			for (int j = 0; j < row.getNumColumns(); j++) {
				if (i == 0) {
					result.append("<td style=\"background:silver;font-weight:bold\">" + row.getColumnAt(j) + "</td>");
				} else {
					if (i > 1) {
						result.append("<td style=\"border-top:1px solid silver;margin-bottom: 30px\">"
								+ row.getColumnAt(j) + "</td>");
					} else {
						result.append("<td valign=\"top\">" + row.getColumnAt(j) + "</td>");
					}
				}
			}
			result.append("</tr>");
		}
		result.append("</table>");
		this.reportText = result.toString();
	}

	// Ausgabe der beiden Sub Reports

	public void process(FanInFanOut c) {

		this.resetReportText();

		StringBuffer result = new StringBuffer();

		result.append("<H2>" + c.getTitel() + "</H2>");
		result.append("<table><tr>");

		for (int i = 0; i < c.getNumSubReports(); i++) {

			this.processSimpleReport(c.getSubReportAt(i));
			result.append(this.reportText + "\n");

			this.resetReportText();
		}

		this.reportText = result.toString();
	}

	// Methode f�r die tabellarische Darstellung der Sub-Reports.

	public void processSimpleReport(Report report) {

		SimpleReport r = (SimpleReport) report;

		this.resetReportText();

		StringBuffer result = new StringBuffer();

		result.append("<H3>" + r.getTitel() + "</H3>");

		Vector<Row> rows = r.getRows();
		result.append("<table style=\"width:400px;margin-bottom: 30px\">");

		for (int i = 0; i < rows.size(); i++) {
			Row row = rows.elementAt(i);
			result.append("<tr>");
			for (int k = 0; k < row.getNumColumns(); k++) {
				if (i == 0) {
					result.append("<td style=\"background:silver;font-weight:bold\">" + row.getColumnAt(k) + "</td>");
				} else {
					if (i > 1) {
						result.append("<td style=\"border-top:1px solid silver;margin-bottom: 30px\">"
								+ row.getColumnAt(k) + "</td>");
					} else {
						result.append("<td valign=\"top\">" + row.getColumnAt(k) + "</td>");
					}
				}
			}
			result.append("</tr>");
		}

		result.append("</table>");

		this.reportText = result.toString();
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
