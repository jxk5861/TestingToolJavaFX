package testing.results;

import java.util.List;

import graphs.paths.GraphPath;

public class C1TestResult extends TestResult{

	public C1TestResult(List<GraphPath> paths) {
		super(paths);
	}

	@Override
	public void display() {
		System.out.println("C1 Testing Results: ");
		System.out.println(paths.size() + " Paths");
		paths.forEach(System.out::println);
		System.out.println();
	}

}
