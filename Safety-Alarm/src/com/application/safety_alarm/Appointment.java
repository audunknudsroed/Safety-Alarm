package com.application.safety_alarm;

import java.io.Serializable;

public class Appointment implements Serializable{
	private static final long serialVersionUID = 0L;
	private long id;
	private String date;
	private String time;
	private String SSID;
	private String recipientName;
	private String recipientNumber;
	private Boolean isGuardian; 
	private Boolean isCompleted;
	public Appointment(){
		date="No date chosen";
		time="No time chosen";
		SSID="No SSID chosen";
		recipientName="No contact chosen";
		recipientNumber="No number selected";
		isGuardian=true;
		id=0;
		isCompleted=false;
	}

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
	public String getRecipientName() {
		return recipientName;
	}
	public void setRecipientName(String recipient) {
		this.recipientName = recipient;
	}
	public String getRecipientNumber() {
		return recipientNumber;
	}
	public void setRecipientNumber(String recipient) {
		this.recipientNumber = recipient;
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
	public Boolean getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(Boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public String toString(){
		String temp="";
		if(this.getIsCompleted()){
			if(this.getIsGuardian()){
				temp=this.getRecipientNumber() +" has arrived at "+this.getSSID(); 
			}else{
				temp="You have arrived at "+this.getSSID();
			}
		}else{
			if(this.getIsGuardian()){
				temp=this.getRecipientNumber() +" should be at "+this.getSSID() +" before "+this.getDate()+", "+this.getTime(); 
			}else{
				temp="You are expected at "+this.getSSID() +" before "+this.getDate()+", "+this.getTime();
			}
		}
		return temp;
	}
}