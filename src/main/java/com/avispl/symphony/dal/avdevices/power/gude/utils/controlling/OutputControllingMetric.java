/*
 *  Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.power.gude.utils.controlling;

/**
 * Set of output controlling metric
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

	public static final String POWER_PORT_UN_INDEXED = "PowerPort";
	public static final String POWER_PORT = "00PowerPort";
	public static final String POWER_PORT_BATCH_INIT_SWITCH = "01PowerPortBatchInitSwitch";
	public static final String POWER_PORT_BATCH_WAITING_TIME = "02PowerPortBatchWaitingTime";
	public static final String POWER_PORT_BATCH_WAITING_TIME_UNIT = "03PowerPortBatchWaitingTimeUnit";
	public static final String POWER_PORT_BATCH_END_SWITCH = "04PowerPortBatchEndSwitch";
	public static final String POWER_PORT_BATCH_WAITING_TIME_REMAINING = "01PowerPortBatchWaitingTimeRemaining";
	public static final String POWER_PORT_STATUS = "PowerPortStatus";
	public static final String APPLY_CHANGES = "ApplyChanges";
	public static final String CANCEL_CHANGES = "CancelChanges";
	public static final String EDITED = "Edited";
}

