package testing.gui.state.states;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import testing.graphs.Graph;
import testing.graphs.Vertex;
import testing.gui.state.DrawingState;

public class MoveState extends DrawingState {

	private Vertex selected;
	private Point2D offset;
	
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
		offset = graph.getVertexPosition(selected).subtract(loc);
		
		return this;
	}

	@Override
	public DrawingState processMouseMovedEvent(MouseEvent event) {
		if(selected == null) {
			return this;
		}
		
		Point2D loc = new Point2D(event.getX(), event.getY()).add(offset);
		
		graph.moveVertex(selected, loc);
		if(!graph.getVertexPosition(selected).equals(loc)) {
			graph.moveVertex(selected, loc);
		}
		
		return this;
	}

	@Override
	public DrawingState processMouseReleasedEvent(MouseEvent event) {
		if(selected == null) {
			return this;
		}
		
		Point2D loc = new Point2D(event.getX(), event.getY()).add(offset);
		
		// If the vertex ends at an invalid position, move it back to where it should be.
		if(graph.invalidVertexPosition(loc, selected)) {
			graph.moveVertex(selected, graph.getVertexPosition(selected));
		}
		
		selected = null;
		offset = null;
		
		return this;
	}
}
