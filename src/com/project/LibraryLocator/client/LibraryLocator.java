package com.project.LibraryLocator.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.jdo.Query;

import com.project.LibraryLocator.shared.FavoriteObj;
import com.project.LibraryLocator.shared.FieldVerifier;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.project.LibraryLocator.shared.Library;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.DOM;
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
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
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
import com.google.maps.gwt.client.Point;
import com.google.maps.gwt.client.ScaleControlOptions;
import com.google.maps.gwt.client.StreetViewControlOptions;
import com.google.maps.gwt.client.ZoomControlOptions;

//
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class LibraryLocator implements EntryPoint {

	// panel for login
	private LoginInfo loginInfo = new LoginInfo();
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label(
			"Please sign in to your Google Account to access the LibraryLocator application.");
	private Anchor signInLink = new Anchor("Sign In");

	// set hyperlink in library class? when display in flextable?

	//private DockPanel mainPanel = new DockPanel();
	private HorizontalPanel headerPanel = new HorizontalPanel();
	private TabPanel mainTab = new TabPanel();

	private TabPanel mainAdminTab = new TabPanel();

	// searchTab
	// things inside the search tab
	private VerticalPanel searchTab = new VerticalPanel();
	private HorizontalPanel searchPanel = new HorizontalPanel();
	private TextBox searchInputBox = new TextBox(); // may use Suggest Box
	private FlexTable librariesFlexTable = new FlexTable();
	private ListBox searchBox = new ListBox();
	private Label numLb = new Label("");
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private HorizontalPanel pagePanel = new HorizontalPanel();
	// Buttons (for search)
	private Button searchButton = new Button("Search");
	private Button checkallButton = new Button("Check All"); // also able to use in favorite?
	private Button toMapButton = new Button("To Map"); // also able to use in favorite?
	private Button addFavoriteButton = new Button("Add Favorite");
	private Button AdminLogin = new Button("Admin Access");
	private Button adminSubmit = new Button("submit");
	private Button footerButton = new Button("testing");

	// favoriteTab
	// things inside favorite
	private VerticalPanel favoriteTab = new VerticalPanel();
	private FlexTable favoriteTable = new FlexTable();
	private Label removeFavLabel = new Label();
	private HorizontalPanel buttonPanelfav = new HorizontalPanel();
	//Buttons
	private Button removeFavorite = new Button("Remove");
	private Button checkallButtonfav = new Button("Check All"); // the one in favorite tab
	private Button toMapButtonfav = new Button("To Map"); // the one in favorite tab

	// adminTab (testing atleast?), display all library and able to add new library
	// things inside admin page
	private VerticalPanel adminTab = new VerticalPanel();
	private VerticalPanel adminLoginPanel = new VerticalPanel();
	private HorizontalPanel addLibraryPanel = new HorizontalPanel();
	private TextBox inputAdmin = new TextBox();
	private TextBox inputLibraryID = new TextBox(); //
	private TextBox inputLibraryName = new TextBox(); // need input box for
														// every attributes?
	private TextBox inputLibraryBranch = new TextBox();
	private TextBox inputLibraryPhone = new TextBox();
	private TextBox inputLibraryAddress = new TextBox();
	private TextBox inputLibraryCity = new TextBox();
	private TextBox inputLibraryPostCode = new TextBox();
	private TextBox inputLibraryLat = new TextBox();
	private TextBox inputLibraryLon = new TextBox();
	private FlexTable addLibraryTable = new FlexTable();
	private FlexTable allLibraries = new FlexTable();
	// Buttons (for admin)
	private Button addLibraryButton = new Button("Add");
	private Button loadLibraryButton = new Button("Load Libraries");

	// Buttons on mainPanel
	private HorizontalPanel mainButtonPanel = new HorizontalPanel();
	private Button socail1 = new Button("Google+");

	// map
	private GoogleMap map;
	private static final int TILE_SIZE = 256;
	private static final LatLng UBC = LatLng.create(41.850033, -87.6500523);
	private InfoWindow infowindow = InfoWindow.create();
	private InfoWindowOptions infowindowOpts = InfoWindowOptions.create();


	// Service classes
	private final LibraryServiceAsync libraryService = GWT
			.create(LibraryService.class);
	private final FavoriteServiceAsync favoriteService = GWT.create(FavoriteService.class);

	private ArrayList<Library> libraries = new ArrayList<Library>(); // list of library object
	private ArrayList<Library> selectedLb = new ArrayList<Library>(); // when refactoring, each tab has its own selected list
	private ArrayList<Library> searchLb = new ArrayList<Library>();
	private ArrayList<FavoriteObj> favorites = new ArrayList<FavoriteObj>(); // list of favorites REFACTOR to favorite tab?
	private ArrayList<FavoriteObj> selectedFav = new ArrayList<FavoriteObj>(); // favorite's selected list
	
	int pageSize = 20;
	
	private long start;

//	 private String contentString = "<div id=\"content\">"
//		      + "<div id=\"siteNotice\">"
//		      + "</div>"
//		      + "<h1 id=\"firstHeading\" class=\"firstHeading\">Uluru</h1>"
//		      + "<div id=\"bodyContent\">"
//		      + "<p><b>TITLE</b>CONTENT</p>"
//		      + "<p>Attribution: title, <a href=\"http://en.wikipedia.org/w/index.php?"
//		      + "title=Uluru&oldid=297882194\">"
//		      + "http://en.wikipedia.org/w/index.php?title=Uluru</a> (last visited June "
//		      + "22, 2009).</p>"
//		      + "</div>"
//		      + "</div>";


	// private Label refleshLabel = new Label(); // not sure about this, do we
	// need it? maybe for hyperlink part...

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		adminLoginPanel.insert(inputAdmin,0);
		adminLoginPanel.insert(adminSubmit,1);
		mainAdminTab.add(adminLoginPanel,"Admin Login");
		ScrollPanel DataPanel = new ScrollPanel(adminTab);
		DataPanel.setHeight("500px");
		mainAdminTab.add(DataPanel,"Database");
		mainAdminTab.selectTab(0);
//		mainAdminTab.setHeight("300px");
//		Element element = document.getElementById("admin");
//		element.onclick = function() {
//		  // onclick stuff
//		}
//	
		//create a new dialogBox for admin
		final AdminDialog dialogBox = createDialogBox();
	    dialogBox.setGlassEnabled(true);
	    dialogBox.setAnimationEnabled(true);

	    // Create a button to show the dialog Box
	    AdminLogin.addClickHandler(new ClickHandler() {
	          public void onClick(ClickEvent sender) {
	            dialogBox.isAutoHideEnabled();
	            dialogBox.isGlassEnabled();
	            dialogBox.center();
	            dialogBox.show();
	            //dialogBox.isAutoHideEnabled(); no difference
	          dialogBox.setAutoHideEnabled(true);
	          dialogBox.setAnimationEnabled(true);
	          dialogBox.setGlassEnabled(true);
	         
	          }
	        });
	    headerPanel.add(AdminLogin);

	    //DOM.getElementById("admin").appendChild(headerPanel);
	    
	    //mainButtonPanel.add(footerButton);
	    

	    //headerPanel.add(loadLibraryButton);
	    //loadLibraryButton.addStyleName("adminsubmit");

	    AdminLogin.addStyleName("adminsubmit");
	    
	    mainAdminTab.addSelectionHandler(new SelectionHandler<Integer>() {
	    	@Override
	    	public void onSelection(SelectionEvent<Integer> event) {
	    	    if (event.getSelectedItem() == 1) {
	    	    	System.out.println("admin tab is selected");
	    	    	displayAdminLibrary(libraries);
	    	    }
	    	  }
	    });
	    
	    
		// Check login status using login service.
		LoginServiceAsync loginService = GWT.create(LoginService.class);
//		loginService.login(GWT.getHostPageBaseURL(),
//				new AsyncCallback<LoginInfo>() {
//					public void onFailure(Throwable error) {
//						Button button = new Button("loginFail");
//						//RootPanel.get("libraryLocator").add(button);
//						RootPanel.get("SocialPanel").add(button);
//
//					}

//					public void onSuccess(LoginInfo result) {
//						loginInfo = result;
//						if (loginInfo.isLoggedIn()) {
							loadLibraryLocator();
//						} else {
//							loadLogin();
//						}
//					}
//				});

	}

	private void loadLogin() {
		// Assemble login panel.
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		
		//RootPanel.get("SocialPanel").add(loginPanel);
	}

	private void loadLibraryLocator() {
		// TODO Assemble Main panel.
		//mainPanel.add(mainButtonPanel, DockPanel.NORTH);
		//mainPanel.add(mainTab, DockPanel.WEST);

		final LatLng myLatLng = LatLng.create(49.269893, -123.253268);
		MapOptions myOptions = MapOptions.create();
		PanControlOptions panOptions = PanControlOptions.create();
		ZoomControlOptions zoomOptions = ZoomControlOptions.create();
		StreetViewControlOptions streetViewOptions = StreetViewControlOptions.create();
		panOptions.setPosition(ControlPosition.RIGHT_BOTTOM);
		streetViewOptions.setPosition(ControlPosition.RIGHT_BOTTOM);
		zoomOptions.setPosition(ControlPosition.RIGHT_BOTTOM);
		myOptions.setZoom(8.0);
		myOptions.setCenter(myLatLng);
		myOptions.setMapTypeId(MapTypeId.ROADMAP);

		myOptions.setDisableDefaultUi(true);//disabling map ui
		final GoogleMap map = GoogleMap.create(Document.get().getElementById("map"),
				myOptions);
		
	    
//	    InfoWindowOptions infowindowOpts = InfoWindowOptions.create();
//	    infowindowOpts.setContent("library");
//	   
//	    final InfoWindow infowindow = InfoWindow.create(infowindowOpts);
//	    
//	    MarkerOptions markerOpts = MarkerOptions.create();
//	    markerOpts.setPosition(myLatLng);
//	    markerOpts.setMap(map);
//	    markerOpts.setTitle("UBC");
//	    
//	    final Marker m = Marker.create(markerOpts);
//
//	    
//	    m.addDblClickListener(new DblClickHandler() {
//
//            @Override
//            public void handle(MouseEvent event) {
//            	infowindow.open(map, m);
//            }
//        });
	    

	
//		DOM.getElementById("admin").
		ScrollPanel SearchPanel = new ScrollPanel(searchTab);
		mainTab.add(SearchPanel, "Search"); // don't think the string after is very necessary, check later!
		mainTab.add(new ScrollPanel(favoriteTab), "Favorite");
		//mainTab.add(new ScrollPanel(adminTab), "Admin");
		
		// initialize default display tab
		mainTab.selectTab(0); // 2 is the admin one
		// style
		mainTab.getTabBar().addStyleName("tabPanel");
		mainTab.getDeckPanel().addStyleName("mainTab"); // dont see difference so far haha...
		SearchPanel.setHeight("115px");
		// Assemble admin Tab
		adminTab.add(allLibraries);
		adminTab.add(addLibraryPanel);

		// create table for libraries (other attributes are still needed to
		// develop) (A,B,C,H,I,J,L,M,N)(lower case on first letter)
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

		// Assemble Add library panel.
		addLibraryPanel.add(addLibraryTable);
		addLibraryPanel.add(addLibraryButton);
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

		// TODO Assemble search tab
		searchTab.add(searchPanel);
		searchTab.add(librariesFlexTable);
		searchTab.add(numLb);
		searchTab.add(buttonPanel);
		
//		searchTab.setHeight("200px");

		// TODO Assemble search panel
		searchPanel.add(searchBox);
		searchPanel.add(searchButton);

		// TODO create table for displaying libraries (search tab)
		librariesFlexTable.setText(0, 0, "Library");
		librariesFlexTable.setText(0, 1, "Branch");
		librariesFlexTable.setText(0, 2, "Select");

		// TODO Assemble button panel
		buttonPanel.add(addFavoriteButton);
		buttonPanel.add(toMapButton);
		buttonPanel.add(checkallButton);

		// TODO Assemble favorite tab
		favoriteTab.add(favoriteTable);
		favoriteTab.add(removeFavLabel);
		favoriteTab.add(buttonPanelfav);

		// TODO create table for displaying libraries (favorite tab)
		favoriteTable.setText(0, 0, "Library");
		favoriteTable.setText(0, 1, "Branch");
		favoriteTable.setText(0, 2, "Select");

		// TODO Assemble button panel (remove button?)
		buttonPanelfav.add(removeFavorite);
		buttonPanelfav.add(toMapButtonfav);
		buttonPanelfav.add(checkallButtonfav);

		// TODO Assemble main button panel
		//mainButtonPanel.add(socail1);
		//mainButtonPanel.add(AdminLogin);

		// TODO Associate the Main panel with the HTML host page.
		RootPanel.get("dialogboxAdmin").add(headerPanel);
		//RootPanel.get("Footer").add(mainButtonPanel);
		RootPanel.get("libraryLocator").add(mainTab);
		
		//RootPanel.get("dialogboxAdmin").add(AdminLogin);
		//RootPanel.get("SocialPanel").add(mainButtonPanel);

		// TODO Move cursor focus to ALL input box.
		inputLibraryID.setFocus(true);
		searchInputBox.setFocus(true);
		

		// Listen for mouse events on the Add button
		addLibraryButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addLibrary();
			}
		});
		
		toMapButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				for (Library lb : selectedLb) {
					mapSelectedLibrary(lb);
				
					
				
				}
				
			}

			private void mapSelectedLibrary(final Library lb) {
				
			  
//			    final InfoWindow infowindow = InfoWindow.create(infowindowOpts);
			    
			    MarkerOptions markerOpts = MarkerOptions.create();
			    markerOpts.setPosition(LatLng.create(lb.getLat(),lb.getLon()));
			    markerOpts.setMap(map);
			    markerOpts.setTitle("UBC");
			    
			    map.setCenter(LatLng.create(lb.getLat(),lb.getLon()-0.2)); //center at whole BC
				map.setZoom(10.0);
			    
			    final Marker marker = Marker.create(markerOpts);
			    marker.addDblClickListener(new DblClickHandler() {

		            @Override
		            public void handle(MouseEvent event) {
		            	map.setCenter(LatLng.create(lb.getLat(),lb.getLon()-0.1));
		            	String s = "Brach Name:" + lb.getName()+", "
					    		+ "Address:"+ lb.getAddress()+ ", "+"Postal Code:"+
		            			lb.getPostalCode()+", "
					    		+"Phone Number:" + lb.getPhone();
		            	String[] parts = s.split(", ");
		            	infowindowOpts.setContent(Arrays.toString(parts));
//		            	infowindowOpts.setContent("Brach Name:" + lb.getName()
//					    		+ "Address:" + lb.getAddress() +"Postal Code:"+ lb.getPostalCode()
//					    		+"Phone Number:" + lb.getPhone());
		            	//infowindowOpts.setContent(lb.getAllData());
					    infowindow.setOptions(infowindowOpts);
		            	infowindow.open(map, marker);
		            }
		        });
			}
		});

		/*// TODO Listen for keyboard events in the (WHAT!)input box.
		addLibraryButton.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					addLibrary();
				}
			}
		});*/
		
		// TODO Listen for mouse event on the Load button
		loadLibraryButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				System.out.println("load library is click");
				libraries.clear();
				addToDataStore();
				//loadLibraries();
			}
		});

		//addToDataStore();
		loadLibraries();
		FavoriteFunc();
	}
	
	private void addToDataStore(){
		libraryService.populateTable(new AsyncCallback<Void>(){

			@Override
			public void onFailure(Throwable error) {
				System.out.println("populateTable Failed");
				// TODO handle error
				
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
		//System.out.println("populateTable (main class) success");
		libraryService.getLibraries(new AsyncCallback<ArrayList<Library>>() {

			@Override
			public void onFailure(Throwable error) {
				// TODO Auto-generated method stub
				System.out.println("loadLibraries fail");

			}

			@Override
			public void onSuccess(ArrayList<Library> lolb) {
				// TODO Auto-generated method stub
				
				System.out.println("loadLibraries success" + (System.currentTimeMillis() - start));
				libraries = lolb;
				//displayAdminLibrary(lolb);
				SearchBoxForLibary();
				System.out.println("loadLibraries: " + libraries);
				
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

		// TODO Check if all the input box is not empty otherwise not able to add library and pop out an message to warn
		// TODO check if the input text is valid
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
		for (TextBox tb : lotb) {
			//checkValid(tb);
		}

		// TODO clean the input box (refactor?)
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

		// Don't add library if the ID is already exist
		// Don't add library if lat and lon is already exist
		ArrayList<String> loid = new ArrayList<String>();
		ArrayList<LatLng> loLatlng = new ArrayList<LatLng>();
		for (Library lb : libraries) {
			loid.add(lb.getId());
			loLatlng.add(lb.getLatlon());
		}

		if (loid.contains(newID) || loLatlng.contains(LatLng.create(newLat, newLon))) {
			Window.alert("the Library is already exit!");
			return;
		}
		

		// TODO Add the Library to table (store in app-engien later?)
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

		// TODO add the select button (using checkbox?) and deal with the click
		// reaction
		CheckBox selectButton = new CheckBox();
		selectButton.setValue(false);

//		selectButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				boolean checked = ((CheckBox) event.getSource()).getValue();
//				Window.alert("It is " + (checked ? "" : "not ") + "checked");
//				if (checked == true) {
//					selectedLb.add(newLibrary);
//					System.out.println(selectedLb + "\n");
//				} else {
//					selectedLb.remove(newLibrary);
//					System.out.println(selectedLb + "\n");
//				}
//			}
//		});

		allLibraries.setWidget(row, 9, selectButton);

		// TODO Don't know if we want to have remove method here?
		
		addLibrary(newLibrary);

	}
	
	private void addLibrary(final Library lib) {
		libraryService.addLibrary(lib, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable error) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Void ignore) {
				// TODO Auto-generated method stub
				displayAdminLibrary(lib);
			}
		});
		
	}


/*	private void displayLb(ArrayList<Library> lolb){
		ArrayList<Library> temp = libraries;
		int pageNum = 0;
		if ((temp.size()%pageSize) != 0){
			pageNum = temp.size()/pageSize + 1;   //20 lb per page
		}else {
			pageNum = temp.size()/pageSize;
		}
		for(int i = 0; i < temp.size(); i++){
			for(int j = 0; j <pageSize; j++){
				
			}
		}
		
	}*/
	
	private void displayAdminLibrary(ArrayList<Library> lolb) {
		for (Library lb : lolb) {
			displayAdminLibrary(lb);
		}
	}

	private void displayAdminLibrary(final Library lb) {
		// Add the stock to the table.
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

		// add the selection button
		CheckBox selectButton = new CheckBox();
		selectButton.setValue(false);

//		selectButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				boolean checked = ((CheckBox) event.getSource()).getValue();
//				Window.alert("It is " + (checked ? "" : "not ") + "checked");
//				if (checked == true) {
//					selectedLb.add(lb);
		
//					System.out.println(selectedLb + "\n");
//					// Window.alert(selectedLb.toString());
//				} else {
//					selectedLb.remove(lb);
//					System.out.println(selectedLb + "\n");
//					// Window.alert(selectedLb.toString());
//				}
//			}
//		});
		allLibraries.setWidget(row, 9, selectButton);

	}
	
	/**
	 * (search) Add Library to FlexTable. Executed when the user clicks the
	 * searchButton or hit enter
	 */
	private void SearchBoxForLibary(){
		System.out.println("search function is runing");
		//System.out.println("search function is runing");
		searchBox.setFocus(true);
		searchBox.addItem("----Please Selet a City----");
		Set<String> allCity = new HashSet<String>();
		for(Library l : libraries){
			allCity.add(l.getCity());
		}
		LinkedList<String>allCitySort = new LinkedList<String>();
		allCitySort.addAll(allCity);
		Collections.sort(allCitySort);
		
		System.out.println("allCity:" + allCitySort);
		for(String c: allCitySort){
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
				cleanTable(librariesFlexTable);
				System.out.println("search selected lb:" + searchLb);
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
				getFavoriteLb();
				ArrayList<FavoriteObj> lof = new ArrayList<FavoriteObj>();
				for(Library lb : selectedLb){
					FavoriteObj temp = new FavoriteObj(lb.getId(), lb.getName(), lb.getBranch());
					if (!favorites.contains(temp)){
						lof.add(temp);
					}
				}
				favoriteService.addFavorites(lof, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable error) {
						// TODO Handle error
						System.out.println("add favorite fails");
						
					}

					@Override
					public void onSuccess(Void ignore) {
						// TODO Auto-generated method stub
						System.out.println("add favorite success");
						
					}
					
				});
			}
		});
		
	}
	
	private void displaySearchLibrary(ArrayList<Library> lolb) {
		for (Library lb : lolb){
			displaySearchLibrary(lb);
		}
		
	}

	private void displaySearchLibrary(final Library lb) {
		
		int row = librariesFlexTable.getRowCount();
		//ArrayList<Library> temp = new ArrayList<Library>();
		//temp.add(lb);
		//librariesFlexTable.setText(row, 0, lb.getName());
		Hyperlink l = new Hyperlink(lb.getName(), "http://lmgtfy.com/?q=" +lb.getName());
		librariesFlexTable.setWidget(row, 0, l);
		librariesFlexTable.setText(row, 1, lb.getBranch());
		//Window.open(l.getHTML(), "_blank", "");
		
		CheckBox selectButton = new CheckBox();
		selectButton.setValue(false);

		selectButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
//				Window.alert("It is " + (checked ? "" : "not ") + "checked");
				if (checked == true) {
					selectedLb.add(lb);
					System.out.println(selectedLb + "\n");
					// Window.alert(selectedLb.toString());
				} else {
					selectedLb.remove(lb);
					System.out.println(selectedLb + "\n");
					// Window.alert(selectedLb.toString());
				}
			}
		});
		librariesFlexTable.setWidget(row, 2, selectButton);
	}
	
	
	//dialogbox for AdminTab
	 public AdminDialog createDialogBox() {
		    // Create a dialog box and set the caption text
		    final AdminDialog dialogBox = new AdminDialog();

		    // Create a table to layout the content
		    VerticalPanel dialogContents = new VerticalPanel();
		    dialogContents.setSpacing(4);
		    dialogBox.setWidget(dialogContents);
		    
		    // Add a close button at the bottom of the dialog
		    Button closeButton = new Button(
		        "close", new ClickHandler() {
		          public void onClick(ClickEvent event) {
		            dialogBox.hide();
		          }
		        });
		    dialogContents.add(mainAdminTab);
		    dialogContents.add(closeButton);

		    // Return the dialog box
		    return dialogBox;
		  }
	 
	/**
	 * (favorite) Add Library to FlexTable. Executed when the user opens the
	 * favorite tab
	 */
	 private void FavoriteFunc(){
		 
		mainTab.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				// TODO Auto-generated method stub
				if (event.getSelectedItem() == 1) {
					System.out.println("favorite tab is selected");
					refleshFav();
				}
			}
		});

		removeFavorite.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				removeFav(selectedFav);
			}
		});
				
	 }
	 
	private void removeFav(final ArrayList<FavoriteObj> favs) {
		favoriteService.removeFavorites(favs, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable error) {
				// TODO Handle error
				System.out.println("remove favorite fails");
			}

			@Override
			public void onSuccess(Void ignore) {
				// TODO Auto-generated method stub
				System.out.println("remove favorite success");
				int removeNum = favs.size();
				selectedFav.clear();
				removeFavLabel.setText("You have removed " + removeNum
						+ " favorites.");
				refleshFav();
			}
		});
	}
	
	private void refleshFav(){
		cleanTable(favoriteTable);
		getFavoriteLb();
		displayFavorites(favorites);
	}
	 
	 private void getFavoriteLb(){
		 favoriteService.getFavorite(new AsyncCallback<ArrayList<FavoriteObj>>() {

			@Override
			public void onFailure(Throwable error) {
				// TODO Handle error
				System.out.println("get favorite fails");
				
			}

			@Override
			public void onSuccess(ArrayList<FavoriteObj> lof) {
				// TODO Auto-generated method stub
				favorites = lof;
				System.out.println("get favorite success:" + favorites);
				//displayFavorites(favorites);
			}
			 
		 });
	 }
	 
	 private void displayFavorites(ArrayList<FavoriteObj> lofav){
		 System.out.println("display Favorites is running");
		 for(FavoriteObj fav : lofav){
			 displayFavorite(fav);
		 }
	 }
	 
	 private void displayFavorite(final FavoriteObj fav) {
		 int row = favoriteTable.getRowCount();
		 favoriteTable.setText(row, 0, fav.getName());
		 favoriteTable.setText(row, 1, fav.getBranch());
		 
		 CheckBox selectButton = new CheckBox();
			selectButton.setValue(false);

			selectButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					boolean checked = ((CheckBox) event.getSource()).getValue();
					Window.alert("It is " + (checked ? "" : "not ") + "checked");
					if (checked == true) {
						selectedFav.add(fav);
						System.out.println("selectedFav: "+ selectedFav + "\n");
						// Window.alert(selectedLb.toString());
					} else {
						selectedFav.remove(fav);
						System.out.println("selectedFav: "+ selectedFav + "\n");
						// Window.alert(selectedLb.toString());
					}
				}
			});
			favoriteTable.setWidget(row, 2, selectButton);	 
		 
	 }
	 
	private void cleanTable(FlexTable table) {
		int row = table.getRowCount();
		for (int i = row - 1; i >= 1; i--) {
			table.removeRow(i);
		}
		// while (librariesFlexTable.getRowCount() > 1) {
		// librariesFlexTable.removeRow(librariesFlexTable.getRowCount()-1);
		// }
	}


}
