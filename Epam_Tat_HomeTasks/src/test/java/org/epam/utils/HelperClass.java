package org.epam.utils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.epam.browser.SetUpWebDriver;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HelperClass {

	private static final Logger LOGGER = LogManager.getRootLogger();
	public final int MINTIME = 10;
	public final int MAXTIME = 50;
	public final int MIDTIME = 20;
	

	public boolean isTextPresent(String textToVerify) {
		String bodyText = SetUpWebDriver.getDriverInstance().findElement(By.tagName("body")).getText();
		return bodyText.contains(textToVerify);
	}

	public void selectOptionByText(WebElement objProperty, String optionToSelect) {

		Select select = new Select(objProperty);
		select.selectByVisibleText(optionToSelect);
		LOGGER.info("Option with '" + optionToSelect + "' is available in Dropdown");

	}

	public WebElement waitForElementVisible(WebElement locator, int timeOut) {

		Wait<WebDriver> wait = new WebDriverWait(SetUpWebDriver.getDriverInstance(), timeOut);
		return wait.until(ExpectedConditions.visibilityOf(locator));
	}

	public void jsClick(WebElement element) {
		JavascriptExecutor jse = (JavascriptExecutor) SetUpWebDriver.getDriverInstance();
		jse.executeScript("arguments[0].click()", element);

	}

	public void takeScreenShot(String methodName) {
		File screenShot = ((TakesScreenshot) SetUpWebDriver.getDriverInstance()).getScreenshotAs(OutputType.FILE);
		try {
			String timeStamp = getTimeStamp();
			String screenShotsDirectory = System.getProperty("user.dir") + File.separator + "ScreenShots"
					+ File.separator;
			String screenShotLocation = screenShotsDirectory + methodName+"_" + timeStamp + ".png";
			FileUtils.copyFile(screenShot, new File(screenShotLocation));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void highlightElement(WebElement element) {

		String bgColor = element.getCssValue("backgroundColor");

		JavascriptExecutor js = ((JavascriptExecutor) SetUpWebDriver.getDriverInstance());

		js.executeScript("arguments[0].style.backgroundColor = '" + "yellow" + "'", element);

		sleep(2000); // just for demo

		js.executeScript("arguments[0].style.backgroundColor = '" + bgColor + "'", element);

	}

	public void sleep(int millis){

		try	{
			Thread.sleep(millis);
			}catch (InterruptedException e)	{
			e.printStackTrace();
	         }

	}

	public String getTimeStamp() {

		Date date = new Date();

		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

		String currentTimeStamp = dateFormat.format(date);

		return currentTimeStamp;
	}

	public Object[][] testData(Map<String, String> data) {
		List<Map<String, String>> dataMaps = new ArrayList<Map<String, String>>();
		if (data.size() > 0) {
			dataMaps.add(data);
		} else {
			LOGGER.info("Data Not Found");
		}

		return convertToArray(dataMaps);
	}

	public Object[][] convertToArray(List<Map<String, String>> dataMaps) {
		LOGGER.info("Converting results to an Array for TestNG");
		Object[][] testData = new Object[dataMaps.size()][1];
		for (int index = 0; index < dataMaps.size(); index++) {
			testData[index][0] = dataMaps.get(index);
		}
		LOGGER.info("Finished converting results to an Array for TestNG");
		return testData;
	}
	
	public String[] splitData(String dataToSplit, String delimiter) {
		return dataToSplit.split(delimiter);
}

}
