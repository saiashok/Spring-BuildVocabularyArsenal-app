package com.javaisfun.spring.web.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.javaisfun.spring.web.model.FlashCard;
import com.javaisfun.spring.web.model.UserSearchRecords;
import com.javaisfun.spring.web.model.WordDef;

@Component("searchrecorddao")
public class SearchHistoryDao {

	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

	}
	
	public boolean addNewRecord(UserSearchRecords newRec){
		
	BeanPropertySqlParameterSource beanParam = new BeanPropertySqlParameterSource(newRec);
		String sql = "insert into dictionary.user_search_history (userid, word_searched, timestamp) values (:userid, :word_searched, :timestamp)";
		
		return jdbcTemplate.update(sql, beanParam) == 1;
		}
		
	
	public List<UserSearchRecords> getUserSearchRecords(String userid){
		
		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("userid", userid);	
		String sql ="Select * from dictionary.user_search_history where userid =:userid  order by timestamp desc";
		
		try{
			List<UserSearchRecords> userRecord=  jdbcTemplate.query(sql, params, new RowMapper<UserSearchRecords>(){
				
				@Override
				public UserSearchRecords mapRow(ResultSet rs, int rowNum) throws SQLException {
					UserSearchRecords record = new UserSearchRecords();
					
					record.setUserid(rs.getString("userid"));
					record.setWord_searched(rs.getInt("word_searched"));
					record.setTimestamp(rs.getTimestamp("timestamp"));
					return record;
				}
				
			});
		return userRecord;
			
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	
	public List<Integer> getWordSearchRecords(String userid){
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("userid", userid);	
		String sql ="Select word_searched from dictionary.user_search_history where userid =:userid order by timestamp desc";
		
		try{
			List<Integer> wordSearchList=  jdbcTemplate.query(sql, params, new RowMapper<Integer>(){
				
				@Override
				public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
					int record= 0;
					record = rs.getInt("word_searched");
					return record;
				}
				
			});
			return wordSearchList;
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	
	public List<HashMap<Integer, Integer>> getMostSearchedWord(){
		
		String sql = "SELECT word_searched, COUNT(word_searched) FROM dictionary.user_search_history GROUP BY word_searched order by COUNT(word_searched) DESC";
		
		List<HashMap<Integer, Integer>> wordList = jdbcTemplate.query(sql, new RowMapper<HashMap<Integer, Integer>>(){

			@Override
			public HashMap<Integer, Integer> mapRow(ResultSet rs, int rowNum) throws SQLException {
							
				HashMap<Integer, Integer> wordDef = new HashMap<Integer, Integer>();
				wordDef.put(rs.getInt("word_searched"), rs.getInt("COUNT(word_searched)"));
				return wordDef;
			}
			
		});
		
		
	
		return wordList;
		
	}
}
