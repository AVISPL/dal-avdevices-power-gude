/*
 *  Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.power.gude.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.avispl.symphony.dal.avdevices.power.gude.dto.monitoring.misc.Misc;
import com.avispl.symphony.dal.avdevices.power.gude.dto.monitoring.output.Output;
import com.avispl.symphony.dal.avdevices.power.gude.dto.monitoring.powerport.PowerPort;
import com.avispl.symphony.dal.avdevices.power.gude.dto.monitoring.sensor.SensorDescription;
import com.avispl.symphony.dal.avdevices.power.gude.dto.monitoring.sensor.SensorValue;

/**
 * Device's Monitoring data
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 21/12/2022
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceMonitoringData {

	@JsonAlias("outputs")
	private List<PowerPort> powerPort;

	@JsonAlias("sensor_descr")
	private List<SensorDescription> sensorDescriptions = new ArrayList<>();

	@JsonAlias("sensor_values")
	private List<SensorValue> sensorValues = new ArrayList<>();

	@JsonAlias
	private Misc misc;

	@JsonAlias
	private List<Output> outputs = new ArrayList<>();

	/**
	 * Retrieves {@link #powerPort}
	 *
	 * @return value of {@link #powerPort}
	 */
	public List<PowerPort> getPowerPort() {
		return powerPort;
	}

	/**
	 * Sets {@link #powerPort} value
	 *
	 * @param powerPort new value of {@link #powerPort}
	 */
	public void setPowerPort(List<PowerPort> powerPort) {
		this.powerPort = powerPort;
	}

	/**
	 * Retrieves {@link #sensorDescriptions}
	 *
	 * @return value of {@link #sensorDescriptions}
	 */
	public List<SensorDescription> getSensorDescriptions() {
		return sensorDescriptions;
	}

	/**
	 * Sets {@link #sensorDescriptions} value
	 *
	 * @param sensorDescriptions new value of {@link #sensorDescriptions}
	 */
	public void setSensorDescriptions(List<SensorDescription> sensorDescriptions) {
		this.sensorDescriptions = sensorDescriptions;
	}

	/**
	 * Retrieves {@link #sensorValues}
	 *
	 * @return value of {@link #sensorValues}
	 */
	public List<SensorValue> getSensorValues() {
		return sensorValues;
	}

	/**
	 * Sets {@link #sensorValues} value
	 *
	 * @param sensorValues new value of {@link #sensorValues}
	 */
	public void setSensorValues(List<SensorValue> sensorValues) {
		this.sensorValues = sensorValues;
	}

	/**
	 * Retrieves {@link #misc}
	 *
	 * @return value of {@link #misc}
	 */
	public Misc getMisc() {
		return misc;
	}

	/**
	 * Sets {@link #misc} value
	 *
	 * @param misc new value of {@link #misc}
	 */
	public void setMisc(Misc misc) {
		this.misc = misc;
	}

	/**
	 * Retrieves {@link #outputs}
	 *
	 * @return value of {@link #outputs}
	 */
	public List<Output> getOutputs() {
		return outputs;
	}

	/**
	 * Sets {@link #outputs} value
	 *
	 * @param outputs new value of {@link #outputs}
	 */
	public void setOutputs(List<Output> outputs) {
		this.outputs = outputs;
	}
}