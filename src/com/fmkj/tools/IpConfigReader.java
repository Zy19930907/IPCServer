package com.fmkj.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fmkj.Begin.Main;
import com.fmkj.values.GatewayType;

public class IpConfigReader {
	private File ipFile = new File(System.getProperty("user.dir")+"\\Configs\\ips.config");
	public BufferedReader ipReader; 
	private FileReader fileReader;
	public BufferedWriter ipWriter;
	public List<String> canIpStrings = new ArrayList<String>();
	public List<String> voiceIpStrings = new ArrayList<String>();

	public void readGatewaysFromFile() {
		 String ip;
		 try {
			fileReader = new FileReader(ipFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		ipReader = new BufferedReader(fileReader);
			try {
				while((ip = ipReader.readLine())!=null) {
					if(ip.substring(0, ip.indexOf(":")).endsWith("VOICE")) {
						Main.gatewayManger.creatNewGateway(GatewayType.GATEWAYTYPE_VOICE, ip.substring(ip.indexOf(":")+1));
					}else if(ip.substring(0, ip.indexOf(":")).endsWith("CAN")) {
						Main.gatewayManger.creatNewGateway(GatewayType.GATEWAYTYPE_CAN, ip.substring(ip.indexOf(":")+1));
					}
				}
				ipReader.close();
				fileReader.close();
				ipReader = null;
				fileReader = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}
