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
public class Favorite extends Library {

	@Persistent
	private User user;
	@Persistent
	private Date createDate;

	public Favorite() {
		this.createDate = new Date();
	}

	public Favorite(User user, String id, String name, String branch) {
		super(id, name, branch);
		this.user = user;
		
	}
	
	public Favorite(User user, String id) {
		super(id);
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreateDate() {
		return createDate;
	}

}
