package de.hdm.gruppe1.Project4u.shared;


import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.gruppe1.Project4u.shared.report.ReportByAusschreibungenForPartnerprofil;
import de.hdm.gruppe1.Project4u.shared.bo.Eigenschaft;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;
import de.hdm.gruppe1.Project4u.shared.report.FanIn;
import de.hdm.gruppe1.Project4u.shared.report.FanInFanOut;
import de.hdm.gruppe1.Project4u.shared.report.FanOut;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAlleAusschreibungen;

@RemoteServiceRelativePath("report")
public interface ReportGenerator extends RemoteService{
	
	public void init() throws IllegalArgumentException;
	
	ReportByAlleAusschreibungen createAlleAusschreibungenReport() throws IllegalArgumentException;

	ReportByAusschreibungenForPartnerprofil createAusschreibungenForPartnerprofil(Organisationseinheit orga);
	
	FanIn createFanInAnalyseReport();
	
	FanOut createFanOutAnalyseReport();
	
	FanInFanOut createFanInFanOutReport();
	
	String testMethode();
	
	
}
