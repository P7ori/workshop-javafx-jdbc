package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import db.DBException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable 
{
	//FIELDS
	private Department entity;
	private DepartmentService service;
	private List<DataChangeListener> listeners = new ArrayList<>();
	
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
	public void setService(DepartmentService value) {service = value;}
	
	//METHODS
	public void SubscribeDataChangeListener(DataChangeListener listener)
	{
		listeners.add(listener);
	}
	
	private void notifyListeners()
	{
		listeners.forEach(DataChangeListener::onDataChanged);
	}
	
	public void updateFormData()
	{
		if(entity == null)
			throw new NullPointerException("Department Entity was null");
		
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
	}
	
	public Department getFormData() 
	{
		Integer id = Utils.tryParseInt(txtId.getText());
		String name = txtName.getText();
		return new Department(id, name);
	}
	
	//EVENTS
	@FXML private void onBtnSaveAction(ActionEvent e)
	{
		if(entity == null)
			throw new IllegalStateException("Department Entity was null.");
		if(service == null)
			throw new IllegalStateException("DepartmentService was null.");
		
		entity = getFormData();
		
		try
		{
			service.saveOrUpdate(entity);
			notifyListeners();
		}
		catch(DBException ex)
		{
			Alerts.show(null,"Error saving or updating object" + ex.getMessage(), AlertType.ERROR);
		}
		
		Utils.currentStage(e).close();
	}
	
	@FXML private void onBtnCancelAction(ActionEvent e)
	{
		Utils.currentStage(e).close();
	}

}
