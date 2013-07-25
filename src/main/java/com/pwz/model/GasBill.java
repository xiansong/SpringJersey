package com.pwz.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GasBill implements Serializable {

	private static final long serialVersionUID = 1L;

	private int billId;
	private int gasAcctId;
	private String username;
	private float bill;
	private Date issuedDate;

	public int getBillId() {
		return billId;
	}

	public void setBillId(final int billId) {
		this.billId = billId;
	}

	public int getGasAcctId() {
		return gasAcctId;
	}

	public void setGasAcctId(final int gasAcctId) {
		this.gasAcctId = gasAcctId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public float getBill() {
		return bill;
	}

	public void setBill(final float bill) {
		this.bill = bill;
	}

	public Date getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(final Date issuedDate) {
		this.issuedDate = issuedDate;
	}

}
