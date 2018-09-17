package com.fmkj.sensers;
import com.fmkj.values.SenserType;

public class Senser_LiquidLevel extends Senser{
	public Senser_LiquidLevel(byte[] senserData,String gatewayIp) {
			SenserName = SenserType.SENSERTYPE_LIQUIDLEVEL;
			initUniversalData(senserData);
			getConcentration(senserData,"m");
}
}
