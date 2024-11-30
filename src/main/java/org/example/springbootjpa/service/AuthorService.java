package org.example.springbootjpa.service;

import org.example.springbootjpa.entity.Author;

import java.util.List;

public interface AuthorService {

    List<Author> findAllAuthors();

    Author findAuthorById(Long id);

    void saveAuthor(Author author);

    void updateAuthor(Author author);

    void deleteAuthor(Long id);
}

