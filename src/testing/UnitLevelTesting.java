package testing;

import org.testng.annotations.Test;

public class UnitLevelTesting { // Extends testBase {
	
    @Test
    public void unitTest1() {
        System.out.println("Unit test1");
    }
     
    @Test
    public void unitTest2() {
        System.out.println("Unit test2");
    }
     
    @Test(groups="someFeature")
    public void unitTest3() {
        System.out.println("Unit test3");
    }

}
