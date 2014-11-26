package com.project.LibraryLocator.server;


import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Admin {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private String id;
	@Persistent
	private String password;
	@Persistent
	private Boolean status;

	public Admin(String id, String psw,  Boolean status) {
		this.id= id;
		this.password = psw;
		this.status = status;
	}

	public String getpassword() {
		return this.password;
	}

	public Boolean getStatus() {
		return this.status;
	}
	
	public void setlogedStatus(){
		this.status = true;
	}
	
//
//	public void setAdmin(String password, Boolean status) {
//		this.password = password;
//		this.status = status;
//	}

	public void setPassword(String password) {
		this.password = password;
	}


}
