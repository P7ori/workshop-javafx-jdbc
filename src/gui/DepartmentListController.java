package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable
{
	//FIELDS________________________________________________
	private DepartmentService service;
	private ObservableList<Department> obsList;
	
	@FXML private Button btnNew;
	@FXML private TableView<Department> tableViewDepartment;
	@FXML private TableColumn<Department, Integer> tableColumnId;
	@FXML private TableColumn<Department, String> tablecolumnName;
	
	//CONSTRUCTORS__________________________________________
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		initializeNodes();
	}
	
	//PROPERTY METHODS
	public void SetDepartmentService (DepartmentService value) {service = value;}
	
	//METHODS_______________________________________________
	private void initializeNodes()
	{
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tablecolumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView()
	{
		if(service == null)
			throw new IllegalStateException("DepartmentService service was null");
		
		List<Department> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewDepartment.setItems(obsList);
	}
	
	private <T> void createDialogForm(String fullPath, Stage parentStage, Consumer<T> action)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fullPath));
			Pane pane = loader.load();
			
			if(action != null)
			{
				T controller = loader.getController();
				action.accept(controller);
			}
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Department data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		}
		catch(IOException ex)
		{
			Alerts.show("IO Exception", ex.getMessage(), AlertType.ERROR);
		}
	}
	
	//EVENTS________________________________________________
	@FXML private void btnNew_click(ActionEvent event)
	{
		Department obj = new Department();
		
		Consumer<DepartmentFormController> action = d -> {
			d.setEntity(obj);
			d.setService(new DepartmentService());
			};
		
		createDialogForm("/gui/DepartmentForm.fxml", Utils.currentStage(event), action);
	}
}
