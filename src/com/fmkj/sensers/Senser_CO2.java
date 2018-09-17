package com.fmkj.sensers;
import com.fmkj.values.SenserType;

public class Senser_CO2 extends Senser{
	public Senser_CO2(byte[] senserData,String gatewayIp) {
		SenserName = SenserType.SENSERTYPE_CO2;
		initUniversalData(senserData);
		getConcentration(senserData,"%");
}
}
