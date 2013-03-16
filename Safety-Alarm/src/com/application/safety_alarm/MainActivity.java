package com.application.safety_alarm;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	private AppointmentsDataSource datasource;
	private List<Appointment> values;
	private ArrayAdapter<Appointment> adapter;
	AlertDialog alertDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		datasource = new AppointmentsDataSource(this);
		datasource.setMainActivityHandler(this);
		displayAppointments();
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
		datasource.close();
		displayAppointments();
	}
	public void onClick_send_sms(View v){

        Toast.makeText(getApplicationContext(), "Sent message", Toast.LENGTH_SHORT).show();
		SMSTransceiver smsTx=new SMSTransceiver(getApplicationContext());
		String CODEWORD=getApplicationContext().getResources().getString(R.string.MSG_CODEWORD);
		smsTx.sendSMS("+18054535634", CODEWORD);
	}
	public void displayAppointments(){
		datasource.open();
		values = datasource.getAllAppointments();
		adapter =new ArrayAdapter<Appointment>(this,
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
		datasource.close();
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
	  alertDialog = new AlertDialog.Builder(this).create();
	  final Appointment app=(Appointment)getListView().getItemAtPosition(position);
	  alertDialog.setTitle("Delete Appointment?");
	  alertDialog.setMessage("Appointment with: "+app.getRecipientName());
	  alertDialog.setButton((0xffffffff), "OK", new DialogInterface.OnClickListener(){
	    public void onClick(DialogInterface dialog, int which) {
	    	datasource = new AppointmentsDataSource(getApplicationContext());
			datasource.open();
			datasource.deleteAppointmentById(app.getId());
			datasource.close();
			displayAppointments();
			
			
	    return;
	  } });
	  alertDialog.setButton((0xfffffffe), "Cancel", new DialogInterface.OnClickListener(){
		    public void onClick(DialogInterface dialog, int which) {
		    return;
		  } });
	  alertDialog.show();
	  super.onListItemClick(l, v, position, id);
	}
}