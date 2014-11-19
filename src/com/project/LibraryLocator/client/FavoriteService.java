package com.project.LibraryLocator.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.project.LibraryLocator.server.Favorite;

@RemoteServiceRelativePath("Favorite")
public interface FavoriteService extends RemoteService {
	
	public void addFavorites(ArrayList<String> loid) throws NotLoggedInException;
	public void removeFavorites(ArrayList<String> loid) throws NotLoggedInException;
	public ArrayList<String> getFavorite() throws NotLoggedInException;

}
