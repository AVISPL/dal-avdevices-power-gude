import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.avispl.symphony.api.dal.dto.control.ControllableProperty;
import com.avispl.symphony.api.dal.dto.monitor.ExtendedStatistics;
import com.avispl.symphony.dal.avdevices.power.gude.GudePDU8045Communicator;
import com.avispl.symphony.dal.avdevices.power.gude.utils.DeviceConstant;
import com.avispl.symphony.dal.avdevices.power.gude.utils.DeviceInfoMetric;
import com.avispl.symphony.dal.avdevices.power.gude.utils.monitoring.DevicesMetricGroup;

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
		communicator.setHost("172.16.251.101");
		communicator.setPort(80);
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
	 * Test CameraPanasonicAWUE150Communicator.getMultipleStatistics successful with valid username password
	 * Expected retrieve valid device monitoring data
	 */
	@Test
	void testGudePDUCommunicatorGetMonitoringDataSuccessful() throws Exception {
		ExtendedStatistics statistics = (ExtendedStatistics) communicator.getMultipleStatistics().get(0);
		communicator.getMultipleStatistics();
		communicator.getMultipleStatistics();
		communicator.getMultipleStatistics();
		communicator.getMultipleStatistics();
		communicator.getMultipleStatistics();
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

		String propertyName = DevicesMetricGroup.SENSOR.getName() + "7106" + DeviceConstant.HASH + "AdvanceMonitoring";
		String propertyValue = "1";
		controllableProperty.setProperty(propertyName);
		controllableProperty.setValue(propertyValue);
		communicator.controlProperty(controllableProperty);

		Assertions.assertEquals(propertyValue, stats.get(propertyName));
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

		Assertions.assertEquals("false", stats.get("PowerPort01#Edited"));
	}

	/**
	 * Test GudePDU8045Communicator.controlProperty advance monitoring control
	 *
	 * Expected: control successfully
	 */
	@Test
	void testPowerPortControlInBatch() throws Exception {
		ExtendedStatistics statistics = (ExtendedStatistics) communicator.getMultipleStatistics().get(0);
		Map<String, String> stats = statistics.getStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();

		String propertyName = "PowerPort01" + DeviceConstant.HASH + "PowerPort";
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
}