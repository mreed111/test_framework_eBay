package testing;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class validateAllLinks {
	
	testUtils tb = new testUtils();
	//ReadWriteExcelData xlsData = new ReadWriteExcelData();
	
	// default target link list is footer section of default URL (this.prop.getProperty("url"))
	public String targetDOMElement = "//*[@id='glbfooter']";
	public String[] validLinkTestResults = {"OK", "Moved Permanently", "Found"};
	
	private int linksCount;
	List<WebElement> linksCollection;
	Object[][] linkData;

	public void getLinkDataList ()
	{
		WebElement targetDom = tb.driver.findElement(By.xpath(this.targetDOMElement));
    	linksCollection = targetDom.findElements(By.tagName("a"));
    	this.linksCount = this.linksCollection.size();
    	
    	Object[][] data = new Object[linksCount][3];
    	for ( int i=0; i<linksCount; i++ )
	    {
	    	String nextLink = this.linksCollection.get(i).getText();
	    	String nextLinkHref = this.linksCollection.get(i).getAttribute("href");
	    	//System.out.println("getLinkDataList:: Testing \""+nextLink+"\" link:  href = " + nextLinkHref );
	
	    	data[i][0] = i;
	    	data[i][1] = nextLink;
	    	data[i][2] = nextLinkHref;
	    }
    	
    	this.linkData = data;
	}
	
	public static String isLinkBroken(URL url) throws Exception
	{
		String response = "";
 		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
 		try
 		{
 		    connection.connect();
 		    response = connection.getResponseMessage();	        
 		    connection.disconnect();
 		    return response;
 		}
 		catch(Exception exp)
 		{
 			return exp.getMessage();
 		}  				
 	}
	
	@BeforeTest
    public void initBrowser() throws IOException, InterruptedException 
	{
        //System.out.println("Test Setup:  initBrowser");
        tb.Setup();
        tb.waitForPageLoaded();
    }
	
	@AfterTest
	public void testCleanup()
	{
		//System.out.println("Test complete:  Close Browser");
		tb.TearDown();
	}
	
	@Test
    public void TC_countTargetLinks() // throws InterruptedException 
    {
		this.getLinkDataList();
        System.out.println("Testing \""+linksCollection.size()+"\" links:  " );
    	Assert.assertTrue("Target Dom section link count > 0", this.linksCount > 0);
    }
	
	//verify link
	@Test(dependsOnMethods={"TC_countTargetLinks"},dataProvider="getLinkData")
	public void TC_validateLink(int linkId, String linkText, String linkHref) throws IOException
	{
		
		String linkTestResult = "";
		try
    	{
			linkTestResult = isLinkBroken(new URL(linkHref));
    	}
    	catch(Exception exp)
    	{
    		Assert.fail("Link \""+linkText+"\" URL: " + linkHref + " Exception occured; " + exp.getMessage());	    		
    	}
		
		//System.out.println("Link \""+linkText+"\" URL: " + linkHref + " returned " + linkTestResult); 
        if ( !Arrays.asList(this.validLinkTestResults).contains(linkTestResult) )
        {
        	if (linkTestResult.contains("Not Found"))
        	{
        		Assert.fail("Broken Link \""+linkText+"\" URL: " + linkHref+ " returned " + linkTestResult);
        	} 
        	else 
        	{
        		System.out.println("Warning::: Link \""+linkText+"\" URL: " + linkHref + " returned " + linkTestResult);
        	}
        }
	}

	@DataProvider
	public Object[][] getLinkData() 
	{
		return this.linkData;
	}
	


}
