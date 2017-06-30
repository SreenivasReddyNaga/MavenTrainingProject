package org.epam.browser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.epam.abstractbrowser.WebDriverCreater;
import org.epam.enums.Browser;
import org.epam.listeners.EventFiringListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class SetUpWebDriver {

	private static final Logger LOGGER = LogManager.getRootLogger();
	private static WebDriver driver;
	private static WebDriverCreater driverCreator;
	
	private SetUpWebDriver(){}
	
	public static void initializeDriver(String browserName) {
		switch (Browser.valueOf(browserName.toUpperCase())) {
		case FIREFOX:
			driverCreator = new FirefoxBrowser();
			driver = driverCreator.getWebDriverInstance();
			break;
		case IEXPLORE:
			driverCreator = new IEBrowser();
			driver = driverCreator.getWebDriverInstance();
			break;
		case CHROME:
			driverCreator = new ChromeBrowser();
			driver = driverCreator.getWebDriverInstance();
			break;
		case EDGE:
			driverCreator = new EdgeBrowser();
			driver = driverCreator.getWebDriverInstance();
			break;
		default:
			LOGGER.info("Invalid browser " + browserName);
			System.exit(0);
		}
		LOGGER.info("Script execting on " + browserName + "browser");
	}

	public static WebDriver getDriverInstance() {
		return driver;
	}

	public static EventFiringWebDriver getEDriverInstance() {
		return setEDriver();
	}

	public static EventFiringWebDriver setEDriver() {
		EventFiringListener eventFiring = new EventFiringListener(getDriverInstance());
		return eventFiring.getEdriver();
	}

	public static void openUrl(String url) {
		driver.manage().window().maximize();
		driver.get(url);
	}

	public static void closeBrowser()	{

		if (null != driver)	{
			driver.quit();
			driver = null;
		}

	}

}
