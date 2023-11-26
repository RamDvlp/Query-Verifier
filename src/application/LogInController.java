package application;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Model;

public class LogInController {
	
	private Model logInModel;
	@FXML
	private Button logInBTN;
	@FXML
	private Button cancelBTN;
	@FXML
	private TextField URL_TXTF;
	@FXML
	private TextField UserTXTF;
	@FXML
	private TextField password_TXT_fld;
	
	
	
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
	
	public void initLogIn(javafx.event.ActionEvent event) {
		logInModel.getLogInModel().setProperties(URL_TXTF.getText(),UserTXTF.getText(),password_TXT_fld.getText());
	}

}
