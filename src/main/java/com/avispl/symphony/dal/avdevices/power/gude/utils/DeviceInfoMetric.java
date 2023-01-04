/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.power.gude.utils;

import java.util.Arrays;
import java.util.Optional;

/**
 * Set of device info metric keys
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 12/25/2022
 * @since 1.0.0
 */
public enum DeviceInfoMetric {

	// Static metric
	ADVANCE_MONITORING("AdvanceMonitoring"),
	DEVICE_NAME("DeviceName"),
	FIRMWARE_VERSION("FirmwareVersion"),
	ALL_POWER_PORT_CONTROL("AllPowerPortControl");

	private final String name;

	/**
	 * Parameterized constructor
	 *
	 * @param name Name of monitoring metric
	 */
	DeviceInfoMetric(String name) {
		this.name = name;
	}

	/**
	 * retrieve {@code {@link #name}}
	 *
	 * @return value of {@link #name}
	 */
	public String getName() {
		return this.name;
	}


	/**
	 * This method is used to get device info metric by name
	 *
	 * @param name is the name of device info metric that want to get
	 * @return DeviceInfoMetric is the device info metric that want to get
	 */
	public static DeviceInfoMetric getByName(String name) {
		Optional<DeviceInfoMetric> deviceInfoMetric = Arrays.stream(DeviceInfoMetric.values()).filter(metric -> metric.getName().equals(name)).findFirst();
		if (deviceInfoMetric.isPresent()) {
			return deviceInfoMetric.get();
		} else {
			throw new IllegalStateException(String.format("control group %s is not supported.", name));
		}
	}

}

