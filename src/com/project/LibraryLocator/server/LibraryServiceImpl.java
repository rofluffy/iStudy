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
// TODO NotLoggedInException
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.project.LibraryLocator.client.LibraryService;
import com.project.LibraryLocator.shared.Library;


public class LibraryServiceImpl extends RemoteServiceServlet implements LibraryService{
	private DataParseImpl parse = new DataParseImpl();
	private ArrayList<Library> allLibrary =parse.parseLibrary();
	private static final Logger LOG = Logger.getLogger(LibraryServiceImpl.class.getName());
	  private static final PersistenceManagerFactory PMF =
	      JDOHelper.getPersistenceManagerFactory("transactions-optional");
	

	@Override
	public void addLibrary(String lid) {
		PersistenceManager pm = getPersistenceManager();
		Library lib = null; 
		for(Library l: allLibrary){
			if(l.getId() == lid){
				lib = l;
				break;
			}			
		}
	    try {
	      pm.makePersistent(lib);
	    } finally {
	      pm.close();
	    }
	    
	}

	@Override
	public void removeLibrary(String lid) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Library> getLibraries() {
		PersistenceManager pm = getPersistenceManager();
	    List<String> id = new ArrayList<String>();
	    try {
	      Query q = pm.newQuery(Library.class, "user == u");
	      q.declareParameters("com.google.appengine.api.users.User u");
	      q.setOrdering("createDate");
	      for (Library l : allLibrary) {
	        id.add(l.getId());
	      }
	    } finally {
	      pm.close();
	    }
	    return allLibrary;
	}
	
	public void populateTable(){
		
		// TODO Auto-generated method stub
		System.out.println("getLibraries is runing");
		new DataParseImpl().parseAll();
		System.out.println("list of all libraries:" + allLibrary);
		//return allLibrary;
		
		
	}
	private PersistenceManager getPersistenceManager() {
	    return PMF.getPersistenceManager();
	  }

}
