package com.project.LibraryLocator.client;

import java.util.ArrayList;

import com.project.LibraryLocator.shared.FieldVerifier;
import com.project.LibraryLocator.shared.Library;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.DockPanel.DockLayoutConstant;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


/*
//import com.google.maps.gwt.client.MapWidget;
//import com.google.maps.gwt.client.LargeMapControl;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.Document;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.dom.client.Element;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapType;
import com.google.gwt.maps.client.MapTypeOptions;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;

import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
*/

import com.google.maps.gwt.client.Marker;
import com.google.maps.gwt.client.MarkerOptions;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.Document;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.GoogleMap.ZoomChangedHandler;
import com.google.maps.gwt.client.InfoWindow;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;
import com.google.maps.gwt.client.Point;

//
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class LibraryLocator implements EntryPoint {

	// set hyperlink in library class? when display in flextable?
	
	private DockPanel mainPanel = new DockPanel(); 
	private TabPanel mainTab = new TabPanel();
	private TabPanel mainAdminTab = new TabPanel();
	
	// searchTab
	// things inside the search tab
	private VerticalPanel searchTab = new VerticalPanel();
	private HorizontalPanel searchPanel = new HorizontalPanel();
	private TextBox searchInputBox = new TextBox();   // may use Suggest Box 
	private FlexTable librariesFlexTable = new FlexTable(); 
	//private CheckBox selectLibrary = new CheckBox();  // or radioButton? [develop in librariesFlexTable]
	private ListBox regionList = new ListBox();
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private HorizontalPanel buttonPanelfav = new HorizontalPanel();
	// Buttons (for search)
	private Button searchButton = new Button("Search");  
	private Button checkallButton = new Button("Check All");  // also able to use in favorite?
	private Button toMapButton = new Button("To Map");        // also able to use in favorite?
	private Button checkallButtonfav = new Button("Check All");  // the one in favorite tab
	private Button toMapButtonfav = new Button("To Map");         // the one in favorite tab
	private Button addFavoriteButton = new Button("Add Favorite");
	private Button testing = new Button("testing");

	
	// favoriteTab
	// things inside favorite
	private VerticalPanel favoriteTab = new VerticalPanel();
	private FlexTable favoriteTable = new FlexTable();
	//private CheckBox selectFavorite = new CheckBox(); //do this later
	// Buttons (for Favorite)
	private Button removeFavorite = new Button("Remove");
	
	// adminTab (testing atleast?), display all library and able to add new library
	// things inside admin page
	private VerticalPanel adminTab = new VerticalPanel();
	private HorizontalPanel addLibraryPanel = new HorizontalPanel();
	private TextBox inputLibraryID = new TextBox();     //
	private TextBox inputLibraryName = new TextBox();   // need input box for every attributes?
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
	
	
	
	// Buttons on mainPanel
	private HorizontalPanel mainButtonPanel = new HorizontalPanel();
	private Button socail1 = new Button("Google+");

	//map
	private GoogleMap map;
	  private static final int TILE_SIZE = 256;
	  private static final LatLng UBC = LatLng.create(41.850033, -87.6500523);

	//private ArrayList<Library> libraries = new ArrayList<Library>();  //list of library object

	//private final DataParseAsync DataParse = GWT.create(DataParse.class);
	
	private ArrayList<Library> libraries = new ArrayList<Library>();  //list of library object
	//private ArrayList<Library> libraries = new DataParseImpl().parseLibray();


	
//	private Label refleshLabel = new Label();   // not sure about this, do we need it? maybe for hyperlink part...
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		mainAdminTab.add(new Button("login"), "Admin Login");
		mainAdminTab.add(new ScrollPanel(adminTab),"Admin");
	
	    
		// TODO Assemble Main panel.
		mainPanel.add(mainButtonPanel, DockPanel.SOUTH);
		mainPanel.add(mainTab, DockPanel.WEST);
	
		
		LatLng myLatLng = LatLng.create(49.269893, -123.253268);
	    MapOptions myOptions = MapOptions.create();
	    myOptions.setZoom(8.0);
	    myOptions.setCenter(myLatLng);
	    myOptions.setMapTypeId(MapTypeId.ROADMAP);
	    GoogleMap map = GoogleMap.create(Document.get().getElementById("map"), myOptions);
	    
		

		mainTab.add(new ScrollPanel(searchTab), "Search");   // don't think the string after is very necessary, check later!
		mainTab.add(new ScrollPanel(favoriteTab), "Favorite");
		//mainTab.add(new ScrollPanel(adminTab), "Admin");
		
		// initialize default display tab
		mainTab.selectTab(0); // 2 is the admin one
		// style
		mainTab.getTabBar().addStyleName("tabPanel");
		mainTab.getDeckPanel().addStyleName("mainTab");   // dont see difference so far haha...
		
		// Assemble admin Tab
		adminTab.add(allLibraries);
		adminTab.add(addLibraryPanel);
		
		// create table for libraries (other attributes are still needed to develop) (A,B,C,H,I,J,L,M,N)(lower case on first letter)
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
		searchTab.add(buttonPanel);
		
		// TODO Assemble search panel
		searchPanel.add(searchInputBox);
		searchPanel.add(searchButton);
		
		// TODO create table for displaying libraries (search tab)
		librariesFlexTable.setText(0, 0, "Library");
		librariesFlexTable.setText(0, 1, "Select");
		
		// TODO Assemble button panel
		buttonPanel.add(addFavoriteButton);
		buttonPanel.add(toMapButton);
		buttonPanel.add(checkallButton);
		
		// TODO Assemble favorite tab
		favoriteTab.add(favoriteTable);
		favoriteTab.add(buttonPanelfav);
		
		
		// TODO create table for displaying libraries (favorite tab)
		favoriteTable.setText(0, 0, "Library");
		favoriteTable.setText(0, 1, "Select");
		
		// TODO Assemble button panel (remove button?)
		buttonPanelfav.add(removeFavorite);
		buttonPanelfav.add(toMapButtonfav);
		buttonPanelfav.add(checkallButtonfav);
		
		// TODO Assemble main button panel
		mainButtonPanel.add(socail1);
		
		// TODO Associate the Main panel with the HTML host page.
		RootPanel.get("libraryLocator").add(mainPanel);
		RootPanel.get("mainAdminTab").add(mainAdminTab);

		
		// TODO Move cursor focus to ALL input box.
		inputLibraryID.setFocus(true);  
		searchInputBox.setFocus(true);
		
		// Listen for mouse events on the Add button
		addLibraryButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addLibrary();
			}
		});
		
		// Listen for keyboard events in the input box.
		addLibraryButton.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					addLibrary();
				}
			}
		});
		
		displayLibrary(libraries);
		//loadLibraries();

  }
	

//	private void loadLibraries() {
//		// TODO not using dataparse, rather use libraryService
//		DataParse.parseLibrary(new AsyncCallback<ArrayList<Library>>() {
//			@Override
//			public void onFailure(Throwable error) {
//				// TODO handleError(error);
//			}
//
//			@Override
//			public void onSuccess(ArrayList<Library> lolb) {
//				displayLibrary(lolb);
//			}
//		});
//	}

	/**
	   * (admin) Add Library to FlexTable. Executed when the user clicks the addLibraryButton (NOT doing the keyHandler)
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
		
		final Library newLibrary = new Library(newID, newName, newBranch, newPhone, newAddress, newCity, newPostCode, newLat, newLon);
		
		// TODO Check if all the input box is not empty otherwise not able to add library and pop out an message to warn
		
		// TODO move mouse focus to the next input box by clicking up and down key
		
		// TODO check if the input text is valid
		
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
		ArrayList<String> loid = new ArrayList<String>();
		for (Library lb : libraries){
			loid.add(lb.getId());
		}
		
		if (loid.contains(newID)) {
			Window.alert("the Library is already exit!");
			return;
		}
		
		// TODO don't add library if lat and lon is already exist
		
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
		
		// TODO add the select button (using checkbox?) and deal with the click reaction
		CheckBox selectButton = new CheckBox();
		
		allLibraries.setWidget(row, 9, selectButton);
		
		// TODO Don't know if we want to have remove method here?
		
	}


	/*
	private void buildUi() {
	    // Open a map centered on UBC
	    LatLng UBC = LatLng.newInstance(49.269893, -123.253268);

	    final MapWidget map = new MapWidget(UBC, 2);
	    map.setSize("100%", "100%");
	    // Add some controls for the zoom level
	    map.addControl(new LargeMapControl());

	    // Add a marker
	    map.addOverlay(new Marker(UBC));

	    // Add an info window to highlight a point of interest
	    map.getInfoWindow().open(map.getCenter(),
	        new InfoWindowContent("This is for testing"));

	    mainPanel.add(map, DockPanel.CENTER);

	    // Add the map to the HTML host page
	    RootPanel.get("map").add(map);
	  }
	  */
	


	
	private void displayLibrary(ArrayList<Library> lolb) {
		for (Library lb : lolb) {
			displayLibrary(lb);
		}
	}

	private void displayLibrary(final Library lb) {
		// Add the stock to the table.
		int row = allLibraries.getRowCount();
		libraries.add(lb);
		allLibraries.setText(row, 0, lb.getId());
		allLibraries.setText(row, 1, lb.getName());
		allLibraries.setText(row, 2, lb.getBranch());
		allLibraries.setText(row, 3, lb.getPhone());
		allLibraries.setText(row, 4, lb.getAdress());
		allLibraries.setText(row, 5, lb.getCity());
		allLibraries.setText(row, 6, lb.getPostalCode());
		allLibraries.setText(row, 7, lb.getLat().toString());
		allLibraries.setText(row, 8, lb.getLon().toString());
		
		CheckBox selectButton = new CheckBox();
		allLibraries.setWidget(row, 9, selectButton);
		
	}
	
}

