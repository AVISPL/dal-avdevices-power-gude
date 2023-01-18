package com.avispl.symphony.dal.avdevices.power.gude.dto.monitoring.powerport;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Contain list of power port configuration data (ID from 1 to 12)
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 14/01/2023
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PowerPortConfig {

	private int powerPortIndex;

	@JsonAlias("ports")
	private List<PowerPortComponentConfig> powerPortComponentConfigs = new ArrayList<>();

	/**
	 * Retrieves {@link #powerPortComponentConfigs}
	 *
	 * @return value of {@link #powerPortComponentConfigs}
	 */
	public List<PowerPortComponentConfig> getPowerPortComponentConfigs() {
		return powerPortComponentConfigs;
	}

	/**
	 * Sets {@link #powerPortComponentConfigs} value
	 *
	 * @param powerPortComponentConfigs new value of {@link #powerPortComponentConfigs}
	 */
	public void setPowerPortComponentConfigs(List<PowerPortComponentConfig> powerPortComponentConfigs) {
		this.powerPortComponentConfigs = powerPortComponentConfigs;
	}

	/**
	 * Retrieves {@link #powerPortIndex}
	 *
	 * @return value of {@link #powerPortIndex}
	 */
	public int getPowerPortIndex() {
		return powerPortIndex;
	}

	/**
	 * Sets {@link #powerPortIndex} value
	 *
	 * @param powerPortIndex new value of {@link #powerPortIndex}
	 */
	public void setPowerPortIndex(int powerPortIndex) {
		this.powerPortIndex = powerPortIndex;
	}
}