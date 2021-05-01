package gui.state.states;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import graphs.GraphRenderer;
import gui.state.DrawingState;

/**
 * The state corresponding to when clicking on the canvas creates vertices.
 */
public class VertexState extends DrawingState {

	public VertexState(GraphRenderer graph) {
		super(graph);
	}

	@Override
	public DrawingState processMouseReleasedEvent(MouseEvent event) {
		Point2D loc = new Point2D(event.getX(), event.getY());

		graph.addVertex(loc);

		return this;
	}
}
