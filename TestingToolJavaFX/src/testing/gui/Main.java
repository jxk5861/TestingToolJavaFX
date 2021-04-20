package testing.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
//			BorderPane root = new BorderPane();
//			Scene scene = new Scene(root,400,400);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
			Scene scene = new Scene(loadFXML("primary"), 900, 600);
			JMetro jMetro = new JMetro(Style.DARK);
			jMetro.setScene(scene);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			
//			primaryStage.setScene(scene);
//			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
		return fxmlLoader.load();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
