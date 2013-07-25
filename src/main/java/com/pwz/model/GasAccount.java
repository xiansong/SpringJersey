package com.pwz.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GasAccount implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int gasAcctId;
	private int userId;
	private String username;
	private String acctNumber;
	private String companyName;
	private long GPRN;

	public int getGasAcctId() {
		return gasAcctId;
	}

	public void setGasAcctId(int gasAcctId) {
		this.gasAcctId = gasAcctId;
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

	public long getGPRN() {
		return GPRN;
	}

	public void setGPRN(long GPRN) {
		this.GPRN = GPRN;
	}
	
}
