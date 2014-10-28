package com.project.LibraryLocator.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.project.LibraryLocator.shared.Library;

public interface DataParseAsync {
	public void parseLibrary(AsyncCallback<ArrayList<Library>> async);

	public void parseAll(AsyncCallback<Void> async);
}
