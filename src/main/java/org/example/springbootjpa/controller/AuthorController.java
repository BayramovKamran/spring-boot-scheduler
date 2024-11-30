package org.example.springbootjpa.controller;

import org.example.springbootjpa.entity.Author;
import org.example.springbootjpa.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.findAllAuthors();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        Author author = authorService.findAuthorById(id);
        return author != null ? ResponseEntity.ok(author) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        authorService.saveAuthor(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(author);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        author.setId(id);
        authorService.updateAuthor(author);
        return ResponseEntity.ok(author);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        if (authorService.findAuthorById(id) != null) {
            authorService.deleteAuthor(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

