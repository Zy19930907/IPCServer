package com.fmkj.boardcast;

import java.util.ArrayList;
import java.util.List;

import com.fmkj.tools.NomalDataSwitch;

public class BoardCastManger {
	private List<BoardCast> boardCasts = new ArrayList<BoardCast>();
	private BoardCast boardCast;
	public void addBoardCast(String voiceGatewayIp,byte addr) {
		boardCast = new BoardCast(voiceGatewayIp, addr);
		boardCasts.add(boardCast);
		boardCast = null;
	}
	public BoardCast getBoardCast(String voiceGatewayIp,String boardcastAddr) {
		int i;
		for(i=0;i<boardCasts.size();i++) {
			if(boardCasts.get(i).voiceGatewayIp.equals(voiceGatewayIp) && NomalDataSwitch.byteToDecimalString(boardCasts.get(i).addr).equals(boardcastAddr))
				return boardCasts.get(i);
		}
		return null;
	}
}
