/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.power.gude.utils.controlling;

import java.util.Arrays;

/**
 * Set of on/off status mode
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 12/27/2022
 * @since 1.0.0
 */
public enum OnOffStatus {

	ON("On", "1"),
	OFF("Off", "0"),
	ERROR("None", "-1");

	private final String uiName;
	private final String apiName;

	/**
	 * Parameterized constructor
	 *
	 * @param uiName ui name of on/off status
	 * @param apiName api name of on/off status
	 */
	OnOffStatus(String uiName, String apiName) {
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
	 * This method is used to get on/off status from ui value
	 *
	 * @param uiName is ui name of on/off status
	 * @return OnOffStatus is the on/off status that want to get
	 */
	public static OnOffStatus getByUIName(String uiName) {
		return Arrays.stream(values()).filter(status -> status.getUiName().equals(uiName)).findFirst().orElse(ERROR);
	}

	/**
	 * This method is used to get output status from ui value
	 *
	 * @param apiName is api name of on/off status
	 * @return OnOffStatus is the on/off status that want to get
	 */
	public static OnOffStatus getByAPIName(String apiName) {
		return Arrays.stream(values()).filter(status -> status.getApiName().equals(apiName)).findFirst().orElse(ERROR);
	}
}

