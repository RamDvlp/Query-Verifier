

package application;

import java.io.File;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import model.Model;

public class HomeController {
	
	private Model model;
	@FXML
	private Label midLabel;
	@FXML
	private RadioButton columnWise;
	@FXML
	private RadioButton rowWise;
	@FXML
	private RadioButton cellWise;
	
	@FXML
	private ToggleGroup toggle;

	@FXML
	private Button browseCorrectBTN;
	@FXML
	private TextField correctPathTXT;
	@FXML
	private Button correctUploadBTN;
	@FXML
	private Label correctConfLABEL;
	
	
	public HomeController() {
		
	}
	
	
	
	public Model getModel() {
		return model;
	}


	public void setModel(Model model) {
		this.model = model;
	}

	public void browseForCorrectFile() {
		FileChooser fileChooser = new FileChooser();

        // Set the title of the file chooser dialog
        fileChooser.setTitle("Open Resource File");

        fileChooser.setInitialDirectory(new File("./"));
        // Show the file chooser dialog and get the selected file
        model.getHomeModel().setSelectedCorrectFile( fileChooser.showOpenDialog(null));

        if (model.getHomeModel().getSelectedCorrectFile() != null) {
            System.out.println("Selected File: " + model.getHomeModel().getSelectedCorrectFile().getAbsolutePath());
            // You can perform further actions with the selected file
            correctPathTXT.setText(model.getHomeModel().getSelectedCorrectFile().getAbsolutePath());
        } else {
            System.out.println("File selection cancelled.");
        }
	}
	
	public void setCheckMode() {
		
		model.getHomeModel().setSelectedModel( toggle.getSelectedToggle().toString());

	}
	
	public void uploadeCorrectFile() {
		System.out.println("pressed upload");
		int correct = model.getHomeModel().uploadQueries();
		correctConfLABEL.setText(correct + " Queries uploaded");
		correctConfLABEL.setVisible(true);
	}	
	
}
