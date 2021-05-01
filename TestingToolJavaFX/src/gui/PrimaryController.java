package gui;

import java.io.File;
import java.lang.reflect.Constructor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import graphs.GraphRenderer;
import gui.state.DrawingState;
import gui.state.DrawingState.Context;
import gui.state.states.VertexState;
import testing.TestIF;
import testing.dynamiclinkage.Environment;
import testing.dynamiclinkage.EnvironmentIF;
import testing.dynamiclinkage.Utility;
import testing.future.TestFuture;
import testing.tests.C1;
import testing.tests.C1P;

public class PrimaryController {
	@FXML
	private MenuButton testMenu;
	@FXML
	private Canvas canvas;
	private DrawingState state;

	public PrimaryController() {
	}

	@FXML
	private void initialize() {
		GraphRenderer graph = new GraphRenderer(canvas.getGraphicsContext2D(), canvas.getWidth(), canvas.getHeight());

		state = new VertexState(graph);

		MenuItem load = new MenuItem("Load Test");
		load.setOnAction((e) -> this.loadTest(e));
		testMenu.getItems().add(load);

		// Add two default tests.
		Environment environment = new Environment(state.getGraphRenderer());
		this.addTest(new C1(environment));
		this.addTest(new C1P(environment));

	}

	@FXML
	private void canvasMousePressed(MouseEvent e) {
		// change state event though it doesn't change.
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
	private void vertexButtonAction(ActionEvent event) {
		state = state.processButtonClickedEvent(Context.VERTEX);
	}

	@FXML
	private void edgeButtonAction(ActionEvent event) {
		state = state.processButtonClickedEvent(Context.EDGE);
	}

	@FXML
	private void moveButtonAction(ActionEvent event) {
		state = state.processButtonClickedEvent(Context.MOVE);
	}

	@FXML
	private void removeVertexButtonAction(ActionEvent event) {
		state = state.processButtonClickedEvent(Context.REMOVE_VERTEX);
	}

	@FXML
	private void removeEdgeButtonAction(ActionEvent event) {
		state = state.processButtonClickedEvent(Context.REMOVE_EDGE);
	}

	private void addTest(TestIF test) {
		String name = test.getClass().getSimpleName();
		MenuItem item = new MenuItem(name);
		item.setOnAction((e) -> createFuture(test));

		// Make sure the same item cannot be added twice.
		for (MenuItem i : testMenu.getItems()) {
			if (i.getText().equals(item.getText())) {
				return;
			}
		}

		testMenu.getItems().add(Math.max(testMenu.getItems().size() - 1, 0), item);
	}

	private void loadTest(ActionEvent event) {
		FileChooser chooser = new FileChooser();
		File f = chooser.showOpenDialog(canvas.getScene().getWindow());

		if (f == null) {
			return;
		}

		// C:\Users\jjkar\git\TestingToolJavaFX\TestingToolJavaFX\target\classes\testing\tests\algorithms
		Class<?> clazz = Utility.tryLoadClassFromFile(f);
		if (clazz == null) {
			return;
		}

		// Find the correct constructor (the one with environment as a parameter)
		for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
			if (constructor.getParameterTypes().length == 1) {
				if (EnvironmentIF.class == constructor.getParameterTypes()[0]) {
					// Create the program environment from the graph renderer.
					Environment environment = new Environment(state.getGraphRenderer());
					try {
						Object o = constructor.newInstance(environment);
						if (o instanceof TestIF test) {
							this.addTest(test);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					return;
				}
			}
		}
	}

	private void createFuture(TestIF test) {
		test.init();
		// create future.
		new TestFuture(test);
	}
}
