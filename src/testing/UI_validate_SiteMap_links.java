package testing;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UI_validate_SiteMap_links extends validateAllLinks {

	public String titleFirstPage;
	public String eBaySiteMapPage = "/html/body";
	
	@BeforeMethod
    public void setSiteMapPage()
	{
		// navigate to the test page.
		String testUrl = "http://pages.ebay.com/sitemap.html";
		tb.driver.navigate().to(testUrl);
		
		this.targetDOMElement = eBaySiteMapPage;
		String[] validResultStringsForLinkTests  = {"OK", "Moved Permanently", "Found", "Moved Temporarily"};
		this.validLinkTestResults = validResultStringsForLinkTests;
		
    }
	

    @Test
    public void TC_countSiteMapLinks() 
    {
    	String[] titleParts = {"Sitemap", " | eBay"};
        this.titleFirstPage = tb._verifyPageTitle(titleParts);
        // find all links in the entire page.
        int linksCount = tb.driver.findElements(By.tagName("a")).size();
        System.out.println("Links Count = " + linksCount);
        
        Assert.assertTrue("page link count > 0", linksCount > 0);
        Assert.assertTrue("page link count < 999", linksCount < 999);
    }

}
