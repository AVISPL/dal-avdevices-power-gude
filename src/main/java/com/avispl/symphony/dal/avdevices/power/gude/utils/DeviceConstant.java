/*
 *  Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.power.gude.utils;

/**
 * Set of constants
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 12/25/2022
 * @since 1.0.0
 */
public class DeviceConstant {

	/**
	 * private/ non-parameterized constructor to prevent instance initialization
	 */
	private DeviceConstant() {
	}

	public static final String PLUS = "+";
	public static final String HASH = "#";
	public static final String COMMA = ",";
	public static final String COLON = ":";
	public static final String SPACE = " ";
	public static final String EMPTY = "";
	public static final String HTTP = "http";
	public static final String SCHEME_SEPARATOR = "://";
	public static final String NONE = "None";
	public static final String ENABLE = "Enable";
	public static final String DISABLE = "Disable";
	public static final String APPLY = "Apply";
	public static final String APPLYING = "Applying";
	public static final String SWITCHING_ON = "Switching On";
	public static final String ALL_ON = "All On";
	public static final String SWITCHING_OFF = "Switching Off";
	public static final String ALL_OFF = "All Off";
	public static final String CANCEL = "Cancel";
	public static final String CANCELING = "Canceling";
	public static final String BASIC = "Basic ";
	public static final String DIGEST = "Basic ";
	public static final String TWO_NUMBER_FORMAT = "%02d";
	public static final String DEFAULT_DEC_PRECISION_FORMAT = "%.0f";
	public static final String SPACE_REGEX = "\\s+";
	public static final String DECIMAL_FORMAT = "#.##";
	public static final String HOUR_MINUTE_SECOND_UNIT = "h:m:s";
	public static final String DEG_C_UNIT = "degC";
	public static final String CELSIUS_UNIT = "C";
	public static final String MILLI_AMPE_UNIT = "mA";
	public static final String MIN = "Min";
	public static final String MINIMUM = "Minimum";
	public static final String MAXIMUM = "Maximum";
	public static final String MAX = "Max";
	public static final String ALL_PORT_NUMBER = "all";
	public static final double DEFAULT_FOR_NULL_VALUE = 0f;
	public static final int MAX_NUMBER_OF_OUTPUT = 30;
	public static final int MIN_WAITING_TIME = 1;
	public static final int MAX_WAITING_TIME = 59;
	public static final int MIN_PORT = 1;
	public static final int MAX_PORT = 12;
	public static final int INIT_SWITCH_INDEX = 2;
	public static final int END_SWITCH_INDEX = 4;
	public static final int ON_OFF_SWITCH_API_FACTOR = 2;
	public static final String DEFAULT_WAITING_TIME = "5";
	public static final int INDEX_TO_ORDINAL_CONVERT_FACTOR = 1;
	public static final int FIRST_ORDINAL = 1;
	public static final float UNIT_TO_MILLI_CONVERT_FACTOR = 1000f;
	public static final int WATCHDOG_ICMP = 0;
	public static final int WATCHDOG_TCP = 1;
	public static final int RESET_PORT_ENABLED = 32;
	public static final int INFINITE_WAIT = 0;
	public static final int REPEAT_RESET = 64;
	public static final int SWITCH_OFF_ONCE = 16;
	public static final int SWITCH_WHEN_HOST_UP = 4;
	public static final int SWITCH_WHEN_HOST_DOWN = 8;
	public static final int COUNT_PING_REQUESTS = 128;
	public static final int INITIALIZATION_DELAY_MAX = 9999;
	public static final int INITIALIZATION_DELAY_MIN = 0;
	public static final int REPOWER_DELAY_MAX = 9999;
	public static final int REPOWER_DELAY_MIN = 0;

	public static final int RESET_DURATION_MAX = 9999;
	public static final int RESET_DURATION_MIN = 0;

	public static final int WATCHDOG_TCP_PORT_MAX = 65535;
	public static final int WATCHDOG_TCP_PORT_MIN = 1;

	public static final int WATCHDOG_PING_INTERVAL_MAX = 255;
	public static final int WATCHDOG_PING_INTERVAL_MIN = 1;

	public static final int WATCHDOG_PING_RETRIES_MAX = 255;
	public static final int WATCHDOG_PING_RETRIES_MIN = 0;

	public static final int WATCH_DOG_DELAY_BOOTING_TIME_MAX = 999;
	public static final int WATCH_DOG_DELAY_BOOTING_TIME_MIN = 0;


	/**
	 * Header representing a server requesting authentication.
	 */
	public static final String WWW_AUTHENTICATE = "WWW-Authenticate";

	/**
	 * Header representing the authorization the client is presenting to a server.
	 */
	public static final String AUTHORIZATION = "Authorization";
}

