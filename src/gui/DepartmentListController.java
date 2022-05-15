package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
	
	//EVENTS________________________________________________
	@FXML private void btnNew_click()
	{
		System.out.println("whoooooooo");
	}
}
