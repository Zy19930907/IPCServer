package com.fmkj.sensers;
import com.fmkj.values.SenserType;

public class Senser_NativePre extends Senser{
	public Senser_NativePre(byte[] senserData,String gatewayIp) {
		SenserName = SenserType.SENSERTYPE_NATIVEPRE;
		initUniversalData(senserData);
		getConcentration(senserData,"KPa");
}
}
