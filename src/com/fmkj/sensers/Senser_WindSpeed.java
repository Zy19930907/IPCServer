package com.fmkj.sensers;
import com.fmkj.values.SenserType;

public class Senser_WindSpeed extends Senser{
	public Senser_WindSpeed(byte[] senserData, String gatewayIp) {
		SenserName = SenserType.SENSERTYPE_WindSpeed;
		initUniversalData(senserData);
		getConcentration(senserData, "m/s");
	}
}
