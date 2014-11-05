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
import com.google.gwt.storage.client.Storage;
// TODO NotLoggedInException
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.project.LibraryLocator.client.LibraryService;
import com.project.LibraryLocator.shared.Library;

public class LibraryServiceImpl extends RemoteServiceServlet implements
		LibraryService {
	// private static final Logger LOG =
	// Logger.getLogger(LibraryServiceImpl.class.getName());
	 private static final PersistenceManagerFactory PMF =
	 JDOHelper.getPersistenceManagerFactory("transactions-optional");
	 private Storage libraryStore=null;


	public ArrayList<Library> loAllLibraries = new ArrayList<Library>();

	//TODO store is so every user can acess it from the app engine, so we only need to run it once 
	@Override
	public void addLibrary(String lid) {
		// PersistenceManager pm = getPersistenceManager();
		// Library lib = null;
		// for(Library l: allLibrary){
		// if(l.getId() == lid){
		// lib = l;
		// break;
		// }
		// }
		// try {
		// pm.makePersistent(lib);
		// } finally {
		// pm.close();
		// }

	}

	@Override
	public void removeLibrary(String lid) {
		// TODO Auto-generated method stub

	}

	
	private ArrayList<Library> getLibrariesFromParse() {
		System.out.println("getLibraries is runing");
		DataParseImpl dataParse = new DataParseImpl();
		dataParse.parseAll();
		loAllLibraries = dataParse.parseLibrary();
		System.out.println("list of all libraries:" + loAllLibraries);
		return loAllLibraries;
	}
	
	@Override
	public ArrayList<Library> getLibraries() {
	PersistenceManager pm = getPersistenceManager();
	Query q = pm.newQuery(Library.class);
	ArrayList<Library> allLib = (ArrayList<Library>) q.execute();
	return allLib;
	}
	

	public void populateTable() {
		getLibrariesFromParse();
		PersistenceManager pm = getPersistenceManager();
		pm.makePersistentAll(loAllLibraries);
	   
	}

	 private PersistenceManager getPersistenceManager() {
	 return PMF.getPersistenceManager();
	 }

}
