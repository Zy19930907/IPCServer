package com.fmkj.sensers;
import com.fmkj.values.SenserType;

public class Senser_Dust extends Senser{
	public Senser_Dust(byte[] senserData,String gatewayIp){
		SenserName = SenserType.SENSERTYPE_Dust;
		initUniversalData(senserData);
		getConcentration(senserData,"mg/m³");	
}
}
