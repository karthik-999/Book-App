package com.maybatch.training.controllers;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maybatch.training.entities.Book;
import com.maybatch.training.exceptions.DuplicateEntryException;
import com.maybatch.training.exceptions.EmptyPageRecordsException;
import com.maybatch.training.services.interfaces.BookServiceInterface;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	BookServiceInterface bookService;

	Instant startTime = null;
	Instant stopTime = null;

	Logger logger = LoggerFactory.getLogger(BookController.class);

	/* CRUD- Training Exercise Behaviors */

	@PostMapping("/create")
	public Book addBook(@Valid @RequestBody Book book) {
		logger.debug("Request Adding Book {}", book);
		startTime = Instant.now();

		if (book != null && book.getBookId() != null && getBook(book.getBookId()) != null)
			throw new DuplicateEntryException("Book already Exists. Add Books with new Title");
		book = bookService.addBook(book);

		stopTime = Instant.now();
		Duration elapsedTime = Duration.between(startTime, stopTime);
		logger.debug("Total Time Execution {}ms", elapsedTime.toMillis());

		return book;
	}

	@DeleteMapping("/delete/{bookId}")
	public void deleteBook(@PathVariable Long bookId) {
		logger.debug("Request Deleting Book {}", bookId);
		startTime = Instant.now();

		bookService.deleteBook(bookId);

		logger.debug("Response Deleting Book {}", bookId);
		Instant stopTime = Instant.now();
		Duration elapsedTime = Duration.between(startTime, stopTime);
		logger.debug("Total Time Execution {}ms", elapsedTime.toMillis());

	}

	@GetMapping("/getBook/{bookId}")
	public Book getBook(@PathVariable Long bookId) {
		logger.debug("Request get Book {}", bookId);
		startTime = Instant.now();

		Book book = bookService.getBook(bookId);

		logger.debug("Response get Book {}", bookId);
		Instant stopTime = Instant.now();
		Duration elapsedTime = Duration.between(startTime, stopTime);
		logger.debug("Total Time Execution {}ms", elapsedTime.toMillis());

		return book;
	}

	@PutMapping("/update/")
	public Book updateBook(@Valid @RequestBody Book book) {
		logger.debug("Request Update Book {}", book);
		startTime = Instant.now();

		book = bookService.updateBook(book);

		logger.debug("Response Updating Book {}", book);

		Instant stopTime = Instant.now();
		Duration elapsedTime = Duration.between(startTime, stopTime);
		logger.debug("Total Time Execution {}ms", elapsedTime.toMillis());

		return book;
	}

	@GetMapping("/getBooks")
	public List<Book> getBooks() {
		logger.debug("Request get All Books {}");
		startTime = Instant.now();

		List<Book> books = bookService.getAllBooks();

		logger.debug("Response get Book {}", books);
		Instant stopTime = Instant.now();
		Duration elapsedTime = Duration.between(startTime, stopTime);
		logger.debug("Total Time Execution {}ms", elapsedTime.toMillis());

		return books;
	}

	// Pagination
	@GetMapping("/all/{page}/{size}")
	public ResponseEntity<Map<String, Object>> getBooksByPagination(@PathVariable int page, @PathVariable int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("bookId").ascending().and(Sort.by("title")));
		Page<Book> pages = bookService.findAll(pageable);
		List<Book> books = pages.getContent();
		if (!books.isEmpty()) {
			Map<String, Object> response = new HashMap<>();
			response.put("books", books);
			response.put("currentPage", pages.getNumber());
			response.put("totalItems", pages.getTotalElements());
			response.put("totalPages", pages.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		throw new EmptyPageRecordsException("No Page Records Found, Give Proper Page Number");
	}

	// Native Queries
	@GetMapping("/getBookByNative/{bookId}")
	public Book getBookById(@PathVariable Long bookId) {
		startTime = Instant.now();

		Book book = bookService.getBookByBookId(bookId);

		Instant stopTime = Instant.now();
		Duration elapsedTime = Duration.between(startTime, stopTime);
		logger.debug("Total Time Execution {}ms", elapsedTime.toMillis());

		return book;
	}

	@GetMapping("/getBookByAuthor/{author}")
	public Book getBookByAuthor(@PathVariable String author) {
		startTime = Instant.now();

		Book book = bookService.getBookByAuthor(author);

		Instant stopTime = Instant.now();
		Duration elapsedTime = Duration.between(startTime, stopTime);
		logger.debug("Total Time Execution {}ms", elapsedTime.toMillis());

		return book;
	}

}
