package com.fmkj.gateways;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import com.fmkj.Begin.Main;
import com.fmkj.tools.IpConfigReader;
import com.fmkj.values.GatewayType;

public class GatewayManger {
	private List<CanGateway> canGateways = new ArrayList<CanGateway>();
	private List<VoiceGateway> voiceGateways = new ArrayList<VoiceGateway>();
	private CanGateway canGateway;
	public IpConfigReader ipConfigReader = new IpConfigReader();
	private VoiceGateway voiceGateway;
	@SuppressWarnings("unused")
	private boolean getvoiceboardinfo = true;
	public Timer timer = new Timer();
	
	public void setGetvoiceboardinfo(boolean getvoiceboardinfo) {
		this.getvoiceboardinfo = getvoiceboardinfo;
	}


	public CanGateway getCangateway(String ipString) {
		int i = 0;
		for (i = 0; i < canGateways.size(); i++) {
			if (ipString.equals(canGateways.get(i).ipString))
				return canGateways.get(i);
		}
		return null;
	}

	public VoiceGateway getVoiceGateway(String ipString) {
		int i = 0;
		for (i = 0; i < voiceGateways.size(); i++) {
			if (ipString.equals(voiceGateways.get(i).ipString))
				return voiceGateways.get(i);
		}
		return null;
	}

	public boolean isGatewayExit(String ipString) {
		int i = 0;
		for (i = 0; i < canGateways.size(); i++) {
			if (ipString.equals(canGateways.get(i).ipString))
				return true;
		}
		for (i = 0; i < voiceGateways.size(); i++) {
			if (ipString.equals(voiceGateways.get(i).ipString))
				return true;
		}
		return false;
	}

	public void removeGateway(String ipString) {
		int i = 0;
		for (i = 0; i < canGateways.size(); i++) {
			if (ipString.equals(canGateways.get(i).ipString)) {
				canGateways.get(i).close();
				canGateways.remove(i);
			}
		}
		for (i = 0; i < voiceGateways.size(); i++) {
			if (ipString.equals(voiceGateways.get(i).ipString))
				voiceGateway = voiceGateways.get(i);
				voiceGateways.remove(i);
				voiceGateway.close();
				voiceGateway = null;
		}
	}
	
	public void relinkGateway(String ipString)
	{
		int i = 0;
		for (i = 0; i < canGateways.size(); i++) {
			if (ipString.equals(canGateways.get(i).ipString)) {
				canGateways.get(i).close();
				canGateways.remove(i);
				reLinkGateway(GatewayType.GATEWAYTYPE_CAN, ipString);
			}
		}
		for (i = 0; i < voiceGateways.size(); i++) {
			if (ipString.equals(voiceGateways.get(i).ipString))
				voiceGateway = voiceGateways.get(i);
				voiceGateways.remove(i);
				voiceGateway.close();
				voiceGateway = null;
				reLinkGateway(GatewayType.GATEWAYTYPE_VOICE, ipString);
		}
	}

	public String[] listVoicegatewayIp() {
		String[] out = new String[voiceGateways.size()];
		for (int i = 0; i < voiceGateways.size(); i++) {
			out[i] = voiceGateways.get(i).ipString;
		}
		return out;
	}

	public String[] listCangatewayIp() {
		String[] out = new String[canGateways.size()];
		for (int i = 0; i < canGateways.size(); i++) {
			out[i] = canGateways.get(i).ipString;
		}
		return out;
	}

//	public void sendGatewatInfos(IoSession session) {
//		int i = 0;
//		CanGateway gateway;
//		LocalAckGatewayInfo ackGatewayInfo = new LocalAckGatewayInfo();
//		List<byte[]> ackData = new ArrayList<byte[]>();
//		for (i = 0; i < canGateways.size(); i++) {
//			gateway = canGateways.get(i);
//			if (gateway != null) {
//				ackData.add(gateway.senserInfo);
//			}
//		}
//		ackGatewayInfo.setTargetPhone("菲莫公司展板");
//		ackGatewayInfo.setGatewayInfos(ackData);
//		session.write(ackGatewayInfo);
//	}

	public void creatNewGateway(String gatewayType, String gatewayIpString) {
		switch (gatewayType) {
		case GatewayType.GATEWAYTYPE_CAN:
			canGateway = new CanGateway(gatewayIpString);
			canGateways.add(canGateway);
			Main.mainView.addGatewayNode(gatewayType, gatewayIpString);
			break;

		case GatewayType.GATEWAYTYPE_VOICE:
			VoiceGateway voiceGateway = new VoiceGateway(gatewayIpString);
			voiceGateways.add(voiceGateway);
			Main.mainView.addGatewayNode(gatewayType, gatewayIpString);
			break;
		}
	}
	
	public void reLinkGateway(String gatewayType, String gatewayIpString) {
		switch (gatewayType) {
		case GatewayType.GATEWAYTYPE_CAN:
			canGateway = new CanGateway(gatewayIpString);
			canGateways.add(canGateway);
			break;

		case GatewayType.GATEWAYTYPE_VOICE:
			VoiceGateway voiceGateway = new VoiceGateway(gatewayIpString);
			voiceGateways.add(voiceGateway);
			break;
		}
	}
	
	public void StartGetInfo(VoiceGateway voiceGateway)
	{
		timer.schedule(voiceGateway.gatewayInfo, 200L, 1000L);
		timer.schedule(voiceGateway.checkAlive, 200L,3000L);
	}
	
	public void StratGetInfo(CanGateway canGateway)
	{
		timer.schedule(canGateway.gatewayInfo, 200L, 1000L);
		timer.schedule(canGateway.checkAlive, 200L, 3000L);
	}
}
