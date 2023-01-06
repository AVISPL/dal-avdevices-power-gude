package com.avispl.symphony.dal.avdevices.power.gude.dto.monitoring.sensor;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * SensorValues
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 21/12/2022
 * @since 1.0.0
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SensorValue {
	@JsonAlias
	private int type;

	@JsonAlias
	private List<List<JsonNode>> values = new ArrayList<>();

	private List<List<SensorFieldValue>> sensorSupportedValues = new ArrayList<>();
	protected final Log logger = LogFactory.getLog(this.getClass());
	private ObjectMapper objectMapper = new ObjectMapper();


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
	 * Retrieves {@link #values}
	 *
	 * @return value of {@link #values}
	 */
	public List<List<JsonNode>> getValues() {
		return values;
	}

	/**
	 * Sets {@link #values} value
	 *
	 * @param values new value of {@link #values}
	 */
	public void setValues(List<List<JsonNode>> values) {
		this.values = values;
	}

	/**
	 * Retrieves {@link #sensorSupportedValues}
	 *
	 * @return value of {@link #sensorSupportedValues}
	 */
	public List<List<SensorFieldValue>> getSensorSupportedValues() {
		return sensorSupportedValues;
	}

	/**
	 * Sets {@link #sensorSupportedValues} value
	 *
	 * @param sensorSupportedValues new value of {@link #sensorSupportedValues}
	 */
	public void setSensorSupportedValues(List<List<SensorFieldValue>> sensorSupportedValues) {
		this.sensorSupportedValues = sensorSupportedValues;
	}

	/**
	 * map the dynamic Json object data of sensor values to DTO
	 */
	public void mapDynamicJsonObjectDataToSensorValuesDTO() {
		for (List<JsonNode> jsonNodes : values) {
			List<SensorFieldValue> sensorValues = new ArrayList<>();
			for (JsonNode node : jsonNodes) {
				try {
					SensorFieldValue sensorPropertyValue = objectMapper.convertValue(node, SensorFieldValue.class);
					sensorValues.add(sensorPropertyValue);
				} catch (Exception e) {
					logger.error(String.format("Error while deserializing data: %s", e.getMessage()), e);
				}
			}
			sensorSupportedValues.add(sensorValues);
		}
	}
}