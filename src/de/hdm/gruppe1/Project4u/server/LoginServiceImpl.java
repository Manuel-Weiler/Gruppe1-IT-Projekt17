package de.hdm.gruppe1.Project4u.server;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe1.Project4u.server.db.OrganisationseinheitMapper;
import de.hdm.gruppe1.Project4u.shared.LoginService;
import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;


public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 	@param Domain der Startseite
	 * 	@return neues oder eingeloggtes Profil
	 */
	@SuppressWarnings("deprecation")
	public Organisationseinheit login(String requestUri) throws Exception {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		Organisationseinheit o = new Organisationseinheit();
		
		if (user != null) {
			//EXISTING PROFILE
			Organisationseinheit bestehendesProfil = OrganisationseinheitMapper.organisationseinheitMapper().
					getOrganisationseinheitByGoogleId(user.getEmail());
			
			if(bestehendesProfil.getGoogleId() != null){
				bestehendesProfil.setLoggedIn(true);
				bestehendesProfil.setLogoutUrl(userService.createLogoutURL(requestUri));
				bestehendesProfil.setGoogleId(user.getEmail());
				return bestehendesProfil;
			}
			//if (bestehendesProfil == null && user != null)
			o.setLoggedIn(true);
			o.setGoogleId(user.getEmail());
			o.setLogoutUrl(userService.createLogoutURL(requestUri));
			OrganisationseinheitMapper.organisationseinheitMapper().insert(o);
		//if(user == null)
		}else{
			o.setLoggedIn(false);
		}
		o.setLoginUrl(userService.createLoginURL(requestUri));
		return o;
	}
	
}





//package de.hdm.gruppe1.Project4u.server;
//
//import com.google.appengine.api.users.User;
//import com.google.appengine.api.users.UserService;
//import com.google.appengine.api.users.UserServiceFactory;
//
//import com.google.gwt.user.server.rpc.RemoteServiceServlet;
//
//import de.hdm.gruppe1.Project4u.shared.LoginService;
//import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
///**
// * Implementierungsklasse des Interfaces LoginService
// * @see LoginService
// * @see LoginServiceAsync
// * @see RemoteServiceServlet
// */
//@SuppressWarnings("serial")
//public class LoginServiceImpl extends RemoteServiceServlet implements
//    LoginService {
//
//	/**
//	 * Diese Methode fuert den Login aus und ruft die Daten von der Google
//	 * Accounts API ab.
//	 * @param requestUri 
////	 * @return loginInfos
//	 */
//  public Organisationseinheit login(String requestUri) {
//    UserService userService = UserServiceFactory.getUserService();
//    User user = userService.getCurrentUser();
//    Organisationseinheit loginInfo = new Organisationseinheit();
//
//    if (user != null) {
//      loginInfo.setLoggedIn(true);
//      loginInfo.setGoogleId(user.getEmail());
//      loginInfo.setLogoutUrl(userService.createLogoutURL(requestUri));
//    } else {
//      loginInfo.setLoggedIn(false);
//      loginInfo.setLoginUrl(userService.createLoginURL(requestUri));
//    }
//    return loginInfo;
//  }
//
//}
