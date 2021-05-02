package testing.results;

import java.util.List;

import graphs.paths.GraphPath;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class PathResultController {
	private List<GraphPath> paths;
	
	@FXML
	private TextArea testResults;
	
	public void setResults(List<GraphPath> paths) {
		this.paths = paths;
		
		StringBuilder results = new StringBuilder();
		
		results.append(this.paths.size() + " Paths");
		results.append(System.lineSeparator());
		paths.forEach(o->results.append(o + System.lineSeparator()));
		results.append(System.lineSeparator());
		
		testResults.setText(results.toString());
	}
}
