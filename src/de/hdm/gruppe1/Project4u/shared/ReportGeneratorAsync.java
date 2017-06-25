package de.hdm.gruppe1.Project4u.shared;


import java.util.Vector;


import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Eigenschaft;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;
import de.hdm.gruppe1.Project4u.shared.report.AllBeteiligungenForNutzer;
import de.hdm.gruppe1.Project4u.shared.report.AllBewerbungenForNutzer;
import de.hdm.gruppe1.Project4u.shared.report.FanIn;
import de.hdm.gruppe1.Project4u.shared.report.FanInFanOut;
import de.hdm.gruppe1.Project4u.shared.report.FanOut;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAlleAusschreibungen;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAlleBewerbungenForAusschreibungen;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAusschreibungenForPartnerprofil;
import de.hdm.gruppe1.Project4u.shared.report.ReportByProjektverflechtungen;
import de.hdm.gruppe1.Project4u.shared.report.ReportForEigeneBewerbungen;

public interface ReportGeneratorAsync {

	/**
	 * Initialisierung des Objekts.
	 * 
	 * @param callback
	 */
	public void init(AsyncCallback<Void> callback);

	void createAlleAusschreibungenReport(AsyncCallback<ReportByAlleAusschreibungen> callback);

	void createAusschreibungenForPartnerprofil(Organisationseinheit orga,
			AsyncCallback<ReportByAusschreibungenForPartnerprofil> callback);
	
	void createProjektverflechtungReport(Organisationseinheit orga,
			AsyncCallback<ReportByProjektverflechtungen> callback);

	void createFanInAnalyseReport(AsyncCallback<FanIn> callback);

	void createFanOutAnalyseReport(AsyncCallback<FanOut> callback);

	void createFanInFanOutReport(AsyncCallback<FanInFanOut> callback);

	void createAlleBewerbungenForAusschreibungen(Organisationseinheit o, AsyncCallback<ReportByAlleBewerbungenForAusschreibungen> callback);

	void createEigeneBewerbungenReport(Organisationseinheit orga, AsyncCallback<ReportForEigeneBewerbungen> callback);

	void allBewerbungenForNutzer(Organisationseinheit orga, AsyncCallback<AllBewerbungenForNutzer> callback);

	void allBeteiligungenForNutzer(Organisationseinheit orga, AsyncCallback<AllBeteiligungenForNutzer> callback);

}
