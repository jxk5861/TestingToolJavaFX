package testing.future;

import testing.TestIF;
import testing.results.NullResult;
import testing.results.TestResult;

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

	public boolean check() {
		return TAF.check();
	}

	public void cancel() {
		TAF.cancel();
	}

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
				TAF.Test();
			} catch (Exception e) {

			}
		}
	}
}
