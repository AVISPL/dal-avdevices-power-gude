/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.power.gude.utils.controlling;

import java.util.Arrays;

/**
 * Set of watchdog reset port when host down mode
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 12/27/2022
 * @since 1.0.0
 */
public enum WatchdogResetPortWhenHostDownMode {

	INFINITE_WAIT("Infinite wait for booting host after reset", "0"),
	REPEAT_RESET("Repeat reset on booting host after ping timeouts", "32"),
	ERROR("None", "-1");

	private final String uiName;
	private final String apiName;

	/**
	 * Parameterized constructor
	 *
	 * @param uiName ui name of watchdog reset port when host down mode
	 * @param apiName api name watchdog reset port when host down mode
	 */
	WatchdogResetPortWhenHostDownMode(String uiName, String apiName) {
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
	 * This method is used to get watchdog reset port when host down mode from ui value
	 *
	 * @param uiName is ui name of watchdog reset port when host down mode
	 * @return WatchdogResetPortWhenHostDownMode is the watchdog reset port when host down mode that want to get
	 */
	public static WatchdogResetPortWhenHostDownMode getByUIName(String uiName) {
		return Arrays.stream(values()).filter(status -> status.getUiName().equals(uiName)).findFirst().orElse(ERROR);
	}

	/**
	 * This method is used to get output status from ui value
	 *
	 * @param apiName is api name second of on/off status
	 * @return WatchdogResetPortWhenHostDownMode is the watchdog reset port when host down mode that want to get
	 */
	public static WatchdogResetPortWhenHostDownMode getByAPIName(String apiName) {
		return Arrays.stream(values()).filter(status -> status.getApiName().equals(apiName)).findFirst().orElse(ERROR);
	}
}

