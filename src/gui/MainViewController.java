package gui;

import java.io.IOException;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;

public class MainViewController 
{
	//FIELDS________________________________________
	@FXML private MenuItem menuItemSeller;
	@FXML private MenuItem menuItemDepartment;
	@FXML private MenuItem menuItemAbout;
	
	//EVENTS________________________________________
	@FXML private void menuItemSeller_click()
	{
		System.out.println("menuItemSeller_Click()");
	}
	
	@FXML private void menuItemDepartment_click()
	{
		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) -> {
			controller.SetDepartmentService(new DepartmentService());
			controller.updateTableView();
		});
	}
	
	@FXML private void menuItemAbout_click()
	{
		loadView("/gui/About.fxml", null);
	}
	
	//METHODS_______________________________________
	
	private synchronized <T> void loadView(String absoluteName, Consumer<T> action)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			Scene mainScene = Main.getMainScene();
			
			VBox mainVBox = (VBox)((ScrollPane)mainScene.getRoot()).getContent();
			MenuBar menuBar = (MenuBar)mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(menuBar);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			if(action != null)
			{
				T controller = loader.getController();
				action.accept(controller);
			}
		}
		catch (IOException ex)
		{
			Alerts.show("IOException", "Error loading view: " + ex.getMessage(), AlertType.ERROR);
		}
	}
}
