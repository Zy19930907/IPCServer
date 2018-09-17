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
import com.fmkj.tools.Adpcm;
import com.fmkj.tools.IpSwitch;
import com.fmkj.tools.NomalDataSwitch;
import com.fmkj.tools.PcmPlayer;
import com.fmkj.tools.WavReader;
import com.fmkj.values.GatewayLinkStatus;
import com.fmkj.values.GatewayType;
import com.fmkj.values.LogFileName;
import com.fmkj.values.MsgColor;
import com.fmkj.values.SenserInfo;
import com.fmkj.values.SenserType;
import com.fmkj.values.ShowType;
import com.fmkj.views.TolkView;

public class VoiceGateway {
	public String gatewayType = GatewayType.GATEWAYTYPE_VOICE;
	public String linkState;
	public String appVer;
	public String macAddr;
	public String ipString;
	public InetSocketAddress address;
	public Socket socket = new Socket();
	private InputStream inputStream;
	private OutputStream outputStream;
	public Link link = new Link();
	private Adpcm adpcm = new Adpcm();
	public WavReader wavReader;
	private PcmPlayer pcmPlayer = new PcmPlayer();
	private TolkView tolkView = new TolkView();
	private boolean tolking = false;
	private TolkViewClose tolkViewClose = new TolkViewClose();
	private String show = "-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
	private byte boardCastAddr;
	private SendMusic sendMusicTask;
	private int sendCnt = 0;
	private int errCnt = 0;
	private long sendTime, recvTime;
	private ListenGatewayMsg listenGatewayMsg;
	volatile int bufferUsed;
	private byte[] getVerCmd = Main.cmdMaker.getAppVerCmd();
	private byte[] getMacCmd = Main.cmdMaker.getMacCmd();
	private byte[] getInfoCmd = Main.cmdMaker.getCurInfoCmd();

	private String senserCnt;
	public byte[] senserInfo;
	private Senser senser;
	private SenserFactory senserFactory = new SenserFactory();
	private Object[][] sensermsg;
	private String[][] msgStrings;
	private String show1 = "************************************************************************************************************************************************************************";
	private String[] senserStrings;
	public boolean getNorMalInfo = true;
	public IPD ipd = new IPD();
	public int SenserCnt = -1;
	public boolean SoftVerGeted = false, MacGeted = false;
	public GetVoiceGatewayInfo gatewayInfo = new GetVoiceGatewayInfo();
	public CheckAlive checkAlive = new CheckAlive();

	public VoiceGateway(String ipString) {
		this.ipString = ipString;
		address = IpSwitch.getInetSocketAdress(ipString);
		adpcm.ReSetAdpcmDecode();
		adpcm.ReSetAdpcmEncode();
		link.start();
		tolkViewClose.start();

	}

	private class Link extends Thread {
		@SuppressWarnings("static-access")
		@Override
		public void run() {
			try {
				linkState = "正在连接";
				Main.logRecoder.saveLog(
						LogFileName.Log_VoiceGatewayLinklogs + ipString.substring(0, ipString.indexOf(":"))
								+ "-link.log",
						Main.datetool.getTimeHMSS() + linkState + gatewayType + ":" + ipString + "\n");
				socket.connect(address, 2000);
				socket.setReuseAddress(true);
				socket.setSoLinger(true, 0);
				inputStream = socket.getInputStream();
				outputStream = socket.getOutputStream();
				listenGatewayMsg = new ListenGatewayMsg();
				listenGatewayMsg.start();
				if ((outputStream != null) && (inputStream != null)) {
					linkState = GatewayLinkStatus.GATEWAYLINK_CONNECTED;
					Main.logRecoder.saveLog(
							LogFileName.Log_VoiceGatewayLinklogs + ipString.substring(0, ipString.indexOf(":"))
									+ "-link.log",
							Main.datetool.getTimeHMSS() + gatewayType + ":" + ipString + linkState + "----------本地端口："
									+ socket.getLocalPort() + "\n");
					try {
						Thread.sleep(200);
						outputStream.write(Main.cmdMaker.getGatewayDebufCmd((byte) 0x01));
						outputStream.flush();
						Main.mainView.tree.gatewayDebugMode.setText("退出调试模式");
						Main.gatewayManger.StartGetInfo(VoiceGateway.this);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				linkState = GatewayLinkStatus.GATEWAYLINK_FAILD;
				Main.logRecoder.saveLog(
						LogFileName.Log_VoiceGatewayLinklogs + ipString.substring(0, ipString.indexOf(":"))
								+ "-link.log",
						Main.datetool.getTimeHMSS() + gatewayType + ":" + ipString + linkState + "----------"
								+ e.getMessage() + "\n");
				Main.gatewayManger.relinkGateway(ipString);
				e.printStackTrace();
			}
		}
	}

	public void sendCmd(byte[] cmd) {
		if (outputStream != null) {
			try {
				outputStream.write(cmd);
				outputStream.flush();
			} catch (IOException e) {
			}
		}
	}

	private class TolkViewClose extends Thread {
		@Override
		public void run() {
			while (true) {
				if (tolking) {
					tolking = false;
				} else {
					tolkView.setVisible(false);
					tolkView.reset = true;
					tolkView.tolkTime = 0;
					pcmPlayer.sdl.stop();
					adpcm.ReSetAdpcmDecode();
				}
				try {
					Thread.sleep(375);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void close() {
		if (listenGatewayMsg != null)
			listenGatewayMsg.listen = false;
		if (gatewayInfo != null)
			gatewayInfo.cancel();
		gatewayInfo = null;
		if (checkAlive != null)
			checkAlive.cancel();
		if (inputStream != null)
			try {
				inputStream.close();
				inputStream = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public void sendMusic(byte addr) {
		adpcm.ReSetAdpcmDecode();
		sendMusicTask = null;
		sendMusicTask = new SendMusic();
		sendMusicTask.start();
		Main.musicPlayView.playslider.setValue(0);
		boardCastAddr = addr;
	}

	public void StopMusic() {
		wavReader.stopRead();
	}

	private class SendMusic extends Thread {
		private byte[] pcmBuf;
		private byte[] adpcmBuf;
		private byte[] sendBuf = new byte[507];
		private long tick1, tick2, tick3 = 0, tick;
		private int sleepTime = 125;

		public SendMusic() {
			sendCnt = 0;
			wavReader = null;
			wavReader = new WavReader();
			pcmPlayer.sdl.stop();
			setPriority(10);
		}

		@Override
		public void run() {
			while ((pcmBuf = wavReader.getNextPcmPack()) != null) {
				adpcm.ReSetAdpcmEncode();
				adpcmBuf = adpcm.PCMToADPCM(pcmBuf, pcmBuf.length);
				System.arraycopy(adpcmBuf, 0, sendBuf, 7, 500);
				try {
					sendBuf[0] = 0x55;
					sendBuf[1] = (byte) 0xAA;
					sendBuf[2] = 0x55;
					sendBuf[3] = (byte) 0xAA;
					sendBuf[4] = boardCastAddr;
					sendBuf[5] = (byte) sendCnt;
					sendBuf[6] = (byte) (sendCnt >> 8);
					sendCnt++;
					sendTime = tick1;
					tick = tick1 - tick3;
					tick3 = tick1;
					outputStream.write(sendBuf, 0, 507);
					outputStream.flush();
					tick1 = System.currentTimeMillis();
					Main.debugView.addMsg(
							"***************************************************发送语音包**********************************************************\n"
									+ "-----------------------------------------" + Main.datetool.getDateString(tick1)
									+ "广播地址：" + NomalDataSwitch.byteToDecimalString(sendBuf[4]) + " ---帧序号："
									+ String.valueOf(sendCnt - 1) + " ---发送间隔：" + String.valueOf(tick) + "Ms"
									+ "----------------------------------------------\n"
									+ NomalDataSwitch.bytesToHexString(sendBuf)
									+ "\n**************************************************************************************************************************************************************",
							ShowType.SHOWTYPE_VOICEDATA, MsgColor.GREEN, ipString + ":5000");
					// if (bufferUsed <= 10000)
					// sleepTime = 15;
					// if (bufferUsed >= 15000)
					// sleepTime = 250;
					Thread.sleep(sleepTime, 1);
					tick2 = System.currentTimeMillis();
					tick = tick2 - tick1;
					if (tick > 125)
						sleepTime -= (tick - 125);
					else if (tick < 125)
						sleepTime += (125 - tick);
					if (sleepTime < 0)
						sleepTime = 0;
				} catch (IOException e) {
					e.printStackTrace();
					Main.debugView.addMsg(e.getMessage(), ShowType.SHOWTYPE_VOICE, MsgColor.RED, "");
				} catch (InterruptedException e) {
					e.printStackTrace();
					Main.debugView.addMsg(e.getMessage(), ShowType.SHOWTYPE_VOICE, MsgColor.RED, "");
				}
			}
			pcmPlayer.sdl.start();
			adpcm.ReSetAdpcmEncode();
			try {
				outputStream.write(Main.cmdMaker.getMusicEndCmd(boardCastAddr));
				outputStream.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
				Main.debugView.addMsg(e1.getMessage(), ShowType.SHOWTYPE_VOICE, MsgColor.RED, "");
			}
			wavReader = null;
			System.gc();
		}
	}

	@SuppressWarnings("static-access")
	public int freshMode(byte[] senserAck, String gatewayIp) {
		byte[] senserbuf = new byte[5];
		int i = 0, j = 0;
		msgStrings = null;
		sensermsg = null;
		SenserCnt = (((NomalDataSwitch.abs(senserAck[11]) * 256) + NomalDataSwitch.abs(senserAck[10])) - 11) / 5;
		msgStrings = new String[SenserCnt][5];
		sensermsg = new Object[SenserCnt][5];
		senserStrings = new String[SenserCnt];

		for (i = 0; i < SenserCnt; i++) {
			for (j = 0; j < 5; j++)
				senserbuf[j] = senserAck[20 + 5 * i + j];
			senserStrings[i] = NomalDataSwitch.bytesToHexString(senserbuf);
			senser = senserFactory.creatSenser(senserbuf, gatewayIp);
			senser.saveSenserLog(gatewayIp, GatewayType.GATEWAYTYPE_VOICE);
			msgStrings[i][0] = senser.SenserName;
			msgStrings[i][1] = senser.linkState;
			msgStrings[i][2] = senser.senserAddr;
			msgStrings[i][3] = senser.canCode;
			msgStrings[i][4] = senser.listenValue;
			if (Main.mainView.tree.getLastNode() != null) {
				if (this.equals(Main.gatewayManger.getVoiceGateway(Main.mainView.tree.getLastNode().toString()))) {
					Main.mainView.table.setValueAt(senser.SenserName, i, 0);
					Main.mainView.table.setValueAt(senser.linkState, i, 1);
					Main.mainView.table.setValueAt(senser.senserAddr, i, 2);
					Main.mainView.table.setValueAt(senser.canCode, i, 3);
					if (senser.linkState.equals(SenserInfo.SENSERSTATE_DISCONNECTED))
						senser.listenValue = "---------------";
					Main.mainView.table.setValueAt(senser.listenValue, i, 4);
				}
			}
			sensermsg[i] = msgStrings[i];
		}
		if (this.equals(Main.gatewayManger.getVoiceGateway(Main.mainView.tree.getLastNode().toString()))) {
			Main.mainView.clearTable(i);
		}
		return SenserCnt;
	}

	public void PekingPowerMsgDeal(byte[] msg) {
		String[] ctrState = new String[] { "电池供电", "电池放电截止", "电池充电", "外部电源供电", "保护状态", "对外输出停止", "容量自检", "均衡状态",
				"可开盖状态", "静置状态", "启动初始化", "电池放电实验" };
		switch (msg[0] & 0x07) {
		case 0x00:
			Main.pekingPowerView.table.setValueAt("交流供电", 1, 0);
			break;
		case 0x02:
			Main.pekingPowerView.table.setValueAt("电池充电", 1, 0);
			break;
		case 0x07:
			Main.pekingPowerView.table.setValueAt("电池供电", 1, 0);
			break;
		case 0x06:
			Main.pekingPowerView.table.setValueAt("电池放电实验", 1, 0);
			break;
		}
		int i = 1;
		Main.pekingPowerView.table.setValueAt(String.valueOf(NomalDataSwitch.abs(msg[i++])) + "%", 1, 1);

		Main.pekingPowerView.table.setValueAt(String.valueOf(((double) NomalDataSwitch.abs(msg[i++])) / 10) + "V", 1,
				2);
		Main.pekingPowerView.table.setValueAt(String.valueOf(((double) NomalDataSwitch.abs(msg[i++])) * 5) + "mA", 1,
				3);

		Main.pekingPowerView.table.setValueAt(String.valueOf(((double) NomalDataSwitch.abs(msg[i++])) / 10) + "V", 3,
				0);
		Main.pekingPowerView.table.setValueAt(String.valueOf(((double) NomalDataSwitch.abs(msg[i++])) * 5) + "mA", 3,
				1);

		Main.pekingPowerView.table.setValueAt(String.valueOf(((double) NomalDataSwitch.abs(msg[i++])) / 10) + "V", 3,
				2);
		Main.pekingPowerView.table.setValueAt(String.valueOf(((double) NomalDataSwitch.abs(msg[i++])) * 5) + "mA", 3,
				3);

		Main.pekingPowerView.table.setValueAt(String.valueOf(((double) NomalDataSwitch.abs(msg[i++])) / 10) + "V", 5,
				0);
		Main.pekingPowerView.table.setValueAt(String.valueOf(((double) NomalDataSwitch.abs(msg[i++])) * 5) + "mA", 5,
				1);

		Main.pekingPowerView.table.setValueAt(String.valueOf(((double) NomalDataSwitch.abs(msg[i++])) / 10) + "V", 5,
				2);
		Main.pekingPowerView.table.setValueAt(String.valueOf(((double) NomalDataSwitch.abs(msg[i++])) * 5) + "mA", 5,
				3);

		Main.pekingPowerView.table.setValueAt(String.valueOf(((double) NomalDataSwitch.abs(msg[i++])) / 10) + "V", 7,
				0);
		Main.pekingPowerView.table.setValueAt(String.valueOf(((double) NomalDataSwitch.abs(msg[i++])) * 5) + "mA", 7,
				1);

		Main.pekingPowerView.table.setValueAt(String.valueOf(((double) NomalDataSwitch.abs(msg[i++])) / 10) + "V", 7,
				2);
		Main.pekingPowerView.table.setValueAt(String.valueOf(((double) NomalDataSwitch.abs(msg[i++])) * 5) + "mA", 7,
				3);

		Main.pekingPowerView.table.setValueAt(String.valueOf(((double) NomalDataSwitch.abs(msg[i++])) / 10) + "V", 9,
				0);
		Main.pekingPowerView.table.setValueAt(String.valueOf(((double) NomalDataSwitch.abs(msg[i++])) * 5) + "mA", 9,
				1);

		Main.pekingPowerView.table.setValueAt("----------", 9, 2);
		i++;
		Main.pekingPowerView.table.setValueAt("----------", 9, 3);
		i++;

		Main.pekingPowerView.table.setValueAt(String.valueOf(NomalDataSwitch.getValue(msg[i++], msg[i++])) + "mV", 11,
				0);
		Main.pekingPowerView.table.setValueAt(String.valueOf(NomalDataSwitch.getValue(msg[i++], msg[i++])) + "mA", 11,
				1);
		Main.pekingPowerView.table.setValueAt(String.valueOf(NomalDataSwitch.getValue(msg[i++], msg[i++])) + "mA", 11,
				2);
		Main.pekingPowerView.table.setValueAt(String.valueOf(NomalDataSwitch.getValue(msg[i++], msg[i++])) + "wH", 11,
				3);

		Main.pekingPowerView.table.setValueAt(String.valueOf(NomalDataSwitch.getValue(msg[i++], msg[i++]) / 10) + "%",
				13, 0);
		Main.pekingPowerView.table.setValueAt(String.valueOf(((double) NomalDataSwitch.abs(msg[i++])) / 10) + "V", 13,
				1);
		Main.pekingPowerView.table.setValueAt(String.valueOf(((double) NomalDataSwitch.abs(msg[i++]))) + "Hz", 13, 2);
		Main.pekingPowerView.table.setValueAt(String.valueOf(((double) NomalDataSwitch.abs(msg[i++])) / 10) + "V", 13,
				3);

		Main.pekingPowerView.table.setValueAt(String.valueOf(((double) NomalDataSwitch.abs(msg[i++])) / 10) + "Hz", 15,
				0);
		Main.pekingPowerView.table.setValueAt(String.valueOf(NomalDataSwitch.getValue(msg[i++], msg[i++])) + "Min", 15,
				1);
		Main.pekingPowerView.table.setValueAt(String.valueOf(NomalDataSwitch.getValue(msg[i++], msg[i++]) / 10) + "%",
				15, 2);
		Main.pekingPowerView.table.setValueAt(ctrState[msg[i]], 15, 3);
	}

	public void guanlianAddrDeal(byte[] msg) {
		int addrCnt, i;
		addrCnt = msg[10] - 3;
		for (i = 0; i < addrCnt; i++)
			Main.acterView.table_1.setValueAt(NomalDataSwitch.abs(msg[i + 12]), i, 0);
		for (; i < 6; i++)
			Main.acterView.table_1.setValueAt("----------", i, 0);
	}

	public void guanlianTypeDeal(byte[] msg) {
		int typeCnt, i;
		typeCnt = msg[10] - 3;
		for (i = 0; i < typeCnt; i++)
			Main.acterView.table_1.setValueAt(typeFlagTotypeString((int) msg[i + 12]), i, 1);
		for (; i < 6; i++)
			Main.acterView.table_1.setValueAt("----------", i, 1);
	}

	public String typeFlagTotypeString(int flag) {
		int i;
		String out = "";
		String[] typeString = new String[] { " 上限断电 ", " 上限报警 ", " 下限断电 ", " 下限报警 ", " 断线闭锁 ", " 0态报警 ", " 1态报警 " };
		for (i = 0; i < 7; i++) {
			if ((flag & 0x01) == 0x01)
				out += typeString[i];
			flag >>= 1;
		}
		return out;
	}

	private class ListenGatewayMsg extends Thread {
		private byte[] recvMsg = new byte[600];
		private byte[] gatewayAck;
		private String gatewayAck1;
		private int recvCnt = 0;
		private String macAdd = "";
		private int i, j, k;
		private byte[] adpcmBuf = new byte[500];
		private byte[] pcm = new byte[2000];
		private byte[] senserinfo1;
		byte cnt;
		byte[] canData;
		byte[] send;
		private String tString, tString1, tString2;
		private int canDataCnt = 1;
		volatile boolean listen = true;

		@SuppressWarnings("static-access")
		@Override
		public void run() {
			while (listen) {
				if (inputStream != null) {
					try {
						if ((recvCnt = inputStream.read(recvMsg, 0, recvMsg.length)) != -1) {
							checkAlive.checkCnt = 0;
							tString = Main.datetool.getTimeHMS();
							tString2 = Main.datetool.getTimeHMSS();
							recvTime = System.currentTimeMillis();
							gatewayAck = null;
							gatewayAck = new byte[recvCnt];
							System.arraycopy(recvMsg, 0, gatewayAck, 0, recvCnt);
							gatewayAck1 = NomalDataSwitch.bytesToHexString(gatewayAck);
							Main.debugView.addMsg(
									Main.datetool.getDateString(System.currentTimeMillis()) + "网关应答：\n" + gatewayAck1,
									ShowType.SHOWTYPE_VOICE, Color.BLACK, ipString.substring(0, ipString.indexOf(":")));
							if (((((gatewayAck[0] == 0x55) && (gatewayAck[1] == (byte) 0xAA))
									&& (gatewayAck[2] == 0x55)) && (gatewayAck[3] == (byte) 0xAA))) {
								pcmPlayer.sdl.start();
								System.arraycopy(gatewayAck, 7, adpcmBuf, 0, recvCnt - 7);
								pcm = adpcm.ADPCMToPCM(adpcmBuf, recvCnt - 7);
								pcmPlayer.play(pcm);
								if (!tolkView.isVisible())
									tolkView.setVisible(true);
								tolking = true;
								tolkView.freshTolkTime();
								tolkView.castaddr.setText(String.valueOf(gatewayAck[4]));
								j = NomalDataSwitch.abs(gatewayAck[5]);
								k = NomalDataSwitch.abs(gatewayAck[6]);
								i = k * 256 + j;
								Main.debugView.addMsg(
										Main.datetool.getDateString(recvTime) + "Recv： 接收字节数：" + String.valueOf(recvCnt)
												+ "  广播地址：" + NomalDataSwitch.byteToDecimalString(gatewayAck[4])
												+ "  帧序号：" + String.valueOf(i) + " \n"
												+ NomalDataSwitch.bytesToHexString(gatewayAck),
										ShowType.SHOWTYPE_VOICE, MsgColor.GRASS, "");
								Main.logRecoder.saveLog(
										LogFileName.Log_VoiceRecv + "\\" + ipString.substring(0, ipString.indexOf(":"))
												+ "\\" + String.valueOf(gatewayAck[4]) + "\\" + Main.datetool.getTimeH()
												+ ".log",
										Main.datetool.getDateString(recvTime) + "Recv： 接收字节数：" + String.valueOf(recvCnt)
												+ "  广播地址：" + NomalDataSwitch.byteToDecimalString(gatewayAck[4])
												+ "  帧序号：" + String.valueOf(i) + " \n"
												+ NomalDataSwitch.bytesToHexString(gatewayAck) + "\n\r");
							} else {
								switch (gatewayAck[9]) {
								case 0x11:// 广播基本参数
									if (recvMsg[12] == 0x28) {
										Main.debugView.addMsg(
												"当前音量：" + NomalDataSwitch.byteToDecimalString(recvMsg[15]),
												ShowType.SHOWTYPE_VOICE, Color.BLUE,
												ipString.substring(0, ipString.indexOf(":")));
										Main.musicPlayView.volumslider.setValue(recvMsg[15]);
										Main.acterView.table.setValueAt(
												Main.senserFactory.byteToSenserTypeString(recvMsg[12]), 1, 0);
										Main.acterView.table.setValueAt(
												String.valueOf(NomalDataSwitch.byteToDecimalString(recvMsg[13])) + "次",
												1, 2);
										Main.acterView.table.setValueAt(
												String.valueOf(((double) NomalDataSwitch.abs(recvMsg[14])) / 10) + "V",
												1, 3);
										Main.acterView.table
												.setValueAt(
														String.valueOf((NomalDataSwitch.abs(recvMsg[17])
																+ (NomalDataSwitch.abs(recvMsg[18]) * 256))) + "ms",
														1, 4);
									} else if (Main.senserFactory.byteToSenserTypeString(recvMsg[12])
											.equals(SenserType.SENSERTYPE_BREAKER)
											|| Main.senserFactory.byteToSenserTypeString(recvMsg[12])
													.equals(SenserType.SENSERTYPE_CARDREADER)) {
										Main.acterView.table.setValueAt(
												Main.senserFactory.byteToSenserTypeString(recvMsg[12]), 1, 0);
										Main.acterView.table.setValueAt(
												String.valueOf(NomalDataSwitch.byteToDecimalString(recvMsg[13])) + "次",
												1, 2);
										Main.acterView.table.setValueAt(
												String.valueOf(((double) NomalDataSwitch.abs(recvMsg[14])) / 10) + "V",
												1, 3);
										Main.acterView.table
												.setValueAt(
														String.valueOf((NomalDataSwitch.abs(recvMsg[17])
																+ (NomalDataSwitch.abs(recvMsg[18]) * 256))) + "ms",
														1, 4);
									} else {
										Main.senserInfoView.table.setValueAt(
												Main.senserFactory.byteToSenserTypeString(recvMsg[12]), 1, 0);
										Main.senserInfoView.table.setValueAt(
												String.valueOf(NomalDataSwitch.byteToDecimalString(recvMsg[13])) + "次",
												1, 2);
										Main.senserInfoView.table.setValueAt(
												String.valueOf(((double) NomalDataSwitch.abs(recvMsg[14])) / 10) + "V",
												1, 3);
										Main.senserInfoView.table
												.setValueAt(
														String.valueOf((NomalDataSwitch.abs(recvMsg[17])
																+ (NomalDataSwitch.abs(recvMsg[18]) * 256))) + "ms",
														1, 4);
									}
									break;
								case 0x14:
									Main.senserInfoView.table_1
											.setValueAt(NomalDataSwitch.bytesToDouble(recvMsg[12], recvMsg[13]), 1, 0);
									Main.senserInfoView.table_1
											.setValueAt(NomalDataSwitch.bytesToDouble(recvMsg[14], recvMsg[15]), 1, 1);
									Main.senserInfoView.table_1
											.setValueAt(NomalDataSwitch.bytesToDouble(recvMsg[16], recvMsg[17]), 1, 2);
									break;
								case 0x15:
									Main.senserInfoView.table_1
											.setValueAt(NomalDataSwitch.bytesToDouble(recvMsg[12], recvMsg[13]), 3, 0);
									Main.senserInfoView.table_1
											.setValueAt(NomalDataSwitch.bytesToDouble(recvMsg[14], recvMsg[15]), 3, 1);
									Main.senserInfoView.table_1
											.setValueAt(NomalDataSwitch.bytesToDouble(recvMsg[16], recvMsg[17]), 3, 2);
									break;
								case 0x19:
									guanlianAddrDeal(recvMsg);
									break;
								case 0x1C:
									guanlianTypeDeal(recvMsg);
									break;
								case 0x60:// 语音网关实时信息
									senserinfo1 = null;
									senserinfo1 = new byte[2000];
									System.arraycopy(recvMsg, 0, senserinfo1, 0, recvCnt);
									send = new byte[gatewayAck.length + 5];
									send[0] = (byte) 0xFE;
									send[1] = (byte) 0xFE;
									send[2] = (byte) 0xFE;
									send[3] = (byte) 0xFE;
									send[4] = 0x00;
									System.arraycopy(gatewayAck, 0, send, 5, gatewayAck.length);
									if (Main.alilink == true) {
//										Main.secTick++;
//										if (Main.secTick >= 20) {
//											Main.secTick = 0;
//											Main.aliLinker.reLink();
//										}
										Main.aliLinker.SendData(send);
									}
									senserInfo = senserinfo1;
									cnt = (byte) freshMode(senserinfo1, ipString.substring(0, ipString.indexOf(":")));
									senserCnt = NomalDataSwitch.byteToDecimalString(cnt);
									if (Main.mainView.tree.getLastNode() != null) {
										if (Main.mainView.tree.getLastNode().toString().equals(ipString))
											Main.mainView.gatewayInfotable.setValueAt(senserCnt, 4, 1);
									}
									Main.debugView.addVoiceGatewayInfo(ipString, msgStrings, senserStrings,
											ShowType.SHOWTYPE_VOICE, MsgColor.BLUE1);
									Main.debugView.addMsg("语音网关 " + ipString + " 传感器信息获取成功", ShowType.SHOWTYPE_VOICE,
											MsgColor.GREEN, ipString.substring(0, ipString.indexOf(":")));
									Main.debugView.addMsg(show1, ShowType.SHOWTYPE_VOICE, Color.BLACK,
											ipString.substring(0, ipString.indexOf(":")));
									break;
								case 0x72:// 语音网关固件版本号
									Main.debugView.addMsg(
											Main.datetool.getDateString(recvTime) + "Recv："
													+ NomalDataSwitch.bytesToHexString(gatewayAck),
											ShowType.SHOWTYPE_VOICE, MsgColor.BLACK,
											ipString.substring(0, ipString.indexOf(":")));
									appVer = ("V" + NomalDataSwitch.byteToDecimalString(gatewayAck[12]).substring(0, 1)
											+ "." + NomalDataSwitch.byteToDecimalString(recvMsg[12]).substring(1));
									Main.debugView.addMsg("语音网关 " + ipString + " 版本号：" + appVer,
											ShowType.SHOWTYPE_VOICE, MsgColor.GREEN, "");
									if (Main.mainView.tree.getLastNode() != null) {
										if (Main.mainView.tree.getLastNode().toString().equals(ipString))
											Main.mainView.gatewayInfotable.setValueAt(appVer, 3, 1);
									}
									Main.debugView.addMsg(show, ShowType.SHOWTYPE_VOICE, MsgColor.BLACK,
											ipString.substring(0, ipString.indexOf(":")));
									SoftVerGeted = true;
									break;
								case (byte) 0x88:
									Main.debugView.addMsg(
											Main.datetool.getDateString(recvTime) + "Recv："
													+ NomalDataSwitch.bytesToHexString(gatewayAck),
											ShowType.SHOWTYPE_VOICE, MsgColor.BLACK,
											ipString.substring(0, ipString.indexOf(":")));
									for (int i = 0; i < 6; i++) {
										macAdd += NomalDataSwitch.byteToDecimalString(gatewayAck[i + 12]);
										if (i < 5)
											macAdd += " : ";
									}
									macAddr = macAdd;
									if (Main.mainView.tree.getLastNode() != null) {
										if (Main.mainView.tree.getLastNode().toString().equals(ipString))
											Main.mainView.gatewayInfotable.setValueAt(macAddr, 2, 1);
									}
									Main.debugView.addMsg("语音网关 " + ipString + " Mac地址：" + macAddr,
											ShowType.SHOWTYPE_VOICE, MsgColor.GREEN,
											ipString.substring(0, ipString.indexOf(":")));
									macAdd = "";
									Main.debugView.addMsg(show, ShowType.SHOWTYPE_VOICE, MsgColor.BLACK,
											ipString.substring(0, ipString.indexOf(":")));
									MacGeted = true;
									break;
								case 0x69:
									// Main.aliLinker.SendData(gatewayAck);
									if (sendCnt != 0) {
										i = NomalDataSwitch.abs(gatewayAck[12]);
										j = NomalDataSwitch.abs(gatewayAck[13]);
										bufferUsed = (j * 256) + i;
										Main.musicPlayView.voicebuffer.setValue(bufferUsed);
										i = NomalDataSwitch.abs(gatewayAck[14]);
										j = NomalDataSwitch.abs(gatewayAck[15]);
										recvCnt = (j * 256) + i;
										if ((recvCnt == (sendCnt - 1)))
											Main.debugView.appandMsg(
													"********************************************缓存区占用量***********************************************\n"
															+ "----------------------------------------------------------------"
															+ Main.datetool.getDateString(recvTime) + "----------应答延迟："
															+ Main.datetool.getDelayString(recvTime - sendTime)
															+ "-----------------------------------------\n"
															+ NomalDataSwitch.bytesToHexString(gatewayAck) + "\n"
															+ "缓存区占用量：" + String.valueOf(bufferUsed) + "字节---" + "发送序号："
															+ String.valueOf(sendCnt - 1) + "---" + "应答序号："
															+ String.valueOf(recvCnt) + "---" + "帧序号检验正确---"
															+ "帧序号错误计数：" + String.valueOf(errCnt)
															+ "\n********************************************************************************************************************************\n",
													ShowType.SHOWTYPE_VOICEDATA, MsgColor.BLUE, ipString);
										else {
											Main.debugView.appandMsg(
													"*************************************************缓存区占用量*************************************************\n"
															+ "----------------------------------------------------------------"
															+ Main.datetool.getDateString(recvTime) + "----------应答延迟："
															+ Main.datetool.getDelayString(recvTime - sendTime)
															+ "-----------------------------------------\n"
															+ NomalDataSwitch.bytesToHexString(gatewayAck) + "\n"
															+ "缓存区占用量：" + String.valueOf(bufferUsed) + "字节---" + "发送序号："
															+ String.valueOf(sendCnt - 1) + "---" + "应答序号："
															+ String.valueOf(recvCnt) + "---" + "帧序号检验错误---"
															+ "帧序号错误计数：" + String.valueOf(errCnt)
															+ "\n********************************************************************************************************************************\n",
													ShowType.SHOWTYPE_VOICEDATA, MsgColor.RED, ipString);
											errCnt++;
										}
									}
									break;

								case (byte) 0xCC:
									canData = new byte[3 + recvMsg[8]];
									System.arraycopy(recvMsg, 6, canData, 0, 3);
									System.arraycopy(recvMsg, 10, canData, 3, recvMsg[8]);
									Main.logRecoder.saveLog(
											LogFileName.Log_InitData_Voice
													+ ipString.substring(0, ipString.indexOf(":")) + "\\"
													+ Main.datetool.getTimeH() + "-InitData.log",
											tString2 + NomalDataSwitch.getEmp2(tString2)
													+ NomalDataSwitch.bytesToHexString(canData)
													+ "-------->网关下发初始化信息,初始化设备地址："
													+ NomalDataSwitch.byteToDecimalString(canData[1]) + "\r\n");

									break;
								case (byte) 0xEB:
									canData = new byte[4 + recvMsg[8]];
									int tick;

									System.arraycopy(recvMsg, 10, canData, 0, canData.length);
									int CanId = (NomalDataSwitch.abs(canData[0]) << 24)
											+ (NomalDataSwitch.abs(canData[1]) << 16)
											+ (NomalDataSwitch.abs(canData[2]) << 8) + NomalDataSwitch.abs(canData[3]);

									if (((CanId >> 17) & 0x7F) == 0x55) {
										send = new byte[canData.length + 5];
										send[0] = (byte) 0xFE;
										send[1] = (byte) 0xFE;
										send[2] = (byte) 0xFE;
										send[3] = (byte) 0xFE;
										send[4] = 0x01;
										// System.arraycopy(canData, 0, send, 5, canData.length);
										// Main.aliLinker.SendData(send);
										String fram = "帧序号：" + String.valueOf(((CanId >> 24) & 0x00000000F));
										String addr1 = Main.datetool.getTimess(
												((CanId & 0xFF) + (2 * ((CanId >> 24) & 0x00000000F) + 1) * 1000));
										String addr2 = Main.datetool.getTimess(
												((CanId & 0xFF) + (2 * ((CanId >> 24) & 0x00000000F) + 2) * 1000));
										String type1 = Main.datetool
												.getTimess(((NomalDataSwitch.abs(canData[7]) & 0x3F) * 1000));
										String type2 = Main.datetool
												.getTimess(((NomalDataSwitch.abs(canData[11]) & 0x3F) * 1000));
										String swAddr = String.valueOf(CanId & 0xFF);
										if ((NomalDataSwitch.abs(canData[7]) & 0x3F) == 0x3F)
											type1 = "FF";
										if ((NomalDataSwitch.abs(canData[11]) & 0x3F) == 0x3F)
											type2 = "FF";
										Main.logRecoder.saveLog(
												System.getProperty("user.dir") + "\\Logs\\SwitchUpload\\"
														+ Main.datetool.getTimeYMD() + "\\" + swAddr
														+ "\\SwitchSenInfo--" + Main.datetool.getTimeHH() + ".txt",
												Main.datetool.getTimeHMS() + "[" + addr1 + "#: "
														+ NomalDataSwitch.byteToHexString(canData[4]) + " "
														+ NomalDataSwitch.byteToHexString(canData[5]) + " "
														+ NomalDataSwitch.byteToHexString(canData[6]) + " "
														+ NomalDataSwitch.byteToHexString(canData[7]) + " 设备类型：" + type1
														+ " ]---" + "[" + addr2 + "#: "
														+ NomalDataSwitch.byteToHexString(canData[8]) + " "
														+ NomalDataSwitch.byteToHexString(canData[9]) + " "
														+ NomalDataSwitch.byteToHexString(canData[10]) + " "
														+ NomalDataSwitch.byteToHexString(canData[11]) + " 设备类型："
														+ type2 + "]---" + fram + "\r\n");
									}
									if (((CanId >> 17) & 0x7F) == 0x56) {
										send = new byte[canData.length + 5];
										send[0] = (byte) 0xFE;
										send[1] = (byte) 0xFE;
										send[2] = (byte) 0xFE;
										send[3] = (byte) 0xFE;
										send[4] = 0x02;
										String swAddr = String.valueOf(CanId & 0xFF);
										Main.logRecoder.saveLog(
												System.getProperty("user.dir") + "\\Logs\\SwitchUpload\\"
														+ Main.datetool.getTimeYMD() + "\\" + swAddr
														+ "\\SwitchCtrInfo--" + Main.datetool.getTimeHH() + ".txt",
												Main.datetool.getTimeHMS() + NomalDataSwitch.bytesToHexString(canData)
														+ "\r\n");
										// System.arraycopy(canData, 0, send, 5, canData.length);
										// Main.aliLinker.SendData(send);
									}
									if (tString.equals(tString1)) {
										canDataCnt++;
										Main.logRecoder.saveLog(
												LogFileName.Log_CanData_Voice
														+ ipString.substring(0, ipString.indexOf(":")) + "\\"
														+ Main.datetool.getTimeH() + "-CanData.log",
												"\r\n" + Main.datetool.getTimeHMSS()
														+ NomalDataSwitch.bytesToHexString(canData)
														+ NomalDataSwitch
																.getEmp3(NomalDataSwitch.bytesToHexString(canData))
														+ NomalDataSwitch.canDataToString(canData));
									} else {
										Main.logRecoder.saveLog(
												LogFileName.Log_CanData_Voice
														+ ipString.substring(0, ipString.indexOf(":")) + "\\"
														+ Main.datetool.getTimeH() + "-CanData.log",
												"\r\n********************【Can数据计数：" + String.valueOf(canDataCnt)
														+ "】********************\n\n************************" + tString
														+ "***********************");
										canDataCnt = 1;
										Main.logRecoder.saveLog(
												LogFileName.Log_CanData_Voice
														+ ipString.substring(0, ipString.indexOf(":")) + "\\"
														+ Main.datetool.getTimeH() + "-CanData.log",
												"\r\n" + Main.datetool.getTimeHMSS()
														+ NomalDataSwitch.bytesToHexString(canData)
														+ NomalDataSwitch
																.getEmp3(NomalDataSwitch.bytesToHexString(canData))
														+ NomalDataSwitch.canDataToString(canData));
									}
									tString1 = tString;
									break;
								case (byte) 0xCE:
									canData = new byte[4 + recvMsg[8]];
									System.arraycopy(recvMsg, 10, canData, 0, canData.length);
									if (tString.equals(tString1)) {
										canDataCnt++;
										Main.logRecoder.saveLog(
												LogFileName.Log_CanData_Voice
														+ ipString.substring(0, ipString.indexOf(":")) + "\\"
														+ Main.datetool.getTimeH() + "-CanData.log",
												"\r\n" + tString2 + NomalDataSwitch.getEmp2(tString2)
														+ NomalDataSwitch.bytesToHexString(canData)
														+ NomalDataSwitch
																.getEmp3(NomalDataSwitch.bytesToHexString(canData))
														+ NomalDataSwitch.canDataToString1(canData));
									} else {
										Main.logRecoder.saveLog(
												LogFileName.Log_CanData_Voice
														+ ipString.substring(0, ipString.indexOf(":")) + "\\"
														+ Main.datetool.getTimeH() + "-CanData.log",
												"\r\n********************【Can数据计数：" + String.valueOf(canDataCnt)
														+ "】********************\n\n************************" + tString
														+ "***********************");
										canDataCnt = 1;
										Main.logRecoder.saveLog(
												LogFileName.Log_CanData_Voice
														+ ipString.substring(0, ipString.indexOf(":")) + "\\"
														+ Main.datetool.getTimeH() + "-CanData.log",
												"\r\n" + tString2 + NomalDataSwitch.getEmp2(tString2)
														+ NomalDataSwitch.bytesToHexString(canData)
														+ NomalDataSwitch
																.getEmp3(NomalDataSwitch.bytesToHexString(canData))
														+ NomalDataSwitch.canDataToString1(canData));
									}
									tString1 = tString;
									break;
								case (byte) 0xCD:// 语音线检测
									canData = new byte[4 + recvMsg[8]];
									System.arraycopy(recvMsg, 10, canData, 0, canData.length);

									Main.logRecoder.saveLog(
											LogFileName.Log_Rs485Data + ipString.substring(0, ipString.indexOf(":"))
													+ "\\" + Main.datetool.getTimeH() + "-Rs485.log",
											tString2 + NomalDataSwitch.getEmp2(tString2)
													+ NomalDataSwitch.bytesToHexString(canData)
													+ NomalDataSwitch.getEmp3(NomalDataSwitch.bytesToHexString(canData))
													+ NomalDataSwitch.Rs485DataToString(canData) + "\r\n");
									break;
								case (byte) 0xED:
									senserinfo1 = new byte[39];
									System.arraycopy(recvMsg, 13, senserinfo1, 0, 39);
									PekingPowerMsgDeal(senserinfo1);
									break;

								case (byte) 0xEF:// 综保信息
									senserinfo1 = new byte[114];
									System.arraycopy(recvMsg, 20, senserinfo1, 0, 114);
									ipd.FreshIPD(senserinfo1);
									break;
								case (byte) 0xEA:
									senserinfo1 = new byte[600];
									senserInfo = new byte[2];
									senserInfo[0] = recvMsg[13];
									senserInfo[1] = recvMsg[12];

									System.arraycopy(recvMsg, 12, senserinfo1, 0, 400);
									int i, j = 0;
									for (i = 0; i < 200; i++) {
										if (senserinfo1[3 * i] != 0) {
											Main.excuteView.table_1.setValueAt(
													NomalDataSwitch.byteToDecimalString(senserinfo1[3 * i]), j, 1);
											Main.excuteView.table_1.setValueAt(
													NomalDataSwitch.byteToDecimalString(senserinfo1[3 * i + 1]), j, 2);
											Main.excuteView.table_1
													.setValueAt(typeFlagTotypeString(senserinfo1[3 * i + 2]), j, 3);
											j++;
										}
									}
									for (; j < 200; j++) {
										Main.excuteView.table_1.setValueAt("----", j, 1);
										Main.excuteView.table_1.setValueAt("----", j, 2);
										Main.excuteView.table_1.setValueAt("----", j, 3);
									}
									Main.excuteView.setVisible(true);
									break;
								case 0x33:
									int uploadtime;
									uploadtime = NomalDataSwitch.abs(recvMsg[13]);
									uploadtime <<= 8;
									uploadtime |= NomalDataSwitch.abs(recvMsg[12]);
									Main.uploadTimeSetView.curTime.setText(String.valueOf(uploadtime) + " Ms");
									break;
								}
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
						Main.debugView.addMsg(e.getMessage(), ShowType.SHOWTYPE_VOICE, MsgColor.RED, "");
						Main.gatewayManger.relinkGateway(ipString);
					}
				}
			}
			try {
				if (inputStream != null)
					inputStream.close();
				if (outputStream != null)
					outputStream.close();
				if (socket != null)
					socket.close();
				inputStream = null;
				outputStream = null;
				socket = null;
				listenGatewayMsg = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public class GetVoiceGatewayInfo extends TimerTask {

		@Override
		public void run() {
			if (!SoftVerGeted) {
				Main.debugView.addMsg(
						Main.datetool.getDateString(System.currentTimeMillis()) + "正在获取 " + gatewayType + ipString
								+ " 固件版本",
						ShowType.SHOWTYPE_VOICE, MsgColor.BLUE, ipString.substring(0, ipString.indexOf(":")));
				sendCmd(getVerCmd);
			} else if (!MacGeted) {
				Main.debugView.addMsg(
						Main.datetool.getDateString(System.currentTimeMillis()) + "正在获取 " + gatewayType + ipString
								+ " Mac地址",
						ShowType.SHOWTYPE_VOICE, MsgColor.BLUE, ipString.substring(0, ipString.indexOf(":")));
				sendCmd(getMacCmd);
			} else {
				Main.debugView.addMsg(
						Main.datetool.getDateString(System.currentTimeMillis()) + "正在获取 " + gatewayType + ipString
								+ " 传感器信息",
						ShowType.SHOWTYPE_VOICE, MsgColor.BLUE, ipString.substring(0, ipString.indexOf(":")));
				sendCmd(getInfoCmd);
			}
		}
	}

	public class CheckAlive extends TimerTask {
		volatile int checkCnt = 0;

		@Override
		public void run() {
//			if (checkCnt >= 3)
//				Main.gatewayManger.relinkGateway(ipString);
			checkCnt++;
		}
	}
}
