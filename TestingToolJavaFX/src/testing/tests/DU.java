package testing.tests;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import graphs.GraphRenderer;
import graphs.Vertex;
import graphs.paths.GraphPath;
import testing.TestResult;
import testing.dynamiclinkage.EnvironmentIF;
import testing.dynamiclinkage.TestLoader;
import testing.results.DUTestResult;

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
		List<GraphPath> duPaths = this.duPaths(declares, uses);
		TestResult result = new DUTestResult(duPaths);
		
		return result;
	}

	@Override
	public void init() {
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
	
	public List<GraphPath> duPaths(List<Vertex> declares, List<Vertex> uses){
		List<GraphPath> duPaths = new ArrayList<>();
		
		for(Vertex declare : declares) {
			for(Vertex use : uses) {
				duPaths.addAll(duPath(declare, use));
			}
		}
		return duPaths;
	}

	private void duPathsHelper(LinkedList<Vertex> list, Vertex out, List<GraphPath> paths) {
		if(this.isCanceled()) {
			return;
		}
		
		// base case 1: v has no edges (fail)
		// base case 2: v has reached the out and paths length > 1(success)

		if (list.size() > 1 && list.peekLast() == out) {
			paths.add(new GraphPath(list));
			return;
		}

		Vertex current = list.getLast();
		if(list.size() > 1 && current == list.getFirst()) {
			// fail
			return;
		}
		
		
		Set<Vertex> adj = current.getAdjacentCopy();
		for (int i = 0; i < list.size() - 1; i++) {
			Vertex c = list.get(i);
			Vertex next = list.get(i + 1);

			if (c == current) {
				if (current.isBranch()) {
					adj.remove(next);
				}
			}
		}

		if (adj.size() == 0) {
			//paths.add(new C1PPath(list, false));
			return;
		}

		for (Vertex v : adj) {
			LinkedList<Vertex> branch = new LinkedList<>(list);
			branch.add(v);
			duPathsHelper(branch, out, paths);
		}
	}
	
	// Find all paths from declare to use without repeating a node.
	private List<GraphPath> duPath(Vertex declare, Vertex use){
		List<GraphPath> duPaths = new ArrayList<>();		
		LinkedList<Vertex> queue = new LinkedList<>();
		queue.add(declare);
		duPathsHelper(queue, use, duPaths);
		
		return duPaths;
	}
}
