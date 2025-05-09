/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.power.gude.utils;

import java.util.Arrays;

/**
 * Set of devices metric group keys
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 12/25/2022
 * @since 1.0.0
 */
public enum DevicesMetricGroup {

	METER("Meter"),
	SENSOR("SensorPort"),
	OUTPUT("PowerPort"),
	CONTROL("Control"),
	POWER_PORT_CONFIG("PowerPortConfiguration");

	private final String name;

	/**
	 * Parameterized constructor
	 *
	 * @param name Name of monitoring metric group
	 */
	DevicesMetricGroup(String name) {
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
	 * This method is used to get device metric group by name
	 *
	 * @param name is the name of device metric group that want to get
	 * @return DevicesMetricGroup is the device metric group that want to get
	 */
	public static DevicesMetricGroup getByName(String name) {
		if (name.equals(POWER_PORT_CONFIG.getName())) {
			return POWER_PORT_CONFIG;
		}
		return Arrays.stream(values()).filter(group -> name.contains(group.getName())).findFirst()
				.orElseThrow(() -> new IllegalStateException(String.format("control group %s is not supported.", name)));
	}
}
