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

	RESET_PORT_WHEN_HOST_DOWN("Reset Port", "32", "Reset port when host down: infinite wait for booting host after reset"),
	SWITCH_OFF_ONCE("Switch off once ", "16", "Switch off once when host down"),
	IP_MASTER_SLAVE_PORT("IP Master-Slave port", "8", "IP Master-Slave port: host goes down then switch on, host comes up then switch off");

	private final String uiName;
	private final String apiName;
	private final String status;

	/**
	 * Parameterized constructor
	 *
	 * @param uiName ui name of watchdog status mode
	 * @param apiName api name watchdog status mode
	 * @param status current status of Watchdog mode
	 */
	WatchDogMode(String uiName, String apiName, String status) {
		this.uiName = uiName;
		this.apiName = apiName;
		this.status = status;
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
	 * Retrieves {@link #status}
	 *
	 * @return value of {@link #status}
	 */
	public String getStatus() {
		return status;
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

