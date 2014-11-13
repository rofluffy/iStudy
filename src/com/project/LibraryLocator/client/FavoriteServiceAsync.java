package com.project.LibraryLocator.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.project.LibraryLocator.server.Favorite;
import com.project.LibraryLocator.shared.FavoriteObj;

public interface FavoriteServiceAsync {

	void addFavorites(ArrayList<FavoriteObj> favs, AsyncCallback<Void> callback);
	
	void removeFavorites(ArrayList<FavoriteObj> favs, AsyncCallback<Void> callback);

	void getFavorite(AsyncCallback<ArrayList<FavoriteObj>> callback);

}
