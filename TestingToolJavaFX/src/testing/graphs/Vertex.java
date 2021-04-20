package testing.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Vertex {
	private String name;
	private Set<Vertex> adjacent;
	private boolean branch;

	public Vertex(String name) {
		super();
		this.name = name;
		branch = false;
		this.adjacent = new LinkedHashSet<>();
	}

	public Vertex(int id) {
		this(Integer.toString(id));
	}

	public Vertex(Vertex vertex) {
		this.name = vertex.name;
		this.branch = vertex.branch;
		this.adjacent = new LinkedHashSet<>();

		this.dfsCopy(vertex, this, new HashMap<>());
	}

	private void dfsCopy(Vertex old, Vertex vertex, HashMap<Vertex, Vertex> copied) {
		vertex.name = old.name;
		vertex.branch = old.branch;

		copied.put(old, vertex);

		for (Vertex adj : old.adjacent) {
			if (copied.containsKey(adj)) {
				vertex.addEdge(copied.get(adj));
			} else {
				// TODO: Why -1?
				Vertex adj_copy = new Vertex(-1);
				dfsCopy(adj, adj_copy, copied);
				vertex.addEdge(adj_copy);
			}
		}
	}

	public void addEdge(Vertex to) {
		adjacent.add(to);
		if (adjacent.size() > 1) {
			branch = true;
		}
	}

	public void addEdge(Vertex... verticies) {
		for (Vertex v : verticies) {
			this.addEdge(v);
		}
	}

	public Set<Vertex> getAdjacent() {
		return this.adjacent;
	}

	public Set<Vertex> getAdjacentCopy() {
		return new HashSet<>(adjacent);
	}

	public void removeEdge(Vertex edge) {
		adjacent.remove(edge);
	}

	public boolean isBranch() {
		return this.branch;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append(name);
		builder.append("->");

		int i = 0;
		for (Vertex v : adjacent) {
			builder.append(v.name);
			if (i++ != adjacent.size() - 1) {
				builder.append(",");
			}
		}

		return builder.toString();
	}

	public Vertex deepClone() {
		return new Vertex(this);
	}

	private int count;

	public void DFS() {
		System.out.println();
		System.out.println("DFS traversal of " + this.name + ":");
		System.out.println("Name HashCode");

		Map<Vertex, Integer> visited = new HashMap<>();
		count = 0;

		for (Vertex vertex : this.adjacent) {
			if (!visited.containsKey(vertex)) {
				dfs(vertex, visited);
			}
		}

//		for(var e : visited.entrySet()) {
//			System.out.println(e.getKey() + " " + e.getValue());
//		}
		System.out.println();
		System.out.println();
	}

	private void dfs(Vertex v, Map<Vertex, Integer> visited) {
		System.out.println(v.name + " " + v.hashCode());
		count++;
		visited.put(v, count);
//		System.out.println(v);

		for (Vertex w : v.adjacent) {
//			System.out.println(v + "->" + w);
			if (!visited.containsKey(w)) {
				dfs(w, visited);
			}
		}
//		System.out.println(v);
	}

//	/**
//	 * Create a transposed graph starting from the deepest node in the graph.
//	 * Remember it is likely no longer possible to find a vertex which can reach all
//	 * other vertices.
//	 */
//	public LinkedList<Vertex> getTransposeCopy() {
//		LinkedList<Vertex> connected = this.getConnected();
//		HashMap<Vertex, Vertex> map = new HashMap<>();
//
//		for (Vertex v : connected) {
//			Vertex w = new Vertex(v.name);
//			map.put(v, w);
//		}
//
//		for (Vertex v : connected) {
//			for (Vertex w : v.adjacent) {
//				map.get(w).addEdge(map.get(v));
//			}
//		}
//
//		LinkedList<Vertex> transposed = new LinkedList<>();
//		for (Vertex v : connected) {
//			transposed.add(map.get(v));
//		}
//
//		return transposed;
//	}

	private void getConnectedUtil(Vertex v, HashSet<Vertex> visited) {
		visited.add(v);

		for (Vertex w : v.getAdjacent()) {
			if (!visited.contains(w)) {
				this.getConnectedUtil(w, visited);
			}
		}
	}

	/**
	 * Get all vertices which can be reached from this vertex.
	 */
	public Set<Vertex> getConnected() {
		Set<Vertex> vertices = new HashSet<>();
		HashSet<Vertex> visited = new LinkedHashSet<>();

		this.getConnectedUtil(this, visited);

		vertices.addAll(visited);

		return vertices;
	}

	private void fillOrder(Vertex v, HashSet<Vertex> visited, Stack<Vertex> stack) {
		visited.add(v);

		for (Vertex w : v.getAdjacent()) {
			if (!visited.contains(w)) {
				fillOrder(w, visited, stack);
			}
		}

		stack.push(v);
	}

	private void sccUtil(Vertex v, HashSet<Vertex> visited, HashSet<Vertex> component) {
		visited.add(v);
		component.add(v);

		for (Vertex w : v.adjacent) {
			if (!visited.contains(w)) {
				sccUtil(w, visited, component);
			}
		}
	}

	// Modified since this is already a connected component (since its a path).
	public List<HashSet<Vertex>> getStronglyConnectedComponents() {
//		System.out.println("Strongly Connected Components:");
		List<HashSet<Vertex>> components = new LinkedList<>();

		Stack<Vertex> stack = new Stack<Vertex>();
		HashSet<Vertex> visited = new HashSet<>();

		// Fill vertices in stack according to their finishing times.
		for (Vertex v : this.getConnected()) {
			fillOrder(v, visited, stack);
		}

		// Now process all vertices in order defined by Stack
		visited.clear();
		while (!stack.isEmpty()) {
			Vertex v = stack.pop();
			if (!visited.contains(v)) {
				HashSet<Vertex> component = new HashSet<>();
				this.sccUtil(v, visited, component);
				components.add(component);
			}
		}

		return components;
	}
}
