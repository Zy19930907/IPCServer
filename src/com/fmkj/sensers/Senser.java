package com.fmkj.sensers;

import com.fmkj.Begin.Main;
import com.fmkj.tools.NomalDataSwitch;
import com.fmkj.values.GatewayType;
import com.fmkj.values.LogFileName;
import com.fmkj.values.SenserInfo;

public class Senser {
	public String SenserName = "等待连接";
	public String senserInfo = "";
	public String concentration= "";
	public String temp="";
	public String linkState="";
	public String senserAddr="";
	public String canCode="";
	public String senserMsg="";
	public String listenValue="";
	public String dataType="";
	int nongdu = 0,j;
	double concen;
	
	public String getLinkState() {
		return linkState;
	}
	public void setLinkState(String linkState) {
		this.linkState = linkState;
	}
	public String getSenserAddr() {
		return senserAddr;
	}
	public void setSenserAddr(String senserAddr) {
		this.senserAddr = senserAddr;
	}
	public String getSenserMsg() {
		return senserMsg;
	}
	public void setSenserMsg(String senserMsg) {
		this.senserMsg = senserMsg;
	}
	public void initUniversalData(byte[] data){
	if ((data[2]&0x80)==0) {
		linkState = SenserInfo.SENSERSTATE_CONNECTED;
		senserInfo += SenserInfo.SENSERSTATE_CONNECTED;      
	}
	else {
		linkState = SenserInfo.SENSERSTATE_DISCONNECTED;
		senserInfo = SenserInfo.SENSERSTATE_DISCONNECTED;      
	}
	
	getSenserAddr(data[0]);
	
	switch (data[2]&0x60){
	            case 0x00:
	          	  canCode = SenserInfo.SENSERSTATE_CANCODE1;
	                break;
	            case 0x20:
	          	  canCode = SenserInfo.SENSERSTATE_CANCODE2;
	                break;
	            case 0x40:
	          	  canCode = SenserInfo.SENSERSTATE_CANCODE3;
	                break;
	            case 0x60:
	          	  canCode = SenserInfo.SENSERSTATE_CANCODE4;
	                break;
	        }
	    }
	    public  void getConcentration(byte[] msg,String dataUnit){
	        nongdu = (((NomalDataSwitch.abs(msg[4])*256)+NomalDataSwitch.abs(msg[3])));
	        concen = (double)(nongdu & 0x000003FF);
	        switch (msg[2]&0x0C){
	            case 0x00:
	          	 dataType="正常";
	                break;
	            case 0x04:
	          	  dataType= "开机预热";
	                break;
	            case 0x08:
	          	  dataType="标定";
	                break;
	            case 0x0C:
	          	  dataType="历史";
	                break;
	        }
	        switch (msg[2]&0x03){
	            case 0x00:
	          	  listenValue = String.valueOf(concen);
	                break;
	            case 0x01:
	          	  concen = concen/10;
	          	  listenValue = String.valueOf(concen);
	                break;
	            case 0x02:
	          	  concen = concen/100;
	          	  listenValue = String.valueOf(concen);
	                break;
	            case 0x03:
	          	  concen = concen/1000;
	          	  listenValue = String.valueOf(concen);
	                break;
	        }
	        listenValue+=dataUnit;
	        if(dataType.equals("开机预热"))
		        listenValue = dataType; 
	    }
	    public String getSenserName() {
		return SenserName;
	}
	public void setSenserName(String senserName) {
		SenserName = senserName;
	}
	public void getSenserAddr(byte addcode){
		senserInfo += byteToOString(addcode);
		senserAddr = byteToOString(addcode);
	  }
	    public String bytesToString(byte[] msg){
	        String[] buf = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
	        String out = "";
	        int i = 0;
	        int k = 0;
	        for (i=0;i<msg.length;i++){
	            if(msg[i]<0)
	                k = msg[i]+256;
	            else
	                k= msg[i];
	                    out += buf[k>>4];
	                    out +=buf[k&0x0f];
	            out+= " ";
	        }
	        return out;
	    }
	    public String byteToString(byte msg){
	        String[] buf = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
	        String out = "";
	        int k = 0;
	        k = Math.abs(msg);
	        out += buf[k>>4];
	        out +=buf[k&0x0f];
	        return out;
	    }
	    public String intToString(int msg){
	        String[] buf = {"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};
	        String out = "";
	        out += buf[msg>>12];
	        out += buf[(msg>>8)&0x000f];
	        out += buf[(msg>>4)&0x000f];
	        out += buf[msg&0x000f];
	        return out;
	    }
	    public String byteToOString(byte in){
	        int i,temp=0;
	        if(in<0)
	            i = in+256;
	        else
	            i = in;
	        temp += (i>>4)*16;
	        temp += i &0x0f;
	        return String.valueOf(temp);
	    }
	    public void saveSenserLog(String gatewayIp,String gatewaytype) {
		    if(gatewaytype.equals(GatewayType.GATEWAYTYPE_CAN))
		    {
		    Main.logRecoder.saveLog(LogFileName.Log_CanGateway+gatewayIp+"\\"+SenserName+"\\"+senserAddr+"\\"+Main.datetool.getTimeH()+SenserName+"-"+senserAddr+".log", Main.datetool.getTimeHMS()+
				    SenserName+NomalDataSwitch.getEmp1(SenserName)+linkState+NomalDataSwitch.getEmp1(linkState)+"ADDR-"+senserAddr+NomalDataSwitch.getEmp1("ADDR-"+senserAddr)+"CAN-"+canCode+NomalDataSwitch.getEmp1("CAN-"+canCode)+listenValue+"\n");
		    if(SenserInfo.SENSERSTATE_DISCONNECTED.equals(linkState)) {
			    Main.logRecoder.saveLog(LogFileName.Log_CanGateway+gatewayIp+"\\ErrLog"+"\\"+Main.datetool.getTimeH()+"错误日志.log",Main.datetool.getTimeHMS()+
					    SenserName+NomalDataSwitch.getEmp1(SenserName)+linkState+NomalDataSwitch.getEmp1(linkState)+"ADDR-"+senserAddr+NomalDataSwitch.getEmp1("ADDR-"+senserAddr)+"CAN-"+canCode+NomalDataSwitch.getEmp1("CAN-"+canCode)+listenValue+"\n");
		    }
		    }else if(gatewaytype.equals(GatewayType.GATEWAYTYPE_VOICE))
		    {
			    Main.logRecoder.saveLog(LogFileName.Log_VoiceGateway+gatewayIp+"\\"+SenserName+"\\"+senserAddr+"\\"+Main.datetool.getTimeH()+SenserName+"-"+senserAddr+".log", Main.datetool.getTimeHMS()+
					    SenserName+NomalDataSwitch.getEmp1(SenserName)+linkState+NomalDataSwitch.getEmp1(linkState)+"ADDR-"+senserAddr+NomalDataSwitch.getEmp1("ADDR-"+senserAddr)+"CAN-"+canCode+NomalDataSwitch.getEmp1("CAN-"+canCode)+listenValue+"\n");
			    if(SenserInfo.SENSERSTATE_DISCONNECTED.equals(linkState)) {
				    Main.logRecoder.saveLog(LogFileName.Log_VoiceGateway+gatewayIp+"\\ErrLog"+"\\"+Main.datetool.getTimeH()+"错误日志.log",Main.datetool.getTimeHMS()+
						    SenserName+NomalDataSwitch.getEmp1(SenserName)+linkState+NomalDataSwitch.getEmp1(linkState)+"ADDR-"+senserAddr+NomalDataSwitch.getEmp1("ADDR-"+senserAddr)+"CAN-"+canCode+NomalDataSwitch.getEmp1("CAN-"+canCode)+listenValue+"\n");
			    } 
			    
		    }
	    }
}
