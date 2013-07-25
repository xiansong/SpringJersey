package com.pwz.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GasBill implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int billId;
	private int gasAcctId;
	private String username;
	private float bill;
	private Date issuedDate;
	
	public int getBillId() {
		return billId;
	}
	
	public void setBillId(int billId) {
		this.billId = billId;
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

	public float getBill() {
		return bill;
	}
	
	public void setBill(float bill) {
		this.bill = bill;
	}
	
	public Date getIssuedDate() {
		return issuedDate;
	}
	
	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}
	
}
