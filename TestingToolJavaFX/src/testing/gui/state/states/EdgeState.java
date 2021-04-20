package testing.gui.state.states;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import testing.graphs.Graph;
import testing.graphs.Vertex;
import testing.gui.state.DrawingState;

public class EdgeState extends DrawingState {

	private Vertex start;

	public EdgeState(Graph graph) {
		super(graph);
		start = null;
	}

	@Override
	public void ProcessMouseReleasedEvent(MouseEvent event) {
		Point2D loc = new Point2D(event.getX(), event.getY());

		if (start == null) {
			start = graph.getVertex(loc);
		} else {
			Vertex end = graph.getVertex(loc);

			if (end == null) {
				return;
			}

			graph.addEdge(start, end);
			start = null;
		}
	}
}
