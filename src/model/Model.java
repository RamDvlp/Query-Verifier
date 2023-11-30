package model;

import backSQL.DB_Service;

public class Model {
	
	private DBLogInModel logInModel;
	private HomeModel homeModel;
	private DB_Service dbService;
	
	
	
	public Model() {
		super();
		this.logInModel = new DBLogInModel();
		this.homeModel = new HomeModel();
		this.dbService = new DB_Service();
	}
	
	
	public DBLogInModel getLogInModel() {
		return logInModel;
	}
	
	public void setLogInModel(DBLogInModel logInModel) {
		this.logInModel = logInModel;
	}
	
	public HomeModel getHomeModel() {
		return homeModel;
	}
	
	public void setHomeModel(HomeModel homeModel) {
		this.homeModel = homeModel;
	}
	
	public DB_Service getDbService() {
		return dbService;
	}
	
	public void setDbService(DB_Service dbService) {
		this.dbService = dbService;
	}
	

}
