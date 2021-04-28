package testing.tests;

import java.util.List;

import testing.graphs.paths.GraphPath;

public class TestResult {
    List<GraphPath> paths;
    
    public TestResult(List<GraphPath> paths){
        this.paths = paths;
    }
}
