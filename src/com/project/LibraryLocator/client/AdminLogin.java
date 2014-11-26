package com.project.LibraryLocator.client;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;

public class AdminLogin extends Button
{
	public AdminLogin(String html) { 
        super(html); 
} 

public void attach() { 
        this.onAttach(); 

        RootPanel.detachOnWindowClose(this); 
} 
	
//	private static Button adminbutton;
//	
//	  public AdminLogin(String nameofbutton){
//		 Button adminbutton = new Button(nameofbutton);
//	  }
//
//	  public void attach()
//	  {
//	    /* Widget.onAttach() is protected
//	     */
//	    onAttach();
//
//	    /* mandatory for all widgets without parent widget
//	     */
//	    RootPanel.detachOnWindowClose(this);
//	  }
//
//	public static void addClickHandler(ClickHandler clickHandler) {
//		// TODO Auto-generated method stub
//		adminbutton.addClickHandler(clickHandler);
//	}
	}