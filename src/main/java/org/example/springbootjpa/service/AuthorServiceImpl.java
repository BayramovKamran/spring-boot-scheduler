package org.example.springbootjpa.service;

import org.example.springbootjpa.entity.Author;
import org.example.springbootjpa.repository.AuthorRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("authors")
    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Author findAuthorById(Long id) {
        return authorRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new IllegalArgumentException("Автор с ID " + id + " не найден"));
    }

    @Override
    @Transactional
    public void saveAuthor(Author author) {
        validateAuthor(author);
        authorRepository.save(author);
    }

    @Override
    @Transactional
    public void updateAuthor(Author author) {
        validateAuthor(author);
        if (author.getId() == null) {
            throw new IllegalArgumentException("ID автора не может быть null");
        }
        authorRepository.save(author);
    }

    @Override
    @Transactional
    public void deleteAuthor(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID автора не может быть null");
        }
        authorRepository.deleteById(Math.toIntExact(id));
    }

    private void validateAuthor(Author author) {
        if (author == null) {
            throw new IllegalArgumentException("Автор не может быть null");
        }
    }
}