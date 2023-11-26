package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.HomeModel;
import model.Model;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			
			Model model = new Model();
			
			FXMLLoader mainloader = new FXMLLoader(getClass().getResource("Home.fxml"));
			BorderPane root = (BorderPane)mainloader.load(); // //FXMLLoader.load(getClass().getResource("Home.fxml"));
			Scene scene = new Scene(root,1280,800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Query-Verefier");
			
			//MVC model assignment 
			HomeController homeController = mainloader.getController();
			homeController.setModel(model);
			
			primaryStage.show();		
			
			
			//Log In to DB screen - the main App window will be disable until login committed.
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DB_Selection_and_Login.fxml"));
			AnchorPane loginPane = fxmlLoader.load(); 
			Stage loginStage = new Stage();
			Scene loginScene = new Scene(loginPane, 600, 500);
			loginStage.initModality(Modality.APPLICATION_MODAL);
			
			loginStage.setOnCloseRequest(event -> {
	            event.consume(); // Consume the event to prevent the stage from closing
	        });
			
			loginStage.setTitle("Connect to DB");
			loginStage.setScene(loginScene);
			
			//MVC model assignment 
			LogInController logController = fxmlLoader.getController();
			logController.setLogInModel(model);
			
			loginStage.initOwner(primaryStage);
			
			
			loginStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	
	
	

}
