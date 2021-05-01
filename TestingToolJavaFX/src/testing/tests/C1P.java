package testing.tests;

import java.util.List;

import javax.swing.JOptionPane;

import graphs.GraphRenderer;
import graphs.Vertex;
import graphs.paths.C1PPath;
import testing.Test;
import testing.algorithms.TestCoverageMetrics;
import testing.dynamiclinkage.EnvironmentIF;
import testing.results.C1PTestResult;
import testing.results.TestResult;

public class C1P extends Test {
	private Vertex start;
	private Vertex end;
	
	public C1P(EnvironmentIF environment) {
		super(environment);
	}

	@Override
	public TestResult run() {
		if(start == null || end == null) {
			return null;
		}
		
		List<C1PPath> paths = TestCoverageMetrics.c1p(start, end);
		TestResult result = new C1PTestResult(paths);
		
		return result;
	}

	@Override
	public void init() {
		// initialize start and end
		GraphRenderer clone = environment.getGraphRenderer().clone();
		
		String id = JOptionPane.showInputDialog("Input the start vertex");
		start = clone.getVertexById(id);
		
		id = JOptionPane.showInputDialog("Input the end vertex");
		end = clone.getVertexById(id);
	}
}
