package testing.tests;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import graphs.GraphRenderer;
import graphs.Vertex;
import graphs.paths.C1PPath;
import testing.Test;
import testing.TestResult;
import testing.dynamiclinkage.TestingEnvironmentIF;
import testing.results.C1PTestResult;

public class C1P extends Test {
	private Vertex start;
	private Vertex end;
		
	public C1P(TestingEnvironmentIF environment) {
		super(environment);
	}

	@Override
	public TestResult run() {		
		if(start == null || end == null) {
			return null;
		}
		
		List<C1PPath> paths = this.c1p(start, end);
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

	// Conduct c1p testing from in to out.
	public List<C1PPath> c1p(Vertex in, Vertex out) {
		LinkedList<Vertex> queue = new LinkedList<>();
		queue.add(in);

		List<C1PPath> paths = new ArrayList<>();
		c1pHelper(queue, out, paths);

		return paths;
	}

	private void c1pHelper(LinkedList<Vertex> list, Vertex out, List<C1PPath> paths) {
		if(this.isCanceled()) {
			return;
		}
		
		// base case 1: v has no edges (fail)
		// base case 2: v has reached the out (success)

		if (list.peekLast() == out) {
			paths.add(new C1PPath(list, true));
			return;
		}

		Vertex current = list.peekLast();
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
			paths.add(new C1PPath(list, false));
		}

		for (Vertex v : adj) {
			LinkedList<Vertex> branch = new LinkedList<>(list);
			branch.add(v);
			c1pHelper(branch, out, paths);
		}
	}
}
