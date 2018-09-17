package com.fmkj.sensers;
import com.fmkj.values.SenserType;

public class Senser_CH4_Low extends Senser{
	public Senser_CH4_Low(byte[] senserData,String gatewayIp) {
		SenserName = SenserType.SENSERTYPE_CH4LOW;
		initUniversalData(senserData);
		getConcentration(senserData,"%");
		}
}
