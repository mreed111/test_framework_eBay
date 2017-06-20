package testing;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.appium.java_client.functions.ExpectedCondition;

public class testUtils extends testBase {
	
public String _verifyPageTitle(String[] strParts) 
{
    // get the title of the page.
    String pageTitle = driver.getTitle();
    System.out.println("_verifyPageTitle:  Page Title = " + pageTitle);
    
    for ( int i=0; i<strParts.length; i++ )
    {
    	Assert.assertTrue(pageTitle.contains(strParts[i]), strParts[i] + "in page title");
    }
    return pageTitle;
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

}
