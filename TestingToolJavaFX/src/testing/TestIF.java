package testing;

import testing.results.TestResult;

public interface TestIF {
    TestResult run();
    void init();
    void setCanceled(boolean canceled);
    boolean isCanceled();
}
