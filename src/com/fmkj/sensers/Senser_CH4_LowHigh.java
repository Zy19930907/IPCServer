package com.fmkj.sensers;
import com.fmkj.values.SenserType;

public class Senser_CH4_LowHigh extends Senser{
	public Senser_CH4_LowHigh(byte[] senserData,String gatewayIp) {
		SenserName = SenserType.SENSERTYPE_CH4HIGHLOW;
		initUniversalData(senserData);
		getConcentration(senserData,"%");
}
}
