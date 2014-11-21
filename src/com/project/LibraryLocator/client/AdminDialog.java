package com.project.LibraryLocator.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AdminDialog extends DialogBox {
	

	/**
     * Center dialog relative to RootPanel
     * unfortunately works only when dialog is visible...
     * because when dialog is hidden this.getOffsetWidth() returns 0
     */
	@Override
    public void center() {

      this.setPopupPosition(
          9*(RootPanel.get().getOffsetWidth() -this.getOffsetWidth())/100,
          (RootPanel.get().getOffsetHeight()-this.getOffsetHeight())/2
      );

}
    
  
}
