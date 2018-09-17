package com.feimo.sendobjects;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GatewayData implements Serializable{
	private byte data[];
	private String targetIP;
	public String getTarPcName() {
		return TarPcName;
	}

	public void setTarPcName(String tarPcName) {
		TarPcName = tarPcName;
	}

	private String TarPcName;

	public String getTargetIP() {
		return targetIP;
	}

	public void setTargetIP(String targetIP) {
		this.targetIP = targetIP;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}
