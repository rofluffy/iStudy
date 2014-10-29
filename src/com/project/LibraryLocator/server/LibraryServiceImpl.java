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

public class LibraryServiceImpl extends RemoteServiceServlet implements LibraryService {
	/* this is the service servlet*/

	public ArrayList<Library> loAllLibraries = new ArrayList<Library>();
	
	@Override
	public void addLibrary(String lid) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeLibrary(String lid) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Library> getLibraries() {
		// TODO Auto-generated method stub
		System.out.println("getLibraries is runing");
		new DataParseImpl().parseAll();
		System.out.println("list of all libraries:" + loAllLibraries);
		return loAllLibraries;
		
	}

	@Override
	public void populateTable() {
		// TODO get arraylist from parser and put into the table
		System.out.println("populateTable is runing");
		DataParseImpl parseData = new DataParseImpl();
		parseData.parseAll();
		loAllLibraries = parseData.parseLibrary();
		System.out.println("list of all libraries:" + loAllLibraries);
		
	}

}
