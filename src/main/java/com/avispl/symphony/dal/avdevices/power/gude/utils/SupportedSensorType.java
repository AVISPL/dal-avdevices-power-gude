/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.power.gude.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Set of supported sensor type
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 12/27/2022
 * @since 1.0.0
 */
public enum SupportedSensorType {

	METER("Meter", 9, true),
	SENSOR_7106("Sensor7106", 53, true),
	SENSOR_7106_2("Sensor7106", 53, true),
	POWER_PORT("PowerPort", 8, false),
	ERROR("PowerPort", -1, false);

	private final String uiName;
	private final int code;
	private final boolean isHistorical;

	/**
	 * Parameterized constructor
	 *
	 * @param uiName ui name of fan status
	 * @param code api name of fan status
	 * @param isHistorical is historical sensor type
	 */
	SupportedSensorType(String uiName, int code, boolean isHistorical) {
		this.uiName = uiName;
		this.code = code;
		this.isHistorical = isHistorical;
	}

	/**
	 * Retrieves {@link #uiName}
	 *
	 * @return value of {@link #uiName}
	 */
	public String getUiName() {
		return uiName;
	}

	/**
	 * Retrieves {@link #code}
	 *
	 * @return value of {@link #code}
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Retrieves {@link #isHistorical}
	 *
	 * @return value of {@link #isHistorical}
	 */
	public boolean isHistorical() {
		return isHistorical;
	}

	/**
	 * init the values for supported sensor types
	 *
	 * @return Set<Integer> Set of supported sensor type
	 */
	public static Set<Integer> getSupportedSensorTypesCode() {
		Set<Integer> supportedSensorTypes = new HashSet<>();
		for (SupportedSensorType supportedSensorType : values()) {
			supportedSensorTypes.add(supportedSensorType.getCode());
		}
		return supportedSensorTypes;
	}

	/**
	 * Get supported sensor type by sensor code
	 *
	 * @return SupportedSensorType matched supportedSensorType
	 */
	public static SupportedSensorType getSupportedSensorTypeByCode(int code) {
		return Arrays.stream(values()).filter(type -> type.getCode() == code).findFirst().orElse(ERROR);
	}
}

