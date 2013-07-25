package com.pwz.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.pwz.dao.ElecAcctDAO;
import com.pwz.model.ElecAccount;

/**
 * @author Green Lens
 * 
 *         DAO layer doesn't handle Data Access Exception,throw it to front end(rest
 *         services) to handle it
 * 
 */

public class JdbcElecAcctDAO extends NamedParameterJdbcDaoSupport implements
		ElecAcctDAO {

	public int addAccount(ElecAccount account) {
		StringBuffer sqlSB = new StringBuffer();
		sqlSB.append("INSERT INTO elec_account (userId, companyName, acctNumber, MPRN) ");
		sqlSB.append("VALUES (:userId, :companyName, :acctNumber, :MPRN)");
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
				account);
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		getNamedParameterJdbcTemplate().update(sqlSB.toString(), paramSource,
				generatedKeyHolder);
		return generatedKeyHolder.getKey().intValue();
	}

	public ElecAccount getAccount(int acctId) {
		StringBuffer sqlSB = new StringBuffer();
		sqlSB.append("SELECT A.elecAcctId, A.userId, U.username, A.companyName, A.acctNumber, A.MPRN ");
		sqlSB.append("FROM elec_account as A INNER JOIN user as U ON A.userId = U.userId ");
		sqlSB.append("WHERE A.elecAcctId = :elecAcctId");
		SqlParameterSource paramSource = new MapSqlParameterSource(
				"elecAcctId", acctId);
		return getNamedParameterJdbcTemplate().queryForObject(sqlSB.toString(),
				paramSource,
				new BeanPropertyRowMapper<ElecAccount>(ElecAccount.class));
	}

	public void updateAccount(ElecAccount account) {
		StringBuffer sqlSB = new StringBuffer();
		sqlSB.append("UPDATE elec_account ");
		sqlSB.append("SET acctNumber = :acctNumber, companyName=:companyName, MPRN = :MPRN ");
		sqlSB.append("WHERE elecAcctId = :elecAcctId");
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
				account);
		getNamedParameterJdbcTemplate().update(sqlSB.toString(), paramSource);
	}

	public List<ElecAccount> listAccounts(int userId) {
		StringBuffer sqlSB = new StringBuffer();
		sqlSB.append("SELECT A.elecAcctId, A.userId, U.username, A.companyName, A.acctNumber, A.MPRN ");
		sqlSB.append("FROM elec_account as A INNER JOIN user as U ON A.userId = U.userId ");
		sqlSB.append("WHERE A.userId = :userId");
		SqlParameterSource paramSource = new MapSqlParameterSource("userId",
				userId);
		return getNamedParameterJdbcTemplate().query(sqlSB.toString(),
				paramSource,
				new BeanPropertyRowMapper<ElecAccount>(ElecAccount.class));
	}

	public void deleteAccount(int acctId) {
		String sql = "DELETE FROM elec_account WHERE elecAcctId = :acctId";
		SqlParameterSource paramSource = new MapSqlParameterSource("acctId",
				acctId);
		getNamedParameterJdbcTemplate().update(sql, paramSource);
	}

}
