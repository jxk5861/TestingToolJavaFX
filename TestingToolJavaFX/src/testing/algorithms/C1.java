package testing.algorithms;

import java.util.List;

import javax.swing.JOptionPane;

import graphs.GraphRenderer;
import graphs.Vertex;
import graphs.paths.GraphPath;
import testing.Test;
import testing.TestCoverageMetrics;
import testing.dynamiclinkage.EnvironmentIF;
import testing.future.TestResult;

public class C1 extends Test{

	private Vertex start;
	
//	@FXML
//	private TextField textField;
	
	public C1(EnvironmentIF environment) {
		super(environment);
	}

	@Override
	public TestResult run() {
//		System.out.println("C1 Running...");
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("C1 Done...");
		
		List<GraphPath> paths = TestCoverageMetrics.c1(start);
		TestResult result = new TestResult(paths);

		System.out.println("C1 Testing Results: ");
		System.out.println(paths.size() + " Paths");
		paths.forEach(System.out::println);
		System.out.println();
		
		return result;
	}

	@Override
	public void initialize() {
		GraphRenderer clone = environment.getGraphRenderer().clone();
		
		String id = JOptionPane.showInputDialog("Input the start vertex");
		start = clone.getVertexById(id);
	}
}
