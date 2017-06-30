package org.epam.model;

public class FlightDetails {

	private String selectedDestinationCity;
	private String selectedFromCity;
	private String chooseYourflightTabStatus;

	public FlightDetails withSelectedFromCity(String selectedFromCity) {
		this.setSelectedFromCity(selectedFromCity);
		return this;
	}

	public FlightDetails withSelectedDestinationCity(String selectedDestinationCity) {
		this.setSelectedDestinationCity(selectedDestinationCity);
		return this;
	}

	public FlightDetails withChooseYourflightTabStatus(String ChooseYourflightTabStatus) {
		this.setChooseYourflightTabStatus(ChooseYourflightTabStatus);
		return this;

	}

	public String getSelectedDestinationCity() {
		return selectedDestinationCity;
	}

	public void setSelectedDestinationCity(String selectedDestinationCity) {
		this.selectedDestinationCity = selectedDestinationCity;
	}

	public String getSelectedFromCity() {
		return selectedFromCity;
	}

	public void setSelectedFromCity(String selectedFromCity) {
		this.selectedFromCity = selectedFromCity;
	}

	public String getChooseYourflightTabStatus() {
		return chooseYourflightTabStatus;
	}

	public void setChooseYourflightTabStatus(String chooseYourflightTabStatusStatus) {
		this.chooseYourflightTabStatus = chooseYourflightTabStatusStatus;
	}

}
