package com.avispl.symphony.dal.avdevices.power.gude.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import com.avispl.symphony.dal.avdevices.power.gude.dto.monitoring.powerport.PowerPortConfig;

/**
 * PowerPortConfigWraper
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 14/01/2023
 * @since 1.0.0
 */
public class DeviceConfigData {

	@JsonAlias("port_cfg")
	private PowerPortConfig powerPortConfig;

	/**
	 * Retrieves {@link #powerPortConfig}
	 *
	 * @return value of {@link #powerPortConfig}
	 */
	public PowerPortConfig getPowerPortConfig() {
		return powerPortConfig;
	}

	/**
	 * Sets {@link #powerPortConfig} value
	 *
	 * @param powerPortConfig new value of {@link #powerPortConfig}
	 */
	public void setPowerPortConfig(PowerPortConfig powerPortConfig) {
		this.powerPortConfig = powerPortConfig;
	}
}