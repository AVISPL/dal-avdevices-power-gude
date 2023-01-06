/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.power.gude.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Set of supported sensor field
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 12/27/2022
 * @since 1.0.0
 */
public enum SupportedSensorField {

	VOLTAGE("Voltage", "Voltage"),
	CURRENT("Current", "Current"),
	FREQUENCY("Frequency", "Frequency"),
	PHASE_IU("PhaseIU", "Phase"),
	ACTIVE_POWER("ActivePower", "PowerActive"),
	REACTIVE_POWER("ReactivePower", "PowerReactive"),
	APPARENT_POWER("ApparentPower", "PowerApparent"),
	POWER_FACTOR("PowerFactor", "PowerFactor"),
	RELATIVE_TIME("RelativeTime", "ResettableEnergyTime"),
	FWD_ACT_ENERGY_RES("FwdActEnergyRes", "ResettableEnergyActive"),
	FWD_ACT_ENERGY_NON_RES("FwdActEnergyNonRes", "TotalEnergyActive"),
	RESIDUAL_CURRENT("Residual Current", "ResidualCurrentACrms"),
	ID("id", "ID"),
	NAME("name", "Name"),
	TEMPERATURE("Temperature", "Temperature"),
	HUMIDITY("Humidity", "Humidity"),
	DEW_POINT("Dew Point", "DewPoint"),
	DEW_DIFF("Dew Diff", "DewDiff"),
	PRESSURE("Pressure", "Pressure");
	private final String apiName;
	private final String uiName;

	/**
	 * Parameterized constructor
	 *
	 * @param apiName api name of fan status
	 * @param uiName ui name of fan status
	 */
	SupportedSensorField(String apiName, String uiName) {
		this.apiName = apiName;
		this.uiName = uiName;
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
	 * Retrieves {@code {@link #apiName}}
	 *
	 * @return value of {@link #apiName}
	 */
	public String getApiName() {
		return apiName;
	}

	/**
	 * init the values for supported sensor value
	 *
	 * @return Map<String, String> Map of supported sensor field
	 */
	public static Map<String, String> getSupportedSensorFields() {
		Map<String, String> supportedSensorFields = new HashMap<>();
		for (SupportedSensorField supportedSensorField : SupportedSensorField.values()) {
			supportedSensorFields.put(supportedSensorField.getApiName(), supportedSensorField.getUiName());
		}
		return supportedSensorFields;
	}

}

