package com.maybatch.training.controller;

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
import com.maybatch.training.response.ResponseMessage;
import com.maybatch.training.services.interfaces.BookServiceInterface;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	BookServiceInterface bookService;

	@PostMapping("/add")
	public ResponseEntity<Book> addBook(@Valid @RequestBody Book book) {

		if (book != null && book.getBookId() != null && getBook(book.getBookId()).getBody() != null)
			throw new DuplicateEntryException("Book already Exists. Add Books with new Title");

		book = bookService.addBook(book);
		return new ResponseEntity<>(book, HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/{bookId}")
	public ResponseEntity<ResponseMessage> deleteBook(@PathVariable Long bookId) {
		bookService.deleteBook(bookId);
		return new ResponseEntity<>(new ResponseMessage("Succesfully Deleted"), HttpStatus.OK);
	}

	@GetMapping("/getBook/{bookId}")
	public ResponseEntity<Book> getBook(@PathVariable Long bookId) {
		Book book = bookService.getBook(bookId);
		return new ResponseEntity<>(book, HttpStatus.OK);
	}

	@PutMapping("/update/")
	public ResponseEntity<ResponseMessage> updateBook(@Valid @RequestBody Book book) {
		book = bookService.updateBook(book);
		return new ResponseEntity<>(new ResponseMessage("Successfully Updated"), HttpStatus.OK);
	}

	@GetMapping("/getBooks")
	public ResponseEntity<List<Book>> getBooks() {
		List<Book> books = bookService.getAllBooks();
		return new ResponseEntity<>(books, HttpStatus.OK);
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
	public ResponseEntity<Book> getBookById(@PathVariable Long bookId) {
		Book book = bookService.getBookByBookId(bookId);
		return new ResponseEntity<>(book, HttpStatus.OK);
	}

	@GetMapping("/getBookByAuthor/{author}")
	public ResponseEntity<Book> getBookByAuthor(@PathVariable String author) {
		Book book = bookService.getBookByAuthor(author);
		return new ResponseEntity<>(book, HttpStatus.OK);
	}

}
