package application;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Model;

public class LogInController {
	
	private Model logInModel;
	@FXML
	private Button logInBTN;
	@FXML
	private Button cancelBTN;
	
	
	public void logIn() {
		
	}
	
	
	
	public Model getLogInModel() {
		return logInModel;
	}

	public void setLogInModel(Model logInModel) {
		this.logInModel = logInModel;
	}

	public void cancelLogIn(javafx.event.ActionEvent event) {
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		window.close();
		
	}

}
