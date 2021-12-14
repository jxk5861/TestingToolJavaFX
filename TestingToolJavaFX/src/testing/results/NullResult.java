package testing.results;

import java.util.Collections;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import testing.TestResult;

/**
 * A null object for TestResult. This way null testresults do not crash the
 * system. Although, null can still be returned from run() if a programmer does
 * not realize, so the future makes sure they are returned as NullTests.
 */
public class NullResult extends TestResult {
	private String message;

	public NullResult() {
		super(Collections.emptyList());
		this.message = "The test failed (invalid inputs?)";
	}

	public NullResult(String message) {
		super(Collections.emptyList());
		this.message = message;
	}

	@Override
	public void display() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("C1 Test Result");
		alert.setHeaderText(this.message);
		alert.showAndWait();
	}

}
