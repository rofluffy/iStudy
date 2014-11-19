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
import com.project.LibraryLocator.client.NotLoggedInException;

@SuppressWarnings("serial")
public class FavoriteServiceImpl extends RemoteServiceServlet implements
		FavoriteService {

	private static final Logger LOG = Logger
			.getLogger(FavoriteServiceImpl.class.getName());
	private static final PersistenceManagerFactory PMF = JDOHelper
			.getPersistenceManagerFactory("transactions-optional"); 

	private void addFavorite(String id) throws NotLoggedInException{
		// TODO add multiple favorites
		System.out.println("add favorite is running");
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.makePersistent(new Favorite(getUser(), id));
		} finally {
			pm.close();
		}

	}
	
	@Override
	public void addFavorites(ArrayList<String> loid) throws NotLoggedInException{
		System.out.println("add favorites is running");
		for(String id : loid){
			addFavorite(id);
		}
	}

	@SuppressWarnings("unchecked")
	private void removeFavorite(String id) throws NotLoggedInException{
		// TODO remove multiple favorites
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		try {
			long deleteCount = 0;
			Query q = pm.newQuery(Favorite.class, "user == u");
			q.declareParameters("com.google.appengine.api.users.User u");
			List<Favorite> favorites = (List<Favorite>) q.execute(getUser());
			for (Favorite fav : favorites) {
				if (id.equals(fav.getId())) {
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
	public void removeFavorites(ArrayList<String> loid) throws NotLoggedInException{
		System.out.println("remove favorites is running");
		for(String id : loid){
			removeFavorite(id);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<String> getFavorite() throws NotLoggedInException{
		// TODO Test require
		System.out.println("get favorite obj is running");
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		List<String> loid = new ArrayList<String>();
		try {
			Query q = pm.newQuery(Favorite.class, "user == u");
			q.declareParameters("com.google.appengine.api.users.User u");
			q.setOrdering("createDate");
			List<Favorite> favorites = (List<Favorite>) q.execute(getUser());
			for (Favorite fav : favorites) {
				loid.add(fav.getId());
			}
		} finally {
			pm.close();
		}
		return (ArrayList<String>) loid;
	}

	private void checkLoggedIn() throws NotLoggedInException{
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
