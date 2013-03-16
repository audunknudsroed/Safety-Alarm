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
        //String sender="+18056376562";
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
           		 
           		 	int appointmentsCompleted=0;
           		 	appointmentsCompleted=datasource.completeAppointmentsbyRecipientNumber(originAddress);
           		 	if(appointmentsCompleted>0){
                    	str += "Coded SMS from a registered number: " + msgs[i].getOriginatingAddress();
                        str += ", which completed ";
                        str += Integer.toString(appointmentsCompleted);
                        str += " appointments\n";
                        Toast.makeText(context, str, Toast.LENGTH_LONG).show();	
           		 	}else{
                    	str += "Coded SMS from an unexpected number: " + msgs[i].getOriginatingAddress();
                    	str +="alt: "+originAddress+" ";
                        str += ". Found "+ Integer.toString(datasource.getNoOfRows(msgs[i].getOriginatingAddress()));
                        str += " matches. ";
                        str += msgs[i].getMessageBody().toString();
                        str += "\n";
                        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
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