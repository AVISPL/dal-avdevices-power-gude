# Gude PDU Integration - Capabilities & Configuration
This document covers Gude PDU Device Adapter Capabilities and Configuration.

Symphony integrates with Gude PDU (Power Distribution Units) to provide monitoring and control of networked power distribution. The adapter communicates directly with the Gude PDU over HTTPS and exposes per-port power control, energy metering, environmental sensor readings, and watchdog configuration.

Main features are: per-outlet power switching and reset, energy metering per port, environmental sensor monitoring, and configurable watchdog management.

## Gude PDU - Main use cases
- **Monitor** power port statuses, energy metrics (voltage, current, active/apparent/reactive power, frequency), and energy counters (kWh)
- **Control** individual power port states (On, Off, Reset, Batch switching) and port configuration (labels, startup delays, watchdog settings)
- **Track** environmental conditions via connected sensors — temperature, humidity, and dew point
- **Inventory** PDU firmware version, device name, and port-level details

## Gude PDU - Supported Models
- 8045 series: 8045-1, 8045-2
- 8031 series: 8031-1, 8031-2, 8031-3, 8031-4

**Note:** PowerPort per-port monitoring (energy metering) is not supported on 8031 series models.

## Gude PDU Device Configuration

The Gude PDU Adapter communicates with the device over HTTPS using the device's built-in credentials. Username and password can be left blank if password protection is disabled on the PDU.

| Field | Description |
|---|---|
| Device Type | AV Devices |
| Category | Power |
| Manufacturer | Gude |
| Model | e.g., 8045-1 |
| Monitoring Service | Advanced Monitoring |
| Monitoring Source | Direct |
| Management Address | IP address of the Gude PDU |
| Protocol | HTTPS |
| Username | PDU username (blank if auth disabled) |
| Password | PDU password (blank if auth disabled) |
| Port Number | 443 |

### Gude PDU - Adapter configuration properties

| Property | Description |
|---|---|
| configManagement | Enables Power Port configuration controls (labels, startup delays, watchdog). `false` by default — configuration properties are hidden until enabled. |
| historicalProperties | Comma-separated list of properties to track historically. Valid values: `Current(A)`, `PowerActive(W)`, `PowerReactive(VAR)`, `PowerApparent(VA)`, `Temperature(C)`, `Humidity(%)`, `DewPoint(C)`. Default: blank (no historical tracking). |

For detailed information on the adapter and its configuration, please refer to our knowledgebase -> https://symphony.knowledgeowl.com/help/gude-pdu

## Gude PDU - Available Monitored Data

| Property Group | Description |
|---|---|
| Device Info | Device name, firmware version, and overall status |
| PowerPort(ID) | Per-port status (On/Off), energy metering: Voltage(V), Current(A), Frequency(Hz), PowerActive(W), PowerApparent(VA), PowerReactive(VAR), energy counters (kWh). Not available on 8031 models. |
| SensorPort(ID) | Environmental readings from connected sensors: Temperature(C), Humidity(%), DewPoint(C) |
| BuzzerStatus | PDU buzzer on/off state (via SNMP property) |

Historical/graphable properties (when configured via `historicalProperties`): temperature, humidity, dew point, active power, apparent power, current, reactive power. Graphs are supported for SensorPort01 and MeterL1 only.

## Gude PDU - Control Capabilities

**Power Port Control** (always available):
- Turn individual ports On or Off
- Reset a port (off → wait reset duration → on)
- Batch switching — switch ports on/off in sequence
- PowerPortAllOn / PowerPortAllOff — bulk actions (hidden when already in that state)

**Power Port Configuration** (requires `configManagement = true`):
- Port label, initialization delay, repower delay, reset duration
- Watchdog settings: mode, ping type, TCP port (1–65535), ping interval (1–255), ping retries (0–255)

Configuration changes show an `Edited` flag and require `ApplyChanges` to commit or `CancelChanges` to revert.

## Gude PDU - Troubleshooting

**Link Error / Ping Timeout / Connection Refused**
- Verify the Management Address is the correct IP of the Gude PDU
- Confirm the Protocol is set to HTTPS and Port is 443

**Login / Connection Error**
- Confirm the Protocol is set to HTTPS and Port is 443
- If password protection is disabled on the PDU, leave Username and Password blank
- Ensure the Cloud Connector can reach the PDU IP on port 443

**API Error**
- Verify the Management Address is the correct IP of the Gude PDU
- Confirm the Protocol is set to HTTPS and Port is 443
- Confirm the PDU model to be in the list of supported ones

**No PowerPort Data**
- Confirm the PDU model — PowerPort monitoring is not supported on 8031 series
- Verify the Cloud Connector has network access to the device

**Configuration Properties Not Visible**
- Set `configManagement` adapter property to `true` to expose Power Port configuration controls

**Historical Properties Not Graphing**
- Confirm property names in `historicalProperties` exactly match the supported shortlist values (case-sensitive)
- Note: historical graphs are only supported for SensorPort01 and MeterL1

If none of the recommended steps help, please enter an SOS ticket at {https://avi-spl.atlassian.net/servicedesk/customer/portals}

## Gude PDU - What AI Assistant can do with it:
- Find Gude PDU devices (AV Devices | Power | Gude) in Symphony
- Verify Gude PDU adapter configuration and property settings
- Report on power port statuses, energy metering values, and sensor readings

## Gude PDU - What AI Assistant cannot do with it:
- Provision devices
- Configure the PDU's internal network or SNMP settings directly
- Enable or disable password protection on the device itself
