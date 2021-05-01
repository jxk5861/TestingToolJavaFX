package gui.state;

import javafx.scene.input.MouseEvent;
import graphs.GraphRenderer;
import gui.state.states.EdgeState;
import gui.state.states.MoveState;
import gui.state.states.RemoveEdgeState;
import gui.state.states.RemoveVertexState;
import gui.state.states.VertexState;

public abstract class DrawingState {
	public enum Context {
		VERTEX, EDGE, MOVE, REMOVE_VERTEX, REMOVE_EDGE;
	}

	protected GraphRenderer graph;

	public DrawingState(GraphRenderer graph) {
		this.graph = graph;
	}

	public DrawingState processMousePressedEvent(MouseEvent event) {
		return this;
	}

	public DrawingState processMouseMovedEvent(MouseEvent event) {
		return this;
	}

	public DrawingState processMouseReleasedEvent(MouseEvent event) {
		return this;
	}

	public DrawingState processButtonClickedEvent(Context context) {
		return this.nextState(context);
	}

	/**
	 * Sometimes a state may need to perform some cleanup before it exits. (such as edge state re-coloring a start vertex)
	 * */
	protected void exitState() {
		
	}
	
	protected DrawingState nextState(Context context) {
		if (context == Context.VERTEX) {
			if (this instanceof VertexState) {
				return this;
			}
			this.exitState();
			return new VertexState(this.graph);
		} else if (context == Context.EDGE) {
			if (this instanceof EdgeState) {
				return this;
			}
			this.exitState();
			return new EdgeState(this.graph);
		} else if (context == Context.MOVE) {
			if (this instanceof MoveState) {
				return this;
			}
			this.exitState();
			return new MoveState(this.graph);
		} else if (context == Context.REMOVE_VERTEX) {
			if (this instanceof RemoveVertexState) {
				return this;
			}
			this.exitState();
			return new RemoveVertexState(this.graph);
		} else if (context == Context.REMOVE_EDGE) {
			if (this instanceof RemoveEdgeState) {
				return this;
			}
			this.exitState();
			return new RemoveEdgeState(this.graph);
		}

		System.err.println(this + " returned null state!");
		return null;
	}
	
	public GraphRenderer getGraphRenderer() {
		return this.graph;
	}
}
