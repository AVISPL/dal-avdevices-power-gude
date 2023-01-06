package com.avispl.symphony.dal.avdevices.power.gude.dto.monitoring.sensor;
/*
 *  Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Sensor Fields
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 21/12/2022
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SensorField {

	@JsonAlias
	private String name;

	@JsonAlias
	private String unit;

	@JsonAlias
	private String decPrecision;

	@JsonAlias
	private String defaultDisplay;

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
	 * Retrieves {@link #unit}
	 *
	 * @return value of {@link #unit}
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * Sets {@link #unit} value
	 *
	 * @param unit new value of {@link #unit}
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * Retrieves {@link #decPrecision}
	 *
	 * @return value of {@link #decPrecision}
	 */
	public String getDecPrecision() {
		return decPrecision;
	}

	/**
	 * Sets {@link #decPrecision} value
	 *
	 * @param decPrecision new value of {@link #decPrecision}
	 */
	public void setDecPrecision(String decPrecision) {
		this.decPrecision = decPrecision;
	}

	/**
	 * Retrieves {@link #defaultDisplay}
	 *
	 * @return value of {@link #defaultDisplay}
	 */
	public String getDefaultDisplay() {
		return defaultDisplay;
	}

	/**
	 * Sets {@link #defaultDisplay} value
	 *
	 * @param defaultDisplay new value of {@link #defaultDisplay}
	 */
	public void setDefaultDisplay(String defaultDisplay) {
		this.defaultDisplay = defaultDisplay;
	}

}