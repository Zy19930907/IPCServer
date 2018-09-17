package com.feimo.sendobjects;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LinkCmd implements Serializable {
	    private String PcIp;
	    private String GatewayIp;

	    public String getPcIp() {
	        return PcIp;
	    }

	    public void setPcIp(String pcIp) {
	        PcIp = pcIp;
	    }

	    public String getGatewayIp() {
	        return GatewayIp;
	    }

	    public void setGatewayIp(String gatewayIp) {
	        GatewayIp = gatewayIp;
	    }
}
