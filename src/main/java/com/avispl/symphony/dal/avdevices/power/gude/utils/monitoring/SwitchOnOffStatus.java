/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.power.gude.utils.monitoring;

import java.util.Arrays;

/**
 * Set of switch on/off status
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 12/27/2022
 * @since 1.0.0
 */
public enum SwitchOnOffStatus {

	ON("On", "1"),
	OFF("Off", "0"),
	ERROR("None", "-1");

	private final String uiName;
	private final String apiName;

	/**
	 * Parameterized constructor
	 * @param uiName ui name of switch on/off status
	 * @param apiName api name first of switch on/off status
	 */
	SwitchOnOffStatus(String uiName, String apiName) {
		this.uiName = uiName;
		this.apiName = apiName;
	}

	/**
	 * Retrieves {@code {@link #uiName }}
	 *
	 * @return value of {@link #uiName}
	 */
	public String getUiName() {
		return this.uiName;
	}

	/**
	 * Retrieves {@code {@link #apiName }}
	 *
	 * @return value of {@link #apiName}
	 */
	public String getApiName() {
		return apiName;
	}

	/**
	 * This method is used to get switch on/off status from ui value
	 *
	 * @param uiName is ui name of switch on/off status
	 * @return SwitchOnOffStatus is the switch on/off status that want to get
	 */
	public static SwitchOnOffStatus getByUIName(String uiName) {
		return Arrays.stream(values()).filter(status -> status.getUiName().equals(uiName)).findFirst().orElse(ERROR);
	}

	/**
	 * This method is used to get output status from ui value
	 *
	 * @param apiName is api name second of switch on/off status
	 * @return SwitchOnOffStatus is the switch on/off status that want to get
	 */
	public static SwitchOnOffStatus getByAPIName(String apiName) {
		return Arrays.stream(values()).filter(status -> status.getApiName().equals(apiName)).findFirst().orElse(ERROR);
	}
}

