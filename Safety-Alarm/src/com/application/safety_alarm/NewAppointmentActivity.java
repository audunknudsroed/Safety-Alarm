package com.application.safety_alarm;



import java.util.Calendar;
import java.util.List;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class NewAppointmentActivity extends FragmentActivity{
	
	TextView chosenWifi;
	TimePickerDialog timePickerDialog;
	DatePickerDialog datePickerDialog;
	private int id;
	Toast mToast;
	Calendar calSet;
	
	private AppointmentsDataSource datasource;
	private Appointment newApp;
	private TextView dateView;
	private TextView timeView;
	private TextView chosenContact;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_appointment);
			
		newApp = new Appointment();
		datasource = new AppointmentsDataSource(this);
		datasource.open();
		datasource.createAppointment(newApp);
		id=(int) newApp.getId();
	
		// Register the receiver
        registerReceiver(alarmReceiver,new IntentFilter(Integer.toString(id)));
		
        // Set up buttons etc
		Button changeStateButton = (Button)findViewById(R.id.change_state);
		changeStateButton.setText("Guardian");
		dateView = (TextView) findViewById(R.id.dateView);
		timeView = (TextView) findViewById(R.id.timeView);
		chosenWifi = (TextView)findViewById(R.id.wifiView);
		chosenContact = (TextView)findViewById(R.id.contactView);
		timeView.setText(newApp.getTime());
		dateView.setText(newApp.getDate());
		chosenWifi.setText("No access point chosen");
		chosenContact.setText("No contact chosen");
	}
	
	private BroadcastReceiver alarmReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			
			if(newApp.getIsGuardian()){
				Log.i("debug", "Alarm received GUARDIAN");
				//if no notice from dependent -> start alarm
			}
			else{
				Log.i("debug", "Alarm received DEPENDENT");
				checkIfSelectedWifiIsInRange(arg0);
			}
		}
	};
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		// For the return of chosen wifi
		if (requestCode == 1){
			if (resultCode == RESULT_OK) {
		        String string = data.getStringExtra("RESULT_STRING");
		        chosenWifi.setText(string);
		        newApp.setSSID(string);
		        Log.i("debug", ".onActivityResult -> " + newApp.getSSID());  
		    }	
		}

		// On the return of chosen contact
        if (requestCode == 2){	
			if (data != null) {
				Uri contactData = data.getData();
				Cursor c = null;
					if (contactData != null) {
		            c =  getContentResolver().query(contactData, null, null, null, null);
		            Cursor pCur = null;
		            try{
			            if (c.moveToFirst()) {
			                  
			                String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
			                String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            
			                if (Integer.parseInt(c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
			                	try {
			          	            pCur = getContentResolver().query(Phone.CONTENT_URI,null,Phone.CONTACT_ID +" = " + id, null, null);
			          	            if(pCur.moveToFirst()){
			          		            String number = pCur.getString(pCur.getColumnIndex(Phone.NUMBER));
			          		            Log.i("debug3", "Contact: " + name + " ID: " + id + "num: " + number);
			          		            newApp.setRecipientName(name);	// This should maybe be number!!!
			          		            newApp.setRecipientNumber(number);
			          		            chosenContact.setText("Name: " + name);
			          	            }
			      	            } finally {
			      	                if (pCur != null) {
			      	                	pCur.close();
			      	                }
			      	            }
			                }
			            }
		            }finally {
		            	if (c!= null){
      	                	c.close();		            		
		            	}
		            }
				}
			}
        }
	}

	public void choseContact(View v){
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);
        startActivityForResult(intent, 2);	
	}

	public void setWifi(View v){
		Log.i("debug", "setWifi");
		Intent intent = new Intent(this, wifiList.class);
		startActivityForResult(intent, 1);	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_new_appointment, menu);
		return true;
	}
	

	//*************************** New timer****************************************
	public void openTimePickerDialog(View v){

		Calendar calendar = Calendar.getInstance();
		
		timePickerDialog = new TimePickerDialog(
				NewAppointmentActivity.this, 
				onTimeSetListener, 
				calendar.get(Calendar.HOUR_OF_DAY), 
				calendar.get(Calendar.MINUTE), 
				true);
		timePickerDialog.setTitle("Set Alarm Time");  
        
		timePickerDialog.show();
	}
    
    OnTimeSetListener onTimeSetListener = new OnTimeSetListener(){

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

			Calendar calNow = Calendar.getInstance();
			calSet = (Calendar) calNow.clone();

			calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
			calSet.set(Calendar.MINUTE, minute);
			calSet.set(Calendar.SECOND, 0);
			calSet.set(Calendar.MILLISECOND, 0);
			
			
			if(calSet.compareTo(calNow) <= 0){
				//Today Set time passed, count to tomorrow
				calSet.add(Calendar.DATE, 1);
			}
			
			
			Log.i("debug","hour: " + String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
			
			newApp.setTime(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
			timeView.setText("Time: " + String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
			openDatePickerDialog(calSet);
		}};

		public void openDatePickerDialog(Calendar calendar){

			
			datePickerDialog = new DatePickerDialog(
					NewAppointmentActivity.this, 
					onDateSetListener, 
					calendar.get(Calendar.YEAR), 
					calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH));
			datePickerDialog.setTitle("Set The Date yo!");  
	        
			datePickerDialog.show();
		}
		
		OnDateSetListener onDateSetListener = new OnDateSetListener(){

			@Override
			public void onDateSet(DatePicker arg0, int year, int month, int day) {
				// TODO Auto-generated method stub
				calSet.set(year, month, day);
				newApp.setDate(String.valueOf(month+1) + "." + String.valueOf(day)+ "."+ String.valueOf(year));
				dateView.setText("Date: " + String.valueOf(month+1) + "." + String.valueOf(day)+ "."+ String.valueOf(year));
				setAlarm(calSet);
			}
		};
		
	private void setAlarm(Calendar targetCal){

		Intent intent = new Intent(Integer.toString(id));	// Set filter to this id
		PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), id, intent, 0);
		
		// Schedule the alarm!
        AlarmManager am = ((AlarmManager)getSystemService(Context.ALARM_SERVICE));
		
		// For debugging if we want the thing to start after 1 seconds ----------------------
		Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 10);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        
        // Else comment the above and uncomment the line below-------------------------
        //am.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
        //am.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
    }
	
	//*********************** end new timer ****************************************
	
	public void confirmAppointment(View v){

		/*Open SQL management
		 * 
		 * Add object to database
		 * Fix database to contain all datas
		 * Appointment needs tostring function
		 * 
		 */

		 datasource.updateAppointment(newApp.getId(), newApp);
		 Intent intent = new Intent(NewAppointmentActivity.this, MainActivity.class);		
		 startActivity(intent);
	}

	public void changeState(View v){
		Button changeStateButton = (Button)findViewById(R.id.change_state);
		if(newApp.getIsGuardian()){
			changeStateButton.setText("Dependent");
			newApp.setIsGuardian(false);
		}else{
			changeStateButton.setText("Guardian");
			newApp.setIsGuardian(true);
		}
	}
	
	public Appointment getNewApp() {
		return newApp;
	}
	public void setNewApp(Appointment newApp) {
		this.newApp = newApp;
	}
	

	public void checkIfSelectedWifiIsInRange(Context context){
		
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
			if(config.SSID.equals(newApp.getSSID())){
				Log.i("debug", "Match found for " + newApp.getSSID());
				Toast.makeText(context, "Match found for " + newApp.getSSID(), Toast.LENGTH_LONG).show();
				SMSTransceiver smsTX = new SMSTransceiver(this);
				smsTX.sendSMS("8054535634", "XYZPDDAFP");
				//smsTX.sendSMS(newApp.getRecipientNumber(), "XYZPDDAFP");
				matchFound = true;
				break;
			}
		}
		if (!matchFound){
			Log.i("debug", "No match found for " + newApp.getSSID());
			Toast.makeText(context, "No match found for " + newApp.getSSID(), Toast.LENGTH_LONG).show();
		}
			
		// If wifi was off -> turn off wifi
		if(connected == false){	
			wifiManager.setWifiEnabled(false);
		}
		
		finish();
	}
	
}