package com.maybatch.training.services.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maybatch.training.entities.Book;

public interface BookServiceInterface {

	Book addBook(Book book);

	Book updateBook(Book updateBookDetails);

	List<Book> getAllBooks();

	void deleteBook(Long bookId);

	Book getBook(Long bookId);

	Book getBookByBookId(Long bookId);

	Page<Book> findAll(Pageable pageable);

    Book getBookByAuthor(String authorName);

}