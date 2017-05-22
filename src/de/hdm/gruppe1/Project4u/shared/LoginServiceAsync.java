package de.hdm.gruppe1.Project4u.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

//import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;

public interface LoginServiceAsync {
	public void login(String requestUri, AsyncCallback<LoginInfo> async);

}
