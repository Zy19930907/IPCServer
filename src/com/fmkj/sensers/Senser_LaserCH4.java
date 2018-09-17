package com.fmkj.sensers;
import com.fmkj.values.SenserType;

public class Senser_LaserCH4 extends Senser{
	public Senser_LaserCH4(byte[] senserData,String gatewayIp) {
		SenserName = SenserType.SENSERTYPE_LASERCH4;
		initUniversalData(senserData);
		getConcentration(senserData,"%");
	}
}
