package org.epam.abstractpage;

import org.openqa.selenium.WebDriver;

public abstract class BasePage {
	
	protected final WebDriver driver;

	public BasePage(WebDriver driver) {
		this.driver = driver;
	}
	
	protected WebDriver getDriver(){
		return this.driver;
	}

}
