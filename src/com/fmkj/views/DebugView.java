package com.fmkj.views;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import com.fmkj.Begin.Main;
import com.fmkj.tools.NomalDataSwitch;
import com.fmkj.values.LogFileName;
import com.fmkj.values.ShowType;

import javax.swing.ButtonGroup;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class DebugView extends JFrame {

	private JPanel contentPane;
	JTextPane textPane = new JTextPane();
	Document docs;
	SimpleAttributeSet attrset = new SimpleAttributeSet();
	public boolean showDebugInfo = true;
	JCheckBox zantingshow = new JCheckBox("暂停显示");
	public JCheckBox showall = new JCheckBox("全部显示");
	JCheckBox voiceshow = new JCheckBox("语音网关");
	JCheckBox canshow = new JCheckBox("CAN网关");
	JCheckBox checkshow = new JCheckBox("显示查询网关信息");
	ButtonGroup buttonGroup = new ButtonGroup();
	JCheckBox voicedata = new JCheckBox("语音数据显示");
	private int fontSize = 14;

	public DebugView() {
		setResizable(false);
		setVisible(false);
		setBounds(100, 100, 1200, 762);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		docs = textPane.getDocument();// 获得文本对象
		StyleConstants.setForeground(attrset, new Color(0x000000));
		contentPane.setLayout(null);
		textPane.setFont(new Font("楷体", Font.ROMAN_BASELINE, fontSize));
		textPane.setEditable(false);
		JScrollPane jsp = new JScrollPane(textPane);
		jsp.setBounds(0, 5, 1190, 685); // 设置 JScrollPane 宽100,高200
		contentPane.add(jsp);
		showall.setFont(new Font("楷体", Font.PLAIN, fontSize));

		showall.setHorizontalAlignment(SwingConstants.CENTER);
		showall.setBounds(238, 706, 103, 23);
		contentPane.add(showall);
		checkshow.setFont(new Font("楷体", Font.PLAIN, fontSize));

		checkshow.setHorizontalAlignment(SwingConstants.CENTER);
		checkshow.setBounds(343, 706, 145, 23);
		contentPane.add(checkshow);
		voiceshow.setFont(new Font("楷体", Font.PLAIN, fontSize));

		voiceshow.setHorizontalAlignment(SwingConstants.CENTER);
		voiceshow.setBounds(490, 706, 103, 23);
		contentPane.add(voiceshow);
		canshow.setFont(new Font("楷体", Font.PLAIN, fontSize));

		canshow.setHorizontalAlignment(SwingConstants.CENTER);
		canshow.setBounds(595, 706, 103, 23);
		contentPane.add(canshow);
		zantingshow.setSelected(true);
		zantingshow.setFont(new Font("楷体", Font.PLAIN, fontSize));

		zantingshow.setHorizontalAlignment(SwingConstants.CENTER);
		zantingshow.setBounds(700, 706, 103, 23);
		voicedata.setFont(new Font("楷体", Font.PLAIN, fontSize));

		contentPane.add(zantingshow);
		buttonGroup.add(showall);
		buttonGroup.add(voiceshow);
		buttonGroup.add(checkshow);
		buttonGroup.add(canshow);
		buttonGroup.add(zantingshow);
		buttonGroup.add(voicedata);
		showall.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				textPane.setText("");
			}
		});
		voiceshow.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				textPane.setText("");
			}
		});
		checkshow.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				textPane.setText("");
			}
		});
		canshow.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				textPane.setText("");
			}
		});
		voicedata.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				textPane.setText("");
			}
		});

		voicedata.setBounds(805, 706, 115, 23);

		contentPane.add(voicedata);
		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent arg0) {
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				Main.mainView.debugBtn.setText("打开调试信息");
				zantingshow.setSelected(true);
				clearText();
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
			}

			@Override
			public void windowActivated(WindowEvent arg0) {
			}
		});
	}

	@SuppressWarnings("static-access")
	public void addMsg(String s, byte showType, Color color, String ipString) {
		if (docs.getLength() > 10000000)
			textPane.setText("");
		if (showType == ShowType.SHOWTYPE_CAN || showType == ShowType.SHOWTYPE_GETGATEWAYINFO
				|| showType == ShowType.SHOWTYPE_VOICE) {
			if (showType == ShowType.SHOWTYPE_VOICE) {
				Main.logRecoder.saveLog(
						LogFileName.Log_VoiceGateway + ipString + "\\"
								+ Main.datetool.getTimeH() + ".log",
						s + "\r\n");
			}
			else {
				Main.logRecoder.saveLog(
						LogFileName.Log_CanGateway + ipString + "\\"
								+ Main.datetool.getTimeH() + ".log",
						s + "\r\n");
			}
		}
		if(showType ==ShowType.SHOWTYPE_VOICEDATA )
			Main.logRecoder.saveLog(
					LogFileName.Log_VoiceSend+ipString.substring(0,ipString.indexOf(":"))+"\\"+Main.datetool.getTimeH()+".log",
					s + "\r\n");
		if (showall.isSelected()) {
			StyleConstants.setForeground(attrset, color);
			try {
				docs.insertString(docs.getLength(), s + "\r\n", attrset);
				textPane.select(docs.getLength(), docs.getLength());
			} catch (BadLocationException e) {
			}
		} else if (canshow.isSelected() && showType == ShowType.SHOWTYPE_CAN) {
			StyleConstants.setForeground(attrset, color);
			try {
				docs.insertString(docs.getLength(), s + "\r\n", attrset);
				textPane.select(docs.getLength(), docs.getLength());
			} catch (BadLocationException e) {
			}
		} else if (voiceshow.isSelected() && showType == ShowType.SHOWTYPE_VOICE) {
			StyleConstants.setForeground(attrset, color);
			try {
				docs.insertString(docs.getLength(), s + "\n", attrset);
				textPane.select(docs.getLength(), docs.getLength());
			} catch (BadLocationException e) {
			}
		} else if (voicedata.isSelected() && showType == ShowType.SHOWTYPE_VOICEDATA) {
			StyleConstants.setForeground(attrset, color);
			try {
				docs.insertString(docs.getLength(), s + "\r\n", attrset);
				textPane.select(docs.getLength(), docs.getLength());
			} catch (BadLocationException e) {
			}
		} else if (showType == ShowType.SHOWTYPE_GETGATEWAYINFO) {
			StyleConstants.setForeground(attrset, color);
			try {
				if (checkshow.isSelected()) {
					docs.insertString(docs.getLength(), s + "\r\n", attrset);
					textPane.select(docs.getLength(), docs.getLength());
				}
			} catch (BadLocationException e) {
			}
		}
	}

	@SuppressWarnings("static-access")
	public void addCanGatewayInfo(String gatewayIp, String[][] info, String[] senserAck, byte showType,
			Color color) {
		int i = 0;
		String timeString;
		if (showType == ShowType.SHOWTYPE_GETGATEWAYINFO) {
			StyleConstants.setForeground(attrset, color);
			try {
				for (String[] string : info) {
					if (string != null) {
						timeString = Main.datetool.getTimeHMS();
						if (checkshow.isSelected()) {
							docs.insertString(docs.getLength(), senserAck[i]
									+ "->【" + string[0] + "】"
									+ NomalDataSwitch.getEmp("->【"
											+ string[0]
											+ "】")
									+ string[1]
									+ NomalDataSwitch.getEmp1(
											string[1])
									+ "ADDR-" + string[2]
									+ NomalDataSwitch.getEmp1(
											"addr-" + string[2])
									+ "CAN-" + string[3]
									+ NomalDataSwitch.getEmp1(
											"CAN-" + string[3])
									+ string[4] + "\n", attrset);
							textPane.select(docs.getLength(),
									docs.getLength());

						}
						if (string[0].equals("广播终端")) {
							Main.logRecoder.saveLog(
									LogFileName.Log_BoardCastInfo
											+ Main.datetool.getTimeH()
											+ ".log",
									timeString + NomalDataSwitch
											.getEmp1(timeString)
											+ senserAck[i]
											+ "->【"
											+ string[0]
											+ "】"
											+ NomalDataSwitch.getEmp(
													"->【" + string[0] + "】")
											+ string[1]
											+ NomalDataSwitch.getEmp1(
													string[1])
											+ "ADDR-"
											+ string[2]
											+ NomalDataSwitch.getEmp1(
													"addr-" + string[2])
											+ "CAN-"
											+ string[3]
											+ NomalDataSwitch.getEmp1(
													"CAN-" + string[3])
											+ string[4]
											+ "\n");
						}
						Main.logRecoder.saveLog(LogFileName.Log_CanGateway
								+ gatewayIp.substring(0, gatewayIp
										.indexOf(":"))
								+ "\\" + Main.datetool.getTimeH()
								+ ".log",
								senserAck[i] + "->【" + string[0] + "】"
										+ NomalDataSwitch.getEmp(
												"->【" + string[0] + "】")
										+ string[1]
										+ NomalDataSwitch.getEmp1(
												string[1])
										+ "ADDR-"
										+ string[2]
										+ NomalDataSwitch.getEmp1(
												"addr-" + string[2])
										+ "CAN-" + string[3]
										+ NomalDataSwitch.getEmp1(
												"CAN-" + string[3])
										+ string[4] + "\n");
						i++;
					}
				}
			} catch (BadLocationException e) {
			}
		}
	}

	@SuppressWarnings("static-access")
	public void addVoiceGatewayInfo(String gatewayIp, String[][] info, String[] senserAck, byte showType,
			Color color) {
		int i = 0;
		String timeString;
		if (showType == ShowType.SHOWTYPE_VOICE) {
			StyleConstants.setForeground(attrset, color);
			try {
				for (String[] string : info) {
					if (string != null) {
						timeString = Main.datetool.getTimeHMS();
						if (voiceshow.isSelected()) {
							docs.insertString(docs.getLength(), senserAck[i]
									+ "->【" + string[0] + "】"
									+ NomalDataSwitch.getEmp("->【"
											+ string[0]
											+ "】")
									+ string[1]
									+ NomalDataSwitch.getEmp1(
											string[1])
									+ "ADDR-" + string[2]
									+ NomalDataSwitch.getEmp1(
											"addr-" + string[2])
									+ "CAN-" + string[3]
									+ NomalDataSwitch.getEmp1(
											"CAN-" + string[3])
									+ string[4] + "\n", attrset);
							textPane.select(docs.getLength(),
									docs.getLength());

						}
						if (string[0].equals("广播终端")) {
							Main.logRecoder.saveLog(
									LogFileName.Log_BoardCastInfo
											+ Main.datetool.getTimeH()
											+ ".log",
									timeString + NomalDataSwitch
											.getEmp1(timeString)
											+ senserAck[i]
											+ "->【"
											+ string[0]
											+ "】"
											+ NomalDataSwitch.getEmp(
													"->【" + string[0] + "】")
											+ string[1]
											+ NomalDataSwitch.getEmp1(
													string[1])
											+ "ADDR-"
											+ string[2]
											+ NomalDataSwitch.getEmp1(
													"addr-" + string[2])
											+ "CAN-"
											+ string[3]
											+ NomalDataSwitch.getEmp1(
													"CAN-" + string[3])
											+ string[4]
											+ "\n");
						}
						Main.logRecoder.saveLog(LogFileName.Log_VoiceGateway
								+ gatewayIp.substring(0, gatewayIp
										.indexOf(":"))
								+ "\\" + Main.datetool.getTimeH()
								+ ".log",
								senserAck[i] + "->【" + string[0] + "】"
										+ NomalDataSwitch.getEmp(
												"->【" + string[0] + "】")
										+ string[1]
										+ NomalDataSwitch.getEmp1(
												string[1])
										+ "ADDR-"
										+ string[2]
										+ NomalDataSwitch.getEmp1(
												"addr-" + string[2])
										+ "CAN-" + string[3]
										+ NomalDataSwitch.getEmp1(
												"CAN-" + string[3])
										+ string[4] + "\n");
						i++;
					}
				}
			} catch (BadLocationException e) {
			}
		}
	}
	@SuppressWarnings("static-access")
	public void appandMsg(String s, byte showType, Color color, String ipString) {
		if (docs.getLength() > 10000000)
			textPane.setText("");
		if (showType == ShowType.SHOWTYPE_CAN || showType == ShowType.SHOWTYPE_GETGATEWAYINFO
				|| showType == ShowType.SHOWTYPE_VOICE) {
			if (showType == ShowType.SHOWTYPE_VOICE) {
				Main.logRecoder.saveLog(
						LogFileName.Log_VoiceGateway + ipString + "\\"
								+ Main.datetool.getTimeH() + ".log",
						s );
			}
			else {
				Main.logRecoder.saveLog(
						LogFileName.Log_CanGateway + ipString + "\\"
								+ Main.datetool.getTimeH() + ".log",
						s );
			}
		}
		if(showType ==ShowType.SHOWTYPE_VOICEDATA )
			Main.logRecoder.saveLog(
					LogFileName.Log_VoiceSend+ipString.substring(0,ipString.indexOf(":"))+"\\"+Main.datetool.getTimeH()+".log",
					s );
		
		if (showall.isSelected()) {
			StyleConstants.setForeground(attrset, color);
			StyleConstants.setForeground(attrset, color);
			try {
				docs.insertString(docs.getLength(), s, attrset);
				textPane.select(docs.getLength(), docs.getLength());
			} catch (BadLocationException e) {
			}
		} else if (canshow.isSelected() && showType == ShowType.SHOWTYPE_CAN) {
			StyleConstants.setForeground(attrset, color);
			try {
				docs.insertString(docs.getLength(), s, attrset);
				textPane.select(docs.getLength(), docs.getLength());
			} catch (BadLocationException e) {
			}
		} else if (voiceshow.isSelected() && showType == ShowType.SHOWTYPE_VOICE) {
			StyleConstants.setForeground(attrset, color);
			try {
				docs.insertString(docs.getLength(), s, attrset);
				textPane.select(docs.getLength(), docs.getLength());
			} catch (BadLocationException e) {
			}
		} else if (voicedata.isSelected() && showType == ShowType.SHOWTYPE_VOICEDATA) {
			StyleConstants.setForeground(attrset, color);
			try {
				docs.insertString(docs.getLength(), s, attrset);
				textPane.select(docs.getLength(), docs.getLength());
			} catch (BadLocationException e) {
			}
		} else if (checkshow.isSelected() && showType == ShowType.SHOWTYPE_GETGATEWAYINFO) {
			StyleConstants.setForeground(attrset, color);
			try {
				docs.insertString(docs.getLength(), s, attrset);
				textPane.select(docs.getLength(), docs.getLength());
			} catch (BadLocationException e) {
			}
		}
	}

	public void clearText() {
		textPane.setText("");
	}
}
