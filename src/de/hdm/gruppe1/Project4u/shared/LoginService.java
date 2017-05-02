package de.hdm.gruppe1.Project4u.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.gruppe1.Project4u.shared.bo.Organisationseinheit;


@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {
  public Organisationseinheit login(String requestUri);
}
