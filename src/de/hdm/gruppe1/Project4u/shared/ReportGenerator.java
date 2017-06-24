package de.hdm.gruppe1.Project4u.shared;


import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAusschreibungenForPartnerprofil;
import de.hdm.gruppe1.Project4u.shared.report.ReportByProjektverflechtungen;
import de.hdm.gruppe1.Project4u.shared.report.ReportForEigeneBewerbungen;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.report.AllBeteiligungenForNutzer;
import de.hdm.gruppe1.Project4u.shared.report.AllBewerbungenForNutzer;
import de.hdm.gruppe1.Project4u.shared.report.FanIn;
import de.hdm.gruppe1.Project4u.shared.report.FanInFanOut;
import de.hdm.gruppe1.Project4u.shared.report.FanOut;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAlleAusschreibungen;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAlleBewerbungenForAusschreibungen;

@RemoteServiceRelativePath("report")
public interface ReportGenerator extends RemoteService{
	
	public void init() throws IllegalArgumentException;
	
	ReportByAlleAusschreibungen createAlleAusschreibungenReport() throws IllegalArgumentException;

	ReportByAusschreibungenForPartnerprofil createAusschreibungenForPartnerprofil(Organisationseinheit orga) throws IllegalArgumentException;
	
	FanIn createFanInAnalyseReport() throws IllegalArgumentException;
	
	FanOut createFanOutAnalyseReport() throws IllegalArgumentException; 
	
	FanInFanOut createFanInFanOutReport() throws IllegalArgumentException;

	ReportByProjektverflechtungen createProjektverflechtungReport(Organisationseinheit orga) throws IllegalArgumentException;

	ReportByAlleBewerbungenForAusschreibungen createAlleBewerbungenForAusschreibungen(Organisationseinheit o) throws IllegalArgumentException;
	
	ReportForEigeneBewerbungen createEigeneBewerbungenReport(Organisationseinheit orga) throws IllegalArgumentException;
	
	AllBewerbungenForNutzer allBewerbungenForNutzer(Organisationseinheit orga) throws IllegalArgumentException;
	
	AllBeteiligungenForNutzer allBeteiligungenForNutzer(Organisationseinheit orga) throws IllegalArgumentException;
	
}
