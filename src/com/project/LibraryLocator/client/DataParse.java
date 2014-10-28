package com.project.LibraryLocator.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.project.LibraryLocator.shared.Library;

//@RemoteServiceRelativePath("Library") TODO
public interface DataParse extends RemoteService {
	public ArrayList<Library> parseLibrary() /* TODO throws NotLoggedInException */;

	public void parseAll()/* TODO throws NotLoggedInException */;
}
