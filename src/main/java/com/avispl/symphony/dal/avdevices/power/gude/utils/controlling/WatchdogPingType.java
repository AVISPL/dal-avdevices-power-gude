/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.power.gude.utils.controlling;

import java.util.Arrays;

/**
 * Set of ping type status mode
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 12/27/2022
 * @since 1.0.0
 */
public enum WatchdogPingType {

	ICMP("ICMP", "0"),
	TCP("TCP", "1"),
	ERROR("None", "-1");

	private final String uiName;
	private final String apiName;

	/**
	 * Parameterized constructor
	 *
	 * @param uiName ui name of ping type status
	 * @param apiName api name of ping type status
	 */
	WatchdogPingType(String uiName, String apiName) {
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
	 * This method is used to get ping type status from ui value
	 *
	 * @param uiName is ui name of ping type status
	 * @return OnOffStatus is the ping type status that want to get
	 */
	public static WatchdogPingType getByUIName(String uiName) {
		return Arrays.stream(WatchdogPingType.values()).filter(status -> status.getUiName().equals(uiName)).findFirst().orElse(WatchdogPingType.ERROR);
	}

	/**
	 * This method is used to get output status from ui value
	 *
	 * @param apiName is api name of ping type status
	 * @return PingType is the ping type status that want to get
	 */
	public static WatchdogPingType getByAPIName(String apiName) {
		return Arrays.stream(WatchdogPingType.values()).filter(status -> status.getApiName().equals(apiName)).findFirst().orElse(WatchdogPingType.ERROR);
	}
}

