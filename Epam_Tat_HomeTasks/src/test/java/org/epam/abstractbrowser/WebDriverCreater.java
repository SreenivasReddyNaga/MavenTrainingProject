package org.epam.abstractbrowser;

import org.openqa.selenium.WebDriver;

public abstract class WebDriverCreater {

	protected WebDriver driver;	
	
	public abstract WebDriver getWebDriverInstance();

}
