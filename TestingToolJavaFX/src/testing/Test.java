package testing;

import testing.dynamiclinkage.TestingEnvironmentIF;

public abstract class Test implements TestIF {
	protected TestingEnvironmentIF environment;
	protected boolean canceled;

	public Test(TestingEnvironmentIF environment) {
		this.environment = environment;
		this.canceled = false;
	}

	@Override
	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	@Override
	public boolean isCanceled() {
		return this.canceled;
	}
}
