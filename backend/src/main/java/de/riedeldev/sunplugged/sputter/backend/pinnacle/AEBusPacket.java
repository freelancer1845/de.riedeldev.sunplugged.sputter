package de.riedeldev.sunplugged.sputter.backend.pinnacle;

import java.nio.ByteBuffer;

import lombok.Getter;

@Getter
public class AEBusPacket {

	private final int address;

	private final byte command;

	private final byte[] data;

	public AEBusPacket(int address, byte command, byte[] data) {
		if (data.length > 255) {
			throw new IllegalArgumentException("Maximum allowed data bytes is 255, instead got " + data.length);
		}
		this.address = address;
		this.command = command;
		this.data = data;
	}

	private ByteBuffer buffer = null;

	public byte[] toBytePacket() {
		if (buffer != null) {
			return buffer.array();
		}
		int length = 2; // Header and Command
		if (data.length > 6) {
			length += 1; // Optional Length Byte
		}
		length += data.length; // Data length
		length += 1; // CRC Byte

		buffer = ByteBuffer.allocate(length);

		byte header = (byte) address;
		header = (byte) (header << 3);
		if (data.length <= 7) {
			header = (byte) (header | (byte) data.length);
		} else {
			header = (byte) (header | (byte) 7);
		}
		buffer.put(header);

		byte crc = header;

		buffer.put(command);

		crc = (byte) (crc ^ command);
		if (data.length > 6) {
			buffer.put((byte) data.length);
			crc = (byte) (crc ^ data.length);
		}
		buffer.put(data);
		for (int i = 0; i < data.length; i++) {
			crc = (byte) (crc ^ data[i]);
		}
		buffer.put(crc);
		return buffer.array();
	}

}
