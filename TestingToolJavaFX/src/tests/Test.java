package tests;

import tests.dynamiclinkage.EnvironmentIF;

public abstract class Test implements TestIF {
    protected EnvironmentIF environment;

    public Test(EnvironmentIF environment){
        this.environment = environment;
    }
}
