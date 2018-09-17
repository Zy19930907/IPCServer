package com.fmkj.sensers;
import com.fmkj.values.SenserType;

public class Senser_O2 extends Senser{
	public Senser_O2(byte[] senserData,String gatewayIp) {
		SenserName = SenserType.SENSERTYPE_O2;
		initUniversalData(senserData);
		getConcentration(senserData,"%");
}
}
