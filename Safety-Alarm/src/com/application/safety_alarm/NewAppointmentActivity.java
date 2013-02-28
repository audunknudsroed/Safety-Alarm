package com.application.safety_alarm;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class NewAppointmentActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_appointment);
		Appointment newApp = new Appointment();
		TextView dateView = (TextView) findViewById(R.id.dateView);
		TextView timeView = (TextView) findViewById(R.id.timeView);
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
}