package testing;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UI_footer_links  {
	
	testUtils tb = new testUtils();
	
	private String titleFirstPage;
	private String eBayFooterLinksTable = "//*[@id='glbfooter']/div[2]/table/tbody/tr";
	
	@BeforeMethod
    public void initBrowser() throws IOException, InterruptedException 
	{
        System.out.println("Test Setup:  initBrowser");
        tb.Setup();
        //tb.waitForPageLoaded();
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
        for ( int i=0; i<linksCount; i++ )
        {
        	WebElement footerSection = tb.driver.findElement(By.xpath(this.eBayFooterLinksTable));
        	List<WebElement> footerLinks = footerSection.findElements(By.tagName("a"));
        	String nextLink = footerLinks.get(i).getText();
        	String nextLinkTitle = footerLinks.get(i).getAttribute("title");
        	String nextLinkHref = footerLinks.get(i).getAttribute("href");
        	if (nextLinkTitle.length() > 0 && nextLink.length() > 0)
        	{
        		if (nextLinkTitle.equals("eBay country sites"))
        		{
        			// verify 'eBay country sites' selector link
        			System.out.println("Testing \""+nextLinkTitle+"\" link:  href = " + nextLinkHref );
        			Assert.assertEquals("verify mouse not over \""+nextLinkTitle+"\"", "false", footerLinks.get(i).getAttribute("aria-expanded"));
        			
        			Actions action = new Actions(tb.driver);
        			action.moveToElement(footerLinks.get(i)).build().perform();
        			System.out.println("visible after = " + footerLinks.get(i).getAttribute("aria-expanded"));
            		
            		Assert.assertEquals("verify mouse over on \""+nextLinkTitle+"\"", "true", footerLinks.get(i).getAttribute("aria-expanded"));
            		tb.driver.navigate().refresh();
        		} 
        		else if (nextLinkTitle.length() > 0 && nextLink.length() > 0)
        		{
        			// verify child window link
        			System.out.println("Testing \""+nextLink+"\" link:  href = " + nextLinkHref );
            		footerLinks.get(i).click();
            		Set<String> winIds = tb.driver.getWindowHandles();
            		System.out.println(winIds.size());
            		
            		Iterator<String> winId = winIds.iterator();
            		String parentWin = winId.next();
            		String childWin = winId.next();
            		tb.driver.switchTo().window(childWin);
            		String newPageTitle = tb.driver.getTitle();
            		Assert.assertNotEquals("verify " + nextLink + " Link", this.titleFirstPage, newPageTitle);
            		
            		tb.driver.close();
            		tb.driver.switchTo().window(parentWin);
            		tb.driver.navigate().refresh();
        		}
        	}
        	else if (nextLink.length() > 0)
        	{
        		System.out.println("Testing \""+nextLink+"\" link:  href = " + nextLinkHref );
        		footerLinks.get(i).click();
        		tb.waitForPageLoaded();
        		
        		String newPageTitle = tb.driver.getTitle();
        		Assert.assertNotEquals("verify " + nextLink + " Link", this.titleFirstPage, newPageTitle);
        		System.out.println("\""+nextLink+"\" link openned \""+ newPageTitle + "\"");
        		tb.driver.navigate().back();
        		tb.waitForPageLoaded();
        		// some links require two attempts to go back to eBay.com
        		if (!tb.driver.getTitle().equals(this.titleFirstPage))
        		{
        			System.out.println("clicking back button on \"" + nextLink + "\" page a second time.");
        			tb.driver.navigate().back();
            		tb.waitForPageLoaded();
        		}
        		
        	}
        	
        	
        }

        

    }
    
}

