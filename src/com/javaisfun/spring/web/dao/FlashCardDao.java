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

import com.javaisfun.spring.web.model.FlashCard;
import com.javaisfun.spring.web.model.WordDef;

@Component("flashcardDao")
public class FlashCardDao {

	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

	}

	public List<FlashCard> getFlashList(String userid) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("userid", userid);

		String sql = "select * from flashcards where userid =:userid and isMastered = 0 ";

		List<FlashCard> flashList = jdbcTemplate.query(sql, params, new RowMapper<FlashCard>() {

			@Override
			public FlashCard mapRow(ResultSet rs, int rowNum) throws SQLException {

				FlashCard flashWord = new FlashCard();
				flashWord.setFlashCardId(rs.getInt("flashCardId"));
				flashWord.setFlashword(rs.getString("flashword"));
				flashWord.setFDefinition(rs.getString("fDefinition"));
				flashWord.setFwordNUm(rs.getInt("fwordNUm"));

				return flashWord;
			}

		});

		return flashList;

	}

	public boolean addFlashCard(FlashCard wordF) {
		
		String wordS =wordF.getFlashword();
		String user = wordF.getUserId();
		
		if(getFlashCard(wordS, user)== null){
		
		BeanPropertySqlParameterSource beanParam = new BeanPropertySqlParameterSource(wordF);
		

		String sql = "insert into dictionary.flashcards (flashword, FDefinition, FwordNum, UserId) values (:flashword,:fDefinition,:fwordNUm, :UserId)";
		return jdbcTemplate.update(sql, beanParam) == 1;
		}
		
		else{
			return false;
		}
	}

	public boolean deleteFlashCard(String fWordD, String userId) {

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("flashword", fWordD);
		params.addValue("userId", userId);

		// Need to get User ID
		String sql = "DELETE from  dictionary.flashcards where flashword=:flashword and userId = :userId";

		return jdbcTemplate.update(sql, params) == 1;
	}
	
	public int getFlashCardCount(String userid) {

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("userid", userid);

		// Need to get User ID
		String sql = "SELECT max(flashCardId) from dictionary.flashcards where userid =:userid";

		return jdbcTemplate.queryForObject(sql, params, new RowMapper<Integer>(){
			
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
			int wordNum = rs.getInt("max(flashCardId)") ;
				return wordNum;
			}
			
		});
	}
	
	public FlashCard getFlashCard(String word, String userid){
		
		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("userid", userid);
		params.addValue("flashword", word);
		
	String sql ="Select * from dictionary.flashcards where userid =:userid and flashword =:flashword";
	
	try{
	FlashCard flashW=  jdbcTemplate.queryForObject(sql, params, new RowMapper<FlashCard>(){
			
			@Override
			public FlashCard mapRow(ResultSet rs, int rowNum) throws SQLException {
				FlashCard flashWord = new FlashCard();
				flashWord.setFlashCardId(rs.getInt("flashCardId"));
				flashWord.setFlashword(rs.getString("flashword"));
				flashWord.setFDefinition(rs.getString("fDefinition"));
				flashWord.setFwordNUm(rs.getInt("fwordNUm"));
				return flashWord;
			}
			
		});
	return flashW;
	}
	catch(EmptyResultDataAccessException e){
		return null;
	}
		
	}
	
	public boolean updateAsMastered(String wordA,String userId){
		
		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("flashword", wordA);
		params.addValue("userId", userId);
		String sql = "UPDATE  dictionary.flashcards SET isMastered = 1 where flashword=:flashword and userId = :userId";
		return jdbcTemplate.update(sql, params) == 1;
		
	}

	public List<FlashCard> getMasteredFlashList(String userid) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("userid", userid);

		String sql = "select * from flashcards where userid =:userid and isMastered='1'";

		List<FlashCard> flashList = jdbcTemplate.query(sql, params, new RowMapper<FlashCard>() {

			@Override
			public FlashCard mapRow(ResultSet rs, int rowNum) throws SQLException {

				FlashCard flashWord = new FlashCard();
				flashWord.setFlashCardId(rs.getInt("flashCardId"));
				flashWord.setFlashword(rs.getString("flashword"));
				flashWord.setFDefinition(rs.getString("fDefinition"));
				flashWord.setFwordNUm(rs.getInt("fwordNUm"));

				return flashWord;
			}

		});

		return flashList;	
	}

}
