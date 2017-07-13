package testing;

import java.util.Iterator;
import java.util.List;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UI_vaidate_footer_links extends validateAllLinks {

	public String titleFirstPage;
	public String eBayFooterLinksTable = "//*[@id='glbfooter']/div[2]/table/tbody/tr";
	
	@BeforeMethod
    public void setFooterPage()
	{
		this.targetDOMElement = eBayFooterLinksTable;
		String[] validResultStringsForLinkTests  = {"OK", "Moved Permanently", "Found"};
		this.validLinkTestResults = validResultStringsForLinkTests;
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
        Assert.assertTrue("page link count < 501", linksCount < 501);
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
    
}
