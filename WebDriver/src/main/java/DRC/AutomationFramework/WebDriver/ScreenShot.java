package DRC.AutomationFramework.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ScreenShot extends BaseDriver
{
	
	private String baseFilePath = "\\\\sqafs01\\qadoc\\DRCAutomation\\";
	private String fileExtension =".BMP";
	
	public ScreenShot(RemoteWebDriver webDriver)
	{
		super(webDriver);
	}

	public File TakeScreenshot()
	{
		/*
		
		File image = new File("\\\\sqafs01\\qadoc\\AutomationImages\\Debug\\test1.jpeg");
		try {
			image.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		File image =null;
		try
		{
		image = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		}
		catch(WebDriverException e)
		{
			e.printStackTrace();
			
		}
	//	image.
		 return image;
		
	}
	
	public String TakeAndSaveScreenShot(String filePath, String fileName) 
	{
		
		filePath+=  fileName + fileExtension;
		
		File image = new File(filePath);
		
		try {
			FileUtils.copyFile(TakeScreenshot(), image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		System.out.println(e.getMessage());
		}
		
		return filePath;
	}
	

	/*
	public class Testing {
	    
	    public void myTest() throws Exception {
	        WebDriver driver = new RemoteWebDriver(
	                                new URL("http://localhost:4444/wd/hub"), 
	                                DesiredCapabilities.firefox());
	        
	        driver.get("http://www.google.com");
	        
	        // RemoteWebDriver does not implement the TakesScreenshot class
	        // if the driver does have the Capabilities to take a screenshot
	        // then Augmenter will add the TakesScreenshot methods to the instance
	        WebDriver augmentedDriver = new Augmenter().augment(driver);
	        File screenshot = ((TakesScreenshot)augmentedDriver).
	                            getScreenshotAs(OutputType.FILE);
	    }
	}
	*/
	
}


