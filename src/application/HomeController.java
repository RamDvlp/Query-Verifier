

package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeController {
	@FXML
	private Label midLabel;

	public void clickLeftButton() {  
		midLabel.setText("The button the left was clicked.");  
     }

	public void clickRightButton() {  
		midLabel.setText("The button the right was clicked.");  
     }
	
	public void clickUpButton() {  
		midLabel.setText("The button the top was clicked.");  
    }
}
