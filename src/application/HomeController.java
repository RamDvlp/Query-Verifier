

package application;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
	
	public void openLogInScreen(ActionEvent event) {
		Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DB_Selection_and_Login.fxml"));
			AnchorPane loginPane = fxmlLoader.load(); 
			Stage loginStage = new Stage();
			
			
			
			Scene loginScene = new Scene(loginPane, 600, 500);
			loginStage.initModality(Modality.APPLICATION_MODAL);
			loginStage.setResizable(false);
			
			loginStage.setOnCloseRequest(e -> {
			    e.consume(); // Consume the event to prevent the stage from closing
			});
			
			loginStage.setTitle("Connect to DB");
			loginStage.setScene(loginScene);
			
			loginStage.initOwner(primaryStage);
			
			//MVC model assignment 
			LogInController logController = fxmlLoader.getController();
			logController.setLogInModel(this.model);
			
			loginStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		

	}
	
	public void quitApp(ActionEvent event) {
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		window.close();
	}
	
}
