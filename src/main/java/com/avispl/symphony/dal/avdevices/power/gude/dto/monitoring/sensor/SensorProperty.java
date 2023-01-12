/*
 *  Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.power.gude.dto.monitoring.sensor;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Sensor Property
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 21/12/2022
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SensorProperty {
	@JsonAlias
	private String id;

	@JsonAlias
	private String name;

	@JsonAlias
	private String state;

	@JsonAlias
	private List<String> statistics = new ArrayList<>();

	/**
	 * Retrieves {@link #id}
	 *
	 * @return value of {@link #id}
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets {@link #id} value
	 *
	 * @param id new value of {@link #id}
	 */
	public void setId(String id) {
		this.id = id;
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
	public String getState() {
		return state;
	}

	/**
	 * Sets {@link #state} value
	 *
	 * @param state new value of {@link #state}
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Retrieves {@link #statistics}
	 *
	 * @return value of {@link #statistics}
	 */
	public List<String> getStatistics() {
		return statistics;
	}

	/**
	 * Sets {@link #statistics} value
	 *
	 * @param statistics new value of {@link #statistics}
	 */
	public void setStatistics(List<String> statistics) {
		this.statistics = statistics;
	}

}