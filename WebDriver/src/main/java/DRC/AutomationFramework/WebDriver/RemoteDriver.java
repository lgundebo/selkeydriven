package DRC.AutomationFramework.WebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import Browsers.ChromeCapability;
import Browsers.FirefoxCapability;
import Browsers.IECapability;
import Browsers.SafariCapability;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.Browser;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.BrowserTargetBits;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.HubURL;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.Platform;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.Version;

public class RemoteDriver extends BaseDriver
{

	public ScreenShot	screenShot;
	public BrowserWait	wait;
	public Element		element;

	public WebElement	currentElement;

	public RemoteDriver(DriverSetup info)
	{
		this(info.platform, info.browser, info.version, info.hubURL,
				info.targetBits);

	}

	public RemoteDriver(Platform platform, Browser browser, Version version,
			HubURL hubURL, BrowserTargetBits browserBits)
	{

		setWebDriverProperty(browserBits);
		DesiredCapabilities capability = getCapabilities(browser);

		if (platform == Platform.LOCAL)
		{

			createLocalDriver(browser, capability);

		} else
		{

			if (version != Version.NOTSPECIFIED)
			{
				capability.setCapability(CapabilityType.VERSION,
						version.getValue());
			}
			capability.setCapability(CapabilityType.PLATFORM,
					platform.getValue());
			driver = new RemoteWebDriver(hubURL.getValue(), capability);

		}

		initialize();

		wait.PageLoad();
		this.driver.manage().window().maximize();
		wait.PageLoad();

	}

	public RemoteDriver(Platform platform, Browser browser, Version version,
			URL hubURL, BrowserTargetBits browserBits)
	{

		setWebDriverProperty(browserBits);
		DesiredCapabilities capability = getCapabilities(browser);

		if (platform == Platform.LOCAL)
		{

			createLocalDriver(browser, capability);

		} else
		{

			if (version != Version.NOTSPECIFIED)
			{
				capability.setCapability(CapabilityType.VERSION,
						version.getValue());
			}
			capability.setCapability(CapabilityType.PLATFORM,
					platform.getValue());

			driver = new RemoteWebDriver(hubURL, capability);

		}

		initialize();

		wait.PageLoad();
		this.driver.manage().window().maximize();
		// wait.PageLoad();

	}

	/**
	 * @param platform
	 * @param browser
	 * @param version
	 * @param hubURL
	 * @param targetBits
	 */
	public RemoteDriver(Platform platform, Browser browser, String version,
			String hubURL, BrowserTargetBits targetBits)
	{
		setWebDriverProperty(targetBits);
		DesiredCapabilities capability = getCapabilities(browser);

		if (platform == Platform.LOCAL)
		{

			createLocalDriver(browser, capability);

		} else
		{

			if (version != Version.NOTSPECIFIED.toString())
			{
				capability.setCapability(CapabilityType.VERSION, version);
			}
			capability.setCapability(CapabilityType.PLATFORM,
					platform.getValue());
			try
			{
				driver = new RemoteWebDriver(new URL("http://" + hubURL
						+ "/wd/hub"), capability);
			} catch (MalformedURLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		initialize();
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		wait.PageLoad();
		this.driver.manage().window().maximize();
		wait.PageLoad();

	}

	public WebElement FindElement(By by) {
		this.wait.WaitElementPresent(by);
		return this.driver.findElement(by);
	}

	public List<WebElement> FindElements(By by) {
		//this.wait.WaitElementPresent(by);
		return this.driver.findElements(by);
	}

	private void setWebDriverProperty(BrowserTargetBits bits) {
		if (bits != BrowserTargetBits.NOTAPPLICABLE)
		{
			String userDir = System.getProperty("user.dir");

			// Set ChromeDriver location
			System.setProperty("webdriver.chrome.driver", userDir
					+ "\\WebDriversEXE\\" + bits.getValue()
					+ "\\chromedriver.exe");

			// Set IEDriver location
			System.setProperty("webdriver.ie.driver", userDir
					+ "\\WebDriversEXE\\" + bits.getValue()
					+ "\\IEDriverServer.exe");
			System.setProperty("webdriver.safari.driver", userDir
					+ "\\WebDriversEXE\\" + bits.getValue()
					+ "\\IEDriverServer.exe");

		}
	}

	private void createLocalDriver(Browser browser,
			DesiredCapabilities capability) {
		switch (browser)
		{
			case CHROME:
				driver = new ChromeDriver(capability);
				break;
			case IE:
				driver = new InternetExplorerDriver(capability);
				break;
			case FIREFOX:
				driver = new FirefoxDriver(capability);
				break;
			case SAFARI:
				driver = new SafariDriver(capability);
				break;
			default:
				throw new UnsupportedOperationException(
						"Desired capabilities not implemented for browser: "
								+ browser.getValue());
		}

	}

	private DesiredCapabilities getCapabilities(Browser browser) {
		DesiredCapabilities capabilities;

		switch (browser)
		{
			case CHROME:
				capabilities = (new ChromeCapability())
				.getDesiredCapabilities();
				break;
			case IE:
				capabilities = (new IECapability()).getDesiredCapabilities();
				break;
			case FIREFOX:
				capabilities = (new FirefoxCapability())
				.getDesiredCapabilities();
				break;
			case SAFARI:
				capabilities = (new SafariCapability())
				.getDesiredCapabilities();
				break;
			default:
				throw new UnsupportedOperationException(
						"Desired capabilities not implemented for browser: "
								+ browser.getValue());
		}

		return capabilities;
	}

	public void Navigate(String URL) {

		try
		{
			Thread.sleep(500);
			this.driver.navigate().to(new URL(URL));
			wait.PageLoad();
			Thread.sleep(500);
		} catch (MalformedURLException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void Quit() throws Exception {

		wait.PageLoad();

		this.driver.quit();

		Thread.sleep(1000);

	}

	public String GetDriverInfo()
	{
		String message = "Driver ID: ";
		
				try{ 
					message += driver.getSessionId();
				}
				catch(Exception e)
				{
					message+="null";
				}
				
				message+=System.getProperty("line.separator") +
				"Browser URL: ";
				try
				{
				message += driver.getCurrentUrl();
				}
				catch (Exception e)
				{
					message += "null";
				}
				
				message+=System.getProperty("line.separator") +
				"Driver object: ";
				try
				{
				message += driver.toString();
				}
				catch(Exception e)
				{
					message += "null";
				}
		
		return message;
		
	}
	
	
	public RemoteWebDriver driver() {
		return driver;

	}

	private void initialize() {
		screenShot = new ScreenShot(this.driver);
		wait = new BrowserWait(this.driver);
		element = new Element(this.driver);
	}

}
