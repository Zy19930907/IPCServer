package com.feimo.sendobjects;

public class AndroidMusicCmd {
	private byte DataDir;
	private String[] MusicList;
	private String[] BoardCastList;
	private String MusicSelect;
	private String BoardCastSelect;
	public String[] getMusicList() {
		return MusicList;
	}
	public void setMusicList(String[] musicList) {
		MusicList = musicList;
	}
	public String getMusicSelect() {
		return MusicSelect;
	}
	public void setMusicSelect(String musicSelect) {
		MusicSelect = musicSelect;
	}
	public String[] getBoardCastList() {
		return BoardCastList;
	}
	public void setBoardCastList(String[] boardCastList) {
		BoardCastList = boardCastList;
	}
	public String getBoardCastSelect() {
		return BoardCastSelect;
	}
	public void setBoardCastSelect(String boardCastSelect) {
		BoardCastSelect = boardCastSelect;
	}
	public byte getDataDir() {
		return DataDir;
	}
	public void setDataDir(byte dataDir) {
		DataDir = dataDir;
	}
}
