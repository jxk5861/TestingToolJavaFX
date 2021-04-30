package testing.algorithms;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import graphs.GraphRenderer;
import graphs.Vertex;
import graphs.filter.AbsPathFilter;
import graphs.filter.filters.AllPathFilter;
import graphs.filter.filters.InvalidPathFilter;
import graphs.filter.filters.ValidPathFilter;
import graphs.filter.source.Source;
import graphs.filter.source.SourceIF;
import graphs.paths.C1PPath;
import graphs.paths.GraphPath;
import testing.Test;
import testing.TestCoverageMetrics;
import testing.dynamiclinkage.EnvironmentIF;
import testing.future.TestResult;

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
		
		
		long valid = paths.stream().filter(C1PPath::succeeds).count();
		long invalid = paths.stream().filter(C1PPath::fails).count();
		
		System.out.println("C1P Testing Results:");
		System.out.println(filter.getData().size() + " Total Paths (" + valid + " valid, " + invalid + " invalid)");
		filter.getData().forEach(C1PPath::print);
		
		// Sleep for a small amount so that the out/err print in the right order.
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
