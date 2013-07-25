package com.pwz.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ElecBill implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int billId;
	private int elecAcctId;
	private String username;
	private float dayBill;
	private float nightBill;
	private float total;
	private Date issuedDate;
	
	public int getBillId() {
		return billId;
	}
	
	public void setBillId(int billId) {
		this.billId = billId;
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
	
	public float getDayBill() {
		return dayBill;
	}
	
	public void setDayBill(float dayBill) {
		this.dayBill = dayBill;
		total += dayBill;
	}
	
	public float getNightBill() {
		return nightBill;
	}
	
	public void setNightBill(float nightBill) {
		this.nightBill = nightBill;
		total += nightBill;
	}
	
	public float getTotalBill() {
		return total;
	}

	public Date getIssuedDate() {
		return issuedDate;
	}
	
	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

}
