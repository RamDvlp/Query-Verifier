package backSQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class DB_Service {

	
	private DBconnection dbConnection;

	
	public DB_Service() {
		super();
		this.dbConnection = DBconnection.getInstance();
	}


	public boolean testConnection(String url, String user, String password) {
		if(dbConnection.getConnectionFromUserInput(url, user, password) == null)
			return false;
		return true;	
	}


	/* At this point it's highly unlikely that the connection will fail (unless db server get shut down intentionally 
	by external application) hence no special consideration for the SQL exception.
	
	*/
	public void establishConnection() {
		try {
			dbConnection.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Connected to DB");
	}


	public void runQuery(String query, TableView<Object> table) {
		 dbConnection.runAquery(query, table);
		
	}

	public void runComparison(TableView<Object> table, String selectedModel,
	        ArrayList<String> correctQueries, ArrayList<String> testedQueries) {
	    String columnName = "Result";
	    table.getColumns().clear();
	    
	    TableColumn<Object, Object> column = new TableColumn<>(columnName);
	    table.getColumns().add(column);

	    // Adjust the cell value factory to correctly extract data
	    column.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));

	    // Set a cell factory to customize cell rendering
	    column.setCellFactory(param -> new TableCell<Object, Object>() {
	        private final Label label = new Label();

	        @Override
	        protected void updateItem(Object item, boolean empty) {
	            super.updateItem(item, empty);

	            if (item != null && item instanceof Label) {
	                label.setText(((Label) item).getText());
	                label.setTextFill(((Label) item).getTextFill());
	                setGraphic(label);
	            } else {
	                setGraphic(null);
	            }
	        }
	    });

	    ObservableList<Object> data = FXCollections.observableArrayList();

	    for (int i = 0; i < correctQueries.size(); i++) {
	        String correct = correctQueries.get(i);
	        String tested = testedQueries.get(i);
	        boolean isEqual = dbConnection.compareQueryByMode(selectedModel, correct, tested);

	        Label lb = new Label(isEqual ? "Correct" : "Incorrect");
	        lb.setTextFill(isEqual ? Color.GREEN : Color.RED);
	        data.add(lb);
	    }

	    table.setItems(data);
	}
	
	
	
	
}
