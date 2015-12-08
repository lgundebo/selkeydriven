package Browsers;

import org.openqa.selenium.remote.DesiredCapabilities;

import DRC.AutomationFramework.WebDriver.DriverEnumerators.Browser;



public class FirefoxCapability extends CapabilityBase {

	@Override
	public DesiredCapabilities getDesiredCapabilities() {
		
		//capability = DesiredCapabilities.firefox();
		
		setBrowserType(Browser.FIREFOX);
		
		return capability;
	}

}
