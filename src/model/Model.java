package model;

import java.util.Map;

import backSQL.DB_Service;
import javafx.scene.control.TableView;

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
	
	public void runTestedQueryNext(TableView<Object> table) {
		
		if(this.homeModel.getIter().hasNext()) {
			String query = this.homeModel.getIter().next();
			System.out.println(query);
			table.getColumns().clear();
			dbService.runQuery(query, table);
		} else {
			return;
		}
			
	}

	public void runTestedQueryPrevious(TableView<Object> table) {
		if(homeModel.getIter().hasPrevious()) {
			String query = this.homeModel.getIter().previous();
			System.out.println(query);
			table.getColumns().clear();
			dbService.runQuery(query, table);
	
		} else {
			return;
		}
				
	}


	public void runAllInFile(TableView<Object> table) {
		dbService.runComparison(table,homeModel.getSelectedModel(),homeModel.getCorrectQueries(),homeModel.getTestedQueries());
		
	}
	

}
