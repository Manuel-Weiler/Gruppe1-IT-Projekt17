/**
 * 
 */
package de.hdm.gruppe1.Project4u.client.gui;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Bewerbung;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;

/**
 * @author Tobias
 *
 */
public class BewerbungWidget {
	Project4uAdministrationAsync Project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();
	Vector<Organisationseinheit> orgas= new Vector<Organisationseinheit>(); 
	Organisationseinheit user = new Organisationseinheit();
	Ausschreibung auss = new Ausschreibung();
	Projekt proj = new Projekt();
	
	DialogBox box = new DialogBox();
	VerticalPanel vp = new VerticalPanel();
	FlexTable flex = new FlexTable();

	DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyy-MM-dd");
	HTML hinweis = new HTML(
			"<p>Bitte wählen Sie das Profil mit dem Sie sich bewerben <br>Das Profil wird dem Projektleiter übermittel, passen Sie es ggf. an!</p>");
	Label ausName = new Label("Bewerbung auf die Ausschreibung: ");
	Label datum = new Label("Datum ");
	Label text = new Label("Motivationsschreiben: ");
	
	
	ListBox bewerbendesProfil = new ListBox();
	TextBox ausschreibungsname = new TextBox();
	DateBox erstelldatum = new DateBox();
	TextArea freitext = new TextArea();
	
	Button save = new Button("Bewerbung abschicken");
	Button cancel = new Button("Abbrechen");
	
	
	
	
	
	public BewerbungWidget(Ausschreibung aus){
		this.auss = aus;
		
		Project4uVerwaltung.getLinkedTeamAndUnternehmenOfOrganisationseinheit(ClientsideSettings.getAktuellerUser(),
				new getOrganisationseinheitenCallback());

		Project4uVerwaltung.findProjektById(aus.getProjektId(), new getProjektOfAusschreibungAndBewerbung());
		
		flex.setWidget(0, 1, bewerbendesProfil);
		flex.setWidget(1, 0, ausName);
		flex.setWidget(1, 1, ausschreibungsname);
		flex.setWidget(2, 0, datum);
		flex.setWidget(2, 1, erstelldatum);
		flex.setWidget(3, 0, text);
		flex.setWidget(3, 1, freitext);
		flex.setWidget(4, 0, cancel);
		flex.setWidget(4, 1, save);
	
		bewerbendesProfil.setVisibleItemCount(1);
		erstelldatum.setEnabled(false);
		ausschreibungsname.setEnabled(false);
		freitext.setWidth("300px");
		freitext.setHeight("200px");
		erstelldatum.setFormat(new DateBox.DefaultFormat(dateFormat));
		erstelldatum.setValue(new Date());
		ausschreibungsname.setValue(aus.getBezeichnung());
		
		
		save.addClickHandler(new saveClickHandler());
		cancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				box.hide();
			}
		});
		
		vp.add(hinweis);
		vp.add(flex);
		box.add(vp);
		
		
	}
	
	
	
	public VerticalPanel getVP(){
		return this.vp;
	}
	
	
	
	
	private class saveClickHandler implements ClickHandler{

		
		@Override
		public void onClick(ClickEvent event) {
			
			Bewerbung neu = new Bewerbung();
			
			neu.setOrganisationseinheitId(orgas.get(bewerbendesProfil.getSelectedIndex()).getOrganisationseinheitId());
			neu.setErstelldatum(erstelldatum.getValue());
			neu.setAusschreibungId(auss.getAusschreibungId());
			neu.setBewerbungstext(freitext.getValue());
			neu.setStatus("laufend");
			neu.setProjektname(proj.getName());
			
			Project4uVerwaltung.createBewerbung(neu, auss.getAusschreibungId(), neu.getOrganisationseinheitId(), new AsyncCallback<Bewerbung>() {
				
				@Override
				public void onSuccess(Bewerbung result) {
					
					box.hide();
					MessageBox.alertWidget("Erfolg!", "Ihre Bewerbung wurde erfolgreich angelegt, </br> verfolgen Sie den Status Ihrer Bewerbung unter 'Ausgangsbewerbungen'");
					
				}
				
				@Override
				public void onFailure(Throwable caught) {
				}
			});
		}
	}
	
	
	
	
	
	
	private class getProjektOfAusschreibungAndBewerbung implements AsyncCallback<Projekt>{
		@Override
		public void onFailure(Throwable caught) {
		}
		@Override
		public void onSuccess(Projekt result) {
			proj = result;
		}	
	}
	
	
	
	
	
	private class getOrganisationseinheitenCallback implements AsyncCallback<Vector<Organisationseinheit>>{

		
		@Override
		public void onFailure(Throwable caught) {
		}

		
		@Override
		public void onSuccess(Vector<Organisationseinheit> result) {
			orgas=result;
			for (Organisationseinheit org : result){
				bewerbendesProfil.addItem(org.getName()+" - "+org.getTyp()+" - "+org.getGoogleId());
			}
			
			Project4uVerwaltung.getOrganisationseinheitByUser(ClientsideSettings.getAktuellerUser(), new AsyncCallback<Organisationseinheit>() {
				
				public void onSuccess(Organisationseinheit result) {
					user = result;
					orgas.addElement(result);
					bewerbendesProfil.addItem(result.getName()+" - "+result.getTyp()+" - "+result.getGoogleId());
				}
				public void onFailure(Throwable caught) {
				}
			});}}
	
	
	
	
	public void show(){
		box.add(vp);
		box.center();
		box.setPopupPositionAndShow(new PositionCallback() {
			
			@Override
			public void setPosition(int offsetWidth, int offsetHeight) {
				box.setPopupPosition(Window.getClientWidth()/2, 
						Window.getClientHeight()/2);
				
			}
		});
	}
	
	
	
	
	//TODO:
	public void setAllDisabled (){
		hinweis.setHTML("<p class='heading'>Ihre Bewerbungsdaten: </p>");
		
		save.setVisible(false);
		cancel.setVisible(false);
		freitext.setReadOnly(true);
		
		bewerbendesProfil.setVisible(false);
		
	}
	
	
	
	
	
}
