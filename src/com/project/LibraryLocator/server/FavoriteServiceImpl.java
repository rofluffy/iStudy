package com.project.LibraryLocator.server;

import java.util.ArrayList;
import java.util.List;
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
import com.project.LibraryLocator.shared.FavoriteObj;

@SuppressWarnings("serial")
public class FavoriteServiceImpl extends RemoteServiceServlet implements
		FavoriteService {

	private static final Logger LOG = Logger
			.getLogger(FavoriteServiceImpl.class.getName());
	private static final PersistenceManagerFactory PMF = JDOHelper
			.getPersistenceManagerFactory("transactions-optional"); 

	private void addFavorite(FavoriteObj fobj) {
		// TODO add multiple favorites
		System.out.println("add favorite is running");
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.makePersistent(new Favorite(getUser(), fobj.getId(), fobj.getName(), fobj.getBranch()));
		} finally {
			pm.close();
		}

	}
	
	@Override
	public void addFavorites(ArrayList<FavoriteObj> favs) {
		System.out.println("add favorites is running");
		for(FavoriteObj f : favs){
			addFavorite(f);
		}
	}

	@SuppressWarnings("unchecked")
	private void removeFavorite(FavoriteObj f) {
		// TODO remove multiple favorites
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		try {
			long deleteCount = 0;
			Query q = pm.newQuery(Favorite.class, "user == u");
			q.declareParameters("com.google.appengine.api.users.User u");
			List<Favorite> favorites = (List<Favorite>) q.execute(getUser());
			for (Favorite fav : favorites) {
				if ((f.getId()).equals(fav.getId())) {
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
	public void removeFavorites(ArrayList<FavoriteObj> favs) {
		System.out.println("remove favorites is running");
		for(FavoriteObj f : favs){
			removeFavorite(f);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<FavoriteObj> getFavorite() {
		// TODO Test require
		System.out.println("get favorite obj is running");
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		List<FavoriteObj> favs = new ArrayList<FavoriteObj>();
		try {
			Query q = pm.newQuery(Favorite.class, "user == u");
			q.declareParameters("com.google.appengine.api.users.User u");
			q.setOrdering("createDate");
			List<Favorite> favorites = (List<Favorite>) q.execute(getUser());
			for (Favorite fav : favorites) {
				FavoriteObj fobj = new FavoriteObj(fav.getId(), fav.getName(), fav.getBranch());
				favs.add(fobj);
			}
		} finally {
			pm.close();
		}
		return (ArrayList<FavoriteObj>) favs;
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
