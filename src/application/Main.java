package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Main extends Application 
{
	//FIELDS__________________________________
	private static Scene mainScene;
	
	//PROPERTY METHODS________________________
	public static Scene getMainScene() {return mainScene;}
	
	//METHODS_________________________________
	@Override
	public void start(Stage stage) throws Exception 
	{
		try 
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
			ScrollPane scrollPane = loader.load();

			scrollPane.setFitToHeight(true);
			scrollPane.setFitToWidth(true);

			mainScene = new Scene(scrollPane);
			stage.setScene(mainScene);
			stage.show();
		}
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch();
	}
}
