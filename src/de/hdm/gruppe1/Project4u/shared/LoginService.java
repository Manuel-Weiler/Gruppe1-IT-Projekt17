package de.hdm.gruppe1.Project4u.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.gruppe1.Project4u.shared.bo.Nutzer;


@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {
  public Nutzer login(String requestUri);
}
