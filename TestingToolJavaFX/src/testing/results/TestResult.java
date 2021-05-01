package testing.results;

import java.util.List;

import graphs.paths.GraphPath;

public abstract class TestResult {
	protected final List<GraphPath> paths;

	public TestResult(List<GraphPath> paths) {
		this.paths = paths;
	}
	
	public abstract void display();
}
