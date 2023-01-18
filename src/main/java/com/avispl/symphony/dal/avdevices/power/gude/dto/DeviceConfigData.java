/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.power.gude.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.avispl.symphony.dal.avdevices.power.gude.dto.monitoring.powerport.PowerPortConfig;

/**
 * Device's configuration data
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 14/01/2023
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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