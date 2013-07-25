package com.pwz.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ElecReading implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int readingId;
	private int elecAcctId;
	private String username;
	private int dayReading;
	private int nightReading;
	private int total;
	private Date readDate;
	
	public int getReadingId() {
		return readingId;
	}
	
	public void setReadingId(int readingId) {
		this.readingId = readingId;
	}
	
	public int getElecAcctId() {
		return elecAcctId;
	}
	
	public void setElecAcctId(int elecAcctId) {
		this.elecAcctId = elecAcctId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public int getDayReading() {
		return dayReading;
	}
	
	public void setDayReading(int dayReading) {
		this.dayReading = dayReading;
		total += dayReading;
	}
	
	public int getNightReading() {
		return nightReading;
	}
	
	public void setNightReading(int nightReading) {
		this.nightReading = nightReading;
		total += nightReading;
	}
	
	public int getTotalReading() {
		return total;
	}
	
	public Date getReadDate() {
		return readDate;
	}
	
	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

}
