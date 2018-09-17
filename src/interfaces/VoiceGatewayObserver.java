package interfaces;
import com.fmkj.gateways.VoiceGateway;

public interface VoiceGatewayObserver {
	public void onVoiceGatewayReceived(VoiceGateway gateway,byte[] msg);
	public void onVoiceGatewayLinkFailed(VoiceGateway gateway);
	public void onVoiceGatewayCreated(VoiceGateway gateway);
	public void onVoiceGatewayLinkSuccess(VoiceGateway gateway);
	public void onVoiceGatewayClosed(VoiceGateway gateway);
}
