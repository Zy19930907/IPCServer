package com.fmkj.sensers;

import com.fmkj.values.SenserType;

public class Senser_IPD_ZJM extends Senser{
	public Senser_IPD_ZJM(byte[] senserData,String gatewayIp) {
		SenserName = SenserType.SENSERTYPE_IPD_ZJM;
		initUniversalData(senserData);
}
}
