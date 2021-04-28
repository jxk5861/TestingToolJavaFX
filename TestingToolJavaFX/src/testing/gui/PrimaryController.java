package testing.gui;

import java.io.File;
import java.lang.reflect.Constructor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import testing.graphs.GraphRenderer;
import testing.gui.state.DrawingState;
import testing.gui.state.DrawingState.Context;
import testing.gui.state.states.VertexState;
import testing.tests.Environment;
import testing.tests.EnvironmentIF;
import testing.tests.TestFuture;
import testing.tests.TestIF;
import testing.tests.algorithms.C1;
import testing.tests.algorithms.C1P;
import testing.tests.loader.Utility;

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
		for(MenuItem i : testMenu.getItems()) {
			if(i.getText().equals(item.getText())) {
				return;
			}
		}
		
		testMenu.getItems().add(Math.max(testMenu.getItems().size() - 1, 0), item);
	}
	
	private void loadTest(ActionEvent event) {
		// load in test.
		FileChooser chooser = new FileChooser();
		File f = chooser.showOpenDialog(canvas.getScene().getWindow());
		// C:\Users\jjkar\git\TestingToolJavaFX\TestingToolJavaFX\target\classes\testing\tests\algorithms
//		File f = new File("C:\\Users\\jjkar\\git\\TestingToolJavaFX\\TestingToolJavaFX\\target\\classes\\testing\\tests\\algorithms\\C1.class");
		if(f == null) {
			return;
		}
		
		//C:\Users\jjkar\git\TestingToolJavaFX\TestingToolJavaFX\target\classes\testing\tests\algorithms
		Class<?> clazz = Utility.tryLoadClassFromFile(f);
		if(clazz == null) {
			return;
		}
		
		for(Constructor<?> constructor : clazz.getDeclaredConstructors()) {
			if(constructor.getParameterTypes().length == 1) {
				if(EnvironmentIF.class == constructor.getParameterTypes()[0]) {
					Environment environment = new Environment(state.getGraphRenderer());
					try {
						Object o = constructor.newInstance(environment);
						if(o instanceof TestIF test) {
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
		test.initialize();
		// create future.
		new TestFuture(test);
	}
}
