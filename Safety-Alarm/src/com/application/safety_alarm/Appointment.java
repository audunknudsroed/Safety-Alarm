package com.application.safety_alarm;

public class Appointment {
	public Appointment(){
		date="No date chosen";
		time="No time chosen";
		SSID="No SSID chosen";
		recipient="No recipient chosen";
	}
	private String date;
	private String time;
	private String SSID;
	private String recipient;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getSSID() {
		return SSID;
	}
	public void setSSID(String sSID) {
		SSID = sSID;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	
	
	
}
