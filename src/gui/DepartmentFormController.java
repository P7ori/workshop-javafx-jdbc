package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

public class DepartmentFormController implements Initializable 
{
	//FIELDS
	private Department entity;
	@FXML private TextField txtId;
	@FXML private TextField txtName;
	@FXML private Button btnSave;
	@FXML private Button btnCancel;
	@FXML private Label labelError;

	//INIT
	@Override
	public void initialize(URL url, ResourceBundle resource) 
	{
	}
	
	//PROPERTY METHODS
	public void setEntity(Department value) {entity = value;}
	
	//METHODS
	public void updateFormData()
	{
		if(entity == null)
			throw new NullPointerException("Department Entity was null");
		
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
	}
	
	//EVENTS
	@FXML private void onBtnSaveAction(ActionEvent e)
	{
		System.out.println("btnSave");
	}
	
	@FXML private void onBtnCancelAction(ActionEvent e)
	{
		System.out.println("btnCancel");
	}

}
