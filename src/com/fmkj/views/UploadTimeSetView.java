package com.fmkj.views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.fmkj.Begin.Main;
import com.fmkj.gateways.VoiceGateway;
import com.fmkj.tools.NomalDataSwitch;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UploadTimeSetView extends JFrame {

	private JPanel contentPane;
	private JTextField sensorCode;
	private JTextField time;
	public JLabel curTime = new JLabel("");
	private VoiceGateway voiceGateway;
	public VoiceGateway getVoiceGateway() {
		return voiceGateway;
	}
	public void setVoiceGateway(VoiceGateway voiceGateway) {
		this.voiceGateway = voiceGateway;
	}
	public UploadTimeSetView() {
		setResizable(false);
		setBounds(100, 100, 439, 179);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		sensorCode = new JTextField();
		sensorCode.setHorizontalAlignment(SwingConstants.CENTER);
		sensorCode.setBounds(99, 60, 66, 21);
		contentPane.add(sensorCode);
		sensorCode.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("传感器编号");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 63, 79, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("上传间隔");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(244, 63, 90, 15);
		contentPane.add(lblNewLabel_1);
		
		time = new JTextField();
		time.setHorizontalAlignment(SwingConstants.CENTER);
		time.setBounds(358, 60, 66, 21);
		contentPane.add(time);
		time.setColumns(10);
		
		JButton button = new JButton("查询上传间隔");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				voiceGateway.sendCmd(Main.cmdMaker.getUploadTimeCmd(NomalDataSwitch.stringToByte(sensorCode.getText())));
			}
		});
		button.setBounds(10, 104, 183, 37);
		contentPane.add(button);
		
		JButton button_1 = new JButton("设置上传间隔");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				voiceGateway.sendCmd(Main.cmdMaker.getSetUploadTimeCmd(NomalDataSwitch.stringToByte(sensorCode.getText()), NomalDataSwitch.stringToInt(time.getText())));
			}
		});
		button_1.setBounds(241, 104, 183, 37);
		contentPane.add(button_1);
		
		curTime.setFont(new Font("仿宋", Font.BOLD, 13));
		curTime.setHorizontalAlignment(SwingConstants.CENTER);
		curTime.setBounds(10, 10, 414, 40);
		contentPane.add(curTime);
	}
}
