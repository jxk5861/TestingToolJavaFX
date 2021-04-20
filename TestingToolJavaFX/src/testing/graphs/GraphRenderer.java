package testing.graphs;

import java.util.HashSet;
import java.util.Set;

import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;

public class GraphRenderer extends Graph {
	private Graph graph;
	private GraphicsContext graphics;

	public GraphRenderer(Graph graph, GraphicsContext graphics) {
		super();
		this.graph = graph;
		this.graphics = graphics;

		graphics.setTextAlign(TextAlignment.CENTER);
		graphics.setTextBaseline(VPos.CENTER);
	}

	public Vertex addVertex(Point2D point) {
		if (this.insideVertex(point)) {
			return null;
		}

		Vertex v = this.graph.addVertex(point);
		this.drawVertex(v);

		return v;
	}

	public void addVertex(Vertex v, Point2D point) {
		if (this.insideVertex(point)) {
			return;
		}

		this.graph.addVertex(v, point);
		this.drawVertex(v);
	}

	public void removeVertex(Vertex v) {
		this.graph.removeVertex(v);
		this.redraw();
	}

	public void addEdge(Vertex v, Vertex w) {
		if (v.getAdjacent().contains(w)) {
			return;
		}

		this.graph.addEdge(v, w);
		this.drawEdge(v, w);
	}

	public void removeEdge(Vertex v, Vertex w) {
		this.graph.removeEdge(v, w);
		this.redraw();
	}

	public Vertex getVertex(Point2D loc) {
		return this.graph.getVertex(loc);
	}

	public void moveVertex(Vertex v, Point2D loc) {
		this.graph.moveVertex(v, loc);
		this.redraw();
	}

	public boolean insideVertex(Point2D loc) {
		return this.graph.insideVertex(loc);
	}

	/** Draw a vertex. */
	private void drawVertex(Vertex v) {
		Point2D point = this.graph.map.get(v);

		// Draw the vertex.
		double x = point.getX() - nodeRadius;
		double y = point.getY() - nodeRadius;
		Paint old = graphics.getFill();

		graphics.setFill(Color.rgb(200, 160, 255));
		// graphics.setFill(Color.rgb(255, 100, 100));
		graphics.fillOval(x, y, nodeDiameter, nodeDiameter);
		graphics.setFill(Color.BLACK);

		graphics.strokeOval(x, y, nodeDiameter, nodeDiameter);

		graphics.fillText(v.getName(), point.getX(), point.getY());
		graphics.setFill(old);
	}

	/** Draw an edge from v to w. */
	private void drawEdge(Vertex v, Vertex w) {
		if (v == w) {
			Point2D point = this.graph.map.get(v);
			double x = point.getX() - nodeRadius;
			double y = point.getY() - nodeRadius;

			graphics.setLineWidth(3);
			graphics.strokeOval(x, y, nodeDiameter, nodeDiameter);
			graphics.setLineWidth(1);
		} else {
			this.drawStraightEdge(v, w);
		}
	}

	/** Draw a straight edge from v to w (v != w). */
	private void drawStraightEdge(Vertex v, Vertex w) {
		Point2D from = this.graph.map.get(v);
		Point2D to = this.graph.map.get(w);

		// Theta is the direction to get from from->to
		double theta = Math.atan2(to.getY() - from.getY(), to.getX() - from.getX());
		// Only draw the line outside of the circle (shrink it in the theta direction)
		from = from.add(nodeRadius * Math.cos(theta), nodeRadius * Math.sin(theta));
		to = to.subtract(nodeRadius * Math.cos(theta), nodeRadius * Math.sin(theta));

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
	private void drawUtil(GraphicsContext graphics, Vertex v, Set<Vertex> visited) {
		// Draw this vertex
		this.drawVertex(v);

		// Set the edge color to black
		graphics.setStroke(Color.BLACK);

		// Draw edges and recursively draw connected vertices.
		for (Vertex w : v.getAdjacent()) {
			this.drawEdge(v, w);

			if (!visited.contains(w)) {
				visited.add(w);
				drawUtil(graphics, w, visited);
			}
		}
	}

	/**
	 * Redraw the graph by drawing a large white square and then drawing the graph.
	 */
	private void redraw() {
		graphics.setFill(Color.WHITE);
		graphics.fillRect(0, 0, 1000, 1000);
		graphics.setFill(Color.BLACK);

		this.draw();
	}

	/** Draw the graph. */
	private void draw() {
		Set<Vertex> visited = new HashSet<>();

		// Draw vertices.
		for (Vertex v : this.graph.map.keySet()) {
			drawUtil(graphics, v, visited);
		}
	}

}
