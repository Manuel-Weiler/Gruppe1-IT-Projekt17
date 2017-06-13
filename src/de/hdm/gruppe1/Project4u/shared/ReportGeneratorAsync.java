package de.hdm.gruppe1.Project4u.shared;


import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAlleAusschreibungen;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAlleBewerbungenForAusschreibungen;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAusschreibungenForPartnerprofil;

public interface ReportGeneratorAsync {

	/**
	 * Initialisierung des Objekts.
	 * 
	 * @param callback
	 */
	public void init(AsyncCallback<Void> callback);

	void createAlleAusschreibungenReport(AsyncCallback<ReportByAlleAusschreibungen> callback);

	void createAusschreibungenForPartnerprofil(Ausschreibung au,
			AsyncCallback<ReportByAusschreibungenForPartnerprofil> callback);

	void createAlleBewerbungenForAusschreibungen(Organisationseinheit o,
			AsyncCallback<ReportByAlleBewerbungenForAusschreibungen> callback);

	void testMethode(AsyncCallback<String> callback);

}
