package com.application.safety_alarm;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	private AppointmentsDataSource datasource;
	private List<Appointment> values;
	private ArrayAdapter<Appointment> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		datasource = new AppointmentsDataSource(this);
		datasource.open();
		//displayAppointments();
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
	public void onClick_delete_all(View v){
		datasource = new AppointmentsDataSource(this);
		datasource.open();
		datasource.deleteAllAppointments();
		displayAppointments();

	}
	public void onClick_send_sms(View v){

        Toast.makeText(getApplicationContext(), "Sent message", Toast.LENGTH_SHORT).show();
		SMSTransceiver smsTx=new SMSTransceiver(getApplicationContext());
		smsTx.sendSMS("+1 805-453-5634", "XYZPDDAFP");
	}
	private void displayAppointments(){
		values = datasource.getAllAppointments();
		adapter =new ArrayAdapter<Appointment>(this,
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}
}