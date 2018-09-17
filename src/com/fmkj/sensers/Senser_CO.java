package com.fmkj.sensers;
import com.fmkj.values.SenserType;

public class Senser_CO extends Senser{
	public Senser_CO(byte[] senserData,String gatewayIp) {
		SenserName = SenserType.SENSERTYPE_CO;
		initUniversalData(senserData);
		getConcentration(senserData,"ppm");
}
}
