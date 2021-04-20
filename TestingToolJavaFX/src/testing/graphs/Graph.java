package testing.graphs;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import javafx.geometry.Point2D;

public class Graph {
	protected Map<Vertex, Point2D> map;
	protected Queue<Integer> names;
	protected final int nodeRadius = 20;
	protected final int nodeDiameter = 40;

	public Graph() {
		map = new HashMap<>();
		names = new PriorityQueue<Integer>();
	}

	public Vertex addVertex(Point2D point) {
		int k;

		if (names.isEmpty()) {
			k = map.size() + 1;
		} else {
			k = names.poll();
		}
		
		Vertex v = new Vertex(k);
		map.put(v, point);
		
		return v;
	}

	public void addVertex(Vertex v, Point2D point) {
		map.put(v, point);
	}

	public void removeVertex(Vertex v) {
		map.remove(v);

		if (v.getName().matches("\\d+")) {
			names.add(Integer.parseInt(v.getName()));
		}

		for (Vertex w : map.keySet()) {
			if (w.getAdjacent().contains(v)) {
				w.removeEdge(v);
			}
		}
	}

	public void addEdge(Vertex v, Vertex w) {
		v.addEdge(w);
	}

	public void removeEdge(Vertex v, Vertex w) {
		v.removeEdge(w);
	}

	public Vertex getVertex(Point2D loc) {
		for (var e : map.entrySet()) {
			Point2D point = e.getValue();
			if (point.distance(loc) < nodeRadius) {
				Vertex v = e.getKey();
				return v;
			}
		}
		return null;
	}

	public void moveVertex(Vertex v, Point2D loc) {
		map.put(v, loc);
	}

	public boolean insideVertex(Point2D loc) {
		for (var e : map.entrySet()) {
			Point2D point = e.getValue();
			if (point.distance(loc) < nodeDiameter) {
				return true;
			}
		}
		return false;
	}
}
