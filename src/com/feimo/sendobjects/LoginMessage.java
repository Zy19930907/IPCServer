package com.feimo.sendobjects;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LoginMessage implements Serializable {
	private String MySyetemName;
	private String MySystemPassword;
	private String CloudPassword;
	private String LoginState;
	private String LoginIp;
	public String getLoginIp() {
		return LoginIp;
	}
	public void setLoginIp(String loginIp) {
		LoginIp = loginIp;
	}
	public String getMySyetemName() {
		return MySyetemName;
	}
	public void setMySyetemName(String mySyetemName) {
		MySyetemName = mySyetemName;
	}
	public String getMySystemPassword() {
		return MySystemPassword;
	}
	public void setMySystemPassword(String mySystemPassword) {
		MySystemPassword = mySystemPassword;
	}
	public String getCloudPassword() {
		return CloudPassword;
	}
	public void setCloudPassword(String cloudPassword) {
		CloudPassword = cloudPassword;
	}
	public String getLoginState() {
		return LoginState;
	}
	public void setLoginState(String loginState) {
		LoginState = loginState;
	}
}
