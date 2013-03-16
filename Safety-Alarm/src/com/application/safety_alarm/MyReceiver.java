package com.application.safety_alarm;

import java.util.List;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent intent) {

		long id = intent.getExtras().getLong("id");
		Log.i("receiver", "Alarm received with id:  " + Long.toString(id));
		
		AppointmentsDataSource datasource;
        datasource = new AppointmentsDataSource(arg0);
		 datasource.open();
		 
		Appointment tmp=datasource.getAppointmentById(id);
		String ssid = tmp.getSSID();
		String number = tmp.getRecipientNumber();
		Boolean isCompleted = tmp.getIsCompleted(); 
		Boolean isGuardian = tmp.getIsGuardian();
		
		if(isGuardian){
			if(isCompleted){
				Log.i("receiver", "the dependent has come home");
				Toast.makeText(arg0, "the dependent is home", Toast.LENGTH_LONG).show();
			} 
			else{
				//alarm goes off
				Log.i("receiver", "the dependent is Not at home");	
				Toast.makeText(arg0, "the dependent did NOT come home", Toast.LENGTH_LONG).show();
				MediaPlayer alarm = MediaPlayer.create(arg0, R.raw.alarm);
				alarm.start();
			}	
		}
		else{
			
			checkIfSelectedWifiIsInRange(arg0,ssid,number);
		}
	}
	
	
	private void checkIfSelectedWifiIsInRange(Context context,String ssid,String number){
		Log.i("debug", "in checkIfSelectedWifiIsInRange");
	    
		WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
	   
		// If wifi is not turned on, we must turn it on 
		boolean connected = false;
	    WifiInfo info = wifiManager.getConnectionInfo();
	    
	    if(info.getBSSID() != null){
		    connected = true;
	    }else{ 
		   wifiManager.setWifiEnabled(true);
		   try {
		    	Thread.sleep(5000);
		    } catch (InterruptedException e) {
		    	// TODO Auto-generated catch block
		    	e.printStackTrace();
		    }
	    }

		// Get WiFi status and show in a toast (not helpful for the app)
		info = wifiManager.getConnectionInfo();
		
		// Compare a list of available wifi access-points to the chosen access-point (chosenAP)
		List<ScanResult> configs = wifiManager.getScanResults();
		boolean matchFound = false;
		for (ScanResult config : configs) {
			//if(config.SSID.equals(chosenAP)){
			if(config.SSID.equals(ssid)){
				Log.i("debug", "Match found for " + ssid);
				Toast.makeText(context, "Match found for " + ssid, Toast.LENGTH_LONG).show();
				SMSTransceiver smsTX = new SMSTransceiver(context.getApplicationContext());
				//smsTX.sendSMS("8054535634", "XYZPDDAFP");
				smsTX.sendSMS(number, "XYZPDDAFP");
				matchFound = true;
				break;
			}
		}
		if (!matchFound){
			Log.i("debug", "No match found for " + ssid);
			Toast.makeText(context, "No match found for " + ssid, Toast.LENGTH_LONG).show();
		}
			
		// If wifi was off -> turn off wifi
		if(connected == false){	
			wifiManager.setWifiEnabled(false);
		}

	}

}