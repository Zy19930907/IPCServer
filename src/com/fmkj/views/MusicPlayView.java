package com.fmkj.views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import com.fmkj.boardcast.BoardCast;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import javax.swing.JProgressBar;
import java.awt.Color;
import java.awt.Font;

@SuppressWarnings("serial")
public class MusicPlayView extends JFrame {
	public JProgressBar voicebuffer = new JProgressBar();
	public JSlider playslider = new JSlider();
	private BoardCast boardCast;
	private SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
	Calendar c=Calendar.getInstance();
	JLabel curtime = new JLabel("");
	JLabel totaltime = new JLabel("");
	JLabel musicname = new JLabel("");
	public JSlider volumslider = new JSlider();
	public void setBoardCast(BoardCast boardCast) {
		this.boardCast = boardCast;
	}
	private JPanel contentPane;
	public MusicPlayView() {
		setResizable(false);
		setBounds(100, 100, 450, 181);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		playslider.setValue(0);
		playslider.setBounds(0, 40, 444, 23);
		playslider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(playslider.getValue()/125>3)
					boardCast.voiceGateway.wavReader.setReadCnt(playslider.getValue()/125);
			}
		});
		contentPane.add(playslider);
		curtime.setHorizontalAlignment(SwingConstants.LEFT);
		curtime.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		curtime.setBounds(0, 15, 54, 15);
		contentPane.add(curtime);
		totaltime.setHorizontalAlignment(SwingConstants.RIGHT);
		totaltime.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		totaltime.setBounds(390, 15, 54, 15);
		contentPane.add(totaltime);
		musicname.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		musicname.setHorizontalAlignment(SwingConstants.CENTER);
		musicname.setBounds(97, 10, 263, 26);
		contentPane.add(musicname);
		
		JButton stopplaybtn = new JButton("停止播放");
		stopplaybtn.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		stopplaybtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boardCast.stopSendMusic();
				setVisible(false);
			}
		});
		stopplaybtn.setBounds(10, 118, 93, 23);
		contentPane.add(stopplaybtn);
		
		JLabel label_2 = new JLabel(new ImageIcon(System.getProperty("user.dir")+"\\Icon\\喇叭.png"));
		label_2.setBounds(128, 113, 35, 34);
		contentPane.add(label_2);

		volumslider.setValue(0);
		volumslider.setMaximum(79);
		volumslider.setBounds(171, 118, 263, 26);
		volumslider.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				boardCast.setVolum((byte) volumslider.getValue());
				//boardCast.getBoardCastInfo();
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
				
			}
		});
		JLabel volumshow = new JLabel("");
		volumshow.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		volumshow.setBounds(267, 103, 54, 15);
		contentPane.add(volumshow);
		volumslider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				volumshow.setText(String.valueOf(volumslider.getValue()) + " / "+String.valueOf(volumslider.getMaximum()));
			}
		});
		contentPane.add(volumslider);
		voicebuffer.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		voicebuffer.setForeground(new Color(46, 139, 87));
		voicebuffer.setMaximum(20000);
		voicebuffer.setStringPainted(true);
		
		voicebuffer.setBounds(70, 73, 364, 23);
		contentPane.add(voicebuffer);
		
		JLabel label = new JLabel("缓存区");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(10, 76, 54, 15);
		contentPane.add(label);
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				stopplaybtn.doClick();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
			}
			
			@Override
			public void windowActivated(WindowEvent e) {		
			}
		});
	}
	public void setMusicTime(int sec) {
		playslider.setMaximum(sec*1000);
		c.setTimeInMillis(((long)sec)*1000);
		totaltime.setText(sdf.format(c.getTime()));
	}
	
	public void setPlayedTime(int sec) {
		c.setTimeInMillis(((long)sec)*1000);
		curtime.setText(sdf.format(c.getTime()));
	}
	
	public void setMusicName(String name) {
		musicname.setText(name);
	}
	public void setPlayedMs(int ms) {
		playslider.setValue(ms);
	}
}
