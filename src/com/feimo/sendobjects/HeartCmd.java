package com.feimo.sendobjects;

import java.io.Serializable;

@SuppressWarnings("serial")
public class HeartCmd implements Serializable{
	private String heart;

	public String getHeart() {
		return heart;
	}

	public void setHeart(String heart) {
		this.heart = heart;
	}
}
