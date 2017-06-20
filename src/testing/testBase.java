package testing;

import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.testng.annotations.AfterTest;
//import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class testBase {
	
	public WebDriver driver;
	public Properties prop = new Properties();

	public void Setup() throws IOException 
	{
//		Properties prop = new Properties();
//		FileInputStream propStream = new FileInputStream("C:\\Users\\miker\\workspace\\framework_Boilerplate\\src\\config.properties");
//		
//		prop.load(propStream);
//		
//		System.out.println("Setup func: " + prop.getProperty("url"));
//		System.out.println("Setup func: " + prop.getProperty("browser"));
		
		this.getProps();
		
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\miker\\Downloads\\chromedriver.exe");
		this.driver=new ChromeDriver();
		this.driver.navigate().to(this.prop.getProperty("url"));
		
	    //driver.manage().window().maximize();
		
	}
	
	
	
	
	public void TearDown()
	{
		System.out.println("TearDown func:");
		driver.quit();
	}
	
	public void getProps() throws IOException 
	{
		//
		//Properties prop = new Properties();
		FileInputStream propStream = new FileInputStream("C:\\Users\\miker\\workspace\\framework_Boilerplate\\src\\config.properties");
		
		prop.load(propStream);
		
		System.out.println("Setup func: " + this.prop.getProperty("url"));
		System.out.println("Setup func: " + this.prop.getProperty("browser"));
		
	}

}
