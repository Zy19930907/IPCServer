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

@SuppressWarnings("serial")
public class SenserInfoView extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel senserInfoModel;
	private DefaultTableModel wornInfoModel;
	private Object[][] senserInfo = new Object[2][5];
	private String[] senserInfoTitle = new String[] {"传感器类型","传感器地址","重启次数","输入电压","主动上传时间"};
	private Object[][] wornInfo = new Object[4][3];
	public JTable table;
	DefaultTableCellRenderer r = new DefaultTableCellRenderer();
	public JTable table_1;
	public SenserInfoView() {
		setResizable(false);
		setBounds(100, 100, 459, 232);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		r.setHorizontalAlignment(JLabel.CENTER);

		senserInfo[0][0] = "传感器类型";senserInfo[0][1] = "传感器地址";senserInfo[0][2] = "重启次数";senserInfo[0][3] = "输入电压";
		senserInfo[0][4] = "主动上传时间";
		senserInfoModel = new DefaultTableModel(senserInfo, senserInfoTitle);
		wornInfo[0][0] = "上限断电";wornInfo[0][1] = "上限复电";wornInfo[0][2] = "上限报警";
		wornInfo[2][0] = "下限断电";wornInfo[2][1] = "下限复电";wornInfo[2][2] = "下限报警";
		wornInfoModel = new DefaultTableModel(wornInfo, new String[] {"","",""});
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 453, 204);
		contentPane.add(panel);
		panel.setLayout(null);
		
		table = new JTable();
		table.setBounds(10, 10, 434, 43);
		panel.add(table);
		table.setFillsViewportHeight(true);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setRowSelectionAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFont(new Font("宋体", Font.PLAIN, 13));
		table.setDefaultRenderer(Object.class, r);
		table.setRowHeight(0,15);
		table.setRowHeight(1, 20);
		table.setModel(senserInfoModel);
		
		table_1 = new JTable();
		table_1.setBounds(10, 80, 434, 98);
		panel.add(table_1);
		table_1.setRowSelectionAllowed(false);
		table_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_1.setFont(new Font("宋体", Font.PLAIN, 13));
		table_1.setDefaultRenderer(Object.class, r);
		table_1.setModel(wornInfoModel);
	}
}
