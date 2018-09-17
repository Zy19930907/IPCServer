package com.fmkj.tools;

import java.net.InetSocketAddress;

public class IpSwitch {
	public static InetSocketAddress getInetSocketAdress(String ipString) {
		return new InetSocketAddress(ipString.substring(0, ipString.indexOf(":")), stringToInt(ipString.substring(ipString.indexOf(":")+1)));
	}
	private static int stringToInt(String in) {
		        int i = 0;
		        int out = 0;
		        int temp=0;
		        int[] chengshu = new int[]{1,10,100,1000,10000};
		        for (i = 0; i < in.length();i++) {
		            temp=Integer.parseInt(in.substring(in.length() - i - 1, in.length() - i)) * chengshu[i];
		            out+=temp;
		        }
		        return out;
	}
}
