package com.maybatch.training.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Book {

	@Column
	@NotNull
	@Size(min = 2, max = 30 , message="Give Author Name")
	private String author;

	@Column
	@Size(min = 2, max = 30, message = "Give Book Description")
	private String body;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column
	private Long bookId;

	@Column
	private Integer copiesOwned;

	@Column
	@NotNull
	private String isbn;

	@Column
	@Size(min = 2, max = 30, message = "Give Book Title")
	private String title;

	public Book() {
		super();
	}

	public String getAuthor() {
		return author;
	}

	public String getBody() {
		return body;
	}

	public Long getBookId() {
		return bookId;
	}

	public Integer getCopiesOwned() {
		return copiesOwned;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public void setCopiesOwned(Integer copiesOwned) {
		this.copiesOwned = copiesOwned;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
