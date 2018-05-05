package de.riedeldev.sunplugged.sputter.backend.core.hardware;

import com.fazecast.jSerialComm.SerialPort;

public class CustomSerialPort {

	private SerialPort port;

	public SerialPort getPort() {
		return port;
	}

	public CustomSerialPort(String name) throws SerialPortException {

		port = com.fazecast.jSerialComm.SerialPort.getCommPort(name);
		if (port.openPort() == false) {
			throw new SerialPortException("Failed to open commport with name: " + name);
		}
	}

	public void writeBytes(byte[] buffer, int bytesToRead) {
		port.writeBytes(buffer, bytesToRead);
	}

	public void readBytes(byte[] buffer, int bytesToRead) {
		port.readBytes(buffer, bytesToRead);
	}

	public boolean isOpen() {
		return port.isOpen();
	}

}
