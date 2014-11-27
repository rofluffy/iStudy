package com.project.LibraryLocator.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.project.LibraryLocator.shared.Library;

@RemoteServiceRelativePath("Library")
public interface LibraryService extends RemoteService {
	/* this is the service interface*/

	//ArrayList<Library> loAllLibraries = new ArrayList<Library>();
	
	public void addLibrary(Library lb);

	public void removeLibrary(ArrayList<Library> lb);

	public ArrayList<Library> getLibraries();

	public void populateTable();// populate table
}
