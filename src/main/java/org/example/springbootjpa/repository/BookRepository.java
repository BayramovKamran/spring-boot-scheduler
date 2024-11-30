package org.example.springbootjpa.repository;

import org.example.springbootjpa.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByStatus(String status);
}