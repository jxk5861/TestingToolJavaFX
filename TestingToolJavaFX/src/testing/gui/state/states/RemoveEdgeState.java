package testing.gui.state.states;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import testing.graphs.Graph;
import testing.graphs.Vertex;
import testing.gui.state.DrawingState;

public class RemoveEdgeState extends DrawingState{

	private Vertex start;

	public RemoveEdgeState(Graph graph) {
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

			graph.removeEdge(start, end);
			start = null;
		}
		
		return this;
	}
	

}
