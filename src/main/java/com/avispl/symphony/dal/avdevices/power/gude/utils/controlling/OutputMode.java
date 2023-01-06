/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.power.gude.utils.controlling;

import java.util.Arrays;

/**
 * Set of output mode
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 12/27/2022
 * @since 1.0.0
 */
public enum OutputMode {

	BATCH("Batch", "5"),
	RESET("Reset", "12"),
	ON("On", "1"),
	OFF("Off", "1"),
	ERROR("None", "-1");

	private final String uiName;
	private final String apiName;

	/**
	 * Parameterized constructor
	 * @param uiName ui name of output mode
	 * @param apiName api name first of mode
	 */
	OutputMode(String uiName, String apiName) {
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
	 * This method is used to get output mode from ui value
	 *
	 * @param uiName is ui name of output mode
	 * @return OutputMode is the output mode that want to get
	 */
	public static OutputMode getByUIName(String uiName) {
		return Arrays.stream(OutputMode.values()).filter(status -> status.getUiName().equals(uiName)).findFirst().orElse(OutputMode.ERROR);
	}

	/**
	 * This method is used to get Shutter mode from ui value
	 *
	 * @param apiName is api name second of output mode
	 * @return OutputMode is the output mode that want to get
	 */
	public static OutputMode getByAPIName(String apiName) {
		return Arrays.stream(OutputMode.values()).filter(status -> status.getApiName().equals(apiName)).findFirst().orElse(OutputMode.ERROR);
	}
}

