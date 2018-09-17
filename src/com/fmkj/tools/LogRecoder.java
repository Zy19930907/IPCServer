package com.fmkj.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogRecoder {
	public void saveLog(String LogFilePath, String log) {
		File logFile = new File(LogFilePath);
		File fileParent = logFile.getParentFile();
		if (!logFile.exists()) {
			fileParent.mkdirs();
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileWriter logWriter;
		BufferedWriter logBufferedWriter = null;
		try {
			logWriter = new FileWriter(logFile, true);
			logBufferedWriter = new BufferedWriter(logWriter);
			if (logBufferedWriter != null) {
				logBufferedWriter.write(log);
				logBufferedWriter.flush();
				logBufferedWriter.close();
				logBufferedWriter = null;
			}
			if (logWriter != null) {
				logWriter.close();
				logWriter = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			try {
				logBufferedWriter.write(e.getMessage());
				logBufferedWriter.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
