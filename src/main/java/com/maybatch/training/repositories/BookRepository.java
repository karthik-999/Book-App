package com.maybatch.training.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.maybatch.training.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	//JPQL Query
	@Query(value="SELECT * FROM Book book WHERE book.book_id=:bookId",nativeQuery=true)
	public Book getBookByBookId(Long bookId);
	
	//jpaRepositories sample
	public Book findByBookId(Long bookId);
	public Book findByAuthor(String authorName);
	
}
