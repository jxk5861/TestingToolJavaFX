package testing.tests;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import graphs.GraphRenderer;
import graphs.Vertex;
import graphs.paths.GraphPath;
import testing.algorithms.DataFlowTesting;
import testing.dynamiclinkage.EnvironmentIF;
import testing.dynamiclinkage.TestLoader;
import testing.future.TestResult;

public class DU extends TestLoader {

	private List<Vertex> declares;
	private List<Vertex> uses;
	
	public DU(EnvironmentIF environment) {
		super(environment);
		
		declares = new ArrayList<>();
		uses = new ArrayList<>();
	}

	@Override
	public TestResult run() {
		List<GraphPath> duPaths = DataFlowTesting.duPaths(declares, uses);
		TestResult result = new TestResult(duPaths);
		
		System.out.println("DU Paths Testing Results");
		System.out.println(duPaths.size() + " Paths");
		duPaths.forEach(System.out::println);
		System.out.println();
		
		return result;
	}

	@Override
	public void initialize() {
		declares.clear();
		uses.clear();
		
		GraphRenderer clone = environment.getGraphRenderer().clone();
		String id = JOptionPane.showInputDialog("Input the declare verticies seperated by a comma (,)");
		for(String s : id.split(",")) {
			Vertex v = clone.getVertexById(s);
			if(v == null) {
				continue;
			}
			declares.add(v);
		}
		
		id = JOptionPane.showInputDialog("Input the uses verticies seperated by a comma (,)");
		for(String s : id.split(",")) {
			Vertex v = clone.getVertexById(s);
			if(v == null) {
				continue;
			}
			uses.add(v);
		}
		
		
	}
}
