package com.project.LibraryLocator.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.project.LibraryLocator.shared.Library;

public abstract class TabFactory {
	// Service classes
	protected final LibraryServiceAsync libraryService = GWT
			.create(LibraryService.class);
	protected final FavoriteServiceAsync favoriteService = GWT
			.create(FavoriteService.class);
	ArrayList<Library> libraries;
	ArrayList<Library> selectedLb;

	public TabFactory() {
		// TODO attributes: libraries list, selected list,
	}
	
	// Build tab
	abstract void buildTab();
	// Build tables
	abstract void buildTable();
	// Build function
	abstract void buildFunc();
	// display Libraries
	abstract void displayLibrary(ArrayList<Library> lolb);
	// display Library
	abstract void displayLibrary(final Library lb);
	
	void cleanTable(FlexTable table){
		int row = table.getRowCount();
		for (int i = row - 1; i >= 1; i--) {
			table.removeRow(i);
		}
		// while (librariesFlexTable.getRowCount() > 1) {
		// librariesFlexTable.removeRow(librariesFlexTable.getRowCount()-1);
		// }
	}
	
	void handleError(Throwable error) {
		Window.alert(error.getMessage());
		if (error instanceof NotLoggedInException) {
			Window.Location.replace(LibraryLocator.loginInfo.getLogoutUrl());
		}
	}

}
