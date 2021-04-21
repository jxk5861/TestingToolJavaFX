package testing.tests;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import testing.graphs.Vertex;
import testing.graphs.paths.C1PPath;
import testing.graphs.paths.GraphPath;

public class TestCoverageMetrics {

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
	 * Conduct c1 testing (find DD-paths) on the given source vertex. Uses the DFS
	 * approach (generates different test cases from BFS).
	 */
	public static List<GraphPath> c1(Vertex in) {
		HashSet<Edge> visited = new HashSet<>();
		ArrayList<GraphPath> paths = new ArrayList<>();

		GraphPath path = new GraphPath();
		path.addVertex(in);

		c1Helper(path, visited, paths);

		return paths;
	}

	private static void c1Helper(GraphPath path, HashSet<Edge> visited, List<GraphPath> paths) {
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

	// Conduct c1p testing from in to out.
	public static List<C1PPath> c1p(Vertex in, Vertex out) {
		LinkedList<Vertex> queue = new LinkedList<>();
		queue.add(in);

		List<C1PPath> paths = new ArrayList<>();
		c1pHelper(queue, out, paths);

		return paths;
	}

	private static void c1pHelper(LinkedList<Vertex> list, Vertex out, List<C1PPath> paths) {
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
