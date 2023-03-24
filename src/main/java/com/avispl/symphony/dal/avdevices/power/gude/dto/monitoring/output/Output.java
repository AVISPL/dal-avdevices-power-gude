/*
 *  Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.power.gude.dto.monitoring.output;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.avispl.symphony.dal.avdevices.power.gude.utils.controlling.OnOffStatus;
import com.avispl.symphony.dal.avdevices.power.gude.utils.controlling.OutputMode;
import com.avispl.symphony.dal.avdevices.power.gude.utils.controlling.WaitingTimeUnit;

/**
 * Set of Output status data
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 01/01/2023
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Output {

	private String groupName;
	private OutputMode outputMode;
	private WaitingTimeUnit waitingTimeUnit;
	private String waitingTime;
	private String portNumber;
	@JsonAlias
	private String name;

	@JsonAlias
	private Integer state;

	@JsonAlias
	private Integer type;

	@JsonAlias
	private List<Integer> batch = new ArrayList<>();

	/**
	 * Non-Parameterized constructor
	 */
	public Output() {
	}

	/**
	 * Parameterized constructor for prevent the shallow clone error
	 */
	public Output(Output output) {
		this.groupName = output.getGroupName();
		this.outputMode = output.getOutputMode();
		this.waitingTimeUnit = output.getWaitingTimeUnit();
		this.waitingTime = output.getWaitingTime();
		this.name = output.getName();
		this.state = output.getState();
		this.type = output.getType();
		this.batch = new ArrayList<>(output.getBatch());
	}

	/**
	 * Retrieves {@link #name}
	 *
	 * @return value of {@link #name}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets {@link #name} value
	 *
	 * @param name new value of {@link #name}
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Retrieves {@link #state}
	 *
	 * @return value of {@link #state}
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * Sets {@link #state} value
	 *
	 * @param state new value of {@link #state}
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * Retrieves {@link #type}
	 *
	 * @return value of {@link #type}
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * Sets {@link #type} value
	 *
	 * @param type new value of {@link #type}
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * Retrieves {@link #batch}
	 *
	 * @return value of {@link #batch}
	 */
	public List<Integer> getBatch() {
		return batch;
	}

	/**
	 * Sets {@link #batch} value
	 *
	 * @param batch new value of {@link #batch}
	 */
	public void setBatch(List<Integer> batch) {
		this.batch = batch;
	}

	/**
	 * Retrieves {@link #groupName}
	 *
	 * @return value of {@link #groupName}
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * Sets {@link #groupName} value
	 *
	 * @param groupName new value of {@link #groupName}
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * Retrieves {@link #outputMode}
	 *
	 * @return value of {@link #outputMode}
	 */
	public OutputMode getOutputMode() {
		return outputMode;
	}

	/**
	 * Sets {@link #outputMode} value
	 *
	 * @param outputMode new value of {@link #outputMode}
	 */
	public void setOutputMode(OutputMode outputMode) {
		this.outputMode = outputMode;
	}

	/**
	 * Retrieves {@link #waitingTimeUnit}
	 *
	 * @return value of {@link #waitingTimeUnit}
	 */
	public WaitingTimeUnit getWaitingTimeUnit() {
		return waitingTimeUnit;
	}

	/**
	 * Sets {@link #waitingTimeUnit} value
	 *
	 * @param waitingTimeUnit new value of {@link #waitingTimeUnit}
	 */
	public void setWaitingTimeUnit(WaitingTimeUnit waitingTimeUnit) {
		this.waitingTimeUnit = waitingTimeUnit;
	}

	/**
	 * Retrieves {@link #waitingTime}
	 *
	 * @return value of {@link #waitingTime}
	 */
	public String getWaitingTime() {
		return waitingTime;
	}

	/**
	 * Sets {@link #waitingTime} value
	 *
	 * @param waitingTime new value of {@link #waitingTime}
	 */
	public void setWaitingTime(String waitingTime) {
		this.waitingTime = waitingTime;
	}

	/**
	 * Retrieves {@link #portNumber}
	 *
	 * @return value of {@link #portNumber}
	 */
	public String getPortNumber() {
		return portNumber;
	}

	/**
	 * Sets {@link #portNumber} value
	 *
	 * @param portNumber new value of {@link #portNumber}
	 */
	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}

	/**
	 * Contribute output control request
	 * @return String request
	 */
	public String contributeOutputControlRequest() {
		OutputMode cachedOutputMode = this.getOutputMode();
		String cmd = cachedOutputMode.getApiName();
		String port = this.getPortNumber();
		switch (cachedOutputMode){
			case ON:
				String portSwitch = OnOffStatus.ON.getApiName();
				return String.format("/statusjsn.js?components=769&cmd=%s&p=%s&s=%s", cmd, port, portSwitch);
			case OFF:
				portSwitch = OnOffStatus.OFF.getApiName();
				return String.format("/statusjsn.js?components=769&cmd=%s&p=%s&s=%s", cmd, port, portSwitch);
			case RESET:
				return String.format("/statusjsn.js?components=769&cmd=%s&p=%s", cmd, port);
			case BATCH:
				String initSwitch = String.valueOf(Optional.ofNullable(this.getBatch().get(2)).orElse(0));
				String endSwitch = String.valueOf(Optional.ofNullable(this.getBatch().get(4)).orElse(0));
				String waitingTimeValue = String.valueOf(this.getWaitingTimeUnit().getApiName() * Integer.parseInt(this.getWaitingTime()));
				return String.format("/statusjsn.js?components=769&cmd=%s&p=%s&a1=%s&a2=%s&s=%s", cmd, port, initSwitch, endSwitch, waitingTimeValue);
			default:
				throw new IllegalStateException(String.format("Unsupported power port control: %s", cachedOutputMode.getUiName()));
		}
	}
}