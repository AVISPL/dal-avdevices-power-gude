/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.power.gude.utils.monitoring;

/**
 * Set of supported historical sensor field
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 12/27/2022
 * @since 1.0.0
 */
public enum HistoricalProperties {

	ACTIVE_POWER("PowerActive(W)"),
	REACTIVE_POWER("PowerReactive(VAR)"),
	APPARENT_POWER("PowerApparent(VA)"),
	CURRENT_POWER("Current(A)"),
	TEMPERATURE("Temperature(C)"),
	HUMIDITY("Humidity(%)"),
	DEW_POINT("DewPoint(C)"),
	DEW_DIFF("DewDiff(C)"),
	PRESSURE("Pressure(hPa)");
	private final String uiName;

	/**
	 * Parameterized constructor
	 *
	 * @param uiName ui name of fan status
	 */
	HistoricalProperties(String uiName) {
		this.uiName = uiName;
	}

	/**
	 * Retrieves {@code {@link #uiName }}
	 *
	 * @return value of {@link #uiName}
	 */
	public String getUiName() {
		return uiName;
	}

}

