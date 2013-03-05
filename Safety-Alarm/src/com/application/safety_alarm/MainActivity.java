package com.application.safety_alarm;

//import java.util.List;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
//import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity {
	
	//private AppointmentsDataSource datasource;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		Bundle b = this.getIntent().getExtras();
//		if(b!=null){
//		    Appointment newApp = b.getParcelable("Appointment");
//		    Context context = getApplicationContext();
//		    CharSequence text = "Date: "+newApp.getDate()+"\nTime: "+newApp.getTime()+"\nSSID: "+newApp.getSSID()+"\nRecipient: "+newApp.getRecipient();
//		    int duration = Toast.LENGTH_LONG;
//
//		    Toast toast = Toast.makeText(context, text, duration);
//		    toast.show();
//		}
		//datasource = new AppointmentsDataSource(this);
		//datasource.open();
		//List<Appointment> values = datasource.getAllAppointments();
		//ArrayAdapter<Appointment> adapter = new ArrayAdapter<Appointment>(this,
		//		android.R.layout.simple_list_item_1, values);
		//setListAdapter(adapter);

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
