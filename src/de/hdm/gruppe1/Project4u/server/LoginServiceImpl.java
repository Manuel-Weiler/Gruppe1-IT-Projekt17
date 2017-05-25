package de.hdm.gruppe1.Project4u.server;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe1.Project4u.shared.LoginInfo;
import de.hdm.gruppe1.Project4u.shared.LoginService;
//import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;
/**
 * Implementierungsklasse des Interfaces LoginService
 * @see LoginService
 * @see LoginServiceAsync
 * @see RemoteServiceServlet
 */
@SuppressWarnings("serial")
public class LoginServiceImpl extends RemoteServiceServlet implements
    LoginService {

	/**
	 * Diese Methode fuert den Login aus und ruft die Daten von der Google
	 * Accounts API ab.
	 * @param requestUri 
	 * @return loginInfos
	 */
	    //Organisationseinheit
  public LoginInfo login(String requestUri) {
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    LoginInfo loginInfo = new LoginInfo();

    if (user != null) {
      loginInfo.setLoggedIn(true);
      loginInfo.setEmailAddress(user.getEmail());    //googleId
	  loginInfo.setNickname(user.getNickname());
      loginInfo.setLogoutUrl(userService.createLogoutURL(requestUri));
    } else {
      loginInfo.setLoggedIn(false);
      loginInfo.setLoginUrl(userService.createLoginURL(requestUri));
    }
    return loginInfo;
  }

}
