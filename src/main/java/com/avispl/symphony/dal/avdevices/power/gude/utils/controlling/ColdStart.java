/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.power.gude.utils.controlling;

import java.util.Arrays;

/**
 * Set of cold start status mode
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 12/27/2022
 * @since 1.0.0
 */
public enum ColdStart {

	ON("On", "1"),
	OFF("Off", "0"),
	REMEMBER_LAST_STATE("Remember last state", "1"),
	ERROR("None", "-1");

	private final String uiName;
	private final String apiName;

	/**
	 * Parameterized constructor
	 *
	 * @param uiName ui name of cold start status
	 * @param apiName api name of cold start status
	 */
	ColdStart(String uiName, String apiName) {
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
	 * This method is used to get cold start status from ui value
	 *
	 * @param uiName is ui name of cold start status
	 * @return ColdStart is the cold start status that want to get
	 */
	public static ColdStart getByUIName(String uiName) {
		return Arrays.stream(ColdStart.values()).filter(status -> status.getUiName().equals(uiName)).findFirst().orElse(ColdStart.ERROR);
	}

	/**
	 * This method is used to get output status from ui value
	 *
	 * @param apiName is api name of cold start status
	 * @return ColdStart is the cold start status that want to get
	 */
	public static ColdStart getByAPIName(String apiName) {
		return Arrays.stream(ColdStart.values()).filter(status -> status.getApiName().equals(apiName)).findFirst().orElse(ColdStart.ERROR);
	}
}

