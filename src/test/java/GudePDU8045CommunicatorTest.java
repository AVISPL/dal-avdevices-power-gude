import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.avispl.symphony.api.dal.dto.control.AdvancedControllableProperty;
import com.avispl.symphony.api.dal.dto.control.ControllableProperty;
import com.avispl.symphony.api.dal.dto.monitor.ExtendedStatistics;
import com.avispl.symphony.api.dal.error.ResourceNotReachableException;
import com.avispl.symphony.dal.avdevices.power.gude.GudePDU8045Communicator;
import com.avispl.symphony.dal.avdevices.power.gude.utils.DeviceConstant;
import com.avispl.symphony.dal.avdevices.power.gude.utils.DeviceInfoMetric;
import com.avispl.symphony.dal.avdevices.power.gude.utils.DevicesMetricGroup;
import com.avispl.symphony.dal.avdevices.power.gude.utils.controlling.ColdStart;
import com.avispl.symphony.dal.avdevices.power.gude.utils.controlling.OnOffStatus;
import com.avispl.symphony.dal.avdevices.power.gude.utils.controlling.OutputControllingMetric;
import com.avispl.symphony.dal.avdevices.power.gude.utils.controlling.PowerPortConfigMetric;
import com.avispl.symphony.dal.avdevices.power.gude.utils.controlling.WatchDogMode;
import com.avispl.symphony.dal.avdevices.power.gude.utils.controlling.WatchdogResetPortWhenHostDownMode;

/**
 * GudePDU8045Comunicator
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 26/12/2022
 * @since 1.0.0
 */
class GudePDU8045CommunicatorTest {
	private final GudePDU8045Communicator communicator = new GudePDU8045Communicator();

	@BeforeEach()
	public void setUp() throws Exception {
		communicator.setHost("***REMOVED***");
		communicator.setPort(80);
		communicator.setLogin("admin");
		communicator.setPassword("admin");
		communicator.setTrustAllCertificates(true);
		communicator.setConfigManagement("true");
		communicator.setConfigCookie("***REMOVED***");
		communicator.init();
		communicator.connect();
	}

	@AfterEach()
	public void destroy() throws Exception {
		communicator.disconnect();
	}

	/**
	 * Test GudePDU8045Communicator.controlProperty advance monitoring control
	 *
	 * Expected: control successfully
	 */
	@Test
	void testAdvanceMonitoringControl() throws Exception {
		ExtendedStatistics statistics = (ExtendedStatistics) communicator.getMultipleStatistics().get(0);
		Map<String, String> stats = statistics.getStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();

		String propertyName = DevicesMetricGroup.SENSOR.getName() + "01" + DeviceConstant.HASH + "AdvanceMonitoring";
		String propertyValue = "1";
		controllableProperty.setProperty(propertyName);
		controllableProperty.setValue(propertyValue);
		communicator.controlProperty(controllableProperty);

		Assertions.assertEquals(propertyValue, stats.get(propertyName));
		// chi co device 9 va 20
	}

	/**
	 * Test GudePDU8045Communicator.controlProperty advance monitoring control
	 *
	 * Expected: control successfully
	 */
	@Test
	void testPowerPortControlInReset() throws Exception {
		ExtendedStatistics statistics = (ExtendedStatistics) communicator.getMultipleStatistics().get(0);
		Map<String, String> stats = statistics.getStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();

		String propertyName = "PowerPort01" + DeviceConstant.HASH + "PowerPort";
		String propertyValue = "Reset";
		controllableProperty.setProperty(propertyName);
		controllableProperty.setValue(propertyValue);
		communicator.controlProperty(controllableProperty);
		communicator.getMultipleStatistics();

		Assertions.assertEquals("true", stats.get("PowerPort01#Edited"));
	}

	/**
	 * Test GudePDU8045Communicator.controlProperty advance monitoring control
	 *
	 * Expected: control successfully
	 */
	@Test
	void testPowerPortControlInApplyChange() throws Exception {
		ExtendedStatistics statistics = (ExtendedStatistics) communicator.getMultipleStatistics().get(0);
		Map<String, String> stats = statistics.getStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();

		String propertyName = "PowerPort01" + DeviceConstant.HASH + "PowerPort";
		String propertyValue = "Reset";
		controllableProperty.setProperty(propertyName);
		controllableProperty.setValue(propertyValue);
		communicator.controlProperty(controllableProperty);
		communicator.getMultipleStatistics();

		propertyName = "PowerPort01" + DeviceConstant.HASH + "ApplyChanges";
		propertyValue = "";
		controllableProperty.setProperty(propertyName);
		controllableProperty.setValue(propertyValue);
		communicator.controlProperty(controllableProperty);
		communicator.getMultipleStatistics();

//		Assertions.assertEquals("false", stats.get("PowerPort01#Edited"));
		Assertions.assertTrue(stats.get("PowerPort01Control#".concat(OutputControllingMetric.POWER_PORT_BATCH_WAITING_TIME_REMAINING_01)).contains("Switch to on in"));
	}

	/**
	 * Test GudePDU8045Communicator.controlProperty advance monitoring control
	 *
	 * Expected: control successfully
	 */
	@Test
	void testPowerPortControlInBatch() throws Exception {
		communicator.setHistoricalProperties("PowerActive(W)");
		ExtendedStatistics statistics = (ExtendedStatistics) communicator.getMultipleStatistics().get(0);
		Map<String, String> stats = statistics.getStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();

		String propertyName = "PowerPort01Control" + DeviceConstant.HASH + "00PowerPort";
		String propertyValue = "Batch";
		controllableProperty.setProperty(propertyName);
		controllableProperty.setValue(propertyValue);
		communicator.controlProperty(controllableProperty);
		communicator.getMultipleStatistics();

		propertyName = "PowerPort01" + DeviceConstant.HASH + "ApplyChanges";
		propertyValue = "1";
		controllableProperty.setProperty(propertyName);
		controllableProperty.setValue(propertyValue);
		communicator.controlProperty(controllableProperty);
		communicator.getMultipleStatistics();

		Assertions.assertEquals("false", stats.get("PowerPort01#Edited"));
	}

	/**
	 * Test GudePDU8045Communicator.controlProperty advance monitoring control
	 *
	 * Expected: control successfully
	 */
	@Test
	void testPowerPortControlAllSwitch() throws Exception {
		ExtendedStatistics statistics = (ExtendedStatistics) communicator.getMultipleStatistics().get(0);
		Map<String, String> stats = statistics.getStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();

		String propertyName = "PowerPortAllOff";
		String propertyValue = "1";
		controllableProperty.setProperty(propertyName);
		controllableProperty.setValue(propertyValue);
		communicator.controlProperty(controllableProperty);
		communicator.getMultipleStatistics();

		Assertions.assertEquals(OnOffStatus.OFF, stats.get("PowerPort01#PowerPortStatus"));
		Assertions.assertEquals(OnOffStatus.OFF, stats.get("PowerPort02#PowerPortStatus"));
		Assertions.assertEquals(OnOffStatus.OFF, stats.get("PowerPort03#PowerPortStatus"));
		Assertions.assertEquals(OnOffStatus.OFF, stats.get("PowerPort04#PowerPortStatus"));
		Assertions.assertEquals(OnOffStatus.OFF, stats.get("PowerPort05#PowerPortStatus"));
		Assertions.assertEquals(OnOffStatus.OFF, stats.get("PowerPort06#PowerPortStatus"));
		Assertions.assertEquals(OnOffStatus.OFF, stats.get("PowerPort07#PowerPortStatus"));
		Assertions.assertEquals(OnOffStatus.OFF, stats.get("PowerPort08#PowerPortStatus"));
		Assertions.assertEquals(OnOffStatus.OFF, stats.get("PowerPort09#PowerPortStatus"));
		Assertions.assertEquals(OnOffStatus.OFF, stats.get("PowerPort10#PowerPortStatus"));
		Assertions.assertEquals(OnOffStatus.OFF, stats.get("PowerPort11#PowerPortStatus"));
		Assertions.assertEquals(OnOffStatus.OFF, stats.get("PowerPort12#PowerPortStatus"));
	}

	/**
	 * Test GudePDU8045Communicator.controlProperty advance monitoring control
	 *
	 * Expected: control successfully
	 */
	@Test
	void testPowerPortControlInBatchOnToOn() throws Exception {
		ExtendedStatistics statistics = (ExtendedStatistics) communicator.getMultipleStatistics().get(0);
		Map<String, String> stats = statistics.getStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();

		String propertyName = "PowerPort01" + DeviceConstant.HASH + "PowerPort";
		String propertyValue = "Off";
		controllableProperty.setProperty(propertyName);
		controllableProperty.setValue(propertyValue);
		communicator.controlProperty(controllableProperty);
		communicator.getMultipleStatistics();

		propertyName = "PowerPort01" + DeviceConstant.HASH + "ApplyChanges";
		propertyValue = "1";
		controllableProperty.setProperty(propertyName);
		controllableProperty.setValue(propertyValue);
		communicator.controlProperty(controllableProperty);
		communicator.getMultipleStatistics();

		propertyName = "PowerPort01" + DeviceConstant.HASH + "PowerPort";
		propertyValue = "Batch";
		controllableProperty.setProperty(propertyName);
		controllableProperty.setValue(propertyValue);
		communicator.controlProperty(controllableProperty);
		communicator.getMultipleStatistics();

		propertyName = "PowerPort01" + DeviceConstant.HASH + "01PowerPortBatchInitSwitch";
		propertyValue = "On";
		controllableProperty.setProperty(propertyName);
		controllableProperty.setValue(propertyValue);
		communicator.controlProperty(controllableProperty);
		communicator.getMultipleStatistics();

		propertyName = "PowerPort01" + DeviceConstant.HASH + "PowerPortBatchEndSwitch";
		propertyValue = "Off";
		controllableProperty.setProperty(propertyName);
		controllableProperty.setValue(propertyValue);
		communicator.controlProperty(controllableProperty);
		communicator.getMultipleStatistics();

		propertyName = "PowerPort01" + DeviceConstant.HASH + "ApplyChanges";
		propertyValue = "1";
		controllableProperty.setProperty(propertyName);
		controllableProperty.setValue(propertyValue);
		communicator.controlProperty(controllableProperty);
		communicator.getMultipleStatistics();

		Assertions.assertEquals("On", stats.get("PowerPort01PowerPortStatus"));
	}

	/**
	 * Test GudePDU8045Communicator.controlProperty PowerPortConfig:
	 *
	 * Expected: control successfully
	 */
	@Test
	void testPowerPortConfigChoosePort() throws Exception {
		ExtendedStatistics statistics = (ExtendedStatistics) communicator.getMultipleStatistics().get(0);
		ControllableProperty controllableProperty = new ControllableProperty();

		String propertyName = DevicesMetricGroup.POWER_PORT_CONFIG.getName() + DeviceConstant.HASH + PowerPortConfigMetric.CHOOSE_POWER_PORT.getUiName();
		String propertyValue = "3:Power Port";
		controllableProperty.setProperty(propertyName);
		controllableProperty.setValue(propertyValue);
		communicator.controlProperty(controllableProperty);
		communicator.getMultipleStatistics();

		List<AdvancedControllableProperty> advancedControllableProperties = statistics.getControllableProperties();
		AdvancedControllableProperty advancedControllableProperty = advancedControllableProperties.stream().filter(control -> propertyName.equals(control.getName())).findFirst().orElse(null);
		String valueAfterControl = (String) Optional.ofNullable(advancedControllableProperty.getValue()).orElse(DeviceConstant.NONE);

		Assertions.assertEquals(propertyValue, valueAfterControl);
	}

	/**
	 * Test GudePDU8045Communicator.controlProperty PowerPortConfig:
	 *
	 * Expected: control successfully
	 */
	@Test
	void testPowerPortConfigLabel() throws Exception {
		ExtendedStatistics statistics = (ExtendedStatistics) communicator.getMultipleStatistics().get(0);
		ControllableProperty controllableProperty = new ControllableProperty();

		String propertyName = DevicesMetricGroup.POWER_PORT_CONFIG.getName() + DeviceConstant.HASH + PowerPortConfigMetric.CHOOSE_POWER_PORT.getUiName();
		String propertyValue = "3:Power Port";
		controllableProperty.setProperty(propertyName);
		controllableProperty.setValue(propertyValue);
		communicator.controlProperty(controllableProperty);
		communicator.getMultipleStatistics();

		propertyName = DevicesMetricGroup.POWER_PORT_CONFIG.getName() + DeviceConstant.HASH + PowerPortConfigMetric.CUSTOM_LABEL.getUiName();
		propertyValue = "Power Port Update";
		controllableProperty.setProperty(propertyName);
		controllableProperty.setValue(propertyValue);
		communicator.controlProperty(controllableProperty);
		communicator.getMultipleStatistics();

		List<AdvancedControllableProperty> advancedControllableProperties = statistics.getControllableProperties();
		String finalPropertyName = propertyName;
		AdvancedControllableProperty advancedControllableProperty = advancedControllableProperties.stream().filter(control -> finalPropertyName.equals(control.getName())).findFirst().orElse(null);
		String valueAfterControl = (String) Optional.ofNullable(advancedControllableProperty.getValue()).orElse(DeviceConstant.NONE);

		Assertions.assertEquals(propertyValue, valueAfterControl);
	}

	/**
	 * Test GudePDU8045Communicator.controlProperty PowerPortConfig:
	 *
	 * Expected: control successfully
	 */
	@Test
	void testPowerPortConfigResetPortWhenHostGoDown() throws Exception {
		ExtendedStatistics statistics = (ExtendedStatistics) communicator.getMultipleStatistics().get(0);
		ControllableProperty controllableProperty = new ControllableProperty();

		String propertyName = DevicesMetricGroup.POWER_PORT_CONFIG.getName() + DeviceConstant.HASH + PowerPortConfigMetric.WATCHDOG_MODE.getUiName();
		String propertyValue = WatchDogMode.RESET_PORT_WHEN_HOST_DOWN.getUiName();
		controllableProperty.setProperty(propertyName);
		controllableProperty.setValue(propertyValue);
		communicator.controlProperty(controllableProperty);
		communicator.getMultipleStatistics();

		propertyName = DevicesMetricGroup.POWER_PORT_CONFIG.getName() + DeviceConstant.HASH + PowerPortConfigMetric.APPLY_CHANGES.getUiName();
		propertyValue = "Power Port Update";
		controllableProperty.setProperty(propertyName);
		controllableProperty.setValue(propertyValue);
		communicator.controlProperty(controllableProperty);
		communicator.getMultipleStatistics();

		List<AdvancedControllableProperty> advancedControllableProperties = statistics.getControllableProperties();
		String finalPropertyName = propertyName;
		AdvancedControllableProperty advancedControllableProperty = advancedControllableProperties.stream().filter(control -> finalPropertyName.equals(control.getName())).findFirst().orElse(null);
		String valueAfterControl = (String) Optional.ofNullable(advancedControllableProperty.getValue()).orElse(DeviceConstant.NONE);

		Assertions.assertEquals(propertyValue, valueAfterControl);
	}

	/**
	 * Test GudePDU8045Communicator.controlProperty PowerPortConfig:
	 *
	 * Expected: control successfully
	 */
	@Test
	void testPowerPortConfigResetPortWhenHostGoDownAndRepeatReset() throws Exception {
		ExtendedStatistics statistics = (ExtendedStatistics) communicator.getMultipleStatistics().get(0);
		ControllableProperty controllableProperty = new ControllableProperty();

		String propertyName = DevicesMetricGroup.POWER_PORT_CONFIG.getName() + DeviceConstant.HASH + PowerPortConfigMetric.WATCHDOG_MODE.getUiName();
		String propertyValue = WatchDogMode.IP_MASTER_SLAVE_PORT.getUiName();
		controllableProperty.setProperty(propertyName);
		controllableProperty.setValue(propertyValue);
		communicator.controlProperty(controllableProperty);
		communicator.getMultipleStatistics();

		propertyName = DevicesMetricGroup.POWER_PORT_CONFIG.getName() + DeviceConstant.HASH + PowerPortConfigMetric.WATCHDOG_MODE_RESET_PORT_WHEN_HOST_DOWN.getUiName();
		propertyValue = WatchdogResetPortWhenHostDownMode.REPEAT_RESET.getUiName();
		controllableProperty.setProperty(propertyName);
		controllableProperty.setValue(propertyValue);
		communicator.controlProperty(controllableProperty);
		communicator.getMultipleStatistics();

		propertyName = DevicesMetricGroup.POWER_PORT_CONFIG.getName() + DeviceConstant.HASH + PowerPortConfigMetric.APPLY_CHANGES.getUiName();
		propertyValue = "Power Port Update";
		controllableProperty.setProperty(propertyName);
		controllableProperty.setValue(propertyValue);
		communicator.controlProperty(controllableProperty);
		communicator.getMultipleStatistics();

		List<AdvancedControllableProperty> advancedControllableProperties = statistics.getControllableProperties();
		String finalPropertyName = propertyName;
		AdvancedControllableProperty advancedControllableProperty = advancedControllableProperties.stream().filter(control -> finalPropertyName.equals(control.getName())).findFirst().orElse(null);
		String valueAfterControl = (String) Optional.ofNullable(advancedControllableProperty.getValue()).orElse(DeviceConstant.NONE);

		Assertions.assertEquals(propertyValue, valueAfterControl);
	}

	/**
	 * Test GudePDU8045Communicator.controlProperty PowerPortConfig:
	 *
	 * Expected: control successfully
	 */
	@Test
	void testPowerPortConfigResetDuration() throws Exception {
		ExtendedStatistics statistics = (ExtendedStatistics) communicator.getMultipleStatistics().get(0);
		ControllableProperty controllableProperty = new ControllableProperty();

		String propertyName = DevicesMetricGroup.POWER_PORT_CONFIG.getName() + DeviceConstant.HASH + PowerPortConfigMetric.RESET_DURATION.getUiName();
		String propertyValue = "";
		controllableProperty.setProperty(propertyName);
		controllableProperty.setValue(propertyValue);
		communicator.controlProperty(controllableProperty);
		communicator.getMultipleStatistics();

		propertyName = DevicesMetricGroup.POWER_PORT_CONFIG.getName() + DeviceConstant.HASH + PowerPortConfigMetric.CUSTOM_LABEL.getUiName();
		propertyValue = "Power Port Update";
		controllableProperty.setProperty(propertyName);
		controllableProperty.setValue(propertyValue);
		communicator.controlProperty(controllableProperty);
		communicator.getMultipleStatistics();

		List<AdvancedControllableProperty> advancedControllableProperties = statistics.getControllableProperties();
		String finalPropertyName = propertyName;
		AdvancedControllableProperty advancedControllableProperty = advancedControllableProperties.stream().filter(control -> finalPropertyName.equals(control.getName())).findFirst().orElse(null);
		String valueAfterControl = (String) Optional.ofNullable(advancedControllableProperty.getValue()).orElse(DeviceConstant.NONE);

		Assertions.assertEquals(propertyValue, valueAfterControl);
	}


	/**
	 * Test GudePDU8045Communicator.controlProperty PowerPortConfig:
	 *
	 * Expected: control successfully
	 */
	@Test
	void testPowerPortConfigInitStatus() throws Exception {
		ExtendedStatistics statistics = (ExtendedStatistics) communicator.getMultipleStatistics().get(0);
		ControllableProperty controllableProperty = new ControllableProperty();

		String propertyName = DevicesMetricGroup.POWER_PORT_CONFIG.getName() + DeviceConstant.HASH + PowerPortConfigMetric.INITIALIZATION_STATUS.getUiName();
		String propertyValue = ColdStart.ON.getUiName();
		controllableProperty.setProperty(propertyName);
		controllableProperty.setValue(propertyValue);
		communicator.controlProperty(controllableProperty);
		communicator.getMultipleStatistics();

		propertyName = DevicesMetricGroup.POWER_PORT_CONFIG.getName() + DeviceConstant.HASH + PowerPortConfigMetric.APPLY_CHANGES.getUiName();
		propertyValue = "";
		controllableProperty.setProperty(propertyName);
		controllableProperty.setValue(propertyValue);
		communicator.controlProperty(controllableProperty);
		communicator.getMultipleStatistics();

		List<AdvancedControllableProperty> advancedControllableProperties = statistics.getControllableProperties();
		String finalPropertyName = propertyName;
		AdvancedControllableProperty advancedControllableProperty = advancedControllableProperties.stream().filter(control -> finalPropertyName.equals(control.getName())).findFirst().orElse(null);
		String valueAfterControl = (String) Optional.ofNullable(advancedControllableProperty.getValue()).orElse(DeviceConstant.NONE);

		Assertions.assertEquals(propertyValue, valueAfterControl);
	}

	/**
	 * Test GudePDU8045Communicator.GetMultipleStatistics:
	 *
	 * Expected: contain valid dynamic statistic values
	 */
	@Test
	void testDynamicStatistic() throws Exception {
		communicator.setHistoricalProperties("Current(mA), PowerActive(W), PowerReactive(VAR), PowerApparent(VA), Temperature(C), Humidity(%), DewPoint(C)");
		ExtendedStatistics statistics = (ExtendedStatistics) communicator.getMultipleStatistics().get(0);
		Map<String, String> dynamicStats = statistics.getDynamicStatistics();

		//Assertions.assertNotNull(dynamicStats.get("SensorPort02#DewPoint(C)"));
		Assertions.assertNotNull(dynamicStats.get("MeterL1#PowerActive(W)"));
		Assertions.assertNotNull(dynamicStats.get("SensorPort01#Humidity(%)"));
		Assertions.assertNotNull(dynamicStats.get("MeterL1#Current(mA)"));
		Assertions.assertNotNull(dynamicStats.get("SensorPort01#DewPoint(C)"));
		Assertions.assertNotNull(dynamicStats.get("MeterL1#PowerReactive(VAR)"));
		Assertions.assertNotNull(dynamicStats.get("MeterL1#PowerApparent(VA)"));
		Assertions.assertNotNull(dynamicStats.get("SensorPort01#Temperature(C)"));
		Assertions.assertNotNull(dynamicStats.get("SensorPort02#Temperature(C)"));
	}

	/**
	 * Test status of power port when apply cookie
	 *
	 * Expected: Power port will return On when apply cookie
	 */
	@Test
	void testPowerPortStatusApplyCookie() throws Exception {
		GudePDU8045Communicator communicator = new GudePDU8045Communicator();
		communicator.setHost("***REMOVED***");
		communicator.setPort(443);
		communicator.setLogin("admin");
		communicator.setPassword("admin");
		communicator.setTrustAllCertificates(true);
		communicator.setConfigManagement("true");
		communicator.setConfigCookie("");
		communicator.init();
		communicator.setPort(443);
		communicator.connect();
		ExtendedStatistics statistics = (ExtendedStatistics) communicator.getMultipleStatistics().get(0);
		Map<String, String> stats = statistics.getStatistics();
		Assertions.assertEquals("Off", stats.get("PowerPort01Status"));
		communicator.destroy();
		communicator.setHost("8031.demo.gude-systems.com");
		communicator.setPort(80);
		communicator.setProtocol("http");
		communicator.setLogin("admin");
		communicator.setPassword("admin");
		communicator.setTrustAllCertificates(true);
		communicator.setConfigManagement("true");
		communicator.setConfigCookie("***REMOVED***");
		communicator.init();
		communicator.connect();
		statistics = (ExtendedStatistics) communicator.getMultipleStatistics().get(0);
		stats = statistics.getStatistics();
		Assertions.assertEquals("On", stats.get("PowerPort01Status"));
	}

	/**
	 * Test remove power port when apply cancel change
	 *
	 * Expected: power port was removed
	 */
	@Test
	void testPowerPortControl05() throws Exception {
		ExtendedStatistics statistics = (ExtendedStatistics) communicator.getMultipleStatistics().get(0);
		Map<String, String> stats = statistics.getStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();

		String propertyName = "PowerPort01Control" + DeviceConstant.HASH + "00PowerPort";
		String propertyValue = "Batch";
		controllableProperty.setProperty(propertyName);
		controllableProperty.setValue(propertyValue);
		communicator.controlProperty(controllableProperty);

		communicator.getMultipleStatistics().get(0);
		propertyName = "PowerPort01Control" + DeviceConstant.HASH + "CancelChanges";
		controllableProperty.setProperty(propertyName);
		controllableProperty.setValue(1);
		communicator.controlProperty(controllableProperty);
		communicator.getMultipleStatistics();
		stats = statistics.getStatistics();
		Assertions.assertNull(stats.get("PowerPort01Control#05PowerPortBatchWaitingTimeRemaining"));
	}

	/**
	 * Test all on or all off button
	 *
	 * Expected: all ports respond "on" when clicking the "all on" button and off when clicking the "all of" button
	 */
	@Test
	public void testAllOnOrAllOff() throws Exception {
		ExtendedStatistics statistics = (ExtendedStatistics) communicator.getMultipleStatistics().get(0);

		ControllableProperty controllableProperty = new ControllableProperty();
		controllableProperty.setProperty(DeviceInfoMetric.ALL_POWER_PORT_CONTROL_OFF.getName());
		controllableProperty.setValue("");
		controllableProperty.setDeviceId("");
		communicator.controlProperty(controllableProperty);
		communicator.getMultipleStatistics();

		Thread.sleep(2000);
		Map<String, String> stats = statistics.getStatistics();
		for (int i = 1; i < 9; ++i) {
			Assertions.assertEquals("Off", stats.get(String.format("PowerPort0%sStatus", i)));
		}

		controllableProperty.setProperty(DeviceInfoMetric.ALL_POWER_PORT_CONTROL_ON.getName());
		controllableProperty.setValue("");
		controllableProperty.setDeviceId("");
		communicator.controlProperty(controllableProperty);
		communicator.getMultipleStatistics();

		Thread.sleep(2000);
		for (int i = 1; i < 9; ++i) {
			Assertions.assertEquals("On", stats.get(String.format("PowerPort0%sStatus", i)));
		}
	}

	/**
	 * Test connection with Http protocol
	 *
	 * Expected: It doesn't throw exception
	 */
	@Test
	public void testConnectionWithHttpProtocol() throws Exception {
		GudePDU8045Communicator gudePDU8045Communicator = new GudePDU8045Communicator();
		gudePDU8045Communicator.setHost("8031.demo.gude-systems.com");
		gudePDU8045Communicator.setPort(80);
		gudePDU8045Communicator.setLogin("admin");
		gudePDU8045Communicator.setPassword("admin");
		gudePDU8045Communicator.setTrustAllCertificates(true);
		gudePDU8045Communicator.setConfigManagement("true");
		gudePDU8045Communicator.setConfigCookie("***REMOVED***");
		gudePDU8045Communicator.init();
		gudePDU8045Communicator.connect();
		Assertions.assertDoesNotThrow(() -> gudePDU8045Communicator.getMultipleStatistics());
	}

	/**
	 * Test connection with Https protocol
	 *
	 * Expected: It doesn't throw exception
	 */
	@Test
	public void testConnectionWithHttpsProtocol() throws Exception {
		GudePDU8045Communicator gudePDU8045Communicator = new GudePDU8045Communicator();
		gudePDU8045Communicator.setHost("8031.demo.gude-systems.com");
		gudePDU8045Communicator.setPort(443);
		gudePDU8045Communicator.setLogin("admin");
		gudePDU8045Communicator.setPassword("admin");
		gudePDU8045Communicator.setTrustAllCertificates(true);
		gudePDU8045Communicator.setConfigManagement("true");
		gudePDU8045Communicator.setConfigCookie("***REMOVED***");
		gudePDU8045Communicator.init();
		gudePDU8045Communicator.connect();
		Assertions.assertDoesNotThrow(() -> gudePDU8045Communicator.getMultipleStatistics());
	}


	/**
	 * Test connection with Http protocol
	 *
	 * Expected: It will throw ResourceNotReachableException exception
	 */
	@Test
	public void testConnectionWhileSettingWrongPort() throws Exception {
		GudePDU8045Communicator gudePDU8045Communicator = new GudePDU8045Communicator();
		gudePDU8045Communicator.setHost("8031.demo.gude-systems.com");
		gudePDU8045Communicator.setPort(8080);
		gudePDU8045Communicator.setLogin("admin");
		gudePDU8045Communicator.setPassword("admin");
		gudePDU8045Communicator.setTrustAllCertificates(true);
		gudePDU8045Communicator.setConfigManagement("true");
		gudePDU8045Communicator.setConfigCookie("***REMOVED***");
		gudePDU8045Communicator.init();
		gudePDU8045Communicator.connect();
		Assertions.assertThrows(ResourceNotReachableException.class, () -> gudePDU8045Communicator.getMultipleStatistics(), "Expect fail here due to using wrong port");
	}

	/**
	 * Test real device connection with Http protocol
	 *
	 * Expected: It doesn't throw exception
	 */
	@Test
	public void testRealDeviceConnectionWithHttpProtocol() throws Exception {
		GudePDU8045Communicator gudePDU8045Communicator = new GudePDU8045Communicator();
		gudePDU8045Communicator.setHost("***REMOVED***");
		gudePDU8045Communicator.setPort(80);
		gudePDU8045Communicator.setLogin("admin");
		gudePDU8045Communicator.setPassword("admin");
		gudePDU8045Communicator.setTrustAllCertificates(true);
		gudePDU8045Communicator.setConfigManagement("true");
		gudePDU8045Communicator.init();
		gudePDU8045Communicator.connect();
		Assertions.assertDoesNotThrow(() -> gudePDU8045Communicator.getMultipleStatistics());
	}

	/**
	 * Test real device connection with Https protocol
	 *
	 * Expected: It doesn't throw exception
	 */
	@Test
	public void testRealDeviceConnectionWithHttpsProtocol() throws Exception {
		GudePDU8045Communicator gudePDU8045Communicator = new GudePDU8045Communicator();
		gudePDU8045Communicator.setHost("***REMOVED***");
		gudePDU8045Communicator.setPort(443);
		gudePDU8045Communicator.setLogin("admin");
		gudePDU8045Communicator.setPassword("admin");
		gudePDU8045Communicator.setTrustAllCertificates(true);
		gudePDU8045Communicator.setConfigManagement("true");
		gudePDU8045Communicator.init();
		gudePDU8045Communicator.connect();
		Assertions.assertDoesNotThrow(() -> gudePDU8045Communicator.getMultipleStatistics());
	}

	/**
	 * Test real device connection with port 80 and Http protocol
	 *
	 * Expected: It will throw ResourceNotReachableException exception
	 */
	@Test
	public void testRealDeviceConnectionWithPort80AndHttpsProtocol() throws Exception {
		GudePDU8045Communicator gudePDU8045Communicator = new GudePDU8045Communicator();
		gudePDU8045Communicator.setHost("***REMOVED***");
		gudePDU8045Communicator.setPort(80);
		gudePDU8045Communicator.setProtocol("https");
		gudePDU8045Communicator.setLogin("admin");
		gudePDU8045Communicator.setPassword("admin");
		gudePDU8045Communicator.setTrustAllCertificates(true);
		gudePDU8045Communicator.setConfigManagement("true");
		gudePDU8045Communicator.init();
		gudePDU8045Communicator.connect();
		Assertions.assertThrows(ResourceNotReachableException.class, () -> gudePDU8045Communicator.getMultipleStatistics(), "Expect fail here due to port 80 must use the Http protocol");
	}

	/**
	 * Test real device connection will use Https protocol when set port is 443
	 *
	 * Expected: It doesn't throw exception
	 */
	@Test
	public void testRealDeviceConnectionWillUseHttpsProtocolWhenSetPortIs443() throws Exception {
		GudePDU8045Communicator gudePDU8045Communicator = new GudePDU8045Communicator();
		gudePDU8045Communicator.setHost("***REMOVED***");
		gudePDU8045Communicator.setPort(443);
		gudePDU8045Communicator.setProtocol("http");
		gudePDU8045Communicator.setLogin("admin");
		gudePDU8045Communicator.setPassword("admin");
		gudePDU8045Communicator.setTrustAllCertificates(true);
		gudePDU8045Communicator.setConfigManagement("true");
		gudePDU8045Communicator.init();
		gudePDU8045Communicator.connect();
		Assertions.assertDoesNotThrow(() -> gudePDU8045Communicator.getMultipleStatistics());
	}
}