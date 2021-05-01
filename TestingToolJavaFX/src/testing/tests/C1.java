package testing.tests;

import java.util.List;

import javax.swing.JOptionPane;

import graphs.GraphRenderer;
import graphs.Vertex;
import graphs.paths.GraphPath;
import testing.Test;
import testing.algorithms.TestCoverageMetrics;
import testing.dynamiclinkage.EnvironmentIF;
import testing.results.C1TestResult;
import testing.results.TestResult;

public class C1 extends Test {

	private Vertex start;

	public C1(EnvironmentIF environment) {
		super(environment);
	}

	@Override
	public TestResult run() {
		if (start == null) {
			return null;
		}

		List<GraphPath> paths = TestCoverageMetrics.c1(start);
		TestResult result = new C1TestResult(paths);

		return result;
	}

	@Override
	public void init() {
		GraphRenderer clone = environment.getGraphRenderer().clone();

		String id = JOptionPane.showInputDialog("Input the start vertex");
		start = clone.getVertexById(id);
	}
}
