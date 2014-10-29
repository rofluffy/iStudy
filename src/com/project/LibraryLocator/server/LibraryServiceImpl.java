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
//	private DataParseImpl parse = new DataParseImpl();
//	private ArrayList<Library> allLibrary =parse.parseLibrary();
//	private static final Logger LOG = Logger.getLogger(LibraryServiceImpl.class.getName());
//	  private static final PersistenceManagerFactory PMF =
//	      JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	public ArrayList<Library> loAllLibraries = new ArrayList<Library>();
	

	@Override
	public void addLibrary(String lid) {
//		PersistenceManager pm = getPersistenceManager();
//		Library lib = null; 
//		for(Library l: allLibrary){
//			if(l.getId() == lid){
//				lib = l;
//				break;
//			}			
//		}
//	    try {
//	      pm.makePersistent(lib);
//	    } finally {
//	      pm.close();
//	    }
	    
	}

	@Override
	public void removeLibrary(String lid) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Library> getLibraries() {
		
		DataParseImpl dataParse = new DataParseImpl();
		dataParse.parseAll();
		loAllLibraries = dataParse.parseLibrary();
		System.out.println("list of all libraries:" + loAllLibraries);
		return loAllLibraries;
	
	}
	
	public void populateTable(){
		
		// TODO Auto-generated method stub
//		System.out.println("getLibraries is runing");
//<<<<<<< HEAD
//		DataParseImpl dataParse = new DataParseImpl();
//		dataParse.parseAll();
//		loAllLibraries = dataParse.parseLibrary();
//		System.out.println("list of all libraries:" + loAllLibraries);
//		return loAllLibraries;
//=======
//		new DataParseImpl().parseAll();
//		System.out.println("list of all libraries:" + allLibrary);
//		//return allLibrary;
//>>>>>>> 5313e767dc03927af21b084c101fc9732bb51ff4
		
		
	}
//	private PersistenceManager getPersistenceManager() {
//	    return PMF.getPersistenceManager();
//	  }

}
