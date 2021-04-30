package gui.state.states;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import graphs.GraphRenderer;
import graphs.Vertex;
import gui.state.DrawingState;

public class RemoveVertexState extends DrawingState {

	public RemoveVertexState(GraphRenderer graph) {
		super(graph);
	}
	
	@Override
	public DrawingState processMouseReleasedEvent(MouseEvent event) {
		Point2D loc = new Point2D(event.getX(), event.getY());
		
		Vertex remove = graph.getVertex(loc);
		if(remove != null) {
			graph.removeVertex(remove);
		}
		
		return this;
	}
}
