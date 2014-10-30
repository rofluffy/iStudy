package com.project.LibraryLocator.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.project.LibraryLocator.shared.Library;

import au.com.bytecode.opencsv.CSVReader;

public class DataParseImpl {
	private URL libraryData;
	private CSVReader reader;
	private Reader lib;
	private List<String[]> dataList;

	public DataParseImpl() {
		this.downloadData();
		this.readFile();

	}

	// return true if data download correctly
	private void downloadData() {
		try {
			libraryData = new URL(
					"http://pub.data.gov.bc.ca/datasets/175528/BC_Public_Library_Systems_Locations_and_Branch_Data.csv");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error,loading again");
		}
		try {
			lib = new InputStreamReader(libraryData.openStream());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error, try again");

		}

	}

	private void readFile() {
		reader = new CSVReader(lib);
		try {
			dataList = reader.readAll();
			System.out.println("dataList: "+dataList);

		} catch (IOException e) {
			System.out.println("Error reading file");
		}

	}

	public ArrayList<Library> parseLibrary() {
		ArrayList<Library> libraryList = new ArrayList<Library>();
		dataList.remove(0);
		for (String[] arr : dataList) {
			libraryList.add(new Library(arr[0], arr[1], arr[2], arr[7], arr[8],
					arr[9], arr[11], Double.parseDouble(arr[12]), Double
							.parseDouble(arr[13])));
		}
		return libraryList;
	}

	public void parseAll() {
		System.out.println("parseAll is runing");
		this.downloadData();
		this.readFile();
	}

}
