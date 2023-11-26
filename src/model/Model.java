package model;

public class Model {
	
	private DBLogInModel logInModel;
	private HomeModel homeModel;
	
	
	
	public Model() {
		super();
		this.logInModel = new DBLogInModel();
		this.homeModel = new HomeModel();
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
	

}
