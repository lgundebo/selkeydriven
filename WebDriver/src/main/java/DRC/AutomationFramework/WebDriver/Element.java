package DRC.AutomationFramework.WebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Element extends BaseDriver 
{
	
	public Element(RemoteWebDriver webDriver)
	{
		super(webDriver);
		
	}
	
	public Boolean Exists(By by)
	{
		Boolean displayed = false;
		try
		{
		displayed = driver.findElement(by).isDisplayed();
		}
		catch(Exception e)
		{
			displayed =false;
		}
		return displayed;
	}
}
