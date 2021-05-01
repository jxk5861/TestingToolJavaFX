package testing;

import testing.dynamiclinkage.EnvironmentIF;

public abstract class Test implements TestIF {
	protected EnvironmentIF environment;
	protected boolean canceled;

	public Test(EnvironmentIF environment) {
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
