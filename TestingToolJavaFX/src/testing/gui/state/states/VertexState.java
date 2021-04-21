package testing.gui.state.states;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import testing.graphs.Graph;
import testing.graphs.GraphRenderer;
import testing.gui.state.DrawingState;

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
