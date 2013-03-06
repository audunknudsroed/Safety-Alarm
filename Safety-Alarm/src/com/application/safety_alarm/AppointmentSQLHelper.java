package com.application.safety_alarm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AppointmentSQLHelper extends SQLiteOpenHelper {

  public static final String TABLE_APPOINTMENTS = "appointments";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_DATE = "date";
  public static final String COLUMN_TIME = "time";
  public static final String COLUMN_SSID = "ssid";
  public static final String COLUMN_RECIPIENT = "recipient";
  public static final String COLUMN_ISGUARDIAN = "isguardian";
  
 
  private static final String DATABASE_NAME = "appointments.db";
  private static final int DATABASE_VERSION = 1;

  // Database creation sql statement
  private static final String DATABASE_CREATE = "create table "
      + TABLE_APPOINTMENTS
      + "("
      + COLUMN_ID + " integer primary key autoincrement, "
      + COLUMN_DATE + " text not null, "
      + COLUMN_TIME + " text not null, "
      + COLUMN_SSID + " text not null, "
      + COLUMN_RECIPIENT + " text not null, "
      + COLUMN_ISGUARDIAN + " integer"
       
      +");";
  
  //create table appointments(_id#, appointment text not null);
  public AppointmentSQLHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }
  
  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(AppointmentSQLHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPOINTMENTS);
    onCreate(db);
  }

} 