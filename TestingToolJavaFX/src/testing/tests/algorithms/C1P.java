package testing.tests.algorithms;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import testing.graphs.GraphRenderer;
import testing.graphs.Vertex;
import testing.graphs.filter.AbsPathFilter;
import testing.graphs.filter.filters.AllPathFilter;
import testing.graphs.filter.filters.InvalidPathFilter;
import testing.graphs.filter.filters.ValidPathFilter;
import testing.graphs.filter.source.Source;
import testing.graphs.filter.source.SourceIF;
import testing.graphs.paths.C1PPath;
import testing.graphs.paths.GraphPath;
import testing.tests.EnvironmentIF;
import testing.tests.Test;
import testing.tests.TestCoverageMetrics;
import testing.tests.TestResult;

public class C1P extends Test {
	private Vertex start;
	private Vertex end;
	private String filterMode;
	
	public C1P(EnvironmentIF environment) {
		super(environment);
	}

	@Override
	public TestResult run() {
		List<C1PPath> paths = TestCoverageMetrics.c1p(start, end);
		TestResult result = new TestResult(paths.stream().map(o -> (GraphPath)o).collect(Collectors.toList()));
		
		SourceIF source = new Source(paths);
		AbsPathFilter filter;
		if(filterMode.equals("All Paths")) {
			filter = new AllPathFilter(source);
		} else if(filterMode.equals("Valid Paths")) {
			filter = new ValidPathFilter(source);
		} else {
			filter = new InvalidPathFilter(source);
		}
		
		System.out.println("C1P Testing Results:");
		System.out.println(filter.getData() + " Paths");
		filter.getData().forEach(C1PPath::print);
		System.out.println();
		
		return result;
	}

	@Override
	public void initialize() {
		// initialize start and end
		GraphRenderer clone = environment.getGraphRenderer().clone();
		
		String id = JOptionPane.showInputDialog("Input the start vertex");
		start = clone.getVertexById(id);
		
		id = JOptionPane.showInputDialog("Input the end vertex");
		end = clone.getVertexById(id);
		
		String[] choices = { "All Paths", "Valid Paths", "Invalid Paths" };
	    String input = (String) JOptionPane.showInputDialog(null, "Select a filter option",
	        "Filter Mode", JOptionPane.QUESTION_MESSAGE, null, choices, null);
		this.filterMode = input;
	}
}
