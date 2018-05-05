package de.riedeldev.sunplugged.sputter.backend.pinnacle;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class AEBusPacketBuilder {

	private int currentByte = 0;

	private byte header;

	private int address;

	private int dataLength = 0;

	private byte dataLengthByte;

	private boolean optionalLength = false;

	private byte command;

	private ByteBuffer data;

	private boolean finished = false;

	private AEBusPacket packet = null;

	public static AEBusPacketBuilder buildFromBytes(byte[] data) throws IOException {
		return buildFromHeader(data[0]).addAllBytes(Arrays.copyOfRange(data, 1, data.length));
	}

	public static AEBusPacketBuilder buildFromHeader(byte header) {
		int address = (header >> 3) & (31);
		int dataLength = header & (7);

		AEBusPacketBuilder builder = new AEBusPacketBuilder();
		builder.header = header;
		builder.address = address;
		builder.dataLength = dataLength;
		if (dataLength != 7) {
			builder.data = ByteBuffer.allocate(dataLength);
		} else {
			builder.optionalLength = true;
		}
		builder.currentByte++;
		return builder;
	}

	public AEBusPacket getPacket() {
		if (finished == false) {
			throw new IllegalStateException("AEBusPacketBuilder is not closed.");
		}
		return packet;
	}

	public AEBusPacketBuilder addAllBytes(byte[] bytes) throws IOException {
		for (int i = 0; i < bytes.length; i++) {
			addByte(bytes[i]);
		}
		return this;
	}

	public AEBusPacketBuilder addByte(byte nextByte) throws IOException {
		if (finished) {
			throw new IllegalStateException("AEBusPacketBuilder is closed.");
		}
		if (currentByte == 1) {
			addCommand(nextByte);
		} else if (currentByte == 2 && optionalLength == true) {
			addOptionalLengthByte(nextByte);
		} else {
			if (data.hasRemaining()) {
				addData(nextByte);
			} else {
				packet = addCRCByte(nextByte);
				finished = true;
			}
		}
		currentByte++;
		return this;
	}

	private void addCommand(byte command) {
		this.command = command;
	}

	public int getDataLength() {
		return dataLength;
	}

	private void addOptionalLengthByte(byte optionalLength) {
		dataLength = Byte.toUnsignedInt(optionalLength);
		dataLengthByte = optionalLength;
		this.data = ByteBuffer.allocate(dataLength);
	}

	private void addData(byte data) throws BufferOverflowException {
		this.data.put(data);
	}

	private AEBusPacket addCRCByte(byte crc) throws IOException {
		byte crcResult = (byte) (header ^ command);
		if (optionalLength) {
			crcResult = (byte) (crcResult ^ dataLengthByte);
		}
		if (dataLength > 0) {
			byte[] dataArray = data.array();
			for (int i = 0; i < dataArray.length; i++) {
				crcResult = (byte) (crcResult ^ dataArray[i]);
			}
		}
		crcResult = (byte) (crcResult ^ crc);

		if (crcResult != 0) {
			throw new IOException("CRC Failed.");
		} else {
			return new AEBusPacket(address, command, data.array());
		}

	}

	public boolean isFinished() {
		return finished;
	}
}
