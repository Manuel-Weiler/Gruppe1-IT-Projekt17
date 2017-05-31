package de.hdm.gruppe1.Project4u.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.gruppe1.Project4u.shared.report.ReportByAusschreibungenForPartnerprofil;
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAlleAusschreibungen;

@RemoteServiceRelativePath("report")
public interface ReportGenerator extends RemoteService{
	
	public void init() throws IllegalArgumentException;
	
	

	ReportByAusschreibungenForPartnerprofil createAusschreibungenForPartnerprofil(Ausschreibung au);
	
	public abstract ReportByAlleAusschreibungen createAlleAusschreibungenReport() throws IllegalArgumentException;
}
