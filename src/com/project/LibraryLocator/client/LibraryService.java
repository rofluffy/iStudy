package com.project.LibraryLocator.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.project.LibraryLocator.shared.Library;

@RemoteServiceRelativePath("Library")
public interface LibraryService extends RemoteService{
	// TODO !needs to decided we use the id to remove or the whole library object
public void addLibrary(String lid) /* TODO throws NotLoggedInException*/;
public void removeLibrary(String lid) /* TODO throws NotLoggedInException*/;
public ArrayList<Library> getLibraries() /* TODO throws NotLoggedInException*/;
}
