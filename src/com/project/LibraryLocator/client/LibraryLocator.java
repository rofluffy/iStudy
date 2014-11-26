package com.project.LibraryLocator.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.jdo.Query;

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
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
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

	// panel for login and logout
	static LoginInfo loginInfo = new LoginInfo();
	// panel for login and logout
	private VerticalPanel loginPanel = new VerticalPanel();
	// label and link for login
	private Label loginLabel = new Label(
			"Please sign in to your Google Account to access your Favorite library list.");
	private Anchor signInLink = new Anchor("Sign In");
	// label and link for logout
	private Label logoutLabel = new Label(
			"Logout here if you want to stop using this amazing app, but seriously, why would you?");
	private Anchor signOutLink = new Anchor("Sign Out");

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
	// Buttons (for search)
	private Button searchButton = new Button("Search");

	private Button checkAllButton = new Button("Check All"); 
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
	private Button checkAllButtonFav = new Button("Check All"); // the one in favorite tab
	private Button toMapButtonfav = new Button("To Map"); // the one in favorite tab

	// adminTab (testing atleast?), display all library and able to add new library
	// things inside admin page
	static VerticalPanel adminTab = new VerticalPanel();
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
	private HorizontalPanel pagePanel = new HorizontalPanel();
	private Label pageMessage = new Label();
	// Buttons (for admin)
	private Button addLibraryButton = new Button("Add");
	private Button loadLibraryButton = new Button("Load Libraries");
	private int pageIndex = 0;
	private Button prev = new Button("<<<");
	private Button next = new Button(">>>");
	private ArrayList<Button> loPage = new ArrayList<Button>();

	// Buttons on mainPanel
	private HorizontalPanel mainButtonPanel = new HorizontalPanel();
	private Button socail1 = new Button("Google+");

	// map
	static GoogleMap map;
	private static final int TILE_SIZE = 256;
	private static final LatLng UBC = LatLng.create(41.850033, -87.6500523);
	private InfoWindow infowindow = InfoWindow.create();
	static InfoWindowOptions infowindowOpts = InfoWindowOptions.create();


	// Service classes	
	private LibraryServiceAsync libraryService = GWT.create(LibraryService.class);
	private FavoriteServiceAsync favoriteService = GWT.create(FavoriteService.class);
	
	private ArrayList<Library> libraries = new ArrayList<Library>(); // list of library object
	private ArrayList<Library> selectedLb = new ArrayList<Library>(); // when refactoring, each tab has its own selected list
	private ArrayList<Library> searchLb = new ArrayList<Library>();
	private ArrayList<Library> favorites = new ArrayList<Library>(); // list of favorites REFACTOR to favorite tab?
	private ArrayList<Library> clientFav = new ArrayList<Library>();
	private ArrayList<Library> selectedFav = new ArrayList<Library>(); // favorite's selected list
	private ArrayList<CheckBox> libCheckBox = new ArrayList<CheckBox>();
	private ArrayList<CheckBox> favCheckBox = new ArrayList<CheckBox>();
	int pageSize = 20;

	ArrayList<Library> subListLb = new ArrayList<Library>();
	
	private long start;
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
		DataPanel.setWidth("1100px");
		mainAdminTab.add(DataPanel,"Database");
		mainAdminTab.selectTab(0);	
		//create a new dialogBox for admin
		final AdminDialog dialogBox = createDialogBox();
	    dialogBox.setGlassEnabled(true);
	    dialogBox.setAnimationEnabled(true);
	    //mainAdminTab.getTabBar().setVisible(false);

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
	    	    	//displayAdminLibrary(libraries);
	    	    	subListLb = createSub(libraries);
	    	    	displayAdminLibrary(subListLb);
	    	    	System.out.println("print sublist:" + subListLb);
	    	    }
	    	  }
	    });
	    
	    // load libraries anyway
	    loadLibraries();
	    // TODO load favorite when login
	    //getFavoriteLb();
	    
	 // Check login status using login service.
 		LoginServiceAsync loginService = GWT.create(LoginService.class);
 		loginService.login(GWT.getHostPageBaseURL(),
 				new AsyncCallback<LoginInfo>() {
 					public void onFailure(Throwable error) {
 						//TODO Handle error

 					}

 					public void onSuccess(LoginInfo result) {
 						loginInfo = result;
 						if (loginInfo.isLoggedIn()) {
 							// TODO deal with this later
 							
 							//loadLibraryLocator();
 							getFavoriteLb();
 							loadLogout();
 							
 						} else {
 							loadLogin();
 							//loadLibraryLocator();
 						}
 					}
 				});							


	}

	private void loadLogin() {
		// Assemble login panel.
//		if (loginInfo.isLoggedIn()) {
//			// if already logged in, show logout button and link
//			signOutLink.setHref(loginInfo.getLogoutUrl());
//			loginPanel.add(logoutLabel);
//			loginPanel.add(signOutLink);
//		} else {
			signInLink.setHref(loginInfo.getLoginUrl());
			loginPanel.add(loginLabel);
			loginPanel.add(signInLink);
//		}
		RootPanel.get("login").add(loginPanel);
	}
	
	private void loadLogout() {
		signOutLink.setHref(loginInfo.getLogoutUrl());
		// replace login label and link with logout label and link
		loginPanel.add(logoutLabel);
		loginPanel.add(signOutLink);
		// still load loginPanel, which now shows the logout label and link
		RootPanel.get("login").add(loginPanel);
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

		//myOptions.setDisableDefaultUi(true);//disabling map ui
		final GoogleMap map = GoogleMap.create(Document.get().getElementById("map"),
				myOptions);

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
		SearchPanel.setHeight("300px");

		// Assemble admin Tab
		adminTab.add(pagePanel);
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
		
		// Assemble page Panel
		pagePanel.add(pageMessage);
		pagePanel.add(prev);
		loPage = createLoPage(libraries);
		System.out.println("pagesize:" + loPage);
		for(int i = 0; i < loPage.size(); i++){
			pagePanel.add(loPage.get(i));
		}
		pagePanel.add(next);
		prev.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (pageIndex == 0){
					pageMessage.setText("It's already the front page =)");
				}else{
					pageIndex -= pageSize;
					System.out.println("PageIndex is " + pageIndex);
					subListLb = createSub(libraries);
					cleanTable(allLibraries);
					displayAdminLibrary(subListLb);
					pageMessage.setText("");
				}
			}			
		});
		next.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if ((pageIndex + pageSize) >= libraries.size()-1){
					pageMessage.setText("It's already the last page =)");
				}else{
					pageIndex += pageSize;
					System.out.println("PageIndex is " + pageIndex);
					subListLb = createSub(libraries);
					cleanTable(allLibraries);
					displayAdminLibrary(subListLb);
					pageMessage.setText("");
				}
			}
			
		});

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
		buttonPanel.add(checkAllButton);

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
		buttonPanelfav.add(checkAllButtonFav);

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
		            	infowindowOpts.setContent("<header><strong>" + lb.getName() + "</strong></header>" + "<p> Brach Name:" + lb.getName() +"<br />"
					    		+ "Address:" + lb.getAddress() +"<br />"  +"Postal Code:"+ lb.getPostalCode() +"<br />" 
					    		+"Phone Number:" + lb.getPhone() +"</p>");
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
		//loadLibraries();
		System.out.println("check libraries:" + libraries);

		Timer t = new Timer(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				SearchFunc();
				FavoriteFunc();
			}

		};
		
		// delay for 3 seconds
		t.schedule(3000);
		
	}
	private ArrayList<Library> createSub(ArrayList<Library> lolb){
		ArrayList<Library> returnLb = new ArrayList<Library>();
		boolean check = (lolb.size()%pageSize != 0) && (pageIndex + pageSize >= lolb.size());
    	List<Library> temp = lolb.subList(pageIndex, pageIndex + (check? lolb.size()%pageSize : pageSize));
    	for (Library lb : temp){
    		returnLb.add(lb);
    	}
		return returnLb;
	}
	
	private ArrayList<Button> createLoPage(ArrayList<Library> lolb){
		int temp;
		System.out.println(libraries.size());
		if (lolb.size() % pageSize != 0){
			temp = lolb.size()/pageSize + 1;
		}else{
			temp = lolb.size()/pageSize;
		}
		ArrayList<Button> lob = new ArrayList<Button>();
		for(int i = 1; i <= temp; i++){
			Button b = new Button(String.valueOf(i));
			final int tempI = i;
			b.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					System.out.println("Page " + tempI + " is clicked");
					pageIndex = (tempI -1)*pageSize;
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
				//SearchBoxForLibary();
				System.out.println("loadLibraries: " + libraries);
				Timer t = new Timer(){

					@Override
					public void run() {
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

		/*		selectButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				Window.alert("It is " + (checked ? "" : "not ") + "checked");
				if (checked == true) {
					selectedLb.add(newLibrary);
					System.out.println(selectedLb + "\n");
				} else {
					selectedLb.remove(newLibrary);
					System.out.println(selectedLb + "\n");
				}
			}
		});*/

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
				// display new lb when adding success
				displayAdminLibrary(lib);
			}
		});

	}
	
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
	private void SearchFunc(){
		System.out.println("search function is runing:" + libraries);
		//System.out.println("search function is runing");
		searchBox.setFocus(true);
		searchBox.addItem("----Please Selet a City----");
		Set<String> allCity = new HashSet<String>();
		for(Library l : libraries){
			allCity.add(l.getCity());
		}
		LinkedList<String>allCitySort = new LinkedList<String>();
		allCitySort.addAll(allCity);
		System.out.println("check allCitySort:" + allCitySort);
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
				for(Library lb : selectedLb){
					loid.add(lb.getId());
				}
				favoriteService.addFavorites(loid, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable error) {
						// TODO Handle error
						System.out.println("add favorite fails");

					}

					@Override
					public void onSuccess(Void ignore) {
						// when the database adding is success
						for(Library lb : selectedLb){
							clientFav.add(lb);
						}
						
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
		TabFactory tf = new SearchTab();
		librariesFlexTable.setWidget(row, 0, tf.nameHyprLink(lb));
		librariesFlexTable.setText(row, 1, lb.getBranch());
		CheckBox selectButton = new CheckBox();
		selectButton.setValue(false);
		libCheckBox.add(selectButton);

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
					setButtonText(checkAllButton, "Check All");
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
				
				for (Library lb : selectedFav){
					loid.add(lb.getId());
				}
				// remove the library in clientFav	
				for(int i=0; i<clientFav.size(); i++){
					for(int j=0; j<selectedFav.size(); j++){
						if(clientFav.get(i).equals(selectedFav.get(j))){
							clientFav.remove(i);
						}
					}
				}
				
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
				// TODO Handle error
				System.out.println("remove favorite fails");
			}

			@Override
			public void onSuccess(Void ignore) {
				System.out.println("remove favorite success");
				int removeNum = loid.size();
				clearSelectedFav();
				removeFavLabel.setText("You have removed " + removeNum
						+ " favorites.");
				//refleshFav();
			}
		});
	}

	private void refleshFav(){
		cleanTable(favoriteTable);
		displayFavorites(clientFav);

	}

	private void getFavoriteLb(){
		favoriteService.getFavorite(new AsyncCallback<ArrayList<String>>() {

			@Override
			public void onFailure(Throwable error) {
				// TODO Handle error
				System.out.println("get favorite fails");

			}

			@Override
			public void onSuccess(final ArrayList<String> loid) {
				Timer t = new Timer(){

					@Override
					public void run() {
						favCheckBox.clear();
						favorites.clear();
						for(String id : loid){
							for(Library lb : libraries){
								if (id.matches(lb.getId())){
									favorites.add(lb);
								}
							}
						}
						System.out.println("get favorite success: (num)" + favorites.size() + favorites);
						clientFav = favorites;
						System.out.println("client favorite success: (num)" + clientFav.size() + clientFav);
						//displayFavorites(favorites);
						//displayFavorites(clientFav);

					}

				};
				// delay 2 sec
				t.schedule(2000);
			}

		});
	}

	private void displayFavorites(ArrayList<Library> lof){
		System.out.println("display Favorites is running");
		for(Library lb : lof){
			displayFavorite(lb);
		}
	}

	private void displayFavorite(final Library fav) {
		int row = favoriteTable.getRowCount();
		favoriteTable.setText(row, 0, fav.getName());
		favoriteTable.setText(row, 1, fav.getBranch());

		CheckBox selectButton = new CheckBox();
		selectButton.setValue(false);
		favCheckBox.add(selectButton);

		selectButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				//Window.alert("It is " + (checked ? "" : "not ") + "checked");
				if (checked == true) {
					selectedFav.add(fav);
					System.out.println("selectedFav: "+ selectedFav + "\n");
					// Window.alert(selectedLb.toString());
				} else {
					selectedFav.remove(fav);
					System.out.println("selectedFav: "+ selectedFav + "\n");
					checkAllButtonFav.setText("Check all");
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

	private void checkAll(){

		checkAllButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (!isAllChecked(libCheckBox)){
					checkAllItems(libCheckBox,checkAllButton);
					addSelected(selectedLb, searchLb);
				}else {
					uncheckAllItems(libCheckBox,checkAllButton);
					clearSelected();
				}
				System.out.println("Check all ran");
			}
		});
	}
	private void checkAllFav(){

		checkAllButtonFav.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (!isAllChecked(favCheckBox)){
					checkAllItems(favCheckBox,checkAllButtonFav);
					addSelected(selectedFav, favorites);
				}else {
					uncheckAllItems(favCheckBox,checkAllButtonFav);
					clearSelectedFav();
					System.out.println("Unchecked all selected in fav");
				}
				System.out.println("Check all ran");

			}
		});
	}

	private void addSelected(ArrayList<Library> selected, ArrayList<Library> displayedLb) {
		selected.addAll(displayedLb);
		for(Library l: displayedLb){
			System.out.println(l.getBranch()+ "is selected");
		}
	}

	private void clearSelected() {
		selectedLb.clear();
	}

	private void setButtonText(Button b,String s) {
		b.setText(s);
	}

	private void clearCheckBoxList(ArrayList<CheckBox> listOfCheckBox) {
		listOfCheckBox.clear();
	}

	private boolean isAllChecked(ArrayList<CheckBox> listOfCheckBox) {
		System.out.println("numebr of checkbox in list " +listOfCheckBox.size());
		for(CheckBox c: listOfCheckBox){
			if (c.getValue()== false){
				return false;
			}
		}
		return true;
		
	}

	private void checkAllItems(ArrayList<CheckBox> listOfCheckBox, Button b) {
		for(CheckBox c: listOfCheckBox){
			c.setValue(true);
			setButtonText(b, "Uncheck All");
		}
	}

	private void uncheckAllItems(ArrayList<CheckBox> listOfCheckBox,Button b) {
		for(CheckBox c: listOfCheckBox){
			c.setValue(false);
		}
		setButtonText(b,"Check All");
	}

	private void clearSelectedFav() {
		selectedFav.clear();
	}
		
		
}
