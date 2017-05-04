package de.hdm.gruppe1.Project4u.client.gui;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;

public class Navigationsleiste extends VerticalPanel {

	Project4uAdministrationAsync project4u = ClientsideSettings.getProject4uVerwaltung();
	Organisationseinheit nutzer = ClientsideSettings.getAktuellerUser();
	
	public void loadLogout(Organisationseinheit nutzer) {
		final String logoutUrl = nutzer.getLogoutUrl();
		Window.Location.assign(logoutUrl);
	}

	// Laedt Navigationsleiste und erstellt Men�
	/*public void loadNavigation() {
		MenuBar menu = new MenuBar();
		menu.setAutoOpen(true);
		menu.setWidth("430px");
		menu.setAnimationEnabled(true);

		// Menubar bauen
		MenuBar nutzerMenu = new MenuBar(true);
		nutzerMenu.setAnimationEnabled(true);

		MenuBar logOutMenu = new MenuBar(true);
		logOutMenu.setAnimationEnabled(true);

		menu.addItem(new MenuItem("Mein Profil", nutzerMenu));
		menu.addSeparator();
		menu.addItem(new MenuItem("Logout", new Command() {
			public void execute() {
				loadLogout(nutzer);
			}
		}));

		nutzerMenu.addSeparator();

		// NutzerMenu
		nutzerMenu.addItem("Dein Profil", new Command() {
			public void execute() {
				RootPanel.get("Profil").clear();
				Startseite loadStartseite = new Startseite();
				loadStartseite.loadStartseite();
			}
		});

		RootPanel.get("navigation").clear();
		RootPanel.get("navigation").add(menu);}*/
	


}
