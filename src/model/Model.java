package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

import backSQL.DB_Service;
import javafx.scene.control.TableView;

public class Model {
	
	private DBLogInModel logInModel;
	private HomeModel homeModel;
	private DB_Service dbService;
	private ArrayList<RS_Container> testedContainer;
	private ArrayList<RS_Container> correctContainer;
	private ContainerIterator containerIter;
	
	
	public Model() {
		super();
		this.logInModel = new DBLogInModel();
		this.homeModel = new HomeModel();
		this.dbService = new DB_Service();
		this.correctContainer = new ArrayList<RS_Container>();
		this.testedContainer = new ArrayList<RS_Container>();
		this.containerIter = new ContainerIterator(testedContainer);
	}
	
	
	public ContainerIterator getContainerIter() {
		return containerIter;
	}

	public void setContainerIter(ContainerIterator containerIter) {
		this.containerIter = containerIter;
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
	
	public ArrayList<RS_Container> getTestedContainer() {
		return testedContainer;
	}

	public void setTestedContainer(ArrayList<RS_Container> testedContainer) {
		this.testedContainer = testedContainer;
	}

	public ArrayList<RS_Container> getCorrectContainer() {
		return correctContainer;
	}

	public void setCorrectContainer(ArrayList<RS_Container> correctContainer) {
		this.correctContainer = correctContainer;
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


	public boolean[] runAllInFile() {
		int index = 0;
		boolean [] ans = new boolean [correctContainer.size()];
		Comparator<RS_Container> comp = null;
		switch (homeModel.getSelectedModel()) {
		case "Column-Wise":
			comp = new ComperatorByColumn();
			break;

		case "Row-Wise":
			
			break;

		case "Cell-Wise":
	
			break;

		default:
			break;
		}
		
		ContainerIterator correctIter = new ContainerIterator(correctContainer);
		while(correctIter.hasNext()) {
			ans[index] = comp.compare(correctIter.next(), containerIter.next())!=0 ?  false : true;
			index++;
			
		}
		
		return ans;
	
	}


	public void fillContainer(String mode) {
		if(mode.equals("Select File")) {
			testedContainer.clear();
			homeModel.setIter(new QueryIterator(homeModel.getTestedQueries()));
			dbService.fillContainer(this.homeModel.getIter(),this.testedContainer);
			
		} else {
			correctContainer.clear();
			homeModel.setIter(new QueryIterator(homeModel.getCorrectQueries()));
			dbService.fillContainer(this.homeModel.getIter(),this.correctContainer);
			
		}
		
		
			
	}
	

}
