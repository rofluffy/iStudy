package com.project.LibraryLocator.client;

import java.io.Serializable;

import com.google.gwt.user.client.Window;

@SuppressWarnings("serial")
public class NotLoggedInException extends Exception implements Serializable {

	public NotLoggedInException() {
	    super();
	  }

	  public NotLoggedInException(String message) {
	    super(message);
	    //Window.alert("Please Log in to use favorite function");
	  }

}
