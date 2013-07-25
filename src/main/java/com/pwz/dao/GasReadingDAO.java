package com.pwz.dao;

import java.util.List;
import com.pwz.model.GasReading;;

public interface GasReadingDAO {
	/**
	 * @param reading
	 * @return the primary key of GasReading object
	 */
	int addReading(GasReading reading);
	GasReading getReading(int readingId);
	List<GasReading> getReadings(int gasAcctId);
	void deleteReading(int readingId);
}
