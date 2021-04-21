package testing.gui.state.states;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import testing.graphs.GraphRenderer;
import testing.graphs.Vertex;
import testing.gui.state.DrawingState;

public class MoveState extends DrawingState {

	private Vertex selected;
	private Point2D offset;
	
	public MoveState(GraphRenderer graph) {
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

		if(graph.invalidVertexPosition(loc, selected)) {
			// Draw the vertex in red at its invalid position.
			graph.redraw();
			graph.drawVertex(selected.getName(), loc, Color.ORANGERED);
			return this;
		}
		
		graph.moveVertex(selected, loc);
		return this;
	}

	@Override
	public DrawingState processMouseReleasedEvent(MouseEvent event) {
		if(selected == null) {
			return this;
		}
		
		Point2D loc = new Point2D(event.getX(), event.getY()).add(offset);
		
		// If the vertex ends at an invalid position, redraw the graph
		// to remove the red invalid vertex.
		if(graph.invalidVertexPosition(loc, selected)) {
			graph.redraw();
		}
		
		selected = null;
		offset = null;
		
		return this;
	}
}
