package Browsers;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import DRC.AutomationFramework.WebDriver.DriverEnumerators.Browser;

public class IECapability extends CapabilityBase
{

	@Override
	public DesiredCapabilities getDesiredCapabilities() {
		
		setBrowserType(Browser.IE);
		
		capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		
		return capability;
	}

}
