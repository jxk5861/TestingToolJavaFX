package testing;

import javafx.stage.Stage;
import testing.dynamiclinkage.EnvironmentIF;

public abstract class Test implements TestIF {
    protected EnvironmentIF environment;
    protected Stage stage;

    public Test(EnvironmentIF environment){
        this.environment = environment;
    }
}
