package com.fmkj.sensers;

import com.fmkj.tools.NomalDataSwitch;
import com.fmkj.values.SenserInfo;
import com.fmkj.values.SenserType;

public class Senser_BoardCast extends Senser {
	private float Battery;

	public Senser_BoardCast(byte[] senserData, String gatewayIp) {
		SenserName = SenserType.SENSERTYPE_BOARDCAST;
		initUniversalData(senserData);
		Battery = NomalDataSwitch.abs(senserData[3]);
		Battery /= 10;
		switch (senserData[2] & 0x01) {
		case 0x00:
			listenValue = SenserInfo.SENSERSTATE_VOICEWIRESTATE_IDLE;
			break;
		case 0x01:
			listenValue = SenserInfo.SENSERSTATE_VOICEWIRESTATE_BUSY;
			break;
		}
		switch (senserData[2] & 0x10) {
		case 0x10:
			listenValue =SenserInfo.SENSERSTATE_VOICEWIRESTATE_Error ;
			break;
		case 0x00:
			listenValue +=SenserInfo.SENSERSTATE_VOICEWIRESTATE_Nomal;
			break;
		}
		listenValue += (" 电池电压：" + String.valueOf(Battery) + "V");
	}
}
