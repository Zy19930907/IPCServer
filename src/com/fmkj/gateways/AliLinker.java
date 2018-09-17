package com.fmkj.gateways;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.JOptionPane;

import com.fmkj.Begin.Main;
import com.fmkj.tools.IpSwitch;

public class AliLinker {
	private Socket socket = new Socket();
	private OutputStream outputStream;
	private InputStream inputStream;
	private InetSocketAddress address;
	private Link link;
	private volatile boolean listen = true;

	public AliLinker(String ipString) {
		address = IpSwitch.getInetSocketAdress(ipString);
		link = new Link();
		link.start();
	}

	public class Link extends Thread {
		@Override
		public void run() {
			try {
				socket.connect(address, 2000);
				if (socket != null) {
					JOptionPane.showMessageDialog(null, address.toString() + "连接成功");
					Main.alilink = true;
					outputStream = socket.getOutputStream();
					inputStream = socket.getInputStream();
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, address.toString() + "连接超时");
				e.printStackTrace();
			}
		}
	}

	public void reLink() {
		address = IpSwitch.getInetSocketAdress("47.52.204.23:12121");
		try {
			socket.close();
			outputStream.close();
			inputStream.close();
			link = null;
			link = new Link();
			link.start();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void SendData(byte[] data) {
		if (outputStream != null) {
			try {
				outputStream.write(data);
				outputStream.flush();
			} catch (IOException e) {
				try {
					socket.close();
					outputStream.close();
					inputStream.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
	}

	public class Listen extends Thread {
		private byte[] msg = new byte[1024];
		private byte[] data;
		private int recvLen;
		private int dataLen;

		@Override
		public void run() {
			while (listen) {
				try {
					if ((recvLen = inputStream.read(msg, 0, msg.length)) != -1) {
						data = null;
						data = new byte[recvLen];
						dataLen = msg[5];
						dataLen <<= 8;
						dataLen += msg[4];
						if (dataLen == recvLen) {
							System.arraycopy(msg, 0, data, 0, recvLen);
							if (msg[0] == 0xEF && msg[1] == 0xEF && msg[2] == 0xEF && msg[3] == 0xEF) {
								switch (data[6]) {
								case 0x00:
									break;
								case 0x01:
									break;
								}
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
