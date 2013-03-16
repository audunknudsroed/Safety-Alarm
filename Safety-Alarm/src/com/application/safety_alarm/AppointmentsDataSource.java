package com.application.safety_alarm;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class AppointmentsDataSource {

  // Database fields
  private SQLiteDatabase database;
  private AppointmentSQLHelper dbHelper;
  private String[] allColumns = { AppointmentSQLHelper.COLUMN_ID,
		  AppointmentSQLHelper.COLUMN_DATE,
		  AppointmentSQLHelper.COLUMN_TIME,
		  AppointmentSQLHelper.COLUMN_SSID,
		  AppointmentSQLHelper.COLUMN_RECIPIENTNAME,
		  AppointmentSQLHelper.COLUMN_RECIPIENTNUMBER,
		  AppointmentSQLHelper.COLUMN_ISGUARDIAN,
		  AppointmentSQLHelper.COLUMN_ISCOMPLETED};
  
  public AppointmentsDataSource(Context context) {
    dbHelper = new AppointmentSQLHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public Appointment createAppointment(Appointment app) {
    ContentValues values = new ContentValues();
    values.put(AppointmentSQLHelper.COLUMN_DATE, app.getDate());
    values.put(AppointmentSQLHelper.COLUMN_TIME, app.getTime());
    values.put(AppointmentSQLHelper.COLUMN_SSID, app.getSSID());
    values.put(AppointmentSQLHelper.COLUMN_RECIPIENTNAME, app.getRecipientName());
    values.put(AppointmentSQLHelper.COLUMN_RECIPIENTNUMBER, app.getRecipientNumber());
    values.put(AppointmentSQLHelper.COLUMN_ISGUARDIAN, app.getIsGuardian());
    values.put(AppointmentSQLHelper.COLUMN_ISCOMPLETED, app.getIsCompleted());
    long insertId = database.insert(AppointmentSQLHelper.TABLE_APPOINTMENTS, null,
        values);
    Cursor cursor = database.query(AppointmentSQLHelper.TABLE_APPOINTMENTS,
        allColumns, AppointmentSQLHelper.COLUMN_ID + " = " + insertId, null,
        null, null, null);
    cursor.moveToFirst();
    Appointment newAppointment = cursorToAppointment(cursor);
    cursor.close();
    app.setId(newAppointment.getId());
    return newAppointment;
	}

  public Appointment updateAppointment(long id, Appointment app){
	  ContentValues values = new ContentValues();
	    values.put(AppointmentSQLHelper.COLUMN_DATE, app.getDate());
	    values.put(AppointmentSQLHelper.COLUMN_TIME, app.getTime());
	    values.put(AppointmentSQLHelper.COLUMN_SSID, app.getSSID());
	    values.put(AppointmentSQLHelper.COLUMN_RECIPIENTNAME, app.getRecipientName());
	    values.put(AppointmentSQLHelper.COLUMN_RECIPIENTNUMBER, app.getRecipientNumber());
	    values.put(AppointmentSQLHelper.COLUMN_ISGUARDIAN, app.getIsGuardian());
	    values.put(AppointmentSQLHelper.COLUMN_ISCOMPLETED, app.getIsCompleted());
	    database.update(AppointmentSQLHelper.TABLE_APPOINTMENTS, values, AppointmentSQLHelper.COLUMN_ID + " = " + id, null);
	    return app;
  }
  public void deleteAppointment(Appointment appointment) {
    long id = appointment.getId();
    System.out.println("Appointment deleted with id: " + id);
    database.delete(AppointmentSQLHelper.TABLE_APPOINTMENTS, AppointmentSQLHelper.COLUMN_ID
        + " = " + id, null);
  }
  public void deleteAppointmentById(long id){
	  database.delete(AppointmentSQLHelper.TABLE_APPOINTMENTS, AppointmentSQLHelper.COLUMN_ID
		        + " = " + id, null);
  }
  public void deleteAllAppointments(){
	  Cursor cursor = database.query(AppointmentSQLHelper.TABLE_APPOINTMENTS,
		        allColumns, null, null, null, null, null);
	  cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
		    	Appointment appointment = cursorToAppointment(cursor);
		    	deleteAppointment(appointment);
		    	cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
  }
  
  public Appointment getAppointmentById(long id)
  {
	  Cursor cursor = database.query(AppointmentSQLHelper.TABLE_APPOINTMENTS,
		        allColumns, AppointmentSQLHelper.COLUMN_ID + " = " + id, null, null, null, null);
	  cursor.moveToFirst();
	  Appointment tmp=cursorToAppointment(cursor);
	  cursor.close();
	  return tmp;
  }
  public List<Appointment> getAllAppointments() {
    List<Appointment> appointments = new ArrayList<Appointment>();

    Cursor cursor = database.query(AppointmentSQLHelper.TABLE_APPOINTMENTS,
        allColumns, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
    	Appointment appointment = cursorToAppointment(cursor);
    	appointments.add(appointment);
    	cursor.moveToNext();
    }
    // Make sure to close the cursor
    cursor.close();
    return appointments;
  }

  public void completeAppointmentbyId(long id){
	  ContentValues values = new ContentValues();
	  values.put(AppointmentSQLHelper.COLUMN_ISCOMPLETED, 1);
	  database.update(AppointmentSQLHelper.TABLE_APPOINTMENTS, values, AppointmentSQLHelper.COLUMN_ID + " = " + id, null);
  }
  public int completeAppointmentsbyRecipientNumber(String number){
	  ContentValues values = new ContentValues();
	  values.put(AppointmentSQLHelper.COLUMN_ISCOMPLETED, 1);
	  int rowsAffected=database.update(AppointmentSQLHelper.TABLE_APPOINTMENTS, values, AppointmentSQLHelper.COLUMN_RECIPIENTNUMBER + "='" + number+"'", null);
	  return rowsAffected;
  }
//  private 
  public boolean hasRecipient(String recipient){
	  return true;
  }
  private Appointment cursorToAppointment(Cursor cursor) {
    Appointment appointment = new Appointment();
    appointment.setId(cursor.getLong(0));
    appointment.setDate(cursor.getString(1));
    appointment.setTime(cursor.getString(2));
    appointment.setSSID(cursor.getString(3));
    appointment.setRecipientName(cursor.getString(4));
    appointment.setRecipientNumber(cursor.getString(5));
    if(cursor.getLong(6)==0){
    	appointment.setIsGuardian(false);
    }else{
    	appointment.setIsGuardian(true);
    }
    if(cursor.getLong(7)==0){
    	appointment.setIsCompleted(false);
    }else{
    	appointment.setIsCompleted(true);
    }
    return appointment;
  }
	public int getNoOfRows(String number){
		Cursor cursor = database.query(AppointmentSQLHelper.TABLE_APPOINTMENTS,
   		        allColumns, AppointmentSQLHelper.COLUMN_RECIPIENTNUMBER + " = " + number, null, null, null, null);
		int length=cursor.getCount();
		cursor.close();
		return length;
	}

}