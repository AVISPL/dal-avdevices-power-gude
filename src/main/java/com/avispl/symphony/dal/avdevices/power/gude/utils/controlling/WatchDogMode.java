/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.power.gude.utils.controlling;

import java.util.Arrays;

/**
 * Set of watchdog status mode
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 12/27/2022
 * @since 1.0.0
 */
public enum WatchDogMode {

	RESET_PORT_WHEN_HOST_DOWN("Reset port when host down", "32"),
	SWITCH_OFF_ONCE("Switch off once when host down", "16"),
	IP_MASTER_SLAVE_PORT_HOST_COME_UP("host comes up −> switch on, host goes down −> switch off", "4"),
	IP_MASTER_SLAVE_PORT_HOST_GOES_DOWN("host comes up −> switch on, host goes down −> switch off", "8");

	private final String uiName;
	private final String apiName;

	/**
	 * Parameterized constructor
	 *
	 * @param uiName ui name of watchdog status mode
	 * @param apiName api name watchdog status mode
	 */
	WatchDogMode(String uiName, String apiName) {
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
	 * This method is used to get watchdog status mode from ui value
	 *
	 * @param uiName is ui name of watchdog status mode
	 * @return WatchDogMode is the watchdog status mode that want to get
	 */
	public static WatchDogMode getByUIName(String uiName) {
		return Arrays.stream(WatchDogMode.values()).filter(status -> status.getUiName().equals(uiName)).findFirst().orElse(WatchDogMode.RESET_PORT_WHEN_HOST_DOWN);
	}

	/**
	 * This method is used to get output status from ui value
	 *
	 * @param apiName is api name watchdog status mode status
	 * @return WatchDogMode is the watchdog status mode that want to get
	 */
	public static WatchDogMode getByAPIName(String apiName) {
		return Arrays.stream(WatchDogMode.values()).filter(status -> status.getApiName().equals(apiName)).findFirst().orElse(WatchDogMode.RESET_PORT_WHEN_HOST_DOWN);
	}
}

