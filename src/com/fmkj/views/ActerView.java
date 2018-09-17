package com.fmkj.views;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class ActerView extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel senserInfoModel;
	private DefaultTableModel wornInfoModel;
	private Object[][] senserInfo = new Object[2][5];
	private String[] senserInfoTitle = new String[] {"传感器类型","传感器地址","重启次数","输入电压","主动上传时间"};
	private Object[][] actInfo = new Object[6][2];
	public JTable table;
	DefaultTableCellRenderer r = new DefaultTableCellRenderer();
	public JTable table_1;
	private JScrollPane scrollPane;
	public ActerView() {
		setResizable(false);
		setBounds(100, 100, 600, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		r.setHorizontalAlignment(JLabel.CENTER);

		senserInfo[0][0] = "传感器类型";senserInfo[0][1] = "传感器地址";senserInfo[0][2] = "重启次数";senserInfo[0][3] = "输入电压";
		senserInfo[0][4] = "主动上传时间";
		
		senserInfoModel = new DefaultTableModel(senserInfo, senserInfoTitle);
		wornInfoModel = new DefaultTableModel(actInfo, new String[] {"关联地址","关联类型"});
		contentPane.setLayout(null);
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setBounds(10, 10, 574, 42);
		contentPane.add(table);
		table.setRowSelectionAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFont(new Font("宋体", Font.PLAIN, 12));
		table.setDefaultRenderer(Object.class, r);
		table.setRowHeight(0,15);
		table.setRowHeight(1, 50);
		table.setModel(senserInfoModel);
		
		table_1 = new JTable();
		table_1.setBounds(10, 52, 564, 130);
		table_1.setRowSelectionAllowed(false);
		table_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_1.setFont(new Font("宋体", Font.PLAIN, 12));
		table_1.setDefaultRenderer(Object.class, r);
		table_1.setModel(wornInfoModel);
		table_1.getColumnModel().getColumn(0).setMaxWidth(80);
		table_1.getColumnModel().getColumn(0).setMinWidth(80);
		int i=0;
		for(i=0;i<6;i++)
			table_1.setRowHeight(i, 30);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 62, 574, 220);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(table_1);
	}

}
