package com.project.LibraryLocator.shared;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class FavoriteObj extends Library {

	public FavoriteObj() {
		// TODO Auto-generated constructor stub
	}

	public FavoriteObj(String id, String name, String branch) {
		super(id, name, branch);
		// TODO Auto-generated constructor stub
	}

	public FavoriteObj(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

}
