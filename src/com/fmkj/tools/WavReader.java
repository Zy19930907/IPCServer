package com.fmkj.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JOptionPane;

import com.fmkj.Begin.Main;

public class WavReader {
	private File playFile;
	private String musicPath = System.getProperty("user.dir")+"\\Music\\";
	private File[] files = new File(musicPath).listFiles();
	private Object[] possibleValues = null;
	private int i = 0;
	private byte[] wavData;
	private int musicLen;
	private InputStream inputStream;
	private int pcmOffset;
	private int playCnt;
	private int readCnt = 0;
	private int packLen = 2000;
	private int totalSec;

	public WavReader() {
		if(files.length>1) {
			possibleValues = null;
			possibleValues = new Object[files.length];
			for(File file:files) {
				possibleValues[i++] = file.getName();
			}
			Object selectedValue = JOptionPane.showInputDialog(null, "", "选择测试语音", 
						JOptionPane.INFORMATION_MESSAGE, null, 
						possibleValues, possibleValues[0]);
			playFile = new File(musicPath+selectedValue);
			
		}else
			playFile = files[0];
		try {
			inputStream = new FileInputStream(playFile);
			musicLen = (int) playFile.length();
			wavData = new byte[musicLen];
			inputStream.read(wavData,0,musicLen);
			pcmOffset = getPcmAddr();
			playCnt = (musicLen - pcmOffset) / packLen;
			totalSec = playCnt/8;
			Main.musicPlayView.setMusicTime(totalSec);
			Main.musicPlayView.setMusicName(playFile.getName());
			if(inputStream != null)
			{
				inputStream.close();
				inputStream = null;
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,"请将wav格式文件放入  "+musicPath, "音乐文件不存在" , JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public WavReader(String musicName) {
		playFile = new File(musicPath+musicName);
		try {
			inputStream = new FileInputStream(playFile);
			musicLen = (int) playFile.length();
			wavData = new byte[musicLen];
			inputStream.read(wavData,0,musicLen);
			pcmOffset = getPcmAddr();
			playCnt = (musicLen - pcmOffset) / packLen;
			totalSec = playCnt/8;
			Main.musicPlayView.setMusicTime(totalSec);
			Main.musicPlayView.setMusicName(playFile.getName());
			inputStream.close();
			inputStream = null;
			playFile = null;
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,"请将wav格式文件放入  "+musicPath, "音乐文件不存在" , JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getPcmAddr() {
		int i,j;
		byte[] temp = new byte[4];
		for(i=0;i<musicLen-4;i++) {
			for(j=0;j<4;j++){
				temp[j] = wavData[(i+j)];
			}
			if(new String(temp).equals("data"))
				return i+4;
		}
		return 0;
	}
	public byte[] getNextPcmPack() {
		byte[] pcmPack = new byte[packLen];
		if(readCnt<playCnt)
			System.arraycopy(wavData, (readCnt++)*packLen+pcmOffset, pcmPack, 0, packLen);
		else if(readCnt>=playCnt) {
			readCnt = 0;
			Main.musicPlayView.setVisible(false);
			Main.musicPlayView.playslider.setValue(0);
			Main.musicPlayView.voicebuffer.setValue(0);
			Main.musicPlayView.setPlayedTime(0);
			Main.musicPlayView.playslider.setValue(0);
			Main.gatewayManger.setGetvoiceboardinfo(true);
			return null;
		}
		Main.musicPlayView.setPlayedTime(readCnt/8);
		Main.musicPlayView.playslider.setValue(readCnt*125);
		return pcmPack;
	}
	public void setReadCnt(int readCnt) {
		this.readCnt = readCnt;
	}
	
	public void stopRead() {
		readCnt = playCnt-1;
		Main.musicPlayView.setVisible(false);
		Main.musicPlayView.playslider.setValue(0);
		Main.musicPlayView.voicebuffer.setValue(0);
		Main.musicPlayView.setPlayedTime(0);
		Main.musicPlayView.playslider.setValue(0);
		wavData = null;
		System.gc();
	}
}
