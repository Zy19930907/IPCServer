package com.fmkj.views;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Font;

@SuppressWarnings("serial")
public class PekingPowerView extends JFrame {

	private JPanel contentPane;
	public JTable table;
	private DefaultTableModel PekingPowerInfoModel;
	public Object[][] PekingPowerInfo= new Object[16][4];
	DefaultTableCellRenderer r = new DefaultTableCellRenderer();
	DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
	public PekingPowerView() {
		setResizable(false);
		setBounds(100, 100, 731, 424);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		r.setHorizontalAlignment(JLabel.CENTER);     
		renderer.setBackground(Color.BLUE);
		renderer.setHorizontalAlignment(JLabel.CENTER);
		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 705, 380);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 705, 380);
		panel.add(scrollPane);
		
		table = new JTable();
		table.setFont(new Font("宋体", Font.PLAIN, 13));
		int i;
		PekingPowerInfoModel = new DefaultTableModel(PekingPowerInfo, new String[] {"","","",""});
		table.setModel(PekingPowerInfoModel);
		table.setDefaultRenderer(Object.class, r); 
		for(i=0;i<8;i++)
		{
			table.setRowHeight(2*i, 15);
			table.setRowHeight(2*i+1, 30);
		}
		settableinfo();
		scrollPane.setViewportView(table);
	}
	public void settableinfo() {
		int i=0,j=0;
		table.setValueAt("供电状态", i, j++);table.setValueAt("电池剩余量", i, j++);table.setValueAt("第一路电压", i, j++);table.setValueAt("第一路电流", i, j++);
		i+=2;j=0;
		table.setValueAt("第二路电压", i, j++);table.setValueAt("第二路电流", i, j++);table.setValueAt("第三路电压", i, j++);table.setValueAt("第三路电流", i, j++);
		i+=2;j=0;
		table.setValueAt("第四路电压", i, j++);table.setValueAt("第四路电流", i, j++);table.setValueAt("第五路电压", i, j++);table.setValueAt("第五路电流", i, j++);
		i+=2;j=0;
		table.setValueAt("第六路电压", i, j++);table.setValueAt("第六路电流", i, j++);table.setValueAt("第七路电压", i, j++);table.setValueAt("第七路电流", i, j++);
		i+=2;j=0;
		table.setValueAt("第八路电压", i, j++);table.setValueAt("第八路电流", i, j++);table.setValueAt("电池电压", i, j++);table.setValueAt("电池电流", i, j++);
		i+=2;j=0;
		table.setValueAt("电池总电压", i, j++);table.setValueAt("电池总电流", i, j++);table.setValueAt("负载电流", i, j++);table.setValueAt("储备电量", i, j++);
		i+=2;j=0;
		table.setValueAt("剩余电量", i, j++);table.setValueAt("交流输入电压", i, j++);table.setValueAt("交流输入频率", i, j++);table.setValueAt("交流输出电压", i, j++);
		i+=2;j=0;
		table.setValueAt("交流输出频率", i, j++);table.setValueAt("剩余工作时间", i, j++);table.setValueAt("负载容量", i, j++);table.setValueAt("控制状态", i, j++);
	}
}
