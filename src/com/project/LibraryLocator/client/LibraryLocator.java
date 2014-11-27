package com.project.LibraryLocator.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.project.LibraryLocator.shared.Library;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.maps.gwt.client.Marker;
import com.google.maps.gwt.client.MarkerOptions;
import com.google.gwt.dom.client.Document;
import com.google.maps.gwt.client.ControlPosition;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.InfoWindow;
import com.google.maps.gwt.client.InfoWindowOptions;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;
import com.google.maps.gwt.client.Marker.DblClickHandler;
import com.google.maps.gwt.client.MouseEvent;
import com.google.maps.gwt.client.PanControlOptions;
import com.google.maps.gwt.client.StreetViewControlOptions;
import com.google.maps.gwt.client.ZoomControlOptions;

//
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class LibraryLocator implements EntryPoint {

	ArrayList<Library> adminSelectedLb = new ArrayList<Library>();
	// panel for login and logout
	static LoginInfo loginInfo = new LoginInfo();
	// panel for login and logout
	private VerticalPanel loginPanel = new VerticalPanel();
	private VerticalPanel logoutPanel = new VerticalPanel();
	// label and link for login
	private Label loginLabel = new Label(
			"Please sign in to your Google Account to access your Favorite library list.");
	private Anchor signInLink = new Anchor("Sign In");
	// label and link for logout
	private Label logoutLabel = new Label(
			"Logout here if you want to stop using this amazing app, but seriously, why would you?");
	private Anchor signOutLink = new Anchor("Sign Out");
	private TabPanel mainTab = new TabPanel();
	private TabPanel mainAdminTab = new TabPanel();

	// search
	private VerticalPanel searchTab = new VerticalPanel();
	private HorizontalPanel searchPanel = new HorizontalPanel();
	private TextBox searchInputBox = new TextBox();
	private FlexTable librariesFlexTable = new FlexTable();
	private ListBox searchBox = new ListBox();
	private Label numLb = new Label("");
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Button searchButton = new Button("Search");
	private Button checkAllButton = new Button("Check All");
	private Button toMapButton = new Button("To Map");
	private Button addFavoriteButton = new Button("Add Favorite");

	// fav
	private VerticalPanel favoriteTab = new VerticalPanel();
	private FlexTable favoriteTable = new FlexTable();
	private Label removeFavLabel = new Label();
	private HorizontalPanel buttonPanelfav = new HorizontalPanel();
	private Button removeFavorite = new Button("Remove");
	private Button checkAllButtonFav = new Button("Check All");
	private Button toMapButtonFav = new Button("To Map");

	// admin
	private AdminLogin adminlogin = new AdminLogin("Admin");
	private Button adminSubmit = new Button("Login as admin");
	private VerticalPanel adminTab = new VerticalPanel();
	private VerticalPanel adminLoginPanel = new VerticalPanel();
	private HorizontalPanel addLibraryPanel = new HorizontalPanel();
	private TextBox inputAdmin = new TextBox();
	private TextBox inputLibraryID = new TextBox();
	private TextBox inputLibraryName = new TextBox();
	private TextBox inputLibraryBranch = new TextBox();
	private TextBox inputLibraryPhone = new TextBox();
	private TextBox inputLibraryAddress = new TextBox();
	private TextBox inputLibraryCity = new TextBox();
	private TextBox inputLibraryPostCode = new TextBox();
	private TextBox inputLibraryLat = new TextBox();
	private TextBox inputLibraryLon = new TextBox();
	private FlexTable addLibraryTable = new FlexTable();
	private FlexTable allLibraries = new FlexTable();
	private HorizontalPanel pagePanel = new HorizontalPanel();
	private Label pageMessage = new Label();
	private Button addLibraryButton = new Button("Add");
	private Button removeLibraryButton = new Button("Remove");
	private Button loadLibraryButton = new Button("Load Libraries");
	private Button adminCheckAll = new Button("Check All");
	private Button adminToMap = new Button("To Map");
	private Button allToMap = new Button("ALL TO MAP");
	private Button adminlogoutButton = new Button("logout");
	private int pageIndex = 0;
	private int pageSize = 20;
	private ArrayList<Library> subListLb = new ArrayList<Library>();
	private Button prev = new Button("<<<");
	private Button next = new Button(">>>");
	private ArrayList<Button> loPage = new ArrayList<Button>();
	private Label adminlabel = new Label();

	// map
	static GoogleMap map;
	private InfoWindow infowindow = InfoWindow.create();
	static InfoWindowOptions infowindowOpts = InfoWindowOptions.create();
	private ArrayList<Marker> markers = new ArrayList<Marker>();

	// service class
	private LibraryServiceAsync libraryService = GWT
			.create(LibraryService.class);
	private FavoriteServiceAsync favoriteService = GWT
			.create(FavoriteService.class);
	private AdminServiceAsync AdminService = GWT.create(AdminService.class);

	// list
	private ArrayList<Library> libraries = new ArrayList<Library>(); // list of library object
	private ArrayList<Library> selectedLb = new ArrayList<Library>(); 
	private ArrayList<Library> searchLb = new ArrayList<Library>();
	private ArrayList<Library> favorites = new ArrayList<Library>(); 
	private Set<Library> clientFav = new HashSet<Library>();
	private ArrayList<Library> selectedFav = new ArrayList<Library>();
	private ArrayList<CheckBox> libCheckBox = new ArrayList<CheckBox>();
	private ArrayList<CheckBox> favCheckBox = new ArrayList<CheckBox>();

	private long start;
	private boolean isFavWorking = true;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		adminLoginPanel.insert(inputAdmin, 0);
		adminLoginPanel.insert(adminSubmit, 1);
		adminLoginPanel.insert(adminlabel, 2);
		mainAdminTab.add(adminLoginPanel, "Welcome");

		final ScrollPanel DataPanel = new ScrollPanel(adminTab);
		DataPanel.setHeight("500px");
		DataPanel.setVisible(false);
		mainAdminTab.add(DataPanel, "Database");
		mainAdminTab.selectTab(0);

		mainAdminTab.getTabBar().setVisible(false);
		searchButton.addStyleName("green");
		checkAllButton.addStyleName("maroon");
		toMapButton.addStyleName("orange");
		addFavoriteButton.addStyleName("submit");
		adminlogin.addStyleName("submit");
		adminSubmit.addStyleName("submitt");

		removeFavorite.addStyleName("submit");
		checkAllButtonFav.addStyleName("orange");
		toMapButtonFav.addStyleName("maroon");

		addAdmin();
		adminSubmit.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String entered = inputAdmin.getText();
				inputAdmin.setText("");
				AdminService.submitMatch(entered, new AsyncCallback<Boolean>() {
					@Override
					public void onFailure(Throwable error) {
						System.out.println("submit fails");
						error.printStackTrace();

					}

					@Override
					public void onSuccess(Boolean result) {
						System.out.println(result.toString());

						if (result) {
							mainAdminTab.getTabBar().setVisible(true);
							System.out.println("database added");
							adminLoginPanel.clear();
							adminLoginPanel.add(adminlogoutButton);
							adminlabel.setText("");
						} else {
							adminlabel.setText("Access denied");
						}
					}

				});
			}
		});

		adminlogoutButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				AdminService.logout(new AsyncCallback<Void>() {
					@Override
					public void onFailure(Throwable error) {
						System.out.println("logout fails");
						error.printStackTrace();
						handleError(error);
					}

					@Override
					public void onSuccess(Void ignore) {
						adminLoginPanel.clear();
						adminLoginPanel.insert(inputAdmin, 0);
						adminLoginPanel.insert(adminSubmit, 1);
						adminLoginPanel.insert(adminlabel, 2);
						mainAdminTab.getTabBar().setVisible(false);
						addAdmin();

					}

				}

				);
			}
		});

		adminlogin.addStyleName("adminsubmit");
		mainAdminTab.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				if (event.getSelectedItem() == 1) {
					System.out.println("admin tab is selected");
					subListLb = createSub(libraries);
					displayAdminLibrary(subListLb);
					System.out.println("print sublist:" + subListLb);
				}
			}
		});
		loadLibraries();
		checkAll();
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL(),
				new AsyncCallback<LoginInfo>() {
					public void onFailure(Throwable error) {
						handleError(error);
					}

					public void onSuccess(LoginInfo result) {
						loginInfo = result;
						if (loginInfo.isLoggedIn()) {

							getFavoriteLb();
							loadLogout();

						} else {
							loadLogin();
						}
					}
				});

	}

	private void addAdmin() {
		AdminService.addAdmin(new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable error) {
				System.out.println("Adding admin fails");
				handleError(error);
			}

			@Override
			public void onSuccess(Void ignore) {
				System.out.println("admin is added");

			}
		});

	}

	private void loadLogin() {
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);

		RootPanel.get("login").add(loginPanel);
	}

	private void loadLogout() {
		signOutLink.setHref(loginInfo.getLogoutUrl());
		loginPanel.add(logoutLabel);
		loginPanel.add(signOutLink);
		RootPanel.get("login").add(loginPanel);
		
//		signOutLink.setHref(loginInfo.getLogoutUrl());
//		logoutPanel.add(logoutLabel);
//		logoutPanel.add(signOutLink);
//		RootPanel.get("login").add(logoutPanel);
	}

	private void loadLibraryLocator() {
		final LatLng myLatLng = LatLng.create(49.269893, -123.253268);
		MapOptions myOptions = MapOptions.create();
		PanControlOptions panOptions = PanControlOptions.create();
		ZoomControlOptions zoomOptions = ZoomControlOptions.create();
		// StreetViewControlOptions streetViewOptions =
		// StreetViewControlOptions.create();
		panOptions.setPosition(ControlPosition.RIGHT_BOTTOM);
		// streetViewOptions.setPosition(ControlPosition.RIGHT_BOTTOM);
		zoomOptions.setPosition(ControlPosition.RIGHT_BOTTOM);
		myOptions.setZoom(8.0);
		myOptions.setCenter(myLatLng);
		myOptions.setMapTypeId(MapTypeId.ROADMAP);
		myOptions.setPanControlOptions(panOptions);
		myOptions.setStreetViewControl(false);
		myOptions.setZoomControlOptions(zoomOptions);

		map = GoogleMap.create(Document.get().getElementById("map"), myOptions);
		ScrollPanel SearchPanel = new ScrollPanel(searchTab);
		mainTab.add(SearchPanel, "Search");
		ScrollPanel FavPanel = new ScrollPanel(favoriteTab);
		mainTab.add(FavPanel, "Favorite");
		FavPanel.setHeight("300px");
		mainTab.selectTab(0);
		mainTab.getTabBar().addStyleName("tabPanel");
		mainTab.getDeckPanel().addStyleName("mainTab");
		SearchPanel.addStyleName("scrollPanel");
		FavPanel.addStyleName("scrollPanel");
		SearchPanel.setHeight("300px");
		adminTab.add(pagePanel);
		adminTab.add(allLibraries);
		adminTab.add(addLibraryPanel);

		allLibraries.setText(0, 0, "ID");
		allLibraries.setText(0, 1, "Name");
		allLibraries.setText(0, 2, "Branch");
		allLibraries.setText(0, 3, "Phone");
		allLibraries.setText(0, 4, "Address");
		allLibraries.setText(0, 5, "City");
		allLibraries.setText(0, 6, "PostCode");
		allLibraries.setText(0, 7, "Latitude");
		allLibraries.setText(0, 8, "Longitude");
		allLibraries.setText(0, 9, "Select");

		// add style
		allLibraries.getRowFormatter().addStyleName(0, "adminTableHeader");
		allLibraries.addStyleName("adminTable");
		allLibraries.getCellFormatter().addStyleName(0, 9, "selectButton");
		allLibraries.setCellPadding(6);

		// Assemble Add library panel.
		addLibraryPanel.add(addLibraryTable);
		addLibraryPanel.add(addLibraryButton);
		//addLibraryPanel.add(removeLibraryButton);
//		addLibraryPanel.add(adminCheckAll);
//		addLibraryPanel.add(adminToMap);
//		addLibraryPanel.add(allToMap);
		addLibraryPanel.add(loadLibraryButton);

		// create the table for adding library attributes
		addLibraryTable.setText(0, 0, "ID:");
		addLibraryTable.setText(1, 0, "Name:");
		addLibraryTable.setText(2, 0, "Branch:");
		addLibraryTable.setText(3, 0, "Phone:");
		addLibraryTable.setText(4, 0, "Address:");
		addLibraryTable.setText(5, 0, "City:");
		addLibraryTable.setText(6, 0, "PostCode:");
		addLibraryTable.setText(7, 0, "Latitude:");
		addLibraryTable.setText(8, 0, "Longitude:");

		addLibraryTable.setWidget(0, 1, inputLibraryID);
		addLibraryTable.setWidget(1, 1, inputLibraryName);
		addLibraryTable.setWidget(2, 1, inputLibraryBranch);
		addLibraryTable.setWidget(3, 1, inputLibraryPhone);
		addLibraryTable.setWidget(4, 1, inputLibraryAddress);
		addLibraryTable.setWidget(5, 1, inputLibraryCity);
		addLibraryTable.setWidget(6, 1, inputLibraryPostCode);
		addLibraryTable.setWidget(7, 1, inputLibraryLat);
		addLibraryTable.setWidget(8, 1, inputLibraryLon);

		// Assemble page Panel
		pagePanel.add(prev);
		loPage = createLoPage(libraries);
		System.out.println("pagesize:" + loPage);
		for (int i = 0; i < loPage.size(); i++) {
			pagePanel.add(loPage.get(i));
		}
		pagePanel.add(next);
		prev.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (pageIndex == 0) {
					pageMessage.setText("It's already the front page =)");
				} else {
					pageIndex -= pageSize;
					System.out.println("PageIndex is " + pageIndex);
					subListLb = createSub(libraries);
					cleanTable(allLibraries);
					displayAdminLibrary(subListLb);
					pageMessage.setText("");
				}
			}
		});
		next.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if ((pageIndex + pageSize) >= libraries.size() - 1) {
					pageMessage.setText("It's already the last page =)");
				} else {
					pageIndex += pageSize;
					System.out.println("PageIndex is " + pageIndex);
					subListLb = createSub(libraries);
					cleanTable(allLibraries);
					displayAdminLibrary(subListLb);
					pageMessage.setText("");
				}
			}

		});
		pagePanel.add(pageMessage);

		// add style
		pagePanel.addStyleName("pagePanel");

		// TODO Assemble search tab
		searchTab.add(searchPanel);
		searchTab.add(librariesFlexTable);
		searchTab.add(numLb);
		searchTab.add(buttonPanel);
		searchPanel.add(searchBox);
		searchPanel.add(searchButton);

		librariesFlexTable.setText(0, 0, "Library");
		librariesFlexTable.setText(0, 1, "Branch");
		librariesFlexTable.setText(0, 2, "Select");
		// add style
		librariesFlexTable.getRowFormatter().addStyleName(0, "uiTableHeader");
		librariesFlexTable.addStyleName("uiTable");
		librariesFlexTable.getCellFormatter().addStyleName(0, 0, "uiTableName");
		librariesFlexTable.getCellFormatter().addStyleName(0, 1,
				"uiTableBranch");
		librariesFlexTable.getCellFormatter()
				.addStyleName(0, 2, "selectButton");
		librariesFlexTable.setCellPadding(6);

		// TODO Assemble button panel
		buttonPanel.add(addFavoriteButton);
		buttonPanel.add(toMapButton);
		buttonPanel.add(checkAllButton);

		// TODO Assemble favorite tab
		//if (loginInfo.isLoggedIn()) {
			favoriteTab.add(favoriteTable);
			favoriteTab.add(removeFavLabel);
			favoriteTab.add(buttonPanelfav);
/*
		} else {
			signInLink.setHref(loginInfo.getLoginUrl());
			loginPanel.add(loginLabel);
			loginPanel.add(signInLink);
			favoriteTab.add(loginPanel);
		}
*/

		// TODO create table for displaying libraries (favorite tab)
		favoriteTable.setText(0, 0, "Library");
		favoriteTable.setText(0, 1, "Branch");
		favoriteTable.setText(0, 2, "Select");

		// add style
		favoriteTable.getRowFormatter().addStyleName(0, "uiTableHeader");
		favoriteTable.addStyleName("uiTable");
		favoriteTable.getCellFormatter().addStyleName(0, 0, "uiTableName");
		favoriteTable.getCellFormatter().addStyleName(0, 1, "uiTableBranch");
		favoriteTable.getCellFormatter().addStyleName(0, 2, "selectButton");

		favoriteTable.setCellPadding(6);

		// TODO Assemble button panel (remove button?)
		buttonPanelfav.add(removeFavorite);
		buttonPanelfav.add(toMapButtonFav);
		buttonPanelfav.add(checkAllButtonFav);

		// TODO Associate the Main panel with the HTML host page.
		RootPanel.get("dialogboxAdmin").add(mainAdminTab);
		// RootPanel.get("Footer").add(mainButtonPanel);
		RootPanel.get("libraryLocator").add(mainTab);

		// TODO Move cursor focus to ALL input box.
		inputLibraryID.setFocus(true);
		searchInputBox.setFocus(true);

		// Listen for mouse events on the Add button
		addLibraryButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addLibrary();
			}
		});
		removeLibraryClickHandler(adminSelectedLb);

		toMapButtonFav.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				System.out.println(selectedFav);
				clearMarkers();
				for (Library lb : selectedFav) {
					addMarkers(lb);
					System.out.println(markers);
				}
				MapMarkers();
			}

		});

		// Listen for mouse events on the ToMap button
		toMapButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				System.out.println(selectedLb);
				clearMarkers();
				for (Library lb : selectedLb) {
					addMarkers(lb);
				}
				MapMarkers();

			}

		});

		loadLibraryButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				System.out.println("load library is click");
				libraries.clear();
				addToDataStore();
			}
		});

		System.out.println("check libraries:" + libraries);

		SearchFunc();
		FavoriteFunc();

	}

	private ArrayList<Library> createSub(ArrayList<Library> lolb) {
		ArrayList<Library> returnLb = new ArrayList<Library>();
		boolean check = (lolb.size() % pageSize != 0)
				&& (pageIndex + pageSize >= lolb.size());
		List<Library> temp = lolb.subList(pageIndex,
				pageIndex + (check ? lolb.size() % pageSize : pageSize));
		for (Library lb : temp) {
			returnLb.add(lb);
		}
		return returnLb;
	}

	private ArrayList<Button> createLoPage(ArrayList<Library> lolb) {
		int temp;
		System.out.println(libraries.size());
		if (lolb.size() % pageSize != 0) {
			temp = lolb.size() / pageSize + 1;
		} else {
			temp = lolb.size() / pageSize;
		}
		ArrayList<Button> lob = new ArrayList<Button>();
		for (int i = 1; i <= temp; i++) {
			Button b = new Button(String.valueOf(i));
			final int tempI = i;
			b.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					System.out.println("Page " + tempI + " is clicked");
					pageIndex = (tempI - 1) * pageSize;
					System.out.println("PageIndex is " + pageIndex);
					subListLb = createSub(libraries);
					cleanTable(allLibraries);
					displayAdminLibrary(subListLb);
					pageMessage.setText("");
				}

			});
			lob.add(b);
		}
		return lob;
	}

	private void addToDataStore() {
		libraryService.populateTable(new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable error) {
				System.out.println("populateTable Failed");
				handleError(error);
			}

			@Override
			public void onSuccess(Void result) {
				System.out.println("Data Store is populated");
				loadLibraries();
			}

		});
	}

	private void loadLibraries() {

		start = System.currentTimeMillis();
		libraryService.getLibraries(new AsyncCallback<ArrayList<Library>>() {

			@Override
			public void onFailure(Throwable error) {
				System.out.println("loadLibraries fail");
				handleError(error);
			}

			@Override
			public void onSuccess(ArrayList<Library> lolb) {
				System.out.println("loadLibraries success"
						+ (System.currentTimeMillis() - start));
				libraries = lolb;
				System.out.println("loadLibraries: " + libraries);
				Timer t = new Timer() {

					@Override
					public void run() {
						mainTab.clear();
						loadLibraryLocator();

					}

				};
				// delay 2 sec
				t.schedule(2000);

			}

		});
	}

	/**
	 * (admin) Add Library to FlexTable. Executed when the user clicks the
	 * addLibraryButton (NOT doing the keyHandler)
	 */
	private void addLibrary() {
		final String newID = inputLibraryID.getText().toUpperCase();
		final String newName = inputLibraryName.getText();
		final String newBranch = inputLibraryBranch.getText();
		final String newPhone = inputLibraryPhone.getText();
		final String newAddress = inputLibraryAddress.getText();
		final String newCity = inputLibraryCity.getText();
		final String newPostCode = inputLibraryPostCode.getText().toUpperCase();
		final Double newLat = Double.parseDouble(inputLibraryLat.getText());
		final Double newLon = Double.parseDouble(inputLibraryLon.getText());

		final Library newLibrary = new Library(newID, newName, newBranch,
				newPhone, newAddress, newCity, newPostCode, newLat, newLon);

		ArrayList<TextBox> lotb = new ArrayList<TextBox>();
		lotb.add(inputLibraryID);
		lotb.add(inputLibraryName);
		lotb.add(inputLibraryBranch);
		lotb.add(inputLibraryPhone);
		lotb.add(inputLibraryAddress);
		lotb.add(inputLibraryCity);
		lotb.add(inputLibraryPostCode);
		lotb.add(inputLibraryLat);
		lotb.add(inputLibraryLon);

		inputLibraryID.setText("");
		inputLibraryName.setText("");
		inputLibraryBranch.setText("");
		inputLibraryPhone.setText("");
		inputLibraryAddress.setText("");
		inputLibraryCity.setText("");
		inputLibraryPostCode.setText("");
		inputLibraryLat.setText("");
		inputLibraryLon.setText("");
		inputLibraryID.setFocus(true);

		ArrayList<String> loid = new ArrayList<String>();
		ArrayList<LatLng> loLatlng = new ArrayList<LatLng>();
		for (Library lb : libraries) {
			loid.add(lb.getId());
			loLatlng.add(lb.getLatlon());
		}

		if (loid.contains(newID)
				|| loLatlng.contains(LatLng.create(newLat, newLon))) {
			Window.alert("the Library is already exit!");
			return;
		}

		int row = allLibraries.getRowCount();
		libraries.add(newLibrary);
		allLibraries.setText(row, 0, newID);
		allLibraries.setText(row, 1, newName);
		allLibraries.setText(row, 2, newBranch);
		allLibraries.setText(row, 3, newPhone);
		allLibraries.setText(row, 4, newAddress);
		allLibraries.setText(row, 5, newCity);
		allLibraries.setText(row, 6, newPostCode);
		allLibraries.setText(row, 7, newLat.toString());
		allLibraries.setText(row, 8, newLon.toString());

		CheckBox selectButton = new CheckBox();
		selectButton.setValue(false);
		allLibraries.setWidget(row, 9, selectButton);
		addLibrary(newLibrary);

	}

	private void addLibrary(final Library lib) {
		libraryService.addLibrary(lib, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable error) {
				handleError(error);
			}

			@Override
			public void onSuccess(Void ignore) {
				if (!libraries.contains(lib)) {
					displayAdminLibrary(lib);
				}
			}
		});
	}

	private void removeLibrary(final ArrayList<Library> adminSelectList) {
		final ArrayList<Library> temp = new ArrayList<Library>();
		temp.removeAll(adminSelectList);
		libraryService.removeLibrary(adminSelectList,
				new AsyncCallback<Void>() {
					@Override
					public void onFailure(Throwable error) {
						System.out.println("remove lib fail");
						handleError(error);
					}

					@Override
					public void onSuccess(Void ignore) {
						adminSelectList.clear();
						displayAdminLibrary(temp);
					}
				});
	}

	private void displayAdminLibrary(ArrayList<Library> lolb) {
		for (Library lb : lolb) {
			displayAdminLibrary(lb);
		}
	}

	private void displayAdminLibrary(final Library lb) {
		int row = allLibraries.getRowCount();
		allLibraries.setText(row, 0, lb.getId());
		allLibraries.setText(row, 1, lb.getName());
		allLibraries.setText(row, 2, lb.getBranch());
		allLibraries.setText(row, 3, lb.getPhone());
		allLibraries.setText(row, 4, lb.getAddress());
		allLibraries.setText(row, 5, lb.getCity());
		allLibraries.setText(row, 6, lb.getPostalCode());
		allLibraries.setText(row, 7, lb.getLat().toString());
		allLibraries.setText(row, 8, lb.getLon().toString());

		CheckBox selectButton = new CheckBox();
		selectButton.setValue(false);

		selectButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked == true) {
					adminSelectedLb.add(lb);
					System.out.println(adminSelectedLb + "\n");
					// Window.alert(selectedLb.toString());
				} else {
					adminSelectedLb.remove(lb);
					System.out.println(adminSelectedLb + "\n");
					// Window.alert(selectedLb.toString());

				}
			}
		});
		allLibraries.setWidget(row, 9, selectButton);
		// add style
		// allLibraries.getCellFormatter().addStyleName(row, 9, "selectButton");

	}

	/**
	 * (search) Add Library to FlexTable. Executed when the user clicks the
	 * searchButton or hit enter
	 */
	private void SearchFunc() {
		System.out.println("search function is runing:" + libraries);
		searchBox.setFocus(true);
		searchBox.addItem("----Please Selet a City----");
		Set<String> allCity = new HashSet<String>();
		for (Library l : libraries) {
			allCity.add(l.getCity());
		}
		LinkedList<String> allCitySort = new LinkedList<String>();
		allCitySort.addAll(allCity);
		System.out.println("check allCitySort:" + allCitySort);
		Collections.sort(allCitySort);

		System.out.println("allCity:" + allCitySort);
		for (String c : allCitySort) {
			searchBox.addItem(c);
		}
		searchBox.setVisibleItemCount(1);

		searchBox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				searchLb.clear();
				int index = ((ListBox) event.getSource()).getSelectedIndex();
				String selectedCity = ((ListBox) event.getSource())
						.getValue(index);
				System.out.println("selected city:" + selectedCity);
				for (Library lb : libraries) {
					if (lb.getCity().matches(selectedCity)) {
						searchLb.add(lb);
					}
				}
				searchButton.setFocus(true);
			}
		});

		searchButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				clearSelected();
				clearCheckBoxList(libCheckBox);
				setButtonText(checkAllButton, "Check All");
				cleanTable(librariesFlexTable);
				displaySearchLibrary(searchLb);

				boolean checked = (librariesFlexTable.getRowCount() > 2);
				numLb.setText("\n There is "
						+ (librariesFlexTable.getRowCount() - 1)
						+ (checked ? " libraries " : " library ")
						+ "in this city.");
				searchBox.setFocus(true);
			}

		});

		addFavoriteButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ArrayList<String> loid = new ArrayList<String>();
				for (Library lb : selectedLb) {
					loid.add(lb.getId());
				}
				favoriteService.addFavorites(loid, new AsyncCallback<Void>() {
					@Override
					public void onFailure(Throwable error) {
						System.out.println("add favorite fails");
						handleError(error);

					}

					@Override
					public void onSuccess(Void ignore) {
						// when the database adding is success
						for (Library lb : selectedLb) {
							clientFav.add(lb);
						}
					}
				});
			}
		});

	}

	private void displaySearchLibrary(ArrayList<Library> lolb) {
		for (Library lb : lolb) {
			displaySearchLibrary(lb);
		}

	}

	private void displaySearchLibrary(final Library lb) {

		int row = librariesFlexTable.getRowCount();
		librariesFlexTable.setWidget(row, 0, nameHyprLink(lb));
		librariesFlexTable.setText(row, 1, lb.getBranch());
		CheckBox selectButton = new CheckBox();
		selectButton.setValue(false);
		libCheckBox.add(selectButton);
		onSelect(selectButton, lb, selectedLb, checkAllButton);

		/*selectButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();

				if (checked == true) {
					selectedLb.add(lb);
					System.out.println(selectedLb + "\n");
				} else {
					selectedLb.remove(lb);
					System.out.println(selectedLb + "\n");
					setButtonText(checkAllButton, "Check All");
				}
			}
		});*/

		librariesFlexTable.setWidget(row, 2, selectButton);

		// add style
		librariesFlexTable.getCellFormatter().addStyleName(row, 0,
				"uiTableName");
		librariesFlexTable.getCellFormatter().addStyleName(row, 1,
				"uiTableBranch");
		librariesFlexTable.getCellFormatter().addStyleName(row, 2,
				"selectButton");
	}

	/**
	 * (favorite) Add Library to FlexTable. Executed when the user opens the
	 * favorite tab
	 */
	private void FavoriteFunc() {
		mainTab.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				// when the fav tab is clicked
				if (event.getSelectedItem() == 1) {
					System.out.println("favourite tab is selected");
					removeFavLabel.setText("");
					refleshFav();
				}
			}
		});

		removeFavorite.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ArrayList<String> loid = new ArrayList<String>();

				for (Library lb : selectedFav) {
					loid.add(lb.getId());
				}
				clientFav.removeAll(selectedFav);
				removeFav(loid);
				System.out.println("client side fav:" + clientFav);
				refleshFav();
			}
		});

	}

	private void removeFav(final ArrayList<String> loid) {
		favoriteService.removeFavorites(loid, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable error) {
				System.out.println("remove favorite fails");
				handleError(error);
			}

			@Override
			public void onSuccess(Void ignore) {
				System.out.println("remove favorite success");
				int removeNum = loid.size();
				clearSelectedFav();
				removeFavLabel.setText("You have removed " + removeNum
						+ " favorites.");
			}
		});
	}

	private void refleshFav() {
		checkAllButtonFav.setText("Check All");
		cleanTable(favoriteTable);
		displayFavorites(clientFav);

	}

	private void getFavoriteLb() {
		favoriteService.getFavorite(new AsyncCallback<ArrayList<String>>() {

			@Override
			public void onFailure(Throwable error) {
				System.out.println("get favorite fails");
				handleError(error);

			}

			@Override
			public void onSuccess(final ArrayList<String> loid) {
				Timer t = new Timer() {

					@Override
					public void run() {
						favCheckBox.clear();
						favorites.clear();
						for (String id : loid) {
							for (Library lb : libraries) {
								if (id.matches(lb.getId())) {
									favorites.add(lb);
								}
							}
						}
						System.out.println("get favorite success: (num)"
								+ favorites.size() + favorites);
						clientFav.addAll(favorites);
						System.out.println("client favorite success: (num)"
								+ clientFav.size() + clientFav);
					}

				};
				// delay 2 sec
				t.schedule(2000);
			}

		});
	}

	private void displayFavorites(Set<Library> clientFav2) {
		System.out.println("display Favorites is running");
		if (!isFavWorking) {
			Window.alert("Please log in to use favorite funtion");
		}
		for (Library lb : clientFav2) {
			displayFavorite(lb);
		}
	}

	private void displayFavorite(final Library fav) {
		int row = favoriteTable.getRowCount();
		favoriteTable.setWidget(row, 0, nameHyprLink(fav));
		favoriteTable.setText(row, 1, fav.getBranch());

		CheckBox selectButton = new CheckBox();
		selectButton.setValue(false);
		favCheckBox.add(selectButton);
		onSelect(selectButton, fav, selectedFav, checkAllButtonFav);
		favoriteTable.setWidget(row, 2, selectButton);
		
		favoriteTable.getCellFormatter().addStyleName(row, 0, "uiTableName");
		favoriteTable.getCellFormatter().addStyleName(row, 1, "uiTableBranch");
		favoriteTable.getCellFormatter().addStyleName(row, 2, "selectButton");

	}

	private void onSelect(CheckBox selectButton, final Library lb, final ArrayList<Library> selectList, final Button checkall) {
		selectButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked == true) {
					selectList.add(lb);
					System.out.println("selected : " + selectList + "\n");
				} else {
					selectList.remove(lb);
					System.out.println("selected : " + selectList + "\n");
					checkall.setText("Check All");
				}
			}
		});
	}

	private void cleanTable(FlexTable table) {
		int row = table.getRowCount();
		for (int i = row - 1; i >= 1; i--) {
			table.removeRow(i);
		}
	}

	private void checkAll() {
		checkAllButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if (!isAllChecked(libCheckBox)) {
					checkAllItems(libCheckBox, checkAllButton);
					addSelected(selectedLb, searchLb);
				} else {
					uncheckAllItems(libCheckBox, checkAllButton);
					clearSelected();
				}
			}
		});
		checkAllButtonFav.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (!isAllChecked(favCheckBox)) {
					checkAllItems(favCheckBox, checkAllButtonFav);
					addSelected(selectedFav, clientFav);
				} else {
					uncheckAllItems(favCheckBox, checkAllButtonFav);
					clearSelectedFav();
					System.out.println("Unchecked all selected in fav");
				}

			}
		});
	}

	private void addSelected(ArrayList<Library> selected,
			ArrayList<Library> displayedLb) {
		selected.addAll(displayedLb);
		for (Library l : displayedLb) {
			System.out.println(l.getBranch() + "is selected");
		}
	}

	private void addSelected(ArrayList<Library> selected,
			Set<Library> displayedLb) {
		selected.addAll(displayedLb);
		for (Library l : displayedLb) {
			System.out.println(l.getBranch() + "is selected");
		}
	}

	private void clearSelected() {
		selectedLb.clear();
	}

	private void setButtonText(Button b, String s) {
		b.setText(s);
	}

	private void clearCheckBoxList(ArrayList<CheckBox> listOfCheckBox) {
		listOfCheckBox.clear();
	}

	private boolean isAllChecked(ArrayList<CheckBox> listOfCheckBox) {
		System.out.println("numebr of checkbox in list "
				+ listOfCheckBox.size());
		for (CheckBox c : listOfCheckBox) {
			if (c.getValue() == false) {
				return false;
			}
		}
		return true;

	}

	private void checkAllItems(ArrayList<CheckBox> listOfCheckBox, Button b) {
		for (CheckBox c : listOfCheckBox) {
			c.setValue(true);
			setButtonText(b, "Uncheck All");
		}
	}

	private void uncheckAllItems(ArrayList<CheckBox> listOfCheckBox, Button b) {
		for (CheckBox c : listOfCheckBox) {
			c.setValue(false);
		}
		setButtonText(b, "Check All");
	}

	private void clearSelectedFav() {
		selectedFav.clear();
	}

	private void clearMarkers() {
		for (Marker mk : markers) {
			mk.setVisible(false);
			mk = null;
			infowindow.close();

		}
		System.out.println("marker cleared");

	}

	// add each maker on the marker list
	private void addMarkers(final Library lb) {
		MarkerOptions markerOpts = MarkerOptions.create();
		markerOpts.setPosition(LatLng.create(lb.getLat(), lb.getLon()));
		markerOpts.setMap(map);
		markerOpts.setTitle("UBC");

		map.setCenter(LatLng.create(lb.getLat(), lb.getLon() - 0.1)); // center
																		// at
																		// whole
																		// BC

		map.setZoom(10.0);

		final Marker marker = Marker.create(markerOpts);
		markers.add(marker);

		marker.addDblClickListener(new DblClickHandler() {

			@Override
			public void handle(MouseEvent event) {
				map.setCenter(LatLng.create(lb.getLat(), lb.getLon() - 0.1));

				infowindowOpts.setContent("<header><strong>" + lb.getName()
						+ "</strong></header>" + "<p> Brach Name: "
						+ lb.getBranch() + "<br />" + "Address: "
						+ lb.getAddress() + "<br />" + "Postal Code: "
						+ lb.getPostalCode() + "<br />" + "Phone Number: "
						+ lb.getPhone() + "</p>");
				infowindowOpts.setMaxWidth(500.00);
				infowindow.setOptions(infowindowOpts);
				infowindow.open(map, marker);
			}
		});
	}

	// map all the markers from a list
	private void MapMarkers() {
		for (Marker mker : markers) {
			mker.setMap(map);
		}

	}

	Hyperlink nameHyprLink(final Library lb) {
		final Hyperlink l = new Hyperlink(lb.getName(), "http://lmgtfy.com/?q="
				+ lb.getName());
		ClickHandler handler = new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.open("http://lmgtfy.com/?q=" + lb.getName(),
						lb.getName(), "");
			}
		};

		l.addDomHandler(handler, ClickEvent.getType());
		return l;
	}

	void handleError(Throwable error) {
		if (error instanceof NotLoggedInException) {
			// Window.alert("not login!");
			Window.Location.replace(LibraryLocator.loginInfo.getLogoutUrl());
		}
		Window.alert(error.getMessage());
	}

	private void removeLibraryClickHandler(
			final ArrayList<Library> adminSelectList) {
		removeLibraryButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				removeLibrary(adminSelectList);

			}
		});
	}
}
