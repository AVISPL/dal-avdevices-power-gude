/*
 *  Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.power.gude.utils.controlling;

/**
 * Set of constants
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 12/25/2022
 * @since 1.0.0
 */
public class OutputControllingMetric {

	/**
	 * private/ non-parameterized constructor to prevent instance initialization
	 */
	private OutputControllingMetric() {
	}

	public static final String POWER_PORT = "PowerPort";
	public static final String POWER_PORT_BATCH_INIT_SWITCH = "PowerPortBatchInitSwitch";
	public static final String POWER_PORT_BATCH_END_SWITCH = "PowerPortBatchEndSwitch";
	public static final String POWER_PORT_BATCH_WAITING_TIME = "PowerPortBatchWaitingTime";
	public static final String POWER_PORT_BATCH_WAITING_TIME_UNIT = "PowerPortBatchWaitingTimeUnit";
	public static final String POWER_PORT_BATCH_WAITING_TIME_REMAINING = "PowerPortBatchWaitingTimeRemaining";
	public static final String POWER_PORT_STATUS = "PowerPortStatus";
	public static final String APPLY_CHANGES = "ApplyChanges";
	public static final String CANCEL_CHANGES = "CancelChanges";
	public static final String EDITED = "Edited";
}

