package de.hdm.gruppe1.Project4u.client.gui;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class Fusszeile {
	
	private HorizontalPanel horfusspanel = new HorizontalPanel();
	private Label horfusslabel = new Label("Dieses Projekt ist von Gruppe1");
	
	public void loadFusszeile(){
		horfusspanel.add(horfusslabel);
		RootPanel.get("trailer").add(horfusspanel);
	}

}
