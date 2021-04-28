package testing.tests;

public class TestFuture {
    TestResult result;
    TestIF t;
    TestAsynchronousFuture TAF;

    public TestFuture(TestIF t){
        this.t = t;
        TAF = new TestAsynchronousFuture(t);
        new Runner().start();
    }

    public boolean check(){
        return TAF.check();      
    }
    public TestResult waitForResult() throws InterruptedException{
        return TAF.getResult();
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
