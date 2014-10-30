package com.project.LibraryLocator.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.project.LibraryLocator.server.Favorite;

public interface FavoriteServiceAsync {

	void addFavorite(String lid, AsyncCallback<Void> callback);
	
	void removeFavorite(String lid, AsyncCallback<Void> callback);

	void getFavorite(AsyncCallback<ArrayList<Favorite>> callback);

}
