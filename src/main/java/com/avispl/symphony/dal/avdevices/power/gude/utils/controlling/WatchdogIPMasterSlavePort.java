/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.power.gude.utils.controlling;

import java.util.Arrays;

/**
 * Set of watchdog IP Master-Slave port mode
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 12/27/2022
 * @since 1.0.0
 */
public enum WatchdogIPMasterSlavePort {

	HOST_COME_UP("Host comes up then switch on and vice versa", "32"),
	HOST_GOES_DOWN("Host goes down then switch on and vice versa", "0"),
	ERROR("None", "-1");

	private final String uiName;
	private final String apiName;

	/**
	 * Parameterized constructor
	 *
	 * @param uiName ui name of watchdog IP Master-Slave port mode
	 * @param apiName api name watchdog IP Master-Slave port mode
	 */
	WatchdogIPMasterSlavePort(String uiName, String apiName) {
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
	 * This method is used to get watchdog IP Master-Slave port mode from ui value
	 *
	 * @param uiName is ui name of watchdog reset port when host down mode
	 * @return WatchdogResetPortWhenHostDownMode is the watchdog reset port when host down mode that want to get
	 */
	public static WatchdogIPMasterSlavePort getByUIName(String uiName) {
		return Arrays.stream(WatchdogIPMasterSlavePort.values()).filter(status -> status.getUiName().equals(uiName)).findFirst().orElse(WatchdogIPMasterSlavePort.ERROR);
	}

}

