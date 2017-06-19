package testing;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
 
public class FunctionalTesting {
	
	testBase tb = new testBase();
	
    @BeforeSuite
    public void beforeSuite() throws IOException {
        System.out.println("beforeSuite");
        tb.Setup();
        
    }
     
    @BeforeTest
    public void beforeTest() {
        System.out.println("beforeTest");
    }
     
    @BeforeMethod
    public void beforeMethod() {
        System.out.println("beforeMethod");
    }
     
    @Test
    public void scenario1() {
        System.out.println("Scenario1");
    }
     
    @Test(groups="someFeature")
    public void scenario2() {
        System.out.println("Scenario2");
    }
     
    @Test
    public void scenario3() {
        System.out.println("Scenario3");
    }
     
    @AfterMethod
    public void afterMethod() {
        System.out.println("afterMethod");
    }
     
    @AfterTest
    public void afterTest() {
        System.out.println("afterTest");
    }
     
    @AfterSuite
    public void afterSuite() {
        System.out.println("afterSuite");
        tb.TearDown();
    }
}
