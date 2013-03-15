package com.application.safety_alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

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
        String sender="15555215554";
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
                	//this is a coded message
                    originAddress=msgs[i].getOriginatingAddress();
                    if(originAddress.matches(sender)/*look for relevant potential matches*/){
                    	abortBroadcast();
                    	//originating address is a match
                    	//make a table lookup
                    	
                    	//displays the received text for test purpose
                    	str += "Coded SMS from " + msgs[i].getOriginatingAddress();
                        str += ", a friend :";
                        str += msgs[i].getMessageBody().toString();
                        str += "\n";
                        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
                    }else{
                    	//received a CODEWORD message, but not from a relevant source,
                    	//may be a coincidence or a lost appointment
                    }
                }else{
                	//received message was a normal message so broadcast it
                	//---display the new SMS message---
                	Intent broadcastIntent = new Intent();
                    broadcastIntent.setAction("SMS_RECEIVED_ACTION");
                    broadcastIntent.putExtra("sms",str);
                    context.sendBroadcast(broadcastIntent);
                }
            }	
        }
    }
}
