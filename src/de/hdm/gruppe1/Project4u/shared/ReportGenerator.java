package de.hdm.gruppe1.Project4u.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.gruppe1.Project4u.shared.report.ReportByAusschreibungenForPartnerprofil;
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAlleAusschreibungen;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAlleBewerbungenForAusschreibungen;

@RemoteServiceRelativePath("report")
public interface ReportGenerator extends RemoteService{
	
	public void init() throws IllegalArgumentException;
	
	ReportByAlleAusschreibungen createAlleAusschreibungenReport() throws IllegalArgumentException;

	ReportByAusschreibungenForPartnerprofil createAusschreibungenForPartnerprofil(Ausschreibung au);
	
	ReportByAlleBewerbungenForAusschreibungen createAlleBewerbungenForAusschreibungen(Organisationseinheit o);
	
    String testMethode();
	
	
}
