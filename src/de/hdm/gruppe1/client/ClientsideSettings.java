package de.hdm.gruppe1.client;

import de.hdm.gruppe1.shared.CommonSettings;
import de.hdm.gruppe1.shared.bo.Nutzer;

public class ClientsideSettings extends CommonSettings{
	
	private static Nutzer aktuellerUser = null;

	/**
	 * gibt den aktuelle eingeloggten User zurueck
	 * @return aktuellerUser
	 */
	public static Nutzer getAktuellerUser() {
		return aktuellerUser;
	}

	/**
	 * setzt den aktuell eingeloggten User als User
	 * @param nutzerprofil
	 */
	public static void setAktuellerUser(Nutzer nutzer) {
		ClientsideSettings.aktuellerUser = nutzer;
	}
}
