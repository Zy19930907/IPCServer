package com.fmkj.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.fmkj.Begin.Main;
import com.fmkj.tools.JMIPV4AddressField;
import com.fmkj.values.LogFileName;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

@SuppressWarnings("serial")
public class AddGateway extends JFrame {

	private JPanel contentPane;
	private String gatewayType;
	private String ipString="";
	@SuppressWarnings("rawtypes")
	JComboBox portcomboBox = new JComboBox();
	private JMIPV4AddressField field = new JMIPV4AddressField();
	public String getGatewayType() {
		return gatewayType;
	}

	public void setGatewayType(String gatewayType) {
		this.gatewayType = gatewayType;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AddGateway() {
		setResizable(false);
		setTitle("添加网关");
		setBounds(500, 300, 450, 153);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		contentPane.setLayout(null);
		field.setBounds(75, 30, 237, 23);
		field.setIpAddress("192.168.1.200");
		contentPane.add(field);
		JLabel lblNewLabel = new JLabel("网关IP");
		lblNewLabel.setBounds(21, 30, 44, 23);
		contentPane.add(lblNewLabel);
		
		portcomboBox.setModel(new DefaultComboBoxModel(new String[] { "5000", "5001", "5002", "5003" }));
		portcomboBox.setBounds(369, 30, 65, 23);
		contentPane.add(portcomboBox);
		
		JLabel label = new JLabel("端口");
		label.setBounds(322, 30, 29, 23);
		contentPane.add(label);
		JButton button = new JButton("添加");
		button.setBounds(108, 78, 93, 23);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			AddGateway.this.setVisible(false);
			ipString = field.getIpAddress() +":"+ portcomboBox.getSelectedItem().toString();
				if(Main.gatewayManger.isGatewayExit(ipString)) {
					JOptionPane.showMessageDialog(null,"添加失败！！！","添加失败："+gatewayType+" 已存在", JOptionPane.ERROR_MESSAGE);
				}else {
					Main.gatewayManger.creatNewGateway(gatewayType, ipString);
					JOptionPane.showMessageDialog(null,(ipString)+"添加成功！！！",gatewayType+"添加成功", JOptionPane.INFORMATION_MESSAGE);
					Main.logRecoder.saveLog(LogFileName.Log_IpConfig, gatewayType+":"+ipString+"\r\n");
				}
			}
		});
		contentPane.add(button);
		JButton button_1 = new JButton("取消");
		button_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AddGateway.this.setVisible(false);
			}
		});
		button_1.setBounds(260, 78, 93, 23);
		contentPane.add(button_1);
		setContentPane(contentPane);
	}
}
