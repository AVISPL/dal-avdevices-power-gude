package com.avispl.symphony.dal.avdevices.power.gude.dto.monitoring.powerport;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.avispl.symphony.dal.avdevices.power.gude.utils.DeviceConstant;
import com.avispl.symphony.dal.avdevices.power.gude.utils.controlling.ColdStart;
import com.avispl.symphony.dal.avdevices.power.gude.utils.controlling.OnOffStatus;
import com.avispl.symphony.dal.avdevices.power.gude.utils.controlling.WatchDogMode;
import com.avispl.symphony.dal.avdevices.power.gude.utils.controlling.WatchdogIPMasterSlavePort;
import com.avispl.symphony.dal.avdevices.power.gude.utils.controlling.WatchdogPingType;
import com.avispl.symphony.dal.avdevices.power.gude.utils.controlling.WatchdogResetPortWhenHostDownMode;

/**
 * Power port element configuration data
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 14/01/2023
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PowerPortComponentConfig {

	private WatchdogPingType watchdogPingType;
	private OnOffStatus countPingRequest;
	private WatchdogResetPortWhenHostDownMode watchdogResetPortWhenHostDownMode;
	private WatchdogIPMasterSlavePort watchdogIPMasterSlavePort;
	private WatchDogMode watchDogMode;
	private ColdStart coldStart;
	protected final Log logger = LogFactory.getLog(this.getClass());
	private String newName;

	@JsonAlias()
	private String name;

	@JsonAlias("powup")
	private int powerUp;

	@JsonAlias( { "powrestore", "powerem" })
	private int powerRemember;

	@JsonAlias("stickylogical")
	private int stickyLogical;

	@JsonAlias("powupdelay")
	private int powerUpDelay;

	@JsonAlias("repowdelay")
	private int repowerDelay;

	@JsonAlias("reset")
	private int reset;

	@JsonAlias("wdog")
	private int watchDog;

	@JsonAlias("wtype")
	private int watchDogType;

	@JsonAlias("whost")
	private String watchDogHost;

	@JsonAlias("wport")
	private int watchDogPort;

	@JsonAlias("wretry")
	private int watchDogRetry;

	@JsonAlias("winterval")
	private int watchDogInterval;

	@JsonAlias("wrbx")
	private int watchDogRbx;

	/**
	 * Retrieves {@link #watchdogPingType}
	 *
	 * @return value of {@link #watchdogPingType}
	 */
	public WatchdogPingType getWatchdogPingType() {
		return watchdogPingType;
	}

	/**
	 * Sets {@link #watchdogPingType} value
	 *
	 * @param watchdogPingType new value of {@link #watchdogPingType}
	 */
	public void setWatchdogPingType(WatchdogPingType watchdogPingType) {
		this.watchdogPingType = watchdogPingType;
	}

	/**
	 * Retrieves {@link #countPingRequest}
	 *
	 * @return value of {@link #countPingRequest}
	 */
	public OnOffStatus getCountPingRequest() {
		return countPingRequest;
	}

	/**
	 * Sets {@link #countPingRequest} value
	 *
	 * @param countPingRequest new value of {@link #countPingRequest}
	 */
	public void setCountPingRequest(OnOffStatus countPingRequest) {
		this.countPingRequest = countPingRequest;
	}

	/**
	 * Retrieves {@link #watchdogResetPortWhenHostDownMode}
	 *
	 * @return value of {@link #watchdogResetPortWhenHostDownMode}
	 */
	public WatchdogResetPortWhenHostDownMode getWatchdogResetPortWhenHostDownMode() {
		return watchdogResetPortWhenHostDownMode;
	}

	/**
	 * Sets {@link #watchdogResetPortWhenHostDownMode} value
	 *
	 * @param watchdogResetPortWhenHostDownMode new value of {@link #watchdogResetPortWhenHostDownMode}
	 */
	public void setWatchdogResetPortWhenHostDownMode(WatchdogResetPortWhenHostDownMode watchdogResetPortWhenHostDownMode) {
		this.watchdogResetPortWhenHostDownMode = watchdogResetPortWhenHostDownMode;
	}

	/**
	 * Retrieves {@link #watchDogMode}
	 *
	 * @return value of {@link #watchDogMode}
	 */
	public WatchDogMode getWatchDogMode() {
		return watchDogMode;
	}

	/**
	 * Sets {@link #watchDogMode} value
	 *
	 * @param watchDogMode new value of {@link #watchDogMode}
	 */
	public void setWatchDogMode(WatchDogMode watchDogMode) {
		this.watchDogMode = watchDogMode;
	}

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
	 * Retrieves {@link #powerUp}
	 *
	 * @return value of {@link #powerUp}
	 */
	public int getPowerUp() {
		return powerUp;
	}

	/**
	 * Sets {@link #powerUp} value
	 *
	 * @param powerUp new value of {@link #powerUp}
	 */
	public void setPowerUp(int powerUp) {
		this.powerUp = powerUp;
	}

	/**
	 * Retrieves {@link #stickyLogical}
	 *
	 * @return value of {@link #stickyLogical}
	 */
	public int getStickyLogical() {
		return stickyLogical;
	}

	/**
	 * Sets {@link #stickyLogical} value
	 *
	 * @param stickyLogical new value of {@link #stickyLogical}
	 */
	public void setStickyLogical(int stickyLogical) {
		this.stickyLogical = stickyLogical;
	}

	/**
	 * Retrieves {@link #powerUpDelay}
	 *
	 * @return value of {@link #powerUpDelay}
	 */
	public int getPowerUpDelay() {
		return powerUpDelay;
	}

	/**
	 * Sets {@link #powerUpDelay} value
	 *
	 * @param powerUpDelay new value of {@link #powerUpDelay}
	 */
	public void setPowerUpDelay(int powerUpDelay) {
		this.powerUpDelay = powerUpDelay;
	}

	/**
	 * Retrieves {@link #repowerDelay}
	 *
	 * @return value of {@link #repowerDelay}
	 */
	public int getRepowerDelay() {
		return repowerDelay;
	}

	/**
	 * Sets {@link #repowerDelay} value
	 *
	 * @param repowerDelay new value of {@link #repowerDelay}
	 */
	public void setRepowerDelay(int repowerDelay) {
		this.repowerDelay = repowerDelay;
	}

	/**
	 * Retrieves {@link #reset}
	 *
	 * @return value of {@link #reset}
	 */
	public int getReset() {
		return reset;
	}

	/**
	 * Sets {@link #reset} value
	 *
	 * @param reset new value of {@link #reset}
	 */
	public void setReset(int reset) {
		this.reset = reset;
	}

	/**
	 * Retrieves {@link #watchDog}
	 *
	 * @return value of {@link #watchDog}
	 */
	public int getWatchDog() {
		return watchDog;
	}

	/**
	 * Sets {@link #watchDog} value
	 *
	 * @param watchDog new value of {@link #watchDog}
	 */
	public void setWatchDog(int watchDog) {
		this.watchDog = watchDog;
	}

	/**
	 * Retrieves {@link #watchDogType}
	 *
	 * @return value of {@link #watchDogType}
	 */
	public int getWatchDogType() {
		return watchDogType;
	}

	/**
	 * Sets {@link #watchDogType} value
	 *
	 * @param watchDogType new value of {@link #watchDogType}
	 */
	public void setWatchDogType(int watchDogType) {
		this.watchDogType = watchDogType;
	}

	/**
	 * Retrieves {@link #watchDogHost}
	 *
	 * @return value of {@link #watchDogHost}
	 */
	public String getWatchDogHost() {
		return watchDogHost;
	}

	/**
	 * Sets {@link #watchDogHost} value
	 *
	 * @param watchDogHost new value of {@link #watchDogHost}
	 */
	public void setWatchDogHost(String watchDogHost) {
		this.watchDogHost = watchDogHost;
	}

	/**
	 * Retrieves {@link #watchDogPort}
	 *
	 * @return value of {@link #watchDogPort}
	 */
	public int getWatchDogPort() {
		return watchDogPort;
	}

	/**
	 * Sets {@link #watchDogPort} value
	 *
	 * @param watchDogPort new value of {@link #watchDogPort}
	 */
	public void setWatchDogPort(int watchDogPort) {
		this.watchDogPort = watchDogPort;
	}

	/**
	 * Retrieves {@link #watchDogRetry}
	 *
	 * @return value of {@link #watchDogRetry}
	 */
	public int getWatchDogRetry() {
		return watchDogRetry;
	}

	/**
	 * Sets {@link #watchDogRetry} value
	 *
	 * @param watchDogRetry new value of {@link #watchDogRetry}
	 */
	public void setWatchDogRetry(int watchDogRetry) {
		this.watchDogRetry = watchDogRetry;
	}

	/**
	 * Retrieves {@link #watchDogInterval}
	 *
	 * @return value of {@link #watchDogInterval}
	 */
	public int getWatchDogInterval() {
		return watchDogInterval;
	}

	/**
	 * Sets {@link #watchDogInterval} value
	 *
	 * @param watchDogInterval new value of {@link #watchDogInterval}
	 */
	public void setWatchDogInterval(int watchDogInterval) {
		this.watchDogInterval = watchDogInterval;
	}

	/**
	 * Retrieves {@link #watchDogRbx}
	 *
	 * @return value of {@link #watchDogRbx}
	 */
	public int getWatchDogRbx() {
		return watchDogRbx;
	}

	/**
	 * Sets {@link #watchDogRbx} value
	 *
	 * @param watchDogRbx new value of {@link #watchDogRbx}
	 */
	public void setWatchDogRbx(int watchDogRbx) {
		this.watchDogRbx = watchDogRbx;
	}

	/**
	 * Retrieves {@link #coldStart}
	 *
	 * @return value of {@link #coldStart}
	 */
	public ColdStart getColdStart() {
		return coldStart;
	}

	/**
	 * Sets {@link #coldStart} value
	 *
	 * @param coldStart new value of {@link #coldStart}
	 */
	public void setColdStart(ColdStart coldStart) {
		this.coldStart = coldStart;
	}

	/**
	 * Retrieves {@link #powerRemember}
	 *
	 * @return value of {@link #powerRemember}
	 */
	public int getPowerRemember() {
		return powerRemember;
	}

	/**
	 * Sets {@link #powerRemember} value
	 *
	 * @param powerRemember new value of {@link #powerRemember}
	 */
	public void setPowerRemember(int powerRemember) {
		this.powerRemember = powerRemember;
	}

	/**
	 * Retrieves {@link #newName}
	 *
	 * @return value of {@link #newName}
	 */
	public String getNewName() {
		return newName;
	}

	/**
	 * Sets {@link #newName} value
	 *
	 * @param newName new value of {@link #newName}
	 */
	public void setNewName(String newName) {
		this.newName = newName;
	}

	/**
	 * Retrieves {@link #watchdogIPMasterSlavePort}
	 *
	 * @return value of {@link #watchdogIPMasterSlavePort}
	 */
	public WatchdogIPMasterSlavePort getWatchdogIPMasterSlavePort() {
		return watchdogIPMasterSlavePort;
	}

	/**
	 * Sets {@link #watchdogIPMasterSlavePort} value
	 *
	 * @param watchdogIPMasterSlavePort new value of {@link #watchdogIPMasterSlavePort}
	 */
	public void setWatchdogIPMasterSlavePort(WatchdogIPMasterSlavePort watchdogIPMasterSlavePort) {
		this.watchdogIPMasterSlavePort = watchdogIPMasterSlavePort;
	}

	/**
	 * Map watchdog type to local dto base on bitwise operator
	 */
	public void mapWatchdogTypeToDTO() {
		if ((watchDogType & DeviceConstant.WATCHDOG_ICMP) == DeviceConstant.WATCHDOG_ICMP) {
			watchdogPingType = WatchdogPingType.ICMP;
		}
		if ((watchDogType & DeviceConstant.WATCHDOG_TCP) == DeviceConstant.WATCHDOG_TCP) {
			watchdogPingType = WatchdogPingType.TCP;
		}
		if ((watchDogType & DeviceConstant.RESET_PORT_ENABLED) == DeviceConstant.RESET_PORT_ENABLED) {
			watchDogMode = WatchDogMode.RESET_PORT_WHEN_HOST_DOWN;
		}
		if ((watchDogType & DeviceConstant.INFINITE_WAIT) == DeviceConstant.INFINITE_WAIT) {
			watchDogMode = WatchDogMode.RESET_PORT_WHEN_HOST_DOWN;
			watchdogResetPortWhenHostDownMode = WatchdogResetPortWhenHostDownMode.INFINITE_WAIT;
		}
		if ((watchDogType & DeviceConstant.REPEAT_RESET) == DeviceConstant.REPEAT_RESET) {
			watchdogResetPortWhenHostDownMode = WatchdogResetPortWhenHostDownMode.REPEAT_RESET;
		}
		if ((watchDogType & DeviceConstant.SWITCH_OFF_ONCE) == DeviceConstant.SWITCH_OFF_ONCE) {
			watchDogMode = WatchDogMode.SWITCH_OFF_ONCE;
		}
		if ((watchDogType & DeviceConstant.SWITCH_WHEN_HOST_UP) == DeviceConstant.SWITCH_WHEN_HOST_UP) {
			watchDogMode = WatchDogMode.IP_MASTER_SLAVE_PORT;
			watchdogIPMasterSlavePort = WatchdogIPMasterSlavePort.HOST_COME_UP;
		}
		if ((watchDogType & DeviceConstant.SWITCH_WHEN_HOST_DOWN) == DeviceConstant.SWITCH_WHEN_HOST_DOWN) {
			watchDogMode = WatchDogMode.IP_MASTER_SLAVE_PORT;
			watchdogIPMasterSlavePort = WatchdogIPMasterSlavePort.HOST_GOES_DOWN;
		}
		if ((watchDogType & DeviceConstant.COUNT_PING_REQUESTS) == DeviceConstant.COUNT_PING_REQUESTS) {
			countPingRequest = OnOffStatus.ON;
		} else {
			countPingRequest = OnOffStatus.OFF;
		}

		coldStart = ColdStart.OFF;
		if (powerUp == Integer.parseInt(ColdStart.ON.getApiName())) {
			coldStart = ColdStart.ON;
		}
		if (powerRemember == Integer.parseInt(ColdStart.REMEMBER_LAST_STATE.getApiName())) {
			coldStart = ColdStart.REMEMBER_LAST_STATE;
		}
	}


	/**
	 * Contribute output control request
	 *
	 * @return String request
	 */
	public String contributePowerPortConfigRequest(String portNumber) {
		newName = Optional.ofNullable(newName).orElse(name);

		if (watchdogPingType.equals(WatchdogPingType.TCP)) {
			watchDogType = 1;
		} else {
			watchDogType = 0;
		}
		if (watchdogResetPortWhenHostDownMode != null && watchdogResetPortWhenHostDownMode == WatchdogResetPortWhenHostDownMode.REPEAT_RESET) {
			watchDogType += DeviceConstant.REPEAT_RESET;
		}
		if (OnOffStatus.ON.equals(countPingRequest)) {
			watchDogType += DeviceConstant.COUNT_PING_REQUESTS;
		}

		switch (watchDogMode) {
			case RESET_PORT_WHEN_HOST_DOWN:
				watchDogType += DeviceConstant.RESET_PORT_ENABLED;
				break;
			case IP_MASTER_SLAVE_PORT:
				if (watchdogIPMasterSlavePort.equals(WatchdogIPMasterSlavePort.HOST_COME_UP)) {
					watchDogType += DeviceConstant.SWITCH_WHEN_HOST_UP;
				} else {
					watchDogType += DeviceConstant.SWITCH_WHEN_HOST_DOWN;
				}
				break;
			case SWITCH_OFF_ONCE:
				watchDogType += DeviceConstant.SWITCH_OFF_ONCE;
				break;
			default:
				logger.debug(String.format("un supported watchdog mode: %s", watchDogMode));
				break;
		}
		if (watchDog == 1) {
			if (watchdogPingType.equals(WatchdogPingType.TCP)) {
				return String.format("/cfgjsn.js?cmd=3&components=128&p=%s"
								+ "&name=%s"
								+ "&powup=%s"
								+ "&powrem=%s"
								+ "&idle=%s"
								+ "&on_again=%s"
								+ "&reset=%s"
								+ "&we=%s"
								+ "&wip=%s"
								+ "&wrbx=%s"
								+ "&wport=%s"
								+ "&wint=%s"
								+ "&wret=%s"
								+ "&wt=%s", portNumber, newName, powerUp, powerRemember, powerUpDelay, repowerDelay, reset, watchDog, watchDogHost, watchDogRbx, watchDogPort,
						watchDogInterval,
						watchDogRetry, watchDogType);
			} else {
				return String.format("/cfgjsn.js?cmd=3&components=128&p=%s"
								+ "&name=%s"
								+ "&powup=%s"
								+ "&powrem=%s"
								+ "&idle=%s"
								+ "&on_again=%s"
								+ "&reset=%s"
								+ "&we=%s"
								+ "&wip=%s"
								+ "&wrbx=%s"
								+ "&wint=%s"
								+ "&wret=%s"
								+ "&wt=%s", portNumber, newName, powerUp, powerRemember, powerUpDelay, repowerDelay, reset, watchDog, watchDogHost, watchDogRbx, watchDogInterval,
						watchDogRetry, watchDogType);
			}
		} else {
			return String.format("/cfgjsn.js?cmd=3&components=128&p=%s"
					+ "&name=%s"
					+ "&powup=%s"
					+ "&powrem=%s"
					+ "&idle=%s"
					+ "&on_again=%s"
					+ "&reset=%s"
					+ "&we=%s", portNumber, newName, powerUp, powerRemember, powerUpDelay, repowerDelay, reset, watchDog);
		}
	}
}