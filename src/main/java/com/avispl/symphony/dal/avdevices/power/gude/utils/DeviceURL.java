/*
 *  Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.power.gude.utils;

/**
 * URLs which will be accessed
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 12/25/2022
 * @since 1.0.0
 */
public class DeviceURL {

	/**
	 * private/ non-parameterized constructor to prevent instance initialization
	 */
	private DeviceURL() {
	}

	public static final String FIRST_LOGIN = "/statusjsn.js?components=2";
	public static final String DEVICE_MONITORING = "/statusjsn.js?components=8470545";
	public static final String DEVICE_CONFIG = "/statusjsn.js?components=1";
}
