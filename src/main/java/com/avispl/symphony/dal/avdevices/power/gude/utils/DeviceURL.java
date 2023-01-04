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

	private DeviceURL() {
	}

	public static final String FIRST_LOGIN = "/dashboard.html";
	public static final String DEVICE_MONITORING = "/statusjsn.js?components=9029395";
	public static final String DEVICE_CONFIG = "/statusjsn.js?components=769";
}
