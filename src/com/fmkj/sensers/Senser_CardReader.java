package com.fmkj.sensers;
import com.fmkj.values.SenserType;

public class Senser_CardReader extends Senser{
	public Senser_CardReader(byte[] senserData,String gatewayIp) {
		SenserName = SenserType.SENSERTYPE_CARDREADER;
		initUniversalData(senserData);
}
}
