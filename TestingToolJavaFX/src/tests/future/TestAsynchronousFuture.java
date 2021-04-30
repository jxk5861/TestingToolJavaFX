package tests.future;

import tests.TestIF;

public class TestAsynchronousFuture {
    TestResult result;
    boolean done;
    TestIF t;

    public TestAsynchronousFuture(TestIF t){
        this.t = t;
    }
    public boolean check(){
        return done;      
    }

    public synchronized TestResult getResult() throws InterruptedException{
        while(!done){
            wait();
        }
        return result;
    }

    public synchronized void Test(){
        result = t.run();
        done = true;
    }
}
