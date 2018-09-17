package com.fmkj.sensers;
import com.fmkj.values.SenserType;

public class Senser_TempHumi extends Senser{
	public Senser_TempHumi(byte[] senserData,String gatewayIp) {
		SenserName = SenserType.SENSERTYPE_TEMPHUMI;
		initUniversalData(senserData);
		getConcentration(senserData,"°C");
}
}
