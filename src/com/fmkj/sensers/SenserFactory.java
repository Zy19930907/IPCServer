package com.fmkj.sensers;

import com.fmkj.values.SenserType;

public class SenserFactory {
	private Senser_CH4_Low senser_CH4_Low;
	private Senser_CH4_LowHigh senser_CH4_LowHigh;
	private Senser_Breaker senser_Breaker;
	private Senser_OpenSwitch senser_OpenSwitch;
	private Senser_Power senser_Power;
	private Senser_CO senser_CO;
	private Senser_CO2 senser_CO2;
	private Senser_CardReader senser_CardReader;
	private Senser_BoardCast senser_BoardCast;
	private Senser_TempHumi senser_TempHumi;
	private Senser_LaserCH4 senser_LaserCH4;
	private Senser_NativePre senser_NativePre;
	private Senser_O2 senser_O2;
	private Senser_WindSpeed senser_WindSpeed;
	private Senser_Dust senser_Dust;
	private Senser_LiquidLevel senser_LiquidLevel;
	private Senser_WindDoor senser_WindDoor;
	private Senser_Unknown senser_Unknown;
	private Senser_Smog senser_Smog;
	private Senser_Fengtong senser_Fengtong;
	private Senser_PekingPower senser_PekingPower;
	private Senser_IPD_ZJM senser_IPD_ZJM;
	public  Senser creatSenser(byte[] senserData,String gatewayIp) {
		        if(senserData[1]==0x00) { 
			        senser_CH4_Low = null; 
			        senser_CH4_Low = new Senser_CH4_Low(senserData,gatewayIp); 
			        return senser_CH4_Low;
			        }
		        else if(senserData[1]==0x01) {
			        senser_CH4_LowHigh = null;
			        senser_CH4_LowHigh = new Senser_CH4_LowHigh(senserData,gatewayIp);
			        return senser_CH4_LowHigh;
		        }
		        else if (senserData[1]==0x03) {
			        senser_LaserCH4 = null;
			        senser_LaserCH4 = new Senser_LaserCH4(senserData,gatewayIp);
			        return senser_LaserCH4;
		        }
		        else  if(senserData[1]==0x04) {
			        senser_CO = null;
			        senser_CO = new Senser_CO(senserData,gatewayIp);
			        return senser_CO;
		        }
		        else if (senserData[1]==0x05) {
			        senser_O2 = null;
			        senser_O2 = new Senser_O2(senserData,gatewayIp);
			        return senser_O2;
		        }
		        else if (senserData[1]==0x06) {
			        senser_NativePre = null;
			        senser_NativePre = new Senser_NativePre(senserData,gatewayIp);
			        return senser_NativePre;
		        }
		        else if(senserData[1] == 0x07) {
			        senser_TempHumi = null;
			        senser_TempHumi = new Senser_TempHumi(senserData,gatewayIp);
			        return senser_TempHumi;
		        }
		        else if(senserData[1] == 0x09) {
			        senser_CO2 = null;
			        senser_CO2 = new Senser_CO2(senserData,gatewayIp);
			        return senser_CO2;
		        }
		        else if(senserData[1]==0x1F) {
			        senser_Breaker = null;
			        senser_Breaker = new Senser_Breaker(senserData,gatewayIp);
			        return senser_Breaker;
		        }
		        else if(senserData[1]==0x22) {
			        senser_OpenSwitch = null;
			        senser_OpenSwitch = new Senser_OpenSwitch(senserData,gatewayIp);
			        return senser_OpenSwitch;
		        }
		        else  if(senserData[1]==0x23) {
			        senser_Power = null;
			        senser_Power = new Senser_Power(senserData,gatewayIp);
			        return senser_Power;
		        }
		        else  if(senserData[1]==0x24) {
			        senser_CardReader = null;
			        senser_CardReader = new Senser_CardReader(senserData,gatewayIp);
			        return senser_CardReader;
		        }
		        else  if(senserData[1]==0x28) {
			        senser_BoardCast = null;
			        senser_BoardCast = new Senser_BoardCast(senserData,gatewayIp);
			        return senser_BoardCast;
		        }
		        else if(senserData[1]==0x08) {
			        senser_WindSpeed = null;
			        senser_WindSpeed = new Senser_WindSpeed(senserData,gatewayIp);
			        return senser_WindSpeed;
		        }
		        else if(senserData[1]==0x0C) {
			        senser_Dust = null;
			        senser_Dust = new Senser_Dust(senserData,gatewayIp);
			        return senser_Dust;
		        }
		        else if(senserData[1]==0x0C) {
			        senser_Dust = null;
			        senser_Dust = new Senser_Dust(senserData,gatewayIp);
			        return senser_Dust;
		        }
		        else if(senserData[1]==0x0D) {
			        senser_LiquidLevel = null;
			        senser_LiquidLevel = new Senser_LiquidLevel(senserData,gatewayIp);
			        return senser_LiquidLevel;
		        }
		        else if(senserData[1]==0x0E) {
			        senser_Smog = null;
			        senser_Smog = new Senser_Smog(senserData,gatewayIp);
			        return senser_Smog;
		        }
		        else if(senserData[1]==0x0F) {
			        senser_WindDoor = null;
			        senser_WindDoor = new Senser_WindDoor(senserData,gatewayIp);
			        return senser_WindDoor;
		        }
		        else if(senserData[1]==0x2A) {
			        senser_Fengtong= null;
			        senser_Fengtong = new Senser_Fengtong(senserData,gatewayIp);
			        return senser_Fengtong;
		        }
		        else if(senserData[1] == 0x38)
		        {
			        senser_IPD_ZJM = null;
			        senser_IPD_ZJM = new Senser_IPD_ZJM(senserData,gatewayIp);
			        return senser_IPD_ZJM;
		        }
		        else if(senserData[1] == 0x39)
		        {
			        senser_PekingPower = null;
			        senser_PekingPower = new Senser_PekingPower(senserData,gatewayIp);
			        return senser_PekingPower;
		        }
		        else {
			senser_Unknown = null;
		        	senser_Unknown = new Senser_Unknown(senserData,gatewayIp);
		          return senser_Unknown;
		        }
	}
	
	public String byteToSenserTypeString(byte type) {
		   if(type ==0x00)
			       return SenserType.SENSERTYPE_CH4LOW;
		        else if(type ==0x01) 
			        return SenserType.SENSERTYPE_CH4HIGHLOW;
		        else if (type==0x03) 
			        return SenserType.SENSERTYPE_LASERCH4;
		        else  if(type==0x04) 
			        return SenserType.SENSERTYPE_CO;
		        else if (type==0x05) 
			        return SenserType.SENSERTYPE_O2;
		        else if (type==0x06) 
			        return SenserType.SENSERTYPE_NATIVEPRE;
		        else if(type== 0x07) 
			        return SenserType.SENSERTYPE_TEMPHUMI;
		        else if(type == 0x09) 
			        return SenserType.SENSERTYPE_CO2;
		        else if(type==0x1F) 
			        return SenserType.SENSERTYPE_BREAKER;
		        else if(type==0x22) 
			        return SenserType.SENSERTYPE_OPENSWITCH;
		        else  if(type==0x23) 
			        return SenserType.SENSERTYPE_POWER;
		        else  if(type==0x24) 
			        return SenserType.SENSERTYPE_CARDREADER;
		        else  if(type==0x28)
			        return SenserType.SENSERTYPE_BOARDCAST;
		        else if(type==0x08) 
			        return SenserType.SENSERTYPE_WindSpeed;
		        else if(type==0x0C) 
			        return SenserType.SENSERTYPE_Dust;
		        else if(type==0x0D) 
			        return SenserType.SENSERTYPE_LIQUIDLEVEL;
		        else if(type==0x0E) 
			        return SenserType.SENSERTYPE_SMOG;
		        else if(type==0x0F) 
			        return SenserType.SENSERTYPE_WINDDOOR;
		        else if(type==0x2A) 
			        return SenserType.SENSERTYPE_Fengtong;
		        else if(type == 0x38)
			        return SenserType.SENSERTYPE_IPD_ZJM;
		        else if(type == 0x39)
			        return SenserType.SENSERTYPE_PKPOWER;
		
		return SenserType.SENSERTYPE_UNKONOW;
	}
}
