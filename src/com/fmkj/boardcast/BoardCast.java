package com.fmkj.boardcast;

import com.fmkj.Begin.Main;
import com.fmkj.gateways.VoiceGateway;
import com.fmkj.tools.CmdMaker;

public class BoardCast {
	public String voiceGatewayIp;
	public VoiceGateway voiceGateway;
	private byte[] cmd;
	private CmdMaker cmdMaker = new CmdMaker();
	public byte addr;

	public BoardCast(String voiceGatewayIp, byte addr) {
		this.voiceGatewayIp = voiceGatewayIp;
		this.addr = addr;
		voiceGateway = Main.gatewayManger.getVoiceGateway(voiceGatewayIp);
	}

	public void sendMusic() {
		if (voiceGateway != null) {
			cmd = cmdMaker.getMusicStartCmd(addr);
			voiceGateway.sendCmd(cmd);
			Main.gatewayManger.setGetvoiceboardinfo(false);
			voiceGateway.sendMusic(addr);
		}
	}

	public void stopSendMusic() {
		if (voiceGateway != null) {
			cmd = cmdMaker.getMusicEndCmd(addr);
			voiceGateway.sendCmd(cmd);
			voiceGateway.StopMusic();
			Main.gatewayManger.setGetvoiceboardinfo(true);
		}
	}

	public void startListen() {
		if (voiceGateway != null) {
			cmd = cmdMaker.getBoardCastListenCmd(addr);
			voiceGateway.sendCmd(cmd);
		}
	}

	public void stopListen() {
		if (voiceGateway != null) {
			cmd = cmdMaker.getDisBoardCastListenCmd(addr);
			voiceGateway.sendCmd(cmd);
		}
	}

	public void setVolum(byte volum) {
		if (voiceGateway != null) {
			cmd = cmdMaker.getSetVolumCmd(addr, volum);
			voiceGateway.sendCmd(cmd);
		}
	}

	public void getBoardCastInfo() {
		if (voiceGateway != null) {
			cmd = cmdMaker.getBoardCastInfo(addr);
			voiceGateway.sendCmd(cmd);
		}
	}
}
