package testing.tests;

public abstract class Test implements TestIF {
    protected EnvironmentIF environment;

    public Test(EnvironmentIF environment){
        this.environment = environment;
    }
}
