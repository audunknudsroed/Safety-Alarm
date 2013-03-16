package com.application.safety_alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent intent) {

		long id = intent.getExtras().getLong("id");
		Log.i("receiver", "Alarm received with id:  " + Long.toString(id));
		
/*		if(guardian){
			if(completed){
				Log.i("receiver", "the dependent has come home");
				Toast.makeText(arg0, "the dependent is home", Toast.LENGTH_LONG).show();
			} 
			else{
				//alarm goes off
				Log.i("receiver", "the dependent is Not at home");	
				Toast.makeText(arg0, "the dependent did NOT come home", Toast.LENGTH_LONG).show();
			}
			
		}
		else{
			checkIfSelectedWifiIsInRange(arg0);
			
		}*/
		
		//Log.i("debug", ((NewAppointmentActivity) getActivity()).getNewApp().getSSID());

//		if(newApp.getIsGuardian()){
//			Log.i("debug", "Alarm received GUARDIAN");
//			//if no notice from dependent -> start alarm
//		}
//		else{
//			Log.i("debug", "Alarm received DEPENDENT");
//			checkIfSelectedWifiIsInRange(arg0);
//		}
	}

}