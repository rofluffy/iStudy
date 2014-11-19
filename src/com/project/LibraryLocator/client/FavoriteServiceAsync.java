package com.project.LibraryLocator.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.project.LibraryLocator.server.Favorite;

public interface FavoriteServiceAsync {

	void addFavorites(ArrayList<String> loid, AsyncCallback<Void> callback);
	
	void removeFavorites(ArrayList<String> loid, AsyncCallback<Void> callback);

	void getFavorite(AsyncCallback<ArrayList<String>> callback);

}
