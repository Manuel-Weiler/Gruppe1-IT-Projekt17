package de.hdm.gruppe1.Project4u.server;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe1.Project4u.server.db.OrganisationseinheitMapper;
import de.hdm.gruppe1.Project4u.shared.LoginService;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;


/**
 * Implementierungsklasse des Interfaces LoginService
 * @see LoginService
 * @see LoginServiceAsync
 * @see RemoteServiceServlet
 */
@SuppressWarnings("serial")
public class LoginServiceImpl extends RemoteServiceServlet implements
    LoginService {



	private static final long serialVersionUID = 1L;
	
	/**
	 * 	@param Domain der Startseite
	 * 	@return neues oder eingeloggtes Profil
	 */

	public Organisationseinheit login(String requestUri) throws Exception {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
	 

		Organisationseinheit o = new Organisationseinheit();
		
		if (user != null) {
			Organisationseinheit bestehendesProfil = OrganisationseinheitMapper.organisationseinheitMapper().
					getOrganisationseinheitByGoogleId(user.getEmail());
			
			if(bestehendesProfil.getGoogleId() != null){
				bestehendesProfil.setLoggedIn(true);
				bestehendesProfil.setLogoutUrl(userService.createLogoutURL(requestUri));
				bestehendesProfil.setGoogleId(user.getEmail());
				return bestehendesProfil;
			}
 			o.setLoggedIn(true);
			o.setGoogleId(user.getEmail());
			o.setLogoutUrl(userService.createLogoutURL(requestUri));
			OrganisationseinheitMapper.organisationseinheitMapper().insert(o);
				}else{
			o.setLoggedIn(false);
		}
		o.setLoginUrl(userService.createLoginURL(requestUri));
		return o;
	}
	

}



