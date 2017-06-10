package de.hdm.gruppe1.Project4u.shared;


import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.gruppe1.Project4u.shared.report.ReportByAusschreibungenForPartnerprofil;
import de.hdm.gruppe1.Project4u.shared.bo.Eigenschaft;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAlleAusschreibungen;

@RemoteServiceRelativePath("report")
public interface ReportGenerator extends RemoteService{
	
	public void init() throws IllegalArgumentException;
	
	ReportByAlleAusschreibungen createAlleAusschreibungenReport() throws IllegalArgumentException;

	ReportByAusschreibungenForPartnerprofil createAusschreibungenForPartnerprofil(Organisationseinheit orga);
	
	public Vector<Eigenschaft> getEigenschaftenOfOrganisationseinheit(Organisationseinheit orga)throws IllegalArgumentException;
	
	public Partnerprofil getPartnerprofilOfOrganisationseinheit(Organisationseinheit orga)throws IllegalArgumentException;
	
	//public Vector<Eigenschaft> getEigenschaftOfPartnerprofil(Partnerprofil p)throws IllegalArgumentException;
	
	public Organisationseinheit getOrganisationseinheitByUser(LoginInfo login)throws IllegalArgumentException;
	
	String testMethode();
	
	
}
