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
	private UserErrors userErrors;
	
	
	
	@FXML
	private void initialize() {
		userErrors = new UserErrors();
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
		
		if(URL_TXTF.getText().equals("")) {
			userErrors.userInputError("Please Enter a URL of the DB server");
			return;
		}
		if(UserTXTF.getText().equals("")) {
			userErrors.userInputError("Please Enter a User of the DB server");
			return;
		}
		if(password_TXT_fld.getText().equals("")) {
			userErrors.userInputError("Please Enter a Password of the DB server");
			return;
		}
		
		if(!logInModel.getDbService().testConnection(URL_TXTF.getText(),UserTXTF.getText(),password_TXT_fld.getText())) {
			userErrors.invalidInputError("Connection to DataBase failed.\n"
					+ "Please Validate the URL, User and password.\n"
					+ "Also, make sure that the Database server is online.");
		    return;
		}
		
		logInModel.getLogInModel().setProperties(URL_TXTF.getText(),UserTXTF.getText(),password_TXT_fld.getText());
		logInModel.getDbService().establishConnection();
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		window.close();
	}

}
