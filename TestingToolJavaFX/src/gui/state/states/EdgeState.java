package gui.state.states;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import graphs.GraphRenderer;
import graphs.Vertex;
import gui.state.DrawingState;

public class EdgeState extends DrawingState {

	private Vertex start;

	public EdgeState(GraphRenderer graph) {
		super(graph);
		start = null;
	}

	@Override
	public DrawingState processMouseReleasedEvent(MouseEvent event) {
		Point2D loc = new Point2D(event.getX(), event.getY());

		if (start == null) {
			start = graph.getVertex(loc);
		} else {
			Vertex end = graph.getVertex(loc);

			if (end == null) {
				return this;
			}

			graph.addEdge(start, end);
			start = null;
		}
		return this;
	}
}
