package de.hdm.gruppe1.Project4u.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;






@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {
  public LoginInfo login(String requestUri);
}