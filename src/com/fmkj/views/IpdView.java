package com.fmkj.views;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class IpdView extends JFrame {

	private JPanel contentPane;
	public JTable table_1 = new JTable();
	public Object[][] IpdInfo = new Object[22][4];
	private DefaultTableModel IPDInfoModel;
	DefaultTableCellRenderer r = new DefaultTableCellRenderer();
	public IpdView() {
		setResizable(false);
		setBounds(100, 100, 883, 546);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		r.setHorizontalAlignment(JLabel.CENTER);     
		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 872, 512);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 872, 511);
		panel.add(scrollPane);
		
		table_1.setRowSelectionAllowed(false);
		table_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_1.setBounds(0, 0, 1, 1);
		table_1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		table_1.setDefaultRenderer(Object.class, r); 
		setIpdInfo();
		IPDInfoModel = new DefaultTableModel(IpdInfo,new String[] {"","","",""});
		table_1.setModel(IPDInfoModel);
		int i;
		for(i=0;i<11;i++)
		{
			table_1.setRowHeight(2*i, 16);
			table_1.setRowHeight(2*i+1, 30);
		}
		scrollPane.setViewportView(table_1);
	}
	
	public void setIpdInfo() 
	{
		int i=0,j=0;
		IpdInfo[i][j++] = "照明额定电流";IpdInfo[i][j++] = "信号额定电流";IpdInfo[i][j++] = "照明速断倍数";IpdInfo[i][j++] = "信号速断倍数";
		i+=2;j=0;
		IpdInfo[i][j++] = "欠压定值";IpdInfo[i][j++] = "欠压延时";IpdInfo[i][j++] = "过压定值";IpdInfo[i][j++] = "过压延时";
		i+=2;j=0;
		IpdInfo[i][j++] = "漏电电阻";IpdInfo[i][j++] = "漏电延时";IpdInfo[i][j++] = "风电瓦斯延时";IpdInfo[i][j++] = "设备地址";
		i+=2;j=0;
		IpdInfo[i][j++] = "UAB";IpdInfo[i][j++] = "UBC";IpdInfo[i][j++] = "UCA";IpdInfo[i][j++] = "当前照明电流A";
		i+=2;j=0;
		IpdInfo[i][j++] = "当前照明电流B";IpdInfo[i][j++] ="当前信号电流B";IpdInfo[i][j++] = "RG";IpdInfo[i][j++] = "XB1";
		i+=2;j=0;
		IpdInfo[i][j++] = "KB1";IpdInfo[i][j++] = "系统时间";IpdInfo[i][j++] = "";IpdInfo[i][j++] ="";
		i+=2;j=0;
		IpdInfo[i][j++] =  "事件1"; IpdInfo[i][j++] ="事件1动作值";IpdInfo[i][j++] ="事件1时间";IpdInfo[i][j++] ="";
		i+=2;j=0;
		IpdInfo[i][j++] = "事件2";IpdInfo[i][j++] ="事件2动作值";IpdInfo[i][j++] ="事件2时间";IpdInfo[i][j++] ="";
		i+=2;j=0;
		IpdInfo[i][j++] = "事件3";IpdInfo[i][j++] = "事件3动作值";IpdInfo[i][j++] ="事件3时间";IpdInfo[i][j++] = "";
		i+=2;j=0;
		IpdInfo[i][j++] = "事件4";IpdInfo[i][j++] = "事件4动作值";IpdInfo[i][j++] = "事件4时间";IpdInfo[i][j++] = "";
		i+=2;j=0;
		IpdInfo[i][j++] = "事件5";IpdInfo[i][j++] = "事件5动作值";IpdInfo[i][j++] = "事件5时间";IpdInfo[i][j++] = "";
	}
}
