package com.avispl.symphony.dal.avdevices.power.gude;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avispl.symphony.api.dal.control.Controller;
import com.avispl.symphony.api.dal.dto.control.ControllableProperty;
import com.avispl.symphony.api.dal.dto.monitor.ExtendedStatistics;
import com.avispl.symphony.api.dal.dto.monitor.Statistics;
import com.avispl.symphony.api.dal.monitor.Monitorable;
import com.avispl.symphony.dal.communicator.RestCommunicator;

/**
 *
 * GudePDU8045Communicator
 * An implementation of RestCommunicator to provide communication and interaction with Gude PDU 8045
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 14/12/2022
 * @since 1.0.0
 */
public class GudePDU8045Communicator extends RestCommunicator implements Monitorable, Controller {

	@Override
	public List<Statistics> getMultipleStatistics() throws Exception {
		Map<String, String> stats = new HashMap<>();
		ExtendedStatistics extendedStatistics = new ExtendedStatistics();
		stats.put("Properties1", "Value1");
		extendedStatistics.setStatistics(stats);
		return Collections.singletonList(extendedStatistics);
	}

	@Override
	public void controlProperty(ControllableProperty controllableProperty) throws Exception {
		//ToDo:
	}

	@Override
	public void controlProperties(List<ControllableProperty> list) throws Exception {
		//ToDo:
	}

	@Override
	protected void authenticate() throws Exception {
		//ToDo:
	}
}