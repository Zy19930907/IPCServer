package com.fmkj.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import com.fmkj.Begin.Main;
public class BoardCastConfigReader {
	private File ipFile = new File(System.getProperty("user.dir")+"\\Configs\\boardcast.config");
	public BufferedReader boardCastReader; 
	private FileReader fileReader;

	public void readBoardCastsFromFile() {
		String[] boardCastInfos = new String[4];
		 String boardCastConfigInfo;
		 try {
			fileReader = new FileReader(ipFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		 boardCastReader = new BufferedReader(fileReader);
			try {
				while((boardCastConfigInfo = boardCastReader.readLine())!=null) {
					boardCastInfos = boardCastConfigInfo.split("-");
					Main.boardCastManger.addBoardCast(boardCastInfos[1], NomalDataSwitch.stringToByte(boardCastInfos[2]));
					Main.mainView.addBoardcast(boardCastInfos[0],boardCastInfos[1],boardCastInfos[2]);
				}
				boardCastReader.close();
				fileReader.close();
				boardCastReader = null;
				fileReader = null;
			} catch (IOException e) {
				e.printStackTrace();
		}
	}
}
