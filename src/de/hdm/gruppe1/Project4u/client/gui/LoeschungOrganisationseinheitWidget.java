package de.hdm.gruppe1.Project4u.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.client.Project4u;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministration;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;

public class LoeschungOrganisationseinheitWidget extends Composite{
	Project4uAdministrationAsync Project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();
	Organisationseinheit organisationseinheit = new Organisationseinheit();
	
	private Anchor signOutLink = new Anchor();
	
	String[] messages = { "Sind Sie sich sicher?", "Ganz ganz sicher?", "<b>Wirklich</b> sicher?",
			"Noch <b>einmal</b> klicken, dann ist das Konto gelöscht", "Wollen Sie das Konto wirklich löschen?",
			"Denken Sie nochmal in Ruhe nach", "klicken Sie zur Abwechslung mal auf Abbrechen",
			"klicken Sie nicht so schnell", "...so leicht kommen Sie nicht davon", "Sie könnten auch abbrechen...",
			"Wirklich <b>ganz</b> sicher?", "Zum Löschen des Kontos klicken Sie auf <h3>Abbrechen</h3>",
			"Überlegen Sie nochmal ganz kurz" };
	Random r;
	HTML message = new HTML("Klicken Sie auf <b>Weiter</b> um das Konto zu löschen");
	int stagecounter = 0;
	int buttonheight = 45;
	int buttonwidth = 85;
	FlexTable flextable = new FlexTable();
	DialogBox diaBox = new DialogBox();
	Button abbrechen = new Button("Abbrechen");
	Button weiter = new Button("Weiter");
	VerticalPanel vp = new VerticalPanel();
	
	public LoeschungOrganisationseinheitWidget(Organisationseinheit orga){
		this.organisationseinheit=orga;
		diaBox.setGlassEnabled(true);
		vp.add(message);
		flextable.setWidget(1, 0, abbrechen);
		flextable.setWidget(1, 1, weiter);
		vp.add(flextable);
		diaBox.add(vp);
		diaBox.center();
		
		diaBox.show();
		
		
		
		abbrechen.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				diaBox.hide();
				
			}
		});
		
		
		
		weiter.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(stagecounter<=10){
					abbrechen.setPixelSize(buttonwidth, buttonheight);
					message.setHTML(messages[r.nextInt(messages.length)]);
					buttonwidth=buttonwidth+10;
					buttonheight=buttonheight+10;
					stagecounter++;
				}
				else{
					//TODO: delete ORGA
					Project4uVerwaltung.deleteOrganisationseinheit(organisationseinheit, new AsyncCallback<Void>() {
						
						@Override
						public void onSuccess(Void result) {
							diaBox.hide();
							MessageBox.alertWidget("Tschüss!", "Ihr Nutzerprofil, Ihre Projektmarktplätze Ihre Projekte, </br>Ihre Ausschreibungen, Ihre Beteiligungen sowie Ihre Bewerbungen wurden gelöscht!");
							signOutLink.setHref(ClientsideSettings.getAktuellerUser().getLogoutUrl());
							Window.Location.assign(signOutLink.getHref());
						}
						
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							
						}
					});
				}
			}
		});
		
	}
	
	
	
	
	
}
