package testing.future;

import testing.TestIF;
import testing.TestResult;

public class TestAsynchronousFuture {
	TestResult result;
	boolean done;
	TestIF t;

	public TestAsynchronousFuture(TestIF t) {
		this.t = t;
	}

	/**
	 * Check if the test has completed.
	 */
	public boolean check() {
		return done;
	}

	/**
	 * Mark the test as canceled.
	 */
	public void cancel() {
		t.setCanceled(true);
	}

	/**
	 * Wait for the test to complete then get its result.
	 */
	public synchronized TestResult getResult() throws InterruptedException {
		while (!done) {
			wait();
		}
		return result;
	}

	public synchronized void test() {
		result = t.run();
		done = true;
	}
}
