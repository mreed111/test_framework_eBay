package testing;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.appium.java_client.functions.ExpectedCondition;

public class testUtils extends testBase {
	
public String _verifyPageTitle(String[] strParts) 
{
    // get the title of the page.
    String testPageTitle = driver.getTitle();
    System.out.println("_verifyPageTitle:  Page Title = " + testPageTitle);
    
    for ( int i=0; i<strParts.length; i++ )
    {
    	System.out.println("_verifyPageTitle:: " + testPageTitle);
    	Assert.assertTrue(testPageTitle.contains(strParts[i]), "\"" + strParts[i] + "\" in page title \"" +testPageTitle+ "\".");
    }
    return testPageTitle;
}

public String _verifyPageTitle()
{
	String title = driver.getTitle();
	Assert.assertTrue(title.length() > 0, "page title is not empty");
	return title;
}

public void waitForPageLoaded() {
    ExpectedCondition<Boolean> expectation = new
        ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
            }
        };
    try {
        Thread.sleep(1000);
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(expectation);
    } catch (Throwable error) {
        Assert.fail("Timeout waiting for Page Load Request to complete.");
    }
}

public void waitForLoad(WebDriver driver) {
    ExpectedCondition<Boolean> pageLoadCondition = new
        ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
            }
        };
    WebDriverWait wait = new WebDriverWait(driver, 30);
    wait.until(pageLoadCondition);
}


public String verifyLinkByPageTitle(WebElement link, String parentPageTitle)
{
	String nextLink = link.getText();
	String nextLinkHref = link.getAttribute("href");
	String newPageTitle = "";
	
	Set<String> winIds;
	
	System.out.println("verifyLinkByPageTitle: Testing \""+nextLink+"\" link:  href = " + nextLinkHref );
	
	winIds = this.driver.getWindowHandles();
	int pagesBeforeClickingLink = winIds.size();
	link.click();
	this.waitForPageLoaded();
	winIds = this.driver.getWindowHandles();
	int pagesAfterClickingLink = winIds.size();
	
	if (pagesAfterClickingLink > pagesBeforeClickingLink)
	{
		// link opened a new window.
		Iterator<String> winId = winIds.iterator();
		String parentWin = winId.next();
		String childWin = winId.next();
		this.driver.switchTo().window(childWin);
		newPageTitle = this.driver.getTitle();
		//Assert.assertNotEquals("verify " + nextLink + " Link", this.titleFirstPage, newPageTitle);
		
		this.driver.close();
		this.driver.switchTo().window(parentWin);
		this.driver.navigate().refresh();
	} else {
		// link opened in same driver page.
		newPageTitle = this.driver.getTitle();
		//Assert.assertNotEquals("verify " + nextLink + " Link", parentPage, newPageTitle);
		//System.out.println("\""+nextLink+"\" link opened \""+ newPageTitle + "\"");
		this.driver.navigate().back();
		this.waitForPageLoaded();
		// some links require two attempts to go back to eBay.com
		if (!this.driver.getTitle().equals(parentPageTitle))
		{
			System.out.println("clicking back button on \"" + nextLink + "\" page a second time.");
			this.driver.navigate().back();
			this.waitForPageLoaded();
		}
	}
	
	if (newPageTitle.equals(parentPageTitle))
	{
		return nextLink;
	}
	
	return "";
}



}
