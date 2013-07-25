package com.pwz.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.pwz.dao.GasBillDAO;
import com.pwz.model.GasBill;

/**
 * @author Green Lens
 * 
 *         DAO layer doesn't handle Data Access Exception,throw it to front
 *         end(rest services) to handle it
 * 
 */

public class JdbcGasBillDAO extends NamedParameterJdbcDaoSupport implements
		GasBillDAO {

	public int addBill(final GasBill bill) {
		StringBuffer sqlSB = new StringBuffer();
		sqlSB.append("INSERT INTO gas_bill (gasAcctId, bill, issuedDate) ");
		sqlSB.append("VALUES (:gasAcctId, :bill, :issuedDate)");
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
				bill);
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		getNamedParameterJdbcTemplate().update(sqlSB.toString(), paramSource,
				generatedKeyHolder);
		return generatedKeyHolder.getKey().intValue();
	}

	public GasBill getBill(final int billId) {
		StringBuffer sqlSB = new StringBuffer();
		sqlSB.append("SELECT B.billId, B.gasAcctId, U.username, B.bill, B.issuedDate ");
		sqlSB.append("FROM gas_bill AS B INNER JOIN gas_account AS A INNER JOIN user AS U ");
		sqlSB.append("WHERE B.billId = :billId ");
		sqlSB.append("AND B.gasAcctId = A.gasAcctId AND A.userId = U.userId");
		SqlParameterSource paramSource = new MapSqlParameterSource("billId",
				billId);
		return getNamedParameterJdbcTemplate().queryForObject(sqlSB.toString(),
				paramSource, new BeanPropertyRowMapper<GasBill>(GasBill.class));
	}

	public List<GasBill> getBills(final int acctId) {
		StringBuffer sqlSB = new StringBuffer();
		sqlSB.append("SELECT B.billId, B.gasAcctId, U.username, B.bill, B.issuedDate ");
		sqlSB.append("FROM gas_bill AS B INNER JOIN gas_account AS A INNER JOIN user AS U ");
		sqlSB.append("WHERE B.gasAcctId = :gasAcctId ");
		sqlSB.append("AND B.gasAcctId = A.gasAcctId AND A.userId = U.userId");
		SqlParameterSource paramSource = new MapSqlParameterSource("gasAcctId",
				acctId);
		return getNamedParameterJdbcTemplate().query(sqlSB.toString(),
				paramSource, new BeanPropertyRowMapper<GasBill>(GasBill.class));
	}

	public void delete(final int billId) {
		String sql = "DELETE FROM gas_bill WHERE billId = :billId";
		SqlParameterSource paramSource = new MapSqlParameterSource("billId",
				billId);
		getNamedParameterJdbcTemplate().update(sql, paramSource);
	}

}
