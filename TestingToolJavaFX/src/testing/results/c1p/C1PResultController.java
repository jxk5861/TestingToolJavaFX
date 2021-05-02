package testing.results.c1p;

import java.util.List;

import graphs.filter.AbsPathFilter;
import graphs.filter.filters.AllPathFilter;
import graphs.filter.filters.InvalidPathFilter;
import graphs.filter.filters.ValidPathFilter;
import graphs.filter.source.Source;
import graphs.filter.source.SourceIF;
import graphs.paths.C1PPath;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class C1PResultController {
	private List<C1PPath> paths;

	@FXML
	private ComboBox<String> filterMode;
	@FXML
	private TextArea testResults;

	@FXML
	private void initialize() {
		filterMode.getItems().add("All Paths");
		filterMode.getItems().add("Valid Paths");
		filterMode.getItems().add("Invalid Paths");
		
		filterMode.setValue("All Paths");
	}

	public void setResults(List<C1PPath> paths) {
		this.paths = paths;
		this.updateResults();
	}
	
	private void updateResults() {
		String filterMode = this.filterMode.getValue();
		List<C1PPath> paths = this.paths;
		
		SourceIF source = new Source(paths);
		AbsPathFilter filter;
		if(filterMode.equals("All Paths")) {
			filter = new AllPathFilter(source);
		} else if(filterMode.equals("Valid Paths")) {
			filter = new ValidPathFilter(source);
		} else {
			filter = new InvalidPathFilter(source);
		}
		
		
		long valid = paths.stream().filter(C1PPath::succeeds).count();
		long invalid = paths.stream().filter(C1PPath::fails).count();
		
		StringBuilder results = new StringBuilder();
		
		results.append(paths.size() + " Total Paths (" + valid + " valid, " + invalid + " invalid)");
		results.append(System.lineSeparator());
		filter.getData().forEach((o)->results.append(o + System.lineSeparator()));
		
		testResults.setText(results.toString());
	}
	
	@FXML
	private void modeChanged(ActionEvent event) {
		this.updateResults();
	}

}
