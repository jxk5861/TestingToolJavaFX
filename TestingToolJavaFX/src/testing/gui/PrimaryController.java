package testing.gui;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import testing.graphs.Graph;
import testing.graphs.GraphRenderer;
import testing.gui.state.DrawingState;
import testing.gui.state.DrawingState.Context;
import testing.gui.state.states.VertexState;

public class PrimaryController {
	@FXML
	private Canvas canvas;
	private DrawingState state;

	public PrimaryController() {
	}
	
	@FXML
    private void initialize() {
		Graph graph = new Graph();
		graph = new GraphRenderer(canvas.getGraphicsContext2D());
		
		state = new VertexState(graph);
    }

	@FXML
	private void canvasMousePressed(MouseEvent e) {
		// change state event though it doesnt change.
		state = state.processMousePressedEvent(e);
	}

	@FXML
	private void canvasMouseMoved(MouseEvent e) {
		state = state.processMouseMovedEvent(e);
	}

	@FXML
	private void canvasMouseReleased(MouseEvent e) {
		state = state.processMouseReleasedEvent(e);
	}

	@FXML
	private void vertexButtonAction() {
		state = state.processButtonClickedEvent(Context.VERTEX);
	}

	@FXML
	private void edgeButtonAction() {
		state = state.processButtonClickedEvent(Context.EDGE);
	}

	@FXML
	private void moveButtonAction() {
		state = state.processButtonClickedEvent(Context.MOVE);
	}
	
	@FXML
	private void removeVertexButtonAction() {
		state = state.processButtonClickedEvent(Context.REMOVE_VERTEX);
	}
	
	@FXML
	private void removeEdgeButtonAction() {
		state = state.processButtonClickedEvent(Context.REMOVE_EDGE);
	}
}
