package testing.gui.state;

import javafx.scene.input.MouseEvent;
import testing.graphs.GraphRenderer;
import testing.gui.state.states.EdgeState;
import testing.gui.state.states.MoveState;
import testing.gui.state.states.RemoveEdgeState;
import testing.gui.state.states.RemoveVertexState;
import testing.gui.state.states.VertexState;

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

	protected DrawingState nextState(Context context) {
		if (context == Context.VERTEX) {
			if (this instanceof VertexState) {
				return this;
			}
			return new VertexState(this.graph);
		} else if (context == Context.EDGE) {
			if (this instanceof EdgeState) {
				return this;
			}
			return new EdgeState(this.graph);
		} else if (context == Context.MOVE) {
			if (this instanceof MoveState) {
				return this;
			}
			return new MoveState(this.graph);
		} else if (context == Context.REMOVE_VERTEX) {
			if (this instanceof RemoveVertexState) {
				return this;
			}
			return new RemoveVertexState(this.graph);
		} else if (context == Context.REMOVE_EDGE) {
			if (this instanceof RemoveEdgeState) {
				return this;
			}
			return new RemoveEdgeState(this.graph);
		}

		System.err.println(this + " returned null state!");
		return null;
	}
}
