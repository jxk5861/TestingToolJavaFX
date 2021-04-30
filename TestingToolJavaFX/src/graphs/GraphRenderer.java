package graphs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;

public class GraphRenderer extends Graph {
	private GraphicsContext graphics;
	private double width;
	private double height;

	public GraphRenderer(GraphicsContext graphics, double width, double height) {
		super();
		this.graphics = graphics;
		this.width = width;
		this.height = height;

		graphics.setTextAlign(TextAlignment.CENTER);
		graphics.setTextBaseline(VPos.CENTER);
	}
	
	public GraphRenderer(GraphRenderer old) {
		super();
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
		
		this.graphics = old.graphics;
		this.width = old.width;
		this.height = old.height;
	}

	@Override
	public Vertex addVertex(Point2D point) {
		if (this.invalidVertexPosition(point)) {
			return null;
		}

		Vertex v = super.addVertex(point);
		this.drawVertex(v);

		return v;
	}

	@Override
	public void addVertex(Vertex v, Point2D point) {
		if (this.invalidVertexPosition(point)) {
			return;
		}

		super.addVertex(v, point);
		this.drawVertex(v);
	}

	@Override
	public void removeVertex(Vertex v) {
		super.removeVertex(v);
		this.redraw();
	}

	@Override
	public void addEdge(Vertex v, Vertex w) {
		if (v.getAdjacent().contains(w)) {
			return;
		}

		super.addEdge(v, w);
		this.drawEdge(v, w);
	}

	@Override
	public void removeEdge(Vertex v, Vertex w) {
		super.removeEdge(v, w);
		this.redraw();
	}
	
	@Override
	public void moveVertex(Vertex v, Point2D loc) {
		super.moveVertex(v, loc);
		this.redraw();
	}
	
	@Override
	public boolean invalidVertexPosition(Point2D loc, Vertex... allowed) {
		if(loc.getX() < VERTEX_RADIUS || loc.getY() < VERTEX_RADIUS) {
			return true;
		}
		if(loc.getX() > this.width - VERTEX_RADIUS || loc.getY() > this.height - VERTEX_RADIUS) {
			return true;
		}
		
		return super.invalidVertexPosition(loc, allowed);
	}
	
	public void drawVertex(String label, Point2D point, Color color) {
		// Draw the vertex.
		double x = point.getX() - VERTEX_RADIUS;
		double y = point.getY() - VERTEX_RADIUS;
		Paint old = graphics.getFill();

		graphics.setFill(color);
		// graphics.setFill(Color.rgb(255, 100, 100));
		graphics.fillOval(x, y, VERTEX_DIAMETER, VERTEX_DIAMETER);
		graphics.setFill(Color.BLACK);

		graphics.strokeOval(x, y, VERTEX_DIAMETER, VERTEX_DIAMETER);

		graphics.fillText(label, point.getX(), point.getY());
		graphics.setFill(old);
	}

	/** Draw a vertex. */
	private void drawVertex(Vertex v) {
		this.drawVertex(v.getName(), this.map.get(v), Color.rgb(200, 160, 255));
	}

	/** Draw an edge from v to w. */
	private void drawEdge(Vertex v, Vertex w) {
		if (v == w) {
			Point2D point = this.map.get(v);
			double x = point.getX() - VERTEX_RADIUS;
			double y = point.getY() - VERTEX_RADIUS;

			graphics.setLineWidth(3);
			graphics.strokeOval(x, y, VERTEX_DIAMETER, VERTEX_DIAMETER);
			graphics.setLineWidth(1);
		} else {
			this.drawStraightEdge(v, w);
		}
	}

	/** Draw a straight edge from v to w (v != w). */
	private void drawStraightEdge(Vertex v, Vertex w) {
		Point2D from = this.map.get(v);
		Point2D to = this.map.get(w);

		// Theta is the direction to get from from->to
		double theta = Math.atan2(to.getY() - from.getY(), to.getX() - from.getX());
		// Only draw the line outside of the circle (shrink it in the theta direction)
		from = from.add(VERTEX_RADIUS * Math.cos(theta), VERTEX_RADIUS * Math.sin(theta));
		to = to.subtract(VERTEX_RADIUS * Math.cos(theta), VERTEX_RADIUS * Math.sin(theta));

		// Draw the line.
		graphics.strokeLine(from.getX(), from.getY(), to.getX(), to.getY());

		// Draw the arrow. (Lines from to->e1 and to->e2)
		// Beta is the direction to get from to->from
		double beta = theta + Math.PI;
		double alpha = .5;

		// The endpoints are 20 pixels away from "to."
		// The direction is alpha away from beta (the direction to get from to->from).
		Point2D e1 = to.add(20 * Math.cos(beta + alpha), 20 * Math.sin(beta + alpha));
		Point2D e2 = to.add(20 * Math.cos(beta - alpha), 20 * Math.sin(beta - alpha));

		graphics.strokeLine(to.getX(), to.getY(), e1.getX(), e1.getY());
		graphics.strokeLine(to.getX(), to.getY(), e2.getX(), e2.getY());
	}

	/** DFS to draw everything on the graph. */
	private void drawUtil(Vertex v, Set<Vertex> visited) {
		// Draw this vertex
		this.drawVertex(v);

		// Set the edge color to black
		graphics.setStroke(Color.BLACK);

		// Draw edges and recursively draw connected vertices.
		for (Vertex w : v.getAdjacent()) {
			this.drawEdge(v, w);

			if (!visited.contains(w)) {
				visited.add(w);
				drawUtil(w, visited);
			}
		}
	}

	/**
	 * Redraw the graph by drawing a large white square and then drawing the graph.
	 */
	public void redraw() {
		graphics.setFill(Color.WHITE);
		graphics.fillRect(0, 0, this.width, this.height);
		graphics.setFill(Color.BLACK);

		this.draw();
	}

	/** Draw the graph. */
	private void draw() {
		Set<Vertex> visited = new HashSet<>();

		// Draw vertices.
		for (Vertex v : this.map.keySet()) {
			drawUtil(v, visited);
		}
	}
	
	public Vertex getVertexById(String id) {
		for(Vertex v : this.map.keySet()) {
			if(v.getName().equals(id)) {
				return v;
			}
		}
		
		return null;
	}
	
	public GraphRenderer clone() {
		return new GraphRenderer(this);
	}

}
