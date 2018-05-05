package de.riedeldev.sunplugged.sputter.backend.evra;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.stream.IntStream;

import javax.xml.bind.DatatypeConverter;

public class EvraPackage {

	private static final byte STX = 0x02;

	private static final byte ETX = 0x03;

	public static final byte CR = 0x0D;

	private boolean analogResponse = false;

	private String message;

	public static EvraPackage parseFromData(byte[] packageData) throws IOException, EvraCRCException {
		return new EvraPackage(packageData);
	}

	private EvraPackage(byte[] packageData) throws IOException, EvraCRCException {
		parsePackage(packageData);
	}

	private void parsePackage(byte[] packageData) throws IOException, EvraCRCException {
		if (packageData[0] != STX) {
			throw new IOException("First byte was not STX");
		}

		if (checkCRC(packageData) == false) {
			throw new EvraCRCException("CRC Error");
		}

		StringBuilder text = new StringBuilder();

		for (int i = 1; i < packageData.length; i++) {
			if (packageData[i] == ETX) {
				break;
			}
			text.append((char) packageData[i]);
		}
		this.message = text.toString();
	}

	private boolean checkCRC(byte[] packageData) {
		String crcString = new String(Arrays.copyOfRange(packageData, packageData.length - 3, packageData.length - 1));

		byte crcByte = DatatypeConverter.parseHexBinary(crcString)[0];

		byte crcCalculated = 0;
		for (int i = 0; i < packageData.length - 3; i++) {
			crcCalculated += packageData[i];
		}

		if (crcCalculated == crcByte) {
			analogResponse = false;
			return true;
		}

		crcCalculated = 0;
		for (int i = 0; i < packageData.length - 4; i++) {
			crcCalculated += packageData[i];
		}

		if (crcCalculated == crcByte) {
			analogResponse = true;
			return true;
		}

		return false;
	}

	public static byte[] toBytes(String message) {
		byte[] text = message.getBytes(Charset.forName("ASCII"));

		int length = 1; // stx byte
		length += text.length;
		length++; // ETX byte
		length = length + 2; // SUM byte
		length++; // CR byte

		ByteBuffer buffer = ByteBuffer.allocate(length);
		buffer.put(STX);
		IntStream.range(0, text.length).forEach(idx -> buffer.put(text[idx]));
		buffer.put(ETX);

		int crc = 0;
		for (int i = 0; i < buffer.position(); i++) {
			crc += buffer.get(i);
		}
		byte crcCut = (byte) crc;
		String crcString = String.format("%02X", crcCut);
		try {
			buffer.put(crcString.getBytes("ASCII"));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("Ascii not supported!", e);
		}
		buffer.put(CR);
		return buffer.array();

	}

	public boolean isAnalogResponse() {
		return analogResponse;
	}

	public String getMessage() {
		return message;
	}

}
