package gui;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import graphs.GraphRenderer;
import gui.state.DrawingState;
import gui.state.DrawingState.Context;
import gui.state.states.VertexState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import testing.TestIF;
import testing.TestResult;
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

	private Map<MenuItem, TestFuture> futures;
	private Map<MenuItem, TestResult> results;
	private Image checkIcon;
	private Image xIcon;

	public PrimaryController() {
		futures = new HashMap<>();
		results = new HashMap<>();
		
		this.checkIcon = new Image("check.png");
		this.xIcon = new Image("x.png");
	}

	@FXML
	private void initialize() {
		GraphRenderer graph = new GraphRenderer(canvas.getGraphicsContext2D(), canvas.getWidth(), canvas.getHeight());

		state = new VertexState(graph);

		// Add the "load test" menu item.
		MenuItem load = new MenuItem("Load Test");
		load.setOnAction((e) -> this.loadTest(e));
		testMenu.getItems().add(load);

		// Add two default tests.
		Environment environment = new Environment(state.getGraphRenderer());
		this.addTest(new C1(environment));
		this.addTest(new C1P(environment));

		// Start the future checker thread.
		Timeline futureChecker = new Timeline(new KeyFrame(Duration.millis(1000 / 20), e -> futureChecker(e)));
		futureChecker.setCycleCount(Timeline.INDEFINITE);
		futureChecker.play();
	}

	@FXML
	private void canvasMousePressed(MouseEvent e) {
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

	/**
	 * Add a unique test (by class name) to the list of menu items and register its
	 * action event.
	 */
	private void addTest(TestIF test) {
		// Just use the simple class name as the menu item.
		String name = test.getClass().getSimpleName();
		MenuItem item = new MenuItem(name);
		item.setOnAction(e -> testItemClicked(item, test));

		// Make sure the same item cannot be added twice.
		for (MenuItem i : testMenu.getItems()) {
			if (i.getText().equals(item.getText())) {
				return;
			}
		}

		// Add to end of list but in front of the "load test" item.
		testMenu.getItems().add(Math.max(testMenu.getItems().size() - 1, 0), item);
	}

	/**
	 * Load in a test from the specified file location. The program environment is
	 * created here and passed into the the test. The PrimaryController class is not
	 * passed in because giving programs that much access is not ideal.
	 */
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
						if (o instanceof TestIF) {
							TestIF test = (TestIF) o;
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

	/**
	 * Called when a test is clicked.
	 * 
	 * If the test icon is null (blank) the test is initialized and ran. If the test
	 * is running (the red x is visible) the test is canceled. If the test is
	 * complete (the green check mark is visible) the test results are displayed.
	 */
	private void testItemClicked(MenuItem item, TestIF test) {
		// If the test is running cancel it.
		if (futures.containsKey(item)) {
			// cancel the test (no need to synchronize since the checker is on the same
			// thread)
			futures.get(item).cancel();
			futures.remove(item);
			item.setGraphic(null);
			return;
		}

		if (results.containsKey(item)) {
			// Show the results
			TestResult result = results.get(item);
			result.display();

			// Remove the results from the results list and allow the test to be run again.
			results.remove(item);
			item.setGraphic(null);
			return;
		}

		// Set the menu item's graphic to the red x (running).
		item.setGraphic(new ImageView(xIcon));

		// Initialize the test with its testing information. Ex. start node
		test.init();

		// Create future.
		TestFuture future = new TestFuture(test);

		// Add the future to the running tests list.
		futures.put(item, future);
	}

	/**
	 * The controller checks the future list for futures which may have completed.
	 * If they have completed, the icon updates to a check mark instead of a red x.
	 * 
	 * Clicking on the item will display the results.
	 */
	private void futureChecker(ActionEvent event) {
		for (var i = futures.entrySet().iterator(); i.hasNext();) {
			var e = i.next();
			MenuItem item = e.getKey();
			TestFuture future = e.getValue();

			if (future.check()) {
				// remove from the checker map.
				i.remove();
				// put onto the results map.
				try {
					results.put(item, future.waitForResult());
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				item.setGraphic(new ImageView(checkIcon));
			}
		}
	}
}
