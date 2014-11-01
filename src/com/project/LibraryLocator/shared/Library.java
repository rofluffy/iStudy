package com.project.LibraryLocator.shared;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


//import com.google.appengine.api.users.User;
import com.google.maps.gwt.client.LatLng;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Library implements Serializable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private String id;
	@Persistent
	private String name;
	@Persistent
	private String branch;
	@Persistent
	private String phone;
	@Persistent
	private String address;
	@Persistent
	private String city;
	@Persistent
	private String postalCode;
	@Persistent
	private Double lat;
	@Persistent
	private Double lon;

//	 @Persistent
//	 private User user;

	public Library() {
	}

	public Library(String id, String name, String branch, String phone,
			String address, String city, String postalCode, Double lat,
			Double lon) {
		this.id = id;
		this.name = name;
		this.branch = branch;
		this.phone = phone;
		this.address = address;
		this.city = city;
		this.postalCode = postalCode;
		this.lat = lat;
		this.lon = lon;
	}

	public Library(String id, String name, String branch) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.name = name;
		this.branch = branch;
	}
	
	public Library(String id) {
		this.id = id;
	}
	
	public String getAllData(){
		return name + " " + branch + "\r\n" + phone + "\r\n " + address + " "+  city + " " +postalCode;
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getBranch() {
		return this.branch;
	}

	public String getPhone() {
		return this.phone;
	}

	public String getAddress() {
		return this.address;
	}

	public String getCity() {
		return this.city;
	}

	public String getPostalCode() {
		return this.postalCode;
	}

	public Double getLat() {
		return this.lat;
	}

	public Double getLon() {
		return this.lon;
	}
	
	public LatLng getLatlon() {
		return LatLng.create(this.lat, this.lon);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lat == null) ? 0 : lat.hashCode());
		result = prime * result + ((lon == null) ? 0 : lon.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result
				+ ((postalCode == null) ? 0 : postalCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Library other = (Library) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lat == null) {
			if (other.lat != null)
				return false;
		} else if (!lat.equals(other.lat))
			return false;
		if (lon == null) {
			if (other.lon != null)
				return false;
		} else if (!lon.equals(other.lon))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (postalCode == null) {
			if (other.postalCode != null)
				return false;
		} else if (!postalCode.equals(other.postalCode))
			return false;
		return true;
	}

}
