package org.epam.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.epam.utils.HelperClass;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestNGCustomListener implements ITestListener, ISuiteListener, IInvokedMethodListener {

	private static final Logger LOGGER = LogManager.getRootLogger();
	private HelperClass helper;
	

	public TestNGCustomListener() {
		helper = new HelperClass();
	}

	public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {

	}

	public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {

	}

	public void onStart(ISuite iSuite) {

	}

	public void onFinish(ISuite iSuite) {

	}

	public void onTestStart(ITestResult iTestResult) {

		LOGGER.info("listener - start of the test");

	}

	public void onTestSuccess(ITestResult iTestResult) {

	}

	public void onTestFailure(ITestResult iTestResult) {

		helper.takeScreenShot(iTestResult.getName());
		LOGGER.info("On Failure Screen shot captured and Stored");

	}

	public void onTestSkipped(ITestResult iTestResult) {

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

	}

	public void onStart(ITestContext iTestContext) {

	}

	public void onFinish(ITestContext iTestContext) {

	}

}
