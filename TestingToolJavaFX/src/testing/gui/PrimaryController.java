package testing.gui;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ChoiceBox;
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
    public void initialize() {
		Graph graph = new Graph();
		graph = new GraphRenderer(graph, canvas.getGraphicsContext2D());
		
		state = new VertexState(graph);
    }

	@FXML
	private void canvasMousePressed(MouseEvent e) {
		state.ProcessMousePressedEvent(e);
	}

	@FXML
	private void canvasMouseMoved(MouseEvent e) {
		state.ProcessMouseMovedEvent(e);
	}

	@FXML
	private void canvasMouseReleased(MouseEvent e) {
		state.ProcessMouseReleasedEvent(e);
	}

	@FXML
	private void vertexPressed() {
		state = state.ProcessButtonClickedEvent(Context.VERTEX);
	}

	@FXML
	private void edgePressed() {
		state = state.ProcessButtonClickedEvent(Context.EDGE);
	}

	@FXML
	private void movePressed() {
		state = state.ProcessButtonClickedEvent(Context.MOVE);
	}
	
	@FXML
	private void removeVertexPressed() {
		state = state.ProcessButtonClickedEvent(Context.REMOVE_VERTEX);
	}
	
	@FXML
	private void removeEdgePressed() {
		state = state.ProcessButtonClickedEvent(Context.REMOVE_EDGE);
	}
}
