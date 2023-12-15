package application;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
	@FXML
	private Button selectFileTooCheckBTN;
	@FXML
	private TextField fileToCheckPath_TXTF;
	@FXML
	private Button runFileButton;
	@FXML
	private TableView<Map<String, Object>> table;
	@FXML
	private Button runNextFromFileBTN;
	@FXML
	private Button runPreviousFromFileBTN;
	private UserErrors userError;
	private Group root;
	
	public HomeController() {
		userError = new UserErrors();
		//root = new Group(table);
	}
	
	
	
	public Model getModel() {
		return model;
	}


	public void setModel(Model model) {
		this.model = model;
	}

	public void browseForCorrectFile(Event event) {
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
        
        uploadeCorrectFile(event);
	}
	
	public void setCheckMode() {
		String info = toggle.getSelectedToggle().toString();
		
		if(info.contains("Column-Wise")) {
			info = "Column-Wise";	
		}
		 if(info.contains("Row-Wise")) {
			info = "Row-Wise";
		} if (info.contains("Cell-Wise"))
			info = "Cell-Wise";
		
		
		model.getHomeModel().setSelectedModel(info);

	}
	
	private void uploadeCorrectFile(Event event) {
		//System.out.println("pressed upload");
		Button clicked = (Button)((Node)event.getSource());
		
		int correct = model.getHomeModel().uploadQueries(clicked.getText());
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
	
	public void readFileToCheck(Event event) {
		FileChooser fileChooser = new FileChooser();

        // Set the title of the file chooser dialog
        fileChooser.setTitle("Open Resource File");

        fileChooser.setInitialDirectory(new File("./"));
        // Show the file chooser dialog and get the selected file
        model.getHomeModel().setFileToCheck(fileChooser.showOpenDialog(null));

        if (model.getHomeModel().getFileToCheck() != null) {
            System.out.println("Selected File: " + model.getHomeModel().getFileToCheck().getAbsolutePath());
            // You can perform further actions with the selected file
            fileToCheckPath_TXTF.setText(model.getHomeModel().getFileToCheck().getAbsolutePath());
        } else {
            System.out.println("File selection cancelled.");
        }
        
        uploadeCorrectFile(event);
		
	}
	
	public void runAllTestedFile() {
		if(toggle.getSelectedToggle() == null)
			userError.unselectedCheckMethod("Please Select mean of validation on the right pannel.");
		else System.out.println(toggle.getSelectedToggle());
		
		
	}
	
	public void runNextFromFile() {
		model.runTestedQueryNext(table);
	}
	
	public void runPreviousFromFile() {
		model.runTestedQueryPrevious(table);
	}
	
}
