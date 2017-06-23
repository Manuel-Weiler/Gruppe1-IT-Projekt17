package de.hdm.gruppe1.Project4u.server;

import java.io.Serializable;

import java.util.ArrayList;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe1.Project4u.server.db.AusschreibungMapper;
import de.hdm.gruppe1.Project4u.server.db.BeteiligungMapper;
import de.hdm.gruppe1.Project4u.server.db.BewerbungMapper;
import de.hdm.gruppe1.Project4u.server.db.BewertungMapper;
import de.hdm.gruppe1.Project4u.server.db.EigenschaftMapper;
import de.hdm.gruppe1.Project4u.server.db.OrganisationseinheitMapper;
import de.hdm.gruppe1.Project4u.server.db.PartnerprofilMapper;
import de.hdm.gruppe1.Project4u.server.db.ProjektMapper;
import de.hdm.gruppe1.Project4u.server.db.ProjektmarktplatzMapper;
import de.hdm.gruppe1.Project4u.shared.LoginInfo;
import de.hdm.gruppe1.Project4u.shared.Project4uAdministration;
import de.hdm.gruppe1.Project4u.shared.bo.Ausschreibung;
import de.hdm.gruppe1.Project4u.shared.bo.Beteiligung;
import de.hdm.gruppe1.Project4u.shared.bo.Bewerbung;
import de.hdm.gruppe1.Project4u.shared.bo.Bewertung;
import de.hdm.gruppe1.Project4u.shared.bo.Eigenschaft;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
import de.hdm.gruppe1.Project4u.shared.bo.Partnerprofil;
import de.hdm.gruppe1.Project4u.shared.bo.Projekt;
import de.hdm.gruppe1.Project4u.shared.bo.Projektmarktplatz;

@SuppressWarnings("serial")
public class Project4uAdministrationImpl extends RemoteServiceServlet implements Project4uAdministration, Serializable {

	private BewerbungMapper bewerbungMapper = null;
	private BewertungMapper bewertungMapper = null;
	private BeteiligungMapper beteiligungMapper = null;
	private AusschreibungMapper ausschreibungMapper = null;
	private OrganisationseinheitMapper organisationseinheitMapper = null;
	private PartnerprofilMapper partnerprofilMapper = null;
	private ProjektmarktplatzMapper projektmarktplatzMapper = null;
	private ProjektMapper projektMapper = null;
	private EigenschaftMapper eigenschaftMapper = null;

	public Project4uAdministrationImpl() throws IllegalArgumentException {

	}

	// Initialisierung
	public void init() throws IllegalArgumentException {

		this.eigenschaftMapper = EigenschaftMapper.eigenschaftMapper();
		this.beteiligungMapper = BeteiligungMapper.beteiligungMapper();
		this.organisationseinheitMapper = OrganisationseinheitMapper.organisationseinheitMapper();
		this.partnerprofilMapper = PartnerprofilMapper.partnerprofilMapper();
		this.projektmarktplatzMapper = ProjektmarktplatzMapper.projektmarktplatzMapper();
		this.projektMapper = ProjektMapper.projektMapper();
		this.bewertungMapper = BewertungMapper.bewertungMapper();
		this.bewerbungMapper = BewerbungMapper.bewerbungMapper();
		this.ausschreibungMapper = AusschreibungMapper.ausschreibungMapper();
	}

	/*
	 * #########################################################################
	 * ABSCHNITT, Beginn: Partnerprofil
	 * #########################################################################
	 * 
	 */

	/**
	 * Mit dieser Methode wird dem zu speichernden Partnerprofil die richtige
	 * <code>id</code> vergeben und das Partnerprofil in der Datenbank abgelegt.
	 * 
	 * @param p
	 *            das Partnerprofil-Objekt, dass in der Datenbank abgelegt wird.
	 * @param o
	 *            das Organisationseinheit-Objekt, dem das Partnerprofil
	 *            zugeordnet ist.
	 * @return das m�glicherweise durch die Methode ge�nderte
	 *         Partnerprofil-Objekt.
	 */

	public Partnerprofil createPartnerprofil() throws IllegalArgumentException {
		Partnerprofil p = new Partnerprofil();
		return this.partnerprofilMapper.insertPartnerprofil(p);
	}

	public Partnerprofil updatePartnerprofil(Partnerprofil p) throws IllegalArgumentException {
		return this.partnerprofilMapper.updatePartnerprofil(p);
	}

	public void deletePartnerprofil(Partnerprofil p) throws IllegalArgumentException {
		System.out.println("deletePartnerprofil");

		// Zugehörige Eigenschaften löschen
		Vector<Eigenschaft> ve = new Vector<Eigenschaft>();

		ve = eigenschaftMapper.findByPartnerprofil(p);
		if (ve != null) {
			for (Eigenschaft eigenschaft : ve) {
				this.deleteEigenschaft(eigenschaft);
			}
		}
		// Partnerprofil löschen
		this.partnerprofilMapper.deletePartnerprofil(p);
	}

	public Partnerprofil findById(int i) throws IllegalArgumentException {
		return this.partnerprofilMapper.findById(i);
	}

	/*
	 * TODO: public Ausschreibung getAusschreibungOf(Partnerprofil p) public
	 * Vector <Eigenschaft> getEigenschaftenOf (Partnerprofil p)
	 */

	public Vector<Eigenschaft> getEigenschaftenOfPartnerprofil(Partnerprofil p) throws IllegalArgumentException {
		return this.partnerprofilMapper.getEigenschaftenOfPartnerprofil(p);
	}

	/*
	 * #########################################################################
	 * ABSCHNITT, Ende: Partnerprofil
	 * #########################################################################
	 * 
	 */

	/*
	 * #########################################################################
	 * ABSCHNITT, Beginn: Organisationseinheit
	 * #########################################################################
	 * 
	 */

	// Login-Status
	public boolean checkStatus(LoginInfo loginInfo) {
		boolean status = false;
		Vector<Organisationseinheit> orgas = new Vector<Organisationseinheit>();
		orgas = organisationseinheitMapper.findAll();
		for (Organisationseinheit o : orgas) {
			if (o.getGoogleId().equalsIgnoreCase(loginInfo.getEmailAddress())) {
				status = true;
				return status;
			}

		}
		return status;
	}

	public Organisationseinheit createOrganisationseinheit(Organisationseinheit orga) throws IllegalArgumentException {
		return this.organisationseinheitMapper.insert(orga);
	}

	public void deleteOrganisationseinheit(Organisationseinheit organisationseinheit) throws IllegalArgumentException {

		System.out.println("deleteOrga");

		// Zugehörige Beteiligungen löschen
		Vector<Beteiligung> vbe = new Vector<Beteiligung>();
		vbe = beteiligungMapper.findByOrganisationseinheit(organisationseinheit);
		if (vbe != null) {
			for (Beteiligung b : vbe) {
				this.deleteBeteiligung(b);
			}
		}

		// Zugehörige Bewerbungen löschen
		Vector<Bewerbung> vb = new Vector<Bewerbung>();
		vb = bewerbungMapper.findByOrganisationseinheit(organisationseinheit);
		if (vb != null) {
			for (Bewerbung b : vb) {
				this.deleteBewerbung(b);
			}
		}

		// Zugehörige Projekte löschen
		Vector<Projekt> vp = new Vector<Projekt>();
		vp = projektMapper.findByOrganisationseinheit(organisationseinheit);
		if (vp != null) {
			for (Projekt projekt : vp) {
				this.delete(projekt);
			}
		}

		// Zugehörige Projektmarktplätze löschen
		Vector<Projektmarktplatz> pm = new Vector<Projektmarktplatz>();
		pm = projektmarktplatzMapper.findByOrganisationseinheit(organisationseinheit);
		if (pm != null) {
			for (Projektmarktplatz projektmarktplatz : pm) {
				this.delete(projektmarktplatz);
			}
		}

		// Zugehörigkeit zu Teams oder Unternehmen löschen
		this.organisationseinheitMapper.deleteVerbindungenOfOrganisationseinheit(organisationseinheit);

		// Organisationseinheit löschen
		this.organisationseinheitMapper.delete(organisationseinheit);

		// Zugehöriges Partnerprofil löschen
		Partnerprofil partnerprofil = partnerprofilMapper.findById(organisationseinheit.getPartnerprofilId());
		if (partnerprofil != null) {
			this.deletePartnerprofil(partnerprofil);
		}
	}

	public void deleteVerbindungenOfOrganisationseinheit(Organisationseinheit organisationseinheit)
			throws IllegalArgumentException {
		this.organisationseinheitMapper.deleteVerbindungenOfOrganisationseinheit(organisationseinheit);
	}

	public Organisationseinheit getOrganisationseinheitById(int id) throws IllegalArgumentException {
		return this.organisationseinheitMapper.findByKey(id);
	}

	public Vector<Organisationseinheit> getAllOrganisationseinheiten() throws IllegalArgumentException {
		return this.organisationseinheitMapper.findAll();
	}

	public Vector<Organisationseinheit> getOrganisationseinheitByName(String name) {
		return this.organisationseinheitMapper.findByName(name);
	}

	public Vector<Organisationseinheit> getOrganisationseinheitByTyp(String typ) {
		return this.organisationseinheitMapper.findByTyp(typ);
	}

	public Organisationseinheit getOrganisationseinheitByUser(LoginInfo login) throws IllegalArgumentException {

		for (Organisationseinheit o : organisationseinheitMapper.findByTyp("Person")) {
			if (o.getGoogleId().equalsIgnoreCase(login.getEmailAddress())) {

				return o;
			}

		}
		return null;

	};

	public void updateOrganisationseinheit(Organisationseinheit organisationseinheit) throws IllegalArgumentException {
		this.organisationseinheitMapper.update(organisationseinheit);
	}

	public Partnerprofil getPartnerprofilOfOrganisationseinheit(Organisationseinheit orga)
			throws IllegalArgumentException {
		return this.partnerprofilMapper.findById(orga.getPartnerprofilId());
	}

	public Vector<Eigenschaft> getEigenschaftenOfOrganisationseinheit(Organisationseinheit orga)
			throws IllegalArgumentException {
		Partnerprofil partnerprofil = new Partnerprofil();
		partnerprofil = getPartnerprofilOfOrganisationseinheit(orga);

		return getEigenschaftenOfPartnerprofil(partnerprofil);
	}

	public Vector<Organisationseinheit> getAllOrganisationseinheitenOfTypTeamUnternehmen()
			throws IllegalArgumentException {
		Vector<Organisationseinheit> result = new Vector<Organisationseinheit>();
		Vector<Organisationseinheit> orgas = new Vector<Organisationseinheit>();
		orgas = organisationseinheitMapper.findAll();

		for (Organisationseinheit o : orgas) {
			if (o.getTyp().equalsIgnoreCase("Team") || o.getTyp().equalsIgnoreCase("Unternehmen")) {
				result.add(o);
			}
		}
		return result;
	}

	/**
	 * Die Methode speichert die Zugehörigkeit einer Organisationseinheit vom
	 * Typ Person zu einer oder mehreren Organisationseinheiten vom Typ
	 * Unternehmen oder Team.
	 * 
	 * @param person
	 * @param TeamUnternehmen
	 * @throws IllegalArgumentException
	 */
	public void insertLinkedTeamUnternehmenOfOrganisationseinheit(LoginInfo login, Organisationseinheit teamunternehmen)
			throws IllegalArgumentException {
		Organisationseinheit o = new Organisationseinheit();
		o = getOrganisationseinheitByUser(login);
		organisationseinheitMapper.insertLinkedTeamUnternehmenOfOrganisationseinheit(o, teamunternehmen);
	}

	/**
	 * Die Methode gibt alle Organisationseinheiten vom Typ Team und Unternehmen
	 * zurück, zu denen der Benutzer die Zugehörigkeit seines "Accounts" vom Typ
	 * Person definiert hat.
	 * 
	 * @param login
	 * @return
	 */
	public Vector<Organisationseinheit> getLinkedTeamAndUnternehmenOfOrganisationseinheit(LoginInfo login)
			throws IllegalArgumentException {
		Organisationseinheit o = new Organisationseinheit();
		o = getOrganisationseinheitByUser(login);

		return organisationseinheitMapper.getLinkedTeamAndUnternehmenOfOrganisationseinheit(o);

	}

	public Vector<Organisationseinheit> getAllOrganisationseinheitOfTypPerson() throws IllegalArgumentException {

		Vector<Organisationseinheit> orgas = organisationseinheitMapper.findAll();
		Vector<Organisationseinheit> result = new Vector<Organisationseinheit>();

		for (Organisationseinheit orga : orgas) {
			if (orga.getTyp().equalsIgnoreCase("Team")) {
				result.add(orga);
			}
		}
		return result;

	}

	public void deleteLinkedTeamUnternehmenOfOrganisationseinheit(LoginInfo login, Organisationseinheit team)
			throws IllegalArgumentException {
		Organisationseinheit o = new Organisationseinheit();
		o = getOrganisationseinheitByUser(login);

		organisationseinheitMapper.deleteLinkedTeamUnternehmenOfOrganisationseinheit(o, team);

	};

	/*
	 * #########################################################################
	 * ABSCHNITT, Ende: Organisationseinheit
	 * #########################################################################
	 * 
	 */

	/*
	 * #########################################################################
	 * ABSCHNITT, Beginn: Eigenschaft
	 * #########################################################################
	 * 
	 */

	/**
	 * Die Methode vergibt dem zu speichernden Eigenschafts-Objekts einen
	 * Prim�rschl�ssel und legt es in der DB ab. Zudem aktualisiert sie das
	 * �nderungsdatum des zugeh�rigen Partnerprofil-Objekts
	 * 
	 * @param e
	 * @param p
	 * @throws IllegalArgumentException
	 */
	public Eigenschaft insertEigenschaft(Eigenschaft e, Partnerprofil p) throws IllegalArgumentException {
		return this.eigenschaftMapper.insertEigenschaft(e, p);
	}

	public void insertEigenschaften(Vector<Eigenschaft> eigenschaften, Organisationseinheit orga)
			throws IllegalArgumentException {
		Partnerprofil partnerprofil = new Partnerprofil();
		partnerprofil = getPartnerprofilOfOrganisationseinheit(orga);
		for (Eigenschaft e : eigenschaften) {
			insertEigenschaft(e, partnerprofil);
		}
	}

	public void insertEigenschaftenByPartnerprofil(Vector<Eigenschaft> eigenschaften, int partnerprofilId)
			throws IllegalArgumentException {
		Partnerprofil partner = findById(partnerprofilId);

		for (Eigenschaft e : eigenschaften) {
			insertEigenschaft(e, partner);
		}
	}

	public Eigenschaft updateEigenschaft(Eigenschaft e) throws IllegalArgumentException {
		return this.eigenschaftMapper.updateEigenschaft(e);
	}

	public void deleteEigenschaft(Eigenschaft e) throws IllegalArgumentException {
		System.out.println("deleteEigenschaft");
		eigenschaftMapper.deleteEigenschaft(e);
	}

	public void deleteAllEigenschaftenOfOrganisationseinheit(Organisationseinheit orga)
			throws IllegalArgumentException {
		Partnerprofil partnerprofil = new Partnerprofil();
		partnerprofil = getPartnerprofilOfOrganisationseinheit(orga);
		deleteAllEigenschaftOfPartnerprofil(partnerprofil.getPartnerprofilId());
	}

	public void deleteAllEigenschaftOfPartnerprofil(int partnerprofilId) throws IllegalArgumentException {
		Partnerprofil p = findById(partnerprofilId);
		eigenschaftMapper.deleteAllEigenschaftOfPartnerprofil(p);
	}

	public Vector<Eigenschaft> getAllEigenschaftenByPartnerprofilId(int partnerprofilId)
			throws IllegalArgumentException {
		Partnerprofil p = findById(partnerprofilId);
		return this.eigenschaftMapper.findByPartnerprofil(p);
	}

	/*
	 * #########################################################################
	 * ABSCHNITT, Ende: Eigenschaft
	 * #########################################################################
	 * 
	 */

	/*
	 * #########################################################################
	 * ABSCHNITT, Beginn: Projektmarktplatz
	 * #########################################################################
	 * 
	 */

	public Projektmarktplatz createProjektmarktplatz(Projektmarktplatz p, LoginInfo login)
			throws IllegalArgumentException {
		Organisationseinheit o = new Organisationseinheit();
		o = getOrganisationseinheitByUser(login);
		p.setOrganisationseinheitId(o.getOrganisationseinheitId());
		return this.projektmarktplatzMapper.insert(p);

	}

	public Projektmarktplatz findProjektmarktplatzById(int id) throws IllegalArgumentException {
		return this.projektmarktplatzMapper.findById(id);
	}

	public Vector<Projektmarktplatz> findAllProjektmarktplatz() throws IllegalArgumentException {
		return this.projektmarktplatzMapper.findAll();
	}

	public void update(Projektmarktplatz p) throws IllegalArgumentException {
		projektmarktplatzMapper.update(p);
	}

	public void delete(Projektmarktplatz p) throws IllegalArgumentException {
		System.out.println("deleteProjektmarktplatz");

		// Zugehörige Projekte löschen
		Vector<Projekt> pv = new Vector<Projekt>();
		pv = projektMapper.findByProjektmarktplatz(p);
		if (pv != null) {
			for (Projekt projekt : pv) {
				this.delete(projekt);
			}
		}

		// Projektmarktplatz löschen
		this.projektmarktplatzMapper.delete(p);
	}

	/*
	 * #########################################################################
	 * ABSCHNITT, Ende: Projektmarktplatz
	 * #########################################################################
	 * 
	 */

	/*
	 * #########################################################################
	 * ABSCHNITT, Beginn: Projekt
	 * #########################################################################
	 * 
	 */

	public Projekt createProjekt(Projekt p, Projektmarktplatz pm, LoginInfo login) throws IllegalArgumentException {
		Organisationseinheit o = new Organisationseinheit();
		o = getOrganisationseinheitByUser(login);
		return this.projektMapper.insert(p, pm, o);
	}

	public Projekt createProjekt(int id, String name, Date startdatum, Date enddatum, String beschreibung,
			int projektmarktplatz_id, int organisationseinheit_id) throws IllegalArgumentException {

		Projekt projekt = new Projekt();
		projekt.setProjektId(1);
		projekt.setName(name);
		projekt.setStartdatum(startdatum);
		projekt.setEnddatum(enddatum);
		projekt.setBeschreibung(beschreibung);
		projekt.setProjektmarktplatzId(projektmarktplatz_id);
		projekt.setOrganisationseinheitId(organisationseinheit_id);

		return this.projektMapper.insert(projekt, null, null);

	}

	public Projekt findProjektById(int id) throws IllegalArgumentException {
		return this.projektMapper.findById(id);
	}

	public Vector<Projekt> findAllProjekt() throws IllegalArgumentException {
		return this.projektMapper.findAll();
	}

	public void update(Projekt p) throws IllegalArgumentException {
		projektMapper.update(p);
	}

	public void delete(Projekt projekt) throws IllegalArgumentException {
		System.out.println("deleteProjekt");

		// Zugehörige Ausschreibungen löschen
		Vector<Ausschreibung> va = new Vector<Ausschreibung>();
		va = this.ausschreibungMapper.findByProjekt(projekt);
		if (va != null) {
			for (Ausschreibung ausschreibung : va) {
				this.deleteAusschreibung(ausschreibung);
			}
		}
		// Zugehörige Beteiligungen löschen
		Vector<Beteiligung> vb = new Vector<Beteiligung>();
		vb = this.beteiligungMapper.findByProjekt(projekt);
		if (va != null) {
			for (Beteiligung beteiligung : vb) {
				this.deleteBeteiligung(beteiligung);
			}
		}
		this.projektMapper.delete(projekt);
	}

	public Vector<Projekt> findByName(String name) throws IllegalArgumentException {
		return this.projektMapper.findByName(name);
	}

	public Vector<Projekt> findByProjektmarktplatz(Projektmarktplatz projektmarktplatz)
			throws IllegalArgumentException {
		return this.projektMapper.findByProjektmarktplatz(projektmarktplatz);
	}

	public Vector<Organisationseinheit> findProjektleiterOfProjects(Vector<Projekt> projekte)
			throws IllegalArgumentException {
		Vector<Organisationseinheit> result = new Vector<>();
		for (Projekt pro : projekte) {

			result.add(getOrganisationseinheitById(pro.getOrganisationseinheitId()));
		}
		return result;
	}

	public Vector<Projekt> getAllProjekteOfProjektleiter(Organisationseinheit orga) throws IllegalArgumentException {

		return this.projektMapper.findByOrganisationseinheit(orga);
	}
	
	
	
	public Projekt getProjektOfBewerbung(Bewerbung bewerbung)throws IllegalArgumentException{
		
		Ausschreibung a = findByIdAusschreibung(bewerbung.getAusschreibungId());
		Projekt p = findProjektById(a.getProjektId());
		
		return p;
	}

	/*
	 * #########################################################################
	 * ABSCHNITT, Ende: Projekt
	 * #########################################################################
	 * 
	 */

	/*
	 * #########################################################################
	 * ABSCHNITT, Beginn: Ausschreibung
	 * #########################################################################
	 * 
	 */

	public Ausschreibung createAusschreibung(Ausschreibung a, int partnerprofilId, Projekt pr)
			throws IllegalArgumentException {
		Partnerprofil p = findById(partnerprofilId);

		return this.ausschreibungMapper.insertAusschreibung(a, p, pr);
	}

	public Ausschreibung updateAusschreibung(Ausschreibung ausschreibung) throws IllegalArgumentException {
		return this.ausschreibungMapper.updateAusschreibung(ausschreibung);
	}

	public void deleteAusschreibung(Ausschreibung ausschreibung) throws IllegalArgumentException {
		System.out.println("deleteAusschreibung");

		// TODO zugehörige Bewerbungen löschen
		Vector<Bewerbung> bv = new Vector<Bewerbung>();
		bv = bewerbungMapper.findByAusschreibung(ausschreibung);
		if (bv != null) {
			for (Bewerbung bewerbung : bv) {
				this.deleteBewerbung(bewerbung);
			}
		}

		this.ausschreibungMapper.delete(ausschreibung);

		// zugehöriges Partnerprofil löschen
		Partnerprofil partnerprofil = partnerprofilMapper.findById(ausschreibung.getPartnerprofilId());
		if (partnerprofil != null) {
			this.deletePartnerprofil(partnerprofil);
		}
	}

	public Ausschreibung findByIdAusschreibung(int id) throws IllegalArgumentException {
		return this.ausschreibungMapper.findByIdAusschreibung(id);
	}

	public Ausschreibung findByNameAusschreibung(String bezeichnung) throws IllegalArgumentException {
		return this.ausschreibungMapper.findByNameAusschreibung(bezeichnung);
	}


	public Vector<Ausschreibung> findAusschreibungbyProjekt(Projekt projekt) throws IllegalArgumentException {
		return this.ausschreibungMapper.findByProjekt(projekt);
	}

	public ArrayList<Ausschreibung> getAlleAusschreibungen() throws IllegalArgumentException {

		return this.ausschreibungMapper.findAllAusschreibungen();
	}

	public Vector<Ausschreibung> getAusschreibungenForPartnerprofil(Organisationseinheit orga)
			throws IllegalArgumentException {

		EigenschaftMapper em = new EigenschaftMapper();
		PartnerprofilMapper pm = new PartnerprofilMapper();
		AusschreibungMapper am = new AusschreibungMapper();

		// Partnerprofil p = pm.findById(orga.getPartnerprofilId());

		Vector<Eigenschaft> nutzereigenschaften = em.findByPartnerprofil(pm.findById(orga.getPartnerprofilId()));
		ArrayList<Ausschreibung> alleAusschreibungen = am.findAllAusschreibungen();
		Vector<Ausschreibung> ausForPartnerprofil = new Vector<Ausschreibung>();

		for (Ausschreibung au : alleAusschreibungen) {
			Vector<Eigenschaft> ausEig = em.findByPartnerprofil(pm.findById(au.getPartnerprofilId()));

			// hier speichern wir die Eigenschaften aller Ausschreibungen ab.
			for (Eigenschaft eig : ausEig) {
				String eigenschaftAus = eig.getName();

				// Hier speichern wir die Eigenschaften des aktuellen Nutzers
				// ab.
				for (Eigenschaft unsereEigenschaft : nutzereigenschaften) {
					String eigenschaftUnsere = unsereEigenschaft.getName();

					// hier vergleichen wir die Eigenschaften des aktuellen
					// Nutzers mit denen der Auschreibungen.
					if (eigenschaftAus.equals(eigenschaftUnsere)) {
						ausForPartnerprofil.add(au);
					}
				}
			}

		}
		return ausForPartnerprofil;

	}

	// Methode um alle Ausschreibungen welche von einer bestimmten
	// Organisationseinheit erstellt wurden zu bekommen.
	// @author: Dominik Sasse

	public Vector<Ausschreibung> getAusschreibungenForOrga(Organisationseinheit orga) throws IllegalArgumentException {

		Vector<Ausschreibung> ergebnis = new Vector<Ausschreibung>();
		if (orga != null && this.ausschreibungMapper != null) {

			ArrayList<Ausschreibung> auss = ausschreibungMapper.findAllAusschreibungen();
			Vector<Projekt> p = ProjektMapper.projektMapper().findAll();

			for (Projekt pro : p) {
				// F�r alle Projekte mit der OrganisationseinheitsId XY...
				if (pro.getOrganisationseinheitId() == orga.getOrganisationseinheitId()) {
					for (Ausschreibung ausschreibung : auss) {
						// ...f�ge die Ausschreibungen dem ergebnis-Vektor
						// hinzu.
						if (pro.getProjektId() == ausschreibung.getProjektId()) {
							ergebnis.add(ausschreibung);
						}
					}
				}
			}

		}
		return ergebnis;
	}

	/*
	 * #########################################################################
	 * ABSCHNITT, Ende: Ausschreibung
	 * #########################################################################
	 * 
	 */

	/*
	 * #########################################################################
	 * ABSCHNITT, Beginn: Bewerbung
	 * #########################################################################
	 * 
	 */

	public Bewerbung createBewerbung(Bewerbung bewerbung, int ausschreibungId, int organisationsId)
			throws IllegalArgumentException {

		return this.bewerbungMapper.insert(bewerbung, ausschreibungId, organisationsId);

	}

	public void updateStatusOfBewerbung(String status, int bewerbungsID) throws IllegalArgumentException {
		this.bewerbungMapper.updateStatus(status, bewerbungsID);
	}

	public void deleteBewerbung(Bewerbung bewerbung) throws IllegalArgumentException {

		// Zuerst wird die zugehörige Bewertung gelöscht, sofern diese vorhanden
		// ist.
		Bewertung bewertung = bewertungMapper.findByBewerbung(bewerbung);

		if (bewertung != null) {
			this.deleteBewertung(bewertung);
		}
		// Die Bewerbung löschen
		this.bewerbungMapper.delete(bewerbung);
	}

	/**
	 * Gibt alle Bewerbungen zurück, die auf eine Ausschreibung eingegangen
	 * sind.
	 * 
	 * @param aus
	 * @return
	 */
	public Vector<Bewerbung> getBewerbungenOfAusschreibung(Ausschreibung aus)throws IllegalArgumentException {

		return this.bewerbungMapper.findByAusschreibung(aus);

	}
	
	public Vector<Bewerbung> getAllBewerbungen() throws IllegalArgumentException {
		
		return this.bewerbungMapper.findAll();
	}
	
	

	/**
	 * Die Methode gibt die Ausgangsbewerbungen einer Organisationseinheit
	 * zurück
	 * 
	 * @param o
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Vector<Bewerbung> findBewerbungOfOrganisationseinheit(Organisationseinheit o)
			throws IllegalArgumentException {
		return this.bewerbungMapper.findByOrganisationseinheit(o);
	}

	public Vector<Bewerbung> getAllBewerbungenOfUser(LoginInfo login) throws IllegalArgumentException {
		Organisationseinheit org = getOrganisationseinheitByUser(login);
		return findBewerbungOfOrganisationseinheit(org);
	}
	
	
	
	

	public Vector<Bewerbung> getAllBewerbungenOfLinkedTeamAndUnternehmen(LoginInfo login)
			throws IllegalArgumentException {
		Vector<Organisationseinheit> linkedOrgas = getLinkedTeamAndUnternehmenOfOrganisationseinheit(login);

		Vector<Bewerbung> bewerbungenOfLinkedOrgas = new Vector<>();

		for (Organisationseinheit o : linkedOrgas) {

			bewerbungenOfLinkedOrgas.addAll(findBewerbungOfOrganisationseinheit(o));

		}
		return bewerbungenOfLinkedOrgas;
	}
	
	
	

	/**
	 * Diese Methode gibt dem Nutzer alle (Eingangs-)Bewerbungen zurück, die auf
	 * Projekte eingegangen sind, für die er die Rolle des Projektleiters inne
	 * hat.
	 * 
	 * @param login
	 * @return
	 */
	public Vector<Bewerbung> getEingangsbewerbungenOfProjektleiter(LoginInfo login)throws IllegalArgumentException {
		Organisationseinheit org = getOrganisationseinheitByUser(login);
		Vector<Projekt> projekte = getAllProjekteOfProjektleiter(org);
		Vector<Ausschreibung> ausschreibungen = new Vector<>();
		Vector<Bewerbung> eingangsbewerbungen = new Vector<>();

		for (Projekt pro : projekte) {
			ausschreibungen.addAll(findAusschreibungbyProjekt(pro));
		}
		for (Ausschreibung aus : ausschreibungen) {
			eingangsbewerbungen.addAll(getBewerbungenOfAusschreibung(aus));
		}
		return eingangsbewerbungen;

	}


	public Vector<Bewerbung> getBewerbungForOrganisationseinheit(Organisationseinheit orga)
			throws IllegalArgumentException {

			Vector<Bewerbung> result = new Vector<Bewerbung>();

			if(orga != null && this.bewerbungMapper != null){
			Vector<Bewerbung> bewerbungen = this.bewerbungMapper.findAll();
		
			for(Bewerbung be :  bewerbungen){
				
				if(be.getOrganisationseinheitId() == orga.getOrganisationseinheitId()){
					result.add(be);
				}
			}
			}
			return result;

	}

	/*
	 * #########################################################################
	 * ABSCHNITT, Ende: Bewerbung
	 * #########################################################################
	 * 
	 */

	/*
	 * #########################################################################
	 * ABSCHNITT, Beginn: Bewertung
	 * #########################################################################
	 * 
	 */

	public Bewertung createBewertung(Bewertung bewertung)
			throws IllegalArgumentException {
		
		return this.bewertungMapper.insert(bewertung);
	}

	public void updateBewertung(Bewertung bewertung) throws IllegalArgumentException {
		this.bewertungMapper.update(bewertung);
	}

	public void deleteBewertung(Bewertung bewertung) {
		System.out.println("deleteBewertung");
		// Zuerst wird die zugehörige Beteiligung gelöscht, sofern diese
		// vorhanden ist.
		Beteiligung beteiligung = beteiligungMapper.findByBewertung(bewertung);
		if (beteiligung != null) {
			this.deleteBeteiligung(beteiligung);
		}
		// Die Bewertung löschen
		this.bewertungMapper.delete(bewertung);
	}

	/*
	 * #########################################################################
	 * ABSCHNITT, Ende: Bewertung
	 * #########################################################################
	 * 
	 */

	/*
	 * #########################################################################
	 * ABSCHNITT, Beginn: Beteiligung
	 * #########################################################################
	 * 
	 */

	public Beteiligung createBeteiligung(Date startdatum, Date enddatum, int personentage,
			int organisationseinheitId, int projektId, int bewertungId)
					throws IllegalArgumentException {

		Beteiligung beteiligung = new Beteiligung();
		beteiligung.setStartdatum(startdatum);
		beteiligung.setEnddatum(enddatum);
		beteiligung.setPersonentage(personentage);
		beteiligung.setOrganisationseinheitId(organisationseinheitId);
		beteiligung.setBewertungId(bewertungId);
		beteiligung.setProjektId(projektId);

		return this.beteiligungMapper.insertBeteiligung(beteiligung);
	}

	public void deleteBeteiligung(Beteiligung b) throws IllegalArgumentException {
		System.out.println("deleteBeteiligung");
		this.beteiligungMapper.delete(b);
	}

	public Vector<Beteiligung> getBeteiligungForOrga(Organisationseinheit orga) throws IllegalArgumentException {
		Vector<Beteiligung> result = new Vector<>();

		Vector<Beteiligung> beteiligungen = this.beteiligungMapper.findByOrganisationseinheit(orga);

		if (beteiligungen != null) {
			result.addAll(beteiligungen);

		}
		return result;
	}

	/*
	 * #########################################################################
	 * ABSCHNITT, Ende: Beteiligung
	 * #########################################################################
	 * 
	 */

	// TODO: Testmethode
	public String testMethode() {
		Organisationseinheit orgaEinheit = new Organisationseinheit();
		orgaEinheit = this.organisationseinheitMapper.findByKey(2);
		// String test = "Dies ist ein Test-String";
		return orgaEinheit.getName();
	}

	public Vector<Projektmarktplatz> testMethode2() {
		Projektmarktplatz test = new Projektmarktplatz();
		test.setName("test");
		test.setProjektmarktplatzId(1);
		Vector<Projektmarktplatz> vtest = new Vector<Projektmarktplatz>();
		vtest.add(test);
		return vtest;
	}

}
