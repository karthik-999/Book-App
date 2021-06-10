package com.maybatch.training.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="Book")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column
	private Long bookId;

	@Column
	@NotNull
	@Size(min = 2, max = 30, message = "Give Author Name between 2 & 30 characters")
	private String author;

	@Column
	@Size(min = 2, max = 30, message = "Give Book Summary between 2 & 50 characters")
	private String summary;

	@Column
	@NotNull
	private String isbn;

	@Column
	@Size(min = 2, max = 30, message = "Give Book Title")
	private String title;

	@Column(name = "price")
	@NotNull
	private Double price;

	public Book() {
		super();
	}

	public String getAuthor() {
		return author;
	}

	public Long getBookId() {
		return bookId;
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

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
