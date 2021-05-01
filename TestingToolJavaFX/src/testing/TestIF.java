package testing;

public interface TestIF {
    TestResult run();
    void init();
    void setCanceled(boolean canceled);
    boolean isCanceled();
}
