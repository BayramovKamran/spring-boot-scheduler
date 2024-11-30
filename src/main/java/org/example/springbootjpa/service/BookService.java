package org.example.springbootjpa.service;

import org.example.springbootjpa.entity.Book;

import java.util.List;

public interface BookService {

    List<Book> findAllBooks();

    Book findBookById(Long id);

    void saveBook(Book book);

    void updateBook(Book book);

    void deleteBook(Long id);
}
