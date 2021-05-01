module TestingToolJavaFX {
	requires javafx.controls;
	requires org.jfxtras.styles.jmetro;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.desktop;
	
	opens gui to javafx.graphics, javafx.fxml;
	opens testing.tests to javafx.fxml;
}
