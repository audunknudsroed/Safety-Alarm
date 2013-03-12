package com.application.safety_alarm;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.widget.Toast;

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
		String SENT = "SMS_SENT";
		String DELIVERED = "SMS_DELIVERED";
		PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, new Intent(SENT), 0);
		PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 0, new Intent(DELIVERED), 0);
		//---when SMS has been sent---
		context.registerReceiver(new BroadcastReceiver(){
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode())
				{
				case Activity.RESULT_OK:
					Toast.makeText(context, "SMS sent",Toast.LENGTH_SHORT).show();
					break;
				 case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                     Toast.makeText(context, "Generic failure",
                             Toast.LENGTH_SHORT).show();
                     break;
                 case SmsManager.RESULT_ERROR_NO_SERVICE:
                     Toast.makeText(context, "No service",
                             Toast.LENGTH_SHORT).show();
                     break;
                 case SmsManager.RESULT_ERROR_NULL_PDU:
                     Toast.makeText(context, "Null PDU",
                             Toast.LENGTH_SHORT).show();
                     break;
                 case SmsManager.RESULT_ERROR_RADIO_OFF:
                     Toast.makeText(context, "Radio off",
                             Toast.LENGTH_SHORT).show();
                     break;
				}
			}
		}, new IntentFilter(SENT));
		
	//---when the SMS has been delivered---
		context.registerReceiver(new BroadcastReceiver(){
    	@Override
    	public void onReceive(Context arg0, Intent arg1) {
        	switch (getResultCode())
            {
            	case Activity.RESULT_OK:
                     Toast.makeText(context, "SMS delivered",
                             Toast.LENGTH_SHORT).show();
                     break;
                 case Activity.RESULT_CANCELED:
                     Toast.makeText(context, "SMS not delivered",
                             Toast.LENGTH_SHORT).show();
                     break;
            }
    	}
	}, new IntentFilter(DELIVERED));
	SmsManager sms=SmsManager.getDefault();
	sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
	}

}
