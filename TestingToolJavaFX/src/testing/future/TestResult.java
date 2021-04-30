package testing.future;

import java.util.List;

import graphs.paths.GraphPath;

public class TestResult {
    List<GraphPath> paths;
    
    public TestResult(List<GraphPath> paths){
        this.paths = paths;
    }
}
