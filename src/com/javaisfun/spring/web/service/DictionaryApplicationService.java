package com.javaisfun.spring.web.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaisfun.spring.web.dao.EpubReaderforWords;
import com.javaisfun.spring.web.dao.FlashCardDao;
import com.javaisfun.spring.web.dao.QuotesFromBriany;
import com.javaisfun.spring.web.dao.SearchHistoryDao;
import com.javaisfun.spring.web.dao.WordDefDao;
import com.javaisfun.spring.web.model.Book_Data;
import com.javaisfun.spring.web.model.FlashCard;
import com.javaisfun.spring.web.model.Quote;
import com.javaisfun.spring.web.model.User;
import com.javaisfun.spring.web.model.UserSearchRecords;
import com.javaisfun.spring.web.model.WordDef;

@Service("dictionaryService")
public class DictionaryApplicationService {

	private WordDefDao wordDao;
	private FlashCardDao flashDao;
	private QuotesFromBriany quotesB;
	private SearchHistoryDao searchRec;
	private EpubReaderforWords epubR;
	
	@Autowired
	public void setSearchHistory(SearchHistoryDao searchRec) {
		this.searchRec = searchRec;
	}

	@Autowired
	public void setWordDao(WordDefDao wordDao) {
		this.wordDao = wordDao;
	}

	@Autowired
	public void setFlashDao(FlashCardDao flashDao) {
		this.flashDao = flashDao;
	}
	
	@Autowired
	public void setQuotesB(QuotesFromBriany quotesB) {
		this.quotesB = quotesB;
	}
	
	@Autowired
	public void setEpubR(EpubReaderforWords epubR) {
		this.epubR = epubR;
	}

	public List<WordDef> getList() {
		return wordDao.getWordList();
	}
	
	public List<WordDef> getListByLimt(int x, int y) {
		return wordDao.getWordListByLimit(x,y);
	}

	public WordDef getWordDetails(String wordS) {
		System.out.println("Word searched is "+wordS);
		return wordDao.getWordDe(wordS);
	}

	public boolean deleteWord(String wordD) {
		return wordDao.deleteWord(wordD);
	}

	public boolean insertWord(WordDef wordAdd) {
		return wordDao.addNewWord(wordAdd);
	}
	
	public boolean updateWord(WordDef wordUpd) {
		return wordDao.updateWordDef(wordUpd);
	}

	public List<FlashCard> getFlashCardList(String userid) {
		return flashDao.getFlashList(userid);
	}

	public boolean deleteFlashWord(String fWordD,String userid) {
		return flashDao.deleteFlashCard(fWordD,userid);
	}

	public boolean insertFlashWord(FlashCard fWordA) {
		return flashDao.addFlashCard(fWordA);
	}
	
	public int getWordNum(){
		return wordDao.maxWordNum();
	}
	
	public List<User>  getListOfUsers(){
		return wordDao.getUserList();
	}
	
	public int getMaxFlashCards(String userid){
		return flashDao.getFlashCardCount(userid);
	}
	
	public List<Quote> getQuotes(String qWord) throws IOException{
		
		List<Quote> qList = quotesB.getQuotefor(qWord);
		
		return qList;
	}
	


	public boolean archiveFlashWord(String wordA, String userid) {
		return flashDao.updateAsMastered(wordA, userid);
		
	}

	public List<FlashCard> getMasteredFlashCardList(String userid) {
		// TODO Auto-generated method stub
		return flashDao.getMasteredFlashList(userid);
	}
	
	public boolean insertSearchTable(UserSearchRecords newSearch){
		return searchRec.addNewRecord(newSearch);
	}
	
	public List<UserSearchRecords> getSearchRecords(String userid){
		return searchRec.getUserSearchRecords(userid);
	}
	
	public List<Integer> getWSearchRecords(String userid){
		return searchRec.getWordSearchRecords(userid);
	}
	
	public WordDef getWordDefByNum(int x){
		return wordDao.getWordByNum(x);
	}
	
	public List<HashMap<Integer, Integer>> mostSearchedWord(){
		return searchRec.getMostSearchedWord();
	}
	
	public List<String> epubRdforWordList(String path) throws FileNotFoundException, IOException{
		System.out.println("Sending request to get text");
		return epubR.getTextOfBook(path);
	}

	public List<String> vocabularyOfBook(int id, int freq){
		return epubR.getVocabList(id, freq);
	}
	
	public List<Book_Data> listOfBooks(){
		return epubR.getListOfBooks();
	}
	
}
