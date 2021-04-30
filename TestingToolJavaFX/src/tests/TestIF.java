package tests;

import tests.future.TestResult;

public interface TestIF {
    TestResult run();
    void initialize();
}
