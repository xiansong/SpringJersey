package com.pwz.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.pwz.dao.GasAcctDAO;
import com.pwz.model.GasAccount;

/**
 * @author Green Lens
 * 
 *         DAO layer doesn't handle Data Access Exception,throw it to front
 *         end(rest services) to handle it
 * 
 */
public class JdbcGasAcctDAO extends NamedParameterJdbcDaoSupport implements
		GasAcctDAO {

	public int addAccount(final GasAccount account) {
		StringBuffer sqlSB = new StringBuffer();
		sqlSB.append("INSERT INTO gas_account (userId, companyName, acctNumber, GPRN) ");
		sqlSB.append("VALUES (:userId, :companyName, :acctNumber, :GPRN)");
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
				account);
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		getNamedParameterJdbcTemplate().update(sqlSB.toString(), paramSource,
				generatedKeyHolder);
		return generatedKeyHolder.getKey().intValue();
	}

	public GasAccount getAccount(final int acctId) {
		StringBuffer sqlSB = new StringBuffer();
		sqlSB.append("SELECT A.gasAcctId, A.userId, U.username, A.companyName, A.acctNumber, A.GPRN ");
		sqlSB.append("FROM gas_account as A INNER JOIN user as U ");
		sqlSB.append("ON A.userId = U.userId WHERE A.gasAcctId = :gasAcctId");
		SqlParameterSource paramSource = new MapSqlParameterSource("gasAcctId",
				acctId);
		return getNamedParameterJdbcTemplate().queryForObject(sqlSB.toString(),
				paramSource,
				new BeanPropertyRowMapper<GasAccount>(GasAccount.class));
	}

	public void updateAccount(final GasAccount account) {
		StringBuffer sqlSB = new StringBuffer();
		sqlSB.append("UPDATE gas_account ");
		sqlSB.append("set acctNumber = :acctNumber, companyName=:companyName, GPRN = :GPRN ");
		sqlSB.append("WHERE gasAcctId = :gasAcctId");
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
				account);
		getNamedParameterJdbcTemplate().update(sqlSB.toString(), paramSource);
	}

	public List<GasAccount> listAccounts(final int userId) {
		StringBuffer sqlSB = new StringBuffer();
		sqlSB.append("SELECT A.gasAcctId, A.userId, U.username, A.companyName, A.acctNumber, A.GPRN ");
		sqlSB.append("FROM gas_account as A INNER JOIN user as U ");
		sqlSB.append("ON A.userId = U.userId WHERE A.userId = :userId");
		SqlParameterSource paramSource = new MapSqlParameterSource("userId",
				userId);
		return getNamedParameterJdbcTemplate().query(sqlSB.toString(),
				paramSource,
				new BeanPropertyRowMapper<GasAccount>(GasAccount.class));
	}

	public void deleteAccount(final int acctId) {
		String sql = "DELETE FROM gas_account WHERE gasAcctId = :acctId";
		SqlParameterSource paramSource = new MapSqlParameterSource("acctId",
				acctId);
		getNamedParameterJdbcTemplate().update(sql, paramSource);
	}

}
