/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.power.gude.utils.controlling;

import java.util.Arrays;

/**
 * Set of WaitingTimeUnit mode
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 12/27/2022
 * @since 1.0.0
 */
public enum WaitingTimeUnit {

	SECOND("Second", 1),
	MINUTE("Minute", 60),
	HOUR("Hour", 3600),
	ERROR("None", -1);

	private final String uiName;
	private final int apiName;

	/**
	 * Parameterized constructor
	 * @param uiName ui name of WaitingTimeUnit mode
	 * @param apiName api name first of WaitingTimeUnit mode
	 */
	WaitingTimeUnit(String uiName, int apiName) {
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
	 * Retrieves {@link #apiName}
	 *
	 * @return value of {@link #apiName}
	 */
	public int getApiName() {
		return apiName;
	}

	/**
	 * This method is used to get WaitingTimeUnit mode from ui value
	 *
	 * @param uiName is ui name of WaitingTimeUnit mode
	 * @return WaitingTimeUnit is the WaitingTimeUnit mode that want to get
	 */
	public static WaitingTimeUnit getByUIName(String uiName) {
		return Arrays.stream(WaitingTimeUnit.values()).filter(status -> status.getUiName().equals(uiName)).findFirst().orElse(WaitingTimeUnit.ERROR);
	}

}

