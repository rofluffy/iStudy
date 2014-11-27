package com.project.LibraryLocator.server;

import java.util.List;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.project.LibraryLocator.client.AdminService;
import com.project.LibraryLocator.client.NotLoggedInException;

@SuppressWarnings("serial")
public class AdminServiceImpl extends RemoteServiceServlet implements
		AdminService {

	private static final Logger LOG = Logger
			.getLogger(FavoriteServiceImpl.class.getName());
	private static final PersistenceManagerFactory PMF = JDOHelper
			.getPersistenceManagerFactory("transactions-optional"); 
	

//
//	@SuppressWarnings("unchecked")
//	@Override
//	public String getAdmin() throws NotLoggedInException{
//		// TODO Test require
//		System.out.println("getpassword is running");
//		
////		if (getStatus() = true)
//		PersistenceManager pm = getPersistenceManager();
//		Admin temp = new Admin();
//		try {
//			Query q = pm.newQuery(Admin.class);
//			q.declareParameters("com.google.appengine.api.users.User u");
//			q.setOrdering("createDate");
//			temp = (Admin) q.execute(getAdmin());
//						
//		} finally {
//			pm.close();
//		}
//		return temp;
//	}
	
	
	public Boolean submitMatch(String submit) {
		
      System.out.println("submitMatch is running");
      PersistenceManager pm = getPersistenceManager();
      Boolean match = new Boolean(false);
		try {
			Query q = pm.newQuery(Admin.class);
			List<Admin> adm = (List<Admin>) q.execute();
			if ( adm.get(0).getStatus()==false){
				System.out.println("ready to sign in");
				if (submit.matches(adm.get(0).getpassword())){
					match = true;
					adm.get(0).setlogedStatus();
					System.out.println("login success");
				}
				
			}else{
			Window.alert("admin already login");
			}
		} finally {
			pm.close();
		}
		return match;
	}

	private PersistenceManager getPersistenceManager() {
		return PMF.getPersistenceManager();
	}
	
	public void logout(){
		addAdmin();
		
	}
	
	 public void addAdmin(){
		PersistenceManager pm = getPersistenceManager();
		String psw = new String("andrewcares");
		try{
			String id= new String("0");
			pm.makePersistent(new Admin(id,psw,false));
		} finally {
			pm.close();
		}
		
	}
	 
	
	 
	



}
