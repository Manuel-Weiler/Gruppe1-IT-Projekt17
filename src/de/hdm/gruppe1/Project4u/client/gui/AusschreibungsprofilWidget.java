/**
 * 
 */
package de.hdm.gruppe1.Project4u.client.gui;

import java.util.Date;
import java.util.Vector;

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
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Eigenschaft;
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
	Vector<Eigenschaft> neueEigenschaften = new Vector<Eigenschaft>();
	FlexTable flex = new FlexTable();
	FlexTable eigFlex = new FlexTable();
	FlexTable buttonFlex = new FlexTable();
	DialogBox db = new DialogBox();
	HTML heading = new HTML("<p class='heading'>Neue Ausschreibung:</p>");
	HTML heading2 = new HTML("<p class='heading'>Eigenschaften:</p>");
	VerticalPanel vPan = new VerticalPanel();
	Partnerprofil localPart = new Partnerprofil();
	Ausschreibung localAus = new Ausschreibung();
	Projekt localProj = new Projekt();
	Projektmarktplatz pMart = new Projektmarktplatz();
	
	Button savenew = new Button("Speichern");
	Button update = new Button("Speichernnnn");
	Button cancel = new Button("Abbrechen");
	//TODO:
	Button change = new Button("Bearbeiten");
	Button quit = new Button("Ausschreibung abbrechen");
	Button delete = new Button("Ausschreibung löschen");
	Button addEig	= new Button("Eigenschaft hinzufügen");
	Button bewerben = new Button("Auf Ausschreibung bewerben");
	
 
	Label bezeichng = new Label("Stellenbezeichnung: ");
	Label projektleitr = new Label("Projektleiter: ");
	Label bewerbungsfrst = new Label("Bewerbungsfrist: ");
	HTML ausschrtext = new HTML("Ausschreibungstext: ");
	
	
	//TODO: Ausschreibung löschen
	//TODO: Ausschreibung abbrechen
	TextBox bezeichnung = new TextBox();
	TextBox projektleiter = new TextBox();
	DateBox bewerbungsfrist = new DateBox();
	TextArea ausschreibungstext = new TextArea();
	
	
	public AusschreibungsprofilWidget(Ausschreibung aus, Projekt p, Projektmarktplatz pMarkt){
		this.pMart=pMarkt;
		this.localAus= aus;
		this.localProj=p;
		db.setGlassEnabled(true);
		
					
		flex.setWidget(0, 0, bezeichng);
		flex.setWidget(0, 1, bezeichnung);
		flex.setWidget(1, 0, projektleitr);
		flex.setWidget(1, 1, projektleiter);
		flex.setWidget(2, 0, bewerbungsfrst);
		flex.setWidget(2, 1, bewerbungsfrist);
		flex.setWidget(3, 0, ausschrtext);
		flex.setWidget(3, 1, ausschreibungstext);
		flex.setWidget(4, 0, cancel);
		
		
		buttonFlex.setWidget(0, 0, cancel);
		buttonFlex.setWidget(1, 0, change);
		buttonFlex.setWidget(0, 2, bewerben);
		buttonFlex.setWidget(2, 0, quit);
		buttonFlex.setWidget(2, 1, delete);
		buttonFlex.setWidget(3, 0, addEig);
		
		
		vPan.add(heading);
		vPan.add(flex);
		vPan.add(heading2);
		vPan.add(eigFlex);
		vPan.add(buttonFlex);
		
		update.setVisible(false);
		change.setVisible(false);
		bewerben.setVisible(false);
		quit.setVisible(false);
		delete.setVisible(false);
		addEig.setVisible(false);
		
		
		bezeichnung.setEnabled(false);
		projektleiter.setEnabled(false);
		bewerbungsfrist.setEnabled(false);
		ausschreibungstext.setEnabled(false);
		
		heading2.setVisible(false);
		bewerbungsfrist.setWidth("180px");
		ausschreibungstext.setWidth("180px");
		ausschreibungstext.setHeight("150px");
		
		
		savenew.addClickHandler(new neueAusschreibungSpeichernClickhandler());
		addEig.addClickHandler(new neueEigenschaftClickHandler());
		change.addClickHandler(new bearbeitenButtonClickHandler());
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
			addEig.setVisible(true);
			
			buttonFlex.setWidget(0, 1, savenew);
			
			
		}
		else{
			
			heading.setHTML("Ausschreibung "+aus.getBezeichnung());
			
			
			bezeichnung.setValue(localAus.getBezeichnung());
			bewerbungsfrist.setValue(localAus.getBewerbungsfrist());
			ausschreibungstext.setValue(localAus.getAusschreibungstext());
			projektleiter.setValue(aus.getNameProjektleiter());
			
			checkIfUserIsProjektleiter();
			
			buttonFlex.setWidget(0, 1, update);
			bewerben.setVisible(true);
			
			Project4uVerwaltung.getAllEigenschaftenByPartnerprofilId(aus.getPartnerprofilId(), new AsyncCallback<Vector<Eigenschaft>>() {
				
				@Override
				public void onSuccess(Vector<Eigenschaft> result) {
					if(!result.isEmpty()){
						heading2.setVisible(true);
					
						for(Eigenschaft e : result){
							TextBox name = new TextBox();
							name.setValue(e.getName());
							name.setEnabled(false);
							eigFlex.setWidget(eigFlex.getRowCount(), 0,  name);
							TextBox wert = new TextBox();
							wert.setValue(e.getWert());
							wert.setEnabled(false);
							eigFlex.setWidget(eigFlex.getRowCount()-1, 1,  wert);
							
						}
				}
				}
				
				@Override
				public void onFailure(Throwable caught) {
					
				}
			});
			
			
		}
		
		db.add(vPan);
		
		
	}
	
	
	
	
	
	
	//TODO: updatespeichern
	private class updateSpeichernButtonClickHandler implements ClickHandler{

		
		@Override
		public void onClick(ClickEvent event) {
			if(bezeichnung.getValue().isEmpty()&&ausschreibungstext.getValue().isEmpty()){
				MessageBox.alertWidget("Werte eintragen!", "Bitte alle Felder ausfüllen");
			}
			else if(!bewerbungsfrist.getValue().after(new Date())){
				MessageBox.alertWidget("Bewerbungsfrist!", "Die Bewerbungsfrist muss in der Zukuft liegen");
			}
			else{
				
				
				
				
				localAus.setBezeichnung(bezeichnung.getValue());
				
				localAus.setBewerbungsfrist(bewerbungsfrist.getValue());
				
				localAus.setAusschreibungstext(ausschreibungstext.getValue());
				
				
				

				
				for(int i=0; i<eigFlex.getRowCount(); i++){
					Eigenschaft e = new Eigenschaft();
					Widget w = eigFlex.getWidget(i, 0);
					 if (w instanceof TextBox) {
						 if(!((TextBox) w).getValue().isEmpty()){
							e.setName(((TextBox) w).getValue());
						 };
					 }
					 
					 Widget v = eigFlex.getWidget(i, 1);
					 if (v instanceof TextBox) {
						 if(!((TextBox) v).getValue().isEmpty()){
								e.setWert(((TextBox) v).getValue());
							 };
					 }
					 if(e.getName()!=null && e.getWert()!=null){
						 neueEigenschaften.add(e);
						 }}
				
				
				
				Project4uVerwaltung.insertEigenschaftenByPartnerprofil(neueEigenschaften, localPart, new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						
						Project4uVerwaltung.createAusschreibung(localAus, localPart.getPartnerprofilId(), localProj, new refreshProjektWidget());
					}
					
					public void onFailure(Throwable caught) {
					}
				});
				
				
			}
			
			
		}
		
	}
	
	
	
	
	
	
	private class bearbeitenButtonClickHandler implements ClickHandler{

		
		@Override
		public void onClick(ClickEvent event) {
			
			bezeichnung.setEnabled(true);
			bewerbungsfrist.setEnabled(true);
			ausschreibungstext.setEnabled(true);
			update.setVisible(true);
			
			addEig.setVisible(true);
			quit.setVisible(true);
			delete.setVisible(true);
			change.setVisible(false);
			
			for(int i=0; i<eigFlex.getRowCount(); i++){
				
				Widget w = eigFlex.getWidget(i, 0);
				 if (w instanceof TextBox) {
					 ((TextBox) w).setEnabled(true);
				 }
				 
				 Widget v = eigFlex.getWidget(i, 1);
				 if (v instanceof TextBox) {
					 ((TextBox) v).setEnabled(true);
				 }
				}
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	/**
	 * Der Clickhandler öffnet ein Eingabefenster und ermöglicht damit das Erstellen neuer Eigenschaften
	 * @author Tobias
	 *
	 */
	private class neueEigenschaftClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			final DialogBox db = new DialogBox();
			Label nam = new Label ("Bezeichnung:");
			Label wer = new Label ("Wert:");
			Button cancel = new Button("Abbrechen");
			Button ok = new Button("OK");
			final TextBox name = new TextBox();
			final TextBox wert = new TextBox();
			FlexTable ft = new FlexTable();
			ft.setWidget(0, 0, nam);
			ft.setWidget(0, 1, wer);
			ft.setWidget(1, 0, name);
			ft.setWidget(1, 1, wert);
			ft.setWidget(2, 1, ok);
			ft.setWidget(2, 0, cancel);
			db.add(ft);
			
			db.center();
			db.setAnimationEnabled(true);
			db.setAutoHideEnabled(true);
			db.show();
			
			cancel.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					db.hide();
					
				}
			});
			
			ok.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					if (name.getValue().isEmpty() || wert.getValue().isEmpty()){
						Window.alert("Bitte beide Felder ausfüllen");
					}
					
					else{
					db.hide();
					heading2.setVisible(true);
					eigFlex.setWidget(eigFlex.getRowCount(), 0, name);
					eigFlex.setWidget(eigFlex.getRowCount()-1, 1, wert);
					
					
					}
				}
			});
			
		;
			
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * Der Clickhandler soll das erzeugen eines neuen Ausschreibungsobjektes
	 * ausführen, sobald der Nutzer alle erforderlichen Daten in das
	 * AusschreibungsprofilWidget eingetragen hat.
	 * @author Tobias
	 */
	private class neueAusschreibungSpeichernClickhandler implements ClickHandler{

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
						localPart = result;
						localAus.setBezeichnung(bezeichnung.getValue());
						localAus.setNameProjektleiter(projektleiter.getValue());
						localAus.setBewerbungsfrist(bewerbungsfrist.getValue());
						localAus.setErstellDatum(new Date());
						localAus.setAusschreibungstext(ausschreibungstext.getValue());
						localAus.setPartnerprofilId(result.getPartnerprofilId());
						localAus.setProjektId(localProj.getProjektId());
						

						
						for(int i=0; i<eigFlex.getRowCount(); i++){
							Eigenschaft e = new Eigenschaft();
							Widget w = eigFlex.getWidget(i, 0);
							 if (w instanceof TextBox) {
								 if(!((TextBox) w).getValue().isEmpty()){
									e.setName(((TextBox) w).getValue());
								 };
							 }
							 
							 Widget v = eigFlex.getWidget(i, 1);
							 if (v instanceof TextBox) {
								 if(!((TextBox) v).getValue().isEmpty()){
										e.setWert(((TextBox) v).getValue());
									 };
							 }
							 if(e.getName()!=null && e.getWert()!=null){
								 neueEigenschaften.add(e);
								 }}
						Project4uVerwaltung.insertEigenschaftenByPartnerprofil(neueEigenschaften, result, new AsyncCallback<Void>() {
							
							@Override
							public void onSuccess(Void result) {
								
								Project4uVerwaltung.createAusschreibung(localAus, localPart.getPartnerprofilId(), localProj, new refreshProjektWidget());
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
	
	
	private class refreshProjektWidget implements AsyncCallback<Ausschreibung>{

		public void onFailure(Throwable caught) {
		}
		public void onSuccess(Ausschreibung result) {
			ProjektWidget PW = new ProjektWidget(pMart);
			PW.ausschreibungAnsehen(localProj);
			RootPanel.get("content").clear();
			RootPanel.get("content").add(PW);
			db.hide();
		}
	}
	
	
	
	
	
	private void checkIfUserIsProjektleiter() {

						Project4uVerwaltung.getOrganisationseinheitById(localProj.getOrganisationseinheitId(),
								new AsyncCallback<Organisationseinheit>() {

							@Override
							public void onSuccess(Organisationseinheit projektleiter) {
								if(ClientsideSettings.getAktuellerUser().getEmailAddress().equalsIgnoreCase(projektleiter.getGoogleId())){
									change.setVisible(true);
									bewerben.setVisible(false);
								}
							}
							public void onFailure(Throwable caught) {
							}
						});
	}
	
	
	
	
	public void show(){
		
		 int left = Window.getClientWidth()/ 4;
         int top = Window.getClientHeight()/ 2;
		
		db.setAutoHideEnabled(false);
		db.setAnimationEnabled(true);
		db.center();
		//db.setPopupPosition(left, top);
		db.show();
	}
	
}
