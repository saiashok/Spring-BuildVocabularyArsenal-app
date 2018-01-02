package com.javaisfun.spring.web.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.javaisfun.spring.web.model.User;
import com.javaisfun.spring.web.model.WordDef;

@Component("wordDefDao")
public class WordDefDao {

	private NamedParameterJdbcTemplate jdbcTemplate;

	public WordDefDao() {
		System.out.println("Sucessfully loaded wordDefDao");
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

	}

	public List<WordDef> getWordList() {

		String sql = "select * from dictionary.searchengine";
		List<WordDef> wordList = jdbcTemplate.query(sql, new RowMapper<WordDef>(){

			@Override
			public WordDef mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				
				WordDef wordDef = new WordDef();
				
				wordDef.setWordNum(rs.getInt("WordNum"));
				wordDef.setWord(rs.getString("word"));
				wordDef.setWordType(rs.getString("wordType"));
				wordDef.setWordDef(rs.getString("Definition"));

				return wordDef;
			}
			
			
		});

		return wordList;
	}

	public List<WordDef> getWordListByLimit(int s, int e) {

		String sql = "select * from dictionary.searchengine Limit "+s+","+e;
		List<WordDef> wordList = jdbcTemplate.query(sql, new RowMapper<WordDef>(){

			@Override
			public WordDef mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				
				WordDef wordDef = new WordDef();

				wordDef.setWordNum(rs.getInt("WordNum"));
				wordDef.setWord(rs.getString("word"));
				wordDef.setWordType(rs.getString("wordType"));
				wordDef.setWordDef(rs.getString("Definition"));

				return wordDef;
			}
			
			
		});

		return wordList;
	}
	
	public WordDef getWordDe(String word) {
		MapSqlParameterSource params = new MapSqlParameterSource("wordS", word);
		
		/*params.addValue("wordS", "Chafe");*/
		
		String sql = "select * from dictionary.searchengine" + " where " + " word = :wordS";		
		System.out.println("I am searching from database "+word);
		try{
		WordDef wordSearched = jdbcTemplate.queryForObject(sql, params, new RowMapper<WordDef>()
		 {

				@Override
				public WordDef mapRow(ResultSet rs, int rowNum) throws SQLException {
					WordDef wordDef = new WordDef();
					wordDef.setWordNum(rs.getInt("WordNum"));
					wordDef.setWord(rs.getString("word"));
					wordDef.setWordType(rs.getString("wordType"));
					wordDef.setWordDef(rs.getString("Definition"));

					return wordDef;
				}				
			});
		 System.out.println(wordSearched);
		return wordSearched;
	}
	catch(EmptyResultDataAccessException e){
		System.out.println("word defintion not found..!");
		return null;
	}
	}

	public boolean addNewWord(WordDef wordAdd) {

		
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(wordAdd);
		  
		String sql =   "Insert into dictionary.searchengine (wordNum, word, wordType, Definition) values (:wordNum, :word, :wordType, :wordDef)";
		  
		return jdbcTemplate.update(sql, params)==1;
	}

	public boolean updateWordDef(WordDef wordUpd) {
		
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(wordUpd);
		
		String sql = "update dictionary.searchengine  SET wordType=:wordType, Definition=:wordDef where wordNum= :wordNum and word= :word";
		
		return  jdbcTemplate.update(sql, params)==1;
	}

	public boolean deleteWord(String wordD) {
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("wordD", wordD);
		
		String sql = "delete from searchengine where word= :wordD";		
		
		return jdbcTemplate.update(sql, params)==1;
	}
	
	
	public int maxWordNum(){
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		String sql = "select max(wordNum) from searchengine"; 
		
		int maxNum = jdbcTemplate.queryForObject(sql, params, new RowMapper<Integer>(){
			
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
			int wordNum = rs.getInt("max(wordNum)") ;
				return wordNum;
			}
			
		}
		);
		System.out.println(maxNum);
		return maxNum;
	}
	
	public List<User> getUserList() {

		String sql = "select * from dictionary.users";
		List<User> users = jdbcTemplate.query(sql, new RowMapper<User>(){

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				
				User us = new User();
				
				us.setUsername(rs.getString("username"));
				us.setUserID(rs.getString("UserID"));
				us.setEnabled(rs.getBoolean("enabled"));
				
				

				return us;
			}
			
			
		});

		return users;
	}

	public WordDef getWordByNum(int wordNum) {
		MapSqlParameterSource params = new MapSqlParameterSource("wordN", wordNum);
		
		/*params.addValue("wordS", "Chafe");*/
		
		String sql = "select * from dictionary.searchengine" + " where " + " wordNum = :wordN";		
		
		try{
		WordDef wordObj = jdbcTemplate.queryForObject(sql, params, new RowMapper<WordDef>()
		 {

				@Override
				public WordDef mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					
					WordDef wordDef = new WordDef();
					
					wordDef.setWordNum(rs.getInt("WordNum"));
					wordDef.setWord(rs.getString("word"));
					wordDef.setWordType(rs.getString("wordType"));
					wordDef.setWordDef(rs.getString("Definition"));

					return wordDef;
				}
				
				
			});
		 return wordObj;
	}
	catch(EmptyResultDataAccessException e){
		return null;
	}
	}

	
}
