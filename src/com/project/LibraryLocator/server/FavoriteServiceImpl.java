package com.project.LibraryLocator.server;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.project.LibraryLocator.client.FavoriteService;

public class FavoriteServiceImpl extends RemoteServiceServlet implements
		FavoriteService {

	private static final Logger LOG = Logger
			.getLogger(FavoriteServiceImpl.class.getName());
	private static final PersistenceManagerFactory PMF = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	@Override
	public void addFavorite(String lid) {
		// TODO add multiple favorites
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.makePersistent(new Favorite(getUser(), lid));
		} finally {
			pm.close();
		}

	}

	@Override
	public void removeFavorite(String lid) {
		// TODO remove multiple favorites
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		try {
			long deleteCount = 0;
			Query q = pm.newQuery(Favorite.class, "user == u");
			q.declareParameters("com.google.appengine.api.users.User u");
			ArrayList<Favorite> favorites = (ArrayList<Favorite>) q.execute(getUser());
			for (Favorite fav : favorites) {
				if (lid.equals(fav.getId())) {
					deleteCount++;
					pm.deletePersistent(fav);
				}
			}
			if (deleteCount != 1) {
				LOG.log(Level.WARNING, "removeLibrary deleted" + deleteCount
						+ "Favorites");
			}

		} finally {
			pm.close();
		}

	}

	@Override
	public ArrayList<Favorite> getFavorite() {
		// TODO Test require
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		ArrayList<Favorite> favs = new ArrayList<Favorite>();
		try {
			Query q = pm.newQuery(Favorite.class, "user == u");
			q.declareParameters("com.google.appengine.api.users.User u");
			q.setOrdering("createDate");
			ArrayList<Favorite> favorites = (ArrayList<Favorite>) q.execute(getUser());
			for (Favorite fav : favorites) {
				favs.add(fav);
			}
		} finally {
			pm.close();
		}    
		return favs;
	}

	private void checkLoggedIn() {
		// TODO Auto-generated method stub
		if (getUser() == null) {
			//throw new NotLoggedInException("Not logged in.");
		}

	}

	private User getUser() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.getCurrentUser();
	}

	private PersistenceManager getPersistenceManager() {
		return PMF.getPersistenceManager();
	}

}
