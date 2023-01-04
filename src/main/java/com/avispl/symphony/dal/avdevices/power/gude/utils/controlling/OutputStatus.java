/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.power.gude.utils.controlling;

import java.util.Arrays;

/**
 * Set of output status
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 12/27/2022
 * @since 1.0.0
 */
public enum OutputStatus {

	ON("On", "1"),
	OFF("Off", "0"),
	ERROR("None", "-1");

	private final String uiName;
	private final String apiName;

	/**
	 * Parameterized constructor
	 * @param uiName ui name of output status
	 * @param apiName api name first of output status
	 */
	OutputStatus(String uiName, String apiName) {
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
	 * This method is used to get output status from ui value
	 *
	 * @param uiName is ui name of output status
	 * @return OutputStatus is the output status that want to get
	 */
	public static OutputStatus getByUIName(String uiName) {
		return Arrays.stream(OutputStatus.values()).filter(status -> status.getUiName().equals(uiName)).findFirst().orElse(OutputStatus.ERROR);
	}

	/**
	 * This method is used to get output status from ui value
	 *
	 * @param apiName is api name second of output status
	 * @return OutputStatus is the output status that want to get
	 */
	public static OutputStatus getByAPIName(String apiName) {
		return Arrays.stream(OutputStatus.values()).filter(status -> status.getApiName().equals(apiName)).findFirst().orElse(OutputStatus.ERROR);
	}
}

