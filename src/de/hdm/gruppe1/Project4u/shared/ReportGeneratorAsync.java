package de.hdm.gruppe1.Project4u.shared;


import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAlleAusschreibungen;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAusschreibungenForPartnerprofil;
import de.hdm.gruppe1.Project4u.shared.report.ReportByProjektverflechtungen;

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
	
	void createProjektverflechtungReport (AsyncCallback<ReportByProjektverflechtungen> callback);

	void testMethode(AsyncCallback<String> callback);

}
