package com.project.LibraryLocator.client;


import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AdminServiceAsync {
	
	  void submitMatch(String submit, AsyncCallback<Boolean> callback);
	  void addAdmin(AsyncCallback<Void> callback);
	  void logout(AsyncCallback<Void> callback);

}
