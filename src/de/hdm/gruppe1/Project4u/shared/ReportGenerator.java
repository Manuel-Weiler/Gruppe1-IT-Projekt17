package de.hdm.gruppe1.Project4u.shared;

import com.google.gwt.user.client.rpc.RemoteService;

import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Bewerbung;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAllAusschreibungen;
import de.hdm.gruppe1.Project4u.shared.report.ReportByAllBewerbungenForAusschreibung;

public interface ReportGenerator extends RemoteService{
	
	public void init() throws IllegalArgumentException;
	
	ReportByAllAusschreibungen createAllAusschreibungenReport(Ausschreibung au);
	
	ReportByAllBewerbungenForAusschreibung createAllBewerbungenForAllAusschreibungReport(Ausschreibung aus);

}
