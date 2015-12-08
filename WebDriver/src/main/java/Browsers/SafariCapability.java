package Browsers;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariOptions;

import DRC.AutomationFramework.WebDriver.DriverEnumerators.Browser;
public class SafariCapability extends CapabilityBase 
{

	@Override
	public DesiredCapabilities getDesiredCapabilities() {
		SafariOptions  options = new SafariOptions();
		
	/*
	 * 
	 * Set any safari options we need here
	 * 
	 */
		//options.setUseCleanSession(true);
		capability.setCapability(SafariOptions.CAPABILITY, options);
	setBrowserType(Browser.SAFARI);
		
		return capability;
	}

}
