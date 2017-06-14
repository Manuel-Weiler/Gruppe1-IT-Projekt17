/**
 * 
 */
package de.hdm.gruppe1.Project4u.client.gui;

import java.util.Date;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;
import de.hdm.gruppe1.Project4u.shared.bo.Projektmarktplatz;

/**
 * @author Tobias
 *
 */
public class AusschreibungsprofilWidget {
	Project4uAdministrationAsync Project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();
	FlexTable flex = new FlexTable();
	DialogBox db = new DialogBox();
	HTML heading = new HTML("<p class='heading'>Neue Ausschreibung:</p>");
	VerticalPanel vPan = new VerticalPanel();
	Ausschreibung local = new Ausschreibung();
	Projekt localProj = new Projekt();
	Projektmarktplatz pMart = new Projektmarktplatz();
	
	Button savenew = new Button("Speichern");
	Button update = new Button("Speichern");
	Button cancel = new Button("Abbrechen");
	//TODO:
	Button change = new Button("Bearbeiten");
	Button quit = new Button("Ausschreibung abbrechen");
	Button delete = new Button("Ausschreibung löschen");
 
	Label bezeichng = new Label("Stellenbezeichnung: ");
	Label projektleitr = new Label("Projektleiter: ");
	Label bewerbungsfrst = new Label("Bewerbungsfrist: ");
	HTML ausschrtext = new HTML("Ausschreibungstext: ");
	
	//TODO: Eigenschaften hinzufügen
	//TODO: Ausschreibung löschen
	//TODO: Ausschreibung abbrechen
	TextBox bezeichnung = new TextBox();
	TextBox projektleiter = new TextBox();
	DateBox bewerbungsfrist = new DateBox();
	TextArea ausschreibungstext = new TextArea();
	
	
	public AusschreibungsprofilWidget(Ausschreibung aus, Projekt p, Projektmarktplatz pMarkt){
		this.pMart=pMarkt;
		this.local= aus;
		this.localProj=p;
		db.setGlassEnabled(true);
		
		
		vPan.add(heading);
		
		flex.setWidget(0, 0, bezeichng);
		flex.setWidget(0, 1, bezeichnung);
		flex.setWidget(1, 0, projektleitr);
		flex.setWidget(1, 1, projektleiter);
		flex.setWidget(2, 0, bewerbungsfrst);
		flex.setWidget(2, 1, bewerbungsfrist);
		flex.setWidget(3, 0, ausschrtext);
		flex.setWidget(3, 1, ausschreibungstext);
		flex.setWidget(5, 0, cancel);
		
		vPan.add(flex);
		
		bezeichnung.setEnabled(false);
		projektleiter.setEnabled(false);
		bewerbungsfrist.setEnabled(false);
		ausschreibungstext.setEnabled(false);
		
		ausschreibungstext.setWidth("180px");
		ausschreibungstext.setHeight("150px");
		
		cancel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				db.hide();
			}
		});
		
		
		
		// Abfrage ob die Ausschreibung neu erstellt oder ein bestehendes Objekt
		// ist. Bei einer Ausschreibungs-ID von 0 (dem default-wert bei
		// Instaniziierung einer Ausschreibung) ist das Objekt neu erzeugt.
		if (aus.getAusschreibungId() == 0) {

			Project4uVerwaltung.getOrganisationseinheitByUser(ClientsideSettings.getAktuellerUser(),
					new AsyncCallback<Organisationseinheit>() {

						@Override
						public void onSuccess(Organisationseinheit result) {
							projektleiter.setValue(result.getName());

						}
						public void onFailure(Throwable caught) {
						}
					});
		
			bezeichnung.setEnabled(true);
			bewerbungsfrist.setEnabled(true);
			ausschreibungstext.setEnabled(true);
			
			flex.setWidget(5, 1, savenew);
			
			savenew.addClickHandler(new neueAusschreibungSpeichernClickhandler());
		}
		else{
			
			bezeichnung.setValue(local.getBezeichnung());
			bewerbungsfrist.setValue(local.getBewerbungsfrist());
			ausschreibungstext.setValue(local.getAusschreibungstext());
			
			flex.setWidget(5, 1, update);
			
		}
		
		db.add(vPan);
		
		
	}
	
	/**
	 * Der Clickhandler soll das erzeugen eines neuen Ausschreibungsobjektes
	 * ausführen, sobald der Nutzer alle erforderlichen Daten in das
	 * AusschreibungsprofilWidget eingetragen hat.
	 * @author Tobias
	 */
	private class neueAusschreibungSpeichernClickhandler implements ClickHandler{

		
		@Override
		public void onClick(ClickEvent event) {
			if(bezeichnung.getValue().isEmpty()&&ausschreibungstext.getValue().isEmpty()){
				MessageBox.alertWidget("Werte eintragen!", "Bitte alle Felder ausfüllen");
			}
			else if(!bewerbungsfrist.getValue().after(new Date())){
				MessageBox.alertWidget("Bewerbungsfrist!", "Die Bewerbungsfrist muss in der Zukuft liegen");
			}
			else{
				Project4uVerwaltung.createPartnerprofil(new AsyncCallback<Partnerprofil>() {
					
					@Override
					public void onSuccess(Partnerprofil result) {
						local.setBezeichnung(bezeichnung.getValue());
						local.setNameProjektleiter(projektleiter.getValue());
						local.setBewerbungsfrist(bewerbungsfrist.getValue());
						local.setErstellDatum(new Date());
						local.setAusschreibungstext(ausschreibungstext.getValue());
						local.setPartnerprofilId(result.getPartnerprofilId());
						local.setProjektId(localProj.getProjektId());
						
						Project4uVerwaltung.createAusschreibung(local, result, localProj, new AsyncCallback<Ausschreibung>() {
							
							@Override
							public void onSuccess(Ausschreibung result) {
								
								
								ProjektWidget PW = new ProjektWidget(pMart);
								PW.ausschreibungAnsehen(localProj);
								RootPanel.get("content").clear();
								RootPanel.get("content").add(PW);
								
								db.hide();
							}
							public void onFailure(Throwable caught) {
							}
						});
						
						
					}
					public void onFailure(Throwable caught) {
					}
				});
				
				
			}
			
			
		}
		
	}
	
	public void show(){
		
		 int left = Window.getClientWidth()/ 3;
         int top = Window.getClientHeight()/ 2;
		
		db.setAutoHideEnabled(false);
		db.setAnimationEnabled(true);
		//db.center();
		db.setPopupPosition(left, top);
		db.show();
	}
	
}
