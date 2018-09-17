package com.fmkj.gateways;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.TimerTask;

import com.fmkj.Begin.Main;
import com.fmkj.sensers.IPD;
import com.fmkj.sensers.Senser;
import com.fmkj.sensers.SenserFactory;
import com.fmkj.tools.IpSwitch;
import com.fmkj.tools.NomalDataSwitch;
import com.fmkj.values.GatewayLinkStatus;
import com.fmkj.values.GatewayType;
import com.fmkj.values.LogFileName;
import com.fmkj.values.MsgColor;
import com.fmkj.values.SenserInfo;
import com.fmkj.values.ShowType;

public class CanGateway {
	public String gatewayType = GatewayType.GATEWAYTYPE_CAN;
	public String ipString;
	public String LinkStatus;
	public String appVer;
	public String macAdd = "";
	private Socket socket = new Socket();
	private String senserCnt;
	private InetSocketAddress socketAddress;
	private InputStream iStream;
	private OutputStream oStream;
	public byte[] senserInfo;
	private Senser senser;
	private SenserFactory senserFactory = new SenserFactory();
	private Object[][] sensermsg;
	private String[][] msgStrings;
	private String show = "------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
	private String show1 = "************************************************************************************************************************************************************************";
	private String[] senserStrings;
	private ListenGatewayMsg listenGatewayMsg;
	public boolean getNorMalInfo = true;
	public IPD ipd = new IPD();
	public int SenserCnt;
	public GetCanGatewayInfo gatewayInfo = new GetCanGatewayInfo();
	private byte[] getVerCmd = Main.cmdMaker.getAppVerCmd();
	private byte[] getMacCmd = Main.cmdMaker.getMacCmd();
	private byte[] getInfoCmd = Main.cmdMaker.getCurInfoCmd();
	public boolean SoftVerGeted = false,MacGeted = false;
	volatile boolean alive = true;
	public CheckAlive checkAlive = new CheckAlive();
	@SuppressWarnings("static-access")
	public int freshMode(byte[] senserAck, String gatewayIp) {
		byte[] senserbuf = new byte[5];
		int i = 0, j = 0;
		msgStrings = null;
		sensermsg = null;
		SenserCnt = (((NomalDataSwitch.abs(senserAck[11]) * 256) + NomalDataSwitch.abs(senserAck[10])) - 11)
				/ 5;
		msgStrings = new String[SenserCnt][5];
		sensermsg = new Object[SenserCnt][5];
		senserStrings = new String[SenserCnt];
		for (i = 0; i < SenserCnt; i++) {
			for (j = 0; j < 5; j++)
				senserbuf[j] = senserAck[20 + 5 * i + j];
			senserStrings[i] = NomalDataSwitch.bytesToHexString(senserbuf);
			senser = senserFactory.creatSenser(senserbuf, gatewayIp);
			senser.saveSenserLog(gatewayIp,GatewayType.GATEWAYTYPE_CAN);
			msgStrings[i][0] = senser.SenserName;
			msgStrings[i][1] = senser.linkState;
			msgStrings[i][2] = senser.senserAddr;
			msgStrings[i][3] = senser.canCode;
			msgStrings[i][4] = senser.listenValue;
			if (Main.mainView.tree.getLastNode() != null) {
				if (this.equals(Main.gatewayManger.getCangateway(
						Main.mainView.tree.getLastNode().toString()))) {
					Main.mainView.table.setValueAt(senser.SenserName, i, 0);
					Main.mainView.table.setValueAt(senser.linkState, i, 1);
					Main.mainView.table.setValueAt(senser.senserAddr, i, 2);
					Main.mainView.table.setValueAt(senser.canCode, i, 3);
					if(senser.linkState.equals(SenserInfo.SENSERSTATE_DISCONNECTED))
						senser.listenValue = "---------------";
					Main.mainView.table.setValueAt(senser.listenValue, i, 4);
				}
			}
			sensermsg[i] = msgStrings[i];
		}
		return SenserCnt;
	}

	public String getLinkStatus() {
		return LinkStatus;
	}

	public void setSenserCnt(String senserCnt) {
		this.senserCnt = senserCnt;
	}

	public void setAppVer(String appVer) {
		this.appVer = appVer;
	}

	public void setMacAdd(String macAdd) {
		this.macAdd = macAdd;
	}

	public InputStream getiStream() {
		return iStream;
	}

	public OutputStream getoStream() {
		return oStream;
	}

	public String getSenserCnt() {
		return senserCnt;
	}

	public String getGatewayType() {
		return gatewayType;
	}

	public String getAppVer() {
		return appVer;
	}

	public String getMacAdd() {
		return macAdd;
	}

	public byte[] getSenserInfo() {
		return senserInfo;
	}

	public void setSenserInfo(byte[] senserInfo) {
		this.senserInfo = senserInfo;
	}

	public String getIpString() {
		return ipString;
	}

	public CanGateway(String ipString) {
		this.ipString = ipString;
		socketAddress = IpSwitch.getInetSocketAdress(ipString);
		new Link().start();
	}

	public void close() {
		listenGatewayMsg.listen = false;
		gatewayInfo.cancel();
		gatewayInfo = null;
		if(iStream != null)
		{
			try {
				iStream.close();
				iStream = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private class Link extends Thread {
		@Override
		public void run() {
			super.run();
			try {
				socket.connect(socketAddress, 2000);
				if (socket != null) {
					socket.setReuseAddress(true);
					socket.setSoLinger(true, 0);
					LinkStatus = GatewayLinkStatus.GATEWAYLINK_CONNECTED;
					iStream = socket.getInputStream();
					oStream = socket.getOutputStream();
					listenGatewayMsg = new ListenGatewayMsg();
					listenGatewayMsg.start();
					Main.gatewayManger.StratGetInfo(CanGateway.this);
				}
			} catch (IOException e) {
				LinkStatus = GatewayLinkStatus.GATEWAYLINK_FAILD;
				Main.gatewayManger.removeGateway(ipString);
				e.printStackTrace();
			}
		}
	}

	public void sendMsg(byte[] msg) {
		try {
			oStream.write(msg, 0, msg.length);
			oStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class ListenGatewayMsg extends Thread {
		private byte[] recvMsg = new byte[2048];
		private byte[] senserinfo1;
		byte[] canData;
		private String macAdd = "";
		private String tString, tString1, tString2;
		byte cnt;
		int recvCnt;
		private String gatewayAck;
		private int canDataCnt = 1;
		volatile boolean listen = true;
		@Override
		public void run() {
			while (listen) {
				if (iStream != null) {
					try {
						if ((recvCnt = iStream.read(recvMsg, 0,
								recvMsg.length)) != -1) {
							alive = true;
							tString = Main.datetool.getTimeHMS();
							tString2 = Main.datetool.getTimeHMSS();
							senserinfo1 = null;
							senserinfo1 = new byte[recvCnt];
							System.arraycopy(recvMsg, 0, senserinfo1, 0,
									recvCnt);
							gatewayAck = NomalDataSwitch.bytesToHexString(
									senserinfo1);
							Main.debugView.addMsg(Main.datetool.getDateString(
									System.currentTimeMillis())
									+ "网关应答：\n" + gatewayAck,
									ShowType.SHOWTYPE_GETGATEWAYINFO,
									Color.BLACK,
									ipString.substring(0, ipString
											.indexOf(":")));
							switch (recvMsg[9]) {
							case 0x72:
								setAppVer("V" + NomalDataSwitch
										.byteToDecimalString(
												recvMsg[12])
										.substring(0, 1)
										+ "."
										+ NomalDataSwitch.byteToDecimalString(
												recvMsg[12])
												.substring(1));
								Main.debugView.addMsg("CAN网关 "
										+ getIpString()
										+ " 固件版本获取成功，版本号："
										+ getAppVer(),
										ShowType.SHOWTYPE_GETGATEWAYINFO,
										Color.BLACK,
										ipString.substring(0,
												ipString.indexOf(":")));
								Main.debugView.addMsg(show,
										ShowType.SHOWTYPE_GETGATEWAYINFO,
										Color.BLACK,
										ipString.substring(0,
												ipString.indexOf(":")));
								if (appVer != null && macAdd != "") {
									getNorMalInfo = false;
								}
								break;
							case (byte) 0x88:
								for (int i = 0; i < 6; i++) {
									macAdd += NomalDataSwitch
											.byteToDecimalString(
													recvMsg[i + 12]);
									if (i < 5)
										macAdd += " : ";
								}
								setMacAdd(macAdd);
								Main.debugView.addMsg("CAN网关 "
										+ getIpString()
										+ " Mac地址获取成功，Mac地址："
										+ getMacAdd(),
										ShowType.SHOWTYPE_GETGATEWAYINFO,
										Color.BLACK,
										ipString.substring(0,
												ipString.indexOf(":")));
								if (appVer != null && macAdd != "") {
									getNorMalInfo = false;
								}
								macAdd = "";
								Main.debugView.addMsg(show,
										ShowType.SHOWTYPE_GETGATEWAYINFO,
										MsgColor.BLUE1,
										ipString.substring(0,
												ipString.indexOf(":")));
								if (gatewayType.equals(
										GatewayType.GATEWAYTYPE_VOICE))
									Main.debugView.addMsg(show1,
											ShowType.SHOWTYPE_GETGATEWAYINFO,
											Color.BLACK,
											ipString.substring(0,
													ipString.indexOf(":")));
								break;
							case 0x60:
								senserinfo1 = null;
								senserinfo1 = new byte[recvCnt];
								System.arraycopy(recvMsg, 0,
										senserinfo1, 0,
										recvCnt);
								senserInfo = senserinfo1;
								cnt = (byte) freshMode(senserinfo1,
										ipString.substring(0,
												ipString.indexOf(":")));
								setSenserCnt(NomalDataSwitch
										.byteToDecimalString(
												cnt));
								Main.debugView.addCanGatewayInfo(
										getIpString(),
										msgStrings,
										senserStrings,
										ShowType.SHOWTYPE_GETGATEWAYINFO,
										MsgColor.BLUE1);
								Main.debugView.addMsg("CAN网关 "
										+ getIpString()
										+ " 传感器信息获取成功",
										ShowType.SHOWTYPE_GETGATEWAYINFO,
										MsgColor.GREEN,
										ipString.substring(0,
												ipString.indexOf(":")));
								Main.debugView.addMsg(show1,
										ShowType.SHOWTYPE_GETGATEWAYINFO,
										Color.BLACK,
										ipString.substring(0,
												ipString.indexOf(":")));
								break;
							case 0x10:
								Main.debugView.addMsg("收到指令成功应答",
										ShowType.SHOWTYPE_GETGATEWAYINFO,
										Color.BLUE,
										ipString.substring(0,
												ipString.indexOf(":")));
								break;
							case 0x11:
								Main.debugView.addMsg("当前音量："
										+ NomalDataSwitch.byteToDecimalString(
												recvMsg[15]),
										ShowType.SHOWTYPE_GETGATEWAYINFO,
										Color.BLUE,
										ipString.substring(0,
												ipString.indexOf(":")));
								Main.musicPlayView.volumslider.setValue(
										recvMsg[15]);
								break;
							case (byte) 0xCC:
								canData = new byte[3 + recvMsg[8]];
								System.arraycopy(recvMsg, 6, canData, 0,
										3);
								System.arraycopy(recvMsg, 10, canData,
										3, recvMsg[8]);
								Main.logRecoder.saveLog(
										LogFileName.Log_InitData
												+ ipString.substring(
														0,
														ipString.indexOf(":"))
												+ "\\"
												+ Main.datetool.getTimeH()
												+ "-InitData.log",
										tString2 + NomalDataSwitch
												.getEmp2(tString2)
												+ NomalDataSwitch.bytesToHexString(
														canData)
												+ "-------->网关下发初始化信息,初始化设备地址："
												+ NomalDataSwitch.byteToDecimalString(
														canData[1])
												+ "\n");
								break;
							case (byte) 0xED:
								canData = new byte[4 + recvMsg[8]];
								System.arraycopy(recvMsg, 10, canData,
										0, canData.length);
								if (tString.equals(tString1)) {
									canDataCnt++;
									Main.logRecoder.saveLog(
											LogFileName.Log_CanData
													+ ipString.substring(
															0,
															ipString.indexOf(":"))
													+ "\\"
													+ Main.datetool.getTimeH()
													+ "-CanData.log",
											"\n" + tString2 + NomalDataSwitch
													.getEmp2(tString2)
													+ NomalDataSwitch.bytesToHexString(
															canData));
								} else {
									Main.logRecoder.saveLog(
											LogFileName.Log_CanData
													+ ipString.substring(
															0,
															ipString.indexOf(":"))
													+ "\\"
													+ Main.datetool.getTimeH()
													+ "-CanData.log",
											"\n********************【Can数据计数："
													+ String.valueOf(canDataCnt)
													+ "】********************\n\n************************"
													+ tString
													+ "***********************");
									canDataCnt = 1;
									Main.logRecoder.saveLog(
											LogFileName.Log_CanData
													+ ipString.substring(
															0,
															ipString.indexOf(":"))
													+ "\\"
													+ Main.datetool.getTimeH()
													+ "-CanData.log",
											"\n" + tString2 + NomalDataSwitch
													.getEmp2(tString2)
													+ NomalDataSwitch.bytesToHexString(
															canData));
								}
								tString1 = tString;
								break;
							case (byte) 0xCE:
								Main.logRecoder.saveLog(
										LogFileName.Log_CanData
												+ ipString.substring(
														0,
														ipString.indexOf(":"))
												+ "\\"
												+ Main.datetool.getTimeH()
												+ "-CanData.log",
										"------【" + tString2 + "同步上传时间"
												+ "】");
								break;
							case (byte) 0xEF:// 综保信息
								senserinfo1 = new byte[114];
								System.arraycopy(recvMsg, 20,
										senserinfo1, 0,
										114);
								ipd.FreshIPD(senserinfo1);
								break;
							default:

								break;
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			try {
				if (iStream != null)
					iStream.close();
				if (oStream != null)
					oStream.close();
				if (socket != null)
					socket.close();
				iStream = null;
				oStream = null;
				socket = null;
				listenGatewayMsg = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void sendCmd(byte[] cmd) {
		if (oStream != null) {
			try {
				oStream.write(cmd);
				oStream.flush();
			} catch (IOException e) {
			}
		}
	}
	
	public class GetCanGatewayInfo extends TimerTask {
		@Override
		public void run() {
			if(!SoftVerGeted) {
				Main.debugView.addMsg(
					Main.datetool.getDateString(System.currentTimeMillis()) + "正在获取 "+gatewayType+ ipString + " 固件版本",
					ShowType.SHOWTYPE_VOICE, MsgColor.BLUE,
					ipString.substring(0, ipString.indexOf(":")));
				sendCmd(getVerCmd);
			}else if(!MacGeted) {
				Main.debugView.addMsg(
					Main.datetool.getDateString(System.currentTimeMillis()) + "正在获取 "+gatewayType+ ipString + " Mac地址",
					ShowType.SHOWTYPE_VOICE, MsgColor.BLUE,
					ipString.substring(0, ipString.indexOf(":")));
				sendCmd(getMacCmd);
			}else {
				Main.debugView.addMsg(
					Main.datetool.getDateString(System.currentTimeMillis()) + "正在获取 "+gatewayType+ ipString + " 传感器信息",
					ShowType.SHOWTYPE_VOICE, MsgColor.BLUE,
					ipString.substring(0, ipString.indexOf(":")));
				sendCmd(getInfoCmd);
			}
		}
	}
	
	public class CheckAlive extends TimerTask{
		@Override
		public void run() {
			if(alive)
				alive = false;
			else 
				Main.gatewayManger.removeGateway(ipString);
		}
	}
}
