package de.hdm.gruppe1.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.gruppe1.shared.bo.Nutzer;

public interface LoginServiceAsync {
	public void login(String requestUri, AsyncCallback<Nutzer> async);

}
