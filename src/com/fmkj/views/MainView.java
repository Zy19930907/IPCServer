package com.fmkj.views;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import com.fmkj.Begin.Main;
import com.fmkj.boardcast.BoardCast;
import com.fmkj.gateways.VoiceGateway;
import com.fmkj.values.LogFileName;
import com.fmkj.values.SenserType;
import com.fmkj.viewitems.GatewayListTree;
import java.awt.Font;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import java.awt.Desktop;

import javax.swing.UIManager;
import javax.swing.JTabbedPane;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class MainView extends JFrame{

	private JPanel contentPane;
	public static GatewayListTree tree = new GatewayListTree();
	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	public JTable gatewayInfotable;
	DefaultTableCellRenderer Rstyle = new DefaultTableCellRenderer();
	public JTable table;
	Calendar c=Calendar.getInstance();
	private DefaultTableModel gatewayInfoModel;
	private String[] tableTitle =  {
			"传感器类型", "连接状态", "地址","CAN编号","监测值"
		};
	private Object[][] SenserInfo = new Object[128][5];
	private Object[][] GatewayInfo =new Object[][] {
		{"网关类型", "----------"},
		{"连接状态","----------"},
		{"MAC地址","----------"},
		{"固件版本", "----------"},
		{ "传感器数量", "----------" },
	};
	private DefaultTableModel SenserInfoModel = new DefaultTableModel(SenserInfo,tableTitle);
	public JButton debugBtn = new JButton("打开调试信息");
	private final JScrollPane scrollPane_2 = new JScrollPane();
	public JTable boardcasttable = new JTable();
	public Object[][] boardcastInfo = new Object[][] {};
	private String[] boardcastTableTitle = new String[] {"安装位置","语音网关","广播地址"};
	public DefaultTableModel boardcastModel = new DefaultTableModel(boardcastInfo, boardcastTableTitle);
	private BoardCast boardCast;
	private JPopupMenu boardCastMenu = new JPopupMenu();
	private JPopupMenu IPD_ZJMMenu = new JPopupMenu();
	private JMenuItem IPD_Config = new JMenuItem("综保设置");
	private JMenuItem playMusic = new JMenuItem("播放音乐");
	private JMenuItem listenBoardCast = new JMenuItem("广播监听");
	
	private Font font = new Font("宋体", Font.PLAIN, 13);
	public MainView() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainView.class.getResource("/com/fmkj/test.png")));
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 300, 998, 563);
		contentPane = new JPanel();
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		contentPane.setLayout(null);
		tree.setBounds(5, 5, 226, 482);
		tree.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		tree.setShowsRootHandles(true);
		tree.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		IPD_ZJMMenu.add(IPD_Config);
		IPD_Config.setFont(font);
		IPD_Config.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Main.ipdControlView.SetValue();
				Main.ipdControlView.setVisible(true);
			}
		});
		boardCastMenu.add(playMusic);
		boardCastMenu.add(listenBoardCast);
		playMusic.setFont(font);listenBoardCast.setFont(font);
		contentPane.add(tree);
		tabbedPane.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		
		tabbedPane.setBounds(235, 5, 757, 526);
		contentPane.add(tabbedPane);
		Rstyle.setHorizontalAlignment(JLabel.CENTER);     
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("网关信息", null, panel, null);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 5, 742, 492);
		panel.add(scrollPane);
		scrollPane.setViewportBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		
		gatewayInfotable = new JTable();
		gatewayInfoModel = new DefaultTableModel(GatewayInfo, new String[] {"",""});
		gatewayInfotable.setModel(gatewayInfoModel);
		gatewayInfotable.getColumnModel().getColumn(0).setResizable(false);
		gatewayInfotable.getColumnModel().getColumn(0).setPreferredWidth(44);
		gatewayInfotable.getColumnModel().getColumn(1).setResizable(false);
		gatewayInfotable.setSurrendersFocusOnKeystroke(true);
		gatewayInfotable.setBorder(UIManager.getBorder("ComboBox.border"));
		gatewayInfotable.setRowHeight(30);
		gatewayInfotable.setDefaultRenderer(Object.class, Rstyle); 
		gatewayInfotable.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		scrollPane.setViewportView(gatewayInfotable);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		tabbedPane.addTab("传感器信息", null, scrollPane_1, null);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBounds(0, 0, 1, 1);
		table.setRowHeight(30);
		table.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		table.setDefaultRenderer(Object.class, Rstyle); 
		table.setModel(SenserInfoModel);
		table.getColumnModel().getColumn(0).setMinWidth(120);
		table.getColumnModel().getColumn(1).setMinWidth(120);
		table.getColumnModel().getColumn(2).setMinWidth(60);
		table.getColumnModel().getColumn(3).setMinWidth(60);
		table.getColumnModel().getColumn(0).setMaxWidth(120);
		table.getColumnModel().getColumn(1).setMaxWidth(120);
		table.getColumnModel().getColumn(2).setMaxWidth(60);
		table.getColumnModel().getColumn(3).setMaxWidth(60);
		table.addMouseListener(new MouseAdapter() {
			 @Override
		            public void mouseClicked(MouseEvent e) {
		                if ((table.getSelectedRow() == -1)||(table.getValueAt(table.getSelectedRow(), 0)==null)) 
		                    return;
		              if(e.getButton() == MouseEvent.BUTTON1 && table.getValueAt(table.getSelectedRow(), 1).equals(com.fmkj.values.SenserInfo.SENSERSTATE_CONNECTED)) {
		                if(table.getValueAt(table.getSelectedRow(), 0).equals(SenserType.SENSERTYPE_PKPOWER))
		                {
		          	      Main.pekingPowerView.setVisible(true);
		          	      Main.gatewayManger.getVoiceGateway(tree.getLastNode().toString()).sendCmd(Main.cmdMaker.getPkPowerCmd(Byte.parseByte((String) table.getValueAt(table.getSelectedRow(), 2))));
		                }
		                else if(table.getValueAt(table.getSelectedRow(), 0).equals(SenserType.SENSERTYPE_BOARDCAST) || table.getValueAt(table.getSelectedRow(), 0).equals(SenserType.SENSERTYPE_BREAKER) || table.getValueAt(table.getSelectedRow(), 0).equals(SenserType.SENSERTYPE_CARDREADER))
		                {
		          	      Main.acterView.setVisible(true);
		          	      Main.senserInfoView.setVisible(false);
		          	      Main.acterView.table.setValueAt(table.getValueAt(table.getSelectedRow(), 2), 1, 1);
		          	      new GetActerInfo(Main.gatewayManger.getVoiceGateway(tree.getLastNode().toString())).start();
		                }
		                else if(table.getValueAt(table.getSelectedRow(), 0).equals(SenserType.SENSERTYPE_IPD_ZJM))
		                {
		          	      Main.ipdView.setVisible(true);
		          	      Main.gatewayManger.getVoiceGateway(tree.getLastNode().toString()).sendCmd(Main.cmdMaker.GetIPDInfoCmd());
		                }
		                else {
		          	      Main.senserInfoView.setVisible(true);
		          	      Main.acterView.setVisible(false);
		          	      Main.senserInfoView.table.setValueAt(table.getValueAt(table.getSelectedRow(), 2), 1, 1);
		          	      new GetSenserInfo(Main.gatewayManger.getVoiceGateway(tree.getLastNode().toString())).start();
		                }
		            }
		              else if( e.getButton() == MouseEvent.BUTTON3)
		              {
		          	    table.setRowSelectionInterval(table.rowAtPoint(e.getPoint()), table.rowAtPoint(e.getPoint()));
		          	    if(table.getValueAt(table.getSelectedRow(), 0).equals(SenserType.SENSERTYPE_IPD_ZJM)) {
		          		    IPD_ZJMMenu.show(e.getComponent(), e.getX(), e.getY());
		          	    }
		          	    else if(table.getValueAt(table.getSelectedRow(), 0).equals(SenserType.SENSERTYPE_BOARDCAST)) {
		          		    boardCastMenu.show(e.getComponent(), e.getX(), e.getY());
		          	    }
		          		    
		              }
		 }	 
		});
		scrollPane_1.setViewportView(table);
		boardcasttable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		boardcasttable.setBounds(0, 0, 1, 1);
		boardcasttable.setRowHeight(30);
		boardcasttable.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		boardcasttable.setDefaultRenderer(Object.class, Rstyle); 
		boardcasttable.setModel(boardcastModel);
		boardcasttable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getButton() == MouseEvent.BUTTON3)
				{
					boardCastMenu.show(arg0.getComponent(), arg0.getX(), arg0.getY());
					boardcasttable.setRowSelectionInterval(boardcasttable.rowAtPoint(arg0.getPoint()), boardcasttable.rowAtPoint(arg0.getPoint()));
				}
			}
		});
		tabbedPane.addTab("广播列表", null, scrollPane_2, null);
		scrollPane_2.setViewportView(boardcasttable);
		
		playMusic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					int i;
					if((i=table.getSelectedRow())!=-1){
						boardCast=Main.boardCastManger.getBoardCast(tree.getLastNode().toString(),table.getValueAt(i, 2).toString());
						if(boardCast!=null) {
							boardCast.getBoardCastInfo();
							boardCast.sendMusic();
							Main.musicPlayView.setBoardCast(boardCast);
							Main.musicPlayView.setVisible(true);
					}
					}
					if((i=boardcasttable.getSelectedRow())!=-1) {
						boardCast = Main.boardCastManger.getBoardCast(boardcastInfo[i][1].toString(),boardcastInfo[i][2].toString());
						if(boardCast!=null) {
							boardCast.getBoardCastInfo();
							boardCast.sendMusic();
							Main.musicPlayView.setBoardCast(boardCast);
							Main.musicPlayView.setVisible(true);
					}
				}
			}
		});

		listenBoardCast.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
					int i;
					if((i=boardcasttable.getSelectedRow())!=-1) {
						boardCast = Main.boardCastManger.getBoardCast(boardcastInfo[i][1].toString(),boardcastInfo[i][2].toString());
					if(boardCast!=null)
						boardCast.startListen();
					}
			}
		});
		
		debugBtn.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		debugBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(debugBtn.getText().equals("打开调试信息")) {
					debugBtn.setText("关闭调试信息");
					Main.debugView.setVisible(true);
					Main.debugView.checkshow.setSelected(true);
					Main.debugView.showDebugInfo = true;
				}else {
					debugBtn.setText("打开调试信息");
					Main.debugView.setVisible(false);
					Main.debugView.zantingshow.setSelected(true);
					Main.debugView.showDebugInfo = false;
					Main.debugView.clearText();
				}
			}
		});
		debugBtn.setBounds(5, 497, 111, 29);
		contentPane.add(debugBtn);
		setContentPane(contentPane);
		
		JButton button = new JButton("连接阿里云");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.aliLinkView.setVisible(true);
				
			}
		});
		button.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		button.setBounds(120, 497, 111, 29);
		contentPane.add(button);
	}
	
	public void addGatewayNode(String gatewayType,String gatewayIp) {
		tree.addNode(gatewayType, gatewayIp);
		
	}

	public void addBoardcast(String setAddr,String voiceGatewayIp,String boardcastAddr) {
		int i = boardcastModel.getRowCount()+1,j=0;
		Object[][] objects = boardcastInfo;
		boardcastModel = null;
		boardcastInfo = null;
		boardcastInfo = new Object[i][3];
		for(;j<i-1;j++) {
			boardcastInfo[j] =objects[j]; 
		}
		boardcastInfo[j][0] = setAddr;
		boardcastInfo[j][1] = voiceGatewayIp;
		boardcastInfo[j][2] = boardcastAddr;
		boardcastModel = new DefaultTableModel(boardcastInfo, boardcastTableTitle);
		boardcasttable.setModel(boardcastModel);
		boardcasttable.updateUI();
	}
	public void stopListen() {
		boardCast.stopListen();
		listenBoardCast.setText("监听");
	}

	public class GetSenserInfo extends Thread{
		VoiceGateway voiceGateway;
		public GetSenserInfo(VoiceGateway voiceGateway ) {
			this.voiceGateway = voiceGateway;
		}
		@Override
		public void run() {
			try {
				voiceGateway.sendCmd(Main.cmdMaker.getSenserInfoCmd(Byte.parseByte((String) table.getValueAt(table.getSelectedRow(), 2))));
				Thread.sleep(80);
				voiceGateway.sendCmd(Main.cmdMaker.getUpWornCmd(Byte.parseByte((String) table.getValueAt(table.getSelectedRow(), 2))));
				Thread.sleep(80);
				voiceGateway.sendCmd(Main.cmdMaker.getDownWornCmd(Byte.parseByte((String) table.getValueAt(table.getSelectedRow(), 2))));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void clearTable(int index) {
		int i,j;
		for(i=index;i<128-index;i++) {
			for(j=0;j<5;j++)
				table.setValueAt("", i, j);
		}
	}
	public class GetActerInfo extends Thread{
		VoiceGateway voiceGateway;
		public GetActerInfo(VoiceGateway voiceGateway ) {
			this.voiceGateway = voiceGateway;
		}
		@Override
		public void run() {
			try {
				voiceGateway.sendCmd(Main.cmdMaker.getSenserInfoCmd(Byte.parseByte((String) table.getValueAt(table.getSelectedRow(), 2))));
				Thread.sleep(80);
				voiceGateway.sendCmd(Main.cmdMaker.getGuanlianAddrCmd(Byte.parseByte((String) table.getValueAt(table.getSelectedRow(), 2))));
				Thread.sleep(80);
				voiceGateway.sendCmd(Main.cmdMaker.getGuanlianTypeCmd(Byte.parseByte((String) table.getValueAt(table.getSelectedRow(), 2))));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
