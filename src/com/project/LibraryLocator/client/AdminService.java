package com.project.LibraryLocator.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("Admin")
public interface AdminService extends RemoteService {
	
   Boolean submitMatch(String submit);
   void addAdmin();
   void logout();
}
