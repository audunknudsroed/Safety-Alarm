package com.application.safety_alarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewAppointmentActivity extends FragmentActivity{
	private Appointment newApp;

	private TextView dateView;
	private TextView timeView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_appointment);
		newApp = new Appointment();
		Button changeStateButton = (Button)findViewById(R.id.change_state);
		changeStateButton.setText("Guardian");
		dateView = (TextView) findViewById(R.id.dateView);
		timeView = (TextView) findViewById(R.id.timeView);
		timeView.setText(newApp.getTime());
		dateView.setText(newApp.getDate());
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_new_appointment, menu);
		return true;
	}
	public void setTime(View v) {
	    DialogFragment newFragment = new TimePickerFragment();
	    newFragment.show(getSupportFragmentManager(), "timePicker");
	}
	public void setDate(View v) {
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getSupportFragmentManager(), "datePicker");
	}
	public void confirmAppointment(View v){
		newApp.setSSID(String.valueOf(((EditText) findViewById(R.id.home_SSID)).getText()));
		newApp.setRecipient(String.valueOf(((EditText) findViewById(R.id.recipient)).getText()));
		/*Open SQL management
		 * 
		 * Add object to database
		 * Fix database to contain all datas
		 * Appointment needs tostring function
		 * 
		 */
		
		Intent intent = new Intent(this, MainActivity.class);		
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
}