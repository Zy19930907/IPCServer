package com.fmkj.views;

import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.fmkj.Begin.Main;
import com.fmkj.gateways.AliLinker;
import com.fmkj.tools.JMIPV4AddressField;

public class AliLinkView extends JFrame {

	private JPanel contentPane;
	private JMIPV4AddressField field = new JMIPV4AddressField();
	private File fontFile;
	public JButton link = new JButton("连接");
	JComboBox port = new JComboBox();
	
	public AliLinkView() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 450, 88);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		port.setModel(new DefaultComboBoxModel(new String[] {"12121"}));
		port.setBounds(238, 10, 83, 37);
		contentPane.add(port);
		
		field.setText("47.52.204.23");
		field.setBounds(10, 10, 218, 37);
		
		contentPane.add(field);
		
		

		link.setBounds(331, 10, 93, 37);
		contentPane.add(link);
		addAction();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
	}

public void addAction() {
		
		link.addActionListener(event -> {
			Main.aliLinker = new AliLinker(field.getIpAddress()+":"+port.getSelectedItem().toString());
			link.setEnabled(false);
		});	
		
	}
}
