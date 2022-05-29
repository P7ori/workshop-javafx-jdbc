package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

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
import model.exceptions.ValidationException;
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
		ValidationException exception = new ValidationException("Validation error");
		
		Integer id = Utils.tryParseInt(txtId.getText());
		
		if(txtName.getText() == null || txtName.getText().trim().equals(""))
		{
			exception.addError("name", "Field can't be empty.");
		}
		
		String name = txtName.getText();
		
		if(exception.getErrors().size() > 0)
			throw exception;
		
		return new Department(id, name);
	}
	
	private void setErrorMessages(Map<String, String> errors)
	{
		Set<String> fields = errors.keySet();
		
		if(fields.contains("name"))
			labelError.setText(errors.get("name"));
	}
	
	//EVENTS
	@FXML private void onBtnSaveAction(ActionEvent e)
	{
		if(entity == null)
			throw new IllegalStateException("Department Entity was null.");
		if(service == null)
			throw new IllegalStateException("DepartmentService was null.");
		
		try
		{
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyListeners();
			Utils.currentStage(e).close();
		}
		catch(ValidationException ex)
		{
			setErrorMessages(ex.getErrors());
		}
		catch(DBException ex)
		{
			Alerts.show(null,"Error saving or updating object" + ex.getMessage(), AlertType.ERROR);
		}
	}
	
	@FXML private void onBtnCancelAction(ActionEvent e)
	{
		Utils.currentStage(e).close();
	}

}
