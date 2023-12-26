package application;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.text.StyledEditorKit.BoldAction;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Model;
import model.RS_Container;

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
	private TableView<Object> table;
	@FXML
	private Button runNextFromFileBTN;
	@FXML
	private Button runPreviousFromFileBTN;
	private UserErrors userError;
	
	
	public HomeController() {
		userError = new UserErrors();
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
            //System.out.println("Selected File: " + model.getHomeModel().getSelectedCorrectFile().getAbsolutePath());
            // You can perform further actions with the selected file
            correctPathTXT.setText(model.getHomeModel().getSelectedCorrectFile().getAbsolutePath());
        } else {
            //System.out.println("File selection cancelled.");
            return;
        }
        
        uploadeFile(event);
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
	
	private void uploadeFile(Event event) {
		//System.out.println("pressed upload");
		Button clicked = (Button)((Node)event.getSource());
		
		int correct = model.getHomeModel().uploadQueries(clicked.getText());
		correctConfLABEL.setText(correct + " Queries uploaded"); 
		correctConfLABEL.setVisible(true);
		//runFileButton.setDisable(true); //not necessary - to fast for user to notice
		Runnable rn = new Runnable() {
			
			@Override
			public void run() {
				model.fillContainer(clicked.getText());
				//runFileButton.setDisable(false);
			}
		};
		
		Thread runQ = new Thread(rn);
		runQ.run();
		
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
            //System.out.println("Selected File: " + model.getHomeModel().getFileToCheck().getAbsolutePath());
            // You can perform further actions with the selected file
            fileToCheckPath_TXTF.setText(model.getHomeModel().getFileToCheck().getAbsolutePath());
        } else {
            //System.out.println("File selection cancelled.");
            return;
        }
        
        uploadeFile(event);
        runNextFromFileBTN.setDisable(false);
        runPreviousFromFileBTN.setDisable(false);
		
	}
	
	public void runAllTestedFile() {
		if(toggle.getSelectedToggle() == null) {
			userError.unselectedCheckMethod("Please Select mean of validation on the right pannel.");
			return;
		} else {
			System.out.println(toggle.getSelectedToggle());
		}
		if(correctPathTXT.getText().equals("Path:/")) {
			userError.userInputError("Please Upload a Correct Results File");
			return;
		}
		if(fileToCheckPath_TXTF.getText().equals("Path:/")) {
			userError.userInputError("Please Upload a File to check");
			return;
		}
		
		boolean[] answers =  model.runAllInFile();
		populateTable(answers);
		
		
		
	}



	public void runNextFromFile() {
		if(model.getContainerIter().hasNext())
			populateTable(model.getContainerIter().next());
	}
	

	public void runPreviousFromFile() {
		if(model.getContainerIter().hasPrevious())
			populateTable(model.getContainerIter().previous());
	}


	private void populateTable(RS_Container container) {
		table.getColumns().clear();

		if (!container.getData().isEmpty()) {
            Map<String, Object> firstRow = container.getData().get(0);

            // Add columns using keys from the first entry in data
            for (String columnName : firstRow.keySet()) {
                TableColumn<Object, Object> column = new TableColumn<>(columnName);

                // Set cell value factory using PropertyValueFactory
                column.setCellValueFactory(cellData -> new SimpleObjectProperty<>(((Map<String,Object>) cellData.getValue()).get(columnName)));
                column.setCellFactory(tc -> new TableCell<Object, Object>() {
                    @Override
                    protected void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.toString());
                        }
                    }
                });  
                table.getColumns().add(column);
            }
            
            // Add data to ObservableList
            ObservableList<Object> data = FXCollections.observableArrayList();
            data.addAll(container.getData());
            
            // Set items to TableView
            table.setItems(data);
        }
		//table.refresh();
	}
	
	
	private void populateTable(boolean[] answers) {
		table.getColumns().clear();


		TableColumn<Object, String> column = new TableColumn<>("Result");
		column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().toString()));
		column.setCellFactory(tc -> new TableCell<Object, String>() {
		    @Override
		    protected void updateItem(String item, boolean empty) {
		        super.updateItem(item, empty);

		        if (item == null || empty) {
		            setGraphic(null);
		        } else {
		            Label label = new Label(item);
		            label.setStyle("-fx-font-weight: bold;");
		            if (item.equals("Correct")) {
		                label.setTextFill(Color.GREEN);
		            } else {
		                label.setTextFill(Color.RED);
		            }
		            setGraphic(label);
		        }
		    }
		});

		table.getColumns().add(column);

		// Add data to ObservableList
		ObservableList<Object> data = FXCollections.observableArrayList();

		for (int i = 0; i < answers.length; i++) {
		    if (answers[i]) {
		        data.add("Correct");
		    } else {
		        data.add("Incorrect");
		    }
		}

		// Set items to TableView
		table.setItems(data);        
		
	}



	
}
