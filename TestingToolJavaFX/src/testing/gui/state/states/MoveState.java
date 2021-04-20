package testing.gui.state.states;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import testing.graphs.Graph;
import testing.graphs.Vertex;
import testing.gui.state.DrawingState;

public class MoveState extends DrawingState {

	private Vertex selected;
	
	public MoveState(Graph graph) {
		super(graph);
		selected = null;
	}

	@Override
	public DrawingState processMousePressedEvent(MouseEvent event) {
		Point2D loc = new Point2D(event.getX(), event.getY());
		
		selected = graph.getVertex(loc);
		if(selected == null) {
			return this;
		}
		
		graph.moveVertex(selected, loc);
		
		return this;
	}

	@Override
	public DrawingState processMouseMovedEvent(MouseEvent event) {
		if(selected == null) {
			return this;
		}
		
		Point2D loc = new Point2D(event.getX(), event.getY());
		
		graph.moveVertex(selected, loc);
		
		return this;
	}

	@Override
	public DrawingState processMouseReleasedEvent(MouseEvent event) {
		selected = null;
		
		return this;
	}
}
