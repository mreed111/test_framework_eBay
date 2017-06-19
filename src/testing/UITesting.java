package testing;

import java.io.IOException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UITesting  {
	
	testBase tb = new testBase();
	
	@BeforeMethod
    public void initBrowser() throws IOException 
	{
        System.out.println("Test Setup:  initBrowser");
        tb.Setup();
    }
	
	@AfterMethod
	public void testCleanup()
	{
		System.out.println("Test complete:  Close Browser");
		tb.TearDown();
	}
	
	@Test(groups="someFeature")
    public void ui1() throws IOException {
        System.out.println("UI1 testing");
        
    }
     
    @Test
    public void ui2() {
        System.out.println("UI2 testing");
        
        
    }
         
    @Test
    public void ui3() {
        System.out.println("UI3 testing");
    }
     
    @Test
    public void ui4() {
        System.out.println("UI4 testing");
    }
}

