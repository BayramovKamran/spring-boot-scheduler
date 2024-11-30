package org.example.springbootjpa.service;

import org.example.springbootjpa.entity.Book;
import org.example.springbootjpa.repository.BookRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("books")
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Book findBookById(Long id) {
        return bookRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new IllegalArgumentException("Книга с ID " + id + " не найдена"));
    }

    @Override
    @Transactional
    public void saveBook(Book book) {
        validateBook(book);
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void updateBook(Book book) {
        validateBook(book);
        if (book.getId() == null) {
            throw new IllegalArgumentException("ID книги не может быть null");
        }
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID книги не может быть null");
        }
        bookRepository.deleteById(Math.toIntExact(id));
    }

    private void validateBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Книга не может быть null");
        }
    }
}