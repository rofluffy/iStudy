package com.project.LibraryLocator.server;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.users.User;
import com.project.LibraryLocator.shared.Library;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Favorite {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private String id;
	@Persistent
	private User user;
	@Persistent
	private Date createDate;

	public Favorite() {
		this.createDate = new Date();
	}

	public Favorite(User user, String id) {
		this.id = id;
		this.user = user;
	}

	public String getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

}
