package com.application.safety_alarm;

//import java.util.Iterator;
//import java.util.List;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.telephony.SmsMessage;
//import android.widget.Toast;

public class SMSDataReceiver /*extends BroadcastReceiver*/{
//		private static String CODEWORD="";
//		private String recMsgString;            
		/*private String fromAddress;
		private SmsMessage[] recMsg;
		private byte[] data;
		private AppointmentsDataSource datasource;
	    @Override
	    public void onReceive(Context context, Intent intent)
	    {
	    	Toast.makeText(context, "trool", Toast.LENGTH_SHORT).show();
		    	CODEWORD=context.getResources().getText(R.string.MSG_CODEWORD).toString();
		        //---get the SMS message passed in---
		        Bundle bundle = intent.getExtras();
		        if (bundle != null){
		        		
						//---retrieve the SMS message received---
				       	Object[] pdus = (Object[]) bundle.get("pdus");
				       	recMsg = new SmsMessage[pdus.length];
				        for (int i=0; i<pdus.length; i++){
					            this.recMsg[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
					            abortBroadcast();
					            try {
					                	data = recMsg[i].getUserData();
					            } catch (Exception e){
					            		//unhandled exception
					            }
					            if (data!=null){
						            	//found some data
						                for(int index=0; index<data.length; ++index)
						                {
						                		this.recMsgString += Character.toString((char)data[index]);
						                }
						                //retrieved message in recMsgString
					            }
					            this.fromAddress = recMsg[i].getOriginatingAddress();
					            if(this.recMsgString.equals(CODEWORD)){
					            		//valid codeword, assumed to be a genuine Safety-Alarm message
					            		//look up stuff in SQL database
						            	
//						            	String str="";
//						            	str += "Coded SMS from " + fromAddress;
//				                        str += ", a friend :";
//				                        str += this.recMsgString;
//				                        str += "\n";
//				                        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
//						            	datasource = new AppointmentsDataSource(context);
	//					        		datasource.open();
	//					        		List<Appointment> values = datasource.getAllAppointments();
	//					        		Iterator<Appointment> iterator = values.iterator();
	//					        		while(iterator.hasNext()){
	//					        				if(this.fromAddress.equals(iterator.next().getRecipient())){
	//					        						//found a match between an element in the database and the message						        						
	//					        				}
//						        		}//done iterating appointments
					            }
				        }
		        }
	    }*/	
}