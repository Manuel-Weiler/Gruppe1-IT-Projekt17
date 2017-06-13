/*package de.hdm.gruppe1.Project4u.client.gui;

import java.util.Vector;

import com.gargoylesoftware.htmlunit.javascript.host.Text;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.client.gui.ProjektmarktplatzWidget.addProjektmarktplatzClickHandler;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;
import de.hdm.gruppe1.Project4u.shared.bo.Projektmarktplatz;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;

public class OrganisationseinheitWidget extends Composite {
	
	Project4uAdministrationAsync Project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();
	
	public static final ProvidesKey<Organisationseinheit> KEY_PROVIDER = new ProvidesKey<Organisationseinheit>() {
		public Object getKey(Organisationseinheit item) {
			return item == null ? null : item.getOrganisationseinheitId();
		}
	};

	
	public OrganisationseinheitWidget() {
		RootPanel.get("contentHeader").clear();
		RootPanel.get("contentHeader").add(new Label("Alle Organisationseinheiten:"));
		
		Button addOrganisationseinheit = new Button("Organisationseinheit anlegen");	
		addOrganisationseinheit.addClickHandler(new addOrganisationseinheitClickHandler());
	}
	
	private class addOrganisationseinheitClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			final DialogBox dBox = new DialogBox();
		
			VerticalPanel vPanel = new VerticalPanel();
			Label name = new Label("Name der Organisationseinheit");
			final TextBox oName = new TextBox();
			Label typ = new Label("Typ der Organisationseinheit");
			final TextBox oTyp = new TextBox();
			Label email = new Label("Google ID");
			final TextBox google_id = new TextBox();
			
			Button createOrga = new Button("Neue Organisationseinheit anlegen");
			createOrga.addClickHandler(new ClickHandler() {

				public void onClick(ClickEvent event) {
					if (!oName.getValue().isEmpty()){
						
						//Dem neu erzeugten Projektmarktplatz wird der Name zugewiesen und er wird in der DB abgelegt
						Project4uVerwaltung.createOrganisationseinheit(google_id.getValue(), oName.getValue(), oTyp.getValue(), partnerprofil new AsyncCallback<Organisationseinheit>() {
							
							@Override
							public void onSuccess(Organisationseinheit result) {
								
								//...anschlie�end wird das ProjektmarktplatzWidget neu erzeugt
								Project4uVerwaltung.findAllProjektmarktplatz(new AsyncCallback<Vector<Projektmarktplatz>>() {

									
									public void onSuccess(Vector<Projektmarktplatz> result) {
										RootPanel.get("content").clear();
										RootPanel.get("content").add(new ProjektmarktplatzWidget(result));

									}
									
									@Override
									public void onFailure(Throwable caught) {							
									}
								});
								
							}
							
							@Override
							public void onFailure(Throwable caught) {
								Window.alert(caught.getMessage());
								
							}
						});
												
						dBox.hide();
					}
					else{
						Window.alert("Bitte einen Namen eingeben");
					}
					
				}
			});
			vPanel.add(name);
			vPanel.add(pName);
			vPanel.add(createP);
			dBox.add(vPanel);
			dBox.center();
			dBox.show();
			dBox.setAutoHideEnabled(true);
			
		}}
}
*/