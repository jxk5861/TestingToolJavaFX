package graphs;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import javafx.geometry.Point2D;

public class Graph implements Cloneable{
	protected Map<Vertex, Point2D> map;
	protected Queue<Integer> names;
	protected static final int VERTEX_RADIUS = 20;
	protected static final int VERTEX_DIAMETER = 40;
	
	public Graph() {
		map = new HashMap<>();
		names = new PriorityQueue<Integer>();
	}
	
	public Graph(Graph old) {
		this();
		Map<Vertex, Vertex> oldToNew = new HashMap<>();
		
		for(Vertex v : old.map.keySet()) {
			Vertex w = new Vertex(v.getName());
			oldToNew.put(v, w);
		}
		
		for(Vertex v : old.map.keySet()) {
			for(Vertex w : v.getAdjacent()) {
				oldToNew.get(v).addEdge(oldToNew.get(w));
			}
		}
		
		for(Entry<Vertex, Point2D> entry : old.map.entrySet()) {
			map.put(oldToNew.get(entry.getKey()), entry.getValue());
		}
		
		for(int i : old.names) {
			this.names.add(i);
		}
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
			if (point.distance(loc) < VERTEX_RADIUS) {
				Vertex v = e.getKey();
				return v;
			}
		}
		return null;
	}

	public void moveVertex(Vertex v, Point2D loc) {
		map.put(v, loc);
	}

	public boolean invalidVertexPosition(Point2D loc, Vertex... allowed) {
		Set<Vertex> allowedSet = new HashSet<>();
		Collections.addAll(allowedSet, allowed);
		
		for (Entry<Vertex, Point2D> e : map.entrySet()) {
			if(allowedSet.contains(e.getKey())) {
				continue;
			}
			
			Point2D point = e.getValue();
			if (point.distance(loc) < VERTEX_DIAMETER) {
				return true;
			}
		}
		return false;
	}
	
	public Point2D getVertexPosition(Vertex v) {
		return this.map.get(v);
	}
	
	public Graph clone() {
		return new Graph(this);
	}
}
