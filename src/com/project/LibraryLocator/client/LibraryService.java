package com.project.LibraryLocator.client;

import java.util.ArrayList;

import javax.jdo.Query;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.project.LibraryLocator.shared.Library;

@RemoteServiceRelativePath("Library")
public interface LibraryService extends RemoteService {
	/* this is the service interface*/

	//ArrayList<Library> loAllLibraries = new ArrayList<Library>();
	
	public void addLibrary(String lid) /* TODO throws NotLoggedInException */;

	public void removeLibrary(String lid) /* TODO throws NotLoggedInException */;

	public ArrayList<Library> getLibraries() /* TODO throws NotLoggedInException */;

	public void populateTable();// populate table
}
