package com.project.LibraryLocator.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.InfoWindow;
import com.google.maps.gwt.client.InfoWindowOptions;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.Marker;
import com.google.maps.gwt.client.MarkerOptions;
import com.google.maps.gwt.client.MouseEvent;
import com.google.maps.gwt.client.Marker.DblClickHandler;
import com.project.LibraryLocator.shared.Library;

public class SearchTab extends TabFactory {

	// searchTab
	// things inside the search tab
	private VerticalPanel searchTab = new VerticalPanel();
	private HorizontalPanel searchPanel = new HorizontalPanel();
	// private TextBox searchInputBox = new TextBox(); // may use Suggest Box
	private FlexTable librariesFlexTable = new FlexTable();
	private ListBox searchBox = new ListBox();
	private Label numLb = new Label("");
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private HorizontalPanel pagePanel = new HorizontalPanel();
	// Buttons (for search)
	private Button searchButton = new Button("Search");
	private Button checkallButton = new Button("Check All"); // also able to use
																// in favorite?
	private Button toMapButton = new Button("To Map"); // also able to use in
														// favorite?
	private Button addFavoriteButton = new Button("Add Favorite");
	private Button AdminLogin = new Button("Admin Access");
	private Button adminSubmit = new Button("submit");
	private Button footerButton = new Button("testing");
	
	ArrayList<Library> searchLb = new ArrayList<Library>();

	public SearchTab() {
		// TODO Auto-generated constructor stub
	}

	@Override
	void buildTab() {
		// TODO Assemble search tab
		searchTab.add(searchPanel);
		searchTab.add(librariesFlexTable);
		searchTab.add(numLb);
		searchTab.add(buttonPanel);

		// TODO Assemble search panel
		searchPanel.add(searchBox);
		searchPanel.add(searchButton);

		// TODO Assemble button panel
		buttonPanel.add(addFavoriteButton);
		buttonPanel.add(toMapButton);
		buttonPanel.add(checkallButton);

	}

	@Override
	void buildTable() {
		// TODO create table for displaying libraries (search tab)
		librariesFlexTable.setText(0, 0, "Library");
		librariesFlexTable.setText(0, 1, "Branch");
		librariesFlexTable.setText(0, 2, "Select");

	}

	@Override
	void buildFunc() {
		System.out.println("search function is runing:" + libraries);
		// System.out.println("search function is runing");
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
				cleanTable(librariesFlexTable);
				System.out.println("search selected lb:" + searchLb);
				displayLibrary(searchLb);
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
						// TODO Handle error
						System.out.println("add favorite fails");

					}

					@Override
					public void onSuccess(Void ignore) {
						// TODO Auto-generated method stub
						Timer t = new Timer() {

							@Override
							public void run() {
								System.out.println("add favorite success");
							}

						};
						t.schedule(3000);

					}

				});
			}
		});
		
		toMapButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				for (Library lb : selectedLb) {
					mapSelectedLibrary(lb);	
				}
				
			}
		});

	}
	
	// TODO extract the buttons!!!!!!
	private void mapSelectedLibrary(final Library lb) {
		
		  
//	    final InfoWindow infowindow = InfoWindow.create(infowindowOpts);
	    final GoogleMap map = LibraryLocator.map;
	    final InfoWindowOptions infowindowOpts = LibraryLocator.infowindowOpts;
	    final InfoWindow infowindow = InfoWindow.create();
		
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
//            	infowindowOpts.setContent("Brach Name:" + lb.getName()
//			    		+ "Address:" + lb.getAddress() +"Postal Code:"+ lb.getPostalCode()
//			    		+"Phone Number:" + lb.getPhone());
            	//infowindowOpts.setContent(lb.getAllData());
			    infowindow.setOptions(infowindowOpts);
            	infowindow.open(map, marker);
            }
        });
	}

	@Override
	void displayLibrary(ArrayList<Library> lolb) {
		for (Library lb : lolb){
			displayLibrary(lb);
		}
		

	}

	@Override
	void displayLibrary(final Library lb) {
		int row = librariesFlexTable.getRowCount();
		//ArrayList<Library> temp = new ArrayList<Library>();
		//temp.add(lb);
		librariesFlexTable.setText(row, 0, lb.getName());
		librariesFlexTable.setText(row, 1, lb.getBranch());
		
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

}
