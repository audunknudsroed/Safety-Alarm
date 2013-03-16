package com.application.safety_alarm;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
//import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver
{
	private static String CODEWORD="";
    @Override
    public void onReceive(Context context, Intent intent)
    {
    	CODEWORD=context.getResources().getText(R.string.MSG_CODEWORD).toString();
        //---get the SMS message passed in---

        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String str = "";
        String originAddress="";
        String msgContent="";
        if (bundle != null)
        {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i=0; i<msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                msgContent=msgs[i].getMessageBody().toString();
                if(msgContent.startsWith(CODEWORD)){
                	abortBroadcast();
                	//this is a coded message
                    originAddress=msgs[i].getOriginatingAddress();
                    AppointmentsDataSource datasource;
                    datasource = new AppointmentsDataSource(context);
           		 	datasource.open();
           		 	datasource.completeAppointmentsbyRecipientNumber(originAddress);
           		 	
           		 	
                }else{
                	//received message was a normal message so broadcast it
                	Intent broadcastIntent = new Intent();
                    broadcastIntent.setAction("SMS_RECEIVED_ACTION");
                    broadcastIntent.putExtra("sms",str);
                    context.sendBroadcast(broadcastIntent);
                }
            }	
        }
    }
}