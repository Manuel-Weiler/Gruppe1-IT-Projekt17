package de.hdm.gruppe1.Project4u.shared;


import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.gruppe1.Project4u.shared.bo.Eigenschaft;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAlleAusschreibungen;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAusschreibungenForPartnerprofil;

public interface ReportGeneratorAsync {

	/**
	 * Initialisierung des Objekts.
	 * 
	 * @param callback
	 */
	public void init(AsyncCallback<Void> callback);

	void createAlleAusschreibungenReport(AsyncCallback<ReportByAlleAusschreibungen> callback);

	void createAusschreibungenForPartnerprofil(Organisationseinheit orga, AsyncCallback<ReportByAusschreibungenForPartnerprofil> callback);

	void testMethode(AsyncCallback<String> callback);

	void getEigenschaftenOfOrganisationseinheit(Organisationseinheit orga, AsyncCallback<Vector<Eigenschaft>> callback);

	void getPartnerprofilOfOrganisationseinheit(Organisationseinheit orga, AsyncCallback<Partnerprofil> callback);

//	void getEigenschaftOfPartnerprofil(Partnerprofil p, AsyncCallback<Vector<Eigenschaft>> callback);

	void getOrganisationseinheitByUser(LoginInfo login, AsyncCallback<Organisationseinheit> callback);

	

}
