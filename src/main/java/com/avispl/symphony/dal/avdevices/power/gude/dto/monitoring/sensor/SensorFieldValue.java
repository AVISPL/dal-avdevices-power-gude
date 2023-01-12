/*
 *  Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.power.gude.dto.monitoring.sensor;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Sensor PropertyValue
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 21/12/2022
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SensorFieldValue {

	@JsonAlias("v")
	private double propertyValue;

	@JsonAlias("st")
	private List<Double> advanceData;

	private SensorField sensorField;

	private SensorProperty sensorProperty;

	/**
	 * Retrieves {@link #propertyValue}
	 *
	 * @return value of {@link #propertyValue}
	 */
	public double getPropertyValue() {
		return propertyValue;
	}

	/**
	 * Sets {@link #propertyValue} value
	 *
	 * @param propertyValue new value of {@link #propertyValue}
	 */
	public void setPropertyValue(double propertyValue) {
		this.propertyValue = propertyValue;
	}

	/**
	 * Retrieves {@link #advanceData}
	 *
	 * @return value of {@link #advanceData}
	 */
	public List<Double> getAdvanceData() {
		return advanceData;
	}

	/**
	 * Sets {@link #advanceData} value
	 *
	 * @param advanceData new value of {@link #advanceData}
	 */
	public void setAdvanceData(List<Double> advanceData) {
		this.advanceData = advanceData;
	}

	/**
	 * Retrieves {@link #sensorField}
	 *
	 * @return value of {@link #sensorField}
	 */
	public SensorField getSensorField() {
		return sensorField;
	}

	/**
	 * Sets {@link #sensorField} value
	 *
	 * @param sensorField new value of {@link #sensorField}
	 */
	public void setSensorField(SensorField sensorField) {
		this.sensorField = sensorField;
	}

	/**
	 * Retrieves {@link #sensorProperty}
	 *
	 * @return value of {@link #sensorProperty}
	 */
	public SensorProperty getSensorProperty() {
		return sensorProperty;
	}

	/**
	 * Sets {@link #sensorProperty} value
	 *
	 * @param sensorProperty new value of {@link #sensorProperty}
	 */
	public void setSensorProperty(SensorProperty sensorProperty) {
		this.sensorProperty = sensorProperty;
	}
}