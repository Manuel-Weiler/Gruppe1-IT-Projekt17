/**
 * 
 */
package de.hdm.gruppe1.Project4u.client.gui;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe1.Project4u.client.ClientsideSettings;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministrationAsync;
import de.hdm.gruppe1.Project4u.shared.bo.Eigenschaft;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;

/**
 * @author Tobias
 *
 */
public class BewerbungsprofilWidget extends Composite{
	
	Project4uAdministrationAsync Project4uVerwaltung = ClientsideSettings.getProject4uVerwaltung();
	
	DialogBox db = new DialogBox();
	VerticalPanel vp = new VerticalPanel();
	FlexTable flexTable = new FlexTable();
	FlexTable flexTableEigenschaften = new FlexTable();
	
	
	
	Label email = new Label("E-Mail:");
	TextBox mail = new TextBox();
	Label orgaName = new Label("Profilname:");
	TextBox orgaNam = new TextBox();
	Label typ = new Label("Kontentyp:");
	ListBox typbox = new ListBox();
	
	
	public BewerbungsprofilWidget(int OrgaID){
		
		Project4uVerwaltung.getOrganisationseinheitById(OrgaID, new AsyncCallback<Organisationseinheit>() {
			
			@Override
			public void onSuccess(Organisationseinheit orga) {
				
				
				HTML Profil = new HTML("<p class='heading'>Profil: "+orga.getName()+"</p>");
				
				db.setGlassEnabled(true);
								
				mail.setValue(orga.getGoogleId());	
				orgaNam.setValue(orga.getName());
				typbox.addItem(orga.getTyp());
				typbox.setVisibleItemCount(1);
				
				
				flexTable.setWidget(0, 0, orgaName);
				flexTable.setWidget(0, 1, orgaNam);
				flexTable.setWidget(1, 0, email);
				flexTable.setWidget(1, 1, mail);		
				flexTable.setWidget(2, 0, typ);			
				flexTable.setWidget(2, 1, typbox);
				
				mail.setEnabled(false);
				orgaNam.setEnabled(false);
				typbox.setEnabled(false);
				
				vp.add(Profil);
				vp.add(flexTable);
				
				Project4uVerwaltung.getEigenschaftenOfOrganisationseinheit(orga, new AsyncCallback<Vector<Eigenschaft>>() {
					
					@Override
					public void onSuccess(Vector<Eigenschaft> result) {
						if(!result.isEmpty()){
						for(Eigenschaft e : result){
							TextBox tb = new TextBox();
							tb.setValue(e.getName());
							tb.setEnabled(false);
							flexTableEigenschaften.setWidget(flexTableEigenschaften.getRowCount(), 0,  tb);
							TextBox tbox = new TextBox();
							tbox.setValue(e.getWert());
							tbox.setEnabled(false);
							flexTableEigenschaften.setWidget(flexTableEigenschaften.getRowCount()-1, 1,  tbox);
							
						}
						vp.add(flexTableEigenschaften);
						
						
						}
					}
					public void onFailure(Throwable caught) {}
				});
				
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
				
			}
		});
		
		initWidget(vp);
	}
	
	
	
	
	
}
