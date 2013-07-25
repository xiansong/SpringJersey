package com.pwz.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.pwz.dao.ElecReadingDAO;
import com.pwz.model.ElecReading;

/**
 * @author Green Lens
 * 
 *         DAO layer doesn't handle Data Access Exception,throw it to front end(rest
 *         services) to handle it
 * 
 */

public class JdbcElecReadingDAO extends NamedParameterJdbcDaoSupport implements
		ElecReadingDAO {

	public int addReading(final ElecReading reading) {
		StringBuffer sqlSB = new StringBuffer();
		sqlSB.append("INSERT INTO elec_reading (elecAcctId, dayReading, nightReading, readDate) ");
		sqlSB.append("VALUES (:elecAcctId, :dayReading, :nightReading, :readDate)");
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
				reading);
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		getNamedParameterJdbcTemplate().update(sqlSB.toString(), paramSource,
				generatedKeyHolder);
		return generatedKeyHolder.getKey().intValue();
	}

	public ElecReading getReading(final int readingId) {
		StringBuffer sqlSB = new StringBuffer();
		sqlSB.append("SELECT R.readingId, R.elecAcctId, U.username, R.dayReading, R.nightReading, R.readDate ");
		sqlSB.append("FROM elec_reading as R INNER JOIN elec_account as A INNER JOIN user as U ");
		sqlSB.append("WHERE R.readingId = :readingId ");
		sqlSB.append("AND R.elecAcctId = A.elecAcctId and A.userId = U.userId");
		SqlParameterSource paramSource = new MapSqlParameterSource("readingId",
				readingId);
		return getNamedParameterJdbcTemplate().queryForObject(sqlSB.toString(),
				paramSource,
				new BeanPropertyRowMapper<ElecReading>(ElecReading.class));
	}

	public List<ElecReading> getReadings(final int acctId) {
		StringBuffer sqlSB = new StringBuffer();
		sqlSB.append("SELECT R.readingId, R.elecAcctId, U.username, R.dayReading, R.nightReading, R.readDate ");
		sqlSB.append("FROM elec_reading as R INNER JOIN elec_account as A INNER JOIN user as U ");
		sqlSB.append("WHERE R.elecAcctId = :elecAcctId ");
		sqlSB.append("AND R.elecAcctId = A.elecAcctId and A.userId = U.userId");
		SqlParameterSource paramSource = new MapSqlParameterSource(
				"elecAcctId", acctId);
		return getNamedParameterJdbcTemplate().query(sqlSB.toString(),
				paramSource,
				new BeanPropertyRowMapper<ElecReading>(ElecReading.class));
	}

	public void deleteReading(final int readingId) {
		String sql = "DELETE FROM elec_reading WHERE readingId = :readingId";
		SqlParameterSource paramSource = new MapSqlParameterSource("readingId",
				readingId);
		getNamedParameterJdbcTemplate().update(sql, paramSource);
	}

}
