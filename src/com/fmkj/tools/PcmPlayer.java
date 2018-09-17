package com.fmkj.tools;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class PcmPlayer {
	 private AudioFormat af = new AudioFormat(8000, 16, 1, true, false);
	 private SourceDataLine.Info info = new DataLine.Info(SourceDataLine.class,af, 4000000);
	 public SourceDataLine sdl;   
	 public PcmPlayer() {
		 try {
			sdl = (SourceDataLine) AudioSystem.getLine(info);
			sdl.open();
			sdl.start();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	 public void play(byte[] pcm) {
		 sdl.write(pcm, 0, pcm.length);
	 }
	 public void playerStart() {
		 sdl.start();
	 }
	 public void playerStop() {
		 sdl.stop();
	 }
	 public void playerClose() {
		 sdl.drain();
		 sdl.close();
		 sdl = null;
		 af = null;
		 info = null;
		 System.gc();
	 }
}
