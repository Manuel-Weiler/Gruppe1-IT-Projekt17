package de.hdm.gruppe1.Project4u.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Bewerbung;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAllAusschreibungen;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAllBewerbungenForAusschreibung;

public interface ReportGeneratorAsync {
	
	/**
	 * Initialisierung des Objekts.
	 * 
	 * @param callback
	 */
	public void init(AsyncCallback<Void> callback);

	void createAllAusschreibungenReport(Ausschreibung au, AsyncCallback<ReportByAllAusschreibungen> callback);

	void createAllBewerbungenForAllAusschreibungReport(Bewerbung be,
			AsyncCallback<ReportByAllBewerbungenForAusschreibung> callback);

}
