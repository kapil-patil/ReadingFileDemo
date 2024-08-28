package com.example.repo;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Table;

@Table(name = "Book")
public class BookEntity {

	@Id
	public Integer idNumber;
	public String author;
	public String title;
	public Integer getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(Integer idNumber) {
		this.idNumber = idNumber;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "BookEntity [idNumber=" + idNumber + ", author=" + author + ", title=" + title + "]";
	}
	
	

}
