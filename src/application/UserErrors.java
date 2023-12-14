package application;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class UserErrors {

	private Alert alert;
	
	
	
	public UserErrors() {
		super();
		this.alert = new Alert(AlertType.WARNING);
	}

	public void userInputError(String messege) {
		this.alert = new Alert(AlertType.WARNING);
		alert.setContentText(messege);
		alert.show();
	}

	public void invalidInputError(String messege) {
		alert.setAlertType(AlertType.ERROR);
		alert.setContentText(messege);
		alert.show();
		
	}

	public void succsessfullLogIn(String messege) {
		alert.setAlertType(AlertType.INFORMATION);
		alert.setContentText(messege);
		alert.show();
		
	}
	
	public void unselectedCheckMethod(String messege) {
		alert.setAlertType(AlertType.WARNING);
		alert.setContentText(messege);
		alert.show();
	}

}
