package testing;

import java.util.List;

import graphs.paths.GraphPath;

public abstract class TestResult {
	protected final List<GraphPath> paths;

	public TestResult(List<GraphPath> paths) {
		this.paths = paths;
	}

	/**
	 * Display a test result. This may be done in any way the programmer wants. Like
	 * printing to console or displaying a javafx scene.
	 */
	public abstract void display();
}
