package testing.results.c1p;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import graphs.paths.C1PPath;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import testing.TestResult;

public class C1PTestResult extends TestResult {

//	private String filterMode;
	private List<C1PPath> paths;

	public C1PTestResult(List<C1PPath> paths) {
		super(Collections.emptyList());
		this.paths = paths;
	}

	@Override
	public void display() {
		FXMLLoader fxmlLoader = new FXMLLoader(C1PTestResult.class.getResource("c1p_result.fxml"));
		try {
			Parent parent = fxmlLoader.load();
			C1PResultController controller = fxmlLoader.getController();
			controller.setResults(paths);

			Scene scene = new Scene(parent, 600, 400);

			JMetro jMetro = new JMetro(Style.LIGHT);
			jMetro.setScene(scene);
			Image image = new Image("Icon.png");

			Stage primaryStage = new Stage();

			primaryStage.getIcons().add(image);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("C1P Testing Results");
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
