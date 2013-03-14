package com.application.safety_alarm;

import java.io.Serializable;

public class Appointment implements Serializable{
	public Appointment(){
		date="No date chosen";
		time="No time chosen";
		SSID="No SSID chosen";
		recipient="No recipient chosen";
		isGuardian=true;
		id=0;
		
	}
	private static final long serialVersionUID = 0L;
	private long id;
	private String date;
	private String time;
	private String SSID;
	private String recipient;
	private Boolean isGuardian; 
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
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
	public Boolean getIsGuardian() {
		return isGuardian;
	}
	public void setIsGuardian(Boolean isGuardian) {
		this.isGuardian = isGuardian;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String toString(){
		String temp;
		if(this.getIsGuardian()){
			temp=this.getRecipient() +"\nLocation: "+this.getSSID() +"\nBefore: "+this.getDate()+", "+this.getTime(); 
		}else{
			temp="You are expected at "+this.getSSID() +" before "+this.getDate()+", "+this.getTime();
		}
		return temp;
	}
}