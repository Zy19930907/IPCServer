package com.feimo.sendobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class LocalAckGatewayInfo implements Serializable{
	private String targetPhone;
	private List<byte[]> gatewayInfos = new ArrayList<byte[]>();
	
	public String getTargetPhone() {
		return targetPhone;
	}

	public void setTargetPhone(String targetPhone) {
		this.targetPhone = targetPhone;
	}

	public List<byte[]> getGatewayInfos() {
		return gatewayInfos;
	}

	public void setGatewayInfos(List<byte[]> gatewayInfos) {
		this.gatewayInfos = gatewayInfos;
	}
}
