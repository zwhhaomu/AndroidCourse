package com.example.remeberword;

public class Word {
	private int id;
	private String word;
	private String detail;
	public Word(){}
	public Word(int id, String word, String detail) {
		super();
		this.id = id;
		this.word = word;
		this.detail = detail;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
}
