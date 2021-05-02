package testing.dynamiclinkage;

import java.io.File;

import graphs.GraphRenderer;
import testing.TestIF;

public interface TestingEnvironmentIF {
	GraphRenderer getGraphRenderer();
	TestIF loadTest(File f);
}
