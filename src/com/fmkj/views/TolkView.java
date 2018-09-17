package com.fmkj.views;

import java.text.SimpleDateFormat;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.fmkj.Begin.Main;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

@SuppressWarnings("serial")
public class TolkView extends JFrame {

	private JPanel contentPane;
	public JLabel castaddr = new JLabel("");
	public long tolkTime = 0,time1,time2;
	private SimpleDateFormat timefmt = new SimpleDateFormat("mm:ss");
	public boolean reset = true;
	JLabel lblNewLabel = new JLabel("");
	private String viconPath = System.getProperty("user.dir")+"\\Icon\\广播.png";
	private Icon vIcon = new ImageIcon(viconPath);
	
	public TolkView() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 338, 127);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("广播地址");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(129, 10, 67, 31);
		contentPane.add(label);
		castaddr.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		castaddr.setBounds(196, 10, 51, 31);
		contentPane.add(castaddr);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblNewLabel.setBounds(129, 37, 86, 41);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel(vIcon,SwingConstants.CENTER);
		lblNewLabel_1.setBounds(35, 10, 81, 64);
		contentPane.add(lblNewLabel_1);
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
				Main.mainView.stopListen();
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {
			}
			
			@Override
			public void windowActivated(WindowEvent arg0) {
			}
		});
	}
	public void freshTolkTime() {
		if(reset) {
			time2 = System.currentTimeMillis();
			reset = false;
		}
		time1 = System.currentTimeMillis();
		tolkTime += time1 - time2;
		time2 = time1;
		lblNewLabel.setText(timefmt.format(tolkTime));
	}
}
