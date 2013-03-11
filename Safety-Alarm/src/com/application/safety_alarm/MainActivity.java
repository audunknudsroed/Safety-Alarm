package com.application.safety_alarm;

//import java.util.List;


import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
//import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity {
	private AppointmentsDataSource datasource;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		datasource = new AppointmentsDataSource(this);
		datasource.open();
		List<Appointment> values = datasource.getAllAppointments();
		ArrayAdapter<Appointment> adapter = new ArrayAdapter<Appointment>(this,
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
		
		//populate list
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	public void onClick_new_appointment(View view){
		 Intent intent = new Intent(this, NewAppointmentActivity.class);
		 startActivity(intent);
	}
	
}
