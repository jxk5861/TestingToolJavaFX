package testing.results;

import java.util.List;

import graphs.paths.GraphPath;

public class DUTestResult extends TestResult{

	public DUTestResult(List<GraphPath> paths) {
		super(paths);
	}

	@Override
	public void display() {
		System.out.println("DU Paths Testing Results");
		System.out.println(paths.size() + " Paths");
		paths.forEach(System.out::println);
		System.out.println();
	}

}
