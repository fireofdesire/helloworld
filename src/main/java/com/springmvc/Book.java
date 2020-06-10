package com.springmvc;

public class Book 
{
    private String BookName;
    private Writer writer;

	public Book() {
		super();
	}
	
	public String getBookName() {
		return BookName;
	}
	public void setBookName(String bookName) {
		BookName = bookName;
	}

	public Writer getWriter() {
		return writer;
	}

	public void setWriter(Writer writer) {
		this.writer = writer;
	}

	public Book(String bookName, Writer writer) {
		super();
		BookName = bookName;
		this.writer = writer;
	}

	@Override
	public String toString() {
		return "Book [BookName=" + BookName + ", writer=" + writer + "]";
	}
	
	
}
