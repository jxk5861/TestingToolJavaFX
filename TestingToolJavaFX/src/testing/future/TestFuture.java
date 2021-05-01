package testing.future;

import testing.TestIF;
import testing.TestResult;
import testing.results.NullResult;

public class TestFuture {
	TestResult result;
	TestIF t;
	TestAsynchronousFuture TAF;

	public TestFuture(TestIF t) {
		this.t = t;
		t.setCanceled(false);
		TAF = new TestAsynchronousFuture(t);
		new Runner().start();
	}

	/**
	 * Check if the test has completed.
	 */
	public boolean check() {
		return TAF.check();
	}

	/**
	 * Mark the test as canceled.
	 */
	public void cancel() {
		TAF.cancel();
	}

	/**
	 * Wait for the test to complete then get its result.
	 */
	public TestResult waitForResult() throws InterruptedException {
		TestResult result = TAF.getResult();
		if (result == null) {
			result = new NullResult();
		}
		return result;
	}

	private class Runner extends Thread {
		public void run() {
			try {
				TAF.test();
			} catch (Exception e) {

			}
		}
	}
}
