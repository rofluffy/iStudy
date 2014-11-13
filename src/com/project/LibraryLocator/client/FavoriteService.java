package com.project.LibraryLocator.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.project.LibraryLocator.server.Favorite;
import com.project.LibraryLocator.shared.FavoriteObj;

@RemoteServiceRelativePath("Favorite")
public interface FavoriteService extends RemoteService {
	
	public void addFavorites(ArrayList<FavoriteObj> favs);
	public void removeFavorites(ArrayList<FavoriteObj> favs);
	public ArrayList<FavoriteObj> getFavorite();

}
