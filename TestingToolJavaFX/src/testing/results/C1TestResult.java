package testing.results;

import java.io.IOException;
import java.util.List;

import graphs.paths.GraphPath;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import testing.TestResult;

public class C1TestResult extends TestResult {

	public C1TestResult(List<GraphPath> paths) {
		super(paths);
	}

	@Override
	public void display() {
		FXMLLoader fxmlLoader = new FXMLLoader(
				C1TestResult.class.getResource("path_result.fxml"));
		try {
			Parent parent = fxmlLoader.load();
			PathResultController controller = fxmlLoader.getController();
			controller.setResults(paths);

			Scene scene = new Scene(parent, 600, 400);

			JMetro jMetro = new JMetro(Style.LIGHT);
			jMetro.setScene(scene);
			Image image = new Image("Icon.png");

			Stage primaryStage = new Stage();

			primaryStage.getIcons().add(image);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("C1 Testing Results");
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
