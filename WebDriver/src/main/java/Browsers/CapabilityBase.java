package Browsers;

import org.openqa.selenium.remote.DesiredCapabilities;

import DRC.AutomationFramework.WebDriver.DriverEnumerators.Browser;

public abstract class CapabilityBase 
{
	DesiredCapabilities capability = new DesiredCapabilities();
	
	protected void setBrowserType(Browser browser)
	{
		capability.setBrowserName(browser.getValue());
		
	}

	abstract public DesiredCapabilities getDesiredCapabilities();
}
