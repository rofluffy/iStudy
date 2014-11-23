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
	private static final PersistenceManagerFactory PMF = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	private ArrayList<Library> loAllLibraries = new ArrayList<Library>();
	//public ArrayList<Library> Libraries = new ArrayList<Library>();

	// TODO store is so every user can acess it from the app engine, so we only
	// need to run it once
	@Override
	public void addLibrary(Library lb) {
		PersistenceManager pm = getPersistenceManager();
		try{
			pm.makePersistent(new Library(lb.getId(), lb.getName(), lb.getBranch(), lb.getPhone(), lb.getAddress(), lb.getCity(), lb.getPostalCode(), lb.getLat(), lb.getLon()));
		} finally {
			pm.close();
		}
	}

	@Override
	public void removeLibrary(String lid) {
		// TODO Auto-generated method stub
		PersistenceManager pm = getPersistenceManager();
		try{
			Query q= pm.newQuery(Library.class, "id == lid");
			List<Library> lb= (List<Library>) q.execute();
			for(Library l :loAllLibraries){
				if(l.getId()==lid)
					pm.deletePersistent(lb);
			}
		}finally {
			pm.close();
		}

	}

	private ArrayList<Library> getLibrariesFromParse() {
		//System.out.println("getLibraries is runing(Parse)");
		DataParseImpl dataParse = new DataParseImpl();
		dataParse.parseAll();
		loAllLibraries = dataParse.parseLibrary();

		//System.out.println("list of all libraries:" + loAllLibraries);
		return loAllLibraries;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Library> getLibraries() {
		System.out.println("try running get Libraries");
		PersistenceManager pm = getPersistenceManager();
		ArrayList<Library> libraries = new ArrayList<Library>();
		try {
			Query q = pm.newQuery(Library.class);
			//System.out.println("see what q.execute does:" + q.execute());
			List<Library> allLib = (List<Library>) q.execute();
			//System.out.println("getLibraries from data:" + allLib);
			for (Library lb: allLib) {
				libraries.add(lb);
			}
		} finally {
			pm.close();
		}
		return libraries;
		
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
