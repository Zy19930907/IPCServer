package com.fmkj.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.fmkj.Begin.Main;
import com.fmkj.gateways.CanGateway;
import com.fmkj.gateways.VoiceGateway;

import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class IpdControlView extends JFrame {

	private JPanel contentPane;
	private JTextField LightingCurrent;
	private JTextField LightningBreak;
	private JTextField SignalCurrent;
	private JTextField SignalBreak;
	private JTextField UnderVolValue;
	private JTextField UnderVolDelay;
	private JTextField OverVolValue;
	private JTextField OverVolDelay;
	private JTextField LeakageResistance;
	private JTextField LeakageDelay;
	private JTextField CH4Delay;
	private JTextField DeviceAddr;
	public IpdControlView() {
		setResizable(false);
		setBounds(100, 100, 570, 283);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		JButton button_3 = new JButton("遥控合");
		button_3.setFont(new Font("楷体", Font.PLAIN, 12));
		button_3.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent arg0) {
				OutputStream outputStream;
				if (Main.mainView.tree.getLastNode() != null) {
					CanGateway gateway = Main.gatewayManger.getCangateway(
							Main.mainView.tree.getLastNode().toString());
					if(gateway!= null ) {
					try {
						outputStream = gateway.getoStream();
						outputStream.write(Main.cmdMaker.GetIPDOpenCmd());
						outputStream.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				}
			}
		});
		contentPane.setLayout(null);
		button_3.setBounds(221, 23, 122, 33);
		contentPane.add(button_3);
		
		JButton button_1 = new JButton("复位");
		button_1.setFont(new Font("楷体", Font.PLAIN, 12));
		button_1.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent arg0) {
				OutputStream outputStream;
				if (Main.mainView.tree.getLastNode() != null) {
					CanGateway gateway = Main.gatewayManger.getCangateway(
							Main.mainView.tree.getLastNode().toString());
					try {
						outputStream = gateway.getoStream();
						outputStream.write(Main.cmdMaker.GetIPDRstCmd());
						outputStream.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		button_1.setBounds(10, 23, 122, 33);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("遥控分");
		button_2.setFont(new Font("楷体", Font.PLAIN, 12));
		button_2.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent arg0) {
				OutputStream outputStream;
				if (Main.mainView.tree.getLastNode() != null) {
					CanGateway gateway = Main.gatewayManger.getCangateway(
							Main.mainView.tree.getLastNode().toString());
					try {
						outputStream = gateway.getoStream();
						outputStream.write(Main.cmdMaker.GetIPDCloseCmd());
						outputStream.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		button_2.setBounds(422, 23, 122, 33);
		contentPane.add(button_2);
		
		LightingCurrent = new JTextField();
		LightingCurrent.setHorizontalAlignment(SwingConstants.CENTER);
		LightingCurrent.setBounds(10, 86, 92, 21);
		contentPane.add(LightingCurrent);
		LightingCurrent.setColumns(10);
		
		LightningBreak = new JTextField();
		LightningBreak.setHorizontalAlignment(SwingConstants.CENTER);
		LightningBreak.setColumns(10);
		LightningBreak.setBounds(251, 86, 92, 21);
		contentPane.add(LightningBreak);
		
		SignalCurrent = new JTextField();
		SignalCurrent.setHorizontalAlignment(SwingConstants.CENTER);
		SignalCurrent.setColumns(10);
		SignalCurrent.setBounds(129, 86, 92, 21);
		contentPane.add(SignalCurrent);
		
		SignalBreak = new JTextField();
		SignalBreak.setHorizontalAlignment(SwingConstants.CENTER);
		SignalBreak.setColumns(10);
		SignalBreak.setBounds(369, 86, 92, 21);
		contentPane.add(SignalBreak);
		
		JButton IpdSet1 = new JButton("设置");
		IpdSet1.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent arg0) {
				int value1,value2,value3,value4;
				double v1,v2,v3,v4;
				v1 = Double.valueOf(LightingCurrent.getText());
				v2 = Double.valueOf(SignalCurrent.getText());
				v3 = Double.valueOf(LightningBreak.getText());
				v4 = Double.valueOf(SignalBreak.getText());
				value1 = (int)(v1*100);
				value2 = (int)(v2*100);
				value3 = (int)(v3*100);
				value4 = (int)(v4*100);
				OutputStream outputStream;
				if (Main.mainView.tree.getLastNode() != null) {
					CanGateway gateway = Main.gatewayManger.getCangateway(
							Main.mainView.tree.getLastNode().toString());
					try {
						outputStream = gateway.getoStream();
						outputStream.write(Main.cmdMaker.GetIPDSet1Cmd(value1, value2, value3, value4));
						outputStream.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		IpdSet1.setFont(new Font("楷体", Font.PLAIN, 12));
		IpdSet1.setBounds(477, 85, 67, 23);
		contentPane.add(IpdSet1);
		
		UnderVolValue = new JTextField();
		UnderVolValue.setHorizontalAlignment(SwingConstants.CENTER);
		UnderVolValue.setColumns(10);
		UnderVolValue.setBounds(10, 150, 92, 21);
		contentPane.add(UnderVolValue);
		
		UnderVolDelay = new JTextField();
		UnderVolDelay.setHorizontalAlignment(SwingConstants.CENTER);
		UnderVolDelay.setColumns(10);
		UnderVolDelay.setBounds(129, 150, 92, 21);
		contentPane.add(UnderVolDelay);
		
		OverVolValue = new JTextField();
		OverVolValue.setHorizontalAlignment(SwingConstants.CENTER);
		OverVolValue.setColumns(10);
		OverVolValue.setBounds(251, 150, 92, 21);
		contentPane.add(OverVolValue);
		
		OverVolDelay = new JTextField();
		OverVolDelay.setHorizontalAlignment(SwingConstants.CENTER);
		OverVolDelay.setColumns(10);
		OverVolDelay.setBounds(369, 150, 92, 21);
		contentPane.add(OverVolDelay);
		
		JButton IpdSet2 = new JButton("设置");
		IpdSet2.addActionListener(new ActionListener() {
			
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int value1,value2,value3,value4;
				double v1,v2,v3,v4;
				v1 = Double.valueOf(UnderVolValue.getText());
				v2 = Double.valueOf(UnderVolDelay.getText());
				v3 = Double.valueOf(OverVolValue.getText());
				v4 = Double.valueOf(OverVolDelay.getText());
				value1 = (int)(v1*1000);
				value2 = (int)(v2*100);
				value3 = (int)(v3*1000);
				value4 = (int)(v4*100);
				OutputStream outputStream;
				if (Main.mainView.tree.getLastNode() != null) {
					CanGateway gateway = Main.gatewayManger.getCangateway(
							Main.mainView.tree.getLastNode().toString());
					try {
						outputStream = gateway.getoStream();
						outputStream.write(Main.cmdMaker.GetIPDSet2Cmd(value1, value2, value3, value4));
						outputStream.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		IpdSet2.setFont(new Font("楷体", Font.PLAIN, 12));
		IpdSet2.setBounds(477, 149, 67, 23);
		contentPane.add(IpdSet2);
		
		LeakageResistance = new JTextField();
		LeakageResistance.setHorizontalAlignment(SwingConstants.CENTER);
		LeakageResistance.setColumns(10);
		LeakageResistance.setBounds(10, 218, 92, 21);
		contentPane.add(LeakageResistance);
		
		LeakageDelay = new JTextField();
		LeakageDelay.setHorizontalAlignment(SwingConstants.CENTER);
		LeakageDelay.setColumns(10);
		LeakageDelay.setBounds(129, 218, 92, 21);
		contentPane.add(LeakageDelay);
		
		CH4Delay = new JTextField();
		CH4Delay.setHorizontalAlignment(SwingConstants.CENTER);
		CH4Delay.setColumns(10);
		CH4Delay.setBounds(251, 218, 92, 21);
		contentPane.add(CH4Delay);
		
		DeviceAddr = new JTextField();
		DeviceAddr.setHorizontalAlignment(SwingConstants.CENTER);
		DeviceAddr.setColumns(10);
		DeviceAddr.setBounds(369, 218, 92, 21);
		contentPane.add(DeviceAddr);
		
		JButton IpdSet3 = new JButton("设置");
		IpdSet3.addActionListener(new ActionListener() {
			
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int value1,value2,value3,value4;
				double v1,v2,v3,v4;
				v1 = Double.valueOf(LeakageResistance.getText());
				v2 = Double.valueOf(LeakageDelay.getText());
				v3 = Double.valueOf(CH4Delay.getText());
				v4 = Double.valueOf(DeviceAddr.getText());
				value1 = (int)(v1*100);
				value2 = (int)(v2*100);
				value3 = (int)(v3*100);
				value4 = (int)v4;
				OutputStream outputStream;
				if (Main.mainView.tree.getLastNode() != null) {
					@SuppressWarnings("static-access")
					CanGateway gateway = Main.gatewayManger.getCangateway(
							Main.mainView.tree.getLastNode().toString());
					try {
						outputStream = gateway.getoStream();
						outputStream.write(Main.cmdMaker.GetIPDSet3Cmd(value1, value2, value3, value4));
						outputStream.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		IpdSet3.setFont(new Font("楷体", Font.PLAIN, 10));
		IpdSet3.setBounds(477, 217, 67, 23);
		contentPane.add(IpdSet3);
		
		JLabel label = new JLabel("照明额定电流");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("楷体", Font.PLAIN, 10));
		label.setBounds(20, 66, 75, 15);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("信号额定电流");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("楷体", Font.PLAIN, 10));
		label_1.setBounds(135, 66, 75, 15);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("照明速断倍数");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("楷体", Font.PLAIN, 10));
		label_2.setBounds(256, 66, 75, 15);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("信号速断倍数");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setFont(new Font("楷体", Font.PLAIN, 10));
		label_3.setBounds(375, 66, 75, 15);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("欠压定值");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setFont(new Font("楷体", Font.PLAIN, 10));
		label_4.setBounds(20, 133, 75, 15);
		contentPane.add(label_4);
		
		JLabel label_5 = new JLabel("欠压延时");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setFont(new Font("楷体", Font.PLAIN, 10));
		label_5.setBounds(135, 132, 75, 15);
		contentPane.add(label_5);
		
		JLabel label_6 = new JLabel("过压定值");
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setFont(new Font("楷体", Font.PLAIN, 10));
		label_6.setBounds(256, 132, 75, 15);
		contentPane.add(label_6);
		
		JLabel label_7 = new JLabel("过压延时");
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setFont(new Font("楷体", Font.PLAIN, 10));
		label_7.setBounds(375, 132, 75, 15);
		contentPane.add(label_7);
		
		JLabel label_8 = new JLabel("漏电电阻");
		label_8.setHorizontalAlignment(SwingConstants.CENTER);
		label_8.setFont(new Font("楷体", Font.PLAIN, 10));
		label_8.setBounds(20, 200, 75, 15);
		contentPane.add(label_8);
		
		JLabel label_9 = new JLabel("漏电延时");
		label_9.setHorizontalAlignment(SwingConstants.CENTER);
		label_9.setFont(new Font("楷体", Font.PLAIN, 10));
		label_9.setBounds(135, 199, 75, 15);
		contentPane.add(label_9);
		
		JLabel label_10 = new JLabel("风电瓦斯延时");
		label_10.setHorizontalAlignment(SwingConstants.CENTER);
		label_10.setFont(new Font("楷体", Font.PLAIN, 10));
		label_10.setBounds(256, 199, 75, 15);
		contentPane.add(label_10);
		
		JLabel label_11 = new JLabel("设备地址");
		label_11.setHorizontalAlignment(SwingConstants.CENTER);
		label_11.setFont(new Font("楷体", Font.PLAIN, 10));
		label_11.setBounds(375, 199, 75, 15);
		contentPane.add(label_11);
	}
	@SuppressWarnings("static-access")
	public void SetValue() {
		if (Main.mainView.tree.getLastNode() != null) {
			VoiceGateway gateway = Main.gatewayManger.getVoiceGateway(
					Main.mainView.tree.getLastNode().toString());
			LightingCurrent.setText(gateway.ipd.LightingCurrent.replaceAll("A", ""));
			SignalCurrent.setText(gateway.ipd.SignalCurrent.replaceAll("A", ""));
			LightningBreak.setText(gateway.ipd.LightingBreak.replaceAll("倍", ""));
			SignalBreak.setText(gateway.ipd.SignalBreak.replaceAll("倍", ""));
			UnderVolValue.setText(gateway.ipd.UnderVolValue.replaceAll("V", ""));
			UnderVolDelay.setText(gateway.ipd.UnderVolDelay.replaceAll("S", ""));
			OverVolValue.setText(gateway.ipd.OverVolValue.replaceAll("V", ""));
			OverVolDelay.setText(gateway.ipd.OverVolDelay.replaceAll("S", ""));
			LeakageResistance.setText(gateway.ipd.LeakageResistance.replaceAll("KΩ", ""));
			LeakageDelay.setText(gateway.ipd.LeakageDelay.replaceAll("S", ""));
			CH4Delay.setText(gateway.ipd.CH4Delay.replaceAll("S", ""));
			DeviceAddr.setText(gateway.ipd.DeviceAddr);
		}
	}
}
