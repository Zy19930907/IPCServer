package com.fmkj.sensers;
import com.fmkj.tools.NomalDataSwitch;
import com.fmkj.values.SenserType;

public class Senser_Unknown extends Senser{
	public Senser_Unknown(byte[] senserData,String gatewayIp) {
		initUniversalData(senserData);
		if(senserData[1] == ((byte)0xFF))
			listenValue = "";
		else
		{	
			SenserName = SenserType.SENSERTYPE_UNKONOW;
			listenValue= "未定义设备，设备类型码："+ NomalDataSwitch.byteToHexString(senserData[1]);
		}
}
}
