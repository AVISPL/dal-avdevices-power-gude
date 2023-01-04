package com.avispl.symphony.dal.avdevices.power.gude.dto.monitoring.misc;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * misc
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 21/12/2022
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Misc {

	@JsonAlias("product_name")
	private String productName;

	@JsonAlias("firm_v")
	private String firmware;

	/**
	 * Retrieves {@link #productName}
	 *
	 * @return value of {@link #productName}
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * Sets {@link #productName} value
	 *
	 * @param productName new value of {@link #productName}
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * Retrieves {@link #firmware}
	 *
	 * @return value of {@link #firmware}
	 */
	public String getFirmware() {
		return firmware;
	}

	/**
	 * Sets {@link #firmware} value
	 *
	 * @param firmware new value of {@link #firmware}
	 */
	public void setFirmware(String firmware) {
		this.firmware = firmware;
	}
}