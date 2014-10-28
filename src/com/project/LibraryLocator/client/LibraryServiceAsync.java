package com.project.LibraryLocator.client;

import java.util.ArrayList;
import com.project.LibraryLocator.shared.Library;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LibraryServiceAsync {
	public void addLibrary(String lid, AsyncCallback<Void> async);
	public void removeLibrary(String lid, AsyncCallback<Void> async);
	public void getLibraries(AsyncCallback<ArrayList<Library>> async) ;
	public void  populateTable(AsyncCallback<Void> async);
}
