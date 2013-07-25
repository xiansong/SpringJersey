package com.pwz.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.pwz.dao.GasReadingDAO;
import com.pwz.model.GasReading;

/**
 * @author Green Lens
 * 
 *         DAO layer doesn't handle Data Access Exception,throw it to front
 *         end(rest services) to handle it
 * 
 */

public class JdbcGasReadingDAO extends NamedParameterJdbcDaoSupport implements
		GasReadingDAO {

	public int addReading(final GasReading reading) {
		StringBuffer sqlSB = new StringBuffer();
		sqlSB.append("INSERT INTO gas_reading (gasAcctId, reading, readDate) VALUES ");
		sqlSB.append("(:gasAcctId, :reading, :readDate)");
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
				reading);
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		getNamedParameterJdbcTemplate().update(sqlSB.toString(), paramSource,
				generatedKeyHolder);
		return generatedKeyHolder.getKey().intValue();
	}

	public GasReading getReading(final int readingId) {
		StringBuffer sqlSB = new StringBuffer();
		sqlSB.append("SELECT R.readingId, R.gasAcctId, U.username, R.reading, R.readDate ");
		sqlSB.append("FROM gas_reading as R INNER JOIN gas_account as A INNER JOIN user as U ");
		sqlSB.append("WHERE R.readingId = :readingId ");
		sqlSB.append("AND R.gasAcctId = A.gasAcctId and A.userId = U.userId");
		SqlParameterSource paramSource = new MapSqlParameterSource("readingId",
				readingId);
		return getNamedParameterJdbcTemplate().queryForObject(sqlSB.toString(),
				paramSource,
				new BeanPropertyRowMapper<GasReading>(GasReading.class));
	}

	public List<GasReading> getReadings(final int gasAcctId) {
		StringBuffer sqlSB = new StringBuffer();
		sqlSB.append("SELECT R.readingId, R.gasAcctId, U.username, R.reading, R.readDate ");
		sqlSB.append("FROM gas_reading as R INNER JOIN gas_account as A INNER JOIN user as U ");
		sqlSB.append("WHERE R.gasAcctId = :gasAcctId ");
		sqlSB.append("AND R.gasAcctId = A.gasAcctId and A.userId = U.userId");
		SqlParameterSource paramSource = new MapSqlParameterSource("gasAcctId",
				gasAcctId);
		return getNamedParameterJdbcTemplate().query(sqlSB.toString(),
				paramSource,
				new BeanPropertyRowMapper<GasReading>(GasReading.class));
	}

	public void deleteReading(final int readingId) {
		String sql = "DELETE FROM gas_reading WHERE readingId = :readingId";
		SqlParameterSource paramSource = new MapSqlParameterSource("readingId",
				readingId);
		getNamedParameterJdbcTemplate().update(sql, paramSource);
	}

}
