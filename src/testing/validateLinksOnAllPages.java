package testing;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class validateLinksOnAllPages {
	
	testUtils tb = new testUtils();
	//ReadWriteExcelData xlsData = new ReadWriteExcelData();
	
	// default target link list is footer section of default URL (this.prop.getProperty("url"))
	public String targetDOMElement = "//*[@id='glbfooter']";
	public String[] validLinkTestResults = {"OK", "Moved Permanently", "Found"};
	
	private int linksCount;
	List<WebElement> linksCollection;
	Object[][] linkData;
	
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
	
	
	@Test(dataProvider="getTestPageInfo")
    public void TC_pageLinks(String page, String url, String domElement, String validLinkResults, String titleParts) 
    {
		tb.driver.navigate().to(url);
		this.targetDOMElement = domElement;
		String[] validResultStringsForLinkTests  = validLinkResults.split(",");
		this.validLinkTestResults = validResultStringsForLinkTests;
		
		
		String[] validTitleParts = titleParts.split(",");
		String pageTitle = tb._verifyPageTitle(validTitleParts);
		System.out.println("validLinkResults::: page " + pageTitle);
				
        // find all links in the entire page.
        int linksCount = tb.driver.findElements(By.tagName("a")).size();
        System.out.println("Page \"" +page+ "\", Links Count = " + linksCount);
        
        Assert.assertTrue("page link count > 0", linksCount > 0);
        
        // call TC_validateLink
        
    }
	
	@DataProvider
	public Object[][] getTestPageInfo()
	{
		//System.out.println("getTestPageInfo::: ");
		return ReadWriteExcelData.getExcelData("./LinkTestData.xls", "pageData", false);
	}

}
