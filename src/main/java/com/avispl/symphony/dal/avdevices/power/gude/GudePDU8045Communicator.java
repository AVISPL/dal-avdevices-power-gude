/*
 *  Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.power.gude;

import static com.avispl.symphony.dal.util.ControllablePropertyFactory.createDropdown;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import javax.security.auth.login.FailedLoginException;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;

import com.avispl.symphony.api.dal.control.Controller;
import com.avispl.symphony.api.dal.dto.control.AdvancedControllableProperty;
import com.avispl.symphony.api.dal.dto.control.ControllableProperty;
import com.avispl.symphony.api.dal.dto.monitor.ExtendedStatistics;
import com.avispl.symphony.api.dal.dto.monitor.Statistics;
import com.avispl.symphony.api.dal.error.ResourceNotReachableException;
import com.avispl.symphony.api.dal.monitor.Monitorable;
import com.avispl.symphony.dal.avdevices.power.gude.dto.DeviceMonitoringData;
import com.avispl.symphony.dal.avdevices.power.gude.dto.monitoring.misc.Misc;
import com.avispl.symphony.dal.avdevices.power.gude.dto.monitoring.output.Output;
import com.avispl.symphony.dal.avdevices.power.gude.dto.monitoring.sensor.SensorDescription;
import com.avispl.symphony.dal.avdevices.power.gude.dto.monitoring.sensor.SensorField;
import com.avispl.symphony.dal.avdevices.power.gude.dto.monitoring.sensor.SensorFieldValue;
import com.avispl.symphony.dal.avdevices.power.gude.dto.monitoring.sensor.SensorProperty;
import com.avispl.symphony.dal.avdevices.power.gude.dto.monitoring.sensor.SensorValue;
import com.avispl.symphony.dal.avdevices.power.gude.utils.AuthorizationChallengeHandler;
import com.avispl.symphony.dal.avdevices.power.gude.utils.DeviceConstant;
import com.avispl.symphony.dal.avdevices.power.gude.utils.DeviceInfoMetric;
import com.avispl.symphony.dal.avdevices.power.gude.utils.DeviceURL;
import com.avispl.symphony.dal.avdevices.power.gude.utils.EnumTypeHandler;
import com.avispl.symphony.dal.avdevices.power.gude.utils.SupportedSensorField;
import com.avispl.symphony.dal.avdevices.power.gude.utils.SupportedSensorType;
import com.avispl.symphony.dal.avdevices.power.gude.utils.controlling.OutputControllingMetric;
import com.avispl.symphony.dal.avdevices.power.gude.utils.controlling.OutputMode;
import com.avispl.symphony.dal.avdevices.power.gude.utils.controlling.OutputStatus;
import com.avispl.symphony.dal.avdevices.power.gude.utils.controlling.WaitingTimeUnit;
import com.avispl.symphony.dal.avdevices.power.gude.utils.monitoring.DevicesMetricGroup;
import com.avispl.symphony.dal.avdevices.power.gude.utils.monitoring.HistoricalProperties;
import com.avispl.symphony.dal.avdevices.power.gude.utils.monitoring.SwitchOnOffStatus;
import com.avispl.symphony.dal.communicator.RestCommunicator;
import com.avispl.symphony.dal.util.StringUtils;

/**
 * GudePDU8045Communicator
 * An implementation of RestCommunicator to provide communication and interaction with Gude PDU 8045
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 14/12/2022
 * @since 1.0.0
 */
public class GudePDU8045Communicator extends RestCommunicator implements Monitorable, Controller {

	private boolean isEmergencyDelivery;
	private ExtendedStatistics localExtendedStatistics = new ExtendedStatistics();
	private DeviceMonitoringData cachedMonitoringStatus;

	private Map<String, Boolean> isAdvanceMonitoringGroups = new HashMap<>();
	private List<Boolean> isOutputsControlEdited = new ArrayList<>();

	private List<String> cachedBatch = new ArrayList<>();

	private Map<String, String> cachedFieldsName = new HashMap<>();

	/**
	 * Stored historical properties from adapter properties
	 */
	private String historicalProperties;

	/**
	 * Stored list control value of waitingTime controllable property
	 */
	private List<String> waitingTimeValues = new ArrayList<>();

	/**
	 * Stored supported sensor type code
	 */
	private Set<Integer> supportedSensorTypes;

	/**
	 * Stored supported sensor fields
	 */
	private Map<String, String> supportedSensorFields;

	/**
	 * Stored authorization value of basic/ digest authentication
	 */
	private String authorizationHeader;

	/**
	 * Cached outputs control data
	 */
	private List<Output> cachedOutputsControl = new ArrayList<>();

	/**
	 * ReentrantLock to prevent null pointer exception to localExtendedStatistics when controlProperty method is called before GetMultipleStatistics method.
	 */
	private final ReentrantLock reentrantLock = new ReentrantLock();

	/**
	 * store configManagement adapter properties
	 */
	private String configManagement;

	/**
	 * configManagement in boolean value
	 */
	private boolean isConfigManagement;

	/**
	 * Retrieves {@link #configManagement}
	 *
	 * @return value of {@link #configManagement}
	 */
	public String getConfigManagement() {
		return configManagement;
	}

	/**
	 * Sets {@link #configManagement} value
	 *
	 * @param configManagement new value of {@link #configManagement}
	 */
	public void setConfigManagement(String configManagement) {
		this.configManagement = configManagement;
	}

	/**
	 * Retrieves {@link #historicalProperties}
	 *
	 * @return value of {@link #historicalProperties}
	 */
	public String getHistoricalProperties() {
		return historicalProperties;
	}

	/**
	 * Sets {@link #historicalProperties} value
	 *
	 * @param historicalProperties new value of {@link #historicalProperties}
	 */
	public void setHistoricalProperties(String historicalProperties) {
		this.historicalProperties = historicalProperties;
	}

	@Override
	public List<Statistics> getMultipleStatistics() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(
					String.format("Getting statistics from the device Gude PDU 8045 at host %s with port %s", this.host,
							this.getPort()));
		}
		reentrantLock.lock();
		try {
			final ExtendedStatistics extendedStatistics = new ExtendedStatistics();
			final Map<String, String> stats = new HashMap<>();
			final Map<String, String> dynamicStats = new HashMap<>();
			final List<AdvancedControllableProperty> advancedControllableProperties = new ArrayList<>();
			if (!isEmergencyDelivery) {
				// login for the first time
				if (StringUtils.isNullOrEmpty(authorizationHeader)) {
					login();
				}
				retrieveDeviceMonitoringData(stats, advancedControllableProperties);
				populateDynamicStats(stats, dynamicStats);
				extendedStatistics.setStatistics(stats);
				extendedStatistics.setDynamicStatistics(dynamicStats);
				extendedStatistics.setControllableProperties(advancedControllableProperties);
				localExtendedStatistics = extendedStatistics;
			}
			isEmergencyDelivery = false;
		} finally {
			reentrantLock.unlock();
		}
		return Collections.singletonList(localExtendedStatistics);
	}

	@Override
	public void controlProperty(ControllableProperty controllableProperty) throws Exception {
		reentrantLock.lock();
		try {
			if (this.localExtendedStatistics == null) {
				return;
			}
			Map<String, String> stats = this.localExtendedStatistics.getStatistics();
			List<AdvancedControllableProperty> advancedControllableProperties = this.localExtendedStatistics.getControllableProperties();

			String property = controllableProperty.getProperty();
			String value = String.valueOf(controllableProperty.getValue());
			if (this.logger.isDebugEnabled()) {
				this.logger.debug("controlProperty property " + property);
				this.logger.debug("controlProperty value " + value);
			}
			if (property.contains(String.valueOf(DeviceConstant.HASH))) {
				String[] splitProperty = property.split(String.valueOf(DeviceConstant.HASH));
				String controlGroup = splitProperty[0];
				DevicesMetricGroup group = DevicesMetricGroup.getByName(controlGroup);

				switch (group) {
					case SENSOR:
						sensorAdvanceMonitoringControl(stats, advancedControllableProperties, controlGroup + DeviceConstant.HASH, value);
						break;
					case OUTPUT:
						int outputIndex = Integer.parseInt(controlGroup.substring(DevicesMetricGroup.OUTPUT.getName().length())) - DeviceConstant.INDEX_TO_ORDINAL_CONVERT_FACTOR;
						powerPortControl(stats, advancedControllableProperties, splitProperty[1], value, outputIndex);
						break;
					default:
						throw new IllegalStateException(String.format("Control group %s is not supported", controlGroup));
				}
			} else {
				DeviceInfoMetric deviceInfoMetric = DeviceInfoMetric.getByName(property);
				Output output = new Output();
				output.setPortNumber(DeviceConstant.ALL_PORT_NUMBER);
				switch (deviceInfoMetric) {
					case ALL_POWER_PORT_CONTROL_OFF:
						output.setOutputMode(OutputMode.OFF);
						performPowerPortControl(output);

						// device need 10s to switch all power port off
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							logger.error(String.format("error while control %s: %s", controllableProperty, e.getMessage()));
						}
						break;
					case ALL_POWER_PORT_CONTROL_ON:
						output.setOutputMode(OutputMode.ON);
						performPowerPortControl(output);

						// device need 10s to switch all power port on
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							logger.error(String.format("error while control %s: %s", controllableProperty, e.getMessage()));
						}
						break;
					default:
						throw new IllegalStateException(String.format("Controllable property %s is not supported", property));
				}
			}
		} finally {
			reentrantLock.unlock();
		}
	}

	@Override
	public void controlProperties(List<ControllableProperty> list) throws Exception {
		if (CollectionUtils.isEmpty(list)) {
			throw new IllegalArgumentException("GudePDU8045Communicator: Controllable properties cannot be null or empty");
		}
		for (ControllableProperty controllableProperty : list) {
			controlProperty(controllableProperty);
		}
	}

	@Override
	protected void authenticate() throws Exception {
		// The device has its own authentication behavior, do not use the common one
	}

	/**
	 * @return HttpHeaders contain digest/ basic authorization header
	 */
	@Override
	protected HttpHeaders putExtraRequestHeaders(HttpMethod httpMethod, String uri, HttpHeaders headers) throws Exception {
		if (StringUtils.isNotNullOrEmpty(authorizationHeader)) {
			headers.set(DeviceConstant.AUTHORIZATION, authorizationHeader);
			headers.set(HttpHeaders.HOST, getHost());
		}
		return super.putExtraRequestHeaders(httpMethod, uri, headers);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void internalInit() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Internal init is called.");
		}
		supportedSensorTypes = SupportedSensorType.getSupportedSensorTypesCode();
		supportedSensorFields = SupportedSensorField.getSupportedSensorFields();
		initValueForWaitingTimeValues();
		isValidConfigManagement();
		super.internalInit();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void internalDestroy() {
		if (localExtendedStatistics != null && localExtendedStatistics.getStatistics() != null) {
			localExtendedStatistics.getStatistics().clear();
		}
		if (localExtendedStatistics != null && localExtendedStatistics.getControllableProperties() != null) {
			localExtendedStatistics.getControllableProperties().clear();
		}
		super.internalDestroy();
	}


	/**
	 * If addressed too frequently, Sembient API may respond with 429 code, meaning that the call rate per second was reached.
	 * Normally it would rarely happen due to the request rate limit, but when it does happen - adapter must retry the
	 * attempts of retrieving needed information. This method retries up to 10 times with 500ms timeout in between to avoid the timeout exception
	 *
	 * @param url to retrieve data from
	 * @return An instance of input class
	 */
	private <T> T doGetWithRetryOnUnauthorized(String url, Class<T> clazz, boolean retryOnUnauthorized) throws Exception {
		try {
			return doGet(url, clazz);
		} catch (FailedLoginException e) {
			if (retryOnUnauthorized) {
				login();
				return doGetWithRetryOnUnauthorized(url, clazz, false);
			} else {
				throw new FailedLoginException("login failed, please check the username, password and server host");
			}
		}
	}

	/**
	 * This method is used to log in to the camera including the Basic and Digest authentication methods
	 */
	private void login() {
		try {
			HttpClient httpClient = this.obtainHttpClient(true);
			HttpGet httpGet = new HttpGet(buildDeviceFullPath(DeviceURL.FIRST_LOGIN));
			HttpResponse response = null;

			try {
				response = httpClient.execute(httpGet);
			} finally {
				if (response instanceof CloseableHttpResponse) {
					((CloseableHttpResponse) response).close();
				}
			}

			Header header = response.getFirstHeader(DeviceConstant.WWW_AUTHENTICATE);
			if (header != null) {
				String headerResponseString = response.getFirstHeader(DeviceConstant.WWW_AUTHENTICATE).toString();
				if (response.getStatusLine().getStatusCode() == HttpStatus.UNAUTHORIZED.value() && StringUtils.isNotNullOrEmpty(headerResponseString)) {
					AuthorizationChallengeHandler authorizationChallengeHandler = new AuthorizationChallengeHandler(getLogin(), getPassword());
					List<Map<String, String>> challenges = new ArrayList<>();
					Map<String, String> challenge = authorizationChallengeHandler.parseAuthenticationOrAuthorizationHeader(headerResponseString);
					challenges.add(challenge);

					if (headerResponseString.contains(DeviceConstant.BASIC)) {
						authorizationHeader = authorizationChallengeHandler.handleBasic();
					}
				}
			}
		} catch (HttpHostConnectException e) {
			throw new ResourceNotReachableException(String.format("Error while connecting to %s: %s", host, e.getMessage()), e);
		} catch (Exception e) {
			throw new ResourceNotReachableException(e.getMessage(), e);
		}
	}

	/**
	 * Build full path for http request
	 *
	 * @param path url of the request
	 * @return String full path of the device
	 */
	private String buildDeviceFullPath(String path) {
		Objects.requireNonNull(path);

		return DeviceConstant.HTTPS
				+ getHost()
				+ path;
	}

	/**
	 * This method is used to retrieve monitoring status by send get request to "http://<IP>/statusjsn.js?components=9029395"
	 * When the response is null or empty, the failedMonitor is going to update and exception is not populated
	 *
	 * @param stats store all statistics
	 * @param advancedControllableProperties store all controllable properties
	 * @throws FailedLoginException when login fails
	 */
	private void retrieveDeviceMonitoringData(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties) {
		String request = buildDeviceFullPath(DeviceURL.DEVICE_MONITORING);
		try {
			cachedMonitoringStatus = doGetWithRetryOnUnauthorized(request, DeviceMonitoringData.class, true);
			if (cachedMonitoringStatus != null) {
				populateDeviceMonitoring(stats, advancedControllableProperties);
			} else {
				throw new ResourceNotReachableException("Error while retrieving Device and Sensor data: response data is empty");
			}
		} catch (Exception e) {
			throw new ResourceNotReachableException(String.format("Error while retrieving Device and Sensor data: %s", e.getMessage()), e);
		}
	}

	/**
	 * populate Device monitoring data
	 *
	 * @param stats store all statistics
	 * @param advancedControllableProperties store all controllable properties
	 */
	private void populateDeviceMonitoring(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties) {
		List<SensorDescription> sensorDescriptions = cachedMonitoringStatus.getSensorDescriptions();
		Map<Integer, SensorValue> sensorValues = cachedMonitoringStatus.getSensorValues().stream().collect(Collectors.toMap(SensorValue::getType, Function.identity()));
		if (sensorDescriptions != null && sensorValues != null) {
			for (SensorDescription sensorDescription : sensorDescriptions) {
				if (supportedSensorTypes.contains(sensorDescription.getType())) {
					// the device return the dynamic Json object => the adapter have to loop through sensor list to map the supported sensor values to DTOs
					SensorValue sensorValue = sensorValues.get(sensorDescription.getType());
					sensorValue.mapDynamicJsonObjectDataToSensorValuesDTO();
					populateSensorData(stats, advancedControllableProperties, sensorValue.getSensorSupportedValues(), sensorDescription);
				}
			}
			populateDeviceInfo(stats, advancedControllableProperties);

			for (int outputIndex = 0; outputIndex < cachedMonitoringStatus.getOutputs().size(); outputIndex++) {
				populateOutputsControl(stats, advancedControllableProperties, outputIndex);
			}
		}
	}

	/**
	 * populate sensor data (include advance data) when advance monitoring is triggered
	 *
	 * @param stats store all statistics
	 * @param advancedControllableProperties store all controllable properties
	 * @param sensorPropertiesValue list of sensor properties value
	 */
	public void populateSensorData(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, List<List<SensorFieldValue>> sensorPropertiesValue,
			SensorDescription sensorDescription) {
		List<SensorProperty> properties = sensorDescription.getProperties();
		List<SensorField> fields = sensorDescription.getFields();
		if (properties != null && fields != null) {
			int type = sensorDescription.getType();
			for (int indexOfSensorProperty = 0; indexOfSensorProperty < properties.size(); indexOfSensorProperty++) {
				List<SensorFieldValue> sensorFieldValues = sensorPropertiesValue.get(indexOfSensorProperty);
				SensorProperty sensorProperty = properties.get(indexOfSensorProperty);
				List<String> sensorPropertyStats = sensorProperty.getStatistics();
				String groupName;
				String fieldName;
				String unit;
				String value;

				SupportedSensorType sensorType = SupportedSensorType.getSupportedSensorTypeByCode(type);
				switch (sensorType) {
					case POWER_PORT:
						groupName = DevicesMetricGroup.OUTPUT.getName() + String.format(DeviceConstant.TWO_NUMBER_FORMAT, Integer.parseInt(sensorProperty.getId())) + DeviceConstant.HASH;
						cachedMonitoringStatus.getOutputs().get(indexOfSensorProperty).setGroupName(groupName);
						break;
					case SENSOR_7106:
						groupName =
								DevicesMetricGroup.SENSOR.getName() + properties.get(indexOfSensorProperty).getId().replaceAll(DeviceConstant.SPACE_REGEX, DeviceConstant.EMPTY)
										+ DeviceConstant.HASH;
						break;
					case METER:
						groupName = DevicesMetricGroup.METER.getName() + properties.get(indexOfSensorProperty).getId() + DeviceConstant.HASH;
						break;
					default:
						groupName = properties.get(indexOfSensorProperty).getName() + DeviceConstant.HASH;
				}

				// populate advance monitoring switch control
				boolean isContainAdvanceMonitoringData = sensorPropertyStats.isEmpty();
				if (!isContainAdvanceMonitoringData) {
					if (!isAdvanceMonitoringGroups.containsKey(groupName)) {
						isAdvanceMonitoringGroups.put(groupName, false);
					}
					addAdvanceControlProperties(advancedControllableProperties, stats,
							createSwitch(groupName.concat(DeviceInfoMetric.ADVANCE_MONITORING.getName()), isAdvanceMonitoringGroups.get(groupName), DeviceConstant.DISABLE, DeviceConstant.ENABLE));
				}

				//populate Sensor ID and Sensor Name
				stats.put(groupName.concat(SupportedSensorField.ID.getUiName()), sensorProperty.getId());
				stats.put(groupName.concat(SupportedSensorField.NAME.getUiName()), sensorProperty.getName());

				// populate monitoring data
				for (int indexOfSensorFieldValue = 0; indexOfSensorFieldValue < sensorFieldValues.size(); indexOfSensorFieldValue++) {
					SensorField sensorField = fields.get(indexOfSensorFieldValue);
					SensorFieldValue sensorFieldValue = sensorFieldValues.get(indexOfSensorFieldValue);

					if (!supportedSensorFields.containsKey(sensorField.getName()) || sensorFieldValue == null) {
						continue;
					}
					fieldName = supportedSensorFields.get(getDefaultValueForNullData(sensorField.getName()));
					unit = getDefaultValueForNullData(sensorField.getUnit()).replaceAll(DeviceConstant.SPACE_REGEX, DeviceConstant.EMPTY);

					// format field value base on the decPrecision returned from the device
					String numberFormat = sensorField.getDecPrecision() != null ? "%.".concat(sensorField.getDecPrecision()).concat("f") : DeviceConstant.DEFAULT_DEC_PRECISION_FORMAT;
					value = String.format(numberFormat, Optional.ofNullable(sensorFieldValue.getPropertyValue()).orElse(DeviceConstant.DEFAULT_FOR_NULL_VALUE));

					// handle special case:
					// 1. convert duration time in second to hh:mm:ss format
					if (fieldName.equals(SupportedSensorField.RELATIVE_TIME.getUiName())) {
						unit = DeviceConstant.EMPTY;
						value = convertSecondToDuration(Long.parseLong(value));
					}
					// 2. convert residual current unit from A to mA
					if (fieldName.equals(SupportedSensorField.RESIDUAL_CURRENT.getUiName())) {
						unit = DeviceConstant.MILLI_AMPE_UNIT;
						value = String.valueOf(sensorFieldValue.getPropertyValue() * DeviceConstant.UNIT_TO_MILLI_CONVERT_FACTOR);
					}
					// 3. normalize unit
					switch (unit) {
						case DeviceConstant.DEG_C_UNIT:
							unit = DeviceConstant.CELSIUS_UNIT;
							break;
						default:
							logger.debug(String.format("un-required normalized unit: %s", unit));
							break;
					}

					String propertyName = String.format("%s%s", groupName, fieldName);
					if (StringUtils.isNotNullOrEmpty(unit) && !unit.equals(DeviceConstant.NONE)) {
						propertyName = String.format("%s%s(%s)", groupName, fieldName, unit);
					}
					stats.put(propertyName, value);

					if (sensorDescription.getType() == SupportedSensorType.SENSOR_7106.getCode() || sensorDescription.getType() == SupportedSensorType.METER.getCode()) {
						cachedFieldsName.put(String.format("%s(%s)", fieldName, unit), propertyName);
					}

					// populate advance monitoring data
					if (!isContainAdvanceMonitoringData && isAdvanceMonitoringGroups.get(groupName)) {
						List<Double> statsValues = sensorFieldValue.getAdvanceData();
						for (int indexOfSensorPropertyStats = 0; indexOfSensorPropertyStats < sensorPropertyStats.size(); indexOfSensorPropertyStats++) {
							String advancePropertyName = toPascalCase(sensorPropertyStats.get(indexOfSensorPropertyStats));
							if (advancePropertyName.contains(DeviceConstant.MAX)) {
								advancePropertyName = advancePropertyName.replaceAll(DeviceConstant.MAX, DeviceConstant.MAXIMUM);
							}
							if (advancePropertyName.contains(DeviceConstant.MIN)) {
								advancePropertyName = advancePropertyName.replaceAll(DeviceConstant.MIN, DeviceConstant.MINIMUM);
							}
							stats.put(String.format("%s%s%s(%s)", groupName, fieldName, advancePropertyName, unit),
									String.format(numberFormat, Optional.ofNullable(statsValues.get(indexOfSensorPropertyStats)).orElse(DeviceConstant.DEFAULT_FOR_NULL_VALUE)));
						}
					}
				}
			}
		}
	}

	/**
	 * populate device info
	 *
	 * @param stats store all statistics
	 * @param advancedControllableProperties store all controllable properties
	 */
	public void populateDeviceInfo(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties) {
		Misc misc = cachedMonitoringStatus.getMisc();
		if (misc != null) {
			stats.put(DeviceInfoMetric.DEVICE_NAME.getName(), misc.getProductName());
			stats.put(DeviceInfoMetric.FIRMWARE_VERSION.getName(), misc.getFirmware());

			if (isConfigManagement) {
				boolean isAllPortOn = true;
				boolean isAllPortOff = true;
				for (Output output : cachedMonitoringStatus.getOutputs()) {
					if (output.getState().toString().equals(OutputStatus.ON.getApiName())) {
						isAllPortOff = false;
						continue;
					}
					if (output.getState().toString().equals(OutputStatus.OFF.getApiName())) {
						isAllPortOn = false;
					}
				}
				if (!isAllPortOff && !isAllPortOn) {
					addAdvanceControlProperties(advancedControllableProperties, stats,
							createButton(DeviceInfoMetric.ALL_POWER_PORT_CONTROL_OFF.getName(), DeviceConstant.ALL_OFF, DeviceConstant.SWITCHING_OFF));
					addAdvanceControlProperties(advancedControllableProperties, stats,
							createButton(DeviceInfoMetric.ALL_POWER_PORT_CONTROL_ON.getName(), DeviceConstant.ALL_ON, DeviceConstant.SWITCHING_ON));
				} else if (isAllPortOn) {
					addAdvanceControlProperties(advancedControllableProperties, stats,
							createButton(DeviceInfoMetric.ALL_POWER_PORT_CONTROL_OFF.getName(), DeviceConstant.ALL_OFF, DeviceConstant.SWITCHING_OFF));
				} else {
					addAdvanceControlProperties(advancedControllableProperties, stats,
							createButton(DeviceInfoMetric.ALL_POWER_PORT_CONTROL_ON.getName(), DeviceConstant.ALL_ON, DeviceConstant.SWITCHING_ON));
				}
			}
		}
	}

	/**
	 * handle sensor advance monitoring control
	 *
	 * @param stats store all statistics
	 * @param advancedControllableProperties store all controllable properties
	 * @param groupName control groups name
	 * @param value value of controllable property
	 */
	private void sensorAdvanceMonitoringControl(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, String groupName,
			String value) {
		isAdvanceMonitoringGroups.put(groupName, value.equals(SwitchOnOffStatus.ON.getApiName()));
		List<SensorDescription> sensorDescriptions = cachedMonitoringStatus.getSensorDescriptions();
		Map<Integer, SensorValue> sensorValues = cachedMonitoringStatus.getSensorValues().stream().collect(Collectors.toMap(SensorValue::getType, Function.identity()));
		for (SensorDescription sensorDescription : sensorDescriptions) {
			if (sensorDescription.getType() == SupportedSensorType.SENSOR_7106.getCode()) {
				// the device return the dynamic Json object => the adapter have to loop through sensor list to map the supported sensor values to DTOs
				SensorValue sensorValue = sensorValues.get(sensorDescription.getType());
				if (sensorValue != null) {
					populateSensorData(stats, advancedControllableProperties, sensorValue.getSensorSupportedValues(), sensorDescription);
				}
			}
		}
	}

	/**
	 * This method is used to map stats to dynamic stats
	 *
	 * @param stats store all statistics
	 * @param dynamicStats store all dynamic statistics
	 */
	private void populateDynamicStats(Map<String, String> stats, Map<String, String> dynamicStats) {
		for (SupportedSensorType supportedSensorType : SupportedSensorType.values()) {
			if (supportedSensorType.isHistorical()) {
				String fieldName;
				Set<String> fields = new HashSet<>();
				if (StringUtils.isNotNullOrEmpty(getHistoricalProperties())) {
					fields = convertUserInput(getHistoricalProperties());
				}

				for (HistoricalProperties historicalProperty : HistoricalProperties.values()) {
					fieldName = historicalProperty.getUiName();
					if (!fields.isEmpty() && cachedFieldsName.containsKey(fieldName) && fields.contains(fieldName)) {
						String propertyName = cachedFieldsName.get(fieldName);
						dynamicStats.put(propertyName, stats.get(propertyName));
					}
				}
			}
		}
	}

	//region power port control
	//--------------------------------------------------------------------------------------------------------------------------------

	/**
	 * populate output control data
	 *
	 * @param stats store all statistics
	 * @param advancedControllableProperties store all controllable properties
	 * @param outputIndex index of output
	 */
	private void populateOutputsControl(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, int outputIndex) {
		Output realtimeOutputConfig = cachedMonitoringStatus.getOutputs().get(outputIndex);
		int outputsSize = cachedMonitoringStatus.getOutputs().size();
		OutputStatus outputStatus = OutputStatus.getByAPIName(String.valueOf(realtimeOutputConfig.getState() % DeviceConstant.ON_OFF_SWITCH_API_FACTOR));
		if (isOutputsControlEdited.size() < outputsSize || !isOutputsControlEdited.get(outputIndex)) {
			OutputMode outputMode = OutputMode.getByUIName(outputStatus.getUiName());
			realtimeOutputConfig.setOutputMode(outputMode);
			realtimeOutputConfig.setWaitingTimeUnit(WaitingTimeUnit.SECOND);
			realtimeOutputConfig.setWaitingTime(DeviceConstant.DEFAULT_WAITING_TIME);

			if (cachedOutputsControl.size() < outputsSize) {
				cachedOutputsControl.add(new Output(realtimeOutputConfig));
				isOutputsControlEdited.add(false);
			} else {
				cachedOutputsControl.set(outputIndex, new Output(realtimeOutputConfig));
				isOutputsControlEdited.set(outputIndex, false);
			}
		}
		Output output = cachedOutputsControl.get(outputIndex);
		output.setPortNumber(String.valueOf(outputIndex + DeviceConstant.INDEX_TO_ORDINAL_CONVERT_FACTOR));

		String groupName = output.getGroupName();
		String powerPortLabel = groupName.concat(OutputControllingMetric.POWER_PORT);
		String powerPortStatusLabel = groupName.concat(OutputControllingMetric.POWER_PORT_STATUS);
		String batchInitSwitchLabel = groupName.concat(OutputControllingMetric.POWER_PORT_BATCH_INIT_SWITCH);
		String batchEndSwitchLabel = groupName.concat(OutputControllingMetric.POWER_PORT_BATCH_END_SWITCH);
		String batchWaitingTimeLabel = groupName.concat(OutputControllingMetric.POWER_PORT_BATCH_WAITING_TIME);
		String batchWaitingTimeUnitLabel = groupName.concat(OutputControllingMetric.POWER_PORT_BATCH_WAITING_TIME_UNIT);
		String batchCountDown = groupName.concat(OutputControllingMetric.POWER_PORT_BATCH_WAITING_TIME_REMAINING);
		String applyChangesLabel = groupName.concat(OutputControllingMetric.APPLY_CHANGES);
		String cancelChangesLabel = groupName.concat(OutputControllingMetric.CANCEL_CHANGES);
		List<String> outputModes = EnumTypeHandler.getListOfEnumNames(OutputMode.class, OutputMode.ERROR);
		List<String> batchSwitchModes = EnumTypeHandler.getListOfEnumNames(OutputStatus.class, OutputStatus.ERROR);
		List<String> batchWaitingTimeUnitModes = EnumTypeHandler.getListOfEnumNames(WaitingTimeUnit.class, WaitingTimeUnit.ERROR);

		List<Integer> batch = output.getBatch();
		OutputStatus batchInitSwitchValue = OutputStatus.getByAPIName(String.valueOf(batch.get(DeviceConstant.INIT_SWITCH_INDEX) % DeviceConstant.ON_OFF_SWITCH_API_FACTOR));
		OutputStatus batchEndSwitchValue = OutputStatus.getByAPIName(String.valueOf(batch.get(DeviceConstant.END_SWITCH_INDEX) % DeviceConstant.ON_OFF_SWITCH_API_FACTOR));
		OutputMode outputMode = output.getOutputMode();
		WaitingTimeUnit waitingTimeUnit = output.getWaitingTimeUnit();
		String waitingTime = output.getWaitingTime();
		if (cachedBatch.size() <= 12) {
			cachedBatch.add(batchEndSwitchValue.getApiName());
		}
		if (batch.get(1) != 0) {
			String remainingTime = convertSecondToDuration(batch.get(1));
			stats.put(batchCountDown, String.format("Switch to %s in %s", OutputStatus.getByAPIName(cachedBatch.get(outputIndex)).getUiName(), remainingTime));
		}

		stats.put(powerPortStatusLabel, getDefaultValueForNullData(outputStatus.getUiName()));
		if (isConfigManagement) {
			stats.put(groupName.concat(OutputControllingMetric.EDITED), toPascalCase(String.valueOf(isOutputsControlEdited.get(outputIndex))));
			addAdvanceControlProperties(advancedControllableProperties, stats, createDropdown(powerPortLabel, outputModes, outputMode.getUiName()));
		}

		Set<String> unusedKeys = new HashSet<>();
		if (isOutputsControlEdited.get(outputIndex)) {
			switch (outputMode) {
				case ON:
				case OFF:
				case RESET:
					unusedKeys.add(batchInitSwitchLabel);
					unusedKeys.add(batchEndSwitchLabel);
					unusedKeys.add(batchWaitingTimeLabel);
					unusedKeys.add(batchWaitingTimeUnitLabel);
					break;
				case BATCH:
					addAdvanceControlProperties(advancedControllableProperties, stats, createDropdown(batchInitSwitchLabel, batchSwitchModes, batchInitSwitchValue.getUiName()));
					addAdvanceControlProperties(advancedControllableProperties, stats, createDropdown(batchEndSwitchLabel, batchSwitchModes, batchEndSwitchValue.getUiName()));
					addAdvanceControlProperties(advancedControllableProperties, stats, createDropdown(batchWaitingTimeLabel, waitingTimeValues, waitingTime));
					addAdvanceControlProperties(advancedControllableProperties, stats, createDropdown(batchWaitingTimeUnitLabel, batchWaitingTimeUnitModes, waitingTimeUnit.getUiName()));
					break;
				default:
					throw new IllegalStateException(String.format("Power port mode %s is not supported", outputMode));
			}
			addAdvanceControlProperties(advancedControllableProperties, stats, createButton(applyChangesLabel, DeviceConstant.APPLY, DeviceConstant.APPLYING));
			addAdvanceControlProperties(advancedControllableProperties, stats, createButton(cancelChangesLabel, DeviceConstant.CANCEL, DeviceConstant.CANCELING));
		} else {
			unusedKeys.add(batchInitSwitchLabel);
			unusedKeys.add(batchEndSwitchLabel);
			unusedKeys.add(batchWaitingTimeLabel);
			unusedKeys.add(batchWaitingTimeUnitLabel);
			unusedKeys.add(applyChangesLabel);
			unusedKeys.add(cancelChangesLabel);
		}
		removeUnusedStatsAndControls(stats, advancedControllableProperties, unusedKeys);
	}

	/**
	 * handle sensor advance monitoring control
	 *
	 * @param stats store all statistics
	 * @param advancedControllableProperties store all controllable properties
	 * @param controllableProperty controllable property
	 * @param value value of controllable property
	 * @param outputIndex index of output
	 */
	private void powerPortControl(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, String controllableProperty, String value, int outputIndex) {
		Output cachedOutput = cachedOutputsControl.get(outputIndex);
		isOutputsControlEdited.set(outputIndex, true);
		isEmergencyDelivery = true;
		switch (controllableProperty) {
			case OutputControllingMetric.POWER_PORT:
				OutputMode outputMode = OutputMode.getByUIName(value);
				cachedOutput.setOutputMode(outputMode);
				break;
			case OutputControllingMetric.POWER_PORT_BATCH_INIT_SWITCH:
				OutputStatus outputStatus = OutputStatus.getByUIName(value);
				List<Integer> batch = cachedOutput.getBatch();
				batch.set(DeviceConstant.INIT_SWITCH_INDEX, Integer.parseInt(outputStatus.getApiName()));
				break;
			case OutputControllingMetric.POWER_PORT_BATCH_END_SWITCH:
				outputStatus = OutputStatus.getByUIName(value);
				batch = cachedOutput.getBatch();
				batch.set(DeviceConstant.END_SWITCH_INDEX, Integer.parseInt(outputStatus.getApiName()));
				cachedBatch.set(outputIndex, outputStatus.getApiName());
				break;
			case OutputControllingMetric.POWER_PORT_BATCH_WAITING_TIME:
				cachedOutput.setWaitingTime(value);
				break;
			case OutputControllingMetric.POWER_PORT_BATCH_WAITING_TIME_UNIT:
				WaitingTimeUnit waitingTimeUnit = WaitingTimeUnit.getByUIName(value);
				cachedOutput.setWaitingTimeUnit(waitingTimeUnit);
				break;
			case OutputControllingMetric.APPLY_CHANGES:
				cachedOutput = performPowerPortControl(cachedOutput);
				cachedMonitoringStatus.getOutputs().set(outputIndex, new Output(cachedOutput));
				isOutputsControlEdited.set(outputIndex, false);
				break;
			case OutputControllingMetric.CANCEL_CHANGES:
				cachedOutput = new Output(cachedMonitoringStatus.getOutputs().get(outputIndex));
				isOutputsControlEdited.set(outputIndex, false);
				break;
			default:
				throw new IllegalStateException(String.format("Controllable property %s is not supported", controllableProperty));
		}
		cachedOutputsControl.set(outputIndex, cachedOutput);
		populateOutputsControl(stats, advancedControllableProperties, outputIndex);
		populateDeviceInfo(stats, advancedControllableProperties);
	}

	/**
	 * Perform power port control: apply power port controllable properties
	 *
	 * @param cachedOutput cached output data
	 * @return Output output data
	 * @throws IllegalStateException when an error occur
	 */
	private Output performPowerPortControl(Output cachedOutput) {
		try {
			String request = buildDeviceFullPath(cachedOutput.contributeOutputControlRequest());
			doGetWithRetryOnUnauthorized(request, DeviceMonitoringData.class, true);
			DeviceMonitoringData monitoringStatus = doGetWithRetryOnUnauthorized(buildDeviceFullPath(DeviceURL.OUTPUT_DATA), DeviceMonitoringData.class, true);

			if (!cachedOutput.getPortNumber().equals(DeviceConstant.ALL_PORT_NUMBER)) {
				Output output = Optional.ofNullable(monitoringStatus).map(DeviceMonitoringData::getOutputs)
						.map(outputs1 -> outputs1.get(Integer.parseInt(cachedOutput.getPortNumber()) - DeviceConstant.INDEX_TO_ORDINAL_CONVERT_FACTOR)).orElse(cachedOutput);
				output.setGroupName(cachedOutput.getGroupName());
				output.setWaitingTime(cachedOutput.getWaitingTime());
				output.setWaitingTimeUnit(cachedOutput.getWaitingTimeUnit());
				output.setOutputMode(cachedOutput.getOutputMode());
				output.setPortNumber(cachedOutput.getPortNumber());
				return output;
			}
			return null;
		} catch (Exception e) {
			throw new IllegalStateException(String.format("Error while control power port %s: %s", cachedOutput.getPortNumber(), e.getMessage()), e);
		}
	}

	//--------------------------------------------------------------------------------------------------------------------------------
	//endregion

	//region populate advanced controllable properties
	//--------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Create a switch controllable property
	 *
	 * @param name name of the switch
	 * @param statusCode initial switch state (0|1)
	 * @return AdvancedControllableProperty button instance
	 */
	private AdvancedControllableProperty createSwitch(String name, boolean statusCode, String labelOff, String labelOn) {
		AdvancedControllableProperty.Switch toggle = new AdvancedControllableProperty.Switch();
		toggle.setLabelOff(labelOff);
		toggle.setLabelOn(labelOn);
		return new AdvancedControllableProperty(name, new Date(), toggle, statusCode);
	}

	/**
	 * Instantiate Text controllable property
	 *
	 * @param name name of the property
	 * @param label default button label
	 * @return AdvancedControllableProperty button instance
	 */
	private AdvancedControllableProperty createButton(String name, String label, String labelPressed) {
		AdvancedControllableProperty.Button button = new AdvancedControllableProperty.Button();
		button.setLabel(label);
		button.setLabelPressed(labelPressed);
		button.setGracePeriod(0L);
		return new AdvancedControllableProperty(name, new Date(), button, DeviceConstant.EMPTY);
	}

	/**
	 * Add advancedControllableProperties if advancedControllableProperties different empty
	 *
	 * @param advancedControllableProperties advancedControllableProperties is the list that store all controllable properties
	 * @param stats store all statistics
	 * @param property the property is item advancedControllableProperties
	 * @return String response
	 * @throws IllegalStateException when exception occur
	 */
	private void addAdvanceControlProperties(List<AdvancedControllableProperty> advancedControllableProperties, Map<String, String> stats, AdvancedControllableProperty property) {
		if (property != null) {
			for (AdvancedControllableProperty controllableProperty : advancedControllableProperties) {
				if (controllableProperty.getName().equals(property.getName())) {
					advancedControllableProperties.remove(controllableProperty);
					break;
				}
			}
			stats.put(property.getName(), DeviceConstant.EMPTY);
			advancedControllableProperties.add(property);
		}
	}

	/**
	 * This method is used to remove unused statistics/AdvancedControllableProperty from {@link GudePDU8045Communicator#localExtendedStatistics}
	 *
	 * @param stats Map of statistics that contains statistics to be removed
	 * @param controls Set of controls that contains AdvancedControllableProperty to be removed
	 * @param listKeys list key of statistics to be removed
	 */
	private void removeUnusedStatsAndControls(Map<String, String> stats, List<AdvancedControllableProperty> controls, Set<String> listKeys) {
		for (String key : listKeys) {
			stats.remove(key);
			controls.removeIf(advancedControllableProperty -> advancedControllableProperty.getName().equals(key));
		}
	}

	//--------------------------------------------------------------------------------------------------------------------------------
	//endregion

	/**
	 * convert second to duration
	 *
	 * @param secondTime duration in second
	 * @return String converted duration
	 */
	private String convertSecondToDuration(long secondTime) {
		long seconds = secondTime % 60;
		long minutes = secondTime / 60;
		if (minutes >= 60) {
			long hours = minutes / 60;
			minutes %= 60;
			if (hours >= 24) {
				long days = hours / 24;
				return String.format("%d day(s) %d hour(s) %d minutes(s) %d second(s)", days, hours % 24, minutes, seconds);
			}
			return String.format("%d hour(s) %d minutes(s) %d second(s)", hours, minutes, seconds);
		}
		return String.format("%d minutes(s) %d second(s)", minutes, seconds);
	}

	/**
	 * Convert string to Pascal Case
	 *
	 * @param init init String
	 * @return String converted string
	 */
	public String toPascalCase(final String init) {
		if (init == null) {
			return null;
		}

		final StringBuilder ret = new StringBuilder(init.length());

		for (final String word : init.split(DeviceConstant.SPACE)) {
			if (!word.isEmpty()) {
				ret.append(Character.toUpperCase(word.charAt(0)));
				ret.append(word.substring(1).toLowerCase());
			}
		}

		return ret.toString();
	}


	/**
	 * init value For waitingTimeValues
	 */
	private void initValueForWaitingTimeValues() {
		for (int i = DeviceConstant.MIN_WAITING_TIME; i <= DeviceConstant.MAX_WAITING_TIME; i++) {
			waitingTimeValues.add(String.valueOf(i));
		}
	}

	/**
	 * get default value for null data
	 *
	 * @param value value of monitoring properties
	 * @return String (none/value)
	 */
	private String getDefaultValueForNullData(String value) {
		return StringUtils.isNullOrEmpty(value) ? DeviceConstant.NONE : value;
	}

	/**
	 * This method is used to validate input config management from user
	 *
	 * @return boolean is configManagement
	 */
	private void isValidConfigManagement() {
		isConfigManagement = StringUtils.isNotNullOrEmpty(this.configManagement) && this.configManagement.equalsIgnoreCase(String.valueOf(true));
	}

	/**
	 * This method is used to handle input from adapter properties and convert it to Set of String for control
	 *
	 * @return Set<String> is the Set of String of filter element
	 */
	private Set<String> convertUserInput(String input) {
		try {
			if (!StringUtils.isNullOrEmpty(input)) {
				String[] listAdapterPropertyElement = input.split(DeviceConstant.COMMA);

				// Remove start and end spaces of each adapterProperty
				Set<String> setAdapterPropertiesElement = new HashSet<>();
				for (String adapterPropertyElement : listAdapterPropertyElement) {
					setAdapterPropertiesElement.add(adapterPropertyElement.trim());
				}
				return setAdapterPropertiesElement;
			}
			return Collections.emptySet();
		} catch (Exception e) {
			logger.error("In valid adapter properties input");
		}
		return Collections.emptySet();
	}
}