package com.maybatch.training.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.maybatch.training.entities.Book;
import com.maybatch.training.repositories.BookRepository;
import com.maybatch.training.services.interfaces.BookServiceInterface;

@Service
public class BookServiceImpl implements BookServiceInterface {

	@Autowired
	BookRepository bookRepository;

	@Override
	public Book addBook(Book book) {
		return bookRepository.save(book);
	}

	@Override
	public Book updateBook(Book updateBookDetails) {
		return bookRepository.save(updateBookDetails);
	}

	@Override
	public List<Book> getAllBooks() {
		return (List<Book>) bookRepository.findAll();
	}

	@Override
	public void deleteBook(Long bookId) {
		Optional<Book> book = bookRepository.findById(bookId);
		if (book.isPresent()) {
			bookRepository.delete(book.get());
		}
	}

	@Override
	public Book getBook(Long bookId) {
		Optional<Book> book = bookRepository.findById(bookId);
		if (book.isPresent())
			return book.get();
		else
			return null;
	}

	public Book getBookByBookId(Long bookId) {
		Book book = bookRepository.getBookByBookId(bookId);
		if (book != null)
			return book;
		return null;
	}

	public Book getBookByAuthor(String authorName) {
		Book book = bookRepository.findByAuthor(authorName);
		if (book != null)
			return book;
		return null;
	}

	@Override
	public Page<Book> findAll(Pageable pageable) {
		return bookRepository.findAll(pageable);
	}

}
