package com.application.safety_alarm;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

public class DatePickerFragment extends DialogFragment
implements DatePickerDialog.OnDateSetListener {
	
	public DatePickerFragment(){
		
	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}
	
	public void onDateSet(DatePicker view, int year, int month, int day) {
		((NewAppointmentActivity) getActivity()).getNewApp().setDate(String.valueOf(month + 1 ) + "/" +   String.valueOf(day) + "/" + String.valueOf(year));
		TextView textView = (TextView) getActivity().findViewById(R.id.dateView);
		textView.setText(((NewAppointmentActivity) getActivity()).getNewApp().getDate());
		
	}
}