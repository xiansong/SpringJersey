package com.pwz.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ElecAccount implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int elecAcctId;
	private int userId;
	private String username;
	private String acctNumber;
	private String companyName;
	private long MPRN;
	
	public int getElecAcctId() {
		return elecAcctId;
	}
	
	public void setElecAcctId(int elecAcctId) {
		this.elecAcctId = elecAcctId;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getAcctNumber() {
		return acctNumber;
	}
	
	public void setAcctNumber(String acctNumber) {
		this.acctNumber = acctNumber;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public long getMPRN() {
		return MPRN;
	}
	
	public void setMPRN(long MPRN) {
		this.MPRN = MPRN;
	}
	
}
