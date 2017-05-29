package de.hdm.gruppe1.Project4u.shared;

import com.google.gwt.user.client.rpc.RemoteService;

import de.hdm.gruppe1.Project4u.shared.report.ReportByAusschreibungenForPartnerprofil;
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAllAusschreibungen;

public interface ReportGenerator extends RemoteService{
	
	public void init() throws IllegalArgumentException;
	
	ReportByAllAusschreibungen createAllAusschreibungenReport();

	ReportByAusschreibungenForPartnerprofil createAusschreibungenForPartnerprofil(Ausschreibung au);

}
