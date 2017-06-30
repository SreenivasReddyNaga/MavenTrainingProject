package org.epam.tests;

import java.util.Map;

import org.epam.annotations.DataSourceSheet;
import org.epam.browser.SetUpWebDriver;
import org.epam.browser.WebDriverBaseClass;
import org.epam.listeners.TestNGCustomListener;
import org.epam.model.FlightDetails;
import org.epam.pages.BookingResultsPage;
import org.epam.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Listeners(TestNGCustomListener.class)
public class FlightBookingTest extends WebDriverBaseClass {
	private static final String expTitle = "Cheap flights, hotel deals, rental car | vueling.com";

	@DataSourceSheet(value = "FlightBooking")
	@Test(dataProvider = "row-based-data")
	public void flightBookingTest(Map<String, String> bookingData) {
		Assert.assertEquals(SetUpWebDriver.getDriverInstance().getTitle(), expTitle);

		boolean isFlightSearchComplete = new HomePage(SetUpWebDriver.getDriverInstance()).flightSearch(bookingData)
				.isDisplayed();
		Assert.assertTrue(isFlightSearchComplete, "User not able to search for flights");

	}

	@DataSourceSheet(value = "FlightBooking")
	@Test(dataProvider = "row-based-data", dependsOnMethods = { "flightBookingTest" })
	public void validateFlightSearchTest(Map<String, String> bookingData) {

		FlightDetails flightData = new FlightDetails()
				.withChooseYourflightTabStatus(bookingData.get("chooseYourflightTabStatusStatus"))
				.withSelectedFromCity(bookingData.get("selectedFromCity"))
				.withSelectedDestinationCity(bookingData.get("selectedDestinationCity"));

		SoftAssert softAssert = new SoftAssert();
		BookingResultsPage bookingPage = new BookingResultsPage(SetUpWebDriver.getDriverInstance());

		softAssert.assertEquals(bookingPage.getChooseYourflightTabStatus(), flightData.getChooseYourflightTabStatus());
		softAssert.assertEquals(bookingPage.getFromAndDestination().trim(),
				flightData.getSelectedFromCity() + " - " + flightData.getSelectedDestinationCity(),
				"Actual city details and Expected city details are not same");
		softAssert.assertEquals(bookingPage.getPassangersDetails().get("JourneyDates").trim(),
				bookingData.get("outBoundDate") + " - " + bookingData.get("inBoundDate"),
				"Actual Journy dates and Expected Journey dates are not same");
		softAssert.assertEquals(bookingPage.getPassangersDetails().get("PassengersData").trim(),
				bookingData.get("adultsCount") + " Adults, " + bookingData.get("childrenCount") + " Children, "
						+ bookingData.get("babiesCount") + " Babies",
				"Actual Passenger details and Expected Passenger details are not same");
		softAssert.assertAll();

	}

}
