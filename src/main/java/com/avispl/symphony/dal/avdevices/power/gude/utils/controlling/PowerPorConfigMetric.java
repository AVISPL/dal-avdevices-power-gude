/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.power.gude.utils.controlling;

import java.util.Arrays;

/**
 * Set of output mode
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 12/27/2022
 * @since 1.0.0
 */
public enum PowerPorConfigMetric {

	CHOOSE_POWER_PORT("ChoosePowerPort", "p"),
	CUSTOM_LABEL("CustomLabel", "name"),
	INITIALIZATION_STATUS("InitializationStatus", "powup"),
	REMEMBER_LAST_STATE("RememberLastState", "powrem"),
	INITIALIZATION_DELAY("InitializationDelay(s)", "idle"),
	REPOWER_DELAY("RepowerDelay(s)", "on_again"),
	RESET_DURATION("ResetDuration(s)", "reset"),
	WATCHDOG("Watchdog", "we"),
	WATCHDOG_PING_TYPE("WatchdogPingType", "wt"),
	WATCHDOG_HOST_NAME("WatchdogHostName", "wip"),
	WATCHDOG_PING_INTERVAL("WatchdogPingInterval", "wint"),
	WATCHDOG_PING_RETRIES("WatchdogPingRetries", "wret"),
	WATCHDOG_TCP_PORT("WatchDogTCPPort", "wport"),
	WATCHDOG_MODE("WatchDogMode", "wtype"),
	WATCHDOG_MODE_RESET_PORT_WHEN_HOST_DOWN("ResetPortWhenHostDown", "wtype"),
	WATCH_DOG_DELAY_BOOTING_TIME("WatchDogDelayBootingTime(s)", "wtype"),
	COUNT_PING_REQUEST("CountPINGRequestsAsUnRepliedWhenEthernetLinkDown", "wtype"),
	APPLY_CHANGES("ApplyChanges", ""),
	CANCEL_CHANGES("CancelChanges", ""),

	ERROR("None", "None");
	private final String uiName;
	private final String command;

	/**
	 * Parameterized constructor
	 *
	 * @param name metric name
	 * @param command metric command
	 */
	PowerPorConfigMetric(String name, String command) {
		this.uiName = name;
		this.command = command;
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
	 * Retrieves {@link #command}
	 *
	 * @return value of {@link #command}
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * This method is used to get power port config metric by UI name
	 *
	 * @param uiName is ui name of power port config
	 * @return PowerPorConfigMetric power port config metric
	 */
	public static PowerPorConfigMetric getByUIName(String uiName) {
		return Arrays.stream(PowerPorConfigMetric.values()).filter(status -> status.getUiName().equals(uiName)).findFirst().orElse(PowerPorConfigMetric.ERROR);
	}

}

