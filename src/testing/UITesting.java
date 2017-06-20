package testing;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UITesting  {
	
	testUtils tb = new testUtils();
	
	private String titleFirstPage;
	private String eBayFooterLinksTable = "//*[@id='glbfooter']/div[2]/table/tbody/tr";
	
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
	
    @Test
    public void TC_countFooterLinks() 
    {
    	String[] titleParts = {"Electronics, ", "Cars, ", "More | eBay"};
        this.titleFirstPage = tb._verifyPageTitle(titleParts);
        // find all links in the entire page.
        int linksCount = tb.driver.findElements(By.tagName("a")).size();
        System.out.println("Links Count = " + linksCount);
        
        Assert.assertTrue("page link count > 0", linksCount > 0);
        Assert.assertTrue("page link count > 0", linksCount < 501);
    }
     
    @Test
    public void TC_verifyFooterTableLinksCount() 
    {
        // count links in footer section
        WebElement footerSection = tb.driver.findElement(By.xpath(this.eBayFooterLinksTable));
        int footerLinks = footerSection.findElements(By.tagName("a")).size();
        System.out.println("Footer Section Links Count = " + footerLinks);
        
        // find columns in Footer Table.
        List<WebElement> footerLinkCols = footerSection.findElements(By.tagName("td"));
        System.out.println("Footer links are organized in " + footerLinkCols.size() + " columns.");
        
        // count links in each column and verify that column totals equals total links in the footer table.
        Iterator<WebElement> Cols = footerLinkCols.iterator();
        int totalLinksInCols = 0;
        while ( Cols.hasNext() )
        {
        	totalLinksInCols = totalLinksInCols + Cols.next().findElements(By.tagName("a")).size();
        }
        Assert.assertEquals("total links in columns of footer", footerLinks, totalLinksInCols);
    }
    
    @Test
    public void TC_verifyFooterLinks() {
        
    	// get the title of the initial page.
        this.titleFirstPage = tb._verifyPageTitle();
        
        // count	 links in footer section
        //WebElement footerSection = tb.driver.findElement(By.xpath(this.eBayFooterLinksTable));
        int linksCount = tb.driver.findElement(By.xpath(this.eBayFooterLinksTable)).findElements(By.tagName("a")).size();

        // Iterate through all links in the footer table.  Verify no broken links.
        for ( int i=10; i<linksCount; i++ )
        {
        	WebElement footerSection = tb.driver.findElement(By.xpath(this.eBayFooterLinksTable));
        	List<WebElement> footerLinks = footerSection.findElements(By.tagName("a"));
        	String nextLink = footerLinks.get(i).getText();
        	String nextLinkTitle = footerLinks.get(i).getAttribute("title");
        	if (nextLinkTitle.length() > 0)
        	{
        		System.out.println("Skipping \""+nextLink+"\" link:  ...");
        	}
        	else if (nextLink.length() > 0)
        	{
        		System.out.println("Testing \""+nextLink+"\" link:  ");
        		footerLinks.get(i).click();
        		tb.waitForPageLoaded();
        		//tb.waitForLoad(tb.driver);
        		String newPageTitle = tb.driver.getTitle();
        		Assert.assertNotEquals("verify " + nextLink + " Link", this.titleFirstPage, newPageTitle);
        		System.out.println("\""+nextLink+"\" link openned \""+ newPageTitle + "\"");
        		tb.driver.navigate().back();
        		tb.waitForPageLoaded();
        	}
        	
        	
        }

        

    }
    
}

