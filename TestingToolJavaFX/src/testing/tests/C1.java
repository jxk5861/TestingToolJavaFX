package testing.tests;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import graphs.GraphRenderer;
import graphs.Vertex;
import graphs.paths.GraphPath;
import javafx.scene.control.TextInputDialog;
import testing.Test;
import testing.TestResult;
import testing.dynamiclinkage.TestingEnvironmentIF;
import testing.results.C1TestResult;

public class C1 extends Test {

	private Vertex start;

	public C1(TestingEnvironmentIF environment) {
		super(environment);
	}

	@Override
	public TestResult run() {
		if (start == null) {
			return null;
		}

		List<GraphPath> paths = this.c1(start);
		TestResult result = new C1TestResult(paths);

		return result;
	}

	@Override
	public void init() {
		GraphRenderer clone = environment.getGraphRenderer().clone();

		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("C1 Test");
		dialog.setHeaderText("Enter the start vertex:");
		dialog.setContentText("Start Vertex:");
		Optional<String> id = dialog.showAndWait();
		
		if (id.isEmpty()) {
			start = null;
			return;
		}
		start = clone.getVertexById(id.get());
	}

	private static class Edge {
		private Vertex from;
		private Vertex to;

		public Edge(Vertex from, Vertex to) {
			super();
			this.from = from;
			this.to = to;
		}

		public static Edge of(Vertex from, Vertex to) {
			return new Edge(from, to);
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Edge) {
				Edge edge = (Edge) obj;
				return edge.from.equals(from) && edge.to.equals(to);
			}
			return super.equals(obj);
		}

		@Override
		public int hashCode() {
			return Objects.hash(from, to);
		}

	}

	/**
	 * Conduct c1 testing (find DD-paths) on the given source vertex. Uses the
	 * DFS approach (generates different test cases from BFS).
	 */
	private List<GraphPath> c1(Vertex in) {
		HashSet<Edge> visited = new HashSet<>();
		ArrayList<GraphPath> paths = new ArrayList<>();

		GraphPath path = new GraphPath();
		path.addVertex(in);

		c1Helper(path, visited, paths);

		return paths;
	}

	private void c1Helper(GraphPath path, Set<Edge> visited,
			List<GraphPath> paths) {
		if (this.isCanceled()) {
			return;
		}

		Vertex v = path.getLast();
		LinkedList<Vertex> unvisitedAdjacent = new LinkedList<>();

		// Direct out-degree edges will first be marked as visited.
		for (Vertex w : v.getAdjacent()) {
			Edge edge = Edge.of(v, w);
			if (!visited.contains(edge)) {
				unvisitedAdjacent.add(w);
				visited.add(edge);
			}
		}

		// Visit all the unvisited adjacent edges.
		for (Vertex w : unvisitedAdjacent) {
			GraphPath newPath = new GraphPath(path);
			newPath.addVertex(w);
			c1Helper(newPath, visited, paths);
		}

		// If this is the end of the path, add the path to the list.
		if (unvisitedAdjacent.isEmpty()) {
			paths.add(path);
		}
	}
}
