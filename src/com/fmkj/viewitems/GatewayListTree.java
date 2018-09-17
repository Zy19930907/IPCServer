package com.fmkj.viewitems;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.fmkj.Begin.Main;
import com.fmkj.values.GatewayType;
import com.fmkj.values.LogFileName;

@SuppressWarnings("serial")
public class GatewayListTree extends JTree {
	DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("网关列表");
	DefaultMutableTreeNode canGatewayNode = new DefaultMutableTreeNode(GatewayType.GATEWAYTYPE_CAN);
	DefaultMutableTreeNode voiceGatewayNode = new DefaultMutableTreeNode(GatewayType.GATEWAYTYPE_VOICE);
	DefaultMutableTreeNode newNode;
	DefaultMutableTreeNode lastNode = null;

	private JPopupMenu canGatewayListMenu = new JPopupMenu();
	private JMenuItem addCanGateway = new JMenuItem("添加CAN网关");
	private JMenuItem addBoardCast = new JMenuItem("添加广播终端");
	private JMenuItem closeSocket = new JMenuItem("断开连接");
	private JPopupMenu GatewayMenu = new JPopupMenu();
	private JPopupMenu voiceGatewayMenu = new JPopupMenu();
	private JMenuItem addVoiceGateway = new JMenuItem("添加语音网关");
	public JMenuItem gatewayDebugMode = new JMenuItem("退出调试模式");
	private JMenuItem setVoiceMode = new JMenuItem("设置语音端口");
	private JMenuItem getExcuteInfo = new JMenuItem("查询关联信息表");
	private JMenuItem openLogFile = new JMenuItem("查看日志文件");
	private JMenuItem setUpLoadTime = new JMenuItem("设置上传时间");
	private Font font = new Font("宋体", Font.PLAIN, 13);
	TreePath path;
	
	public DefaultMutableTreeNode getLastNode() {
		return lastNode;
	}

	public GatewayListTree() {
		setBounds(5, 5, 220, 526);
		canGatewayListMenu.add(addCanGateway);
		GatewayMenu.add(gatewayDebugMode);
		GatewayMenu.add(setVoiceMode);
		GatewayMenu.add(addBoardCast);
		GatewayMenu.add(closeSocket);
		GatewayMenu.add(getExcuteInfo);
		GatewayMenu.add(openLogFile);
		GatewayMenu.add(setUpLoadTime);
		voiceGatewayMenu.add(addVoiceGateway);
		setUpLoadTime.setFont(font);
		closeSocket.setFont(font);
		openLogFile.setFont(font);
		setVoiceMode.setFont(font);
		addVoiceGateway.setFont(font);
		addCanGateway.setFont(font);
		gatewayDebugMode.setFont(font);
		addBoardCast.setFont(font);
		getExcuteInfo.setFont(font);
		rootNode.add(canGatewayNode);
		rootNode.add(voiceGatewayNode);
		updateUI();

		setUpLoadTime.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.uploadTimeSetView.setVoiceGateway(Main.gatewayManger.getVoiceGateway(getLastNode().toString()));
				Main.uploadTimeSetView.setTitle(getLastNode().toString());
				Main.uploadTimeSetView.setVisible(true);
			}
		});
		openLogFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter songFilter = new FileNameExtensionFilter("日志文件(*.log;*.Log)", "log", "Log");
				fileChooser.setFileFilter(songFilter);
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")+"\\Logs"));
				if (fileChooser.showOpenDialog(openLogFile.getComponent()) != JFileChooser.APPROVE_OPTION)
					return;
				File file = fileChooser.getSelectedFile();
					try {
						Desktop.getDesktop().open(file);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
			}
		});
		closeSocket.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (closeSocket.getText().equals("断开连接")) {
					closeSocket.setText("连接");
					Main.gatewayManger.removeGateway(getLastNode().toString());
				} else {
					closeSocket.setText("断开连接");
					Main.gatewayManger.reLinkGateway(GatewayType.GATEWAYTYPE_VOICE,
							getLastNode().toString());
				}

			}
		});
		addCanGateway.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Main.addGateway.setGatewayType(GatewayType.GATEWAYTYPE_CAN);
				Main.addGateway.setTitle("添加" + GatewayType.GATEWAYTYPE_CAN);
				Main.addGateway.setVisible(true);
			}
		});
		
		getExcuteInfo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.gatewayManger.getVoiceGateway(getLastNode().toString())
				.sendCmd(Main.cmdMaker.getExcuteInfoCmd());
			}
		});
		addVoiceGateway.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Main.addGateway.setGatewayType(GatewayType.GATEWAYTYPE_VOICE);
				Main.addGateway.setTitle("添加" + GatewayType.GATEWAYTYPE_VOICE);
				Main.addGateway.setVisible(true);
			}
		});
		setVoiceMode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Main.gatewayManger.getVoiceGateway(getLastNode().toString())
						.sendCmd(Main.cmdMaker.getSetVoicePortCmd());
			}
		});
		gatewayDebugMode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (gatewayDebugMode.getText().equals("进入调试模式")) {
					gatewayDebugMode.setText("退出调试模式");
					Main.gatewayManger.getVoiceGateway(getLastNode().toString()).sendCmd(
							Main.cmdMaker.getGatewayDebufCmd((byte) 0x01));
				} else {
					gatewayDebugMode.setText("进入调试模式");
					Main.gatewayManger.getVoiceGateway(getLastNode().toString()).sendCmd(
							Main.cmdMaker.getGatewayDebufCmd((byte) 0x00));
				}

			}
		});

		addBoardCast.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Main.addBoardCastView.setVoiceGatewayIpString(getLastNode().toString());
				Main.addBoardCastView.setVisible(true);
			}
		});
		addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) getLastSelectedPathComponent();
				if (node.toString().equals("网关列表")
						|| node.toString().equals(GatewayType.GATEWAYTYPE_CAN)
						|| node.toString().equals(GatewayType.GATEWAYTYPE_VOICE)) {
					return;
				}
				lastNode = node;
				if (Main.gatewayManger.getVoiceGateway(lastNode.toString()) != null) {
					Main.mainView.gatewayInfotable.setValueAt(
							Main.gatewayManger.getVoiceGateway(lastNode
									.toString()).gatewayType,
							0, 1);
					Main.mainView.gatewayInfotable.setValueAt(Main.gatewayManger
							.getVoiceGateway(lastNode.toString()).linkState,
							1, 1);
					Main.mainView.gatewayInfotable.setValueAt(
							Main.gatewayManger.getVoiceGateway(
									lastNode.toString()).macAddr,
							2, 1);
					Main.mainView.gatewayInfotable.setValueAt(
							Main.gatewayManger.getVoiceGateway(
									lastNode.toString()).appVer,
							3, 1);
					Main.mainView.gatewayInfotable.setValueAt(Main.gatewayManger
							.getVoiceGateway(lastNode.toString()).SenserCnt,
							4, 1);
				} else if (Main.gatewayManger.getCangateway(lastNode.toString()) != null) {
					Main.mainView.gatewayInfotable.setValueAt(
							Main.gatewayManger.getCangateway(lastNode
									.toString()).gatewayType,
							0, 1);
					Main.mainView.gatewayInfotable.setValueAt(Main.gatewayManger
							.getCangateway(lastNode.toString()).LinkStatus,
							1, 1);
					Main.mainView.gatewayInfotable.setValueAt(
							Main.gatewayManger.getCangateway(
									lastNode.toString()).macAdd,
							2, 1);
					Main.mainView.gatewayInfotable.setValueAt(
							Main.gatewayManger.getCangateway(
									lastNode.toString()).appVer,
							3, 1);
					Main.mainView.gatewayInfotable.setValueAt(Main.gatewayManger
							.getCangateway(lastNode.toString()).SenserCnt,
							4, 1);
				}
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				if (SwingUtilities.isRightMouseButton(e)) {
					path = getPathForLocation(e.getX(), e.getY());
					if (path != null) {
						if (path.getLastPathComponent().toString()
								.equals(GatewayType.GATEWAYTYPE_CAN)) {
							canGatewayListMenu.show(GatewayListTree.this,
									e.getX(), e.getY());
						} else if (path.getLastPathComponent().toString().equals(
								GatewayType.GATEWAYTYPE_VOICE)) {
							voiceGatewayMenu.show(GatewayListTree.this,
									e.getX(), e.getY());
						} else if (!(path.getLastPathComponent().toString()
								.equals("网关列表"))) {
							GatewayMenu.show(GatewayListTree.this, e.getX(),
									e.getY());
						}
						setSelectionPath(path);
					}
				}
			}
		});
		setModel(new DefaultTreeModel(rootNode));
	}

	public void addNode(String gatewayType, String newGatewayIp) {
		newNode = new DefaultMutableTreeNode(newGatewayIp);
		switch (gatewayType) {
		case GatewayType.GATEWAYTYPE_CAN:
			canGatewayNode.add(newNode);
			rootNode.removeAllChildren();
			rootNode.add(canGatewayNode);
			rootNode.add(voiceGatewayNode);
			updateUI();
			break;

		case GatewayType.GATEWAYTYPE_VOICE:
			voiceGatewayNode.add(newNode);
			rootNode.removeAllChildren();
			rootNode.add(canGatewayNode);
			rootNode.add(voiceGatewayNode);
			Main.mainView.tree.setSelectionPath(new TreePath(newNode.getPath()));
			updateUI();
			break;
		}
	}
}
