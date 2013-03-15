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
  public static final String COLUMN_RECIPIENTNAME = "recipientname";
  public static final String COLUMN_RECIPIENTNUMBER = "recipientnumber";
  public static final String COLUMN_ISGUARDIAN = "isguardian";
  public static final String COLUMN_ISCOMPLETED = "iscompleted";
  
 
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
      + COLUMN_RECIPIENTNAME + " text not null, "
      + COLUMN_RECIPIENTNUMBER + " text not null, "
      + COLUMN_ISGUARDIAN + " integer, "
      + COLUMN_ISCOMPLETED + " integer"
       
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