package com.pojo;

import java.util.List;
import java.util.Map;
import java.util.Properties;


public class Person 
{
	private String Name;
	private String Sex;
    
    @Override
	public String toString() {
		return "Person [Name=" + Name + ", Sex=" + Sex + ", book=" + book + ", books=" + books + ", maps=" + maps
				+ ", properties=" + properties + "]";
	}
	private Book book;
    private List<Book> books;
    private Map<String,Object> maps;
    private Properties properties;
	
	public Person() {
		super();
		System.out.println("person进行了创建");
	}
	
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getSex() {
		return Sex;
	}
	public void setSex(String sex) {
		Sex = sex;
	}
	
	public Person(String name, String sex, Book book, List<Book> books, Map<String, Object> maps,
			Properties properties) {
		super();
		Name = name;
		Sex = sex;
		this.book = book;
		this.books = books;
		this.maps = maps;
		this.properties = properties;
	}


	public Book getBook() {
		return book;
	}


	public void setBook(Book book) {
		this.book = book;
	}


	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	public Map<String, Object> getMaps() {
		return maps;
	}
	public void setMaps(Map<String, Object> maps) {
		this.maps = maps;
	}
	public Person(Book book) {
		super();
		this.book = book;
	}


	public Properties getProperties() {
		return properties;
	}
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
}
