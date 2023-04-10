import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.avispl.symphony.api.dal.dto.control.AdvancedControllableProperty;
import com.avispl.symphony.api.dal.dto.control.ControllableProperty;
import com.avispl.symphony.api.dal.dto.monitor.ExtendedStatistics;
import com.avispl.symphony.api.dal.dto.monitor.Statistics;
import com.avispl.symphony.dal.avdevices.power.gude.GudePDU8045Communicator;
import com.avispl.symphony.dal.avdevices.power.gude.utils.DeviceConstant;
import com.avispl.symphony.dal.avdevices.power.gude.utils.DevicesMetricGroup;
import com.avispl.symphony.dal.avdevices.power.gude.utils.controlling.ColdStart;
import com.avispl.symphony.dal.avdevices.power.gude.utils.controlling.OnOffStatus;
import com.avispl.symphony.dal.avdevices.power.gude.utils.controlling.OutputControllingMetric;
import com.avispl.symphony.dal.avdevices.power.gude.utils.controlling.PowerPortConfigMetric;
import com.avispl.symphony.dal.avdevices.power.gude.utils.controlling.WatchDogMode;
import com.avispl.symphony.dal.avdevices.power.gude.utils.controlling.WatchdogResetPortWhenHostDownMode;
import com.avispl.symphony.dal.util.StringUtils;

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
		communicator.setHost("192.168.254.172");
		communicator.setPort(443);
		communicator.setLogin("admin");
		communicator.setPassword("admin");
		communicator.setTrustAllCertificates(true);
		communicator.setConfigManagement("true");
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
		Assertions.assertNotNull(dynamicStats.get("SensorPort02#Humidity(%)"));
		Assertions.assertNotNull(dynamicStats.get("SensorPort01#DewPoint(C)"));
		Assertions.assertNotNull(dynamicStats.get("MeterL1#PowerReactive(VAR)"));
		Assertions.assertNotNull(dynamicStats.get("MeterL1#PowerApparent(VA)"));
		Assertions.assertNotNull(dynamicStats.get("SensorPort01#Temperature(C)"));
		Assertions.assertNotNull(dynamicStats.get("SensorPort02#Temperature(C)"));
	}

	/**
	 *  Test returned value of getMultipleStatistics
	 *
	 * Expected: control successfully
	 */
	@Test
	void testReturnedValue() throws Exception {
		Statistics statistics = communicator.getMultipleStatistics().get(0);
		ExtendedStatistics x = (ExtendedStatistics) statistics;
		Map<String, String> x1 = x.getStatistics();
		for (Entry<String, String> entry : x1.entrySet()) {
			if (entry.getKey().contains("PowerPortStatus")) {
				Assertions.assertNotNull(entry.getValue());
			}
		}
	}
}