package com.javaisfun.spring.web.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.javaisfun.spring.web.model.User;
import com.javaisfun.spring.web.model.WordDef;
import com.javaisfun.spring.web.service.DictionaryApplicationService;


@Component("/userdao")
public class UserDao {
	
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

	}
	
	public String getUserId(String username){
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("username", username);
		
		String sql = "SELECT userid FROM dictionary.users where username =:username";
		String userid = jdbcTemplate.queryForObject(sql, params, new RowMapper<String>(){
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String usernum = rs.getString("userid") ;
				return usernum;
			}
			
		});
	
		return userid;
		
	}
	
	@Transactional
	public boolean addNewUser(User user) {
		
	System.out.println("I am inserting....");
		
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(user);
		  
		String sql =   "Insert into dictionary.users (username, password, UserID, enabled, emailid) values (:username, :password, :UserID, :enabled, :emailid)";
		  
		jdbcTemplate.update("Insert into dictionary.authorities (user_role_id, username, authority) values (:UserID, :username, :authority)", params);
		return jdbcTemplate.update(sql, params)==1;
	}
	

	public int maxUserID(){
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		String sql = "select max(UserID) from dictionary.users"; 
		
		int maxUserId = jdbcTemplate.queryForObject(sql, params, new RowMapper<Integer>(){
			
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
			int usernum = rs.getInt("max(UserID)") ;
				return usernum;
			}
			
		}
		);
		System.out.println(maxUserId);
		return maxUserId;
	}
	
	
public String searchEmailId(String emailId){
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("emailId", emailId);
		
		String sql = "SELECT userid FROM dictionary.users where emailid =:emailId";
		try{
		String userid = jdbcTemplate.queryForObject(sql, params, new RowMapper<String>(){
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String usernum = rs.getString("userid") ;
				return usernum;
			}
			
		});
	
		return userid;
		}catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}
