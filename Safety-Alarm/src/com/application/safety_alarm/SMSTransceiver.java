package com.application.safety_alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;

public class SMSTransceiver extends BroadcastReceiver{
	private IntentFilter intentFilter;
	private Context context;
	BroadcastReceiver intentReceiver;
	public SMSTransceiver(Context context) {
		intentFilter = new IntentFilter();
		intentFilter.addAction("SMS_RECEIVED_ACTION");
		this.context=context;
		this.context.registerReceiver(intentReceiver, intentFilter);
	}
	public void onReceive(Context context, Intent intent) {
		//TextView SMSes = (TextView) findViewById(R.id.textView1);
		//SMSes.setText(intent.getExtras().getString("sms"));
	}
	public void unregisterReceiver(){
		this.context.unregisterReceiver(intentReceiver);
	}
	public void registerReceiver(){
		this.context.registerReceiver(intentReceiver, intentFilter);
	}

	public void sendSMS(String phoneNumber, String message){
		SmsManager sms=SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, null, null);
	}

}
