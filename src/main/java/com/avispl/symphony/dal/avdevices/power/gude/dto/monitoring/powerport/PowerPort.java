package com.avispl.symphony.dal.avdevices.power.gude.dto.monitoring.powerport;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * PowerPort
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 21/12/2022
 * @since 1.0.0
 */

@JsonIgnoreProperties (ignoreUnknown = true)
public class PowerPort {
	@JsonAlias
	private String name;

	@JsonAlias
	private String state;

	@JsonAlias
	private String type;

	@JsonAlias
	private List<Integer> batch = new ArrayList<>();

	@JsonAlias("wdog")
	private List<Integer> watchDog =new ArrayList<>();

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
	 * Retrieves {@link #type}
	 *
	 * @return value of {@link #type}
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets {@link #type} value
	 *
	 * @param type new value of {@link #type}
	 */
	public void setType(String type) {
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
	 * Retrieves {@link #watchDog}
	 *
	 * @return value of {@link #watchDog}
	 */
	public List<Integer> getWatchDog() {
		return watchDog;
	}

	/**
	 * Sets {@link #watchDog} value
	 *
	 * @param watchDog new value of {@link #watchDog}
	 */
	public void setWatchDog(List<Integer> watchDog) {
		this.watchDog = watchDog;
	}
}