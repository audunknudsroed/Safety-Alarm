package com.application.safety_alarm;

import android.telephony.SmsManager;
import android.util.Log;

public class SMSDataTransceiver {
	/*using this class:
	 *create an object of this class, like
	 *SMSDataTransceiver smsTx=new SMSDataTransceiver();
	 *then use 
	 *		smsTx.sendMsg(String receiver);
	 *to send a data sms to the receiver telephone number 
	 */
	private SmsManager smsManager;
	private String messageText;
	private short SMS_PORT;
	public SMSDataTransceiver(){
		smsManager = SmsManager.getDefault();
		messageText = "XYZPDDAFP";
		SMS_PORT= 5404; //you can use a different port if you'd like. I believe it just has to be an int value.
	}
	public void sendMsg(String receiver){
		try{
			smsManager.sendDataMessage(receiver, null, SMS_PORT, messageText.getBytes(), null, null);
		}catch (Exception e){
			Log.e("SMS_FAILURE", e.getMessage(), e);
		}
	}
}
