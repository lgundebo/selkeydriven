package DRC.AutomationFramework.WebDriver;

import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class BaseDriver {
	
	protected  RemoteWebDriver driver;
	
	public BaseDriver()
	{}
	
	public BaseDriver(RemoteWebDriver webDriver)
	{
		driver = webDriver;
	}
}
