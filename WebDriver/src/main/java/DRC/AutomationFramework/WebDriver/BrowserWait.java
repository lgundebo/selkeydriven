package DRC.AutomationFramework.WebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BrowserWait extends BaseDriver {

	public BrowserWait(RemoteWebDriver webDriver) {
		super(webDriver);

	}

	public void PageLoad() {

		WebDriverWait wait = new WebDriverWait(driver, 15);

		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {

				boolean loaded = ((JavascriptExecutor) driver).executeScript(
						"return document.readyState").equals("complete");

				if (loaded)
					return true;
				else
					return false;
			}
		});

	}

	public WebElement WaitElementPresent(final By by) {
		return WaitElementPresent(by, 15);
	}

	public WebElement WaitElementPresent(final By by, int secondsToWait) {
		// this.PageLoad();
		Boolean found = false;
		WebElement element = null;

		try {

			this.WaitElementNotPresent(By.className("blockUI"));

			WebDriverWait wait = new WebDriverWait(driver, secondsToWait);
			element = wait.until(ExpectedConditions
					.presenceOfElementLocated(by));
		}

		catch (Exception e) {
			element = null;
			if (!found && element == null) {
				System.out.println("");
				System.out.println("Web element not found with: "
						+ by.toString());
				System.out.println("");
			} else if (!found) {
				System.out.println("");
				System.out.println("Web element not displayed: "
						+ by.toString());
				System.out.println("");
			}

		}
		return element;
	}

	public void WaitElementClickable(WebElement element) {

		WebDriverWait wait = new WebDriverWait(driver, 15);
		element = wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void WaitElementDisplayed(WebElement element) {

		WebDriverWait wait = new WebDriverWait(driver, 15);
		element = wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void WaitElementNotPresent(final By by) {
		
		WebDriverWait wait = new WebDriverWait(driver, 30);

		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {

				WebElement element = null;

				try {
					element = driver.findElement(by); // .isDisplayed();
				} catch (Exception e) {

				}

				if (element == null)
					return true;
				else
					return false;
			}
		});

	}

	public void WaitElementNotDisplayed(final By by) {
		
		Boolean elementWasDisplayed = false;
		WebElement element = null;
		
		try {
			element = driver.findElement(by);
		} catch (Exception e) {
			element =null;
		}

		
		
		if (element != null) {
		
			/*
			 * Element for eDIRECT is not shown up as displayed right away
			 * looping 30 times looking for it to be displayed seems to 
			 * work
			 * 
			 */
			for(int i=0; i < 30; i++)
			{

				element= driver.findElement(by);
				
				if(element.isDisplayed())
				{
					elementWasDisplayed = true;
					//System.out.println("I equals: " + i);
					break;
				}
			}
			
			
			while (driver.findElement(by).isDisplayed()) {
				
				elementWasDisplayed = true;
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					
				}
			}
			if(elementWasDisplayed)
			{

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					
				}
			}
		}

	}


}
