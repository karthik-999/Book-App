package com.maybatch.training.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	/* CRUD- Training Exercise Behaviors */

	@PostMapping("/create")
	public Book addBook(@Valid @RequestBody Book book) {
		if (book != null && book.getBookId() != null && getBook(book.getBookId()) != null)
			throw new DuplicateEntryException("Book already Exists. Add Books with new Title");
		return bookService.addBook(book);
	}

	@DeleteMapping("/delete/{bookId}")
	public void deleteBook(@PathVariable Long bookId) {
		bookService.deleteBook(bookId);
	}

	@GetMapping("/getBook/{bookId}")
	public Book getBook(@PathVariable Long bookId) {
		return bookService.getBook(bookId);
	}

	@PutMapping("/update/")
	public Book updateBook(@Valid @RequestBody Book book) {
		return bookService.updateBook(book);
	}

	@GetMapping("/getBooks")
	public List<Book> getBooks() {
		return bookService.getAllBooks();
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
	public Book getBookByNativeQuery(@PathVariable Long bookId) {
		return bookService.getBookByBookId(bookId);
	}


	@GetMapping("/getBookByAuthor/{author}")
	public Book getBookByNativeQuery(@PathVariable String author) {
		return bookService.getBookByAuthor(author);
	}

	
}
