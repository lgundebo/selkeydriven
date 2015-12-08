package Browsers;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import DRC.AutomationFramework.WebDriver.DriverEnumerators.Browser;

public class ChromeCapability extends CapabilityBase
{

	@Override
	public DesiredCapabilities getDesiredCapabilities() {
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		capability.setCapability(ChromeOptions.CAPABILITY, options);
		
		setBrowserType(Browser.CHROME);
		
		return capability;
		
	}

}
