package gui.state.states;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import graphs.GraphRenderer;
import graphs.Vertex;
import gui.state.DrawingState;

public class RemoveEdgeState extends DrawingState {

	private Vertex start;

	public RemoveEdgeState(GraphRenderer graph) {
		super(graph);
		start = null;
	}

	@Override
	public DrawingState processMouseReleasedEvent(MouseEvent event) {
		Point2D loc = new Point2D(event.getX(), event.getY());

		if (start == null) {
			start = graph.getVertex(loc);

			if (start != null) {
				graph.drawVertex(start.getName(), graph.getVertexPosition(start), Color.LIGHTBLUE);
			}
		} else {
			Vertex end = graph.getVertex(loc);

			if (end == null) {
				return this;
			}

			graph.removeEdge(start, end);
			// Draw a vertex at the location of the green "start" vertex
			// to reset the color of it. (less expensive then a redraw).
			graph.drawVertex(start);
			start = null;
		}

		return this;
	}

	@Override
	protected void exitState() {
		if (start != null) {
			// Draw a vertex at the location of the green "start" vertex
			// to reset the color of it. (less expensive then a redraw).
			graph.drawVertex(start);
		}
	}

}
