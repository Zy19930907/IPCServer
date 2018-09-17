package com.fmkj.tools;

import com.fmkj.Begin.Main;

public class NomalDataSwitch {

	public static String byteToDecimalString(byte in) {
		int i, temp = 0;
		if (in < 0)
			i = in + 256;
		else
			i = in;
		temp += (i >> 4) * 16;
		temp += i & 0x0f;
		return String.valueOf(temp);
	}

	public static int stringToInt(String in) {
		int i = 0;
		int out = 0;
		int temp = 0;
		int[] chengshu = new int[] { 1, 10, 100, 1000, 10000 };
		for (i = 0; i < in.length(); i++) {
			temp = Integer.parseInt(in.substring(in.length() - i - 1, in.length() - i)) * chengshu[i];
			out += temp;
		}
		return out;
	}

	public static byte stringToByte(String in) {
		int i = 0;
		byte out = 0;
		int temp = 0;
		int[] chengshu = new int[] { 1, 10, 100, 1000, 10000 };
		for (i = 0; i < in.length(); i++) {
			temp = Byte.parseByte(in.substring(in.length() - i - 1, in.length() - i)) * chengshu[i];
			out += temp;
		}
		return out;
	}

	public static String bytesToHexString(byte[] inBytes) {

		String outString = "";
		int i = 0, j = 0;
		for (i = 0; i < inBytes.length; i++) {
			outString += byteToHexString(inBytes[i]);
			outString += " ";
			j++;
			if (j >= 50) {
				outString += '\n';
				j = 0;
			}
		}
		return outString;
	}

	public static String byteToHexString(byte inByte) {
		String[] temp = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
		int i;
		if (inByte < 0)
			i = inByte + 256;
		else
			i = inByte;
		return (temp[i >> 4] + temp[i & 0x0f]);
	}

	public static String getEmp(String preString) {
		String out = "";
		int cnt = 10;
		cnt = 10 - preString.length();
		while ((cnt--) > 0) {
			out += "  ";
		}
		return out;
	}

	public static String getEmp1(String preString) {
		String out = "";
		int cnt = 10;
		cnt = 10 - preString.length();
		while ((cnt--) > 0) {
			out += " ";
		}
		return out;
	}

	public static String getEmp2(String preString) {
		String out = "";
		int cnt = 16;
		cnt = 16 - preString.length();
		while ((cnt--) > 0) {
			out += " ";
		}
		return out;
	}

	public static String getEmp3(String preString) {
		String out = "";
		int cnt = 60;
		if(preString.length()<60)
			cnt = 60 - preString.length();
		while ((cnt--) > 0) {
			out += " ";
		}
		return out;
	}
	
	public static String getEmp4(String preString) {
		String out = "",temp="";
		int cnt = 8;
		if(preString.length()< 8)
			cnt = 8 - preString.length();
		if(preString.length() < 5)
			temp = "-";
		else 
			temp ="--";
		while ((cnt--) > 0) {
			out += temp;
		}
		return out;
	}
	public static int abs(byte in) {
		if (in < 0)
			return ((int) (in + 256));
		return (int) in;
	}

	public static String bytesToDouble(byte byteL, byte byteH) {
		double out = 0;
		int i, j = 1;
		String fuhao = "";
		if ((byteL != (byte) 0xFF) && (byteH != (byte) 0xFF)) {
			i = abs(byteL) + (abs(byteH) * 256);
			switch ((i >> 13) & 0x00000003) {
			case 0x00:
				j = 1;
				break;
			case 0x01:
				j = 10;
				break;
			case 0x02:
				j = 100;
				break;
			case 0x03:
				j = 1000;
				break;
			}
			if ((i >> 15) == 1)
				fuhao = "-";
			out = ((double) (i & 0x0FFF)) / j;
			return fuhao+String.valueOf(out);
		} else
			return "----------";
	}
	
	public static int getValue(byte valueH, byte valueL) {
		return (NomalDataSwitch.abs(valueH) << 8) | NomalDataSwitch.abs(valueL);
	}
	
	public static String canDataToString(byte[] data)
	{
		String out = "";
		String temp = "",temp1="",temp2="";
		String[] cmd = new String[]{"定时上传","主动上传","开关量上传","定时上传","主动上传","电源上传","广播上传","系统复位","断电器上传",
				"","","","","北京电源","","确认应答","设备参数","标定点1","标定点2","报警信息","当前信息","出厂时间"};
		int CanId;
		CanId = (abs(data[0]) << 24)+(abs(data[1]) << 16)+(abs(data[2]) << 8)+abs(data[3]);
		
		if(((CanId >> 28)&0x01)==0x01)
			temp ="分时复用";
		else
			temp ="正常";
		temp += getEmp4(temp);
		out += temp;
		
		temp = ("帧序数："+ String.valueOf(CanId >>24 & 0x0F));
		temp += getEmp4(temp);
		out += temp;
	
		if(((CanId >> 17) & 0x7F)<=22) {
			int i = ((CanId >> 17) & 0x7F)-1;
			if(i < 0)
				return "----------------------------------------------";
			temp1 = cmd[i];
		}
		else if(((CanId >> 17)&0x7F) == 0x51)
			temp1 = "发初始化";
		else if(((CanId >> 17)&0x7F) == 0x52)
			temp1 = "清初始化";
		else if(((CanId >> 17)&0x7F) == 0x14)
			temp1 = "查询上报";
		else if(((CanId >> 17)&0x7F) == 0x15)
			temp1 = "查询下报";
		else if(((CanId >> 17)&0x7F) == 0x19)
			temp1 = "关联地址";
		else if(((CanId >> 17)&0x7F) == 0x1C)
			temp1 = "关联类型";
		else if(((CanId >> 17)&0x7F) == 0x24)
			temp1 = "设置参数";
		else if(((CanId >> 17)&0x7F) == 0x23)
			temp1 = "同步时间";
		else
			temp1 = "其它指令";
		temp2 =(temp1+ getEmp4(temp1));
		out+= temp2;
		
		if(((CanId >> 16)&0x01) == 0x01)
			temp = "断电测试";
		else
			temp = "正常数据";
		temp += getEmp4(temp);
		out+= temp;
		
		if(((CanId >> 15) & 0x01) == 0x01)
			temp = "控制信息";
		else
			temp = "普通信息";
		temp += getEmp4(temp);
		out+= temp;
		
		if(((CanId >> 14) & 0x01) == 0x01)
			temp = "上行数据";
		else 
			temp = "下行数据";
		temp += getEmp4(temp);
		out+= temp;
		
		temp = Main.senserFactory.byteToSenserTypeString((byte)((CanId >> 8)&0x3F));
		if(temp1.equals("发初始化") || temp1.equals("清初始化")||temp1.equals("设置参数"))
			temp = temp1;
		temp += getEmp4(temp);
		out+= temp;
		
		out += "设备地址："+ String.valueOf(CanId & 0xFF);
		return out;
	}
	
	public static String canDataToString1(byte[] data)
	{
		String out = "";
		String temp = "",temp1="",temp2="";
		String[] cmd = new String[]{"定时上传","主动上传","开关量上传","定时上传","主动上传","电源上传","广播上传","系统复位","断电器上传",
				"","","","","北京电源","","确认应答","设备参数","标定点1","标定点2","报警信息","当前信息","出厂时间"};
		int CanId;
		CanId = (abs(data[0]) << 24)+(abs(data[1]) << 16)+(abs(data[2]) << 8)+abs(data[3]);
		
		if(((CanId >> 28)&0x01)==0x01)
			temp ="分时复用";
		else
			temp ="正常";
		temp += getEmp4(temp);
		out += temp;
		
		temp = ("帧序数："+ String.valueOf(CanId >>24 & 0x0F));
		temp += getEmp4(temp);
		out += temp;
	
		if(((CanId >> 17) & 0x7F)<=22)
			temp1 = cmd[((CanId >> 17) & 0x7F)-1];
		else if(((CanId >> 17)&0x7F) == 0x51)
			temp1 = "发初始化";
		else if(((CanId >> 17)&0x7F) == 0x52)
			temp1 = "清初始化";
		else if(((CanId >> 17)&0x7F) == 0x14)
			temp1 = "查询上报";
		else if(((CanId >> 17)&0x7F) == 0x15)
			temp1 = "查询下报";
		else if(((CanId >> 17)&0x7F) == 0x19)
			temp1 = "关联地址";
		else if(((CanId >> 17)&0x7F) == 0x1C)
			temp1 = "关联类型";
		else if(((CanId >> 17)&0x7F) == 0x24)
			temp1 = "设置参数";
		else if(((CanId >> 17)&0x7F) == 0x23)
			temp1 = "同步时间";
		else
			temp1 = "其它指令";
		temp2 =(temp1+ getEmp4(temp1));
		out+= temp2;
		
		if(((CanId >> 16)&0x01) == 0x01)
			temp = "断电测试";
		else
			temp = "正常数据";
		temp += getEmp4(temp);
		out+= temp;
		
		if(((CanId >> 15) & 0x01) == 0x01)
			temp = "控制信息";
		else
			temp = "普通信息";
		temp += getEmp4(temp);
		out+= temp;
		
		temp = "网关下发";
		temp += getEmp4(temp);
		out+= temp;
		
		temp = temp1;
		temp += getEmp4(temp);
		out+= temp;
		
		out += "设备地址："+ String.valueOf(CanId & 0xFF);
		return out;
	}
	
	public static String Rs485DataToString(byte[] data)
	{
		String out = "";
		String temp = "";
		int CanId;
		CanId = (abs(data[0]) << 24)+(abs(data[1]) << 16)+(abs(data[2]) << 8)+abs(data[3]);
		if(CanId == 0x55AA55AA)
			temp = "AB检测";
		else 
			temp = "帧头错误";
		temp += getEmp4(temp);
		out+= temp;
		
		if((CanId == 0x55AA55AA)&&( data[5] == 0x01))
			temp = "网关下发";
		else if (CanId == 0x55AA55AA &&( data[5] == 0x10))
			temp = "广播应答";
		
		temp += getEmp4(temp);
		out+= temp;
		
		temp = "广播地址："+String.valueOf(NomalDataSwitch.abs(data[4]));
		out+=temp;
		return out;
	}
}
