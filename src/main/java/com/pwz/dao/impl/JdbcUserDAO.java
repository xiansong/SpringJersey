package com.pwz.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.pwz.dao.UserDAO;
import com.pwz.model.User;

/**
 * @author Green Lens
 * 
 *         DAO layer doesn't handle Data Access Exception,throw it to front end(rest
 *         services) to handle it
 * 
 */

public class JdbcUserDAO extends NamedParameterJdbcDaoSupport implements
		UserDAO {

	public User getUser(int userId) {
		// TODO Auto-generated method stub
		String sql = "select userId,username,password, enabled from user where userId = :userId";
		SqlParameterSource paramSource = new MapSqlParameterSource("userId",
				userId);
		return getNamedParameterJdbcTemplate().queryForObject(sql, paramSource,
				new BeanPropertyRowMapper<User>(User.class));
	}

	public User getUser(String username) {
		String sql = "select userId,username,password, enabled from user where username = :username";
		SqlParameterSource paramSource = new MapSqlParameterSource("username",
				username);
		return (User) getNamedParameterJdbcTemplate().queryForObject(sql,
				paramSource, new BeanPropertyRowMapper<User>(User.class));
	}

	public int register(User user) {
		String sql = "INSERT INTO user(username, password, enabled) VALUES (:username, :password, true)";
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
				user);
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		getNamedParameterJdbcTemplate().update(sql, paramSource,
				generatedKeyHolder);
		return generatedKeyHolder.getKey().intValue();
	}

	public List<User> listUsers() {
		String sql = "select userId,username,password, enabled FROM user";
		return getNamedParameterJdbcTemplate().getJdbcOperations().query(sql,
				new BeanPropertyRowMapper<User>(User.class));
	}

	public void delete(int userId) {
		String sql = "DELETE FROM user WHERE userId = :userId";
		SqlParameterSource paramSource = new MapSqlParameterSource("userId",
				userId);
		getNamedParameterJdbcTemplate().update(sql, paramSource);
	}

	public void update(User user) {
		String sql = "UPDATE user SET password = :password WHERE userId = :userId";
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
				user);
		getNamedParameterJdbcTemplate().update(sql, paramSource);
	}

}
