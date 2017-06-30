package org.epam.pages;

import java.util.Map;

import org.epam.abstractpage.BasePage;
import org.epam.utils.DatePickerClass;
import org.epam.utils.HelperClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {

	@FindBy(xpath = "//span[text()='Return']")
	private WebElement returnLabel;

	@FindBy(xpath = "//input[contains(@id,'_TextBoxMarketOrigin1')]")
	private WebElement txtFrom;

	@FindBy(xpath = "//input[contains(@id,'_TextBoxMarketDestination1')]")
	private WebElement txtDestination;

	private String cityFirst = "//div[@id='stationsList']/ul/li/a/strong[contains(text(),'";
	private String cityNext = "')]";

	@FindBy(id = "DropDownListPassengerType_ADT_PLUS")
	private WebElement adultPassengers;

	@FindBy(id = "adtSelectorDropdown")
	private WebElement slctAdults;

	@FindBy(xpath= "//select[@id='AvailabilitySearchInputXmlSearchView_DropDownListPassengerType_CHD']")
	private WebElement slctChildren;

	@FindBy(xpath = "//select[@id='AvailabilitySearchInputXmlSearchView_DropDownListPassengerType_INFANT']")
	private WebElement slctBabies;

	@FindBy(xpath = "//div[@class='popupBottomSingleButton']/a")
	private WebElement babyPopup;

	@FindBy(xpath = "//label[@for='isResident']")
	private WebElement chkResidents;

	@FindBy(xpath = "//select[contains(@id,'_residentFamNumSelector')]")
	private WebElement slctFamilyType;

	@FindBy(xpath = "//a[contains(@id,'_btnClickToSearchNormal')]")
	private WebElement lnkSearchFlights;

	private HelperClass helper;
	private DatePickerClass datePicker;	

	public HomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(getDriver(), this);
		helper = new HelperClass();
		datePicker = new DatePickerClass();
	}

	private void bookingType() {
		returnLabel.click();
	}

	private void setFromCity(String fromCity) {
		txtFrom.clear();
		txtFrom.sendKeys(fromCity);
		helper.sleep(3000);
		By lnkCityFrom = By.xpath(cityFirst + fromCity + cityNext);
		getDriver().findElement(lnkCityFrom).click();
	}

	private void setDestinationCity(String destinationCity) {
		helper.waitForElementVisible(txtDestination, helper.MIDTIME);
		txtDestination.clear();
		txtDestination.sendKeys(destinationCity);
		helper.sleep(3000);
		By destination = By.xpath(cityFirst + destinationCity + cityNext);
		getDriver().findElement(destination).click();
	}

	private void setFromDate(String outBoundDate) {
		datePicker.selectDate(outBoundDate);
	}

	private void setToDate(String inBoundDate) {
		datePicker.selectDate(inBoundDate);
	}

	private void setPassengers(String adultsCount, String childrenCount, String babiesCount) {
		helper.waitForElementVisible(adultPassengers,  helper.MIDTIME);
		adultPassengers.click();
		helper.waitForElementVisible(slctAdults,  helper.MIDTIME);
		helper.selectOptionByText(slctAdults, adultsCount);
		helper.waitForElementVisible(slctChildren,  helper.MIDTIME);
		helper.selectOptionByText(slctChildren, childrenCount);
		helper.waitForElementVisible(slctBabies,  helper.MIDTIME);
		helper.selectOptionByText(slctBabies, babiesCount);
		helper.jsClick(babyPopup);
	}

	private void setFamilyType(String familyType) {
		helper.waitForElementVisible(chkResidents, helper.MIDTIME);
		chkResidents.click();
		helper.selectOptionByText(slctFamilyType, familyType);

	}

	public BookingResultsPage flightSearch(Map<String,String> data) {
		bookingType();
		setFromCity(data.get("fromCity"));
		setDestinationCity(data.get("destinationCity"));
		setFromDate(data.get("outBoundDate"));
		setToDate(data.get("inBoundDate"));
		setPassengers(data.get("adultsCount"), data.get("childrenCount"), data.get("babiesCount"));
		setFamilyType(data.get("familyType"));
		helper.highlightElement(lnkSearchFlights);
		lnkSearchFlights.click();
		return new BookingResultsPage(getDriver());

	}

}
