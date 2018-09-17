package com.fmkj.sensers;

import com.fmkj.values.SenserType;

public class Senser_PekingPower extends Senser{
	public Senser_PekingPower(byte[] senserData,String gatewayIp) {
		SenserName = SenserType.SENSERTYPE_PKPOWER;
		initUniversalData(senserData);
		switch (senserData[2]&0x01){
	            case 0x00:
	          	  listenValue ="交流";
	                break;
	                
	            case 0x01:
	          	  listenValue ="直流";
	                break;
	        }
}
}
