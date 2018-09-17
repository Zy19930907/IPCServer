package com.fmkj.views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.fmkj.Begin.Main;
import com.fmkj.tools.NomalDataSwitch;
import com.fmkj.values.LogFileName;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.Font;

@SuppressWarnings("serial")
public class AddBoardCastView extends JFrame {

	private JPanel contentPane;
	private String ipString;
	public JTextPane setaddr = new JTextPane();
	@SuppressWarnings("rawtypes")
	public DefaultComboBoxModel voiceModel;
	@SuppressWarnings("rawtypes")
	public DefaultComboBoxModel canModel;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AddBoardCastView() {
		setResizable(false);
		setBounds(100, 100, 405, 98);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox boartcastaddr = new JComboBox();
		boartcastaddr.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100", "101", "102", "103", "104", "105", "106", "107", "108", "109", "110", "111", "112", "113", "114", "115", "116", "117", "118", "119", "120", "121", "122", "123", "124", "125", "126", "127", "128", "129", "130", "131", "132", "133", "134", "135", "136", "137", "138", "139", "140", "141", "142", "143", "144", "145", "146", "147", "148", "149", "150", "151", "152", "153", "154", "155", "156", "157", "158", "159", "160", "161", "162", "163", "164", "165", "166", "167", "168", "169", "170", "171", "172", "173", "174", "175", "176", "177", "178", "179", "180", "181", "182", "183", "184", "185", "186", "187", "188", "189", "190", "191", "192", "193", "194", "195", "196", "197", "198", "199", "200", "201", "202", "203", "204", "205", "206", "207", "208", "209", "210", "211", "212", "213", "214", "215", "216", "217", "218", "219", "220", "221", "222", "223", "224", "225", "226", "227", "228", "229", "230", "231", "232", "233", "234", "235", "236", "237", "238", "239", "240", "241", "242", "243", "244", "245", "246", "247", "248", "249", "250", "251", "252", "253", "254", "255"}));
		boartcastaddr.setBounds(125, 40, 59, 21);
		contentPane.add(boartcastaddr);
		
		JLabel label = new JLabel("广播地址");
		label.setBounds(23, 43, 54, 15);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("安装位置");
		label_1.setBounds(23, 12, 54, 15);
		contentPane.add(label_1);
		
		JButton button = new JButton("添加");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.boardCastManger.addBoardCast(ipString,NomalDataSwitch.stringToByte(boartcastaddr.getSelectedItem().toString()));
				Main.mainView.addBoardcast(setaddr.getText(),ipString, boartcastaddr.getSelectedItem().toString());
				Main.logRecoder.saveLog(LogFileName.Log_BoardCastConfig, setaddr.getText()+"-"+ipString+"-"+boartcastaddr.getSelectedItem().toString()+"\n");
				AddBoardCastView.this.setVisible(false);
			}
		});
		button.setBounds(206, 39, 185, 23);
		contentPane.add(button);
		setaddr.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		setaddr.setBounds(125, 9, 266, 21);
		contentPane.add(setaddr);
	}
	public void setVoiceGatewayIpString(String ipString) {
		this.ipString = ipString;
	}
}
