package testing.dynamiclinkage;

import graphs.GraphRenderer;

public class TestingEnvironment implements TestingEnvironmentIF {
	private GraphRenderer graphRenderer;

	public TestingEnvironment(GraphRenderer graphRenderer) {
		this.graphRenderer = graphRenderer;
	}

	@Override
	public GraphRenderer getGraphRenderer() {
		return this.graphRenderer;
	}

}
