package testing.graphs.paths;

import java.util.HashMap;
import java.util.LinkedList;

import testing.graphs.Vertex;

public class GraphPath implements Cloneable{
	protected LinkedList<Vertex> path;
	protected HashMap<Vertex, Integer> map;

	public GraphPath() {
		this.path = new LinkedList<>();
		this.map = new HashMap<>();
	}
	
	public GraphPath(LinkedList<Vertex> path) {
		super();
		this.path = path;
		this.map = new HashMap<>();
		
		for(Vertex v : path) {
			this.addToMap(v);
		}
	}
	
	public GraphPath(GraphPath old) {
		this.path = new LinkedList<>();
		this.map = new HashMap<>();
		
		for(Vertex v : old.path) {
			this.path.add(v);
			this.addToMap(v);
		}
	}
	
	protected void addToMap(Vertex vertex) {
		if(map.containsKey(vertex)) {
			map.put(vertex, map.get(vertex) + 1);
		}else {
			map.put(vertex, 1);
		}
	}
	
	public Vertex getFirst() {
		return path.getFirst();
	}
	
	public Vertex getLast() {
		return path.getLast();
	}
	
	public void addVertex(Vertex vertex) {
		path.add(vertex);
		this.addToMap(vertex);
	}
	
	public boolean contains(Vertex vertex) {
		return map.containsKey(vertex);
	}
	
	public int numAppearances(Vertex vertex) {
		return map.getOrDefault(vertex, 0);
	}

	public int size() {
		return path.size();
	}
	
	@Override
	public String toString() {
		if (path.size() == 0) {
			return "";
		}

		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < path.size() - 1; i++) {
			Vertex v = path.get(i);
			builder.append(v.getName());
			builder.append("->");
		}

		builder.append(path.getLast().getName());
		return builder.toString();
	}
	
	/**
	 * Deep clone.
	 * */
	@Override
	protected GraphPath clone() throws CloneNotSupportedException {
		return new GraphPath(this);
	}
}
