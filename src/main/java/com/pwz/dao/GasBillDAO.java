package com.pwz.dao;

import java.util.List;

import com.pwz.model.GasBill;

public interface GasBillDAO {
	
	/**
	 * @param bill an instance of GasBill class
	 * @return	   the primary key generated by database
	 */
	int addBill(GasBill bill);
	GasBill getBill(int billId);
	List<GasBill> getBills(int acctId);
	void delete(int billId);
	
}
