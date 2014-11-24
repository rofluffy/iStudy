package com.project.LibraryLocator.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.maps.gwt.client.LatLng;
import com.project.LibraryLocator.shared.Library;

public class AdminTab extends TabFactory {

	// adminTab (testing atleast?), display all library and able to add new
	// library
	// things inside admin page
	//VerticalPanel adminTab = new VerticalPanel();
	//private VerticalPanel adminLoginPanel = new VerticalPanel();
	private HorizontalPanel addLibraryPanel = new HorizontalPanel();
	//private TextBox inputAdmin = new TextBox();
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
	
	int pageSize = 20;
	ArrayList<Library> subListLb = new ArrayList<Library>();

	public AdminTab() {
	}
	
	@Override
	void buildTab(){
		// Assemble admin Tab
		LibraryLocator.adminTab.add(pagePanel);
		LibraryLocator.adminTab.add(allLibraries);
		LibraryLocator.adminTab.add(addLibraryPanel);

		// Assemble Add library panel.
		addLibraryPanel.add(addLibraryTable);
		addLibraryPanel.add(addLibraryButton);
		addLibraryPanel.add(loadLibraryButton);
		buildTable();
		buildAddPanel();
		buildPagePanel();
	}

	@Override
	void buildTable() {
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
	}
	
	private void buildAddPanel() {
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
		
		// Move cursor focus to ALL input box.
		inputLibraryID.setFocus(true);
		
		// Listen for mouse events on the Add button
		addLibraryButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addLibrary();
			}
		});
		
		// TODO Listen for mouse event on the Load button
		loadLibraryButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				System.out.println("load library is click");
				libraries.clear();
				//addToDataStore();
				// loadLibraries();
			}
		});
				
	}
	
	private void buildPagePanel(){
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
							displayLibrary(subListLb);
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
							displayLibrary(subListLb);
							pageMessage.setText("");
						}
					}
					
				});
	}

	@Override
	void buildFunc() {
		// TODO Auto-generated method stub

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
					displayLibrary(subListLb);
					pageMessage.setText("");
				}
				
			});
			lob.add(b);
		}
		return lob;
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
				handleError(error);
				
			}

			@Override
			public void onSuccess(Void ignore) {
				// TODO Auto-generated method stub
				displayLibrary(lib);
			}
		});
		
	}

	@Override
	void displayLibrary(ArrayList<Library> lolb) {
		for (Library lb : lolb) {
			displayLibrary(lb);
		}

	}

	@Override
	void displayLibrary(Library lb) {
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
	
	@Override
	void run() {
		// TODO Auto-generated method stub
		buildTab();

	}

}
