/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.power.gude.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Set of fan status
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 12/27/2022
 * @since 1.0.0
 */
public enum SupportedSensorType {

	METER("Meter", 9),
	SENSOR_7106("Sensor7106", 53),
	POWER_PORT("PowerPort", 8),
	ERROR("PowerPort", -1);

	private final String uiName;
	private final int code;

	/**
	 * Parameterized constructor
	 *
	 * @param uiName ui name of fan status
	 * @param code api name of fan status
	 */
	SupportedSensorType(String uiName, int code) {
		this.uiName = uiName;
		this.code = code;
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
	 * init the values for supported sensor types
	 *
	 * @return Set<Integer> Set of supported sensor type
	 */
	public static Set<Integer> getSupportedSensorTypesCode() {
		Set<Integer> supportedSensorTypes = new HashSet<>();
		for (SupportedSensorType supportedSensorType : SupportedSensorType.values()) {
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
		return Arrays.stream(SupportedSensorType.values()).filter(type -> type.getCode() == code).findFirst().orElse(ERROR);
	}
}

