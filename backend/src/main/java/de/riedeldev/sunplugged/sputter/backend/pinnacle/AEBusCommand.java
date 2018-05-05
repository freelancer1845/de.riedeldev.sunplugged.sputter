package de.riedeldev.sunplugged.sputter.backend.pinnacle;

import java.io.IOException;
import java.util.function.Consumer;

import de.riedeldev.sunplugged.sputter.backend.core.hardware.CustomSerialPort;

public class AEBusCommand {

	private final byte command;

	private final int address;

	private final byte[] data;

	public final static byte ACK = 6;

	public final static byte NCK = 21;

	private AEBusPacket response = null;

	private Consumer<byte[]> callback;

	public AEBusCommand(byte command, int address, byte[] data, Consumer<byte[]> callBack) {
		this.command = command;
		this.address = address;
		this.data = data;
		this.callback = callBack;

	}

	public void execute(CustomSerialPort port) throws AEException {
		if (port.isOpen() == false) {
			throw new AEException("Port not open.");
		}

		sendCommand(port);

	}

	private void sendCommand(CustomSerialPort port) throws AEException {
		AEBusPacket packet = new AEBusPacket(address, command, data);
		byte[] buffer = packet.toBytePacket();
		port.writeBytes(buffer, buffer.length);
		byte[] ack = new byte[1];
		port.readBytes(ack, 1);
		if (ack[0] == NCK) {
			throw new AEException("Recieved NCK");
		} else if (ack[0] == ACK) {

			byte[] header = new byte[1];

			port.readBytes(header, 1);

			AEBusPacketBuilder builder = AEBusPacketBuilder.buildFromHeader(header[0]);

			byte[] singleBuffer = new byte[1];
			while (builder.isFinished() == false) {
				port.readBytes(singleBuffer, 1);
				try {
					builder.addByte(singleBuffer[0]);
				} catch (IOException e) {
					throw new AEException("Error in reading Response.");
				}
			}

			response = builder.getPacket();
			if (callback != null) {
				callback.accept(response.getData());
			}
		} else {
			throw new AEException("Unexpected response. Expected a ACK or NCK. " + ack[0]);
		}

	}

	public AEBusPacket getResponse() {
		return response;
	}

}
