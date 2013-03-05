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
		  AppointmentSQLHelper.COLUMN_APPOINTMENT };

  public AppointmentsDataSource(Context context) {
    dbHelper = new AppointmentSQLHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public Appointment createAppointment(String appointment) {
    ContentValues values = new ContentValues();
    values.put(AppointmentSQLHelper.COLUMN_APPOINTMENT, appointment);
    long insertId = database.insert(AppointmentSQLHelper.TABLE_APPOINTMENTS, null,
        values);
    Cursor cursor = database.query(AppointmentSQLHelper.TABLE_APPOINTMENTS,
        allColumns, AppointmentSQLHelper.COLUMN_ID + " = " + insertId, null,
        null, null, null);
    cursor.moveToFirst();
    Appointment newAppointment = cursorToAppointment(cursor);
    cursor.close();
    return newAppointment;
  }

  public void deleteAppointment(Appointment appointment) {
    long id = appointment.getId();
    System.out.println("Appointment deleted with id: " + id);
    database.delete(AppointmentSQLHelper.TABLE_APPOINTMENTS, AppointmentSQLHelper.COLUMN_ID
        + " = " + id, null);
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

  private Appointment cursorToAppointment(Cursor cursor) {
    Appointment appointment = new Appointment();
    appointment.setId(cursor.getLong(0));
    appointment.setSSID(cursor.getString(1));
    return appointment;
  }
} 