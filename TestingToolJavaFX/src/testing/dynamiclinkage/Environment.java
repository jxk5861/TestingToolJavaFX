package testing.dynamiclinkage;

import graphs.GraphRenderer;

public class Environment implements EnvironmentIF {
	private GraphRenderer graphRenderer;

	public Environment(GraphRenderer graphRenderer) {
		this.graphRenderer = graphRenderer;
	}

	@Override
	public GraphRenderer getGraphRenderer() {
		return this.graphRenderer;
	}

}
