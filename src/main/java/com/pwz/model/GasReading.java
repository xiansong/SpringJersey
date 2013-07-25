package com.pwz.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GasReading implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int readingId;
	private int gasAcctId;
	private String username;
	private int reading;
	private Date readDate;
	
	public int getReadingId() {
		return readingId;
	}
	
	public void setReadingId(int readingId) {
		this.readingId = readingId;
	}
	
	public int getGasAcctId() {
		return gasAcctId;
	}
	
	public void setGasAcctId(int gasAcctId) {
		this.gasAcctId = gasAcctId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public int getReading() {
		return reading;
	}
	
	public void setReading(int reading) {
		this.reading = reading;
	}
	
	public Date getReadDate() {
		return readDate;
	}
	
	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

}
