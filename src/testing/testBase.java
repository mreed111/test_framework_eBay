package testing;

import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;


public class testBase {
	
	public WebDriver driver;
	public Properties prop = new Properties();

//	public void Setup() throws IOException 
//	{
////		Properties prop = new Properties();
////		FileInputStream propStream = new FileInputStream("C:\\Users\\miker\\workspace\\framework_Boilerplate\\src\\config.properties");
////		
////		prop.load(propStream);
////		
////		System.out.println("Setup func: " + prop.getProperty("url"));
////		System.out.println("Setup func: " + prop.getProperty("browser"));
//		
//		this.getProps();
//		
//		//System.setProperty("webdriver.chrome.driver", "C:\\Users\\miker\\Downloads\\chromedriver.exe");
//		System.setProperty("webdriver.chrome.driver", "C:\\projects\\selenium\\selenium_drivers\\chromedriver.exe");
//		this.driver=new ChromeDriver();
//		this.driver.navigate().to(this.prop.getProperty("url"));
//		
//	    //driver.manage().window().maximize();
//		
//	}
	
	public void Setup() throws IOException 
	{
		this.getProps();
		
		if (this.prop.getProperty("browser").equals("chrome")) 
		{ 
			System.setProperty("webdriver.chrome.driver", "C:\\projects\\selenium\\selenium_drivers\\chromedriver.exe");
			this.driver=new ChromeDriver();
		} 
		else if (this.prop.getProperty("browser").equals("firefox")) 
		{
			System.setProperty("webdriver.gecko.driver","c:\\projects\\selenium\\selenium_drivers\\geckodriver.exe");
			this.driver = new FirefoxDriver();
		} 
		else 
		{
			System.setProperty("webdriver.ie.driver", "C:\\projects\\selenium\\selenium_drivers\\IEDriverServer.exe");
			this.driver = new InternetExplorerDriver();
		}
				
		this.driver.navigate().to(this.prop.getProperty("url"));
		
	}
	
	
	public void TearDown()
	{
		System.out.println("TearDown func:");
		this.driver.close();
	}
	
	public void getProps() throws IOException 
	{
		//
		//Properties prop = new Properties();
		FileInputStream propStream = new FileInputStream("C:\\Users\\miker\\workspace\\test_framework_eBay\\src\\config.properties");
		
		this.prop.load(propStream);
		
		System.out.println("Setup func: " + this.prop.getProperty("url"));
		System.out.println("Setup func: " + this.prop.getProperty("browser"));
		
	}

}
