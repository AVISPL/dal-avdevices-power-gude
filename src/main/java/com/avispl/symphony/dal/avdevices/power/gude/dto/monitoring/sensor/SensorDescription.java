package com.avispl.symphony.dal.avdevices.power.gude.dto.monitoring.sensor;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * SensorDescription
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 21/12/2022
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SensorDescription {
	@JsonAlias
	private int type;

	@JsonAlias("fields")
	private List<SensorField> fields = new ArrayList<>();

	@JsonAlias("properties")
	private List<SensorProperty> properties = new ArrayList<>();

	/**
	 * Retrieves {@link #type}
	 *
	 * @return value of {@link #type}
	 */
	public int getType() {
		return type;
	}

	/**
	 * Sets {@link #type} value
	 *
	 * @param type new value of {@link #type}
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * Retrieves {@link #fields}
	 *
	 * @return value of {@link #fields}
	 */
	public List<SensorField> getFields() {
		return fields;
	}

	/**
	 * Sets {@link #fields} value
	 *
	 * @param fields new value of {@link #fields}
	 */
	public void setFields(List<SensorField> fields) {
		this.fields = fields;
	}

	/**
	 * Retrieves {@link #properties}
	 *
	 * @return value of {@link #properties}
	 */
	public List<SensorProperty> getProperties() {
		return properties;
	}

	/**
	 * Sets {@link #properties} value
	 *
	 * @param properties new value of {@link #properties}
	 */
	public void setProperties(List<SensorProperty> properties) {
		this.properties = properties;
	}

}
