package com.application.safety_alarm;

import java.util.Iterator;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSDataReceiver extends BroadcastReceiver{
		private static String CODEWORD="";
		private Intent intent;
		private Bundle bundle; 
		private String recMsgString;            
		private String fromAddress;
		private SmsMessage recMsg;
		private byte[] data;
		private AppointmentsDataSource datasource;
	    public SMSDataReceiver(){
	    	intent=new Intent();
	    	bundle = intent.getExtras();
	    	recMsgString = "";
	    	fromAddress = "";
	    	data = null;
	    }
	    public void test(){
	    	
			    if (bundle != null){
						//---retrieve the SMS message received---
				       	Object[] pdus = (Object[]) bundle.get("pdus");
				        for (int i=0; i<pdus.length; i++){
					            this.recMsg = SmsMessage.createFromPdu((byte[])pdus[i]);
					            try {
					                	data = recMsg.getUserData();
					            } catch (Exception e){
					
					            }
					            if (data!=null){
						                for(int index=0; index<data.length; ++index)
						                {
						                		this.recMsgString += Character.toString((char)data[index]);
						                } 
					            }	
					            this.fromAddress = recMsg.getOriginatingAddress();
				        }
				}
		}
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
	        if (bundle != null){
					//---retrieve the SMS message received---
			       	Object[] pdus = (Object[]) bundle.get("pdus");
			        for (int i=0; i<pdus.length; i++){
				            this.recMsg = SmsMessage.createFromPdu((byte[])pdus[i]);
				            try {
				                	data = recMsg.getUserData();
				            } catch (Exception e){
				            	
				            }
				            if (data!=null){
					            	//found some data
					                for(int index=0; index<data.length; ++index)
					                {
					                		this.recMsgString += Character.toString((char)data[index]);
					                }
					                //retrieved message in recMsgString
				            }
				            this.fromAddress = recMsg.getOriginatingAddress();
				            if(this.recMsgString.equals(CODEWORD)){
				            		//valid codeword, assumed to be a genuine Safety-Alarm message
				            		//look up stuff in SQL database
					            	datasource = new AppointmentsDataSource(context);
					        		datasource.open();
					        		List<Appointment> values = datasource.getAllAppointments();
					        		Iterator<Appointment> iterator = values.iterator();
					        		while(iterator.hasNext()){
					        				if(this.fromAddress.equals(iterator.next().getRecipient())){
					        						//found a match between an element in the database and the message
					        						
					        				}
					        		}
				            }
				            //has message in recMsgString
				            //has sender in fromAddress
			        }
	        }
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
	                        
	                        //brings the application mainactivity to the front.
	                        //should not be necessary in final app
	                        Intent mainActivityIntent = new Intent(context, MainActivity.class);
	                        mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	                        context.startActivity(mainActivityIntent);
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