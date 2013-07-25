package com.pwz.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.pwz.dao.ElecBillDAO;
import com.pwz.model.ElecBill;

/**
 * @author Green Lens
 * 
 *         DAO layer doesn't handle Data Access Exception,throw it to front end(rest
 *         services) to handle it
 * 
 */

public class JdbcElecBillDAO extends NamedParameterJdbcDaoSupport implements
		ElecBillDAO {

	public int addBill(final ElecBill bill) {
		StringBuffer sqlSB = new StringBuffer();
		sqlSB.append("INSERT INTO elec_bill (elecAcctId, dayBill, nightBill, issuedDate) ");
		sqlSB.append("VALUES (:elecAcctId, :dayBill, :nightBill, :issuedDate)");
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
				bill);
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		getNamedParameterJdbcTemplate().update(sqlSB.toString(), paramSource,
				generatedKeyHolder);
		return generatedKeyHolder.getKey().intValue();
	}

	public ElecBill getBill(final int billId) {
		StringBuffer sqlSB = new StringBuffer();
		sqlSB.append("SELECT B.billId, B.elecAcctId, U.username, B.dayBill, B.nightBill, B.issuedDate ");
		sqlSB.append("FROM elec_bill AS B INNER JOIN elec_account as A INNER JOIN user as U ");
		sqlSB.append("WHERE B.billId = :billId ");
		sqlSB.append("AND B.elecAcctId = A.elecAcctId AND A.userId = U.userId");
		SqlParameterSource paramSource = new MapSqlParameterSource("billId",
				billId);
		return getNamedParameterJdbcTemplate().queryForObject(sqlSB.toString(),
				paramSource,
				new BeanPropertyRowMapper<ElecBill>(ElecBill.class));
	}

	public List<ElecBill> getBills(final int acctId) {
		StringBuffer sqlSB = new StringBuffer();
		sqlSB.append("SELECT B.billId, B.elecAcctId, U.username, B.dayBill, B.nightBill, B.issuedDate ");
		sqlSB.append("FROM elec_bill AS B INNER JOIN elec_account as A INNER JOIN user as U ");
		sqlSB.append("WHERE B.elecAcctId = :elecAcctId ");
		sqlSB.append("AND B.elecAcctId = A.elecAcctId AND A.userId = U.userId");
		SqlParameterSource paramSource = new MapSqlParameterSource(
				"elecAcctId", acctId);
		return this.getNamedParameterJdbcTemplate().query(sqlSB.toString(),
				paramSource,
				new BeanPropertyRowMapper<ElecBill>(ElecBill.class));
	}

	public void delete(final int billId) {
		String sql = "DELETE FROM elec_bill WHERE billId = :billId";
		SqlParameterSource paramSource = new MapSqlParameterSource("billId",
				billId);
		this.getNamedParameterJdbcTemplate().update(sql, paramSource);
	}

}
