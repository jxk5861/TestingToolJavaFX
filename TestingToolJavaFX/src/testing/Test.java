package testing;

import testing.dynamiclinkage.EnvironmentIF;

public abstract class Test implements TestIF {
    protected EnvironmentIF environment;

    public Test(EnvironmentIF environment){
        this.environment = environment;
    }
}
