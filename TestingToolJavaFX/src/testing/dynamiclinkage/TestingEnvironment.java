package testing.dynamiclinkage;

import java.io.File;
import java.lang.reflect.Constructor;

import graphs.GraphRenderer;
import testing.TestIF;

public class TestingEnvironment implements TestingEnvironmentIF {
	private GraphRenderer graphRenderer;

	public TestingEnvironment(GraphRenderer graphRenderer) {
		this.graphRenderer = graphRenderer;
	}

	@Override
	public GraphRenderer getGraphRenderer() {
		return this.graphRenderer;
	}
	
	@Override
	public TestIF loadTest(File file) {
		Class<?> clazz = Utility.tryLoadClassFromFile(file);
		if (clazz == null) {
			return null;
		}

		// Find the correct constructor (the one with environment as a parameter)
		for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
			if (constructor.getParameterTypes().length == 1) {
				if (TestingEnvironmentIF.class == constructor.getParameterTypes()[0]) {
					try {
						Object o = constructor.newInstance(this);
						if (o instanceof TestIF) {
							TestIF test = (TestIF) o;
							return test;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

}
