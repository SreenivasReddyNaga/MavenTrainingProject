package org.epam.browser;

import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.epam.utils.ExcelDataUtil;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

public class WebDriverBaseClass {

	private static final Logger LOGGER = LogManager.getRootLogger();
	private String excelFilePath; 
	
	@BeforeClass
	@Parameters({ "browserName", "url" })
	public void setUp(String browserName, String url) {
		LOGGER.info("initializing the driver with browser name:" + browserName);
		SetUpWebDriver.initializeDriver(browserName);
		LOGGER.info("navigating to the Url:" + url);
		SetUpWebDriver.openUrl(url);
	}

	@AfterClass
	public void tearDown() {
		
		LOGGER.info("closing the driver");

		SetUpWebDriver.closeBrowser();

		LOGGER.info("driver  closed");
	}
	
	@BeforeSuite
	@Parameters({"excelFilePath"})
	public void excelPaths(String excelPath){
		this.excelFilePath= excelPath;
	}
	
	@DataProvider(name = "row-based-data")
	public Object[][] createRowBasedData(Method m) {
		ExcelDataUtil excelUtil=new ExcelDataUtil();
		 LOGGER.debug("trying to create row based data");
		Object[][] testData =excelUtil.getExcelData(excelFilePath, excelUtil.findDataSourceSheet(m));
		return testData;
	}

}
