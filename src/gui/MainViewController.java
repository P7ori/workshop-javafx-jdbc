package gui;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

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
		System.out.println("menuItemDepartment_Click()");
	}
	
	@FXML private void menuItemAbout_click()
	{
		System.out.println("menuItemAbout_Click()");
	}
}
