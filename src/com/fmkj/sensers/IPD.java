package com.fmkj.sensers;
import com.fmkj.Begin.Main;
import com.fmkj.tools.NomalDataSwitch;

public class IPD {
	private int[] IpdValues = new int[56];
	public String LightingCurrent=""; // 照明额定电流
	public String SignalCurrent=""; // 信号额定电流
	public String LightingBreak=""; // 照明速断倍数
	public String SignalBreak=""; // 信号速断倍数
	public String UnderVolValue=""; // 欠压定值
	public String UnderVolDelay=""; // 欠压延时
	public String OverVolValue=""; // 过压定值
	public String OverVolDelay=""; // 过压延时
	public String LeakageResistance=""; // 漏电电阻
	public String LeakageDelay=""; // 漏电延时
	public String CH4Delay=""; // 风电瓦斯延时
	public String DeviceAddr=""; // 设备地址
	public String Stanby0="";
	public String Stanby1="";
	public String Stanby2="";
	public String UAB="";
	public String UBC="";
	public String UCA="";
	public String LightingCurrentNow_A=""; // 当前照明电流A
	public String LightingCurrentNow_B=""; // 当前照明电流B
	public String SignalCurrentNow_B=""; // 当前信号电流B
	public String RG="";
	public String Stanby3="";
	public String Stanby4="";
	public String XB1="";
	public String KB1="";
	public String Stanby5="";
	public String Stanby6="";
	public String Stanby7="";
	public String SysDateYM=""; // 系统时间--年月
	public String SysDateDH=""; // 系统时间--日时
	public String SysDateMS=""; // 系统时间--分秒
	public String Event1=""; // 事件1代码
	public String Event1ActValue=""; // 事件1动作值
	public String Event1ActTimeYM=""; // 事件1动作时间--年月
	public String Event1ActTimeDH=""; // 事件1动作时间--日时
	public String Event1ActTimeMS=""; // 事件1动作时间--分秒
	public String Event2=""; // 事件2代码
	public String Event2ActValue=""; // 事件2动作值
	public String Event2ActTimeYM=""; // 事件2动作时间--年月
	public String Event2ActTimeDH=""; // 事件2动作时间--日时
	public String Event2ActTimeMS=""; // 事件2动作时间--分秒
	public String Event3=""; // 事件3代码
	public String Event3ActValue=""; // 事件3动作值
	public String Event3ActTimeYM=""; // 事件3动作时间--年月
	public String Event3ActTimeDH=""; // 事件3动作时间--日时
	public String Event3ActTimeMS=""; // 事件3动作时间--分秒
	public String Event4=""; // 事件4代码
	public String Event4ActValue=""; // 事件4动作值
	public String Event4ActTimeYM=""; // 事件4动作时间--年月
	public String Event4ActTimeDH=""; // 事件4动作时间--日时
	public String Event4ActTimeMS=""; // 事件4动作时间--分秒
	public String Event5=""; // 事件5代码
	public String Event5ActValue=""; // 事件5动作值
	public String Event5ActTimeYM=""; // 事件5动作时间--年月
	public String Event5ActTimeDH=""; // 事件5动作时间--日时
	public String Event5ActTimeMS=""; // 事件5动作时间--分秒
	public String[] IpdStatus = new String[39];

	private int getValue(byte valueH, byte valueL) {

		return (NomalDataSwitch.abs(valueH) << 8) | NomalDataSwitch.abs(valueL);
	}

	private String GetXB1() {
		String[] out = { "失压", "过压", "照明速断", "信号速断", "漏电闭锁", "漏电检测", "瓦斯", "----", "末端短路保护", "----", "----",
				"----", "----", "----", "照明超负荷", "信号超负荷" };
		return out[IpdValues[24]];
	}

	private String GetKB1() {
		String[] out = { "合位置", "照明速断测试", "----", "----", "----", "----", "----", "----", "----", "----",
				"----", "----", "----", "----", "----", "----" };
		return out[IpdValues[25]];
	}

	private String GetEvent(int eventCode) {
		String[] Events = { "失压", "过压", "照明速断", "信号速断", "漏电闭锁", "漏电检测", "漏电闭锁返回", "", "", "分到合", "合到分",
				"AB相末端短路", "BC相末端短路", "CA相末端短路", "照明超负荷", "信号超负荷" };
		if (eventCode == 0 || eventCode > 0x13)
			return "";
		return Events[eventCode];
	}

	public int BcdIntToHexInt(int bcd) {
		return (((((bcd >> 8) / 16 * 10) + ((bcd >> 8) % 16)) << 8)
				| (((bcd & 0x000000ff) / 16 * 10) + (bcd & 0x000000ff) % 16));
	}

	public void FreshIPD(byte[] ipdMsg) {
		int i = 0, j, k = 0;
		for (i = 0; i < 56; i++)
			IpdValues[i] = getValue(ipdMsg[2 * i], ipdMsg[2 * i + 1]);
		i = 0;
		LightingCurrent = String.valueOf(((double) IpdValues[0]) / 100) + "A";
		IpdStatus[i++] = LightingCurrent;
		SignalCurrent = String.valueOf((double) IpdValues[1] / 100) + "A";
		IpdStatus[i++] = SignalCurrent;
		LightingBreak = String.valueOf((double) IpdValues[2] / 100) + "倍";
		IpdStatus[i++] = LightingBreak;
		SignalBreak = String.valueOf((double) IpdValues[3] / 100) + "倍";
		IpdStatus[i++] = SignalBreak;
		UnderVolValue = String.valueOf((double) IpdValues[4] / 1000) + "V";
		IpdStatus[i++] = UnderVolValue;
		UnderVolDelay = String.valueOf((double) IpdValues[5] / 100) + "S";
		IpdStatus[i++] = UnderVolDelay;
		OverVolValue = String.valueOf((double) IpdValues[6] / 1000) + "V";
		IpdStatus[i++] = OverVolValue;
		OverVolDelay = String.valueOf(IpdValues[7] / 100) + "S";
		IpdStatus[i++] = OverVolDelay;
		LeakageResistance = String.valueOf((double) IpdValues[8] / 100) + "KΩ";
		IpdStatus[i++] = LeakageResistance;
		LeakageDelay = String.valueOf(IpdValues[9] / 100) + "S";
		IpdStatus[i++] = LeakageDelay;
		CH4Delay = String.valueOf(IpdValues[10] / 100) + "S";
		IpdStatus[i++] = CH4Delay;
		DeviceAddr = String.valueOf(IpdValues[11]);
		IpdStatus[i++] = DeviceAddr;
		UAB = String.valueOf((double) IpdValues[15] / 10) + "V";
		IpdStatus[i++] = UAB;
		UBC = String.valueOf((double) IpdValues[16] / 10) + "V";
		IpdStatus[i++] = UBC;
		UCA = String.valueOf((double) IpdValues[17] / 10) + "V";
		IpdStatus[i++] = UCA;
		LightingCurrentNow_A = String.valueOf((double) IpdValues[18] / 100) + "A";
		IpdStatus[i++] = LightingCurrentNow_A;
		LightingCurrentNow_B = String.valueOf((double) IpdValues[19] / 100) + "A";
		IpdStatus[i++] = LightingCurrentNow_B;
		SignalCurrentNow_B = String.valueOf((double) IpdValues[20] / 100) + "A";
		IpdStatus[i++] = SignalCurrentNow_B;
		if(IpdValues[21]==65535)
			RG = "∞";
		else
			RG = String.valueOf((double) IpdValues[21] / 100) + "KΩ";
		IpdStatus[i++] = RG;
		XB1 = GetXB1();
		IpdStatus[i++] = XB1;
		KB1 = GetKB1();
		IpdStatus[i++] = KB1;

		IpdValues[29] = BcdIntToHexInt(IpdValues[29]);
		IpdValues[30] = BcdIntToHexInt(IpdValues[30]);
		IpdValues[31] = BcdIntToHexInt(IpdValues[31]);
		SysDateYM = "20" + String.valueOf(IpdValues[29] >> 8) + "-"
				+ String.valueOf(IpdValues[29] & 0x000000FF) + "-";
		SysDateDH = String.valueOf(IpdValues[30] >> 8) + "  " + String.valueOf(IpdValues[30] & 0x000000FF)
				+ ":";
		SysDateMS = String.valueOf(IpdValues[31] >> 8) + ":" + String.valueOf(IpdValues[31] & 0x000000FF);
		IpdStatus[i++] = SysDateYM + SysDateDH + SysDateMS;

		Event1 = GetEvent(IpdValues[32]);
		IpdStatus[i++] = Event1;
		Event1ActValue = String.valueOf((double) IpdValues[33] / 100);
		IpdStatus[i++] = Event1ActValue;
		IpdValues[34] = BcdIntToHexInt(IpdValues[34]);
		IpdValues[35] = BcdIntToHexInt(IpdValues[35]);
		IpdValues[36] = BcdIntToHexInt(IpdValues[36]);
		Event1ActTimeYM = "20" + String.valueOf(IpdValues[34] >> 8) + "-"
				+ String.valueOf(IpdValues[34] & 0x000000FF) + "-";
		Event1ActTimeDH = String.valueOf(IpdValues[35] >> 8) + "  "
				+ String.valueOf(IpdValues[35] & 0x000000FF) + ":";
		Event1ActTimeMS = String.valueOf(IpdValues[36] >> 8) + ":"
				+ String.valueOf(IpdValues[36] & 0x000000FF);
		IpdStatus[i++] = Event1ActTimeYM + Event1ActTimeDH + Event1ActTimeMS;

		Event2 = GetEvent(IpdValues[37]);
		IpdStatus[i++] = Event2;
		Event2ActValue = String.valueOf((double) IpdValues[38] / 100);
		IpdValues[39] = BcdIntToHexInt(IpdValues[39]);
		IpdValues[40] = BcdIntToHexInt(IpdValues[40]);
		IpdValues[41] = BcdIntToHexInt(IpdValues[41]);
		IpdStatus[i++] = Event2ActValue;
		Event2ActTimeYM = "20" + String.valueOf(IpdValues[39] >> 8) + "-"
				+ String.valueOf(IpdValues[39] & 0x000000FF) + "-";
		Event2ActTimeDH = String.valueOf(IpdValues[40] >> 8) + "  "
				+ String.valueOf(IpdValues[40] & 0x000000FF) + ":";
		Event2ActTimeMS = String.valueOf(IpdValues[41] >> 8) + ":"
				+ String.valueOf(IpdValues[41] & 0x000000FF);
		IpdStatus[i++] = Event2ActTimeYM + Event2ActTimeDH + Event2ActTimeMS;

		Event3 = GetEvent(IpdValues[42]);
		IpdStatus[i++] = Event3;
		Event3ActValue = String.valueOf((double) IpdValues[43] / 100);
		IpdStatus[i++] = Event3ActValue;
		IpdValues[44] = BcdIntToHexInt(IpdValues[44]);
		IpdValues[45] = BcdIntToHexInt(IpdValues[45]);
		IpdValues[46] = BcdIntToHexInt(IpdValues[46]);
		Event3ActTimeYM = "20" + String.valueOf(IpdValues[44] >> 8) + "-"
				+ String.valueOf(IpdValues[44] & 0x000000FF) + "-";
		Event3ActTimeDH = String.valueOf(IpdValues[45] >> 8) + "  "
				+ String.valueOf(IpdValues[45] & 0x000000FF) + ":";
		Event3ActTimeMS = String.valueOf(IpdValues[46] >> 8) + ":"
				+ String.valueOf(IpdValues[46] & 0x000000FF);
		IpdStatus[i++] = Event3ActTimeYM + Event3ActTimeDH + Event3ActTimeMS;

		Event4 = GetEvent(IpdValues[47]);
		IpdStatus[i++] = Event4;
		Event4ActValue = String.valueOf((double) IpdValues[48] / 100);
		IpdStatus[i++] = Event4ActValue;
		IpdValues[49] = BcdIntToHexInt(IpdValues[49]);
		IpdValues[50] = BcdIntToHexInt(IpdValues[50]);
		IpdValues[51] = BcdIntToHexInt(IpdValues[51]);
		Event4ActTimeYM = "20" + String.valueOf(IpdValues[49] >> 8) + "-"
				+ String.valueOf(IpdValues[49] & 0x000000FF) + "-";
		Event4ActTimeDH = String.valueOf(IpdValues[50] >> 8) + "  "
				+ String.valueOf(IpdValues[50] & 0x000000FF) + ":";
		Event4ActTimeMS = String.valueOf(IpdValues[51] >> 8) + ":"
				+ String.valueOf(IpdValues[51] & 0x000000FF);
		IpdStatus[i++] = Event4ActTimeYM + Event4ActTimeDH + Event4ActTimeMS;

		Event5 = GetEvent(IpdValues[52]);
		IpdStatus[i++] = Event5;
		Event5ActValue = String.valueOf((double) IpdValues[53] / 100);
		IpdStatus[i++] = Event5ActValue;
		IpdValues[53] = BcdIntToHexInt(IpdValues[53]);
		IpdValues[54] = BcdIntToHexInt(IpdValues[54]);
		IpdValues[55] = BcdIntToHexInt(IpdValues[55]);
		Event5ActTimeYM = "20" + String.valueOf(IpdValues[53] >> 8) + "-"
				+ String.valueOf(IpdValues[53] & 0x000000FF) + "-";
		Event5ActTimeDH = String.valueOf(IpdValues[54] >> 8) + "  "
				+ String.valueOf(IpdValues[54] & 0x000000FF) + ":";
		Event5ActTimeMS = String.valueOf(IpdValues[55] >> 8) + ":"
				+ String.valueOf(IpdValues[55] & 0x000000FF);
		IpdStatus[i++] = Event5ActTimeYM + Event5ActTimeDH + Event5ActTimeMS;

		for (i = 0; i < 11; i++) {
			if (i < 5) {
				for (j = 0; j < 4; j++) 
					Main.ipdView.table_1.setValueAt(IpdStatus[k++], 2 * i + 1, j);
			}else if(i==5)
			{
				for(j=0;j<2;j++)
					Main.ipdView.table_1.setValueAt(IpdStatus[k++], 2 * i + 1, j);
			}
			else {
				for(j=0;j<3;j++)
					{
						Main.ipdView.table_1.setValueAt(IpdStatus[k++], 2 * i + 1, j);
						if(k>=39)
							return;
					}
			}
		}
	}
}
