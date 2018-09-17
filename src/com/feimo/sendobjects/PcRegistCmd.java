package com.feimo.sendobjects;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PcRegistCmd implements Serializable{
	private String PcName = null;

	public String getPcName() {
		return PcName;
	}

	public void setPcName(String pcName) {
		PcName = pcName;
	}
	
}
