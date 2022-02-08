module TestingToolJavaFX {
	exports graphs.filter.source;
	exports testing;
	exports gui.state.states;
	exports graphs;
	exports graphs.filter.filters;
	exports testing.future;
	exports gui.state;
	exports testing.dynamiclinkage;
	exports testing.results;
	exports graphs.paths;
	exports graphs.filter;
	exports gui;
	exports testing.tests;

	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires org.jfxtras.styles.jmetro;
	
	opens gui to javafx.fxml;
}