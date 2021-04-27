package testing.gui;

import java.lang.reflect.Field;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import testing.graphs.Graph;
import testing.graphs.GraphRenderer;
import testing.graphs.Vertex;
import testing.graphs.paths.C1PPath;
import testing.gui.state.DrawingState;
import testing.gui.state.DrawingState.Context;
import testing.gui.state.states.VertexState;
import testing.tests.TestCoverageMetrics;

public class PrimaryController {
	@FXML
	private Canvas canvas;
	private DrawingState state;

	public PrimaryController() {
	}

	@FXML
	private void initialize() {
		GraphRenderer graph = new GraphRenderer(canvas.getGraphicsContext2D(), canvas.getWidth(), canvas.getHeight());
		
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

	//TODO: Remove
	@FXML
	private void runTestAction() {
		try {
//			System.out.println(Arrays.toString(DrawingState.class.getDeclaredFields()));
//			if(true)return;
			Field f = DrawingState.class.getDeclaredField("graph");
			f.setAccessible(true);
			Object o = f.get(state);
			Graph graph = (Graph) o;
			Field f2 = Graph.class.getDeclaredField("map");
			f2.setAccessible(true);
			Object o2 = f2.get(graph);
			@SuppressWarnings("unchecked")
			Map<Vertex, Point2D> map = (Map<Vertex, Point2D>) o2;
			Vertex first = null;
			int firstId = Integer.MAX_VALUE;
			Vertex last = null;
			int lastId = Integer.MIN_VALUE;
			for (var x : map.entrySet()) {
				String name = x.getKey().getName();
				if (name.matches("\\d+")) {
					int id = Integer.parseInt(name);
					if (id < firstId) {
						firstId = id;
						first = x.getKey();
					}
					if (id > lastId) {
						lastId = id;
						last = x.getKey();
					}
				}
			}
			var x = TestCoverageMetrics.c1p(first, last);
			System.out.println("\n\n\n\n");
			System.out.println(x.size());
			x.forEach(C1PPath::print);
			System.out.println(x.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
