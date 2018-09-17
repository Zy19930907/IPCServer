package com.fmkj.gateways;

public class AliLinkManger {
	private AliLinker aliLinker;
	
	public void LinkAliServer(String ipString) {
		aliLinker = new AliLinker(ipString);
	}
	
	
}
