package DRC.AutomationFramework.WebDriver;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.testng.TestListenerAdapter;
import org.testng.annotations.*;

import com.thoughtworks.selenium.webdriven.commands.GetTitle;

import DRC.AutomationFramework.WebDriver.DriverEnumerators.Browser;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.BrowserTargetBits;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.HubURL;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.Platform;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.Version;


public class ParrallelTest {

	/*
	DriverSetup info = new DriverSetup();
	
	
	public ParrallelTest()
	{
		info.browser = Browser.CHROME;
		info.hubURL = HubURL.NONE;// LOCALHOST;
		info.platform = Platform.WINDOWS;
		info.targetBits = BrowserTargetBits.BIT_32;
		info.version = Version.NOTSPECIFIED;
		
		
	}
	
	public ParrallelTest(DriverSetup setup)
	{
		info = setup;
		 System.out.println("Thread constructor: " + Thread.currentThread().getId());
	}
	
	
	@BeforeSuite
	//@Parameters({"param1"})
	public void SetupSuite()
	{
	//	this.HubURL;
		 System.out.println("Set Up Suite");
	    //    System.out.println("Getting param1: "+param1);
	     //   ParallelTest.setTestParam(param1);
		
	}
	
	
	
	RemoteDriver browser;
	String testName;
	List<String> imagePathPostfix = new ArrayList<String>(); 
	

	
	@BeforeMethod 
	public void beforeClass()
	{
		browser = new RemoteDriver(info);
		imagePathPostfix.add("Debug");
		imagePathPostfix.add("tester");
		imagePathPostfix.add(info.platform.getValue());
		
		imagePathPostfix.add(info.browser.getValue());
		imagePathPostfix.add(info.version.getValue() +"_" +info.targetBits.getValue());
	}
	
	*/
	
	/*
  @Test
  public void RemoteDriverTest() throws InterruptedException {
	  // browser = new RemoteDriver(Browser.Chrome, OSBit.ThirtyTwoBit);
	 
	  
		browser.Navigate("http://www.google.com");
	browser.screenShot.TakeAndSaveScreenShot(imagePathPostfix, "google");
	
  System.out.println( "Hello World!" + testName );
  browser.Navigate("http://www.yahoo.com");
  browser.screenShot.TakeAndSaveScreenShot(imagePathPostfix, "yahoo");
 
  System.out.println(Thread.currentThread().getId());
 
  
  }
  */
  /*
  @AfterMethod
  public void quit()
  {
	  
	  browser.Quit();  
  }
  */
  /*
  @Test
  public void RemoteDriverTestTwo() throws InterruptedException {
	  // browser = new RemoteDriver(Browser.Chrome, OSBit.ThirtyTwoBit);
	 
	  
		browser.Navigate("http://www.cnn.com");
	browser.screenShot.TakeAndSaveScreenShot(imagePathPostfix, "google");
	
  System.out.println( "Hello World2!");
  browser.Navigate("http://www.yahoo.com");
  browser.screenShot.TakeAndSaveScreenShot(imagePathPostfix, "yahoo");
  browser.Quit();
  }
  
  
  @Test
  public void Tester1() throws InterruptedException {
	//  browser = new RemoteDriver(Browser.IE, OSBit.ThirtyTwoBit);
  	
		browser.Navigate("http://www.google.com");
	
  System.out.println( "Hello World!1" );
  browser.Navigate("http://www.yahoo.com");
  browser.Quit();
  }
*/
}
